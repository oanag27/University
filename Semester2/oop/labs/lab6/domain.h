#pragma once
#pragma once
#include <string>
using namespace std;

class Coat {
private:
	string colour;
	string photograph;
	string size;
	int quantity;
	int price;

public:

	Coat(string size, string colour, int price, int quantity, string photograph);

	string getSize();

	string getColour();

	int getPrice();

	int getQuantity();

	string getPhotograph();
};