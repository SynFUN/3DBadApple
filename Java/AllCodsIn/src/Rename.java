/**
 * @Time : 2021.5.11 10:41
 * @Author : Synthesis 杜品赫
 * @File : Rename.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import java.io.File;

public class Rename {
    private final String originName;
    private String path;
    private String nowName;

    public Rename(String p) {
        originName = new File(p).getName();
        nowName = new File(p).getName();
        path = p.substring(0, p.length() - nowName.length());
    }

    public String getOriginName() { return originName; }

    public String getNowName() { return nowName; }

    public String getPath() { return path; }

    public String getNowFilePath() { return path + "\\" + nowName; }

    public void setName() {
        String newName = "";
        System.out.println("@ Check if video name illegal that needs to rename");
        if (nowName.length() > 12 || nowName.contains(" ")) {
            newName = (nowName.replaceAll(" ", "").substring(0, 8) + ".mp4");
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
