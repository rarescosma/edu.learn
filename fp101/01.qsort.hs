qsort [] = []
qsort (x : xs) = qsort smaller ++ [x] ++ qsort larger
  where smaller = [a | a <- xs, a <= x]
        larger = [b | b <- xs, b > x]

zs = [3,2,4,4,5,4,1]


