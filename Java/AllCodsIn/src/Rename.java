import java.io.File;

/**
 * @Time : 2021.5.14 08:54
 * @Author : Synthesis 杜品赫
 * @File : Rename
 * @Software : IntelliJ IDEA
 * https://github.com/SynthesisDu
 */

public class Rename {
    private final String originName;
    private final String path;
    private String nowName;

    public Rename(String p) {
        File file = new File(p);
        p = file.getAbsolutePath().replaceAll(file.getName(), "");
        path = p;
        originName = file.getName();
        nowName = file.getName();
    }

    public String getOriginName() { return originName; }

    public String getNowName() { return nowName; }

    public String getPath() { return path; }

    public String getNowFilePath() { return path + nowName; }

    public void setName() {
        String newName = nowName;
        System.out.println("@ Check if video name illegal that needs to rename");
        if (nowName.length() > 12 || nowName.contains(" ")) {
            newName = (nowName.substring(0,8).replaceAll(" ", "") + ".mp4");
            System.out.println("@ Rename video file to became legal");
            System.out.println("@ Path : " + path);
            System.out.println("@ From [" + nowName + "] To [" + newName + "]");
        }
        // 如果新文件名与原来不同就执行
        if (!newName.equals(nowName)) {
            File oldFile = new File(path + nowName);
            File newFile = new File(path + newName);
            System.out.print("@ Renamed : ");
            System.out.println(oldFile.renameTo(newFile));
            nowName = newName;
        } else System.out.println("# Error : NewNameSameWithOldName");
    }

    public void restoreName() {
        if (!nowName.equals(originName)) {
            File file = new File(path);
            String oldName = nowName;
            String newName = originName;
            nowName = originName;
            // 如果新文件名与原来不同就执行
            if (!oldName.equals(newName)) {
                File oldFile = new File(path + oldName);
                File newFile = new File(path + newName);
                if (!oldFile.exists()) {
                    return;//重命名文件不存在
                }
                // 如果没有重名文件就执行
                if (newFile.exists() || !newFile.renameTo(oldFile)) System.out.println("# Error : NewNameAlreadyBeenUsed");
            } else System.out.println("# Error : NewNameSameWithOldName");
        } else System.out.println("# Error : NameHaveNotBeenChanged");
    }
}
