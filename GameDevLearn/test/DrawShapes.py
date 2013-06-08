'''
Created on 13-Oct-2012

@author: wish
'''

import pygame, sys
from pygame.locals import *

pygame.init()
screen = pygame.display.set_mode((800,600), 0, 32)

points = [(20,20), (400,20), (100,100), (200,200)]
color = (250,250,0)

while True:
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()
    
    screen.lock()
    
    pygame.draw.rect(screen, (230, 50, 50), Rect((100,100), (130, 100)))
    
    pygame.draw.polygon(screen, color, points)
    pygame.draw.circle(screen, color, (300,300), 50)
    screen.unlock()
    pygame.display.update()



if __name__ == '__main__':
    pass