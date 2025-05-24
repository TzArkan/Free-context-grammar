Role of this automata is to see if a word would be part of a grammar.

Goal:
	To analyze a context-free grammar (CFG) and apply three lemmas that:

	Filter out useless symbols

	Identify reachable symbols

	Eliminate nullable nonterminals
	...and also check if a subsequence can be generated.

Main Functions in Lema3
	analizeaza()
	Extracts:

		Terminals (lowercase letters)

		Nonterminals (uppercase letters from left-hand sides)

	Does a basic pass to collect grammar components.

	aplicaLema1(): "Remove useless symbols"
		Identifies useful nonterminals: ones that can eventually derive strings made of terminals.

		Filters out useless productions that can't contribute to terminal strings.

	aplicaLema2(): "Remove unreachable symbols"
		Starts from the start symbol.

		Marks only those symbols and productions that are accessible from the start via derivations.

		Filters out unreachable rules/symbols.

	verificaSubsir(): "Can a substring be derived?"
		Checks recursively if a specific substring (like "ab") can be produced starting from a given nonterminal, using the production rules.

	aplicaLema3(): "Eliminate nullable nonterminals"
		Identifies nullable nonterminals (i.e., those that can derive ε).

		Generates new equivalent productions by eliminating nullable symbols in all possible combinations.

		Avoids keeping empty-string productions (except maybe start if needed).

Auxiliary
	genereazaCombinatii()
		Helper for aplicaLema3(): generates all right-hand side variations with/without nullable nonterminals.

	citesteDinFisier()
		Reads a CFG from a text file, parsing lines like S aA, A b, or B.

Example Usage
	You could use this code to:

	Clean a CFG by eliminating useless/unreachable/null-producing rules.

	Analyze the grammar structure.

	Answer questions like "Can the nonterminal S produce the substring abc?"