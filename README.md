# 【Minecraft】玄学渲染，BadApple顺利起飞

[![2021-06-22-17-34-58.png](https://i.postimg.cc/zGRsFKR5/2021-06-22-17-34-58.png)](https://www.bilibili.com/video/BV1Ww411o77g/)

各种视频都可以用此程序实现同样效果。↓整合包下载链接，闲者可以试试。
[整合包 （视频含50秒小白教学)](https://github.com/SynthesisDu/MC_BadAppleDGDH/releases/tag/v1.0)
[借物 (地图存档) Azuma no Niwa](https://www.planetminecraft.com/project/garden-of-the-east-3583394/)
可惜车万主题的地图大小都不太合适，所以最后用了这个东亚风的景观图。

使用工具：FFmpeg，Minecraft_Mcpi模组，Minecraft_Replay模组
代码：Java+Python+dos

一直想试试这种拖影的视频是什么效果。这次实现了。
有很多废案，如键盘精灵输指令、预设命令方块、修改区块文件等。
最后用的mcpi模组实现的，这个模组是Forge到Python的api转接。Python很好写但是比较吃性能。
JVM爆了好几次内存，后来给Python加了调整了休眠时间，JVM才能定期回收。

很遗憾受限于JVM的性能和Replay模组的性能，只能不带光影用7%的分辨率生成这么一个依然掉帧的成品。
尽管如此，这也是几十次导出失败了。真的累死个人了。
