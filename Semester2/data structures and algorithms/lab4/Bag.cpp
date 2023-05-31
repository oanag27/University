#include "Bag.h"
#include "BagIterator.h"

//BC=AV=WC:O(1)
Bag::Bag() {
    // Initialize the hashtable with a default capacity
    capacity = INITIAL_CAPACITY;
    hashTable.resize(capacity);
    head = -1;
    tail = -1;
    count = 0;
}

//BC:O(1)
//AC:O(N/M) M = total number of elements in the bag
//WC:O(N) N = number of elements in the longest coalesced chain for e
void Bag::add(TElem e) {
    int position = hash(e);

    // Create a new node with the element
    Node newNode;
    newNode.element = e;
    newNode.next = -1;

    // Check if the position is empty
    if (hashTable[position].element == NULL_TELEM) {
        hashTable[position] = newNode; //place the new node at the position

        if (head == -1) {
            head = position;
            tail = position;
        }
        else {
            hashTable[tail].next = position;//update the next pointer of the current tail to the position
            tail = position;
        }
    }
    else {
        // Find an empty position in the hashtable
        int emptyPosition = -1;
        for (int i = 0; i < capacity; i++) {
            if (hashTable[i].element == NULL_TELEM) {
                emptyPosition = i;
                break;
            }
        }
        if (emptyPosition == -1) {
            // hashtable is full, double its capacity
            int newCapacity = capacity * 2;
            // copy elements from the current hashtable to the new hashtable
            std::vector<Node> newHashTable(newCapacity, Node());
            for (int i = 0; i < capacity; i++) {
                newHashTable[i] = hashTable[i];
            }
            hashTable = newHashTable;// update the hashtable to the new larger hashtable
            capacity = newCapacity;
            emptyPosition = capacity / 2;//start searching for a new empty position from the middle
        }

        hashTable[emptyPosition] = newNode; // place the new node at the empty position
        hashTable[tail].next = emptyPosition; // update the next pointer of the current tail to the empty position
        tail = emptyPosition; // update tail to the empty position
    }

    count++;
}


//BC:O(1)
//AC:O(N/M) M = total number of elements in the bag
//WC:O(N) N = number of elements in the longest coalesced chain for e
bool Bag::remove(TElem e) {
    int position = hash(e); //compute hash value for elem e
    if (hashTable[position].element == e) {
        hashTable[position].element = NULL_TELEM; //remove the elem
        // Adjust the next pointers to maintain the integrity of the hashtable
        int current = head;  
        int previous = -1;
        // rtaverse until reaching the element that has to be removed
        while (current != position) {
            previous = current;
            current = hashTable[current].next;
        }
        if (previous != -1)
            hashTable[previous].next = hashTable[current].next;// update the next pointer of the previous element to skip the current element
        else
            //the element being removed is the first element in the chain
            head = hashTable[current].next; //update the head to the next element
        if (position == tail)
            tail = previous;// update the tail to the previous element
        count--;
        return true;
    }
    else {
        
        int current = hashTable[position].next;
        int previous = position;
        while (current != -1) {
            if (hashTable[current].element == e) { //element found
                hashTable[current].element = NULL_TELEM; // remove the element by setting its value to NULL_TELEM
                hashTable[previous].next = hashTable[current].next;// update the next pointer of the previous element to skip the current element
                if (current == tail)
                    tail = previous;// update the tail to the previous element in the chain
                count--;// decrement the count of elements
                return true;
            }
            previous = current;
            current = hashTable[current].next;// update current to the next element 
        }
    }

    return false;
}


// WC: O(N)  N = number of elements in the longest coalesced chain for e
// BC: O(1)
// AC: O(N/M) M = total number of elements in the bag
bool Bag::search(TElem e) const {
    int position = hash(e);

    if (hashTable[position].element == e)
        return true;

    int current = hashTable[position].next;
    while (current != -1) {
        if (hashTable[current].element == e)
            return true;
        current = hashTable[current].next;
    }

    return false;
}

// WC: O(N)  N = number of elements in the longest coalesced chain for e
// BC: O(1)
// AC: O(N/M) M = total number of elements in the bag
int Bag::nrOccurrences(TElem e) const {
    int countOccurrences = 0;

    int position = hash(e);

    if (hashTable[position].element == e)
        countOccurrences++;

    //curent->next value of the element at the computed hash position
    int current = hashTable[position].next;
    while (current != -1) {
        if (hashTable[current].element == e)//end of the coalesced chain
            countOccurrences++;
        current = hashTable[current].next;
    }

    return countOccurrences;
}

//BC=AV=WC:O(capacity)
int Bag::distinctCount() const {
    int distinctElements = 0;

    for (int i = 0; i < capacity; i++) {
        if (hashTable[i].element != NULL_TELEM)//current position contains a valid element
            distinctElements++;
    }

    return distinctElements;
}

//BC=AV=WC:O(1)
int Bag::size() const {
    return count;
}

//BC=AV=WC:O(1)
bool Bag::isEmpty() const {
    return count == 0;
}

//BC=AV=WC:O(1)
BagIterator Bag::iterator() const {
    return BagIterator(*this);
}

////BC=AV=WC:O(1)
Bag::~Bag() {
    // Destructor
}
