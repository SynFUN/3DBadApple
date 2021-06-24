from tkinter import *
from PIL import Image
import numpy
import os
from mcpi import minecraft
import time

def setBlock(x, y, z):
    mc.setBlocks(xx - 1 - z, yy - x, zz - y, xx - 1 - z, yy - x, zz - y, add)

def setBlockG(x, y, z):
    mc.setBlocks(xx - 1 - z, yy - x, zz - y, xx - 1 - z, yy - x, zz - y, aee)


def runLoop(file, z, a):
    img = numpy.array(Image.open(file))
    numX, numY, null = img.shape
    imgout = img
    # 黑白二值化
    for i in range(numX):
        for j in range(numY):
            if img[i, j][0] >= 255:
                imgout[int(i / abb), int(j / abb)] = 255    # 白像素
            else:
                imgout[int(i / abb), int(j / abb)] = 0      # 黑像素
    # 游戏内输出
    for i in range(int(numX / abb)):
        for j in range(int(numY / abb)):
            # 仅当黑像素时产生方块
            if imgout[i, j][0] == 0:
                # a用于计次，首次在这个位置打印时a == 0，第二次相同位置时a == 1
                if a == 0:
                    setBlock(i, j, z)
                # 覆盖已经打印过一次的图像
                if a == 1:
                    setBlockG(i, j, z)

if __name__ == '__main__':
    # 读取[.minecraft\set.txt]
    try:
        abb = 13
        acc = 1
        add = 41
        aee = 20
        data = open(os.getcwd()[:-18] + "\\set.txt", "r")
        aaa = ''
        for i in data.readlines():
            aaa = aaa + i
        data.close()
        ab = aaa.partition("每张图像素到方块的缩放>>")
        abb = ab[2][0:10].rstrip()
        ac = aaa.partition("每个图像的刷新间隔秒数>>")
        acc = ac[2][0:10].rstrip()
        ad = aaa.partition("游戏内的显像方块的块值>>")
        add = ad[2][0:10].rstrip()
        ae = aaa.partition("游戏内的拖影方块的块值>>")
        aee = ae[2][0:10].rstrip()
    except BaseException:
        abb = 13
        acc = 1
        add = 41
        aee = 20
    mc = minecraft.Minecraft.create()
    pos = mc.player.getPos()
    xx = pos.x
    yy = pos.y
    zz = pos.z
    i = 0
    c = 0
    # 找到最新的帧切片所在的文件夹
    lists = os.listdir(os.getcwd()[:-18])
    lists.sort(key=lambda fn: os.path.getmtime(os.getcwd()[:-18] + "\\" + fn))
    folder = os.path.join(os.getcwd()[:-18], lists[-1])
    # 获取文件夹下所有文件
    allFile = ''
    for root, ds, fs in os.walk(folder):
        for f in fs:
            for i in f:
                allFile += i
            allFile += "\n"
    # 遍历文件夹
    while True:
        if i + 10 > len(allFile):
            break
        try:
            time.sleep(acc / 2)
            f = open(folder + '\\' + allFile[i:i + 9], 'rb')
            runLoop(f, c, 0)
            if c != 0:
                runLoop(ff, c - 1, 1)
            c += 1
            ff = f
            time.sleep(acc / 2)
            f = open(folder + '\\' + allFile[i:i + 9], 'rb')
            runLoop(f, c, 0)
            runLoop(ff, c - 1, 1)
            ff = f
            i += 10
            c += 1
        finally:
            print()
