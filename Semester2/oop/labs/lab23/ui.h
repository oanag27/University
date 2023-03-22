#pragma once
#include "medicine.h"
#include "repository.h"
#include "service.h"
#include <stdio.h>

typedef struct
{
    Service* service;
} Console;


///  With this function we create the console
/// \param service represents the service
/// \return it returns the created console
Console* createConsole(Service* service);

/// With this function we destroy the console
/// \param console
void destroyConsole(Console* console);

///// With this function we display all the products
///// \param console
//void displayMedicineConsole(Console* console);

/// With this function we start the program
void startMenu(Console* console);
