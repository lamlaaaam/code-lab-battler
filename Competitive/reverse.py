def reverseStr(s):
    return s if len(s) <= 1 else s[-1] + reverseStr(s[:-1])
s = input("Gimme a damn string: ")
print("Here's yo dam reversed string: " + reverseStr(s))

