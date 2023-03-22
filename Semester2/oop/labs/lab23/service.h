#pragma once
#include "repository.h"


typedef struct
{
    Repository* repository;
} Service;

Service* createService(Repository* repository);

/// Add a medicine
/// * repository: repository
/// * name: string, represents the name of a medicine
/// * concentration : integer
/// * quantity : integer
/// * price : integer
void addMedicineService(Service* service, char* name, int concentration, int quantity, int price);


DynamicArray* getMedicineArrayService(Service* service);

/// With this function we delete
/// \param service represents the service
/// \param name represents the name of the product
/// \param type represents the type of the product
/// \return returns 0 if the given product doesn't exits or 1 otherwise
int deleteMedicineService(Service* service, char* name, int concentration);
/*updates the medicine quantity
*/
int updateMedicineQuantityService(Service* service, char* name, int concentration, int quantity);

/*updates the medicine price
*/
int updateMedicinePriceService(Service* service, char* name, int concentration, int price);

/// dealloc the space for the repository
void destroyService(Service* service);

/// search for medicine which names contain a given substring
/// \param repository represents the repository
/// \param string represents the string after which we look in the products names
/// \param repository represents the new repository in which we add the products which contain the given string
DynamicArray* searchElementBySubStringService(Service* service, char* string);

/// search for medicine that are short in supply
/// \param repository represents the repository
/// \param x represents the number of elements that we are comparing to
DynamicArray* searchElementsThatAreShortInSupplyService(Service* service, int x);

void undoOperationService(Service* service);
void redoOperationService(Service* service);