'''
Created on 29-Oct-2012

@author: wish
'''

from misc import *


faceMat = getImageMatrix("../data/SmallYaleDB.mat", 'm')    #shape: (n2,M)
M = faceMat.shape[1]

#    GET AVERAGE FACE
avrFace = np.mean(faceMat, axis = 1)    #shape: (n2,M)
avrFace = np.reshape(avrFace, (avrFace.shape[0],1))

#    DIFFERENCE BETWEEN FACES AND MEAN FACE
A = faceMat - avrFace   #shape: (n2,M)

#    GET EIGENMATRIX
eigMat, score, latent = pca(A.T * np.asmatrix(A))   #shape: (M,M)  <-  pca(shape: (M,M))


sio.savemat(OutputFilePath + "A.mat", {'m':A})
sio.savemat(OutputFilePath + "avrFace.mat", {'m':avrFace})
sio.savemat(OutputFilePath + "eigenFace.mat", {'m':eigMat})
