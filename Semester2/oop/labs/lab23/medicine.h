
#pragma once
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct
{
    char* name;
    int concentration;
    int quantity;
    int price;
} Medicine;


/*create a medicine
name: string, represents the name of the medicine
concentration: integer, represents the concentration of the medicine
quantity: integer, represents the quantity of the medicine
price: integer, represents the price of the medicine
*/

Medicine* createMedicine(char* name, int concentration, int quantity, int price);


/*destroy medicine
name: string, represents the name of the medicine
*/

//void destoryMedicine(Medicine* name); //the memory is freed


/*get name of a medicine
medication: medication
returns the name of the medication(string)
*/

char* getName(Medicine* medication);


/*get concentration of a medicine
medication: medication
returns the concentration of the medication(integer)*/

int getConcentration(Medicine* medication);


/*get quantity of a medicine
medication: medication
returns the quantity of the medication(integer)*/

int getQuantity(Medicine* medication);


/*get price of a medicine
medication: medication
returns the price of the medication(integer)
*/

int getPrice(Medicine* medication);


/*create a copy of a medication
medication: medication
returns a pointer to the created and allocated - copy of the initial medication given
*/

//Medicine createACopyOfAMedication(Medicine* medication);

/*copy the contains of a medication from a source medication, to a destination medication
destinationMedication: pointer to the copy of the source medication
sourceMedication: pointer to a medication which will be duplicated(initial medication)
*/
//void copyOfAMedication(Medicine* destinationMedication, Medicine* sourceMedication);

/*create a medication of representation of a string
medication: pointer to a medication
stringMedication: array of characters which will contain the string representation of the data stored in medication
*/
void transformIntoAString(Medicine medication, char stringMedication[]);