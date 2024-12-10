# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
# Press the green button in the gutter to run the script.
from symboltable import SymbolTableClass
from scanner import ScannerClass

if __name__ == '__main__':
    scan = ScannerClass("p2err.txt","token.in")
    scan.display_in_files()
