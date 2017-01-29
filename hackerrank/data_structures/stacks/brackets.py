#!/bin/python3

import sys

# Number of strings to check
n = int(input().strip())

# Read the strings
strings = [[] for i in range(n)]
for idx in range(n):
    strings[idx] = input().strip()

# print(strings)

symbol_table = {
    ')' : '(',
    ']' : '[',
    '}' : '{'
}
opening_syms = symbol_table.values()

def is_balanced(string):
    stack = []
    balanced = True
    idx = 0

    while idx < len(string) and balanced:
        symbol = string[idx]
        if is_opening(symbol):
            stack.append(symbol)
        elif not len(stack):
            return False
        elif symbol_table.get(symbol, '') == stack[-1]:
            stack.pop()

        idx = idx + 1

    return balanced and len(stack) == 0

def is_opening(symbol):
    return symbol in opening_syms

def answer(string):
    return 'YES' if is_balanced(string) else 'NO'

[print(answer(x)) for x in strings]
