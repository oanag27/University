#include <vector>
#include "service.h"

class UI
{
private:
	Service service;
	int userMode;
public:
	UI(Service service) : service{ service }, userMode{ -1 } {};
	void addUi();
	void removeUi();
	void updateUi();
	void addUserUi();
	void get_all_coats_user_ui_and_price();
	void get_all_coats_ui();
	void get_all_coats_user_by_size();
	void shoppingMenu();
	void menuAdmin();
	void menuUser();
	void runMenu();
};