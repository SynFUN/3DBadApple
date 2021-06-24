/*
 * @Time : 2021.5.12 08:56
 * @Author : Synthesis 杜品赫
 * @File : Main.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link : https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static ArrayList<String> getFilesInFolder(String path){
        ArrayList<String> fileNameInFolder = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return fileNameInFolder;
        File[] fa = f.listFiles();
        //获取数组中的第i个
        assert fa != null;
        for (File fs : fa) {
            if (!fs.isDirectory()) fileNameInFolder.add(fs.getName());
        }
        return fileNameInFolder;
    }

    public static void main(String[] args) {
        FFmpeg ffmpeg = new FFmpeg();
        if (ffmpeg.batFFS()) {
            System.out.println("@ [v.log] formed succeed");
            System.out.println("@ width : " + ffmpeg.getWidthInt());
            System.out.println("@ height : " + ffmpeg.getHeightInt());
        } else { System.out.println("# Error : [v.log]FormedError"); }
        if (ffmpeg.batFFR()) {
            System.out.println("@ MP4 to PNG formed succeed");
        } else { System.out.println("# Error : FFmpeg.batFFR()"); }
        while (getFilesInFolder(ffmpeg.editPath.getFolderPath()).size() == 1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                Path path = Paths.get(ffmpeg.editPath.getVideoPath());
                Files.delete(path);
                break;
            } catch (Exception e) { }
        }
        System.out.println("______________________________________________");
        System.out.println("You can close this window when program finish");
    }
}
