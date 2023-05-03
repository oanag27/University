#include "MultiMapIterator.h"
#include "MultiMap.h"

// Complexity: Theta(1)
MultiMapIterator::MultiMapIterator(const MultiMap& c):col(c) {
	this->currentKey = this->col.head;
	this->currentElement = -1;
	if (this->currentKey != -1)
		this->currentElement = this->col.map[this->currentKey].head;
}

// Complexity: Theta(1)
TElem MultiMapIterator::getCurrent() const {
	//check validity
	if (this->currentKey == this->col.firstEmpty || this->currentKey == -1)
		throw exception("Element doesn't exist.\n");
	TElem element;
	//first field of the TElem struct to the key of the current node ->stored in the key field
	element.first = this->col.map[this->currentKey].key;
	//second field of the TElem struct to the value of the current node ->stored in infoSLLA array
	element.second = this->col.map[this->currentKey].infoSLLA[this->currentElement];

	return element;
}

// Complexity: Theta(1)
bool MultiMapIterator::valid() const {
	if (this->currentKey != this->col.firstEmpty && this->currentKey != -1)
		return true;

	return false;
}

// Complexity: Theta(1)
void MultiMapIterator::next() {
	//checks if the current key is the last key 
	if (this->currentKey == this->col.firstEmpty || this->currentKey == -1)
		throw exception("Exception\n");

	if (this->col.map[this->currentKey].next[this->currentElement] != this->col.map[this->currentKey].firstEmpty)
		this->currentElement = this->col.map[this->currentKey].next[this->currentElement];
	else
	{
		this->currentKey = this->col.next[this->currentKey];
		if (this->currentKey != this->col.firstEmpty)
		{
			//If there is a next key, the iterator is positioned at the first element of the DLL for that key
			this->currentElement = this->col.map[this->currentKey].head;
		}
		else
			//invalid state
			this->currentElement = -1;
	}
}

// Complexity: Theta(1)
void MultiMapIterator::first() {
	this->currentKey = this->col.head;
	this->currentElement = -1;
	if (this->currentKey != -1)
		this->currentElement = this->col.map[this->currentKey].head;
}
