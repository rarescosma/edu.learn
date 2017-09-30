t = [1..6]

halve1 xs = splitAt (length xs `div` 2) xs

halve2 xs = (take (n `div` 2) xs, drop (n `div` 2) xs)
  where n = length xs

halve4 xs = (take n xs, drop n xs)
  where n = length xs `div` 2

safetail0 xs = if null xs then [] else tail xs

safetail1 [] = []
safetail1 (_:xs) = xs

safetail2 (_:xs)
  | null xs = []
  | otherwise = tail xs

safetail3 xs
  | null xs = []
  | otherwise = tail xs

safetail5 [] = []
safetail5 xs = tail xs

safetail6 [x] = [x]
safetail6 (_:xs) = xs

safetail7
 = \ xs ->
  case xs of
   [] -> []
   (_:xs) -> xs

mult0 x y z = x * y * z
mult1 = \ x -> (\ y -> (\ z -> x*y*z))
  
