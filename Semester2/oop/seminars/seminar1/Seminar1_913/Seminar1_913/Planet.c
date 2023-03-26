#include "Planet.h"
#include <stdlib.h>
#include <stdio.h>

Planet createPlanet(char* name, char* type, double dist)
{
	Planet p;
	p.name = malloc((strlen(name) + 1) * sizeof(char));
	if (p.name == NULL)
		return p;
	strcpy(p.name, name);

	p.type = malloc((strlen(type) + 1) * sizeof(char));
	if (p.type == NULL)
	{
		free(p.name);
		return p;
	}
	strcpy(p.type, type);

	/*printf("Address of planet in function: %p\n", &p);
	printf("Address of name in function: %p\n", p.name);
	printf("Address of type in function: %p\n", p.type);*/

	p.distanceFromEarth = dist;

	return p;
}

void destroyPlanet(Planet p)
{
	free(p.name);
	free(p.type);
}

char* getName(Planet p)
{

}
