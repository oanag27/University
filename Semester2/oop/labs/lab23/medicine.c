
#pragma once
#include "medicine.h"
#include <string.h>
#include <stdio.h>

Medicine* createMedicine(char* name, int concentration, int quantity, int price)
{
    Medicine* medication = (Medicine*) malloc(sizeof(Medicine));
    medication->name = (char*) malloc(sizeof(char)*(strlen(name)+1));
    strcpy(medication->name, name); //copy the name of the medication
    medication->quantity = quantity; //introduce the quantity of the medication
    medication->concentration = concentration; //introduce the concentration of the medication
    medication->price = price; //introduce the price of the medication
    return medication;  //return the medication
}

/*
void destoryMedicine(Medicine* name)
{
    if (name == NULL)
        return;
    free(name);
}
*/
char* getName(Medicine* medication)
{
    if (medication == NULL)
        return NULL;
    return medication->name;
}

int getConcentration(Medicine* medication)
{
    if (medication == NULL)
        return -1;
    return medication->concentration;
}

int getQuantity(Medicine* medication)
{
    if (medication == NULL)
        return -1;
    return medication->quantity;
}

int getPrice(Medicine* medication)
{
    if (medication == NULL)
        return -1;
    return medication->price;
}
/*
Medicine* createACopyOfAMedication(Medicine* medication)
{
    if (medication == NULL)
        return NULL;
    Medicine* newMedication = createMedicine(getName(medication), getConcentration(medication), getQuantity(medication), getPrice(medication));
    return newMedication;
}

void copyOfAMedication(Medicine* destinationMedication, Medicine* sourceMedication)
{
    strcpy_s(destinationMedication->name, getName(sourceMedication));
    destinationMedication->concentration = getConcentration(sourceMedication);
    destinationMedication->quantity = getQuantity(sourceMedication);
    destinationMedication->price = getPrice(sourceMedication);
}
*/

void transformIntoAString(Medicine medication, char stringMedication[])
{
    sprintf(stringMedication, "%s has concentration %d%%, quantity %d and price %d \n", medication.name, medication.concentration, medication.quantity, medication.price);
}

