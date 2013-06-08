'''
Created on 02-Nov-2012

@author: wish
'''
from misc import *
import os

E = sio.loadmat(OutputFilePath + "E.mat")['m']

for i in range(E.shape[1]):
    pylab.imshow(np.reshape(E[:,i],(480,640)), pylab.cm.Greys_r)
    pylab.savefig(os.path.join(os.environ["HOME"],"Desktop/eigenImages/",str(i)))