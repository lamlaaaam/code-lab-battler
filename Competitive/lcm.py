# LCM found by taking the largest number and generating
# multiples of it. Each time check if every other number
# in the set divides it. If such number is found, it is
# the LCM.

# Be careful with range mode, a range difference of 10
# is rather painful.

# *****************************************************
# Importing functools for reduce function
import functools
# *****************************************************

# *****************************************************
# Finds LCM given a list of numbers
def lcm(listOfNums):
    largestNum = max(listOfNums)
    multiplier = 1
    while True:
        largestNumMultiple = largestNum * multiplier
        if divisibleByList(largestNumMultiple, listOfNums):
            return largestNumMultiple
        multiplier += 1

def isDivisibleBy(checkedNum, divisor):
    return checkedNum % divisor == 0

def divisibleByList(checkedNum, listOfNums):
    return functools.reduce(lambda x, y: x and isDivisibleBy(checkedNum, y), 
                            listOfNums,
                            True)
# *****************************************************

# *****************************************************
# Handles inputs
def getChoice():
    print("Select input mode\n[1] Range mode\n[2] Set of numbers mode")
    choice = int(input())
    if choice != 1 and choice != 2:
        print("Invalid choice!")
        return getChoice()
    return choice

def handleRangeInput():
    lower = int(input("Enter lower bound (inclusive): "))
    upper = int(input("Enter upper bound (inclusive): "))
    if upper < lower:
        print("Upper bound is lower than lower bound! Try again")
        return handleRangeInput()
    listOfNums = list(range(lower, upper + 1))
    return lcm(listOfNums)

def handleSetInput():
    inputStr = input("Enter numbers separated by space (e.g. 1 3 5 20): ")
    strToList = inputStr.split()
    listOfNums = list(map(lambda x : int(x), strToList))
    return lcm(listOfNums)
# *****************************************************

# *****************************************************
# Main driver 
def main():
    choice = getChoice()
    if choice == 1: 
        print("LCM: " + str(handleRangeInput()))
        return
    print("LCM: " + str(handleSetInput()))
# *****************************************************
    
main()
