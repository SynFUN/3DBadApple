/**
 * @Time : 2021.5.11 10:40
 * @Author : Synthesis 杜品赫
 * @File : Jython.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * https://github.com/SynthesisDu/MC_BadAppleDGDH
 * -----------------------------------------------
 * One extra environmental require : Jython.jar
 * -----------------------------------------------
 * You need to add a JAR from [MC_BadAppleDGDH\Java\InstallFirst\jython-2.7.2] to your compiler.
 * 你需要从【MC_BadAppleDGDH\Java\InstallFirst\jython-2.7.2】这个文件夹添加一个JAR到你的编译器。
 */

import org.python.util.PythonInterpreter;
import java.io.*;

public class Jython {
    public static String getPathOfPythonFile() {
        File directory = new File("");
        String path = null;

        try {
            path = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (path == null) {
            path = "Error:CannotGetRightPath(Jython.getPathOfPythonFile())";
        } else {
            int temp = path.length();
            temp -= 4;
            path = path.substring(0,temp);
            path += "Python";
        }
        return path;
    }
    public static void runPythonMcpi() {
        String fileName = getPathOfPythonFile() + "\\Mcpi.py";
        PythonInterpreter pyFile = new PythonInterpreter();
        pyFile.execfile(fileName);
    }
    public static void runPythonImgToArray() {
        String fileName = getPathOfPythonFile() + "\\ImgToArray.py";
        PythonInterpreter pyFile = new PythonInterpreter();
        pyFile.execfile(fileName);
    }
}