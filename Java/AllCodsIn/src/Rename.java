/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : Rename.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.File;

/**
 * 此类将对传入的文件进行名称检验并将非法的名称合法化 也可以将被修改的文件的名称还原
 *
 * @author Synthesis 杜品赫 <https://github.com/SynthesisDu>
 *
 */
public class Rename {

    /**
     * 用于保存被修改前的文件名称
     *
     * @see "被修改"
     * @see #Rename(String)
     */
    private final String originName;

    /**
     * 用于保存文件所在的文件夹的路径
     *
     * @see "被修改"
     * @see #Rename(String)
     * @see #setPath(String)
     * @see "被使用"
     * @see #getPath()
     * @see #setName()
     * @see #restoreName()
     */
    private String path;

    /**
     * 用于保存被修改前的文件名称
     *
     * @see "被修改"
     * @see #Rename(String)
     * @see #setName()
     * @see "被使用"
     * @see #getNowName()
     * @see #getNowFilePath()
     * @see #setPath(String)
     * @see #restoreName()
     */
    private String nowName;

    /**
     * 创建此对象并获得对象文件的基本参数
     *
     * @param p 需要被重命名的文件的绝对路径
     */
    public Rename(String p) {
        originName = new File(p).getName();
        nowName = new File(p).getName();
        path = p.substring(0, p.length() - nowName.length());
    }

    /**
     * Getter
     *
     * @return 文件原名称
     */
    public String getOriginName() { return originName; }

    /**
     * Getter
     *
     * @return 文件当前的名称
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
            // 如果没有重名文件就执行
            if (!oldFile.renameTo(newFile)) System.out.println("# Error : NewNameAlreadyBeenUsed=[Rename.restoreName()]=A");
}   }   }
