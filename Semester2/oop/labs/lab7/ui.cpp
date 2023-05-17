#include "ui.h"
#include "fileCoat.h"
#include "exception.h"
#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;
void UI::addUi()
{
	Validator validator;
	cout << "Size:";
	string size;
	cin >> size;
	try {
		if (validator.check_string(size)!=true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Colour:";
	string colour;
	cin >> colour;
	try {
		if (validator.check_string(colour)!=true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Price:";
	string price;
	cin >> price;
	std::stoi(price);
	try {
		if (validator.check_integer(price) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Quantity:";
	string quantity;
	cin >> quantity;
	std::stoi(quantity);
	try {
		if (validator.check_integer(quantity) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	try {
		if (validator.check_string(photograph) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cin.get();
	if (this->service.addService(Coat(size, colour,std::stoi(price), std::stoi(quantity), photograph)) == 1)
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
	Validator validator;
	cout << "Size:";
	string size;
	cin >> size;
	try {
		if (validator.check_string(size) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Colour:";
	string colour;
	cin >> colour;
	try {
		if (validator.check_string(colour) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Price:";
	string price;
	cin >> price;
	std::stoi(price);
	try {
		if (validator.check_integer(price) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Quantity:";
	string quantity;
	cin >> quantity;
	std::stoi(quantity);
	try {
		if (validator.check_integer(quantity) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	try {
		if (validator.check_string(photograph) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cin.get();
	cout << "New coat: ";
	cout << "Size:";
	string new_size;
	cin >> new_size;
	try {
		if (validator.check_string(new_size) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Colour:";
	string new_colour;
	cin >> new_colour;
	try {
		if (validator.check_string(new_colour) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Price:";
	string new_price;
	cin >> new_price;
	try {
		if (validator.check_integer(new_price) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Quantity:";
	string new_quantity;
	cin >> new_quantity;
	try {
		if (validator.check_integer(new_quantity) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Photograph:";
	string new_photograph;
	cin >> new_photograph;
	try {
		if (validator.check_string(new_photograph) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cin.get();
	if (this->service.updateService(Coat(size, colour, std::stoi(price), std::stoi(quantity), photograph), Coat(new_size, new_colour, std::stoi(new_price), std::stoi(new_quantity), new_photograph)) == 1)
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
	Validator validator;
	cout << "Size:";
	string size;
	cin >> size;
	try {
		if (validator.check_string(size) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Colour:";
	string colour;
	cin >> colour;
	try {
		if (validator.check_string(colour) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Price:";
	string price;
	cin >> price;
	std::stoi(price);
	try {
		if (validator.check_integer(price) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Quantity:";
	string quantity;
	cin >> quantity;
	std::stoi(quantity);
	try {
		if (validator.check_integer(quantity) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cout << "Photograph:";
	string photograph;
	cin >> photograph;
	try {
		if (validator.check_string(photograph) != true)
		{
			throw Exception("Invalid type\n");
		}
	}
	catch (const Exception& exception) {
		// Handle the exception
		cout << "Exception caught: " << exception.what() << endl;
		return;
	}
	cin.get();
	if (this->service.removeService(Coat(size, colour,std::stoi(price), std::stoi(quantity), photograph)) == 1)
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
		cout << "Size:" << new_coat[i].getSize() << "\n" << "Colour:" << new_coat[i].getColour() << "\n" << "Price:" << new_coat[i].getPrice() << "\n" << "Quantity:" << new_coat[i].getQuantity() << "\n" << "Photograph:" << new_coat[i].getPhotograph() << "\n";

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
				try {
					addUi();
				}
				catch (const Exception& exception) {
					// Handle the exception
					cout << "Exception caught: " << exception.what() << endl;
				}
			}
			else if (option == 3)
			{
				try {
					removeUi();
				}
				catch (const Exception& exception) {
					// Handle the exception
					cout << "Exception caught: " << exception.what() << endl;
				}
			}
			else if (option == 4)
			{

				try {
					updateUi();
				}
				catch (const Exception& exception) {
					// Handle the exception
					cout << "Exception caught: " << exception.what() << endl;
				}
			}
			else if (option == 5)
			{
				break;
			}
			else cout << "Invalid option\n";
		}
		else if (userMode == 0)
		{
			//menuUser();
			cout << "1.HTML 2.CSV 3.Exit\n";
			HTMLFile shoppingBasket;
			CSVFile shoopingBasketCSV;
			cout << "Option: \n";
			int option;
			cin >> option;
			if (option == 1)
			{
				cout << "HTML\n";
				user_start_html(shoppingBasket);
			}
			else if (option == 2)
			{
				cout << "CSV\n";
				user_start_csv(shoopingBasketCSV);
			}
			else
			{
				break;
			}
			/*
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
		*/
		}
	}
}

void UI::user_start_csv(CSVFile shoppingBasketCSV)
{
	Coat coat;
	while (1)
	{
		cout << "1.Show shopping basket  2.Add to shooping basket 3.Exit\n";
		int choice;
		cin >> choice;
		if (choice == 1)
		{
			std::vector<Coat>basket = service.get_basket();
			for (int i = 0; i < basket.size(); i++)
			{
				//cout << "Size:" << shoppingBasket[i].getSize() << "\n" << "Colour:" << shoppingBasket[i].getColour() << "\n" << "Price:" << shoppingBasket[i].getPrice() << "\n" << "Quantity:" << shoppingBasket[i].getQuantity() << "\n" << "Photograph:" << shoppingBasket[i].getPhotograph() << "\n";
				shoppingBasketCSV.addCoat(basket[i]);
			}
			shoppingBasketCSV.writeCoats();
		}
		else if (choice == 2)
		{
			Validator validator;
			cout << "Size:";
			string size;
			cin >> size;
			try {
				if (validator.check_string(size) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Colour:";
			string colour;
			cin >> colour;
			try {
				if (validator.check_string(colour) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Price:";
			string price;
			cin >> price;
			std::stoi(price);
			try {
				if (validator.check_integer(price) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Quantity:";
			string quantity;
			cin >> quantity;
			std::stoi(quantity);
			try {
				if (validator.check_integer(quantity) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Photograph:";
			string photograph;
			cin >> photograph;
			try {
				if (validator.check_string(photograph) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cin.get();
			if (service.addToShoppingBasket(Coat(size, colour, std::stoi(price), std::stoi(quantity), photograph)) == true)
			{
				cout << "Coat added \n";
			}
			else
			{
				cout << "Coat already exists \n";
			}
		}
		else
		{
			shoppingBasketCSV.writeCoats();
			string fileName = "coat.csv";
			string aux = """ + fileName + """; // if the path contains spaces, we must put it inside quotations
			ShellExecuteA(NULL, NULL, "C:\\Users\\oanam\\source\\repos\\a7-oanag27\\a7-oanag27\\coat.csv", aux.c_str(), NULL, SW_SHOWMAXIMIZED);
			string filePath = "C:\\Users\\oanam\\source\\repos\\a7-oanag27\\a7-oanag27\\coat.csv"; // Replace with the actual file path
			string command = "start excel "" + filePath + """;
			system(command.c_str());
			//std::string topicName = "coat.csv";
			//topicName = "notepad \"" + topicName + "\"";
			//system(topicName.c_str());
			break;
		}
	}
}

void UI::user_start_html(HTMLFile shoppingBasket)
{
	Coat coat;
	while (1)
	{
		cout << "1.Show shopping basket  2.Add to shooping basket 3.Exit\n";
		int choice;
		cin >> choice;
		if (choice == 1)
		{
			std::vector<Coat>basket = service.get_basket();
			for (int i = 0; i < basket.size(); i++)
			{
				//cout << "Size:" << shoppingBasket[i].getSize() << "\n" << "Colour:" << shoppingBasket[i].getColour() << "\n" << "Price:" << shoppingBasket[i].getPrice() << "\n" << "Quantity:" << shoppingBasket[i].getQuantity() << "\n" << "Photograph:" << shoppingBasket[i].getPhotograph() << "\n";
				shoppingBasket.addCoat(basket[i]);
			}
			shoppingBasket.writeCoats();
		}
		else if (choice == 2)
		{
			Validator validator;
			cout << "Size:";
			string size;
			cin >> size;
			try {
				if (validator.check_string(size) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Colour:";
			string colour;
			cin >> colour;
			try {
				if (validator.check_string(colour) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Price:";
			string price;
			cin >> price;
			std::stoi(price);
			try {
				if (validator.check_integer(price) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Quantity:";
			string quantity;
			cin >> quantity;
			std::stoi(quantity);
			try {
				if (validator.check_integer(quantity) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cout << "Photograph:";
			string photograph;
			cin >> photograph;
			try {
				if (validator.check_string(photograph) != true)
				{
					throw Exception("Invalid type\n");
				}
			}
			catch (const Exception& exception) {
				// Handle the exception
				cout << "Exception caught: " << exception.what() << endl;
				return;
			}
			cin.get();
			if(service.addToShoppingBasket(Coat(size,colour,std::stoi(price),std::stoi(quantity),photograph))==true)
			{
				cout << "Coat added \n";
			}
			else
			{
				cout << "Coat already exists \n";
			}
		}
		else
		{
			shoppingBasket.writeCoats();
			std::string filename = "coat.html";

			#ifdef _WIN32
			// For Windows
			std::string command = "start " + filename;
			#else
			// For macOS
			std::string command = "open " + filename;
			#endif
			system(command.c_str());
			break;
		}
	}
}
