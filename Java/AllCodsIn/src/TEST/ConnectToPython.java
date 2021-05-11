/**
 * @Time : 2021.5.11 10:40
 * @Author : Synthesis 杜品赫
 * @File : ConnectToPython
 * @Software : IntelliJ IDEA
 * https://github.com/SynthesisDu
 */
package TEST;

import org.python.util.PythonInterpreter;
import java.io.File;
import java.io.IOException;

public class ConnectToPython {
    public static String getPathOfPythonFile() {
        File directory = new File("");
        String path = null;

        try {
            path = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (path == null) {
            path = "Error:CannotGetRightPath";
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
}