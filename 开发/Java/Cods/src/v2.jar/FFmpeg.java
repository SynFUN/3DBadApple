/*
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis ��Ʒ��
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
 * ����Ҫ�ȴӡ�MC_BadAppleDGDH\Java\InstallFirst\ffmpeg-4.4�����ffmpeg��path������������������ffmpeg��cmd�ӿڡ�
 * https://blog.csdn.net/Chanssl/article/details/83050959
 * -----------------------------------------------
 * ������Ĳ������룬��������ر������õ���ΪGBK����
 * >
 */

import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * ���ฺ�����FFmpeg���ߴ�����Ƶ�ļ�
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 * @apiNote add FFmpeg to path environment variable
 *
 * @see "���õĲ����ͷ���"
 * @see GetPath#pathBinFolder()
 * @see GetPath#pathChooseMP4()
 * @see Rename#Rename(String)
 * @see Rename#newName()
 * @see Rename#getNowFilePath()
 * @see Rename#restoreName()
 * @see EditPath#EditPath(String)
 * @see EditPath#getVideoPath()
 * @see EditPath#getFolderPath()
 */
public class FFmpeg {

    /**
     * �����ڹ����� �Ὣ�ļ����Ϸ��� ���ָ�ԭ�ļ���
     *
     * @see "���޸�"
     * @see #FFmpeg()
     */
    public final Rename video;

    /**
     * �����ڹ������ͷ��� �����ƶ��Ϸ������ƺ���ļ���Bin ����Bin�´���ͬ�����ļ���
     *
     * @see "���޸�"
     * @see #FFmpeg()
     */
    public final EditPath editPath;

    /**
     * ���ڲ�׽ffs.bat���ɵ�log�е���Ƶ�Ŀ��
     *
     * @see "���޸�"
     * @see #FFmpeg()
     */
    private int widthInt;

    /**
     * ���ڲ�׽ffs.bat���ɵ�log�е���Ƶ�ĸ߶�
     *
     * @see "���޸�"
     * @see #FFmpeg()
     */
    private int heightInt;

    /**
     * ���ڲ�׽ffs.bat���ɵ�log�е���Ƶ�ı����ʽ
     *
     * @see "���޸�"
     * @see #FFmpeg()
     */
    private String codecName;

    private int MMJQ;

