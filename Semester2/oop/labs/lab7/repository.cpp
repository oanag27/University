#include "repository.h"
#include "fileCoat.h"
#include "exception.h"
#include <algorithm>
#include <fstream>
void Repository::readFromFile()
{
	std::ifstream file;
	file.open(this->fileName);
	if (!file.is_open())
		throw Exception("The file could not be opened!");
	Coat coat;
	while (file >> coat)
	{
		this->coat_administrator.push_back(coat);
	}
	file.close();
}
void Repository::writeToFile()
{
	std::ofstream file;
	file.open(this->fileName);
	if (!file.is_open())
		throw Exception("The file could not be opened!");

	for (auto const& coat: this->coat_administrator)
	{
		file << coat;
	}
	file.close();
}
void Repository::writeToFileHTML()
{
	//std::ofstream file;
	//file.open(this->fileNameHtml);
	//if (!file.is_open())
	//	throw Exception("The file could not be opened!");
	HTMLFile htmlFile;
	// Get the coats from the repository
	std::vector<Coat> coats = get_all_coats_ui();
	for (auto const& coat : coats)
	{
		htmlFile.addCoat(coat);
	}
	htmlFile.writeCoats();
	//file.close();
}
void Repository::writeToFileCSV()
{
	//std::ofstream file;
	//file.open(this->fileNameCsv);
	//if (!file.is_open())
	//	throw Exception("The file could not be opened!");
	CSVFile csvFile;
	std::vector<Coat> coats = get_all_coats_ui();
	for (auto const& coat : coats)
	{
		csvFile.addCoat(coat);
	}
	csvFile.writeCoats();
}
vector<Coat> Repository::get_all_coats()
{
	return this->coat_administrator;
}

vector<Coat> Repository::get_all_coats_ui()
{
	//writeToFileHTML();
	return this->shoppingBasket;
}


vector<Coat> Repository::get_all_coats_by_size(string size)
{
	if (size == "")
	{
		return coat_administrator;
	}
	std::vector<Coat> new_coat;
	//for (int i = 0; i < this->coat_administrator.size(); i++)
	for (Coat current : coat_administrator)
	{
		if (current.getSize() == size)
		{
			new_coat.push_back(current);
			writeToFile();
		}
	}
	return new_coat;
}

bool Repository::addCoat(Coat new_coat)
{
	/*for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == new_coat.getSize() && this->coat_administrator[i].getPhotograph() == new_coat.getPhotograph())
		{
			return false;
		}
	}*/
	bool isFound = searchForCoat(new_coat.getSize(), new_coat.getPhotograph());
	if (isFound == true)
	{
		throw Exception("It already exists!\n");
	}
	this->coat_administrator.push_back(new_coat);
	writeToFile();
	return true;
}

bool Repository::removeCoat(Coat new_coat)
{
	int index = -1;
	bool isFound = searchForCoat(new_coat.getSize(), new_coat.getPhotograph());
	if (isFound == false)
	{
		throw Exception("It does not exist!\n");
	}
	auto iterator = std::find_if(coat_administrator.begin(), coat_administrator.end(), [&](Coat coat) {return coat.getSize() == new_coat.getSize() && coat.getPhotograph() == new_coat.getPhotograph(); });
	/*for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == new_coat.getSize() && this->coat_administrator[i].getPhotograph() == new_coat.getPhotograph())
		{
			index = i;
			break;
		}
	}*/
	if (iterator == coat_administrator.end())
	{
		return false;
	}
	else
	{
		for (auto i = iterator - coat_administrator.begin(); i < this->coat_administrator.size() - 1; i++)
		{
			this->coat_administrator[i] = this->coat_administrator[i + 1];
		}this->coat_administrator.pop_back(); writeToFile(); return true;
	}
}

bool Repository::updateCoat(Coat coat, Coat new_coat)
{
	int index = -1;
	bool isFound = searchForCoat(coat.getSize(), coat.getPhotograph());
	if (isFound == false)
	{
		throw Exception("It does not exist!\n");
	}
	for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == coat.getSize() && this->coat_administrator[i].getPrice() == coat.getPrice())
		{
			this->coat_administrator[i] = new_coat;
			writeToFile();
			return true;
		}
	}
	return false;
}

bool Repository::addCoatShoppingBag(Coat new_coat)
{
	for (int i = 0; i < this->shoppingBasket.size(); i++)
	{
		if (this->shoppingBasket[i].getSize() == new_coat.getSize() && this->shoppingBasket[i].getPhotograph() == new_coat.getPhotograph())
		{
			return false;
		}
	}
	this->shoppingBasket.push_back(new_coat);
	writeToFileHTML();
	writeToFileCSV();
	totalSum = totalSum + new_coat.getPrice();
	return true;
}

int Repository::getTotalPrice()
{
	return totalSum;
}

int Repository::get_repo_size()
{
	return this->coat_administrator.size();
}

bool Repository::searchForCoat(std::string size, std::string photograph)
{
	for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == size && this->coat_administrator[i].getPhotograph() == photograph)
		{
			return true;
		}
	}
	return false;
}
