import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis ��Ʒ��
 * @File : VideoFolder.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

public class VideoFolder {
    private File originFile;
    private String folderPath;

    public VideoFolder(String videoPath) {
        originFile = new File(videoPath);
        folderPath = "";
    }

    public File getOriginFile() { return originFile; }

    public String getFolderPath() { return folderPath; }

    public String getVideoPath() { return folderPath + "\\" + originFile.getName(); }

    public void setFileName(String fileName) {
        File in = new File(folderPath + "\\" + fileName);
        originFile = in;
    }

    public void setNewFileFolder() {
        // ·��Ϊ��ĿBin�ļ������½�originFile��ͬ���ļ���
        String path = Path.pathBinFolder() + "\\.temp\\" + originFile.getName().substring(0, originFile.getName().length() - 4);
        // ����File�����ʾ�����������ļ��е�·��
        File newFolder = new File(path);
        // �ж��ļ��Ƿ��Ѵ��� �������else
        if (!newFolder.exists()) {
            // ������� ����ʧ�����׳��쳣
            if (newFolder.mkdirs()) System.out.println("@ �ɹ���bin\\.temp������Ƶ�ļ���");
            else System.out.println("# Error : ������Ƶ�ļ��г���=[VideoFolder.setNewFileFolder]=A");
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
            if (newFolder.mkdirs()) System.out.println("@ �ɹ���bin\\.temp������Ƶ�ļ���");
            else System.out.println("# Error : ������Ƶ�ļ��г���=[VideoFolder.setNewFileFolder]=B");
        }
        folderPath = newFolder.getAbsolutePath();
        copyVideoFile();
    }

    public void copyVideoFile() {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(originFile.getAbsolutePath()).getChannel();
            outputChannel = new FileOutputStream(folderPath + "\\" + originFile.getName()).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputChannel != null) {
                try {
                    outputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
}   }   }   }
