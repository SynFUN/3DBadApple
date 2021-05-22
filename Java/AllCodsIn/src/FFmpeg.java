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
 *
 * @see "调用的参数和方法"
 * @see Path#pathBinFolder()
 * @see Path#pathChooseMP4()
 * @see Rename#Rename(String)
 * @see Rename#newName()
 * @see Rename#getNowFilePath()
 * @see Rename#restoreName()
 * @see VideoFolder#VideoFolder(String)
 * @see VideoFolder#getVideoPath()
 * @see VideoFolder#getFolderPath()
 */
public class FFmpeg {

    /**
     * 作用于构造器 会将文件名合法化 并恢复原文件名
     *
     * @see "被修改"
     * @see #FFmpeg()
     */
    public final Rename video;

    /**
     * 作用于构造器和方法 用于移动合法化名称后的文件到Bin 并在Bin下创建同名字文件夹
     *
     * @see "被修改"
     * @see #FFmpeg()
     */
    public final VideoFolder videoFolder;

    /**
     * 用于捕捉ffs.bat生成的log中的视频的宽度
     *
     * @see "被修改"
     * @see #FFmpeg()
     */
    private int widthInt;

    /**
     * 用于捕捉ffs.bat生成的log中的视频的高度
     *
     * @see "被修改"
     * @see #FFmpeg()
     */
    private int heightInt;

    /**
     * 用于捕捉ffs.bat生成的log中的视频的编码格式
     *
     * @see "被修改"
     * @see #FFmpeg()
     */
    private String codecName;

    /**
     * 创建此对象后将引出路径选择器 对所选视频文件进行名称检查和转移
     *
     * @see "调用的参数和方法"
     * @see Path#pathChooseMP4()
     * @see Rename#Rename(String)
     * @see Rename#newName()
     * @see Rename#getNowFilePath()
     * @see Rename#restoreName()
     * @see VideoFolder#VideoFolder(String)
     */
    public FFmpeg() {
        // 初始化Rename对象
        video = new Rename(Path.pathChooseMP4());
        // 原视频文件名称合法化
        video.newName();
        // 初始化VideoFolder对象
        videoFolder = new VideoFolder(video.getNowFilePath());
        // 生成同名文件夹到Bin 复制合法化名称的视频到生成的文件夹
        videoFolder.newFileFolder(Path.pathBinFolder() + "\\.temp\\");
        // 还原原位的原文件的名称
        video.restoreName();
        // 初始化其他变量
        widthInt = -1;
        heightInt = -1;
        codecName = "";
    }

    /**
     * Getter
     *
     * @return 对象对应的视频的宽度
     *
     * @see "需先被运行"
     * @see #batFFS()
     * @see "调用的参数"
     * @see #widthInt
     */
    public int getWidthInt() { return widthInt; }

    /**
     * Getter
     *
     * @return 对象对应的视频的高度
     *
     * @see "需先被运行"
     * @see #batFFS()
     * @see "调用的参数"
     * @see #heightInt
     */
    public int getHeightInt() { return heightInt; }

    /**
     * Getter
     *
     * @return 对象对应的视频的编码格式
     *
     * @see "需先被运行"
     * @see #batFFS()
     * @see "调用的参数"
     * @see #codecName
     */
    public String getCodecName() { return codecName; }

    /**
     * 此方法将对象对应的视频的ffmpeg代码写入ffs.bat文件 ffmpeg代码功能为生成log文件保存对象文件的信息
     *
     * @return 仅当出现异常时返回false
     *
     * @see "调用的参数和方法"
     * @see Path#pathBinFolder()
     * @see VideoFolder#getVideoPath()
     */
    public boolean batFFS() {
        // 此方法将编辑的bat文件的路径
        String batPath = Path.pathBinFolder() + "\\ffs.bat";
        // 准备要存入ffs.bat的cmd命令
        String cmd = "ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv=\"p=0\" -of json -i " + videoFolder.getVideoPath() + " > " + Path.pathBinFolder() + "\\v.log";
        // 存入命令到ffs.bat
        try {
            File bat = new File(Path.pathBinFolder() + "\\ffs.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error：无法正常的编辑ffs.bat
            System.out.print("# Error CannotEditFile[ffs.bat]=[FFmpeg.batFFS()]=A=");
            e.printStackTrace();
            return false;
        }
        // 运行ffs.bat
        try {
            Desktop.getDesktop().open(new File(batPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 读取ffs.bat生成的v.log
        try {
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
                System.out.print("# Error CannotReadFile[v.log]=[FFmpeg.batFFS()]=B=");
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 此方法将对象对应的视频的ffmpeg代码写入ffr.bat文件 ffmpeg代码功能为将对象视频切片成图片
     *
     * @return 仅当出现异常时返回false
     *
     * @see "调用的参数和方法"
     * @see Path#pathBinFolder()
     * @see VideoFolder#getVideoPath()
     * @see VideoFolder#getFolderPath()
     */
    public boolean batFFR() {
        // 此方法将编辑的bat文件的路径
        String batPath = Path.pathBinFolder() + "\\ffr.bat";
        // 准备要存入ffr.bat的cmd命令 “-r 8"意为每秒生成8帧图片 "explorer.exe + videoFolder.getFolderPath()"意为直接打开目标文件夹
        String cmd = "ffmpeg -i " + videoFolder.getVideoPath() + " -r 8 " + videoFolder.getFolderPath() + "\\%%05d.png\nexplorer.exe " + videoFolder.getFolderPath();
        // 存入命令到ffr.bat
        try {
            File bat = new File(Path.pathBinFolder() + "\\ffr.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error：无法正常的编辑ffs.bat
            System.out.print("# Error CannotEditFile[ffr.bat]=[FFmpeg.batFFS()]=A=");
            e.printStackTrace();
            return false;
        }
        // 运行ffr.bat
        try {
            Desktop.getDesktop().open(new File(batPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
