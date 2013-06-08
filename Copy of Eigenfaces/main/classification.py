'''
Created on 02-Nov-2012

@author: wish
'''
from misc import *
from math import sqrt
from numpy.linalg import norm



A = sio.loadmat(OutputFilePath + "A.mat")['m']
eigMat = sio.loadmat(OutputFilePath + "eigenFace.mat")['m']
avrFace = sio.loadmat(OutputFilePath + "avrFace.mat")['m']
imageLabel = sio.loadmat(OutputFilePath + "SmallYaleLabel.mat")['l'].T


resultList = []

dirCount = 0
fileCount = 0

k = 20
V = eigMat[:,:k]    # shape(200,k)
E = A * np.asmatrix(V)  # shape(n2,k) <- shape(n2, 200) * shape(200,k)
featureSpace = E.T * np.asmatrix(A)     # shape(k, 200) <- shape(k, n2) * shape(n2, 200)

#for i in range(E.shape[1]):
##    print E[:,i].shape
##    np.reshape(E[:,:i],defaultImageShape)
#    pylab.imsave("/home/wish/SmallYaleDB/eigenImgsAsh/" + str(i) + ".png",np.reshape(E[:,i], defaultImageShape)
#                 ,format = 'png',cmap = pylab.cm.Greys_r)


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