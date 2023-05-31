#include "Bag.h"

class BagIterator
{
	//DO NOT CHANGE THIS PART
	friend class Bag;
	
private:
	const Bag& bag;
	//TODO  - Representation
	int currentPosition;//current position of the iterator

public:
	// Constructor
	BagIterator(const Bag& b);
	void first();
	void next();
	TElem getCurrent() const;
	bool valid() const;
};
