#include "Header.h"
#include <iostream>
#include <crtdbg.h>
#include "DynamicVector.h"

using namespace std;

void testDynamicVector()
{
	DynamicVector<int> vect1{};

	DynamicVector<int> vect2{vect1};
}

int main()
{
	/*{
		Song s{ "Pink Floyd", "Comfortably numb", 360 };
		cout << s.getTitle();
	}

	Song* s1 = new Song{ "A1", "T1", 300 };
	delete s1;*/

	testDynamicVector();

	_CrtDumpMemoryLeaks();

	return 0;
}