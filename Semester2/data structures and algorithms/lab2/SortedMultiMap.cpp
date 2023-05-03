#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
#include<algorithm>
using namespace std;

//Theta(1)
SortedMultiMap::SortedMultiMap(Relation r) {
	//TODO - Implementation
	nrElements = 0;
	head = nullptr; ///{<key, {info, head, tail}>, next, prev}
	tail = nullptr;
	relation = r;
}


// WC:O(n), n = number of elements currently stored in the SortedMultiMap.
// BC:O(log n)
void SortedMultiMap::add(TKey c, TValue v) {
	//TODO - Implementation
    //multimap is empty, create a new key node with the given key-value pair and set it as the head and tail of the multimap
    if (nrElements == 0) {
        auto newValue = new ValueNode{ v, nullptr, nullptr };
        auto dll = pair<ValueNode*, ValueNode*>(newValue, newValue);
        auto info = pair<TKey, pair<ValueNode*, ValueNode*> >(c, dll);
        auto newNode = new KeyNode{ info , nullptr, nullptr };
        head = newNode;
        tail = newNode;
        nrElements++;
        return;
    }
    else {
        //search for the correct position to insert the new key-value pair. Traverse the multimap starting from the head until 
        //either the end of the multimap is reached or a key >= to the given key is found
        nrElements++;
        auto current = head;
        auto prevKey = current->prev;
        while (current != nullptr && relation(current->info.first, c)) {

            if (current->info.first == c) {
                auto valueNode = current->info.second.first;
                auto prevValue = valueNode->prev;
                while (valueNode != nullptr && relation(valueNode->info, v)) {
                    prevValue = valueNode;
                    valueNode = valueNode->next;
                }
                if (valueNode != nullptr && !relation(valueNode->info, v)) {
                    auto newNode = new ValueNode{ v, valueNode->prev, valueNode };
                    if (valueNode->prev != nullptr) {
                        valueNode->prev->next = newNode;
                        valueNode->prev = newNode;
                    }
                    else {
                        current->info.second.first = newNode;
                        valueNode->prev = newNode;
                    }
                    return;
                }
                else {//valueNode = nullptr
                    //key equal to the given key is found, traverse the DLL of 
                    // values belonging to that key to find the correct position to 
                    // insert the new value. 
                    //Traverse the DLL starting from the head until 
                    //either the end of the DLL is reached or a value 
                    //greater than or equal to the given value is found
                    auto newNode = new ValueNode{ v, prevValue, nullptr };
                    prevValue->next = newNode;
                    current->info.second.second = newNode;
                    return;
                }
            }
            prevKey = current;
            current = current->next;
        }
        //If a value equal to the given value is found, do not insert 
        // the new value as it is already present in the DLL.
        //If a value greater than the given value is found, insert the 
        // new value before that value in the DLL.
        //If the end of the DLL is reached, insert the new value at the end of the DLL.
        //If a key greater than the given key is found, insert a new 
        //key node with the given key - value pair before that key node.
        if (current != nullptr && !relation(current->info.first, c)) {
            auto newValue = new ValueNode{ v, nullptr, nullptr };
            auto dll = pair<ValueNode*, ValueNode*>(newValue, newValue);
            auto info = pair<TKey, pair<ValueNode*, ValueNode*> >(c, dll);
            auto newNode = new KeyNode{ info, current->prev, current };
            if (current->prev != nullptr) {
                current->prev->next = newNode;
                current->prev = newNode;
            }
            else {
                head = newNode;
                current->prev = newNode;
            }
        }
        else {
            // multimap is reached, insert a new key node with the given key-value pair at the end of the multimap
            auto newValue = new ValueNode{ v, nullptr, nullptr };
            auto dll = pair<ValueNode*, ValueNode*>(newValue, newValue);
            auto info = pair<TKey, pair<ValueNode*, ValueNode*> >(c, dll);
            auto newNode = new KeyNode{ info, prevKey, nullptr };
            prevKey->next = newNode;
            tail = newNode;
        }
    }
}


