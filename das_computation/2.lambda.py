#!/usr/bin/env python

"""
Mission statement: write the factorial function with lambda calculus.

Don't use 0, 1 or any number, don't use def, comparision, multiplication,
subtraction, if.

The only thing we can use:
! Definitions of functions with EXACTLY one argument
! Calls to functions (can be nested)
"""

def fact(n):
    if n == 0:
        return 1
    else:
        return n * fact(n - 1)

# Step 1: replace the IF
IS_ZERO = lambda x: x == 0

# Step 2: replace value 1 with literal
ONE = 1

# Step 3: replace minus with function
SUB1 = lambda x: x - ONE

# Step 4: replace multiplication
MULT = lambda x, y: x * y

def fact2(n):
    if IS_ZERO(n):
        return ONE
    else:
        return MULT(n, fact(SUB1(n)))

# Step 5: replace if
IF = lambda cond, t_func, f_func: t_func() if cond else f_func()

def fact3(n):
    return IF(
        IS_ZERO(n),
        lambda: ONE,
        lambda: MULT(n, fact(SUB1(n)))
    )

# Step 6: curry everything
MULT2 = lambda x: lambda y: x * y
IF2 = lambda cond: lambda t_func: lambda f_func: t_func(None) if cond else f_func(None)

# Step 7: add arguments to 0-ary functions

def fact4(n):
    return IF2(
        IS_ZERO(n)
    )(
        lambda _: ONE
    )(
        lambda _: MULT2(n)(fact(SUB1(n)))
    )


print(fact(6))
print(fact2(6))
print(fact3(6))
print(fact4(6))

# Step 8: get rid of def by inlining
print(
    (
        lambda myself: (
            lambda n: (
                IF2(
                    IS_ZERO(n)
                )(
                    lambda _: ONE
                )(
                    lambda _: MULT2(n)(myself(myself)(SUB1(n)))
                )
            )
        )
    )(
        lambda myself: (
            lambda n: (
                IF2(
                    IS_ZERO(n)
                )(
                    lambda _: ONE
                )(
                    lambda _: MULT2(n)(myself(myself)(SUB1(n)))
                )
            )
        )
    )
    (6)
)
