A = open("../data/dataA",'r')
B = open("../data/dataB",'r')

f = open("../data/dataSort",'w')

total = 0

while True:
	try:
		val = int(A.readline()) * int(B.readline())
		total += val
		f.write(str(val) + "\n")
	except ValueError:
		break
		
A.close()
B.close()
f.close()

print total
