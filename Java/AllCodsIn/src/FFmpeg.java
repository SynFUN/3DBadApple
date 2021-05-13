/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : FFmpeg.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 * -----------------------------------------------
 * windows only
 * One extra environmental require : ffmpeg/bin
 * -----------------------------------------------
 * You need to install put ffmpeg into the path environment variables from [MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4] first, this cod will use it's cmd api.
 * 你需要先从【MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4】添加ffmpeg到path环境变量，此类依靠ffmpeg的cmd接口。
 * https://blog.csdn.net/Chanssl/article/details/83050959
 * -----------------------------------------------
 * 如果中文产生乱码，编译器相关编码设置调整为GBK即可
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.io.*;

/**
 * 此类负责操作FFmpeg工具处理视频文件
 *
 * @author Synthesis 杜品赫
 *
 * @apiNote add FFmpeg to path environment variable
 */
public class FFmpeg {
    // C:\Users\Admin\Documents\GitHub\BadApple.mp4

    public static void main(String[] args) throws IOException, InterruptedException {
        String cmd = "ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv=\"p=0\" -of json -i " + Path.pathChooseVideo() + " > " + Path.pathBinFolder() + "v.log";
        File bat = new File(Path.pathBinFolder() + "v.log");
    }
}

