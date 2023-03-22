#include "tests.h"
#include "repository.h"
#include "dynamicArray.h"
#include "service.h"
#include <crtdbg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

void test_createDynamicArray()
{
    DynamicArray* array = createDynamicArray(100);
    assert(array->count== 0);
    assert(array->max_length == 100);
    destroy(array);
}
void test_addMedicineRepository()
{
    DynamicArray* array = createDynamicArray(10);
    Repository* repository = createRepository(*array);
    initialiseRepository(repository);
    addMedicine(repository, "a", 1, 2, 3);
    assert(repository->array->count == 11);
    assert(strcmp(repository->array->data[10].name,'a') == 0);
    assert(repository->array->data[10].concentration == 1);
    assert(repository->array->data[10].price == 3);

}
void testCreateRepository()
{
        DynamicArray* array = createDynamicArray(1);
        Repository* repository = createRepository(*array);
        initialiseRepository(repository);
        assert(strcmp(repository->array->data[0].name, "Nurofen") == 0);
        assert(repository->array->data[0].concentration == 10);
        assert(repository->array->data[0].quantity == 2);
        assert(repository->array->data[0].price == 10);
        destroyRepository(repository);
        destroy(array);
}

void test_deleteRepository()
{
    DynamicArray* array = createDynamicArray(10);
    Repository* repository = createRepository(*array);
    initialiseRepository(repository);
    addMedicine(repository, "a", 1, 2, 3);
    deleteMedicine(repository, "a", 1);
    assert(repository->array->count == 10);
}

void test_updateRepository()
{
    DynamicArray* array = createDynamicArray(10);
    Repository* repository = createRepository(*array);
    initialiseRepository(repository);
    updateMedicineQuantity(repository, "Nospa", 3, 100);
    assert(repository->array->data[9].quantity == 100);
}