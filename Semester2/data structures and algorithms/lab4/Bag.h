#pragma once
// DO NOT INCLUDE BAGITERATOR

// DO NOT CHANGE THIS PART
#define NULL_TELEM -111111
#define INITIAL_CAPACITY 10 // Initial capacity of the hashtable

#include <vector>
typedef int TElem;
//5.ADT Bag–using a hashtable with coalesced chaining in which the elements 
//are stored. If an element appears multiple times,
//it will be stored multiple times in the hashtable

//hashtable->array of buckets
//coalesced chaining->each bucket stores a data structure that allows chaining elems
//that hash to the same index

class BagIterator;

class Bag {
private:
    // TODO - Representation
    // DO NOT CHANGE THIS PART
    friend class BagIterator;
    // Hashtable node structure
    struct Node {
        TElem element;
        int next; // index of the next node in the chain

        Node() : element(NULL_TELEM), next(-1) {}
    };

    // Hashtable representation
    std::vector<Node> hashTable;
    int capacity;//current capacity of the hashtable (the number of buckets)
    int head;//first node
    int tail;//last node
    int count;//number of elements stored in the bag

    // Helper function to compute the hash value for an element
    int hash(TElem e) const {
        return std::abs(e) % capacity;
    }


public:
    // Constructor
    Bag();

    // Adds an element to the bag
    void add(TElem e);

    // Removes one occurrence of an element from the bag
    // Returns true if an element was removed, false otherwise (if e was not part of the bag)
    bool remove(TElem e);

    // Checks if an element appears in the bag
    bool search(TElem e) const;

    // Returns the number of occurrences for an element in the bag
    int nrOccurrences(TElem e) const;

    // Returns the number of distinct elements in the bag
    int distinctCount() const;

    // Returns the number of elements in the bag
    int size() const;

    // Returns an iterator for this bag
    BagIterator iterator() const;

    // Checks if the bag is empty
    bool isEmpty() const;

    // Destructor
    ~Bag();
};
