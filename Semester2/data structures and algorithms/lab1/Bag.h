#pragma once
//DO NOT INCLUDE BAGITERATOR


//DO NOT CHANGE THIS PART
#define NULL_TELEM -111111;
typedef int TElem;
class BagIterator;
class Bag {

private:
	//TODO - Representation
	//dynamic array data for unique elements
	TElem* array_unique;
	int capacity_array_unique;
	int size_array_unique;
	//dynamic array data for positions
	TElem* array_positions;
	int capacity_aray_positions;
	int size_array_positions;

	//DO NOT CHANGE THIS PART
	friend class BagIterator;
public:
	//constructor
	Bag();

	//adds an element to the bag
	void add(TElem e);

	//removes one occurence of an element from a bag
	//returns true if an element was removed, false otherwise (if e was not part of the bag)
	bool remove(TElem e);

	//checks if an element appearch is the bag
	bool search(TElem e) const;

	//returns the number of occurrences for an element in the bag
	int nrOccurrences(TElem e) const;

	//returns the number of elements from the bag
	int size() const;

	//returns an iterator for this bag
	BagIterator iterator() const;

	//checks if the bag is empty
	bool isEmpty() const;

	//adds an element to the bag the number of occurrences times
	// raise an error if the number of occurrences < 0
	void addElemNumberOfOccurrencesTimes(int number, TElem e);

	//destructor
	~Bag();
};