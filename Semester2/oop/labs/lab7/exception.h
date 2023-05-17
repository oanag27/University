#pragma once
#include <exception>
#include <iostream>
#include <string>

class Exception : public std::exception
{
    std::string message;
public:
    Exception(const std::string& msg) : message(msg) {}

    virtual const char* what() const noexcept override
    {
        return message.c_str();
    }
    
};


class Validator {
public:
    bool check_integer(std::string integer) {
        for (int i = 0; i < strlen(integer.c_str()); i++)
            if (!isdigit(integer.c_str()[i]))
                throw Exception("please introduce integers\n");
        return true;
    }
    bool check_string(std::string word) {
        for (int i = 0; i < strlen(word.c_str()); i++)
        if (!isalpha(word.c_str()[i])) {
            throw Exception("please introduce a string\n");
        }else
            return true; 
    }
};