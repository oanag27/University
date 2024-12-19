using System;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        // 2D screen coordinates into 3D coordinates on the camera’s view plane
        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            return -n * viewPlaneSize / imgSize + viewPlaneSize / 2;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = Intersection.NONE;
            foreach (var geometry in geometries)
            {
                var currentIntersection = geometry.GetIntersection(ray, minDist, maxDist);

                if (!currentIntersection.Valid || !currentIntersection.Visible) 
                    continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = currentIntersection;
                }
                else if (currentIntersection.T < intersection.T)
                {
                    intersection = currentIntersection;
                }
            }
            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            // TODO: ADD CODE HERE
            var incidentLine = new Line(point, light.Position);
            const double epsilon = 1e-10;
            var lightDistance = (point - light.Position).Length();
            foreach (var geometry in geometries)
            {
                // Skip RawCtMask geometry
                if (geometry is RawCtMask)
                {
                    continue;
                }
                var intersection = geometry.GetIntersection(incidentLine, epsilon, lightDistance, true);
                if (intersection.Visible)
                {
                    return false;
                }
            }
            return true;
        }

        public void Render(Camera camera, int width, int height, string filename)
        {
            var backgroundColor = new Color(0.2, 0.2, 0.2, 1.0);
            var viewParallel = (camera.Up ^ camera.Direction).Normalize(); //horizontal axis
            var image = new Image(width, height);
            var cameraViewDirection = camera.Direction * camera.ViewPlaneDistance;
            // Loop through pixels
            for (var i = 0; i < width; i++)
            {
                var normalizedViewPlaneX = ImageToViewPlane(i, width, camera.ViewPlaneWidth);
                // Loop through rows
                for (var j = 0; j < height; j++)
                {
                    var normalizedViewPlaneY = ImageToViewPlane(j, height, camera.ViewPlaneHeight);
                    var rayVector = camera.Position
                                    + cameraViewDirection
                                    + viewParallel * normalizedViewPlaneX
                                    + camera.Up * normalizedViewPlaneY;
                    var ray = new Line(camera.Position, rayVector); 
                    var closestIntersection = FindFirstIntersection(ray, camera.FrontPlaneDistance, camera.BackPlaneDistance);
                    if (!closestIntersection.Visible) //no intersection
                    {
                        image.SetPixel(i, j, backgroundColor);
                        continue;
                    }
                    var material = closestIntersection.Material;
                    var pixelColor = new Color();
                    var surfacePoint = closestIntersection.Position;
                    var cameraToSurfaceVector = (camera.Position - surfacePoint).Normalize();
                    var surfaceNormal = closestIntersection.Normal;
                    // Iterate over each light source to calculate lighting contributions
                    foreach (var light in lights)
                    {
                        var ambientComponent = material.Ambient * light.Ambient;
                        if (IsLit(surfacePoint, light))
                        {
                            var lightDirection = (light.Position - surfacePoint).Normalize();
                            var reflectionDirection = (surfaceNormal * (surfaceNormal * lightDirection) * 2 - lightDirection).Normalize();
                            var diffuseFactor = surfaceNormal * lightDirection;
                            var specularFactor = cameraToSurfaceVector * reflectionDirection;
                            if (diffuseFactor > 0) //surface faces the light
                            {
                                pixelColor += material.Diffuse * light.Diffuse * diffuseFactor;
                            }
                            if (specularFactor > 0) //reflection direction is valid
                            {
                                pixelColor += material.Specular * light.Specular * Math.Pow(specularFactor, material.Shininess);
                            }
                            pixelColor *= light.Intensity;
                        }
                        pixelColor += ambientComponent;
                    }
                    image.SetPixel(i, j, pixelColor);
                }
            }
            image.Store(filename);
        }
    }
}