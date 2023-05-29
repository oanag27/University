#include <cassert>
#include <iostream>
#include "domain.h"
#include "service.h"
#include "repository.h"
#include "service.h"
#include "tests.h"
#include <vector>

using namespace std;


void tests::test_add()
{
	Repository repository{};
	Service service{ repository };
	bool coat = service.addService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(coat==1);
	bool coat2 = service.addService(Coat("LL","b",1,2,"https://google.com/image11.jpg"));
	assert(coat2 == 0);
	
}

void tests::test_remove()
{
	Repository repository{};
	Service service{ repository };
	bool coat = service.addService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(coat == 1);
	bool new_coat = service.removeService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(new_coat == 1);
	bool new_coat2 = service.removeService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(new_coat2 == 0);
}

void tests::test_update()
{
	Repository repository{};
	Service service{ repository };
	bool coat = service.addService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(coat==1);
	bool new_coat = service.updateService(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"),Coat("LX", "a", 2, 3, "https://google.com/image22.jpg"));
	assert(new_coat == 1);
	bool new_coat2 = service.updateService(Coat("a", "c", 3, 4, "https://google.com/image0.jpg"), Coat("LX", "a", 2, 3, "https://google.com/image22.jpg"));
	assert(new_coat2 == 0);

}

void tests::test_addToShoppingBag()
{
	Repository repository{};
	Service service{ repository };
	bool coat = service.addToShoppingBasket(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(coat == 1);
	bool coat2 = service.addToShoppingBasket(Coat("LL", "b", 1, 2, "https://google.com/image11.jpg"));
	assert(coat2 == 0);
}

void tests::testTotalPrice()
{
	Coat c1("a", "b", 2, 1, "http");
	Coat c2("ee", "b", 2, 1, "asdfg");
	Repository repo;
	repo.addCoat(c1);
	repo.addCoat(c2);
	repo.addCoatShoppingBag(c1);
	repo.addCoatShoppingBag(c2);
	assert(repo.getTotalPrice() == 4);
	Service serv1{ repo };
	assert(serv1.getTotalPriceUser() == 4);
}


void tests::testAll()
{
	test_add();
	test_remove();
	test_update();
	test_addToShoppingBag();
	test_all_coats();
	test_all_coats_by_size();
	testTotalPrice();
	testRepoSize();
}

void tests::test_all_coats()
{
	Repository repository{};
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	repository.addCoat(coat);
	std::vector<Coat> new_coat = repository.get_all_coats();
	assert(new_coat[0].getColour() == "Blue");
	assert(new_coat[0].getSize() == "S");
	assert(new_coat[0].getPrice() == 20);
	assert(new_coat[0].getQuantity() == 1);
	assert(new_coat[0].getPhotograph() == "https://google.com/image1.jpg");
}

void tests::testRepoSize()
{
	Repository repo1;
	assert(repo1.get_repo_size() == 0);
}

void tests::test_all_coats_by_size()
{
	Repository repository{};
	Service serv1{ repository };
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	repository.addCoat(coat);
	std::vector<Coat> new_coat = repository.get_all_coats_by_size("S");
	assert(new_coat[0].getColour() == "Blue");
	assert(new_coat[0].getSize() == "S");
	assert(new_coat[0].getPrice() == 20);
	assert(new_coat[0].getQuantity() == 1);
	assert(new_coat[0].getPhotograph() == "https://google.com/image1.jpg");
	for (Coat current : serv1.get_all_coats_by_size_service("S"))
		assert(current.getSize() == "S");
}


void testDomain::testGetSize()
{
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	assert(coat.getSize() == "S");
}

void testDomain::testGetColour()
{
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	assert(coat.getColour() == "Blue");
}

void testDomain::testGetPrice()
{
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	assert(coat.getPrice() == 20);
}

void testDomain::testGetQuantity()
{
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	assert(coat.getQuantity() == 1);
}

void testDomain::testGetPhotograph()
{
	Coat coat("S", "Blue", 20, 1, "https://google.com/image1.jpg");
	assert(coat.getPhotograph() == "https://google.com/image1.jpg");
}

void testDomain::testAllDomain()
{
	testGetColour();
	testGetPhotograph();
	testGetPrice();
	testGetQuantity();
	testGetSize();
	
}