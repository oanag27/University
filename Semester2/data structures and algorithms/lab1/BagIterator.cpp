#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;

//Complexity: 
//Best case: theta(1)
//Average case: theta(1)
//Worst case: theta(1)
BagIterator::BagIterator(const Bag& c) : bag(c)
{
	//TODO - Implementation
	this->index = 0;
}

//Complexity: 
//Best case: theta(1)
//Average case: theta(1)
//Worst case: theta(1)
void BagIterator::first() {
	//TODO - Implementation
	this->index = 0;
}

//Complexity: 
//Best case: theta(1)
//Average case: theta(1)
//Worst case: theta(1)
void BagIterator::next() {
	//TODO - Implementation
	if (this->index >= this->bag.size())
		throw std::exception();

	this->index++;
}

//Complexity: 
//Best case: theta(1)
//Average case: theta(1)
//Worst case: theta(1)
bool BagIterator::valid() const {
	//TODO - Implementation
	return (this->index < this->bag.size());
}



TElem BagIterator::getCurrent() const
{
	//TODO - Implementation
	if (this->valid() == false)
		throw std::exception();
	return this->bag.array_unique[this->bag.array_positions[this->index]];
}