    /**
     * �����˶��������·��ѡ���� ����ѡ��Ƶ�ļ��������Ƽ���ת��
     *
     * @see "���õĲ����ͷ���"
     * @see GetPath#pathChooseMP4()
     * @see Rename#Rename(String)
     * @see Rename#newName()
     * @see Rename#getNowFilePath()
     * @see Rename#restoreName()
     * @see EditPath#EditPath(String)
     */
    public FFmpeg() {
        // ��ʼ��Rename����
        video = new Rename(GetPath.pathChooseMP4());
        // ԭ��Ƶ�ļ����ƺϷ���
        video.newName();
        // ��ʼ��VideoFolder����
        editPath = new EditPath(video.getNowFilePath());
        // ����ͬ���ļ��е�Bin ���ƺϷ������Ƶ���Ƶ�����ɵ��ļ���
        editPath.newFileFolder(GetPath.pathMainFile() + "\\");
        // ��ԭԭλ��ԭ�ļ�������
        video.restoreName();
        // ��ʼ����������
        widthInt = -1;
        heightInt = -1;
        codecName = "";

        // ��ȡset.txt
        try {
            // �����洢�����������ļ��ڵ�����
            ArrayList<String> allInLog = new ArrayList<>();
            try {
                // ��[v.log]��һ��File����
                File log = new File(GetPath.pathMainFile() + "\\.minecraft\\set.txt");
                // ����һ��read buffer���󣨽���һ�����������󣨽���File����������File���󣩣���
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(log)));
                // ����һ���ַ������������ļ���ÿһ�� (D)-C
                String line = bufferedReader.readLine();
                // �����ļ�
                while (line != null) {
                    // ����һ������
                    line = bufferedReader.readLine();
                    allInLog.add(line);
                }
            } catch (Exception e) {
                // Error���޷������Ķ�ȡv.log
                System.out.print("# Error CannotReadFile[set.txt]=[FFmpeg]=A=");
                e.printStackTrace();
            }
            try {
                // �ӱ��������������л�ȡcodecName
                int MMJQIndex = allInLog.toString().indexOf("(01~99)");
                String MMJQString = allInLog.toString().substring(MMJQIndex + 9, MMJQIndex + 11);
                MMJQ = Integer.parseInt(MMJQString);
            } catch (Exception e) {
                System.out.println("@ ��ȡ[.minecraft\\set.txt]ʧ�ܣ�ʹ��Ĭ������");
                MMJQ = 8;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter
     *
     * @return �����Ӧ����Ƶ�Ŀ��
     *
     * @see "���ȱ�����"
     * @see #batFFS()
     * @see "���õĲ���"
     * @see #widthInt
     */
    public int getWidthInt() { return widthInt; }

    /**
     * Getter
     *
     * @return �����Ӧ����Ƶ�ĸ߶�
     *
     * @see "���ȱ�����"
     * @see #batFFS()
     * @see "���õĲ���"
     * @see #heightInt
     */
    public int getHeightInt() { return heightInt; }

    /**
     * Getter
     *
     * @return �����Ӧ����Ƶ�ı����ʽ
     *
     * @see "���ȱ�����"
     * @see #batFFS()
     * @see "���õĲ���"
     * @see #codecName
     */
    public String getCodecName() { return codecName; }

    /**
     * �˷����������Ӧ����Ƶ��ffmpeg����д��ffs.bat�ļ� ffmpeg���빦��Ϊ����log�ļ���������ļ�����Ϣ
     *
     * @return ���������쳣ʱ����false
     *
     * @see "���õĲ����ͷ���"
     * @see GetPath#pathBinFolder()
     * @see EditPath#getVideoPath()
     */
    public boolean batFFS() {
        // �˷������༭��bat�ļ���·��
        String batPath = GetPath.pathBinFolder() + "\\ffs.bat";
        // ׼��Ҫ����ffs.bat��cmd����
        String cmd = "ffprobe -select_streams v -show_entries format=size -show_streams -v quiet -of csv=\"p=0\" -of json -i " + editPath.getVideoPath() + " > " + GetPath.pathBinFolder() + "\\v.log";
        // �������ffs.bat
        try {
            File bat = new File(GetPath.pathBinFolder() + "\\ffs.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error���޷������ı༭ffs.bat
            System.out.print("# Error CannotEditFile[ffs.bat]=[FFmpeg.batFFS()]=A=");
            e.printStackTrace();
            return false;
        }
        // ����ffs.bat
        try {
            Desktop.getDesktop().open(new File(batPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // ��ȡffs.bat���ɵ�v.log
        try {
            // �����洢�����������ļ��ڵ�����
            ArrayList<String> allInLog = new ArrayList<>();
            try {
                // ��[v.log]��һ��File����
                File log = new File(GetPath.pathBinFolder() + "\\v.log");
                // ����һ��read buffer���󣨽���һ�����������󣨽���File����������File���󣩣���
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(log)));
                // ����һ���ַ������������ļ���ÿһ�� (D)-C
                String line = bufferedReader.readLine();
                // �����ļ�
                while (line != null) {
                    // ����һ������
                    line = bufferedReader.readLine();
                    allInLog.add(line);
                }
            } catch (Exception e) {
                // Error���޷������Ķ�ȡv.log
                System.out.print("# Error CannotReadFile[v.log]=[FFmpeg.batFFS()]=B=");
                e.printStackTrace();
            }
            // �ӱ��������������л�ȡcodecName
            int codecNameIndex = allInLog.toString().indexOf("\"codec_name\": ");
            String codecNameString = allInLog.toString().substring(codecNameIndex, codecNameIndex + 20);
            codecNameString = codecNameString.replaceAll("\"codec_name\": ", "");
            codecNameString = codecNameString.replaceAll(",", "");
            codecNameString = codecNameString.replaceAll(" ", "");
            codecNameString = codecNameString.replaceAll("\"", "");
            codecName = codecNameString;
            // �ӱ��������������л�ȡwidth
            int widthIndex = allInLog.toString().indexOf("\"width\": ");
            String widthString = allInLog.toString().substring(widthIndex, widthIndex + 20);
            widthString = widthString.replaceAll("\"width\": ", "");
            widthString = widthString.replaceAll(",", "");
            widthString = widthString.replaceAll(" ", "");
            widthInt = new Integer(widthString);
            // �ӱ��������������л�ȡheight
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
     * �˷����������Ӧ����Ƶ��ffmpeg����д��ffr.bat�ļ� ffmpeg���빦��Ϊ��������Ƶ��Ƭ��ͼƬ
     *
     * @return ���������쳣ʱ����false
     *
     * @see "���õĲ����ͷ���"
     * @see GetPath#pathBinFolder()
     * @see EditPath#getVideoPath()
     * @see EditPath#getFolderPath()
     */
    public boolean batFFR() {
        // �˷������༭��bat�ļ���·��
        String batPath = GetPath.pathBinFolder() + "\\ffr.bat";
        // ׼��Ҫ����ffr.bat��cmd���� ��-r 8"��Ϊÿ������8֡ͼƬ "explorer.exe + videoFolder.getFolderPath()"��Ϊֱ�Ӵ�Ŀ���ļ���
        String cmd = "ffmpeg -i " + editPath.getVideoPath() + " -r " + MMJQ + " " + editPath.getFolderPath() + "\\%%05d.png";
        // �������ffr.bat
        try {
            File bat = new File(GetPath.pathBinFolder() + "\\ffr.bat");
            BufferedWriter out = new BufferedWriter(new FileWriter(bat));
            out.write(cmd);
            out.flush();
            out.close();
        } catch (Exception e) {
            // Error���޷������ı༭ffs.bat
            System.out.print("# Error CannotEditFile[ffr.bat]=[FFmpeg.batFFS()]=A=");
            e.printStackTrace();
            return false;
        }
        // ����ffr.bat
        try {
            Desktop.getDesktop().open(new File(batPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
