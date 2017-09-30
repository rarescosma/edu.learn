second xs = head (tail xs)
swap (x, y) = (y, x)
pln xs = reverse xs == xs
twice f x = f (f x)
odds n =  map (\x -> 2*x + 1) [0..n-1]

