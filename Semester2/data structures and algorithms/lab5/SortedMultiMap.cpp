#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

//BC=WC=AC: O(1)
SortedMultiMap::SortedMultiMap(Relation r) {
	//TODO - Implementation
	this->length = 0;
	this->root = nullptr;
	this->relation = r;
}

//complexity depends on the complexity of the insert function
//BC:O(1)
//AC:O(log N), as each insertion divides the search space in half
//WC:O(N) N=number of nodes in the BST
void SortedMultiMap::add(TKey c, TValue v) {
	//TODO - Implementation
    //Create a new TElem object el with the specified key c and value v
	TElem el{ c,v }; 
    //Call the insert helper function to insert the new key-value pair into the BST
	this->root = this->insert(this->root, el);
}

//BC:O(1)
//AC:O(log N), as each insertion divides the search space in half
//WC:O(N) N=number of nodes in the BST
vector<TValue> SortedMultiMap::search(TKey c) const {
	//TODO - Implementation
	vector<TValue> tmp;//create empty vector
	Node* node = this->root;//initialize pointer node to the root

	while (node != nullptr) {
		if (c == node->info.first) {//c==current node
			tmp.push_back(node->info.second);//add value to the vector
		}
        // If the relation between the current node's key 
        // and the given key is false, 
        //it means the desired key is smaller
		if (!relation(node->info.first, c))
		{
			node = node->left;
		}
		else {
			node = node->right;
		}
	}
    return tmp;
}

//BC:O(1)
//AC:O(log N), as each insertion divides the search space in half
//WC:O(N) N=number of nodes in the BST
bool SortedMultiMap::remove(TKey c, TValue v) {
	//TODO - Implementation
	if (this->removeRec(root, c, v))
	{
		this->length--;
		return true;
	}
	return false;
}

//BC=AC=WC:O(1)
int SortedMultiMap::size() const {
	//TODO - Implementation
	return this->length;
}

//BC=AC=WC:O(1)
bool SortedMultiMap::isEmpty() const {
	//TODO - Implementation
	if (this->length == 0)
		return true;
	return false;
}

//BC=AC=WC:O(1)
SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}

//BC=AC=WC:O(n) n = number of nodes in the BST
void SortedMultiMap::replaceAll(Transformer t) {
    replaceAllGuide(root, t);
}

//performs the recursive traversal of the BST and 
//applies the second replaceAllGuide function to each value
void SortedMultiMap::replaceAllGuide(Node* node, Transformer t) {
    if (node != nullptr) {
        // Apply to the value of the current node
        node->info.second = t(node->info.first, node->info.second);

        //call the function for the left and right subtrees
        replaceAllGuide(node->left, t);
        replaceAllGuide(node->right, t);
    }
}


/// COMPLEXITY:
///  Overall: O(n) n=number of nodes in the BST
SortedMultiMap::~SortedMultiMap() {
	//TODO - Implementation
	Node* node = this->root;
	this->destructor(node);
}

/// COMPLEXITY:
///  Overall: T(n)
void SortedMultiMap::destructor(Node* node) {
	if (node != nullptr) {
		this->destructor(node->right);
		this->destructor(node->left);
		delete node;
	}
}

/// COMPLEXITY:
///  Overall: O(n^2)
vector<TValue> SortedMultiMap::removeKey(TKey key) {
	vector<TValue> values = this->search(key);

	for (int value : values)
	{
		remove(key, value);
	}
	return values;
}

/// COMPLEXITY:
///  Overall: O(n)
Node* getMinimumKey(Node* current)
{
    while (current->left != nullptr)
    {
        current = current->left;
    }
    return current;
}

//BC:O(log n)
//WC:O(n)  n = number of nodes in the BST
//AC:O(log n)
Node* SortedMultiMap::insert(Node* node, TElem e)
{
	if (node == nullptr)
	{
        //create a new node and assign it the elem e
		node = new Node();
		node->info = e;
		node->left = nullptr;
		node->right = nullptr;
		this->length++;
	}
	else if (!relation(node->info.first, e.first))
        // recursively insert the element into the left subtree of the current node
		node->left = insert(node->left, e);
	else
        // recursively insert the element into the right subtree of the current node
		node->right = insert(node->right, e);
	return node;
}


//WC: O(n), n=number of nodes in the tree
//BC:O(1) -> remove the elem without traversing the tree
//AC:O(log n)
bool SortedMultiMap::removeRec(Node* node, TKey c, TValue v)
{
    Node* lastNode = nullptr;

    while (node != nullptr)
    {
        if (c == node->info.first && v == node->info.second)
        {
            // if the current node has no children
            if (node->left == nullptr && node->right == nullptr)
            {
                // if the node is a leaf node, but it's not the root
                if (node != this->root)
                {
                    if (lastNode->left == node)
                    {
                        lastNode->left = nullptr;
                    }
                    // if it's the root, we set the tree as empty
                    else
                    {
                        lastNode->right = nullptr;
                    }
                }
                else
                {
                    this->root = nullptr;
                }
            }
            // if the node has two children
            else if (node->left && node->right)
            {
                Node* successor = getMinimumKey(node->right);
                TElem val = successor->info;
                this->removeRec(node, successor->info.first, successor->info.second);
                node->info = val;
            }
            // if the node has only want child
            else
            {
                Node* child = (node->left) ? node->left : node->right;
                // if the node is not the root
                if (node != root)
                {
                    if (node == lastNode->left)
                    {
                        lastNode->left = child;
                    }
                    else
                    {
                        lastNode->right = child;
                    }
                }
                else
                {
                    root = child;
                }
            }
            return true;
        }
        if (!relation(node->info.first, c))
        {
            lastNode = node;
            node = node->left;
        }
        else
        {
            lastNode = node;
            node = node->right;
        }
    }
    return false;
}

