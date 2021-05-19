import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : VideoFolder.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

public class VideoFolder {
    private File originFile;
    private String folderPath;

    public VideoFolder(String videoPath) {
        originFile = new File(videoPath);
        folderPath = "";
    }

    public File getOriginFile() { return originFile; }

    public String getFolderPath() { return folderPath; }

    public String getVideoPath() { return folderPath + "\\" + originFile.getName(); }

    public void setFileName(String fileName) {
        File in = new File(folderPath + "\\" + fileName);
        originFile = in;
    }

    public void setNewFileFolder() {
        // 路径为项目Bin文件夹下新建originFile的同名文件夹
        String path = Path.pathBinFolder() + "\\.temp\\" + originFile.getName().substring(0, originFile.getName().length() - 4);
        // 创建File对象表示即将创建的文件夹的路径
        File newFolder = new File(path);
        // 判断文件是否已存在 是则进入else
        if (!newFolder.exists()) {
            // 创建语句 创建失败则抛出异常
            if (newFolder.mkdirs()) System.out.println("@ 成功在bin\\.temp创建视频文件夹");
            else System.out.println("# Error : 创建视频文件夹出错=[VideoFolder.setNewFileFolder]=A");
        // 如果已存在文件则添加(1)序号后缀
        } else {
            path += " (1)";
            // i用于提升后缀
            int i = 1;
            // 如果(1)也重复则尝试更高数字后缀
            while (true) {
                // 此对象用于每次尝试
                File test = new File(path);
                // 如果不再重复则完成新建
                if (!test.exists()) {
                    newFolder = test;
                    break;
                    // 依然重复则更改文件后缀
                } else {
                    i++;
                    path = path.substring(0, path.length() - Integer.toString(i - 1).length() - 1) + i + ")";
                }
            }
            // 当不再重复时结束while并创建文件 有异常则抛出
            if (newFolder.mkdirs()) System.out.println("@ 成功在bin\\.temp创建视频文件夹");
            else System.out.println("# Error : 创建视频文件夹出错=[VideoFolder.setNewFileFolder]=B");
        }
        folderPath = newFolder.getAbsolutePath();
        copyVideoFile();
    }

    public void copyVideoFile() {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(originFile.getAbsolutePath()).getChannel();
            outputChannel = new FileOutputStream(folderPath + "\\" + originFile.getName()).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputChannel != null) {
                try {
                    outputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
}   }   }   }
