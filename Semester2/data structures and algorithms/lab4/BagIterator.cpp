#include "BagIterator.h"

// Constructor
BagIterator::BagIterator(const Bag& bag) : bag(bag), currentPosition(0) {}

// WC:O(N) N = number of empty elements at the beginning of the bag
// AC:O(N/M) M = total number of elements in the bag
// BC:O(1)
// Moves the iterator to the first position
void BagIterator::first() {
    currentPosition = 0;
    //iteration continues until the currentPosition reaches the end of the bag's hashtable 
    //or until a non-empty element is found 
    while (currentPosition < bag.capacity && bag.hashTable[currentPosition].element == NULL_TELEM) {
        currentPosition++;
    }
}

// Advances the iterator to the next position
//BC:O(1)
//WC:O(N)  N = number of empty elements after the current position
//AC:O(N/M) M = total number of elements in the bag
void BagIterator::next() {
    if (!valid())
        throw std::exception();
    currentPosition++;
    while (currentPosition < bag.capacity && bag.hashTable[currentPosition].element == NULL_TELEM) {
        currentPosition++;
    }
}

// Checks if the iterator is valid
//BC=AV=WC:O(1)
bool BagIterator::valid() const {
    return currentPosition < bag.capacity && bag.hashTable[currentPosition].element != NULL_TELEM;
}

// Returns the current element pointed by the iterator
//BC=AV=WC:O(1)
TElem BagIterator::getCurrent() const {
    if(!valid())
        throw std::exception();
    return bag.hashTable[currentPosition].element;
}
