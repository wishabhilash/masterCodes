from bots import *
import sys
try:
    from PyQt4.QtGui import *
    from PyQt4.QtCore import *
except ImportError:
    print "PyQt4 not installed"
    sys.exit(1)


class MyView(QGraphicsView):
    def __init__(self, size, parent = None):
        
        obsList, botList = self.readConfig()
        super(MyView, self).__init__(parent)
        
        self.scene = QGraphicsScene(self)
        self.scene.setSceneRect(size)
        self.setScene(self.scene)
        
        self.obstacles = []
        for i in obsList:
            self.obstacles.append(ObsAnimate(i))
        
        for i in range(len(self.obstacles)):
            self.createBot(self.obstacles[i], obsList[i])
        
        self.bots = []
        for i in botList:
            self.bots.append(BotAnimate(i))
        
        for i in range(len(self.bots)):
            self.createBot(self.bots[i], botList[i])
        
        sceneItems = self.scene.items()
        
        
        
        
    def createBot(self, anim, data):
        ''' Creates an animated bot or obstacle'''
        self.moveBot(anim, data)
        self.scene.addItem(anim.getItem())
        anim.startTimer()
        
    
    def moveBot(self, anim, data):
        ''' Move the bot'''
        if data['dir'] == "V":
            for i in range(int(data['dist'])):
                anim.setPosAt(1.0, QPointF(int(anim.getItem().pos().x()),i))
        else:
            for i in range(int(data['dist'])):
                anim.setPosAt(1.0, QPointF(i,int(anim.getItem().pos().y())))
        
    
    def readConfig(self):
        ''' Parses the config file '''
        obsList = []
        botList = []
        flag = None
        
        f = open("config", 'r')
        lines = f.readlines()
        f.close()
        
        for line in lines:
            if line == "\n":
                continue
            if line[0] == "#" or line == "":
                continue
            if line == "[[ OBSTACLES ]]\n":
                flag = True
                continue
            if line == "[[ BOTS ]]\n":
                flag = False
                continue
                
            if flag:
                obsList.append(self.getInfoDic(line))
            elif not flag:
                botList.append(self.getInfoDic(line))
                
        return obsList, botList
    
    def getInfoDic(self, data):
        ''' Returns dict with keys: rad {radius}, pos (x,y), dur {duration}, dir {direction}'''
        dataList = data.split()
        return {
                    "rad" : float(dataList[0]),
                    "pos": (float(dataList[1]),float(dataList[2])),
                    "dur": float(dataList[3]),
                    "dist": float(dataList[4]),
                    "dir": dataList[5]
                }


if __name__ == '__main__':
    app = QApplication(sys.argv)
    myapp = MyView(QRectF(400,400), None)
    myapp.show()
    sys.exit(app.exec_())
