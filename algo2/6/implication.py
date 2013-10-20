#!/usr/bin/env python
import sys

input_file = sys.argv[1]

fin = open(input_file)
fout = open(input_file + '.out', 'a')

next(fin)

# A line of form x y should produce two edges: -x y AND -y x
for line in fin:
	e = [int(i) for i in line.split()]
	fout.write("%d %d\n" % (-e[0], e[1]))
	fout.write("%d %d\n" % (-e[1], e[0]))

fin.close()
fout.close()
