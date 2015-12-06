import Prelude hiding((^), and)

m ^ 0 = 1
and [] = True
-- good
--m ^ n = m * m ^ (n-1)
--m ^ n = m * (^) m (n-1)
--and (b:bs) = b && and bs
and (b:bs)
  | b = b
  | otherwise = and bs


-- bad
--m ^ n = m * m ^ n - 1

