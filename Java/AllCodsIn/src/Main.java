/**
 * @Time : 2021.5.12 08:56
 * @Author : Synthesis 杜品赫
 * @File : Main.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

public class Main {
    public static void main(String[] args) {
        FFmpeg ffmpeg = new FFmpeg();
        if (ffmpeg.editBat() && ffmpeg.runBat(Path.pathBinFolder() + "\\ffs.bat")) {
            System.out.println("@ [v.log] formed succeed");
        } else {
            System.out.println("@ [v.log] formed error");
        }
    }
}
