'''
Created on 05-Nov-2012

@author: wish
'''
from misc import *
from numpy.linalg import norm

A = sio.loadmat(OutputFilePath + "A.mat")['m']
eigMat = sio.loadmat(OutputFilePath + "eigenFace.mat")['m']
avrFace = sio.loadmat(OutputFilePath + "avrFace.mat")['m']
imageLabel = sio.loadmat(OutputFilePath + "SmallYaleLabel.mat")['l'].T

resultList = []

outputPath = "/home/wish/SmallYaleDB/opFiles"

dirCount = 0
fileCount = 0

for ran in range(1,16):
    V = eigMat[:,:ran]
    E = A * np.asmatrix(V)
    featureSpace = E.T * np.asmatrix(A)     # shape(k, 200) <- shape(k, n2) * shape(n2, 200)
    
    for file in os.listdir(os.path.join(testDBPath,"3.1")):
        saveDirPath = os.path.join(outputPath,"3.1",file[:-4])
        if not os.path.isdir(saveDirPath):
            os.mkdir(os.path.join(outputPath,"3.1",file[:-4]))
        
        newImage = getImageCol(os.path.join(testDBPath, "3.1",file))
        testSub = E.T * np.asmatrix(newImage - avrFace)
        newImage = E * testSub
        
        newImagePath = os.path.join(saveDirPath,str(ran))
#        print [norm(featureSpace[:,i] - testSub) for i in range(featureSpace.shape[1])]
        pylab.imsave(newImagePath,np.reshape(newImage, defaultImageShape),format = 'png',cmap = pylab.cm.Greys_r)
        