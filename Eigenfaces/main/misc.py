'''
Created on 02-Nov-2012

@author: wish
'''
import scipy.io as sio
from cv2 import cv
import numpy as np
from copy import deepcopy
import pylab
import os

OutputFilePath = "/home/wish/workspace/Eigenfaces/data/"
DBPath = "/home/wish/SmallYaleDB/training"
testDBPath = "/home/wish/SmallYaleDB/testing"

defaultImageShape = (480,640)

def listDir(path):
    files = os.listdir(path)
    files.sort()
    return files

def getImageMatrix(image, key):
    ''' Get matrix from image'''
    return deepcopy(sio.loadmat(image)[key])

def pca(A):
    ''' PCA for the matrix '''
    [latent,coeff] = np.linalg.eig(np.cov(A)) # attention:not always sorted
    score = np.dot(coeff.T,A) # projection of the data in the new space
    return coeff,score,latent



def showImage(image):
    pylab.imshow(deepcopy(image), pylab.cm.Greys_r)
    pylab.show()

def getImageCol(file):
    img = cv.LoadImage(file, cv.CV_LOAD_IMAGE_GRAYSCALE)
    npArr = np.array(np.asarray(cv.GetMat(img)))
    npArr = np.ndarray.flatten(npArr)
    return np.reshape(npArr, (npArr.shape[0], 1))