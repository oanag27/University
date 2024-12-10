class HashTableClass:
    def __init__(self, sizeOfHashTable):
        self.table = []
        for i in range(sizeOfHashTable):
            self.table.append([])
        self.sizeOfHashTable = sizeOfHashTable

    def retrieveHashIndex(self, value):
        return self.hash(value)

    def hash(self, value):
        if isinstance(value, int):
            return value % self.sizeOfHashTable
        elif isinstance(value, str):
            ascii_sum = 0
            for char in value:
                ascii_sum += ord(char) #add the ASCII value to the s
            return  ascii_sum % self.sizeOfHashTable

    def addValueToHashTable(self, value):
        newValue = self.retrieveHashIndex(value)
        self.table[newValue].append(value)

    def deleteValueFromHashTable(self, value):
        index = self.hash(value)
        if index < 0 or index >= self.sizeOfHashTable:
            return
        for item in self.table[index]:
            if item == value:
                self.table[index].remove(item)
                return

    def getValueFromHashTable(self, value):
        index = self.retrieveHashIndex(value)
        if index < 0 or index >= self.sizeOfHashTable:
            return None
        for item in self.table[index]:
            if item == value:
                return item
        return None

    def hashTableContainsValue(self,value):
        newValue = self.retrieveHashIndex(value)
        for i in self.table[newValue]:
            if i == value:
                return True
        return False

    def __str__(self):
        return str(self.table)

