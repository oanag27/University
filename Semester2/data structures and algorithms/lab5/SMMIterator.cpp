#include "SMMIterator.h"
#include "SortedMultiMap.h"
/// COMPLEXITY:
///  Overall: T(n) n=number of elements in the map
SMMIterator::SMMIterator(const SortedMultiMap& m) : map(m) {
    stack = new Node * [map.size()];
    for (int i = 0; i < map.size(); i++)
        stack[i] = nullptr;
    first();
}


//BC:O(1)
//WC:O(n)
//AC:O(log n) n = number of nodes in the BST(portion of the left subtree before reaching the leftmost leaf node)
void SMMIterator::first() {

    index = 0;
    Node* current = map.root;

    while (current != nullptr)
    {
        //push current in the stack
        stack[index] = current;
        index++;
        //updates the current variable to the left child of the current node
        current = current->left;
    }
}

//BC:(log n)
//WC:O(n)
//AC:O(log n) n = number of nodes in the BST(portion of the left subtree before reaching the leftmost leaf node)
void SMMIterator::next() {
    if (!valid())
        throw exception();
    //It retrieves the right child of the node at the top of the stack
    Node* current = stack[index - 1]->right;
    index--;
    while (current != nullptr) {
        stack[index] = current;
        index++;
        current = current->left;
    }
}



///  BC=WC=AC: T(1)
bool SMMIterator::valid() const {
    if (index == 0)
        return false;
    return true;
}

///  BC=WC=AC: T(1)
TElem SMMIterator::getCurrent() const {
    if (!valid())
        throw exception();
    Node* current = stack[index - 1];
    return current->info;
}


