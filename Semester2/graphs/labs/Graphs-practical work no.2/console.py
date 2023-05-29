from dictionary import *

class Console:
    def __init__(self,file_name,file_name2):
        self.graph = Dictionaries()
        self.graph.read_file(file_name)
        self.undirGraph = undirectedGraph()
        self.undirGraph.read_file2(file_name2)

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
        print("16. Connected components of an undirected graph using a breadth-first traversal of the graph")
        print("17. Connected components of an undirected graph using a breadth-first traversal of the graph (as unidirected graph type)")

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
                try:
                    vertex = int(input('Vertex: '))
                    inDegree = self.graph.inDegree(vertex)
                    outDegree = self.graph.outDegree(vertex)
                    print('In degree: ',inDegree)
                    print('Out degree: ',outDegree)
                except ValueError:
                    print("Value error!")
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
            elif choice == 16:
                connectedComponents = self.undirGraph.connected_components_bfs_method()
                for c in range(0,len(connectedComponents)):
                    print("Connected component " + str(c + 1) + " => " + str(list(connectedComponents[c])))
            elif choice == 17:
                connectedComponents = self.undirGraph.connected_components_bfs_method()
                # Create a new list which stores the connected components as undirectedGraph objects
                component_graphs = []
                # Iterate through each connected component
                # Create a new graph object for each one
                for c in connectedComponents:
                    # Create a new graph object
                    component_graph = undirectedGraph()
                    # Add vertex to the new graph object
                    for vertex in c:
                        component_graph.D_vertices[vertex] = []
                        # Add edge to the new graph object
                        for neighbor in self.undirGraph.D_vertices[vertex]:
                            if neighbor in c:
                                component_graph.D_vertices[vertex].append(neighbor)
                    # Add the new graph object to the list of component graphs
                    component_graphs.append(component_graph)
                # Print the list
                for i,graph_component in enumerate(component_graphs):
                    print(f"Component {i} {graph_component}")
            else:
                print("Invalid command!!!")


