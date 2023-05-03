#pragma once
//DO NOT INCLUDE SMMITERATOR
/// ADT  SortedMultiMap –using  a  DLL  with uniquekeys  ordered  based  on  a  
/// relation  on  the keys. 
/// Every key will be associated with a DLL of the values belonging to that key.

//DO NOT CHANGE THIS PART
#include <vector>
#include <utility>
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TELEM pair<TKey, TValue>(-111111, -111111);
using namespace std;
class SMMIterator;
typedef bool(*Relation)(TKey, TKey);
/// <summary>
/// DLL is used to store the key-value pairs, 
/// with each node in the list representing a unique key
/// keys are unique + ordered based on a specified relation
/// </summary>
struct ValueNode {
    TValue info;
    ValueNode* prev = nullptr;
    ValueNode* next = nullptr;
};


struct KeyNode {
    pair<TKey, pair<ValueNode*, ValueNode*> >info;
    KeyNode* prev = nullptr;
    KeyNode* next = nullptr;
};


class SortedMultiMap {
	friend class SMMIterator;
    private:
		//TODO - Representation
        int nrElements;
        KeyNode* head;
        KeyNode* tail;
        Relation relation;

    public:

    // constructor
    SortedMultiMap(Relation r);

	//adds a new key value pair to the sorted multi map
    void add(TKey c, TValue v);

	//returns the values belonging to a given key
    vector<TValue> search(TKey c) const;

	//removes a key value pair from the sorted multimap
	//returns true if the pair was removed (it was part of the multimap), false if nothing is removed
    bool remove(TKey c, TValue v);

    //returns the number of key-value pairs from the sorted multimap
    int size() const;
    
    //ADT  SortedMultiMap
    //returns the difference between the max and the min value(assume integer val)
    //if the sortedmultimap is empty range=-1
    int getValueRange() const;

    //verifies if the sorted multi map is empty
    bool isEmpty() const;

    // returns an iterator for the sorted multimap. The iterator will returns the pairs as required by the relation (given to the constructor)	
    SMMIterator iterator() const;

    // destructor
    ~SortedMultiMap();
};
