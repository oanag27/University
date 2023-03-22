#include <stdio.h>
#include "medicine.h"
#include "repository.h"
#include "dynamicArray.h"
#include "service.h"
#include "ui.h"
#include "tests.h"
#include <crtdbg.h>


int main()
{
	Repository* repository = createRepository();
	Service* service = createService(repository);
	Console* console = createConsole(service);
	void testCreateRepository();
	void test_createDynamicArray();
	void test_addMedicineRepository();
	void test_deleteRepository();
	void test_updateRepository();
	startMenu(console);
	destroyConsole(console);
	_CrtDumpMemoryLeaks();

	/*printf("Hello world! \n");
	Repository* repo = createRepository();
	initialiseRepository(repo);
	printf("\n The initial array is: \n");
	for (int i = 0; i < repo->array->count; i++)
	{
		char buffer[100];
		transformIntoAString(repo->array->data[i], buffer);
		puts(buffer);
	}
	a)
	printf("\n Add a medicine: \n");
	addMedicine(repo, "test1", 10, 10, 10);
	for (int i = 0; i < repo->array->count; i++)
	{
		char buffer[100];
		transformIntoAString(repo->array->data[i],buffer);
		puts(buffer);
	}
	printf(" \n");
	printf("Delete a medicine: \n");
	deleteMedicine(repo, "test1", 10);
	for (int i = 0; i < repo->array->count; i++)
	{
		char buffer[100];
		transformIntoAString(repo->array->data[i], buffer);
		puts(buffer);
	}
	addMedicine(repo, "test1", 10, 10, 10);
	printf("Update medicine price and quantity: \n");
	updateMedicinePrice(repo, "test1", 10, 2);
	updateMedicineQuantity(repo, "test1", 10, 4);
	printf("\n  \n");
	for (int i = 0; i < repo->array->count; i++)
	{
		char buffer[100];
		transformIntoAString(repo->array->data[i], buffer);
		puts(buffer);
	}
	printf("\n Sort the dynamic array by the given substring \n");
	b)
	DynamicArray* sorted_array = searchElementBySubString(repo,"");
	for (int i = 0; i < sorted_array->count; i++)
	{
		char buffer[100];
		transformIntoAString(sorted_array->data[i], buffer);
		puts(buffer);
	}
	c)
	printf("\n cccc \n");
	DynamicArray* array = searchElementsThatAreShortInSupply(repo, 5);
	for (int i = 0; i < array->count; i++)
	{
		char buffer2[100];
		transformIntoAString(array->data[i], buffer2);
		puts(buffer2);
	}*/
	return 0;
}