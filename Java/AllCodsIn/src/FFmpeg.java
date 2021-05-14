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

import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * 此类负责操作FFmpeg工具处理视频文件
 *
 * @author Synthesis 杜品赫 <https://github.com/SynthesisDu>
 *
 * @apiNote add FFmpeg to path environment variable
 */
public class FFmpeg {

    public Rename video;

    public boolean editBat() {
        video = new Rename(Path.pathChooseVideo());
        video.setName();
        // 准备要存入ffs.bat的cmd命令
        String cmd = "ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv=\"p=0\" -of json -i " + video.getNowFilePath() + " > " + Path.pathBinFolder() + "\\v.log";
        // 存入命令道ffs.bat
        try {
            File bat = new File(Path.pathBinFolder() + "\\ffs.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close();
        } catch (Exception e) {
            // Error：无法正常的编辑ffs.bat
            System.out.print("# Error:CannotEditFile[ffs.bat]=");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean runBat(String batPath) {
        try {
            Desktop.getDesktop().open(new File(video.getNowFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void readLog() {
        try {
            File log = new File(Path.pathBinFolder() + "\\v.log");
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(log)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = bufferedReader.readLine();
            ArrayList<String> allInLog = new ArrayList<>();
            while (line != null) {
                line = bufferedReader.readLine(); // 一次读入一行数据
                allInLog.add(line);
            }
            System.out.println(allInLog);
        } catch (Exception e) {
            // Error：无法正常的读取v.log
            System.out.print("# Error:CannotReadFile[v.log]=");
            e.printStackTrace();
        }
    }
}
