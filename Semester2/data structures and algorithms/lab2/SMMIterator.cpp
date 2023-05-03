#include "SMMIterator.h"
#include "SortedMultiMap.h"
///Theta(1)
SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d){
	//TODO - Implementation
	keyNode = d.head;
	if (keyNode != nullptr)
		valueNode = keyNode->info.second.first;
	else
		valueNode = nullptr;
}

///Theta(1)
void SMMIterator::first(){
	//TODO - Implementation
	//add the head ( the top of the map )
	keyNode = map.head;
	if (keyNode != nullptr)
		valueNode = keyNode->info.second.first;
	else
		valueNode = nullptr;
}


///Theta(1)
void SMMIterator::next(){
	//TODO - Implementation
	if (keyNode == nullptr)
		throw exception();
	valueNode = valueNode->next;
	if (valueNode == nullptr) {
		keyNode = keyNode->next;
		if (keyNode != nullptr)
			valueNode = keyNode->info.second.first;
	}
}


///Theta(1)
bool SMMIterator::valid() const{
	//TODO - Implementation
	// we have value and key
	if (valueNode != nullptr && keyNode != nullptr)
		return true;
	return false;
}


//Theta(1)
TElem SMMIterator::getCurrent() const{
	//TODO - Implementation
	if (valid()) {
		TElem p(keyNode->info.first, valueNode->info);
		return p;
	}
	throw exception();
}


