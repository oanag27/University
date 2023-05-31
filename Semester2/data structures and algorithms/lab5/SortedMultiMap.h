#pragma once
//DO NOT INCLUDE SMMITERATOR

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
//11.ADT SortedMultiMap–using  a  BST  with  linked  
// representation  with  dynamic  allocation.  
// In the BST(key, value) pairs are stored. 
// If a key has multiple values, it appears in multiple pairs.

//bst - each node has key+value
//smm - each key has multiple values

struct Node {
    TElem info;// key-value pair stored in the node
    Node* left;//left and right nodes
    Node* right;
};

class SortedMultiMap {
	friend class SMMIterator;
    private:
		//TODO - Representation
        Node* root;// A pointer to the root node of the BST
        int length;//The number of key-value pairs stored in the multimap
        Relation relation;
        //A function pointer representing the relation used to compare keys in the BST. 
        //This allows for customization of the sorting order of the keys

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

    //verifies if the sorted multi map is empty
    bool isEmpty() const;

    // returns an iterator for the sorted multimap. The iterator will returns the pairs as required by the relation (given to the constructor)	
    SMMIterator iterator() const;

    //Transformer is a function which takes as param a key and a value
    //returns a new value for that key
    typedef TValue(*Transformer)(TKey, TValue);
    //replace every value of every key, with the result given by the function
    //invoked on the pair
    void replaceAll(Transformer t);

    void replaceAllGuide(Node* node, Transformer t);
    // destructor
    ~SortedMultiMap();

    // removes a key together with all its values
    // returns a vector with the values that were previously associated to this value (and were removed)
    vector<TValue> removeKey(TKey key);

private:
    //helper function to insert a key-value pair into the BST
    Node* insert(Node* node, TElem e);
    
    //Node* getMinimumKey(Node* current);

    //helper function to recursively remove a key
    bool removeRec(Node* node, TKey c, TValue v);

    void destructor(Node* pNode);
};
