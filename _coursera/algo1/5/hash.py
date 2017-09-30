# Read the input
fin = open("HashInt.txt")
H = {}

for line in fin:
    i = int(line)
    if not H.has_key(i):
        H[i] = 1

fin.close()

# Summ array
Sums = [231552,234756,596873,648219,726312,981237,988331,1277361,1283379]
Res = {s:0 for s in Sums}

print Res

for i in H:
    for s in Sums:
        if H.has_key(s-i):
            Res[s] = 1
            break

b = [Res[s] for s in Sums]
print b