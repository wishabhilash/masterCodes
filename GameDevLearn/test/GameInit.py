'''
Created on 13-Oct-2012

@author: wish
'''

bgi = "../images/back.jpg"
mif = "../images/ball.png"

import pygame, sys
from pygame.locals import *

pygame.init()
screen = pygame.display.set_mode((800,600), 0, 32)

background = pygame.image.load(bgi).convert()
cursor = pygame.image.load(mif).convert_alpha()

x, y = 0, 0
move_x, move_y = 0, 0

#x, y = pygame.mouse.get_pos()

while True:
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()
        if event.type == KEYDOWN:
            if event.key == K_LEFT:
                move_x = -1
            elif event.key == K_RIGHT:
                move_x = +1
            elif event.key == K_UP:
                move_y = -1
            elif event.key == K_DOWN:
                move_y = +1
                
        elif event.type == KEYUP:
            if event.key == K_LEFT:
                move_x = 0
            elif event.key == K_RIGHT:
                move_x = 0
            elif event.key == K_UP:
                move_y = 0
            elif event.key == K_DOWN:
                move_y = 0
                
    x += move_x
    y += move_y        
            
    screen.blit(background, (0,0))
    
    #x -= cursor.get_width()/2
    #y -= cursor.get_height()/2
    
    screen.blit(cursor, (x, y))
    
    pygame.display.update()


if __name__ == '__main__':
    pass