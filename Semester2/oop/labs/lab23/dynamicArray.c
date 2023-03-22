#pragma once
#include "dynamicArray.h"
#include <stdlib.h>

DynamicArray* createDynamicArray(int capacity)
{
    DynamicArray* da = (DynamicArray*)malloc(sizeof(DynamicArray));
    // make sure that the space was allocated
    if (da == NULL)
        return NULL;

    da->max_length = capacity;
    da->count = 0;

    // allocate space for the elements
    da->data = (TElem*)malloc(capacity * sizeof(TElem));
    if (da->data == NULL)
        return NULL;

    return da;
}


void destroy(DynamicArray* array)
{
    if (array == NULL)
        return;

    // free the space allocated for the planets
    free(array->data);
    array->data = NULL;

    // free the space allocated for the dynamic array
    free(array);
}

void resize(DynamicArray* array)
{
    if (array == NULL)
        return;

    array->max_length *= 2;
    TElem* aux = realloc(array->data, array->max_length * sizeof(TElem));
    if (aux == NULL)
        return; // array cannot be resized (TODO - return error code and check when resizing)
    array->data = aux;
}

void delete_TElem(DynamicArray* array, int position)
{
    for (int i = position; i <= array->count - 1; i++)
        array->data[i] = array->data[i + 1];

    array->count--;
}

void add_TElem(DynamicArray* array, TElem* TElem)
{
    if (array == NULL)
        return;
    if (array->data == NULL)
        return;

    // resize the array, if necessary
    if (array->max_length == array->count)
        resize(array);
    array->data[array->count] = *TElem;
    array->count++;

}

void update_TElem(DynamicArray* array, TElem* TElem, int position)
{
    array->data[position] = *TElem;
}