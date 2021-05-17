/**
 * @Time : 2021.5.13 15:37
 * @Author : Synthesis ��Ʒ��
 * @File : Path.java
 * @Software : IntelliJ IDEA
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

/**
 * ����ר���ṩ����Ŀ������������·���ķ���
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 * @see #pathMainFile()
 * @see #pathPythonFile()
 * @see #pathDesktop()
 * @see #pathChooseVideo()
 * @see #pathBinFolder()
 */
public class Path {
    /**
     * �˷����᷵����Ŀ��һ���ļ��еľ���·��
     * <This method returns the absolute path to the main folder of this repository from the relative path relationship>
     *
     * @return ��Ŀ��һ���ļ��еľ���·��
     *
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
            System.out.print("# Error:File.getCanonicalPath()Error=");
            e.printStackTrace();
        }
        // ��������Ƿ��Ѿ���ȷ���� path�Ƿ񱻸�ֵ
        if (path == null) {
            // Error���޷���ȡ����ȷ��·��
            path = "# Error:CannotGetRightPath(Jython.getPathOfPythonFile())";
        } else {
            path = path.substring(0,path.length()-5);
        }
        return path;
    }
    /**
     * �˷�����������Ŀ��Python�ļ��еľ���·��
     * <This method returns the absolute path to the Python folder>
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
     * <This method returns the absolute path to the src folder of Java>
     *
     * @return ��Ŀ��Java��src�ļ��еľ���·��
     *
     * @see #pathMainFile()
     */
    public static String pathJavaSrcFile() {
        return pathMainFile() + "\\Java\\AllCodsIn\\src";
    }
    /**
     * �˷����ɻ�ȡ�û�������ľ���·��
     * <This method gets the absolute path to the user's desktop>
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
     * <This method brings up a select box specifically for selecting .mp4 files and returns the absolute path of the selected file>
     *
     * @return ѡ�����Ƶ�ļ���·��
     *
     * @see #pathDesktop()
     * @see Rename#Rename(String)
     */
    public static String pathChooseVideo() {
        // �ڴ���ѡ��������ǰ�޸�ѡ���������� ����ѡ������ʼ���̫��
        try {
            // �˾��ڱ��������׳��쳣
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException e) {
            // Error���޷��ı��ļ�ѡ�����ķ��
            System.out.print("# Error : CannotChangeFileChooserStyle=");
            e.printStackTrace();
        }
        // �����ļ�ѡ�������󣨲����趨ѡ������ʼλ�����û����棩
        JFileChooser jFileChooser = new JFileChooser(pathDesktop());
        // ѡ����������ѡ���ļ� �������ļ���
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // ѡ��������ѡ��һ���ļ� �����ܶ�ѡ
        jFileChooser.setMultiSelectionEnabled(false);
        // �趨ѡ����ѡ����ļ����ͣ�FileFilter�ǳ����������Ҫ�̳и�д��
        jFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                // �޶��ļ���׺
                return f.getName().endsWith(".mp4") || f.isDirectory();
            }
            public String getDescription() {
                // ���ļ����͵���������
                return "MP4(*.mp4)";
            }
        });
        // �����ļ�ѡ��������
        jFileChooser.showOpenDialog(null);
        // ��ȡ�ļ�ѡ����ѡ�����ļ�·��
        File file = jFileChooser.getSelectedFile();
        return file.getAbsolutePath();
    }
    /**
     * �˷����᷵����Ŀ��bin�ļ��еľ���·��
     * <This method returns the absolute path of the project's bin folder>
     *
     * @return ��Ŀ��bin�ļ��еľ���·��
     *
     * @see #pathPythonFile()
     */
    public static String pathBinFolder() { return pathPythonFile() + "\\bin"; }
}
