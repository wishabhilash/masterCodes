'''
Created on 30-Oct-2012

@author: wish
'''
import re
from misc import *

YaleDBPath = "/home/wish/Downloads/yaleDB/"
SmallDBPath = "/home/wish/SmallYaleDB/"
pattern = "^yaleB\d{2}_P00A[\+\-]\d{3}E[\+\-]\d{2}\.pgm$"

dirList = listDir(YaleDBPath)
print dirList

def isvalid(fileName):
    if re.match(pattern, fileName):
        print fileName
        data = fileName[11:len(fileName)-4]
        dataList = data.split("E")
        A = int(dataList[0][1:])
        E = int(dataList[1])
        if A > -35 and A < 35 and E > -25 and E < 25:
            return True
        else:
            return False
    else:
        return False

for i in dirList:
    os.mkdir(SmallDBPath + i)
    os.chmod(SmallDBPath + i, 0777)
    imgList = os.listdir(YaleDBPath + i)
    for img in imgList:
        if isvalid(img):
#            print os.path.join(YaleDBPath,i,img)
            os.system("cp " + os.path.join(YaleDBPath,i,img) + " " + os.path.join(SmallDBPath,i,img))
