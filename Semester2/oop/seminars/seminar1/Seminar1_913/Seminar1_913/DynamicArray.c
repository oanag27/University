#include "DynamicArray.h"
#include <stdlib.h>
#include <assert.h>

DynamicArray* createArray(int capacity)
{
	DynamicArray* arr = (DynamicArray*)malloc(sizeof(DynamicArray));
	if (arr == NULL)
		return NULL;
	arr->capacity = capacity;
	arr->size = 0;
	arr->elems = malloc(capacity * sizeof(TElem));
	if (arr->elems == NULL)
	{
		free(arr);
		return NULL;
	}
}

void destroyArray(DynamicArray* arr)
{
	if (arr == NULL)
		return;
	for (int i = 0; i < arr->size; i++)
		destroyPlanet(arr->elems[i]);
	free(arr->elems);
	free(arr);
}

int getCapacity(DynamicArray* arr)
{
	if (arr == NULL)
		return;
	return arr->capacity;
}

int getSize(DynamicArray* arr)
{
	if (arr == NULL)
		return;
	return arr->size;
}

void resizeDynamicArray(DynamicArray* arr)
{
	if (arr == NULL)
		return;
	int cap = arr->capacity * 2;
	TElem* aux = realloc(arr->elems, cap * sizeof(TElem));
	if (aux == NULL)
		return;
	arr->capacity = cap;
	arr->elems = aux;
}

void addElem(DynamicArray* arr, TElem elem)
{
	if (arr == NULL)
		return;
	if (arr->size == arr->capacity)
		resizeDynamicArray(arr);
	arr->elems[arr->size++] = elem;
}

void testDynamicArray()
{
	DynamicArray* arr = createArray(2);
	assert(getCapacity(arr) == 2);
	assert(getSize(arr) == 0);

	Planet p = createPlanet("HD 189733 b", "a blue-ish planet", 10);
	addElem(arr, p);

	/*addElem(arr, 10);
	assert(getCapacity(arr) == 2);
	assert(getSize(arr) == 1);

	addElem(arr, 22);
	assert(getCapacity(arr) == 2);
	assert(getSize(arr) == 2);

	addElem(arr, 33);
	assert(getCapacity(arr) == 4);
	assert(getSize(arr) == 3);*/

	destroyArray(arr);
}
