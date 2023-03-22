#pragma once
#include "repository.h"
#include "medicine.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "dynamicArray.h"
#include "dynamicArrayOperations.h"

Repository* createRepository()
{
	DynamicArray* array = createDynamicArray(1);
	DynamicArrayOperations* undoArray = createDynamicArrayOperations(1);
	DynamicArrayOperations* redoArray = createDynamicArrayOperations(1);
	Repository* repository = (Repository*)malloc(sizeof(Repository));
	//make sure that the space was allocated
	if (repository == NULL)
		return NULL;

	repository->array = array;
	repository->undoArray = undoArray;
	repository->redoArray = redoArray;
	initialiseRepository(repository);
	return repository;
}

int checkExistenceOfAMedicine(Repository* repository, char name[101], int concentration)
{
	for (int i = 0; i < repository->array->count; i++)
		if ((strcmp(repository->array->data[i].name, name) == 0) && repository->array->data[i].concentration == concentration)
			return i;
	return -1;
}

int getLengthOfArray(Repository* repository)
{
	return repository->array->count;
}

int addMedicine(Repository* repository, char* name, int concentration, int quantity, int price)
{
	int position;
	position = checkExistenceOfAMedicine(repository, name, concentration);
	if (position == -1)
	{
		Medicine* med = createMedicine(name, concentration, quantity, price);
		//printf("%s ", med.name);
		add_TElem(repository->array,med);
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		strcpy(operation->command, "add");
		operation->medicine = *med;
		add_Operation(repository->undoArray, operation);

		
	}
	else
	{  
		repository->array->data[position].quantity = repository->array->data[position].quantity + quantity;
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		strcpy(operation->command, "add");
		operation->medicine = repository->array->data[position];
		add_Operation(repository->undoArray, operation);

	}
}

int deleteMedicine(Repository* repository, char* name, int concentration)
{
	int position;
	position = checkExistenceOfAMedicine(repository, name, concentration);
	if (position == -1)
		return 0;
	else
	{
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		strcpy(operation->command, "delete");
		operation->medicine = repository->array->data[position];
		add_Operation(repository->undoArray, operation);
		
		delete_TElem(repository->array, position);
		
		
		
		return 1;
	}
}

int updateMedicineQuantity(Repository* repository, char* name, int concentration, int quantity)
{
	int position;
	position = checkExistenceOfAMedicine(repository, name, concentration);
	if (position == -1)
		return 0;
	else
	{
		Medicine* med = createMedicine(name, concentration, quantity, getPrice(&(repository->array->data[position])));
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		strcpy(operation->command, "update");
		operation->medicine = repository->array->data[position];
		add_Operation(repository->undoArray, operation);
		update_TElem(repository->array, med, position);
		return 1;
	}
}

int updateMedicinePrice(Repository* repository, char* name, int concentration, int price)
{
	int position;
	position = checkExistenceOfAMedicine(repository, name, concentration);
	if (position == -1)
		return 0;
	else
	{
		Medicine* med = createMedicine(name, concentration, getQuantity(&(repository->array->data[position])), price);
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		strcpy(operation->command, "update");
		operation->medicine = repository->array->data[position];
		add_Operation(repository->undoArray, operation);
		update_TElem(repository->array, med, position);
		return 1;
	}
}

void destroyRepository(Repository* repository)
{
	if (repository == NULL)
		return;
	destroy(repository->array);
	destroyOperations(repository->undoArray);
	destroyOperations(repository->redoArray);

	free(repository);

}

void swap(int* xp, int* yp)
{
	int aux = *xp;
	*xp = *yp;
	*yp = aux;
}




void initialiseRepository(Repository* repository)
{
	addMedicine(repository, "Nurofen", 10, 2, 10);
	addMedicine(repository, "Paracetamol", 20, 5, 11);
	addMedicine(repository, "Strepsils", 1, 3, 1);
	addMedicine(repository, "Algocalmin", 9, 9, 9);
	addMedicine(repository, "Diclofenac", 5, 8, 100);
	addMedicine(repository, "Fasconal", 0, 2, 8);
	addMedicine(repository, "Chetonal", 13, 6, 21);
	addMedicine(repository, "Antinevralgic", 100, 20, 9);
	addMedicine(repository, "Tantum", 1, 10, 12);
	addMedicine(repository, "Nospa", 3, 2, 17);
}

DynamicArray* getMedicineArray(Repository* repository)
{
	return repository->array;
}

