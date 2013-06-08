'''
Created on 02-Nov-2012

@author: wish
'''
from misc import *
from math import sqrt
from numpy.linalg import norm

def coordDist(c1, c2):
    ret = 0
    for i in range(len(c1)):
        ret += (c1[i] - c2[i])**2
    return sqrt(ret)
        


A = sio.loadmat(OutputFilePath + "A.mat")['m']
eigMat = sio.loadmat(OutputFilePath + "eigenFace.mat")['m']
avrFace = sio.loadmat(OutputFilePath + "avrFace.mat")['m']
imageLabel = sio.loadmat(OutputFilePath + "SmallYaleLabel.mat")['l'].T


resultList = []

dirCount = 0
fileCount = 0

k = 2
V = eigMat[:,:k]    # shape(200,k)
E = A * np.asmatrix(V)  # shape(n2,k) <- shape(n2, 200) * shape(200,k)
featureSpace = E.T * np.asmatrix(A)     # shape(k, 200) <- shape(k, n2) * shape(n2, 200)


for dir in listDir(DBPath):
    files = listDir(os.path.join(DBPath, dir))
    for file in files:
        newImage = getImageCol(os.path.join(DBPath,dir,file))
        testSub = E.T * np.asmatrix(newImage - avrFace)     # shape(k,1) <- shape(k, n2) * shape(n2,1)
        result = [norm(featureSpace[:,i] - testSub) for i in range(featureSpace.shape[1])]
        resultList.append(imageLabel[0][result.index(min(result))])
        fileCount += 1
    dirCount += 1

correctCount = 0
for i in range(len(resultList)):
    if imageLabel[0][i] == resultList[i]:
        correctCount += 1
        
accuracy = (correctCount/float(len(resultList))) * 100
print accuracy