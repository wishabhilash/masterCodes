'''
Created on 14-Oct-2012

@author: wish
'''
import math


class Vector(object):
    def add(self, vec1, vec2):
        return vec1[0] + vec2[0], vec1[1] + vec1[1]
    
    def multiply(self, vec, mul):
        return vec[0]*mul, vec[1]*mul
    
    def hypotenuse(self, vec):
        return math.hypot(vec[0], vec[1])
    
    def unit(self, vec):
        hyp = self.hypotenuse(vec)
        return vec[0]/hyp, vec[1]/hyp
    
if __name__ == '__main__':
    vect = Vector()
    vec1 = (20,20)
    vec2 = (30,30)
    print "Addition: ",vect.add(vec1, vec2)
    print "Mult by 3: ", vect.multiply(vec1, 3)
    print "Hyp: ", vect.hypotenuse(vec1)
    print "Unit: ", vect.unit(vec1)
     