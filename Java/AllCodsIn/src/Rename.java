/**
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
 *
 * @author Synthesis ��Ʒ�� <https://github.com/SynthesisDu>
 *
 */
public class Rename {

    /**
     * ���ڱ��汻�޸�ǰ���ļ�����
     *
     * @see "���޸�"
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
     * @see #setName()
     * @see #restoreName()
     */
    private String path;

    /**
     * ���ڱ��汻�޸�ǰ���ļ�����
     *
     * @see "���޸�"
     * @see #Rename(String)
     * @see #setName()
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
     */
    public String getOriginName() { return originName; }

    /**
     * Getter
     *
     * @return �ļ���ǰ������
     */
    public String getNowName() { return nowName; }

    public String getPath() { return path; }

    public String getNowFilePath() { return path + "\\" + nowName; }

    public boolean setPath(String s) {
        File f = new File(s + "\\" + nowName);
        if (f.exists()) {
            path = s;
            return true;
        } else {
            return false;
        }
    }

    public void setName() {
        String newName = "";
        System.out.println("@ Check if video name illegal that needs to rename");
        if (nowName.length() > 12 || nowName.contains(" ")) {
            newName = (nowName.replaceAll(" ", "").substring(0, 8) + nowName.substring(nowName.lastIndexOf(".")));
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
                else System.out.println("# Error : OriginFileNameUnacceptableForRename=[Rename.setName()]");
            } else {
                System.out.println("@ Renamed for remove illegal chars and retain 8 chars");
            } nowName = newName;
    }   }

    public void restoreName() {
        if (!nowName.equals(originName)) {
            File oldFile = new File(path + nowName);
            File newFile = new File(path + originName);
            // ���û�������ļ���ִ��
            if (!oldFile.renameTo(newFile)) System.out.println("# Error : NewNameAlreadyBeenUsed=[Rename.restoreName()]=A");
}   }   }
