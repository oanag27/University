from dictionary import *

class Console:
    def __init__(self,file_name):
        self.graph = Dictionaries()
        self.graph.read_file(file_name)
        self.file_name = file_name

    def menu(self):
        print("\n")
        print("Your choices are: ")
        print("0.EXIT!!")
        print("1. Get the number of vertices")
        print("2. Parse the set of vertices")
        print("3. Check edge between 2 vertices")
        print("4. Get in and out degree")
        print("5. Parse the set of outbound edges of a specified vertex")
        print("6. Parse the set of inbound edges of a specified vertex ")
        print("7. Get the endpoint of a specified edge")
        print("8. Get cost")
        print("9. Modify cost")
        print("10. Add an edge")
        print("11. Remove an edge")
        print("12. Add a vertex")
        print("13. Remove a vertex")
        print("14. Copy the graph")
        print("15. Save changes in file")

    def run_menu(self):
        while True:
            self.menu()
            choice = int(input("Your choice is: "))
            if choice == 0:
                return
            elif choice == 1:
                print('Number of vertices: ',self.graph.nrVertices())
            elif choice == 2:
                print('The iterable list of vertices: ',self.graph.vertices())
            elif choice == 3:
                vertex1 = int(input('First vertex: '))
                vertex2 = int(input('Second vertex: '))
                try:
                    if self.graph.isEdge(vertex1,vertex2) == True:
                        print('There is an edge between the two vertices')
                    else:
                        print('There is no edge!')
                except ValueError:
                    print('There is no edge!')
            elif choice == 4:
                vertex = int(input('Vertex: '))
                inDegree = self.graph.inDegree(vertex)
                outDegree = self.graph.outDegree(vertex)
                print('In degree: ',inDegree)
                print('Out degree: ',outDegree)
            elif choice == 5:
                vertex = int(input('Vertex: '))
                print(self.graph.outboundEdges(vertex))
            elif choice == 6:
                vertex = int(input('Vertex: '))
                print(self.graph.inboundEdges(vertex))
            elif choice == 7:
                print("Not applicable!")
            elif choice == 8:
                vertex1 = int(input('First vertex: '))
                vertex2 = int(input('Second vertex: '))
                try:
                    print('Cost: ',self.graph.getCost(vertex1,vertex2))
                except EdgeHasNotBeenFound:
                    print("No such edge!")
                except ValueError:
                    print("Value Error!")
            elif choice == 9:
                vertex1 = int(input('First vertex: '))
                vertex2 = int(input('Second vertex: '))
                newCost = int(input('New cost: '))
                try:
                    self.graph.modifyCost(vertex1,vertex2,newCost)
                except EdgeHasNotBeenFound:
                    print("No such edge!")
                except ValueError:
                    print("Value Error!")
            elif choice == 10:
                vertex1 = int(input('First vertex: '))
                vertex2 = int(input('Second vertex: '))
                cost = int(input('Cost: '))
                try:
                    self.graph.addEdge(vertex1,vertex2,cost)
                except CompleteGraphException:
                    print("The graph is complete!")
                except ItAlreadyExists:
                    print("The edge already exists!")
            elif choice == 11:
                vertex1 = int(input('First vertex: '))
                vertex2 = int(input('Second vertex: '))
                try:
                    self.graph.removeEdge(vertex1,vertex2)
                except EdgeHasNotBeenFound:
                    print("No such edge!")
                except ValueError:
                    print("Value Error!")
            elif choice == 12:
                vertex = int(input('Choose your vertex: '))
                try:
                    self.graph.addVertex(vertex)
                except Exception:
                    print("Vertex already exists!")
            elif choice == 13:
                vertex = int(input('Choose your vertex: '))
                try:
                    self.graph.removeVertex(vertex)
                except Exception:
                    print("Vertex does not exist!")
            elif choice == 14:
                new_graph = Dictionaries()
                try:
                    self.graph.copyGraph(new_graph)
                except ValueError:
                    print("Value Error!")
            elif choice == 15:
                writeGraphExternally(self.graph,self.file_name)
            else:
                print("Invalid command!!!")


