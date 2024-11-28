class Grammar:
    def __init__(self, N, E, S, P):
        self.N = N #non-terminal symbols
        self.E = E #terminal symbols
        self.S = S #start symbol
        self.P = P #productions

    def read_grammar_from_file(filename):
        with open(filename,'r',encoding='utf-8') as file:
            # Extract N,E,S,P
            N = set(file.readline().split('=',maxsplit=1)[1].strip().split())
            E = set(file.readline().split('=',maxsplit=1)[1].strip().split())
            S = file.readline().split('=',maxsplit=1)[1].strip()
            file.readline()  # Skip empty line
            P = Grammar.parse_productions([line.strip() for line in file])
            # Validate the grammar
            if not Grammar.validate_grammar(N,E,S,P):
                return f"Grammar in {filename} is not valid"

            return Grammar(N,E,S,P)

    def parse_productions(lines):
        P = {}
        for line in lines:
            if line == '':
                continue
            # Split lhs and rhs at the '->' symbol
            lhs, rhs = line.split('->')
            lhs = lhs.strip()
            rhs_list = rhs.strip().split('|')
            # Check if lhs is already a key in the dictionary
            if lhs in P:
                P[lhs].append(rhs_list)
            else:
                P[lhs] = [rhs_list]
        return P

    def get_the_set_of_non_terminals(self):
        return self.N

    def get_the_set_of_terminals(self):
        return self.E

    def get_the_set_of_productions(self):
        return self.P

    def get_the_productions_for_a_given_non_terminal(self,nonterminal):
        # Check if the nonterminal exists in the grammar's nonterminals
        return self.P.get(nonterminal,[])

    def validate_grammar(N, E, S, P):
        #validate S
        if S not in N:
            return False

        for k,v in P.items():
            # For each left-hand side of production, ensure it is a valid non-terminal
            if k not in N:
                return False
            # Check the right-hand side of the production
            for production in v:
                for symbol in production:
                    symbol = symbol.strip().split()  # Split production by whitespace
                    for s in symbol:
                        # Check symbol is non-terminal or a terminal
                        if s not in N and s not in E:
                            print(s)
                            return False

        return True

    def check_if_context_free(self):
        # LHS of every production must be a single nonterminal
        return all(key in self.N for key in self.P.keys())

