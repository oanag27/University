#include "domain.h"
#include "service.h"
#include "repository.h"
#include "ui.h"
#include "iostream"

using namespace std;
int main() {
	{
		Repository repository;
		Service service{ repository };
		UI ui{ service };
		ui.runMenu();
	}
	return 0;
}
