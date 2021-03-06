/*
 * @Time : 2021.5.11 10:40
 * @Author : Synthesis 杜品赫
 * @File : Jython.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 * <
 * -----------------------------------------------
 * One extra environmental require : Jython.jar
 * -----------------------------------------------
 * You need to add a JAR from [MC_BadAppleDGDH\Java\InstallFirst\jython-2.7.2] to your compiler.
 * 你需要从【MC_BadAppleDGDH\Java\InstallFirst\jython-2.7.2】这个文件夹添加一个JAR到你的编译器。
 * >
 */

import org.python.util.PythonInterpreter;

/**
 * 此类负责运行Python文件
 *
 * @author Synthesis 杜品赫 <https://github.com/SynthesisDu>
 *
 * @apiNote Jython.jar
 *
 * @see GetPath#pathPythonFile()
 */
public class Jython {

    /**
     * 此方法将运行项目的另一路径中的Python文件：Python\Mcpi.py
     * <This method will run the Python file in another path: Python\McPi.py>
     *
     * @see GetPath#pathPythonFile()
     */
    public static void runPythonMcpi() {
        String fileName = GetPath.pathPythonFile() + "\\Mcpi.py";
        PythonInterpreter pyFile = new PythonInterpreter();
        pyFile.execfile(fileName);
    }

    /**
     * 此方法将运行项目的另一路径中的Python文件：Python\ImgToArray.py
     *
     * @see GetPath#pathPythonFile()
     */
    public static void runPythonImgToArray() {
        String fileName = GetPath.pathPythonFile() + "\\ImgToArray.py";
        PythonInterpreter pyFile = new PythonInterpreter();
        pyFile.execfile(fileName);
}   }