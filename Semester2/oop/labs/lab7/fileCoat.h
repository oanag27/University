#pragma once
#include <vector>
#include <string>
#include <fstream>
#include <stdexcept>
#include "domain.h"
#include "exception.h"
#include <iomanip>

class File {
protected:
    std::vector<Coat> coat;
public:
    virtual std::vector<Coat> getCoats() { return this->coat; }

    virtual void addCoat(const Coat& new_coat) { coat.push_back(new_coat); };

    virtual void removeCoat(int position) { coat.erase(coat.begin() + position); };

    virtual int getSizeOfCoat() const { return coat.size(); };

    virtual void writeCoats() { throw Exception("It has not been implemented!"); }

    virtual ~File() {};
};


class CSVFile : public File {
public:
    /*void writeCoats() override {
        std::ofstream file("coats.csv");	// will append data
        if (!file.is_open())
            return;
        for (auto coat : getCoats()) {
            file << coat;
        }
        file.close();
    }*/
    void writeCoats() override {
        std::ofstream file("coats.csv");
        if (!file.is_open())
            return;

        // Write the table header
        file << "Size,Color,Price,Quantity,Photograph\n";

        // Determine the maximum length of each column for alignment
        size_t maxSize = 0;
        for (const auto& coat : getCoats()) {
            maxSize = std::max(maxSize, coat.getSize().size());
            maxSize = std::max(maxSize, coat.getColour().size());
            // Add similar lines for the other columns (Price, Quantity, Photograph)
        }

        // Write each coat as a row in the table
        for (const auto& coat : getCoats()) {
            file << std::setw(maxSize) << coat.getSize() << ","
                << std::setw(maxSize) << coat.getColour() << ","
                << std::setw(maxSize) << coat.getPrice() << ","
                << std::setw(maxSize) << coat.getQuantity() << ","
                << std::setw(maxSize) << coat.getPhotograph() << "\n";
        }

        file.close();
    }
    CSVFile() { getCoats() = std::vector<Coat>(); }
    CSVFile(CSVFile& file) : File(file) { getCoats() = file.getCoats(); };
    ~CSVFile() {};
    CSVFile& operator=(CSVFile file) {
        if (this == &file)
            return *this;
        getCoats() = file.getCoats();
        return *this;
    }
};

class HTMLFile : public File {
public:
    void writeCoats() override {
        std::ofstream file("coat.html");
        if (!file.is_open())
            return;
        std::string introduction = "<!DOCTYPE html>  \n"
            "<html>\n"
            "<head>\n"
            "    <title>shoppingBasket</title> \n"
            "</head> \n"
            "<body>\n"
            "<table border=\"1\"> \n"
            "    <tr> \n"
            "        <td>Size</td>\n"
            "        <td>Color</td>\n"
            "        <td>Price</td>\n"
            "        <td>Quantity</td>\n"
            "        <td>Photograph</td>\n"
            "    </tr>\n";
        file << introduction;
        for (auto coat : getCoats()) {
            file << "    <tr>\n"
                "<td>"<< coat.getColour() << "</td>\n"
                "<td>" << coat.getSize() << "</td>\n"
                "<td>" << coat.getPrice() << "</td>\n"
                "<td>"<< coat.getQuantity() <<"</td>\n"
                "<td><a href=\""<< coat.getPhotograph() <<"</td>\n"
                "    </tr>\n";
        }
        std::string ending = "</table>\n"
            "</body>\n"
            "</html>";
        file << ending;
        file.close();
    }
    Coat getCoatAtIndex(int index) const {
        if (index >= 0 && index < coat.size()) {
            return coat[index];
        }
        else {
            throw std::out_of_range("Invalid index or empty coats vector.");
        }
    }

    HTMLFile() { getCoats() = std::vector<Coat>(); }

    HTMLFile(HTMLFile& file) : File(file) { getCoats() = file.getCoats(); };

    ~HTMLFile() {};

    HTMLFile& operator=(HTMLFile file) {
        if (this == &file)
            return *this;
        getCoats() = file.getCoats();
        return *this;
    }
};