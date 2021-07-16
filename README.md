# 【Minecraft】把BadApple三维展开（附程序+整合包）

[![gh.png](https://i.postimg.cc/x1SLbdcH/gh.png)](https://www.bilibili.com/video/BV1Ww411o77g)


[整合包 （视频含50秒小白教学)](https://github.com/SynthesisDu/MC_BadAppleDGDH/releases/tag/v1.0)

[借物 (地图存档) Azuma no Niwa](https://www.planetminecraft.com/project/garden-of-the-east-3583394/)

(可惜车万主题的地图大小都不太合适，所以最后用了这个东亚风的景观图。)


使用工具：FFmpeg，Minecraft_Mcpi模组，Minecraft_Replay模组

代码：Java+Python+dos


一直想试试这种拖影的视频是什么效果。这次实现了。

有很多废案，如键盘精灵输指令、预设命令方块、修改区块文件等。

最后用的mcpi模组实现的，这个模组是Forge到Python的api转接。Python很好写但是比较吃性能。

JVM爆了好几次内存，后来给Python加了调整了休眠时间，JVM才能定期回收。

很遗憾受限于JVM的性能和Replay模组的性能，只能不带光影用7%的分辨率生成这么一个依然掉帧的成品。

尽管如此，这也是几十次导出失败了。真的累死个人了。


# 以下附整合包使用相关说明（v2.0）

本整合包出于~~闲的蛋疼~~为图一乐群众的考虑，打包了一个甚至不需要玩过Java版MC就能一次性成功启动的整合包。理解下面1和2的说明可以帮助你更好的使用这个整合包。请注意操作系统建议是高于1909的win10，其他Windows版本随缘。

<h2>目录（介绍的是和正常整合包的不同）</h2>

[1. 操作指引](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%5Bv2.0-Release%5D%20%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#1-%E6%93%8D%E4%BD%9C%E6%8C%87%E5%BC%95)

[2. 参数修改文件set.txt](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%5Bv2.0-Release%5D%20%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#2-%E5%8F%82%E6%95%B0%E4%BF%AE%E6%94%B9%E6%96%87%E4%BB%B6settxt)

[3. 自带环境及特殊文件](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%5Bv2.0-Release%5D%20%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#3-%E8%87%AA%E5%B8%A6%E7%8E%AF%E5%A2%83%E5%8F%8A%E7%89%B9%E6%AE%8A%E6%96%87%E4%BB%B6)

## 1. 操作指引

解压后你会看到四个文件：

[![20210716144013.png](https://i.postimg.cc/qqM3pbMM/20210716144013.png)](https://postimg.cc/62g30hvD)

后面的可以不看了。

## 2. 参数修改文件set.txt

在【.minecraft】文件夹内你可以找到这个【set.txt】文件。它就长这个样子：

[![2021-06-22-17-34-58.png](https://i.postimg.cc/zvQdyy2c/2021-06-22-17-34-58.png)](https://postimg.cc/kRxNZgmQ)

里面每个>>后的数值就是可以自由调整的参数。请注意：

【每秒截取的帧数 (01~99)>>】必须是两位数。

【每张图像素到方块的缩放>>】是指原图在转换成方块时缩小的倍数，如果填1则1080*720的图片在游戏内也会生成同样长宽的方块。默认是13，算是比较合适的大小，正常视野内也可以显示到。

【每个图像的刷新间隔秒数>>】会影响到游戏内视频向前推进的速度。过快的话由于jvm内存回收的机制问题可能造成游戏爆内存崩溃。请适当的延长这个等待时间。当然过长的话就会使等待变得漫长且痛苦。

【游戏内的显像方块的块值>>】和【游戏内的拖影方块的块值>>】是定义产生的图像柱子的方块的类型的。具体每个方块与数值的对应关系可以查阅[方块ID索引](方块ID索引.md)这个文档。受几年前别人开发的这个模组的限制只有这些个方块。未来如果有机会做彩色版（八成是不会做的），我可能会想办法添加一些方块。

修改这些参数前建议先备份一个这个文档防止不小心改错。当然如果出错了程序会自动忽略并使用默认设置。备份主要是为了再多几次试的机会。

## 3. 自带环境及特殊文件

以下会注明整合包里外挂的一些文件。将这里注明的所有文件搬到别的【.minecraft】里去也可以正常运作。

[![5.png](https://i.postimg.cc/nV10jcPz/5.png)](https://postimg.cc/14fpbPPh)

# 次要代码：jar包

【.minecraft\.java】路径下有个jar包，依靠.bat脚本运行，作用是调用FFmpeg和文件选择器，实现自动转换视频。并整理和移动图片。

# 核心代码：mcpi脚本

```python
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
    i = 0
    c = 0
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
```
