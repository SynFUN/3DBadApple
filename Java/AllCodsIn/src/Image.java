/**
 * @Time : 2021.5.18 14:30
 * @Author : Synthesis 杜品赫
 * @File : ImgToData.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Image {
    private BufferedImage bufferedImage;
    private FileInputStream fileInputStream;
    private final File file;
    private String pathFile;
    private String pathFolder;
    private String fileName;
    private int width;
    private int height;
    private int[][] valuePixel;
    private boolean[][] blackPixel;
    private ArrayList<Object> all;
    //
    private int[][] pixel;
    //
    private int[][] valueRed;
    private int[][] valueGreen;
    private int[][] valueBlue;

    public Image(File f) {
        file = f;
        all = new ArrayList();
        pathFile = file.getAbsolutePath();
        pathFolder = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));
        fileName = file.getName();
        // 创建指定文件的BufferedImage对象: BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File));
        // Try: fileInputStream = new FileInputStream("路径");
        try { fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) { e.printStackTrace();
            try { fileInputStream.close();
            } catch (IOException ioException) { ioException.printStackTrace(); }
        }
        // Try: bufferedImage = ImageIO.read(fileInputStream);
        try { bufferedImage = ImageIO.read(fileInputStream);
        } catch (IOException e) { e.printStackTrace(); }
        // 用BufferImage获取长宽
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        // 创建与像素对应的数组
        valuePixel = new int[width][height];
        blackPixel = new boolean[width][height];
        // 遍历所有像素
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                valuePixel[x][y] = bufferedImage.getRGB(x, y);
                // 当bufferedImage.getRGB(x, y)的值为-1时，表示当前像素是白色像素
                blackPixel[x][y] = valuePixel[x][y] != -1;
            }
        }
        all.add(bufferedImage);
        all.add(fileInputStream);
        all.add(file);
        all.add(pathFile);
        all.add(pathFolder);
        all.add(fileName);
        all.add(width);
        all.add(height);
        all.add(valuePixel);
        all.add(blackPixel);
    }

    public Image(File f, int a) {
        file = f;
        all = new ArrayList();
        pathFile = file.getAbsolutePath();
        pathFolder = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));
        fileName = file.getName();
        // 创建指定文件的BufferedImage对象: BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File));
        // Try: fileInputStream = new FileInputStream("路径");
        try { fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) { e.printStackTrace();
            try { fileInputStream.close();
            } catch (IOException ioException) { ioException.printStackTrace(); }
        }
        // Try: bufferedImage = ImageIO.read(fileInputStream);
        try { bufferedImage = ImageIO.read(fileInputStream);
        } catch (IOException e) { e.printStackTrace(); }
        // 用BufferImage获取长宽
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        // 创建与像素对应的数组
        valuePixel = new int[width][height];
        blackPixel = new boolean[width][height];
        pixel = new int[width][height];
        valueRed = new int[width][height];
        valueGreen = new int[width][height];
        valueBlue = new int[width][height];
        // 遍历所有像素
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                valuePixel[x][y] = bufferedImage.getRGB(x, y);
                // 当bufferedImage.getRGB(x, y)的值为-1时，表示当前像素是白色像素
                blackPixel[x][y] = valuePixel[x][y] != -1;
                Color c = new Color(valuePixel[x][y]);
                pixel[x][y] = 255;
                valueRed[x][y] = c.getRed();
                valueGreen[x][y] = c.getGreen();
                valueBlue[x][y] = c.getBlue();
            }
        }
        all.add(bufferedImage);     // 0
        all.add(fileInputStream);   // 1
        all.add(file);              // 2
        all.add(pathFile);          // 3
        all.add(pathFolder);        // 4
        all.add(fileName);          // 5
        all.add(width);             // 6
        all.add(height);            // 7
        all.add(valuePixel);        // 8
        all.add(blackPixel);        // 9
        if (a == 1) {
            all.add(pixel);         // 10
            all.add(pixel);         // 11
            all.add(pixel);         // 12
        }
        if (a == 2) {
            all.add(valueRed);      // 10
            all.add(valueGreen);    // 11
            all.add(valueBlue);     // 12
        }
    }

    public BufferedImage getBufferedImage() { return bufferedImage; }

    public FileInputStream getFileInputStream() { return fileInputStream; }

    public File getFile() { return file; }

    public String getPathFile() { return pathFile; }

    public String getPathFolder() { return pathFolder; }

    public String getFileName() { return fileName; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int[][] getValuePixel() { return valuePixel; }

    public boolean[][] getBlackPixel() { return blackPixel; }

    public ArrayList<Object> getAll() { return all; }

    public void setAll(ArrayList<Object> a) { all = a; }

    public int[][] getValueRed() { return valueRed; }

    public int[][] getValueGreen() { return valueGreen; }

    public int[][] getValueBlue() { return valueBlue; }

    public String toString() {
        int size = all.size();
        return Integer.toString(size);
    }
}

class MonoImage extends Image {

    public MonoImage(String path){
        super(new File(path), 1);
    }

    public MonoImage(File f) {
        super(f, 1);
    }

    public String toString() {
        StringBuilder re = new StringBuilder();
        boolean[][] blackPixel = getBlackPixel();
        for (int x = 0; x < blackPixel[0].length; x += 6) {
            for (double y = 0; y < blackPixel.length; y += 3.3) {
                if (blackPixel[(int)y][x]) re.append("#");
                else re.append(" ");
            }
            re.append("\n");
        }
        return re.toString();
    }
}

class PolyImage extends Image {

    public PolyImage(String path) {
        super(new File(path), 2);
    }

    public PolyImage(File f) {
        super(f, 2);
    }

    public String toString() {
        StringBuilder re = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (int x = 0; x < valueRed[0].length; x += 6) {
            for (double y = 0; y < valueRed.length; y += 3.3) {
                if (valueRed[(int)y][x] > valueGreen[(int)y][x] && valueRed[(int)y][x] > valueBlue[(int)y][x]) re.append("R");
                else if (valueGreen[(int)y][x] > valueRed[(int)y][x] && valueGreen[(int)y][x] > valueBlue[(int)y][x]) re.append("G");
                else if (valueBlue[(int)y][x] > valueRed[(int)y][x] && valueBlue[(int)y][x] > valueGreen[(int)y][x]) re.append("B");
                else re.append(" ");
            }
            re.append("\n");
        }
        return re.toString();
    }
}
