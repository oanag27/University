from symboltable import SymbolTableClass
import re
class ScannerClass:
    def __init__(self, file_name, file_token):
        self.file_name = file_name
        self.file_token = file_token
        self.words_token_file = []
        self.separators_token_file = []
        self.operators_token_file = []
        self.get_token_info_from_token_file()
        self.sym_table = SymbolTableClass(200)
        self.pif = []

    def get_token_info_from_token_file(self):
        part = None
        with open(self.file_token, 'r') as f:
            for l in f:
                l = l.strip()
                if l == "operators":
                    part = "operators"
                    continue
                elif l == "separators":
                    part = "separators"
                    continue
                if part == "separators":
                    self.separators_token_file.append(l)
                    if l == "_":
                        part = "words"
                if part == "operators" and l:
                    self.operators_token_file.append(l)
                elif part == "words" and l:
                    if l != "_":
                        self.words_token_file.append(l)

    def check_for_valid_identifier(self, identifier):
        not_ok_words = ["innt", "innput", "printt","retturn"]
        if len(identifier) == 0:
            return False
        if identifier in not_ok_words:
            return False
        if not identifier[0].isalpha(): # starts with letter
            return False
        for character in identifier[1:]: # either a letter or a digit.
            if not (character.isalnum()):
                return False
        return True

    def check_for_valid_constants(self, constant):
        if constant == "0":
            return True
        if len(constant) > 1 and constant[0] == '0':
            return False
        if constant[0] == '-' and len(constant) > 1:
            constant = constant[1:]
        if constant[0] == '+' and len(constant) > 1:
            constant = constant[1:]
        for char in constant:
            if not char.isdigit():
                return False
        return True

    def is_operator(self,token):
        return token in self.operators_token_file

    def is_separator(self,token):
        return token in self.separators_token_file

    def display_in_files(self):
        lexical_errors = []
        self.line_number = 0
        with open(self.file_name,'r') as f:
                self.character_count = 0
                for line in f:
                    self.line_number += 1
                    line = line.strip()
                    # split on whitespace
                    tokens = re.findall(r'[\w]+|[^\s\w]',line)
                    for token in tokens:
                        token = token.strip()
                        if self.is_operator(token):
                            self.pif.append((token,"nil"))
                        elif self.is_separator(token):
                            self.pif.append((token,"nil"))
                        elif token in self.words_token_file:
                            self.pif.append((token,"nil"))
                        elif self.check_for_valid_identifier(token):
                            self.sym_table.addHashValue(token)
                            self.pif.append(("identifier", token, self.character_count))
                        elif self.check_for_valid_constants(token):
                            self.sym_table.addHashValue(token)
                            self.pif.append(("constant", token, self.character_count))
                        else:
                            lexical_errors.append(f"Lexical error at line {self.line_number}: {token}")
                        self.character_count+=1
        with open("pif.out",'w') as pif:
            for i in self.pif:
                pif.write(f"{i}\n")
        with open("st.out",'w') as st:
            st.write(str(self.sym_table))
        if lexical_errors:
            for error in lexical_errors:
                print(error)
            print("Lexical analysis contains errors.")
        else:
            print("Lexically correct.")
