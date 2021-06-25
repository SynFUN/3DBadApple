# 【Minecraft】玄学渲染，BadApple顺利起飞

[![2021-06-22-17-34-58.png](https://i.postimg.cc/zGRsFKR5/2021-06-22-17-34-58.png)](https://www.bilibili.com/video/BV1Ww411o77g/)


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


# 以下附整合包使用相关说明

本整合包出于~~闲的蛋疼~~为图一乐群众的考虑，打包了一个甚至不需要玩过Java版MC就能一次性成功启动的整合包。理解下面1和2的说明可以帮助你更好的使用这个整合包。请注意操作系统建议是19年后的win10，其他Windows版本随缘。

<h2>目录（介绍的是和正常整合包的不同）</h2>

[1. 启动器伴用程序.bat](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#1-%E5%90%AF%E5%8A%A8%E5%99%A8%E4%BC%B4%E7%94%A8%E7%A8%8B%E5%BA%8Fbat)

[2. 参数修改文件set.txt](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#2-%E5%8F%82%E6%95%B0%E4%BF%AE%E6%94%B9%E6%96%87%E4%BB%B6settxt)

[3. 自带环境及特殊文件](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E.md#3-%E8%87%AA%E5%B8%A6%E7%8E%AF%E5%A2%83%E5%8F%8A%E7%89%B9%E6%AE%8A%E6%96%87%E4%BB%B6)

## 1. 启动器伴用程序.bat

[![2021-06-22-17-34-59.png](https://i.postimg.cc/76KCM0km/2021-06-22-17-34-59.png)](https://postimg.cc/cg84dK6n)

解压完文件后首先先双击运行一次这个文件。你可以把它理解为就是exe程序（实际上这里只是没有将它打包），用法上是一样的。第一次运行时它会完成环境配置，环境就在【.minecraft】文件夹内，（3）处会详细说明。

之后再运行它就会弹出选择框让你选择MP4文件并生成帧切片文件夹在这个程序所在的同一个地方。当在游戏内使用完这个要生成的视频后，就可以删除生成的图片文件了，当然不删也是可以的。或者就把这个bat程序作为视频转图片的工具也是可以的。但是要注意运行过后就不能移动这个bat和【.minecraft】文件夹的位置了。否则功能会失效。

## 2. 参数修改文件set.txt

在【.minecraft】文件夹内你可以找到这个【set.txt】文件。它就长这个样子：

[![2021-06-22-17-34-58.png](https://i.postimg.cc/zvQdyy2c/2021-06-22-17-34-58.png)](https://postimg.cc/kRxNZgmQ)

里面每个>>后的数值就是可以自由调整的参数。请注意：

【每秒截取的帧数 (01~99)>>】必须是两位数。

【每张图像素到方块的缩放>>】是指原图在转换成方块时缩小的倍数，如果填1则1080*720的图片在游戏内也会生成同样长宽的方块。默认是13，算是比较合适的大小，正常视野内也可以显示到。

【每个图像的刷新间隔秒数>>】会影响到游戏内视频向前推进的速度。过快的话由于jvm内存回收的机制问题可能造成游戏爆内存崩溃。请适当的延长这个等待时间。当然过长的话就会使等待变得漫长且痛苦。

【游戏内的显像方块的块值>>】和【游戏内的拖影方块的块值>>】是定义产生的图像柱子的方块的类型的。具体每个方块与数值的对应关系可以查阅[方块ID索引](https://github.com/SynthesisDu/MC_BadAppleDGDH/blob/main/%E6%95%B4%E5%90%88%E5%8C%85%E4%BD%BF%E7%94%A8%E7%9B%B8%E5%85%B3%E8%AF%B4%E6%98%8E/%E6%96%B9%E5%9D%97ID%E7%B4%A2%E5%BC%95.md)这个文档。受几年前别人开发的这个模组的限制只有这些个方块。未来如果有机会做彩色版（八成是不会做的），我可能会想办法添加一些方块。

修改这些参数前建议先备份一个这个文档防止不小心改错。当然如果出错了程序会自动忽略并使用默认设置。备份主要是为了再多几次试的机会。

## 3. 自带环境及特殊文件

以下会注明整合包里外挂的一些文件。将这里注明的所有文件搬到别的【.minecraft】里去也可以正常运作。具体功能可以查看首页的README.

[![5.png](https://i.postimg.cc/nV10jcPz/5.png)](https://postimg.cc/14fpbPPh)
