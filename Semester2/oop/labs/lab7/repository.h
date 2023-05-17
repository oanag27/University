#pragma once
#include <vector>
#include "domain.h"

class Repository
{
private:
	std::vector<Coat> coat_administrator;
	std::vector<Coat> shoppingBasket;
	int totalSum;
	std::string fileName = "coat.txt";
	void readFromFile();
	void writeToFile();
	void writeToFileHTML();
	void writeToFileCSV();
public:
	vector<Coat> get_all_coats();
	vector<Coat> get_all_coats_ui();
	vector<Coat> get_all_coats_by_size(string size);
	bool addCoat(Coat new_coat);
	bool removeCoat(Coat new_coat);
	bool updateCoat(Coat coat, Coat new_coat);
	bool addCoatShoppingBag(Coat new_coat);
	int getTotalPrice();
	Repository() : totalSum{ 0 } {};
	int get_repo_size();
	bool searchForCoat(std::string size, std::string photgraph);
};