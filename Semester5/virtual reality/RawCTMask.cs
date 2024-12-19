using System;
using System.IO;
using System.Text.RegularExpressions;

namespace rt;

public class RawCtMask : Geometry
{
    private readonly Vector _position;
    private readonly double _scale;
    private readonly ColorMap _colorMap;
    private readonly byte[] _data;

    private readonly int[] _resolution = new int[3];
    private readonly double[] _thickness = new double[3];
    private readonly Vector _v0;
    private readonly Vector _v1;
    private readonly Ellipsoid _ellipsoid;

    public RawCtMask(string datFile, string rawFile, Vector position, double scale, ColorMap colorMap) : base(Color.NONE)
    {
        _position = position;
        _scale = scale;
        _colorMap = colorMap;

        var lines = File.ReadLines(datFile);
        foreach (var line in lines)
        {
            var kv = Regex.Replace(line, "[:\\t ]+", ":").Split(":");
            if (kv[0] == "Resolution")
            {
                _resolution[0] = Convert.ToInt32(kv[1]);
                _resolution[1] = Convert.ToInt32(kv[2]);
                _resolution[2] = Convert.ToInt32(kv[3]);
            }
            else if (kv[0] == "SliceThickness")
            {
                _thickness[0] = Convert.ToDouble(kv[1]);
                _thickness[1] = Convert.ToDouble(kv[2]);
                _thickness[2] = Convert.ToDouble(kv[3]);
            }
        }

        _v0 = position;
        var diagonal = new Vector
        (
            _resolution[0] * _thickness[0] * scale,
            _resolution[1] * _thickness[1] * scale,
            _resolution[2] * _thickness[2] * scale
        );
        _v1 = position + diagonal;

        var len = _resolution[0] * _resolution[1] * _resolution[2]; //total nr voxels
        _data = new byte[len];
        using FileStream f = new FileStream(rawFile, FileMode.Open, FileAccess.Read);
        if (f.Read(_data, 0, len) != len)
        {
            throw new InvalidDataException($"Failed to read the {len}-byte raw data");
        }

        var halfDiagonal = diagonal / 2;

        _ellipsoid = new Ellipsoid
            (position + halfDiagonal, halfDiagonal, 1, Color.NONE);
    }

    //scalar value (voxel intensity)
    private ushort Value(int x, int y, int z)
    {
        if (x < 0 || y < 0 || z < 0 || x >= _resolution[0] || y >= _resolution[1] || z >= _resolution[2])
        {
            return 0;
        }

        return _data[z * _resolution[1] * _resolution[0] + y * _resolution[0] + x];
    }

    //ray-volume intersections
    public override Intersection GetIntersection(Line line, double minDist, double maxDist, bool? firstIntersectionFound)
    {
        var (firstIntersectionParam, lastIntersectionParam) = _ellipsoid.GetFirstAndLastIntersectionPointsTuple(line);
        if (firstIntersectionParam == null || lastIntersectionParam == null)
        {
            return Intersection.NONE;
        }
        var intersectionStart = Math.Max(firstIntersectionParam ?? 0, minDist);
        var intersectionEnd = Math.Min(lastIntersectionParam ?? 0, maxDist);
        var stepSize = _scale;
        var firstIntersection = 0d;
        var normal = new Vector();
        var accumulatedColor = new Color();
        var accumulatedAlpha = 1d;
        var hasPassedFirstIntersection = false;
        for (var i = intersectionStart; i <= intersectionEnd; i += stepSize)
        {
            var point = line.CoordinateToPosition(i);
            var voxelIndex = GetIndexes(point);
            var pointColor = GetColor(voxelIndex);
            if (pointColor.Alpha == 0)
                continue;
            if (!hasPassedFirstIntersection)
            {
                firstIntersection = i;
                normal = GetNormal(voxelIndex);
                hasPassedFirstIntersection = true;
                if (firstIntersectionFound == true)
                {
                    return new Intersection(true, true, this, line, i, normal, Material, Color.NONE);
                }
            }
            accumulatedColor += pointColor * pointColor.Alpha * accumulatedAlpha;
            accumulatedAlpha *= 1 - pointColor.Alpha;
            if (accumulatedAlpha < 1e-10)
                break;
        }
        return new Intersection
            (
                true,
                hasPassedFirstIntersection,
                this,
                line,
                firstIntersection,
                normal,
                Material.FromColor(accumulatedColor),
                accumulatedColor
            );
    }


    private int[] GetIndexes(Vector v)
    {
        return new[]{
            (int)Math.Floor((v.X - _position.X) / _thickness[0] / _scale),
            (int)Math.Floor((v.Y - _position.Y) / _thickness[1] / _scale),
            (int)Math.Floor((v.Z - _position.Z) / _thickness[2] / _scale)};
    }

    private Color GetColor(int[] idx)
    {
        ushort value = Value(idx[0], idx[1], idx[2]);
        return _colorMap.GetColor(value);
    }

    private Vector GetNormal(int[] idx)
    {
        double x0 = Value(idx[0] - 1, idx[1], idx[2]);
        double x1 = Value(idx[0] + 1, idx[1], idx[2]);
        double y0 = Value(idx[0], idx[1] - 1, idx[2]);
        double y1 = Value(idx[0], idx[1] + 1, idx[2]);
        double z0 = Value(idx[0], idx[1], idx[2] - 1);
        double z1 = Value(idx[0], idx[1], idx[2] + 1);

        return new Vector(x1 - x0, y1 - y0, z1 - z0).Normalize();
    }
}