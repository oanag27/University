#include "domain.h"
#include "service.h"
#include "repository.h"
#include "ui.h"
#include "iostream"
#include "tests.h"

using namespace std;
int main() {
	testDomain tests_domain{};
	tests_domain.testAllDomain();
	tests tests_s{};
	tests_s.testAll();
	{
	Repository repository;
	Service service{ repository };
	UI ui{ service };
	ui.runMenu();
	}
	return 0;
}