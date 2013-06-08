'''
Created on 05-Nov-2012

@author: wish
'''
#!/usr/bin/python

import os, sys

#path = sys.argv[1]
#files = os.listdir(path)
#for i in files:
#    os.system("convert " + os.path.join(path,i) + "\'[640x480]\' " + os.path.join(path,"640x480","res" + i))

path = "/home/wish/Desktop/Proj/abhilash"
newFiles = os.listdir(os.path.join(path,"640x480"))
for i in newFiles:
    os.system("convert " + os.path.join(path,"640x480",i) + " " + os.path.join(path,"pgm",i[:-3] + "pgm"))
