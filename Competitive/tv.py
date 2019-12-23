import ast

result = []

def readInput(fileInput):
    inp = ast.literal_eval(fileInput.read())
    return inp

def handleInput(inp):
    for entry in inp:
        handleEntry(entry)

def handleEntry(entry):
    orgName = entry["orgName"]
    chName = entry["chName"]
    chNum = entry["chNum"]
    addEntry(orgName, chName, chNum)

def addEntry(orgName, chName, chNum):
    for entry in result:
        if orgName in entry:
            entry[orgName].append({chName : chNum})
            return
    result.append({orgName : [{chName : chNum}]})

def prettyPrint(result):
    counter = len(result)
    print('[')
    for entry in result:
        print(str(entry) + (',' if counter > 1 else ''))
        counter -= 1
    print(']')

def main():
    fileInput = open("./input.txt", 'r')
    inp = readInput(fileInput)
    handleInput(inp)
    prettyPrint(result)
    return result

main()
