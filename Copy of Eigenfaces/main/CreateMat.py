'''
Created on 30-Oct-2012

@author: wish
'''

from misc import * 


dirList = listDir(DBPath)

mainArray = np.ndarray([])
seqArray = np.ndarray([])

fileCount = 0
dirCount = 1




if __name__ == '__main__':

    for dir in dirList:
        fileList = os.listdir(os.path.join(DBPath,dir))
        for file in fileList:
            npArr = getImageCol(os.path.join(DBPath,dir,file))
            if fileCount == 0:
                mainArray = npArr
                seqArray = np.array([dirCount])
            else:
                mainArray = np.hstack((mainArray, npArr))
                seqArray = np.append(seqArray, np.array([dirCount]))
            
            fileCount += 1
        dirCount += 1
        if dirCount == 14:
            dirCount += 1
    
    
    sio.savemat(OutputFilePath + "SmallYaleDB.mat", {'m':mainArray})
    sio.savemat(OutputFilePath + "SmallYaleLabel.mat", {'l':seqArray})
