class CompleteGraphException(Exception):
    pass
class ItAlreadyExists(Exception):
    pass
class EdgeHasNotBeenFound(Exception):
    pass

import random
from collections import deque


class Dictionaries:

    def __init__(self):
        """
            Creates a graph using dictionaries
        """
        self.n = 0          # number of vertices
        self.m = 0          # number of edges
        self.D_out = {}
        self.D_in = {}
        self.D_cost = {}    # dictionary of edges costs

    def outboundEdges(self,vertex):
        """
            returns the outbound edges of a vertex
            vertex - integer
        """
        edges = []
        for edg in self.D_cost.keys():
            if edg[0] == vertex:
                edges.append(edg)
        return edges

    def inboundEdges(self,vertex):
        """
            returns the outbound edges of a vertex
            vertex - integer
        """
        edges = []
        for edg in self.D_cost.keys():
            if edg[1] == vertex:
                edges.append(edg)
        return edges

    def read_file(self,file_name):
        """
            Reads a graph from a file
            file_name - the name of the file (string)
        """
        f = open(file_name,'r')
        line = f.readline()
        line = line.split(' ')
        self.n = int(line[0])
        self.m = int(line[1])
        for i in range(self.n):
            self.D_out[i] = []
            self.D_in[i] = []
        while True:
            line = f.readline()
            if len(line) == 0:
                return
            line = line[:-1]
            line = line.split(' ')
            v1 = int(line[0])
            v2 = int(line[1])
            cost = line[2]
            self.D_out[v1].append(v2)
            self.D_in[v2].append(v1)
            self.D_cost[(v1,v2)] = cost

    def removeVertex(self,vertex):
        """
            removes a vertex
            vertex - integer
        """
        if vertex not in self.D_out.keys():
            raise Exception
        if vertex not in self.D_in.keys():
            raise Exception
        del (self.D_out[vertex])
        del (self.D_in[vertex])
        for elems in list(self.D_cost):
            if vertex in elems:
                del (self.D_cost[elems])
        for vert in self.D_out:
            try:
                self.D_out[vert].remove(vertex)
            except ValueError:
                continue
        for vert in self.D_in:
            try:
                self.D_in[vert].remove(vertex)
            except ValueError:
                continue

    def getCost(self,vertex1,vertex2):
        """
            returns the cost of an edge
            vertex1, vertex2 - integers
        """
        if self.isEdge(vertex1,vertex2) == False:
            raise EdgeHasNotBeenFound
        if (vertex1,vertex2) not in self.D_cost.keys():
            raise EdgeHasNotBeenFound
        return self.D_cost[(vertex1,vertex2)]

    def modifyCost(self,vertex1,vertex2,newCost):
        """
            modifies the cost of an edge
            vertex1, vertex2 integers
            newCost - new cost, integer
        """
        if self.isEdge(vertex1,vertex2) == False:
            raise EdgeHasNotBeenFound
        if (vertex1,vertex2) not in self.D_cost.keys():
            raise EdgeHasNotBeenFound
        self.D_cost[(vertex1,vertex2)] = newCost

    def isEdge2(self,vertex1,vertex2):
        """
            return True if there is an edge between the 2 vertices, False otherwise
        """
        if vertex2 not in self.D_in.keys():
            raise ValueError
        if vertex1 not in self.D_out.keys():
            raise ValueError
        return vertex1 in self.D_in[vertex2] and vertex2 in self.D_out[vertex1]

    def isEdge(self,vertex1,vertex2):
        """
            return True if there is an edge between the 2 vertices, False otherwise
        """
        if vertex2 not in self.D_in.keys():
            return False
        if vertex1 not in self.D_out.keys():
            return False
        if vertex1 in self.D_in[vertex2]:
            return True
        if vertex2 in self.D_out[vertex1]:
            return True

    def inDegree(self,vertex):
        """
            returns the in degree of a vertex
            vertex - integer
        """
        if vertex not in self.D_out.keys():
            raise ValueError
        return len(self.D_in[vertex])

    def outDegree(self,vertex):
        """
            returns the out degree of a vertex
            vertex - integer
        """
        if vertex not in self.D_out.keys():
            raise ValueError
        return len(self.D_out[vertex])

    def addEdge(self,vertex1,vertex2,cost):
        """
            adds an edge between vertex1 and vertex2 with the cost givem
            vertex1 - first vertex, integer
            vertex2 - second vertex, integer
            cost - cost, integer
        """
        if self.m == (self.n * (self.n - 1)) / 2:
            raise CompleteGraphException
        if self.isEdge(vertex1,vertex2) == True:
            raise ItAlreadyExists
        self.D_in[vertex2].append(vertex1)
        self.D_out[vertex1].append(vertex2)
        self.D_cost[(vertex1,vertex2)] = cost
        self.m += 1

    def removeEdge(self,vertex1,vertex2):
        """
            Removes an edge
            or raise ValueError if it doesn't exist
        """
        if self.isEdge(vertex1,vertex2) == False:
            raise EdgeHasNotBeenFound
        self.D_out[vertex1].remove(vertex2)
        self.D_in[vertex2].remove(vertex1)
        self.D_cost.pop(vertex1,vertex2)
        self.m -= 1

    def addVertex(self,vertex):
        """
            adds a vertex to the graph
        """
        if vertex in self.D_in.keys():
            raise Exception
        if vertex in self.D_out.keys():
            raise Exception
        self.D_in[vertex] = []
        self.D_out[vertex] = []
        self.n += 1

    def nrVertices(self):
        """
            returns the number of vertices
        """
        return len(self.D_out)

    def vertices(self):
        """
            returns the list of vertices
        """
        l = []
        for i in self.D_in.keys():
            l.append(i)
        return l

    def copyGraph(self,newGraph):
        """
            copies the graph
            new_graph - a new copy of the graph
        """
        if (type(newGraph) != Dictionaries):
            raise ValueError
        newGraph.n = self.n
        newGraph.m = self.m
        newGraph.D_in = self.D_in.copy()
        newGraph.D_out = self.D_out.copy()
        newGraph.D_cost = self.D_cost.copy()


