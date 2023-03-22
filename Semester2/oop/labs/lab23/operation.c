#include "operation.h"
#include <stdlib.h>
Operation* createOperation(char* command, Medicine medicine)
{
	Operation* operation = (Operation*)malloc(sizeof(Operation));
	if (operation == NULL)
		return NULL;

	
	strcpy(operation->command, command);
	operation->medicine = medicine;
}

char* getCommand(Operation* operation)
{
	return operation->command;
}

Medicine getMedicine(Operation* operation)
{
	return operation->medicine;
}
