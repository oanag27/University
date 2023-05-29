#pragma once
#include"repository.h"
#include<vector>

class Service {
private:
	Repository administrator_repository;
public:
	Service(Repository administrator_repository);
	bool addService(Coat new_coat);
	bool addToShoppingBasket(Coat coat);
	bool removeService(Coat new_coat);
	void initialiseService();
	bool updateService(Coat coat,Coat new_coat);
	vector<Coat> get_all_coats_service();
	vector<Coat> get_all_coats_by_size_service(string size);
	int getTotalPriceUser();
};