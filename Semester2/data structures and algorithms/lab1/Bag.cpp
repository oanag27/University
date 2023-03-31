#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;

/// Complexity: theta(1)
Bag::Bag() {
	//TODO - Implementation
	this->capacity_array_unique = 1;
	this->size_array_unique = 0;
	this->array_unique = new int[1];

	this->capacity_aray_positions = 1;
	this->size_array_positions = 0;
	this->array_positions = new int[1];
}



Bag::Bag(Bag& c)
{
	this->array_unique = new int[c.capacity_array_unique];
	this->array_positions = new int[c.capacity_aray_positions];

	this->capacity_aray_positions = c.capacity_aray_positions;
	this->capacity_array_unique = c.capacity_array_unique;

	this->size_array_unique = c.size_array_unique;
	this->size_array_positions = c.size_array_positions;

	for (int i = 0; i < this->size_array_unique; i++)
		this->array_unique[i] = c.array_unique[i];

	for (int i = 0; i < this->size_array_positions; i++)
		this->array_positions[i] = c.array_positions[i];
}


//Complexity:
//Best case: theta(number of elements)
//Average case: theta(number of distinct elements)
//Worst case: theta(number of elements + number of distinct elements)
void Bag::add(TElem elem) {
	//TODO - Implementation
	int index = -1, i;

	//find the index of the elem
	for (i = 0; i < this->size_array_unique; i++)
	{
		if (this->array_unique[i] == elem)
		{
			index = i;
			break;
		}
	}

	//we did not find the elem
	if (index == -1) {
		if (this->size_array_unique == this->capacity_array_unique) {
			TElem* new_elem = new int[this->capacity_array_unique * 2];
			for (int i = 0; i < this->size_array_unique; i++) {
				new_elem[i] = this->array_unique[i];
			}
			delete[] this->array_unique;
			this->array_unique = new_elem;
			this->capacity_array_unique *= 2;
		}
		this->array_unique[this->size_array_unique++] = elem;
		index = this->size_array_unique - 1;
	}

	//we found the elem
	if (this->size_array_positions == this->capacity_aray_positions) {
		TElem* new_elem = new int[this->capacity_aray_positions * 2];
		for (int i = 0; i < this->size_array_positions; i++) {
			new_elem[i] = array_positions[i];
		}
		delete[] this->array_positions;
		this->array_positions = new_elem;
		this->capacity_aray_positions *= 2;
	}
	this->array_positions[this->size_array_positions++] = index;

}



//Complexity:
//Best case: theta(number of elements)
//Average case: theta(number of distinct elements)
//Worst case: theta(number of elements + number of distinct elements)
bool Bag::remove(TElem elem) {
	//TODO - Implementation
	int firstIndex = -1;
	int lastIndex = -1;
	int i;
	for (i = 0; i < this->size_array_positions; i++)
	{
		if (this->array_unique[this->array_positions[i]] == elem)
		{
			if (firstIndex == -1)
			{
				firstIndex = i;
			}
			lastIndex = i;
		}
	}

	//we did not find the elem
	if (lastIndex == -1) {
		return false;
	}

	for (i = lastIndex; i + 1 < this->size_array_positions; i++)
	{
		this->array_positions[i] = this->array_positions[i + 1];

	}
	this->size_array_positions--;

	//the elem does not appear
	/*int new_index_unique=-1,j;
	if (firstIndex == lastIndex)
	{
		new_index_unique = -1;
		for (i = 0; i < this->size_array_unique; i++)
		{
			if (this->array_unique[i] == elem)
			{
				new_index_unique = i;
			}
		}
	}*/
	/*int new_index_unique = 1,j;
	for (j = new_index_unique; j + 1 < this->size_array_unique; j++)
	{
		this->array_unique[j] = this->array_unique[j + 1];
	}
	this->size_array_unique--;

	for (j = 0; j < this->size_array_positions; j++)
	{
		if (this->array_positions[j] == new_index_unique)
		{
			this->array_positions[j]--;
		}
	}*/

	return true;
}


//Complexity:
//Best case: theta(number of elements)
//Average case: theta(number of elements)
//Worst case: theta(number of elements)
bool Bag::search(TElem elem) const {
	//TODO - Implementation
	for (int i = 0; i < this->size_array_positions; i++)
	{
		if (this->array_unique[this->array_positions[i]] == elem)
		{
			return true;
		}
	}
	return false;
}


/// Complexity:
/// Best case:theta(size_array_positions^2)
/// Average case:theta(size_array_positions^2)
/// Worst case:theta(size_array_positions^2)
int Bag::removeElementsWithMultipleOccurences()
{
	int number = 0,i,j,index=-1,k;
	for (i = 0; i < this->size_array_positions; i++)
	{
		for (j = 0; j < this->size_array_positions; j++)
		{
			if (this->array_positions[i] == this->array_positions[j])
			{
				index = i;
				remove(this->array_positions[j]);
				this->size_array_positions--;
				j--;
			}
			if (index != -1)
			{
				remove(this->array_positions[i]);
				remove(this->array_unique[index]);
				number++;
			}
		}
	}
	return number;
}

int Bag::nrOccurrences(TElem elem) const {
	//TODO - Implementation
	int number = 0;
	int i;
	for (i = 0; i < this->size_array_positions; i++)
	{
		if (this->array_unique[this->array_positions[i]] == elem)
		{
			number++;
		}
	}
	return number;
}


int Bag::size() const {
	//TODO - Implementation
	return this->size_array_positions;
}


bool Bag::isEmpty() const {
	//TODO - Implementation
	return (this->size_array_positions == 0);
}

BagIterator Bag::iterator() const {
	return BagIterator(*this);
}

//Complexity:
//Best case: theta(number)
//Average case: theta(number of unique elements + number)
//Worst case: theta(number of unique elements + number + number of elements)
void Bag::addElemNumberOfOccurrencesTimes(int number, TElem elem)
{
	if (number < 0)
	{
		throw std::exception();
	}

	int index = -1;
	int i = 0;
	for (i = 0; i < this->size_array_unique; i++)
	{
		if (this->array_unique[i] == elem)
		{
			index = i;
			break;
		}
	}

	if (index == -1) {
		if (this->size_array_unique == this->capacity_array_unique) {
			TElem* new_elem = new int[this->capacity_array_unique * 2];
			for (int i = 0; i < this->size_array_unique; i++) {
				new_elem[i] = this->array_unique[i];
			}
			delete[] this->array_unique;
			this->array_unique = new_elem;
			this->capacity_array_unique *= 2;
		}
		this->array_unique[this->size_array_unique++] = elem;
		index = this->size_array_unique - 1;
	}

	for (i = 0; i < number; i++)
	{
		if (this->size_array_positions == this->capacity_aray_positions) {
			TElem* new_elem = new int[this->capacity_aray_positions * 2];
			for (int i = 0; i < this->size_array_positions; i++) {
				new_elem[i] = array_positions[i];
			}
			delete[] this->array_positions;
			this->array_positions = new_elem;
			this->capacity_aray_positions *= 2;
		}
		this->array_positions[this->size_array_positions++] = index;
	}
}

Bag::~Bag() {
	//TODO - Implementation
	delete[] this->array_positions;
	delete[] this->array_unique;
}

