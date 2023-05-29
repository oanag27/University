#include "domain.h"

Coat::Coat(string size, string colour, int price, int quantity, string photograph)
{
	this->size = size;
	this->colour = colour;
	this->price = price;
	this->quantity = quantity;
	this->photograph = photograph;
}

string Coat::getSize()
{
	return this->size;
}

string Coat::getColour()
{
	return this->colour;
}

int Coat::getPrice()
{
	return this->price;
}

int Coat::getQuantity()
{
	return this->quantity;
}

string Coat::getPhotograph()
{
	return this->photograph;
}

