'''
Created on 29-Oct-2012

@author: wish
'''

from misc import *


faceMat = getImageMatrix("../data/SmallYaleDB.mat", 'm')
M = faceMat.shape[1]

#    GET AVERAGE FACE
avrFace = np.mean(faceMat, axis = 1)
avrFace = np.reshape(avrFace, (avrFace.shape[0],1))
#pylab.imsave("/home/wish/SmallYaleDB/eigenImgsAsh/avrFace.png"
#             ,np.reshape(avrFace, defaultImageShape),format = 'png',cmap = pylab.cm.Greys_r)
    DIFFERENCE BETWEEN FACES AND MEAN FACE
A = faceMat - avrFace

#    GET EIGENMATRIX
eigMat, score, latent = pca(A.T * np.asmatrix(A))


sio.savemat(OutputFilePath + "A.mat", {'m':A})
sio.savemat(OutputFilePath + "avrFace.mat", {'m':avrFace})
sio.savemat(OutputFilePath + "eigenFace.mat", {'m':eigMat})
