'''
Created on 31-Oct-2012

@author: wish
'''

from util.pca import princomp
from util.CreateMat import showImage
import scipy.io as sio
import numpy as np


dic = sio.loadmat("../data/SmallYaleDB.mat")['m'][0]
princomp(dic)
showImage(dic)
