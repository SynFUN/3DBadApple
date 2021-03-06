/*
 * @Time : 2021.5.13 15:37
 * @Author : Synthesis 杜品赫
 * @File : Path.java
 * @Software : IntelliJ IDEA
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

/**
 * 此类专门提供本项目所需的生成相关路径的方法
 *
 * @author Synthesis 杜品赫 <https://github.com/SynthesisDu>
 */
public class GetPath {

    /**
     * 此方法会返回项目的一级文件夹的绝对路径
     *
     * @return 项目的一级文件夹的绝对路径
     */
    public static String pathMainFile() {
        // 创建指向此项目的Java文件夹的File对象
        File fileFolder = new File("");
        // path用于存储并返回一串绝对路径
        String path = null;
        try {
            // 此句在编译器会抛出异常
            path = fileFolder.getCanonicalPath();
        } catch (IOException e) {
            // Error：File类的getCanonicalPath()运行异常
            System.out.print("# Error [Path.pathMainFile()]=A=");
            e.printStackTrace();
        }
        // 检查以上是否已经正确运行 path是否被赋值
        if (path == null) {
            // Error：无法获取到正确的路径
            path = "# Error NoPath=[Path.pathMainFile()]=B";
        } else {
            path = path.substring(0,path.length()-17);
        }
        return path;
    }

    /**
     * 此方法会返回项目的bin文件夹的绝对路径
     *
     * @return 项目的bin文件夹的绝对路径
     *
     * @see #pathMainFile()
     */
    public static String pathBinFolder() { return pathMainFile() + "\\.minecraft\\bin"; }

    /**
     * 此方法可获取用户的桌面的绝对路径
     *
     * @return 用户的桌面的绝对路径
     */
    public static String pathDesktop() {
        // 获取用户的桌面路径
        File file = FileSystemView.getFileSystemView().getHomeDirectory();
        return file.getAbsolutePath();
    }

    /**
     * 此方法会弹出一个专门选择.mp4文件的选择框并返回所选文件的绝对路径
     *
     * @return 选择的视频文件的路径
     *
     * @see #pathDesktop()
     */
    public static String pathChooseMP4() {
        // 在创建选择器对象前修改选择器界面风格 否则选择器初始风格太丑
        try {
            // 此句在编译器会抛出异常
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException e) {
            // Error：无法改变文件选择器的风格
            System.out.print("# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=A=");
            e.printStackTrace();
        }
        try {
            // 创建文件选择器对象（参数设定选择器初始位置在用户桌面）
            JFileChooser jFileChooser = new JFileChooser(pathDesktop());
            // 选择器仅用于选择文件 而不是文件夹
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // 选择器仅能选择一个文件 而不能多选
            jFileChooser.setMultiSelectionEnabled(false);
            // 设定选择器选择的文件类型（FileFilter是抽象类因此需要继承覆写）
            jFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                // 限定文件后缀
                public boolean accept(File f) { return f.getName().endsWith(".mp4") || f.isDirectory(); }
                // 对文件类型的文字描述
                public String getDescription() { return "MP4(*.mp4)"; }
            });
            // 弹出文件选择器窗口
            jFileChooser.showOpenDialog(null);
            // 获取文件选择器选定的文件路径
            File file = jFileChooser.getSelectedFile();
            return file.getAbsolutePath();
        } catch (Exception e) { return "# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=B"; }
    }
}
