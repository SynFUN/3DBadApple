/*
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis ��Ʒ��
 * @File : EditPath.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ཫ��ָ��Ŀ¼�´���һ���ļ�����������һ��ָ���ļ��ı���
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 */
public class EditPath {

    /**
     * ���ڱ��Դ�ļ�
     *
     * @see "������ be defined"
     * @see #EditPath(String)
     * @see "��ʹ�� be used"
     * @see #getVideoPath()
     */
    private final File originFile;

    /**
     * ���ڼ�¼�½����ļ��е�·��
     *
     * @see "���޸� be defined"
     * @see #EditPath(String)
     * @see "��ʹ�� be used"
     * @see #getFolderPath()
     * @see #getVideoPath()
     */
    private String folderPath;

    public EditPath(String videoPath) {
        originFile = new File(videoPath);
        folderPath = "";
    }

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
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        folderPath = newFolderPath + df.format(day);
        String path = folderPath;
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
        }
        copyFile(originFile, newFolder.getAbsolutePath());
    }

    public static void copyFile(File file, String folderPath) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        // ������ѡ�ļ����½����ļ���
        try {
            inputChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
            outputChannel = new FileOutputStream(folderPath + "\\" + file.getName()).getChannel();
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
            }
        }
    }
}
