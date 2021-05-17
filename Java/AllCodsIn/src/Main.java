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
        if (ffmpeg.batFFS() && ffmpeg.runBat(Path.pathBinFolder() + "\\ffs.bat")) {
            System.out.println("@ [v.log] formed succeed");
        } else { System.out.println("# Error : [v.log]FormedError"); }
        ffmpeg.readLog();
        System.out.println("@ width : " + ffmpeg.getWidthInt());
        System.out.println("@ height : " + ffmpeg.getHeightInt());
        if (ffmpeg.batFFR() && ffmpeg.runBat(Path.pathBinFolder() + "\\ffr.bat")) {
            System.out.println("@ MP4 to PNG formed succeed");
        } else { System.out.println("# Error : MP4ToPNGFormedError"); }
    }
}
