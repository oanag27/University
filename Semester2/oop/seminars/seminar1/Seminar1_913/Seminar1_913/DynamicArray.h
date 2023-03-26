#pragma once
#include "Planet.h"

typedef Planet TElem;

typedef void (*destroyFct)(TElem);

typedef struct
{
	TElem* elems;
	int size, capacity;
	destroyFct destructionFunction;
} DynamicArray;


DynamicArray* createArray(int capacity);
void destroyArray(DynamicArray* arr);
int getCapacity(DynamicArray* arr);
int getSize(DynamicArray* arr);
void addElem(DynamicArray* arr, TElem elem);

void testDynamicArray();