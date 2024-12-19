using System;


namespace rt
{
    public class Ellipsoid : Geometry
    {
        private Vector Center { get; }
        private Vector SemiAxesLength { get; }
        private Vector LengthOfSemiAxesSquared { get; } 
        private double Radius { get; }
        
        
        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Material material, Color color) : base(material, color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            LengthOfSemiAxesSquared = new Vector(SemiAxesLength.X * SemiAxesLength.X, SemiAxesLength.Y * SemiAxesLength.Y, SemiAxesLength.Z * SemiAxesLength.Z);
            Radius = radius;
        }

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Color color) : base(color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            LengthOfSemiAxesSquared = new Vector(SemiAxesLength.X * SemiAxesLength.X, SemiAxesLength.Y * SemiAxesLength.Y, SemiAxesLength.Z * SemiAxesLength.Z);
            Radius = radius;
        }

        //normalizes a given vector
        private Vector NormalizeComponentsRelativeToSemiAxes(Vector vector)
        {
            // Divide component by the corresponding semi-axes length.
            return new Vector(vector.X / SemiAxesLength.X, vector.Y / SemiAxesLength.Y, vector.Z / SemiAxesLength.Z);
        }

        private Vector NormalizeComponentsRelativeToSquaredSemiAxes(Vector vector)
        {
            // Divide component by the corresponding squared semi-axes length.
            return new Vector(vector.X / LengthOfSemiAxesSquared.X, vector.Y / LengthOfSemiAxesSquared.Y, vector.Z / LengthOfSemiAxesSquared.Z);
        }

        public Tuple<double?, double?> GetFirstAndLastIntersectionPointsTuple(Line line)
        {
            var directionNormalizedByAxes = NormalizeComponentsRelativeToSemiAxes(line.Dx);
            var startToCenterNormalizedByAxes = NormalizeComponentsRelativeToSemiAxes(line.X0 - Center);
            // Coefficients -> quadratic equation.(ray-ellipsoid intersection)
            var qa = directionNormalizedByAxes.Length2();
            var qb = directionNormalizedByAxes * startToCenterNormalizedByAxes * 2;
            var qc = startToCenterNormalizedByAxes.Length2() - Radius * Radius;
            // Discriminant(nr of solutions->intersection points)
            var discriminant = qb * qb - 4 * qa * qc; //Δ
            if (discriminant < 1e-10) 
                return new Tuple<double?, double?>(null, null);
            // Repeated root
            if (Math.Abs(discriminant) < 1e-10)
            {
                return new Tuple<double?, double?>(-qb / (2 * qa), null);
            }
            // Quadratic equation
            var squareRootOfDiscriminant = Math.Sqrt(discriminant);
            // Two roots of the equation.
            var inverse2A = 1 / (2 * qa);
            var root1 = (-qb - squareRootOfDiscriminant) * inverse2A;
            var root2 = (-qb + squareRootOfDiscriminant) * inverse2A;
            return new Tuple<double?, double?>(root1, root2);
        }

        public override Intersection GetIntersection(Line line, double minDist, double maxDist, bool? firstIntersectionFound)
        {
            // TODO: ADD CODE HERE
            var (firstIntersectionPoint, lastIntersectionPoint) = GetFirstAndLastIntersectionPointsTuple(line);
            if (firstIntersectionPoint == null)
            {
                return Intersection.NONE;
            }
            if (lastIntersectionPoint== null)
            {
                var intersectionParam = firstIntersectionPoint ?? 0;
                var isWithinRange = intersectionParam >= minDist && intersectionParam <= maxDist;
                var position = line.CoordinateToPosition(intersectionParam);
                var normal = NormalizeComponentsRelativeToSquaredSemiAxes((position - Center) * 2).Normalize();
                return new Intersection(true, isWithinRange, this, line, intersectionParam, normal, Material, Color);
            }
            else
            {
                var isWithinRange = (firstIntersectionPoint >= minDist && firstIntersectionPoint <= maxDist) || (lastIntersectionPoint >= minDist && lastIntersectionPoint <= maxDist);
                var intersectionParam = (firstIntersectionPoint >= minDist ? firstIntersectionPoint : lastIntersectionPoint) ?? 0; // closest point
                var position = line.CoordinateToPosition(intersectionParam);  //intersection position
                var normal = NormalizeComponentsRelativeToSquaredSemiAxes((position - Center) * 2).Normalize();
                return new Intersection(true, isWithinRange, this, line, intersectionParam, normal, Material, Color);
            }
        }
    }
}

