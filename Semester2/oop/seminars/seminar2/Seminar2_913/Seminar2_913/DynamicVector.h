#pragma once

template <typename T>
class DynamicVector
{
private: 
	int capacity, size;
	T* elems;

public:
	DynamicVector(int capacity = 10);
	~DynamicVector();
	DynamicVector(const DynamicVector& v);
};

template<typename T>
inline DynamicVector<T>::DynamicVector(int capacity):
	capacity{capacity}, size{0}
{
	this->elems = new T[this->capacity];
}

template<typename T>
inline DynamicVector<T>::~DynamicVector()
{
	delete[] this->elems;
}
