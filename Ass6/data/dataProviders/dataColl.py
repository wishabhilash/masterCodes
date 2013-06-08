dic = {}
for i in range(999):
	dic[i] = 0

f = open("hashOutput.txt",'r')

while True:
	line = f.readline()
	if line == "": break
	key = int(line.split()[1])
	if key in dic:
		dic[key] += 1

f.close()
total = 0
f = open("data",'w')
for i in range(999):
	total += dic[i]
	f.write(str(dic[i])+"\n")
f.close()

print total
