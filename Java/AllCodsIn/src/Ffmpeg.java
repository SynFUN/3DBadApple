/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : Ffmpeg.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0_101
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 * -----------------------------------------------
 * windows only
 * One extra environmental require : ffmpeg/bin
 *//*
    You need to install put ffmpeg into the path environment variables from [MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4] first, this cod will use it's cmd api.
    你需要先从【MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4】添加ffmpeg到path环境变量，此类依靠ffmpeg的cmd接口。
    https://blog.csdn.net/Chanssl/article/details/83050959
 */

import java.io.*;
import java.sql.Time;
import java.util.*;
import javax.swing.*;


public class Ffmpeg {
    // ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv="p=0" -of json -i C:\Users\Admin\Documents\GitHub\BadApple.mp4
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFileChooser fd = new JFileChooser();
        //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fd.showOpenDialog(null);
        File f = fd.getSelectedFile();
        if(f != null){}
    }
}
