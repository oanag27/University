#include "Planet.h"
#include <crtdbg.h>
#include <stdio.h>
#include "DynamicArray.h"

int main()
{
	Planet p = createPlanet("HD 189733 b", "a blue-ish planet", 10);
	/*printf("Address of planet in main: %p\n", &p);
	printf("Address of name in function: %p\n", p.name);
	printf("Address of type in function: %p\n", p.type);*/

	destroyPlanet(p);

	testDynamicArray();

	_CrtDumpMemoryLeaks();

	return 0;
}