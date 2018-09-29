#!/usr/bin/env python3

def simulate(instructions: dict, tape_size: int = 16, iterations: int = 24):
    tape, head, state = ['B'] * tape_size, 0, 's1'

    for _ in range(iterations):
        print("{}{}{}".format(state.rjust(4), ": ", "".join(tape)))
        print(' ' * (6 + head) + '^')
        tape[head], head_dir, state = instructions[(tape[head], state)]
        head += 1 if head_dir == 'R' else -1

def h_simulate(name='', width=20, **kwargs):
    mid = width - (len(name) + 2)
    left = mid // 2
    print('{} {} {}'.format('=' * left, name, '=' * (mid - left)))
    simulate(instructions=globals()[name], **kwargs)
    print()


"""
The X_B machine exhibits repetition and (to some extent) conditional
"""
X_B = {
    ('B', 's1'): ('X', 'R', 's2'),
    ('B', 's2'): ('B', 'L', 's3'),
    ('X', 's3'): ('B', 'R', 's4'),
    ('B', 's4'): ('B', 'L', 's1'),
}
h_simulate('X_B', tape_size=2, iterations=8)

"""
A complete Turing machine should have:
* repetition
* conditionals
* nested & compound data structures

We're going for an adder with unary number encoding.

Unary numbers:
1    = 1
11   = 2
111  = 3
1111 = 4

Example:

Start with
(11+111)BBB
        ^
Move cursor left until it sees +, write 1 over it
(111111)BBB
   ^
Move cursor right until it sees ), write B over it
(111111BBBB
       ^
Move one left and write )
(11111)BBBB

From here we can move on to list representations:
(1,11,111)

If we then assign each letter its own code, we have strings:
"DAD" = (1111,1,1111)

And even lists of lists (in other words trees, yay!):
((1,11),(111,1111))
"""
ADDER = {
    ('B', 's1'): ('(', 'R', 's2'),
    ('B', 's2'): ('1', 'R', 's3'),
    ('B', 's3'): ('1', 'R', 's4'),
    ('B', 's4'): ('+', 'R', 's5'),
    ('B', 's5'): ('1', 'R', 's6'),
    ('B', 's6'): ('1', 'R', 's7'),
    ('B', 's7'): ('1', 'R', 's7b'),
    ('B', 's7b'): ('1', 'R', 's8'),
    ('B', 's8'): (')', 'R', 's9'),

    ('B', 's9'): ('B', 'L', 's9'),
    (')', 's9'): (')', 'L', 's9'),
    ('1', 's9'): ('1', 'L', 's9'),
    ('+', 's9'): ('1', 'R', 's10'),

    ('1', 's10'): ('1', 'R', 's10'),
    (')', 's10'): ('B', 'L', 's11'),

    ('1', 's11'): (')', 'R', 's12'),

    ('B', 's12'): ('B', 'R', 's12'),
}
h_simulate('ADDER', iterations=23)
