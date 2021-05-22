/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis ��Ʒ��
 * @File : VideoFolder.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * ���ཫ��ָ��Ŀ¼�´���һ���ļ�����������һ��ָ���ļ��ı���
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 */
public class VideoFolder {

    /**
     * ���ڱ��Դ�ļ�
     *
     * @see "������ be defined"
     * @see #VideoFolder(String)
     * @see "��ʹ�� be used"
     * @see #getOriginFile()
     * @see #getVideoPath()
     */
    private final File originFile;

    /**
     * ���ڼ�¼�½����ļ��е�·��
     *
     * @see "���޸� be defined"
     * @see #VideoFolder(String)
     * @see "��ʹ�� be used"
     * @see #getFolderPath()
     * @see #getVideoPath()
     */
    private String folderPath;

    public VideoFolder(String videoPath) {
        originFile = new File(videoPath);
        folderPath = "";
    }

    /**
     * Getter
     *
     * @return Դ�ļ�
     */
    public File getOriginFile() { return originFile; }

    /**
     * Getter
     *
     * @return �ļ��е�·��
     */
    public String getFolderPath() { return folderPath; }

    /**
     * Getter
     *
     * @return �����Ƶ��������ļ��е��ļ���·��
     */
    public String getVideoPath() { return folderPath + "\\" + originFile.getName(); }

    /**
     * �˷�������ָ��·���´����µ������ͬ�����ļ��в�����һ��Դ�ļ������ļ�����
     *
     * @see "���õĲ����ͷ���"
     * @see #originFile
     * @see File
     */
    public void newFileFolder(String newFolderPath) {
        folderPath = newFolderPath + originFile.getName().substring(0, originFile.getName().lastIndexOf("."));
        // ·��Ϊ��ĿBin�ļ������½�originFile��ͬ���ļ���
        String path = newFolderPath + originFile.getName().substring(0, originFile.getName().length() - 4);
        // ����File�����ʾ�����������ļ��е�·��
        File newFolder = new File(path);
        // �ж��ļ��Ƿ��Ѵ��� �������else
        if (!newFolder.exists()) {
            // ������� ����ʧ�����׳��쳣
            if (newFolder.mkdirs()) System.out.println("@ �ɹ���ָ��·���������ļ���");
            else {
                System.out.println("# Error �������ļ��г���=[VideoFolder.setNewFileFolder()]=A");
                return;
            }
        // ����Ѵ����ļ������(1)��ź�׺
        } else {
            path += " (1)";
            // i����������׺
            int i = 1;
            // ���(1)Ҳ�ظ����Ը������ֺ�׺
            while (true) {
                // �˶�������ÿ�γ���
                File test = new File(path);
                // ��������ظ�������½�
                if (!test.exists()) {
                    newFolder = test;
                    break;
                    // ��Ȼ�ظ�������ļ���׺
                } else {
                    i++;
                    path = path.substring(0, path.length() - Integer.toString(i - 1).length() - 1) + i + ")";
                }
            }
            // �������ظ�ʱ����while�������ļ� ���쳣���׳�
            if (newFolder.mkdirs()) System.out.println("@ �ɹ���ָ��·���������ļ���");
            else {
                System.out.println("# Error �������ļ��г���=[VideoFolder.setNewFileFolder]=B");
                return;
        }   }
        newFolderPath = newFolder.getAbsolutePath();
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        // ������ѡ�ļ����½����ļ���
        try {
            inputChannel = new FileInputStream(originFile.getAbsolutePath()).getChannel();
            outputChannel = new FileOutputStream(newFolderPath + "\\" + originFile.getName()).getChannel();
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
}   }   }   }
