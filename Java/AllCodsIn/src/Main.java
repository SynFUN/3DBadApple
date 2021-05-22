import java.io.File;
import java.util.ArrayList;

/**
 * @Time : 2021.5.12 08:56
 * @Author : Synthesis 杜品赫
 * @File : Main.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

public class Main {
    public static void main(String[] args) {
//        FFmpeg ffs = new FFmpeg();
//        if (ffs.batFFS()) {
//            System.out.println("@ [v.log] formed succeed");
//            System.out.println("@ width : " + ffs.getWidthInt());
//            System.out.println("@ height : " + ffs.getHeightInt());
//        } else { System.out.println("# Error : [v.log]FormedError"); }
//        if (ffs.batFFR()) {
//            System.out.println("@ MP4 to PNG formed succeed");
//        } else { System.out.println("# Error : FFmpeg.batFFR()"); }
        Image image = new PolyImage(new File(Path.pathChoosePNG()));
        System.out.println(image.toString());
    }
}
