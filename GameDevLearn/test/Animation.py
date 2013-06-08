'''
Created on 13-Oct-2012

@author: wish
'''

'''
Created on 13-Oct-2012

@author: wish
'''

bgi = "../images/back.jpg"
mif = "../images/ball.png"

screen_size = (800, 600)

import pygame, sys
from pygame.locals import *

pygame.init()
screen = pygame.display.set_mode(screen_size, 0, 32)

background = pygame.image.load(bgi).convert()
ball = pygame.image.load(mif).convert_alpha()

x, y = 0, 0
move_x, move_y = 0, 0

x, y = 0, 0
flag = False

clock = pygame.time.Clock()
speed_x = 250
speed_y = 300

while True:
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()
    
    screen.blit(background, (0,0))
    screen.blit(ball, (x,y))
    
    milli = clock.tick()
    secs = milli/1000.0
    dist_moved_x = secs * speed_x
    dist_moved_y = secs * speed_y
#    x += dist_moved
    
    if not flag:
        x += dist_moved_x
        y += dist_moved_y
    else:
        x -= dist_moved_x
        y -= dist_moved_y
    
    if x > screen_size[0] or y > screen_size[1]:
        flag = True
    elif x < 0 or y < 0:
        flag = False
    
    pygame.display.update()


if __name__ == '__main__':
    pass