void undoOperation(Repository* repository)
{
	if (strcmp(getCommand(&repository->undoArray->data[getOperationsCount(repository->undoArray) - 1]), "add") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		operation->medicine = repository->array->data[getLengthOfArray(repository->array) - 1];
		strcpy(operation->command, "add");
		add_Operation(repository->redoArray, operation);
		delete_Operation(repository->undoArray, getOperationsCount(repository->undoArray) - 1);
		
		delete_TElem(repository->array, getLengthOfArray(repository->array) - 1);
	}

	if (strcmp(getCommand(&repository->undoArray->data[getOperationsCount(repository->undoArray) - 1]), "delete") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		//operation->medicine = &repository->array->data[getLengthOfArray(repository->array) - 1];
		operation->medicine = repository->undoArray->data[getOperationsCount(repository->undoArray) - 1].medicine;
		strcpy(operation->command, "delete");
		add_Operation(repository->redoArray, operation);
		add_TElem(repository->array, &repository->undoArray->data[getOperationsCount(repository->undoArray) - 1].medicine);
		delete_Operation(repository->undoArray, getOperationsCount(repository->undoArray) - 1);
		
	}

	if (strcmp(getCommand(&repository->undoArray->data[getOperationsCount(repository->undoArray) - 1]), "update") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		//operation->medicine = &repository->array->data[getLengthOfArray(repository->array) - 1];
		int position = checkExistenceOfAMedicine(repository, repository->undoArray->data[getOperationsCount(repository->undoArray) - 1].medicine.name, repository->undoArray->data[getOperationsCount(repository->undoArray) - 1].medicine.concentration);
		operation->medicine = repository->array->data[position];
		strcpy(operation->command, "update");
		add_Operation(repository->redoArray, operation);
		update_TElem(repository->array, &repository->undoArray->data[getOperationsCount(repository->undoArray) - 1].medicine, position);
		delete_Operation(repository->undoArray, getOperationsCount(repository->undoArray) - 1);
	}


}

void redoOperation(Repository* repository)
{
	if (strcmp(getCommand(&repository->redoArray->data[getOperationsCount(repository->redoArray) - 1]), "add") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		operation->medicine = repository->redoArray->data[getOperationsCount(repository->redoArray) - 1].medicine;
		strcpy(operation->command, "add");
		add_Operation(repository->undoArray, operation);
		delete_Operation(repository->redoArray, getOperationsCount(repository->redoArray) - 1);
		add_TElem(repository->array, &operation->medicine);
	}

	if (strcmp(getCommand(&repository->redoArray->data[getOperationsCount(repository->redoArray) - 1]), "delete") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		//operation->medicine = &repository->array->data[getLengthOfArray(repository->array) - 1];
		operation->medicine = repository->redoArray->data[getOperationsCount(repository->redoArray) - 1].medicine;
		strcpy(operation->command, "delete");
		add_Operation(repository->undoArray, operation);
		int position = checkExistenceOfAMedicine(repository, operation->medicine.name, operation->medicine.concentration);
		delete_TElem(repository->array,position);
		delete_Operation(repository->redoArray, getOperationsCount(repository->redoArray) - 1);

	}

	if (strcmp(getCommand(&repository->redoArray->data[getOperationsCount(repository->redoArray) - 1]), "update") == 0) {
		Operation* operation = (Operation*)malloc(sizeof(Operation));
		if (operation == NULL)
			return;
		//operation->medicine = &repository->array->data[getLengthOfArray(repository->array) - 1];
		operation->medicine = repository->redoArray->data[getOperationsCount(repository->redoArray) - 1].medicine;

		strcpy(operation->command, "update");
		add_Operation(repository->undoArray, operation);
		int position = checkExistenceOfAMedicine(repository, operation->medicine.name, operation->medicine.concentration);
		update_TElem(repository->array, &operation->medicine, position);
		delete_Operation(repository->redoArray, getOperationsCount(repository->redoArray) - 1);
	}

}


/*
Repository duplicateRepository(Repository* repository)
{
	Repository newRepository;
	newRepository.array.max_length = repository->array.max_length;
	newRepository.array.count = repository->array.count;

	newRepository.array.data = (Medicine*)malloc(newRepository.array.max_length * sizeof(Medicine));

	copyRepository(repository, newRepository);
	return newRepository;
}

void copyRepository(Repository* repository, Repository newRepository)
{
	for (int i = 0; i < repository->array.count; i++)
		assign_values(&newRepository.array.data[i], repository->array.data[i]);
}
*/