# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

from grammar import Grammar

def display_menu():
    print("Menu:")
    print("1. Display set of nonterminals")
    print("2. Display set of terminals")
    print("3. Display set of productions")
    print("4. Display productions for a given nonterminal")
    print("5. Check if the grammar is a context-free grammar (CFG)")
    print("6. Exit")

def main():
    grammar = Grammar.read_grammar_from_file("g1.txt")
    while True:
        display_menu()
        choice = input("Choose: ")
        if choice == '1':
            print("Set of non-terminals:",grammar.get_the_set_of_non_terminals())
        elif choice == '2':
            print("Set of terminals:",grammar.get_the_set_of_terminals())
        elif choice == '3':
            print("Set of productions:",grammar.get_the_set_of_productions())
        elif choice == '4':
            nonterminal = input("Enter the nonterminal to get productions for: ")
            productions = grammar.get_the_productions_for_a_given_non_terminal(nonterminal)
            if productions:
                print(f"Productions for {nonterminal}:")
                for prod in productions:
                    print(' | '.join(prod))
            else:
                print(f"No productions found for nonterminal {nonterminal}.")
        elif choice == '5':
            if grammar.check_if_context_free():
                print("The grammar is a context-free grammar (CFG).")
        elif choice == '6':
            break
        else:
            print("Invalid choice. Please try again.")

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
