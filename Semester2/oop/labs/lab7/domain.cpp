#include "domain.h"
#include <iostream>
#include <string>
#include <vector>
#include <sstream>

Coat::Coat(string size, string colour, int price, int quantity, string photograph)
{
    this->size = size;
    this->colour = colour;
    this->price = price;
    this->quantity = quantity;
    this->photograph = photograph;
}
string Coat::getSize() const
{
    return size;
}
string Coat::getColour() const
{
    return colour;
}
int Coat::getPrice() const
{
    return price;
}
int Coat::getQuantity() const
{
    return quantity;
}
string Coat::getPhotograph() const
{
    return photograph;
}
/*
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
*/
std::vector<std::string> tokenize(std::string stringg, char delimiter)
{
    std::vector <std::string> result;
    std::stringstream ss(stringg); //
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);

    return result;
}

std::istream& operator>>(std::istream& inputstream, Coat& coat)
{
    string line;
    getline(inputstream, line);

    vector<string> tokens = tokenize(line, ',');
    if (tokens.size() != 5) // make sure all the coat data was valid
        return inputstream;
    coat.size = tokens[0];
    coat.colour = tokens[1];
    coat.price = stoi(tokens[2]);
    coat.quantity = stoi(tokens[3]);
    coat.photograph = tokens[4];
    return inputstream;
}

std::ostream& operator<<(std::ostream& outputstream, const Coat& coat)
{
    outputstream << coat.size << "," << coat.colour << "," << coat.price << "," << coat.quantity << "," << coat.photograph << "\n";
    return outputstream;
}
