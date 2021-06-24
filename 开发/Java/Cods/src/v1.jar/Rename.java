/*
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis ��Ʒ��
 * @File : Rename.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.File;

/**
 * ���ཫ�Դ�����ļ��������Ƽ��鲢���Ƿ������ƺϷ��� Ҳ���Խ����޸ĵ��ļ������ƻ�ԭ
 * (���Ϸ�������: ����8���ַ����Ҵ��пո�) ����ѿո��滻Ϊ�»��߲�ɾ������8λ���ַ�
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 */
public class Rename {

    /**
     * ���ڱ��汻�޸�ǰ���ļ�����
     *
     * @see "������"
     * @see #Rename(String)
     */
    private final String originName;

    /**
     * ���ڱ����ļ����ڵ��ļ��е�·��
     *
     * @see "���޸�"
     * @see #Rename(String)
     * @see #setPath(String)
     * @see "��ʹ��"
     * @see #getPath()
     * @see #newName()
     * @see #restoreName()
     */
    private String path;

    /**
     * ���ڱ��汻�޸�ǰ���ļ�����
     *
     * @see "���޸�"
     * @see #Rename(String)
     * @see #newName()
     * @see "��ʹ��"
     * @see #getNowName()
     * @see #getNowFilePath()
     * @see #setPath(String)
     * @see #restoreName()
     */
    private String nowName;

    /**
     * �����˶��󲢻�ö����ļ��Ļ�������
     *
     * @param p ��Ҫ�����������ļ��ľ���·��
     */
    public Rename(String p) {
        originName = new File(p).getName();
        nowName = new File(p).getName();
        path = p.substring(0, p.length() - nowName.length());
    }

    /**
     * Getter
     *
     * @return �ļ�ԭ����
     *
     * @see #originName
     */
    public String getOriginName() { return originName; }

    /**
     * Getter
     *
     * @return �ļ���ǰ������
     *
     * @see #nowName
     */
    public String getNowName() { return nowName; }

    /**
     * Getter
     *
     * @return �ļ����ڵ��ļ��еľ���·��
     *
     * @see #path
     */
    public String getPath() { return path; }

    /**
     * Getter
     *
     * @return �ļ���ǰ�ľ���·��
     *
     * @see #path
     * @see #nowName
     */
    public String getNowFilePath() { return path + "\\" + nowName; }

    /**
     * Setter
     *
     * @param absPath ���¶�λ�ļ������ļ��еľ���·��
     *
     * @return  ��������λ���Ҳ����ļ�ʱ����false
     *
     * @see #nowName
     * @see #path
     */
    public boolean setPath(String absPath) {
        File f = new File(absPath + "\\" + nowName);
        if (f.exists()) {
            path = absPath;
            return true;
        } else {
            return false;
        }
    }

    /**
     * �˷����������ļ��ĺϷ��Բ��޸Ĳ��Ϸ�������
     *
     * @see "���õĲ����ͷ���"
     * @see #path
     * @see #nowName
     */
    public void newName() {
        String newName = "";
        System.out.println("@ Check if video name illegal that needs to rename");
        if (nowName.length() > 12 || nowName.contains(" ")) {
            newName = (nowName.replaceAll(" ", "_").substring(0, 8) + nowName.substring(nowName.lastIndexOf(".")));
            System.out.println("@ Rename video file to became legal");
            System.out.println("@ Path : " + path);
            System.out.println("@ From [" + nowName + "] To [" + newName + "]");
            File oldFile = new File(path + nowName);
            File newFile = new File(path + newName);
            if (!oldFile.renameTo(newFile)) {
                newName = "illegal.mp4";
                oldFile = new File(path + nowName);
                newFile = new File(path + newName);
                if (oldFile.renameTo(newFile)) System.out.println("@ Have to rename the video to illegal.mp4");
                else System.out.println("# Error OriginFileNameUnacceptableForRename=[Rename.newName()]");
            } else {
                System.out.println("@ Renamed for remove illegal chars and retain 8 chars");
            } nowName = newName;
    }   }

    /**
     * �˷�������ԭ���޸ĵ�����
     *
     * @see "���õĲ����ͷ���"
     * @see #path
     * @see #nowName
     * @see #originName
     */
    public void restoreName() {
        if (!nowName.equals(originName)) {
            File oldFile = new File(path + nowName);
            File newFile = new File(path + originName);
            // ���û�������ļ���ִ��
            if (!oldFile.renameTo(newFile)) System.out.println("# Error NewNameAlreadyBeenUsed=[Rename.restoreName()]");
}   }   }
