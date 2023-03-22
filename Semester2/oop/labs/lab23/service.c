#include "service.h"
#include <stdlib.h>
#include <string.h>

Service* createService(Repository* repository)
{
    Service* service = (Service*)malloc(sizeof(Service));

    if (service == NULL)
        return NULL;
    service->repository = repository;
    return service;
}

void addMedicineService(Service* service, char* name, int concentration, int quantity, int price)
{
    addMedicine(service->repository, name, concentration, quantity, price);
}

DynamicArray* getMedicineArrayService(Service* service)
{
    return getMedicineArray(service->repository);
}

int deleteMedicineService(Service* service, char* name, int concentration)
{
    if (deleteMedicine(service->repository, name, concentration) == 0)
        return 0;
    else
    {
        ///addRepositoryToUndoRedoRepository(&service->undo_repository, &service->repository);
        return 1;
    }
}

int updateMedicineQuantityService(Service* service, char* name, int concentration, int quantity)
{
    if (updateMedicineQuantity(service->repository, name, concentration, quantity) == 0)
        return 0;
    else
    {
        ///addRepositoryToUndoRedoRepository(&service->undo_repository, &service->repository);
        return 1;
    }
}

int updateMedicinePriceService(Service* service, char* name, int concentration, int price)
{
    if (updateMedicinePrice(service->repository, name, concentration, price) == 0)
        return 0;
    else
    {
       /// addRepositoryToUndoRedoRepository(&service->undo_repository, &service->repository);
        return 1;
    }
}

void destroyService(Service* service)
{
    if (service == NULL)
        return;

    free(service);
}

DynamicArray* searchElementBySubStringService(Service* service, char* string)
{
    DynamicArray* new_array = getMedicineArrayService(service);
        if (strlen(string) == 0)
        {
            for (int i = 0; i < new_array->count - 1; i++)
                for (int j = i + 1; j < new_array->count; j++)
                {
                    if (strcmp(new_array->data[i].name, new_array->data[j].name) > 0)
                    {
                        swap(&new_array->data[i], &new_array->data[j]);
                    }
                }
            return new_array;

        }
        else
        {
            DynamicArray* da = createDynamicArray(1);
            for (int i = 0; i < new_array->count; i++)
            {
                if (strstr(new_array->data[i].name, string) != NULL)
                    add_TElem(da, &new_array->data[i]);
            }

            for (int i = 0; i < da->count - 1; i++)
                for (int j = i + 1; j < da->count; j++)
                {
                    if (strcmp(da->data[i].name, da->data[j].name) > 0)
                    {
                        swap(&da->data[i], &da->data[j]);
                    }
                }
            return da;
        }

    
}

void swap2(int* xp, int* yp)
{
    int aux = *xp;
    *xp = *yp;
    *yp = aux;
}

void sortAscendingQuantity(Service* service,int x, Repository* repository)
{
    for (int i = 0; i < repository->array->count - 1; i++)
        for (int j = i + 1; j < repository->array->count; j++)
            if (repository->array->data[i].quantity > repository->array->data[j].quantity)
            {
                swap2(&repository->array->data[i], &repository->array->data[j]);
            }
}


DynamicArray* searchElementsThatAreShortInSupplyService(Service* service, int x)
{
    DynamicArray* new_array = getMedicineArrayService(service);
        DynamicArray* da = createDynamicArray(1);
        for (int i = 0; i < new_array->count; i++)
        {
            if (new_array->data[i].quantity < x)
                add_TElem(da, &new_array->data[i]);
        }
        return da;    
}

void undoOperationService(Service* service)
{
    undoOperation(service->repository);
}

void redoOperationService(Service* service)
{
    redoOperation(service->repository);
}
