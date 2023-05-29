#include "service.h"

Service::Service(Repository administrator_repository)
{
    this->administrator_repository = administrator_repository;
    initialiseService();
}

bool Service::addService(Coat new_coat)
{
    return this->administrator_repository.addCoat(new_coat);
}

bool Service::addToShoppingBasket(Coat coat)
{
    return this->administrator_repository.addCoatShoppingBag(coat);
}

bool Service::removeService(Coat new_coat)
{
    return this->administrator_repository.removeCoat(new_coat);
}

void Service::initialiseService()
{
    Coat coat1 = Coat("L", "brown", 1111, 21, "https://google.com/image1.jpg");
    Coat coat2 = Coat("XL", "black", 63, 24, "https://google.com/image2.jpg");
    Coat coat3 = Coat("XS", "red", 134, 78, "https://google.com/image3.jpg");
    Coat coat4 = Coat("XXS", "pink", 77, 90, "https://google.com/image4.jpg");
    Coat coat5 = Coat("L", "yellow", 908, 56, "https://google.com/image5.jpg");
    Coat coat6 = Coat("XXL", "grey", 788, 92, "https://google.com/image6.jpg");
    Coat coat7 = Coat("XS", "green", 1002, 354, "https://google.com/image7.jpg");
    Coat coat8 = Coat("S", "purple", 234, 123, "https://google.com/image8.jpg");
    Coat coat9 = Coat("M", "orange", 74, 0, "https://google.com/image9.jpg");
    Coat coat10 = Coat("L", "white", 100, 10, "https://google.com/image10.jpg");
    this->administrator_repository.addCoat(coat1);
    this->administrator_repository.addCoat(coat2);
    this->administrator_repository.addCoat(coat3);
    this->administrator_repository.addCoat(coat4);
    this->administrator_repository.addCoat(coat5);
    this->administrator_repository.addCoat(coat6);
    this->administrator_repository.addCoat(coat7);
    this->administrator_repository.addCoat(coat8);
    this->administrator_repository.addCoat(coat9);
    this->administrator_repository.addCoat(coat10);
}

bool Service::updateService(Coat coat,Coat new_Coat)
{
    return administrator_repository.updateCoat(coat,new_Coat);
}

vector<Coat> Service::get_all_coats_service()
{
    return administrator_repository.get_all_coats();
}


vector<Coat> Service::get_all_coats_by_size_service(string size)
{
    return administrator_repository.get_all_coats_by_size(size);
}

int Service::getTotalPriceUser()
{
   return administrator_repository.getTotalPrice();
}

