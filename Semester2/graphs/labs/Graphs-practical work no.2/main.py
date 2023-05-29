from console import Console
from dictionary import Dictionaries
from dictionary import createRandomGraph
from dictionary import writeGraphExternally
from dictionary import readTheGraphExternally
from dictionary import undirectedGraph

console = Console("file1.txt","graph10k.txt")
console.run_menu()




"""
def menuFiles():
    print("1. graph1k.txt")
    print("2. graph1k_modif.txt")
    print("3. graph10k.txt")
    print("4. graph100k.txt")


menuFiles()
choice = int(input("Choose a file: "))
if choice == 1:
    graph = Dictionaries()
    readTheGraphExternally(graph,"graph1k.txt")
    writeGraphExternally(graph,"graph1k_modification.txt")
    console = Console("graph1k_modification.txt")
    console.run_menu()
elif choice == 2:
    console = Console("graph1k_modif.txt")
    console.run_menu()
elif choice ==3:
    graph = Dictionaries()
    readTheGraphExternally(graph,"graph10k.txt")
    writeGraphExternally(graph,"graph10k_modification.txt")
    console = Console("graph10k_modification.txt")
    console.run_menu()
elif choice == 4:
    graph = Dictionaries()
    readTheGraphExternally(graph,"graph100k.txt")
    writeGraphExternally(graph,"graph100k_modification.txt")
    console = Console("graph100k_modification.txt")
    console.run_menu()
else:
    print("Invalid command!")


# graph1 = createRandomGraph(7,20)
# writeGraphExternally(graph1,"random_graph1.txt"
"""