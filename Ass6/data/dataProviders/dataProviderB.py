dic = {}
for i in range(999):
	dic[i] = 0
	
for i in range(6):
	fileName = "../../B_split_files/" + str(i) + ".txt"
	f = open(fileName,'r')
	while True:
		try:
			key = int(f.readline().split()[0])
			if key in dic:
				dic[key] += 1
		except IndexError:
			break
	f.close()
	
f = open("../data/dataB",'w')
total = 0

for i in range(999):
	total += dic[i]
	f.write(str(dic[i])+"\n")
f.close()

