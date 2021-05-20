/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : FFmpeg.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 * <
 * -----------------------------------------------
 * windows only
 * One extra environmental require : ffmpeg/bin
 * -----------------------------------------------
 * You need to install put ffmpeg into the path environment variables from [MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4] first, this cod will use it's cmd api.
 * 你需要先从【MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4】添加ffmpeg到path环境变量，此类依靠ffmpeg的cmd接口。
 * https://blog.csdn.net/Chanssl/article/details/83050959
 * -----------------------------------------------
 * 如果中文产生乱码，编译器相关编码设置调整为GBK即可
 * >
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
    // 此对象作用于构造器 会将文件名合法化 并恢复原文件名
    public final Rename video;
    // 此对象作用于构造器和方法 用于移动合法化名称后的文件到Bin 并在Bin下创建同名字文件夹
    public final VideoFolder videoFolder;
    // 作用于readLog() 用于捕捉ffs.bat生成的log中的视频的长宽高以及编码
    private int widthInt;
    private int heightInt;
    private String codecName;
    // 作用于batFFS()batFFR()和runBat()之间 会存储刚编辑过的bat文件的路径 并使runBat()运行刚编辑过的bat
    private String batPath;

    /**
     * 创建此对象后将引出路径选择器 对所选视频文件进行名称检查和转移
     */
    public FFmpeg() {
        // 初始化Rename对象
        video = new Rename(Path.pathChooseVideo());
        // 原视频文件名称合法化
        video.setName();
        // 初始化VideoFolder对象
        videoFolder = new VideoFolder(video.getNowFilePath());
        // 生成同名文件夹到Bin 复制合法化名称的视频到生成的文件夹
        videoFolder.setNewFileFolder();
        // 还原原位的原文件的名称
        video.restoreName();
        // 初始化其他变量
        widthInt = -1;
        heightInt = -1;
        codecName = "";
        batPath = "";
    }
    /**
     * Getter
     *
     * @return 对象对应的视频的宽度
     *
     * @see #readLog()
     */
    public int getWidthInt() { return widthInt; }
    /**
     * Getter
     *
     * @return 对象对应的视频的高度
     *
     * @see #readLog()
     */
    public int getHeightInt() { return heightInt; }
    /**
     * Getter
     *
     * @return 对象对应的视频的编码格式
     *
     * @see #readLog()
     */
    public String getCodecName() { return codecName; }

    /**
     * 此方法将对象对应的视频的ffmpeg代码写入ffs.bat文件 代码功能为生成log文件保存对象文件的相关信息
     *
     * @return 仅当出现异常时返回false
     */
    public boolean batFFS() {
        batPath = Path.pathBinFolder() + "\\ffs.bat";
        // 准备要存入ffs.bat的cmd命令
        String cmd = "ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv=\"p=0\" -of json -i " + videoFolder.getVideoPath() + " > " + Path.pathBinFolder() + "\\v.log";
        // 存入命令道ffs.bat
        try {
            File bat = new File(Path.pathBinFolder() + "\\ffs.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error：无法正常的编辑ffs.bat
            System.out.print("# Error : CannotEditFile[ffs.bat]=");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean batFFR() {
        batPath = Path.pathBinFolder() + "\\ffr.bat";
        // 准备要存入ffr.bat的cmd命令
        String cmd = "ffmpeg -i " + videoFolder.getVideoPath() + " -r 8 " + videoFolder.getFolderPath() + "\\%%05d.png";
        // 存入命令道ffr.bat
        try {
            File bat = new File(Path.pathBinFolder() + "\\ffr.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error：无法正常的编辑ffs.bat
            System.out.print("# Error : CannotEditFile[ffr.bat]=");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void readLog() {
        // 用来存储遍历的所有文件内的内容
        ArrayList<String> allInLog = new ArrayList<>();
        try {
            // 打开[v.log]到一个File对象
            File log = new File(Path.pathBinFolder() + "\\v.log");
            // 建立一个read buffer对象（建立一个输入流对象（建立File输入流对象（File对象）））
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(log)));
            // 建立一个字符串用来遍历文件的每一行 (D)-C
            String line = bufferedReader.readLine();
            // 遍历文件
            while (line != null) {
                // 读入一行数据
                line = bufferedReader.readLine();
                allInLog.add(line);
            }
        } catch (Exception e) {
            // Error：无法正常的读取v.log
            System.out.print("# Error : CannotReadFile[v.log]=");
            e.printStackTrace();
        }
        // 从遍历的所有内容中获取codecName
        int codecNameIndex = allInLog.toString().indexOf("\"codec_name\": ");
        String codecNameString = allInLog.toString().substring(codecNameIndex, codecNameIndex + 20);
        codecNameString = codecNameString.replaceAll("\"codec_name\": ", "");
        codecNameString = codecNameString.replaceAll(",", "");
        codecNameString = codecNameString.replaceAll(" ", "");
        codecNameString = codecNameString.replaceAll("\"", "");
        codecName = codecNameString;
        // 从遍历的所有内容中获取width
        int widthIndex = allInLog.toString().indexOf("\"width\": ");
        String widthString = allInLog.toString().substring(widthIndex, widthIndex + 20);
        widthString = widthString.replaceAll("\"width\": ", "");
        widthString = widthString.replaceAll(",", "");
        widthString = widthString.replaceAll(" ", "");
        widthInt = new Integer(widthString);
        // 从遍历的所有内容中获取height
        int heightIndex = allInLog.toString().indexOf("\"height\": ");
        String heightString = allInLog.toString().substring(heightIndex, heightIndex + 20);
        heightString = heightString.replace("\"height\": ", "");
        heightString = heightString.replaceAll(",", "");
        heightString = heightString.replaceAll(" ", "");
        heightInt = new Integer(heightString);
    }

    public boolean runBat() {
        try {
            Desktop.getDesktop().open(new File(batPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
