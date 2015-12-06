import Data.Char

let2int c = ord c - ord 'a'

int2let n = chr (ord 'a' + n)

shiftLower n c = int2let ((let2int c + n) `mod` 26)

shift n c
  | isLower c = shiftLower n c
  | isUpper c = toUpper (shiftLower n (toLower c))
  | otherwise = c

encode n xs = [shift n x | x <- xs]

