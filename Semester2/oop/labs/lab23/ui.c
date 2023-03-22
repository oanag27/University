#include "ui.h"
#include "dynamicArray.h"
#include "medicine.h"
#include "repository.h"
#include "service.h"
#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>



Console* createConsole(Service* service)
{
    Console* console = (Console*)malloc(sizeof(Console));

    if (console == NULL)
        return NULL;

    console->service = service;

    return console;
}

void destroyConsole(Console* console)
{
    if (console == NULL)
        return;

    free(console);
}

//void displayMedicineConsole(Console* console)
//{
//    for (int i = 0; i < console->service->repository->array->count; i++)
//        printf("Name: %s  Concentration: %s  Quantity: %d  Price: %s\n", console->service->repository->array->data[i].name,
//            console->service->repository->array->data[i].concentration, console->service->repository->array->data[i].quantity,
//            console->service->repository->array->data[i].price);
//
//}

void menu()
{
    printf("Type 1: print the initial array \n");
    printf("Type 2: add a medicine \n");
    printf("Type 3: delete a medicine \n");
    printf("Type 4: update price \n");
    printf("Type 5: update quantity \n");
    printf("Type 6: search elements by a given substring \n");
    printf("Type 7: search elements that are short in supply \n");
    printf("Type 8: undo last operation\n");
    printf("Type 9: redo last operation\n");
    printf("Type 10: exit \n");
    printf("\nChoose: ");
}

void startMenu(Console* console)
{
    int choice;
    while (1) {
        menu();
        scanf("%d", &choice);
        if (choice == 10) {
            return;
        }
        if (choice == 1)
        {
            DynamicArray* arrayExample = getMedicineArrayService(console->service);
            printf("\nThe initial array is: \n");
            for (int i = 0; i < arrayExample->count; i++)
            {
                char buffer[100];
                transformIntoAString(arrayExample->data[i], buffer);
                puts(buffer);
            }

        }
        if (choice == 2)
        {
            char name[100];
            int concentration;
            int quantity;
            int price;
            printf("Choose a name: ");
            scanf("%s", name);
            printf("Choose a concentration: ");
            scanf("%d", &concentration);
            printf("Choose a quantity: ");
            scanf("%d", &quantity);
            printf("Choose a price: ");
            scanf("%d", &price);
            ///getchar();
            addMedicineService(console->service,name,concentration,quantity,price);
            ///printf(" ");
  
        }
        if (choice == 3)
        {
            char name[100];
            int concentration;
            int quantity;
            int price;
            printf("Choose a name: ");
            scanf("%s", name);
            printf("Choose a concentration: ");
            scanf("%d", &concentration);
            printf("Choose a quantity: ");
            scanf("%d", &quantity);
            printf("Choose a price: ");
            scanf("%d", &price);
            deleteMedicineService(console->service, name, concentration, quantity, price);
        }
        if (choice == 4)
        {
            char name[100];
            int concentration;
            int price;
            printf("Choose a name: ");
            scanf("%s", name);
            printf("Choose a concentration: ");
            scanf("%d", &concentration);
            printf("Choose a price: ");
            scanf("%d", &price);
            updateMedicinePriceService(console->service, name, concentration, price);
        }
        if (choice == 5)
        {
            char name[100];
            int concentration;
            int quantity;
            printf("Choose a name: ");
            scanf("%s", name);
            printf("Choose a concentration: ");
            scanf("%d", &concentration);
            printf("Choose a quantity: ");
            scanf("%d", &quantity);
            updateMedicineQuantityService(console->service, name, concentration, quantity);
        }
        if (choice == 6)
        {
            char string[101];
            printf("Choose a string: ");
            //scanf("%s", string);
            gets(string);
            DynamicArray* new_array = searchElementBySubStringService(console->service, string);
            for (int i = 0; i < new_array->count; i++)
            {
                char buffer[100];
                transformIntoAString(new_array->data[i], buffer);
                puts(buffer);
            }
        }
        if (choice == 7)
        {
            int x;
            printf("Choose an x: ");
            scanf("%d", &x);
            DynamicArray* new_array = searchElementsThatAreShortInSupplyService(console->service, x);
            for (int i = 0; i < new_array->count; i++)
            {
                char buffer[100];
                transformIntoAString(new_array->data[i], buffer);
                puts(buffer);
            }
        }
        if (choice == 8)
        {
            undoOperationService(console->service);
        }

        if (choice == 9)
        {
            redoOperationService(console->service);
        }

    }
}