### Undirected graph ###

class undirectedGraph:
    def __init__(self):
        """
            Creates a graph using dictionaries
        """
        self.n = 0  # the number of vertices
        self.m = 0  # the number of edges
        self.D_vertices = {}
        self.D_cost = {}    # dictionary of edges costs

    def __str__(self):
        vertices = "\nVertices: "
        edges = "\nEdges: "
        for vertex in self.D_vertices:
            vertices += str(vertex) + "  "
            for i in self.D_vertices[vertex]:
                edges += "( " + str(vertex) + "  " + str(i) + " ) "

        vertices += edges
        return vertices

    def read_file2(self,file_name):
        """
            Reads a graph from a file
            file_name - the name of the file (string)
        """
        f = open(file_name,'r')
        line = f.readline()
        line = line.split(' ')
        self.n = int(line[0])
        self.m = int(line[1])
        for i in range(self.n):
            self.D_vertices[i] = []

        line = f.readline()
        while line:
            if len(line) == 0:
                return
            line = line.split(' ')
            v1 = int(line[0])
            v2 = int(line[1])
            cost = int(line[2])
            self.D_vertices[v1].append(v2)
            self.D_vertices[v2].append(v1)
            self.D_cost[(v1,v2)] = cost
            self.D_cost[(v2,v1)] = cost
            line = f.readline()

    def find(self,parent,i):
        """
        Find the parent of a vertex
        """
        if parent[i] == i:
            return i
        return self.find(parent,parent[i])

    def union(self,parentNodes,ranking,x,y):
        """
        Union of two sets
        """
        xroot = self.find(parentNodes,x)
        yroot = self.find(parentNodes,y)

        if ranking[xroot] < ranking[yroot]:
            parentNodes[xroot] = yroot
        elif ranking[xroot] > ranking[yroot]:
            parentNodes[yroot] = xroot
        else:
            parentNodes[yroot] = xroot
            ranking[xroot] += 1

    def KruskalMinimumSpanningTree(self):
        """
        Constructs a minimal spanning tree using Kruskal's algorithm
        """
        spanningTreeMin = []  # stores the minimal spanning tree
        i = 0  # an index variable-> sorted edges
        j = 0  # an index variable-> spanningTreeMin
        totalCost = 0  # total cost

        # Sort edges in non-decreasing order
        edgesList = []  # list to store edges-> (cost, u, v)
        for u in self.D_vertices:
            for v in self.D_vertices[u]:
                if (u,v) not in edgesList and (v,u) not in edgesList:  # Check if the edge is already present
                    edgesList.append((self.D_cost[(u,v)],u,v))
        edgesList.sort()  # sort edges

        parentNodes = []
        ranking = []
        # Create disjoint sets
        for node in range(self.n):
            parentNodes.append(node)
            ranking.append(0)

        # Number of edges equal to n-1 !!!
        while j < self.n - 1 and i < len(edgesList):
            cost,u,v = edgesList[i]
            i += 1
            x = self.find(parentNodes,u)
            y = self.find(parentNodes,v)

            if x != y:
                j += 1
                spanningTreeMin.append((u,v))
                # print(cost," ")
                totalCost += cost               # Add the cost of the current edge to the total cost
                self.union(parentNodes,ranking,x,y)

        return spanningTreeMin,totalCost

    def connected_components_bfs_method(self):
        # visited initialized false for all vertices
        is_visited = [False] * len(self.D_vertices)
        # components store all the connected components
        all_connected_components = []
        # iterate through all the vertices in the graph
        # perform BFS on each unvisited vertex
        for i in range(len(self.D_vertices)):
            if not is_visited[i]:
                connected_component = []        # considered a new connected component
                queue = deque([i])              # the first vertex of the connected component
                is_visited[i] = True

                # BFS traversal
                while queue:
                    node = queue.pop()
                    connected_component.append(node)
                    for neighbor in self.D_vertices[node]:
                    # iterate through all the neighbours of the vertex and add the unvisited ones to the queue
                        if not is_visited[neighbor]:
                            is_visited[neighbor] = True
                            queue.append(neighbor)
                # add component to the list of connected components
                all_connected_components.append(connected_component)
        return all_connected_components     # return a list

    def connected_components_bfs_method_2(self):
        # visited initialized false for all vertices
        is_visited = [False] * len(self.D_vertices)
        # components store all the connected components
        all_connected_components = []
        # iterate through all the vertices in the graph
        # perform BFS on each unvisited vertex
        for i in range(len(self.D_vertices)):
            if not is_visited[i]:
                connected_component = []        # considered a new connected component
                queue = deque([i])              # the first vertex of the connected component
                is_visited[i] = True
                # BFS traversal
                while queue:
                    node = queue.pop()
                    connected_component.append(node)
                    for neighbor in self.D_vertices[node]:
                    # iterate through all the neighbours of the vertex and add the unvisited ones to the queue
                        if not is_visited[neighbor]:
                            is_visited[neighbor] = True
                            queue.append(neighbor)
                # create a new undirectedGraph instance for the connected component
                new_graph = undirectedGraph()
                new_graph.n = len(connected_component)  # nr of vertices
                for vertex in connected_component:      # go through the connected_component
                    new_graph.D_vertices[vertex] = []   # initialise as empty
                    for neighbor in self.D_vertices[vertex]:  # go through the neighbours of the vertex
                        if neighbor in connected_component:   # if it is a neighbour
                            new_graph.D_vertices[vertex].append(neighbor)   # add it
                all_connected_components.append(new_graph)
        return all_connected_components     # return the list