//BC=WC=AC= O(number of nodes in the linked list map)
vector<TValue> SortedMultiMap::search(TKey c) const {
	//TODO - Implementation
    auto result = vector<TValue>(); //creates an empty vector 
    auto current = head;            //initializes a pointer to the first node in the linked list
    //iterates through the linked list until the end or until the first node with the key value c is found
    while (current != nullptr && current->info.first != c) {
        current = current->next;
    }
    //checks if the node with the given key value was found
    if (current != nullptr && current->info.first == c) {
        // initializes a pointer to the first node in the linked list of the found node's associated values
        auto node = current->info.second.first;
        //iterates through the linked list of associated values and appends each TValue to the result vector
        while (node != nullptr) {
            result.push_back(node->info);
            node = node->next;
        }
    }
    return result;
}

// WC:O(n), n = number of elements currently stored in the SortedMultiMap.
// BC:O(log n)
bool SortedMultiMap::remove(TKey c, TValue v) {
	//TODO - Implementation
    auto current = head;
    //search for a pair with a key equal to the given key c
    while (current != nullptr && current->info.first != c) {
        current = current->next;
    }
    //If a value node with the given value is found, the function removes it from 
    // the list by updating the previous and next pointers of adjacent nodes, 
    // and then deleting the value node. The function also updates the head and tail 
    // pointers of the list if necessary, 
    //and decreases the count of elements in the map.
    if (current != nullptr && current->info.first == c) {
        auto valueNode = current->info.second.first;
        while (valueNode != nullptr && valueNode->info != v) {
            valueNode = valueNode->next;
        }
        if (valueNode != nullptr) {
            if (valueNode->info == v) {
                if (valueNode->prev != nullptr)
                    valueNode->prev->next = valueNode->next;

                if (valueNode->next != nullptr)
                    valueNode->next->prev = valueNode->prev;

                if (valueNode->next == nullptr)
                    current->info.second.second = valueNode->prev;

                if (valueNode->prev == nullptr)
                    current->info.second.first = valueNode->next;

                delete valueNode;
                nrElements--;
                if (current->info.second.first == nullptr) {
                    if (current->next != nullptr)
                        current->next->prev = current->prev;

                    if (current->prev != nullptr)
                        current->prev->next = current->next;

                    if (current->next == nullptr)
                        tail = current->prev;

                    if (current->prev == nullptr)
                        head = current->next;

                    delete current;
                }
                return true;
            }
        }
    }
    return false;
}

///Theta(1)
int SortedMultiMap::size() const {
	//TODO - Implementation
	return nrElements;
}

/// O(nlogn) n=total number of values in the SortedMultiMap.
/// sort( O(nlogn) )
/// create vector ( O(n) )
int SortedMultiMap::getValueRange() const
{
    if (nrElements == 0) {
        return -1;
    }
    else {
        // create a vector of all the values in the map
        std::vector<TValue> allValues;
        KeyNode* current = head;
        while (current != nullptr) {
            ValueNode* valueNode = current->info.second.first;
            while (valueNode != nullptr) {
                allValues.push_back(valueNode->info); //add
                valueNode = valueNode->next;
            }
            current = current->next;
        }

        // sort the vector of values
        std::sort(allValues.begin(), allValues.end());

        // compute and return the range
        return static_cast<int>(allValues.back()) - static_cast<int>(allValues.front());
    }
}

///Theta(1)
bool SortedMultiMap::isEmpty() const {
	//TODO - Implementation
	return nrElements==0;
}


///Theta(1)
SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}

//WC:O(n*m)
//n = number of distinct keys 
//m = average number of values associated with each key
SortedMultiMap::~SortedMultiMap() {
    auto keyNode = head;
    // function enters a loop that iterates over each key node in the list, 
    // deleting each value node 
    //associated with that key and then deleting the key node itself.
    if (keyNode != nullptr) {
        auto nextKey = keyNode->next;
        while (keyNode != nullptr) {
            //cout << keyNode->info.first << ": ";
            //first value
            auto valueNode = keyNode->info.second.first;
            //next value
            auto nextValue = valueNode->next;
            while (valueNode != nullptr) {
                //cout << valueNode->info << " ";
                delete valueNode;
                valueNode = nextValue;
                if (valueNode != nullptr)
                    nextValue = valueNode->next;
            }
            //After all the value nodes for a key have been deleted, the function 
            // then deletes the key node 
            // and updates the pointers to the current and next key nodes
            delete keyNode;
            keyNode = nextKey;
            if (keyNode != nullptr)
                nextKey = keyNode->next;
        }
    }
    head = nullptr;
    tail = nullptr;
}
