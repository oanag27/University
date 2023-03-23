#pragma once
#include "medicine.h"
#include "dynamicArray.h"
#include "dynamicArrayOperations.h"

typedef struct {
	DynamicArray* array;
	DynamicArrayOperations* undoArray;
	DynamicArrayOperations* redoArray;
	
}Repository;

/* create a repository
*/
Repository* createRepository();

/*check existence of a medicine in the array
* name: string, represents the name of the medicine
* concentration: integer
*/
int checkExistenceOfAMedicine(Repository* repository, char name[101], int concentration);

/*get length of the array
* repository: the repository
* returns the length of the array
*/
int getLengthOfArray(Repository* repository);



/*adds a medicine to the repository
* repository: repository
* name: string, represents the name of a medicine
* concentration: integer
* quantity: integer
* price: integer
*/
int addMedicine(Repository* repository, char* name, int concentration, int quantity, int price);



/*deletes a medicine from the repository
* repository: repository
* name: string, represents the name of a medicine
* concentration: integer
*/
int deleteMedicine(Repository* repository, char* name, int concentration);

/*updates the medicine quantity
*/
int updateMedicineQuantity(Repository* repository, char* name, int concentration, int quantity);

/*updates the medicine price
*/
int updateMedicinePrice(Repository* repository, char* name, int concentration, int price);

/// dealloc the space for the repository
void destroyRepository(Repository* repository);

/// With this function we initialize the repository with 10 datas.
/// \param repo represents the repository
void initialiseRepository(Repository* repository);


DynamicArray* getMedicineArray(Repository* repository);

void undoOperation(Repository* repository);
void redoOperation(Repository* repository);
