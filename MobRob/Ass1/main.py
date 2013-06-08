#!/usr/bin/python

import sys, math
from view import *
try:
    from PyQt4.QtGui import *
    from PyQt4.QtCore import *
except ImportError:
    print "PyQt4 not installed"
    sys.exit(1)


SCENE_RECT = QRectF(0,0,1000,600)

class MyApp(QWidget):
    def __init__(self, parent = None):
        super(MyApp, self).__init__(parent)
        self.setGeometry(SCENE_RECT.toRect())
        self.hbox = QHBoxLayout()
        self.hbox.addWidget(MyView(SCENE_RECT))
        self.setLayout(self.hbox)

    
if __name__ == "__main__":
    app = QApplication(sys.argv)
    myapp = MyApp()
    myapp.show()
    sys.exit(app.exec_())
    
