'''
Created on 05-Nov-2012

@author: wish
'''
from misc import *
from numpy.linalg import norm
from matplotlib import pyplot

meDir = "/home/wish/SmallYaleDB/opFiles/3.1/me"
outerDir = "/home/wish/SmallYaleDB/opFiles/3.1/outer"
dbDir = "/home/wish/SmallYaleDB/opFiles/3.1/db"
source = "/home/wish/SmallYaleDB/testing/3.1"

meList = []
outerList = []
dbList = []    

sourceMeImg = getImageCol(os.path.join(source,"me.pgm"))
sourceOuterImg = getImageCol(os.path.join(source,"outer.pgm"))
sourceDbImg = getImageCol(os.path.join(source,"db.pgm"))

for ran in range(1,16):
    meImg = getImageCol(os.path.join(meDir,str(ran)))
    meList.append(norm(meImg - sourceMeImg))
    
    outerImg = getImageCol(os.path.join(outerDir,str(ran)))
    outerList.append(norm(outerImg - sourceOuterImg))
    
    dbImg = getImageCol(os.path.join(dbDir,str(ran)))
    dbList.append(norm(dbImg - sourceDbImg))
    
pyplot.plot(range(15), meList, label = 'Me')
pyplot.plot(range(15), outerList, label = 'Outer', linestyle = "--", color = 'r')
pyplot.plot(range(15), dbList, label = 'Db', linestyle = "-.", color = 'k')

pyplot.xlabel("Iterations")
pyplot.ylabel("RMSE")
pyplot.show()