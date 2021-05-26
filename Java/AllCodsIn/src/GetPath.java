/*
 * @Time : 2021.5.13 15:37
 * @Author : Synthesis ��Ʒ��
 * @File : Path.java
 * @Software : IntelliJ IDEA
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

/**
 * ����ר���ṩ����Ŀ������������·���ķ���
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 */
public class GetPath {

    /**
     * �˷����᷵����Ŀ��һ���ļ��еľ���·��
     *
     * @return ��Ŀ��һ���ļ��еľ���·��
     */
    public static String pathMainFile() {
        // ����ָ�����Ŀ��Java�ļ��е�File����
        File fileFolder = new File("");
        // path���ڴ洢������һ������·��
        String path = null;
        try {
            // �˾��ڱ��������׳��쳣
            path = fileFolder.getCanonicalPath();
        } catch (IOException e) {
            // Error��File���getCanonicalPath()�����쳣
            System.out.print("# Error [Path.pathMainFile()]=A=");
            e.printStackTrace();
        }
        // ��������Ƿ��Ѿ���ȷ���� path�Ƿ񱻸�ֵ
        if (path == null) {
            // Error���޷���ȡ����ȷ��·��
            path = "# Error NoPath=[Path.pathMainFile()]=B";
        } else {
            path = path.substring(0,path.length()-5);
        }
        return path;
    }

    /**
     * �˷�����������Ŀ��Python�ļ��еľ���·��
     *
     * @return ��Ŀ��Python�ļ��еľ���·��
     *
     * @see #pathMainFile()
     */
    public static String pathPythonFile() {
        return pathMainFile() + "\\Python";
    }

    /**
     * �˷�����������Ŀ��Java��src�ļ��еľ���·��
     *
     * @return ��Ŀ��Java��src�ļ��еľ���·��
     *
     * @see #pathMainFile()
     */
    public static String pathJavaSrcFile() {
        return pathMainFile() + "\\Java\\AllCodsIn\\src";
    }

    /**
     * �˷����᷵����Ŀ��bin�ļ��еľ���·��
     *
     * @return ��Ŀ��bin�ļ��еľ���·��
     *
     * @see #pathPythonFile()
     */
    public static String pathBinFolder() { return pathPythonFile() + "\\bin"; }

    /**
     * �˷����ɻ�ȡ�û�������ľ���·��
     *
     * @return �û�������ľ���·��
     */
    public static String pathDesktop() {
        // ��ȡ�û�������·��
        File file = FileSystemView.getFileSystemView().getHomeDirectory();
        return file.getAbsolutePath();
    }

    /**
     * �˷����ᵯ��һ��ר��ѡ��.mp4�ļ���ѡ��򲢷�����ѡ�ļ��ľ���·��
     *
     * @return ѡ�����Ƶ�ļ���·��
     *
     * @see #pathDesktop()
     */
    public static String pathChooseMP4() {
        // �ڴ���ѡ��������ǰ�޸�ѡ���������� ����ѡ������ʼ���̫��
        try {
            // �˾��ڱ��������׳��쳣
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException e) {
            // Error���޷��ı��ļ�ѡ�����ķ��
            System.out.print("# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=A=");
            e.printStackTrace();
        }
        try {
            // �����ļ�ѡ�������󣨲����趨ѡ������ʼλ�����û����棩
            JFileChooser jFileChooser = new JFileChooser(pathDesktop());
            // ѡ����������ѡ���ļ� �������ļ���
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // ѡ��������ѡ��һ���ļ� �����ܶ�ѡ
            jFileChooser.setMultiSelectionEnabled(false);
            // �趨ѡ����ѡ����ļ����ͣ�FileFilter�ǳ����������Ҫ�̳и�д��
            jFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                // �޶��ļ���׺
                public boolean accept(File f) { return f.getName().endsWith(".mp4") || f.isDirectory(); }
                // ���ļ����͵���������
                public String getDescription() { return "MP4(*.mp4)"; }
            });
            // �����ļ�ѡ��������
            jFileChooser.showOpenDialog(null);
            // ��ȡ�ļ�ѡ����ѡ�����ļ�·��
            File file = jFileChooser.getSelectedFile();
            return file.getAbsolutePath();
        } catch (Exception e) { return "# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=B"; }
    }

    /**
     * �˷����ᵯ��һ��ר��ѡ��.png�ļ���ѡ��򲢷�����ѡ�ļ��ľ���·��
     *
     * @return ѡ���ͼƬ�ļ���·��
     *
     * @see #pathDesktop()
     */
    public static String pathChoosePNG() {
        // �ڴ���ѡ��������ǰ�޸�ѡ���������� ����ѡ������ʼ���̫��
        try {
            // �˾��ڱ��������׳��쳣
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException e) {
            // Error���޷��ı��ļ�ѡ�����ķ��
            System.out.print("# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=A=");
            e.printStackTrace();
        }
        try {
            // �����ļ�ѡ�������󣨲����趨ѡ������ʼλ�����û����棩
            JFileChooser jFileChooser = new JFileChooser(pathDesktop());
            // ѡ����������ѡ���ļ� �������ļ���
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // ѡ��������ѡ��һ���ļ� �����ܶ�ѡ
            jFileChooser.setMultiSelectionEnabled(false);
            // �趨ѡ����ѡ����ļ����ͣ�FileFilter�ǳ����������Ҫ�̳и�д��
            jFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                // �޶��ļ���׺
                public boolean accept(File f) { return f.getName().endsWith(".png") || f.isDirectory(); }
                // ���ļ����͵���������
                public String getDescription() { return "PNG(*.png)"; }
            });
            // �����ļ�ѡ��������
            jFileChooser.showOpenDialog(null);
            // ��ȡ�ļ�ѡ����ѡ�����ļ�·��
            File file = jFileChooser.getSelectedFile();
            return file.getAbsolutePath();
        } catch (Exception e) { return "# Error CannotChangeFileChooserStyle=[Path.pathChooseVideo()]=B"; }
    }
}
