/*
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : EditPath.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此类将在指定目录下创建一个文件夹用来保存一个指定文件的备份
 *
 * @author Synthesis 杜品赫 <https://github.com/SynthesisDu>
 *
 */
public class EditPath {

    /**
     * 用于标记源文件
     *
     * @see "被创建 be defined"
     * @see #EditPath(String)
     * @see "被使用 be used"
     * @see #getVideoPath()
     */
    private final File originFile;

    /**
     * 用于记录新建的文件夹的路径
     *
     * @see "被修改 be defined"
     * @see #EditPath(String)
     * @see "被使用 be used"
     * @see #getFolderPath()
     * @see #getVideoPath()
     */
    private String folderPath;

    public EditPath(String videoPath) {
        originFile = new File(videoPath);
        folderPath = "";
    }

    /**
     * Getter
     *
     * @return 文件夹的路径
     */
    public String getFolderPath() { return folderPath; }

    /**
     * Getter
     *
     * @return 被复制到创建的文件夹的文件的路径
     */
    public String getVideoPath() { return folderPath + "\\" + originFile.getName(); }

    /**
     * 此方法将在指定路径下创建新的与对象同名的文件夹并复制一个源文件到此文件夹里
     *
     * @see "调用的参数和方法"
     * @see #originFile
     * @see File
     */
    public void newFileFolder(String newFolderPath) {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        folderPath = newFolderPath + df.format(day);
        String path = folderPath;
        // 创建File对象表示即将创建的文件夹的路径
        File newFolder = new File(path);
        // 判断文件是否已存在 是则进入else
        if (!newFolder.exists()) {
            // 创建语句 创建失败则抛出异常
            if (newFolder.mkdirs()) System.out.println("@ 成功在指定路径创建新文件夹");
            else {
                System.out.println("# Error 创建新文件夹出错=[VideoFolder.setNewFileFolder()]=A");
                return;
            }
        }
        copyFile(originFile, newFolder.getAbsolutePath());
    }

    public static void copyFile(File file, String folderPath) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        // 拷贝所选文件到新建的文件夹
        try {
            inputChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
            outputChannel = new FileOutputStream(folderPath + "\\" + file.getName()).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputChannel != null) {
                try { inputChannel.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            if (outputChannel != null) {
                try { outputChannel.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
}
