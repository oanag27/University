from hashtabble import HashTableClass

class SymbolTableClass:
    def __init__(self, sizeOfSymbolTable):
        self.sizeOfSymbolTable = sizeOfSymbolTable
        self.table = HashTableClass(sizeOfSymbolTable)

    def addHashValue(self, value):
        return self.table.addValueToHashTable(value)

    def deleteHashValue(self,value):
        self.table.deleteValueFromHashTable(value)

    def getHashValue(self,value):
        return self.table.getValueFromHashTable(value)

    def hashContainsValue(self,value):
        return self.table.hashTableContainsValue(value)

    def __str__(self):
        return "SymbolTable {{ hash_table={} }}".format(self.table)