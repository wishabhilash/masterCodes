try:
    from PyQt4.QtGui import *
    from PyQt4.QtCore import *
except ImportError:
    import sys
    print "PyQt4 not installed"
    sys.exit(1)


BOT_COLOR = "22,235,21"
OBSTACLE_COLOR = "210,78,78"
FRAME_RANGE = 200


    
class ObsAnimate(QGraphicsItemAnimation):
    botType = False
    
    def __init__(self, botData, parent = None):
        super(ObsAnimate, self).__init__(parent)
        self.bot = BotObj((botData['pos'][0],botData['pos'][1]), botData['rad'],
                            [BOT_COLOR if self.botType else OBSTACLE_COLOR][0], self.botType)
        self.setItem(self.bot)
        
        self.timer = QTimeLine(botData['dur'])
        self.timer.setCurveShape(0)
        print self.timer.curveShape()
        self.timer.setFrameRange(0,FRAME_RANGE)
        self.setTimeLine(self.timer)
        
        QObject.connect(self.timer, SIGNAL("finished()"), self.resumeTimer)
        
        
    def getTimeLine(self):
        return self.timer
        
    def startTimer(self):
        self.timer.start()    
        
    def getItem(self):
        return self.bot
    
    def resumeTimer(self):
        self.timer.toggleDirection()
        self.timer.start()
    
    def currentFrame(self):
        return self.timer.currentFrame()
        

class BotAnimate(ObsAnimate):
    botType = True
    
    def __init__(self, parent = None):
        super(BotAnimate, self).__init__(parent)
        QObject.connect(self.timer, SIGNAL("frameChanged(int)"), self.distChanged)
        
    def distChanged(self, frameNum):
        scene = self.bot.scene()
        itemsList = scene.items()
        for i in itemsList:
            if not i.botType:
                #print i.pos()
                pass


class BotObj(QGraphicsItem):
    botType = False
    
    def __init__(self, pos, radius, color = OBSTACLE_COLOR, botType = False, parent = None):
        self.botType = botType
        self.radius = radius
        super(BotObj, self).__init__(parent)
        
        self.rect = QRectF(0, 0, float(radius*2), float(radius*2))
        self.color = color
        self.setPos(pos[0], pos[1])
        
    def paint(self, painter, option, widget = None):
        pen = QPen(QColor(255,255,255))
        pen.setWidth(0)
        painter.setPen(pen)
        painter.setBrush(QBrush(self.digestColor(self.color)))
        painter.drawEllipse(self.rect)
        #painter.drawRect(self.rect)
        
    def digestColor(self, color):
        colorList = color.split(",")

        return QColor(int(colorList[0]),int(colorList[1]),int(colorList[2]))
    
    def boundingRect(self):
        return self.rect
        
    def getActualPos(self):
        return (self.pos(), self.radius)
