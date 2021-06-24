from tkinter import *
from PIL import Image
import numpy
import os
from pynput.keyboard import Controller
from mcpi import minecraft
from mcpi import block
import time

def setBlock(x, y, z):
    mc.setBlocks(xx - 1 - z, yy - x, zz - y, xx - 1 - z, yy - x, zz - y, block.GOLD_BLOCK.id)

def setBlockG(x, y, z):
    mc.setBlocks(xx - 1 - z, yy - x, zz - y, xx - 1 - z, yy - x, zz - y, block.GLASS.id)

def setBlockS(x, y, z):
    mc.setBlocks(xx - 1 - z, yy - x, zz - y, xx - 1 - z, yy - x, zz - y, block.SAND.id)

def findAllFile():
    base = 'D:\\User\\Administrator\\Documents\\GitHub\\MC_BadAppleDGDH\\Python\\bin\\.temp\\BadApple'
    rE = ''
    for root, ds, fs in os.walk(base):
        for f in fs:
            for i in f:
                rE += i
            rE += "\n"
    return rE

def runLoop(file, z, a):
    img = numpy.array(Image.open(file))
    numX, numY, null = img.shape
    imgout = img
    for i in range(numX):
        for j in range(numY):
            if img[i, j][0] >= 255:
                imgout[int(i / 13), int(j / 13)] = 255
            else:
                imgout[int(i / 13), int(j / 13)] = 0
    for i in range(int(numX / 13)):
        for j in range(int(numY / 13)):
            if imgout[i, j][0] == 0:
                if a == 0:
                    setBlock(i, j, z)
                if a == 1:
                    setBlockG(i, j, z)
                if a == 2:
                    setBlockS(i, j, z)
mc = minecraft.Minecraft.create()
pos = mc.player.getPos()
xx = pos.x
yy = pos.y
zz = pos.z
keyboard = Controller()
allFile = findAllFile()
i = 0
c = 0
while True:
    if i + 10 > len(allFile):
        break
    try:
        time.sleep(0.5)
        f = open('D:\\User\\Administrator\\Documents\\GitHub\\MC_BadAppleDGDH\\Python\\bin\\.temp\\BadApple\\' + allFile[i:i + 9], 'rb')
        runLoop(f, c, 0)
        if c != 0:
            runLoop(ff, c - 1, 1)
        c += 1
        ff = f
        time.sleep(0.5)
        f = open('D:\\User\\Administrator\\Documents\\GitHub\\MC_BadAppleDGDH\\Python\\bin\\.temp\\BadApple\\' + allFile[i:i + 9], 'rb')
        runLoop(f, c, 0)
        runLoop(ff, c - 1, 1)
        ff = f
        i += 10
        c += 1
    finally:
        print()