# external functions #
def writeGraphExternally(graph,file_name):
    """
        writes the graph in a file
        graph - the graph (corresponding to the Dictionaries class)
        file_name - name of the file (string)
    """
    f = open(file_name,"w")
    first_line = str(graph.n) + ' ' + str(graph.m)+'\n'
    f.write(first_line)
    lines = []
    for edge in graph.D_cost:
        line = str(edge[0]) + ' ' + str(edge[1]) + ' ' + str(graph.D_cost[edge])+ "\n"
        lines.append(line)
    f.writelines(lines)
    f.close()


def readTheGraphExternally(graph,file_name):
    """
        reads the graph from a file
        graph - the graph (corresponding to the Dictionaries class)
        file_name - name of the file (string)
    """
    f = open(file_name,'r')
    line = f.readline()
    line = line.split(' ')
    graph.n = int(line[0])
    graph.m = int(line[1])
    for i in range(graph.n):
        graph.D_out[i] = []
        graph.D_in[i] = []
    while True:
        line = f.readline()
        if len(line) == 0:
            return
        line = line[:-1]
        line = line.split(' ')
        v1 = int(line[0])
        v2 = int(line[1])
        cost = int(line[2])
        graph.D_out[v1].append(v2)
        graph.D_in[v2].append(v1)
        graph.D_cost[(v1,v2)] = cost
    f.close()

def createRandomGraph(number_of_vertices,number_of_edges):
    """
        creates a random graph
        number_of_vertices - integer
        number_of_edges - integer
    """
    graph = Dictionaries()
    for i in range(number_of_vertices):
        graph.n += 1
        graph.D_in[i] = []
        graph.D_out[i] = []
    while graph.m != number_of_edges:
        try:
            v1 = random.randint(0,number_of_vertices-1)
            v2 = random.randint(0,number_of_vertices-1)
        except:
            v1 = random.randint(0,number_of_vertices-1)
            v2 = random.randint(0,number_of_vertices-1)
        cost = random.randint(-9999,9999)
        graph.addEdge(v1,v2,cost)
    return graph