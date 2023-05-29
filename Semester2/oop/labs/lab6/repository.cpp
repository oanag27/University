#include "repository.h"
#include <algorithm>
vector<Coat> Repository::get_all_coats()
{
	return this->coat_administrator;
}

vector<Coat> Repository::get_all_coats_by_size(string size)
{
	if (size == "")
	{
		return coat_administrator;
	}
	std::vector<Coat> new_coat;
	//for (int i = 0; i < this->coat_administrator.size(); i++)
	for(Coat current: coat_administrator)
	{
		if (current.getSize() == size)
		{
			new_coat.push_back(current);
		}
	}
	return new_coat;
}

bool Repository::addCoat(Coat new_coat)
{
	for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == new_coat.getSize() && this->coat_administrator[i].getPhotograph() == new_coat.getPhotograph())
		{
			return false;
		}
	}
	this->coat_administrator.push_back(new_coat);
	return true;
}

bool Repository::removeCoat(Coat new_coat)
{
	int index = -1;
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
	{	return false;}
	else
	{for (auto i = iterator-coat_administrator.begin(); i < this->coat_administrator.size() - 1; i++)
		{this->coat_administrator[i] = this->coat_administrator[i + 1];
		}this->coat_administrator.pop_back();return true;}}

bool Repository::updateCoat(Coat coat, Coat new_coat)
{int index = -1;
	for (int i = 0; i < this->coat_administrator.size(); i++)
	{
		if (this->coat_administrator[i].getSize() == coat.getSize() && this->coat_administrator[i].getPrice() == coat.getPrice())
		{
			this->coat_administrator[i] = new_coat;
			return true;
		}
	}
	return false;}

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
