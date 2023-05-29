#include "ui.h"
#include <iostream>
using namespace std;
void UI::addUi()
{
	cout << "Size:";
	string size; 
	cin >> size;
	cout << "Colour:";
	string colour; 
	cin >> colour;
	cout << "Price:";
	int price; 
	cin >> price;
	cout << "Quantity:";
	int quantity; 
	cin >> quantity;
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	cin.get();
	if (this->service.addService(Coat(size, colour, price, quantity, photograph)) == 1)
	{
		cout << "Coat added \n";
	}
	else
	{
		cout << "Coat already exists \n";
	}
}

void UI::updateUi()
{
	cout << "Size:";
	string size;
	cin >> size;
	cout << "Colour:";
	string colour;
	cin >> colour;
	cout << "Price:";
	int price;
	cin >> price;
	cout << "Quantity:";
	int quantity;
	cin >> quantity;
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	cin.get();
	cout << "New coat: ";
	cout << "Size:";
	string new_size;
	cin >> new_size;
	cout << "Colour:";
	string new_colour;
	cin >> new_colour;
	cout << "Price:";
	int new_price;
	cin >> new_price;
	cout << "Quantity:";
	int new_quantity;
	cin >> new_quantity;
	cout << "Photograph:";
	string new_photograph;
	cin >> new_photograph;
	cin.get();
	if (this->service.updateService(Coat(size, colour, price, quantity, photograph),Coat(new_size,new_colour,new_price,new_quantity,new_photograph)) == 1)
	{
		cout << "Coat updated \n";
	}
	else
	{
		cout << "Coat does not exist \n";
	}
}

void UI::addUserUi()
{
	std::vector<Coat>new_coat = service.get_all_coats_service();
	int i = 0;
	while (i < new_coat.size())
	{
		cout << "\nThe coat is: \n";
		cout << "Size:" << new_coat[i].getSize() << "\n" << "Colour:" << new_coat[i].getColour() << "\n" << "Price:" << new_coat[i].getPrice() << "\n" << "Quantity:" << new_coat[i].getQuantity() << "\n" << "Photograph:" << new_coat[i].getPhotograph() << "\n";
		shoppingMenu();
		int shoppingOption;
		cout << "Option: ";
		cin >> shoppingOption;
		if (shoppingOption == 1) {
			service.addToShoppingBasket(new_coat[i]);
			int price = service.getTotalPriceUser();
			cout << "Total price is: " << price << "\n";
		}
		else if (shoppingOption == 2)
		{
			if (i == new_coat.size() - 1)
			{
				i = 0;
			}
		}
		else if (shoppingOption == 3)
		{
			cout << "Program done\n";
			break;
		}
		else
		{
			cout << "Invalid command";
		}
		i++;
	}
}

void UI::get_all_coats_user_ui_and_price()
{
	int price = 0;
	std::vector<Coat>new_coat = service.get_all_coats_service();
	for (int i = 0; i < new_coat.size(); i++)
	{
		cout << "Size:" << new_coat[i].getSize() << "\n" << "Colour:" << new_coat[i].getColour() << "\n" << "Price:" << new_coat[i].getPrice() << "\n" << "Quantity:" << new_coat[i].getQuantity() << "\n" << "Photograph:" << new_coat[i].getPhotograph() << "\n";
		price += new_coat[i].getPrice();
	}
	cout << "Total price is: " << price << "\n";
	
}


void UI::removeUi()
{
	cout << "Size:";
	string size;
	cin >> size;
	cout << "Colour:";
	string colour;
	cin >> colour;
	cout << "Price:";
	int price;
	cin >> price;
	cout << "Quantity:";
	int quantity;
	cin >> quantity;
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	cin.get();
	if (this->service.removeService(Coat(size, colour, price, quantity, photograph)) == 1)
	{
		cout << "Coat removed \n";
	}
	else
	{
		cout << "Coat does not exist \n";
	}
}

void UI::get_all_coats_ui()
{
	std::vector<Coat>new_coat = service.get_all_coats_service();
	for (int i = 0; i < new_coat.size(); i++)
	{
		cout << "Size:" << new_coat[i].getSize() <<"\n"<< "Colour:"<< new_coat[i].getColour() << "\n" << "Price:"<< new_coat[i].getPrice() << "\n" << "Quantity:"<< new_coat[i].getQuantity()<<"\n"<< "Photograph:"<<new_coat[i].getPhotograph() << "\n";

	}
}

void UI::get_all_coats_user_by_size()
{
	string size;
	cout << "Size:";
	cin.ignore();
	getline(cin, size);
	std::vector<Coat>new_coat = service.get_all_coats_by_size_service(size);
	for (int i = 0; i < new_coat.size(); i++) {
		cout << "Size:" << new_coat[i].getSize() << "\n" << "Colour:" << new_coat[i].getColour() << "\n" << "Price:" << new_coat[i].getPrice() << "\n" << "Quantity:" << new_coat[i].getQuantity() << "\n" << "Photograph:" << new_coat[i].getPhotograph() << "\n";

	}
	
}

void UI::shoppingMenu()
{
	cout << "\n\n1.Buy\n";
	cout << "2.Skip\n";
	cout << "3.Done\n";
}

void UI::menuAdmin()
{
	cout << "Administrator mode: \n";
	cout << "1.Show all coats \n";
	cout << "2.Add a coat \n";
	cout << "3.Remove a coat \n";
	cout << "4.Update a coat \n";
	cout << "5.Exit";
	
}

void UI::menuUser()
{
	cout << "User mode: \n";
	cout << "1.See all trench coats with size given \n";
	cout << "2.Choose to add a coat to the shopping bascket\n";
	cout << "3.See the shopping basket and the total price of the items. \n";
	cout << "4.Exit";
}

void UI::runMenu()
{
	while (userMode != 0 && userMode != 1) {
		cout << "Choose a mode: 1 for admin, 0 for user: ";
		cin >> userMode;
	}
	while (1)
	{
		if (userMode == 1) {
			menuAdmin();
			cout << "Option: \n";
			int option;
			cin >> option;
			if (option == 1)
			{
				get_all_coats_ui();
			}
			else if (option == 2)
			{
				addUi();
			}
			else if (option == 3)
			{
				removeUi();
			}
			else if (option == 4)
			{
				updateUi();
			}
			else if (option == 5)
			{
				break;
			}
			else cout << "Invalid option\n";
		}
		else if (userMode == 0)
		{

			menuUser();
			cout << "Option: \n";
			int option;
			cin >> option;
			if (option == 1)
			{
				get_all_coats_user_by_size();
			}
			else if (option == 2)
			{
				addUserUi();
			}
			else if (option == 3)
			{
				get_all_coats_user_ui_and_price();
			}
			else cout << "Invalid option\n";
		}
		
	}
}
