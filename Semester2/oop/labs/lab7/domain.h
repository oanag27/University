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
	Coat() = default;

	Coat(string size, string colour, int price, int quantity, string photograph);

	string getSize() const;

	string getColour() const;

	int getPrice() const;

	int getQuantity() const;

	string getPhotograph() const;

	///friend-> has access to both private and protected
	friend std::istream& operator>>(std::istream& inputstream, Coat& coat);
	friend std::ostream& operator<<(std::ostream& outputstream, const Coat& coat);
};