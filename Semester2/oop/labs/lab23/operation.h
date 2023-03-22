#pragma once
#include "medicine.h"

typedef struct {
	char command[20];
	Medicine medicine;
}Operation;

Operation* createOperation(char* command,Medicine medicine);

char* getCommand(Operation* operation);
Medicine getMedicine(Operation* operation);