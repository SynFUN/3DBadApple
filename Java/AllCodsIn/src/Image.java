/**
 * @Time : 2021.5.18 14:30
 * @Author : Synthesis ��Ʒ��
 * @File : ImgToData.java
 * @Software : IntelliJ IDEA 2020.3.4
 * @JDK : 1.8.0
 * @link :  https://github.com/SynthesisDu/MC_BadAppleDGDH
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Image {
    private BufferedImage bufferedImage;
    private FileInputStream fileInputStream;
    private final File file;
    private final String pathFile;
    private final String pathFolder;
    private final String fileName;
    private final int width;
    private final int height;
    private final int[][] valuePixel;
    private final boolean[][] blackPixel;
    private final ArrayList<Object> all;
    private final int[][] pixel;
    private final int[][] valueRed;
    private final int[][] valueGreen;
    private final int[][] valueBlue;
    private String newPath;

    public Image(File f, int a) {
        file = f;
        all = new ArrayList<Object>();
        pathFile = file.getAbsolutePath();
        pathFolder = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));
        fileName = file.getName();
        newPath = "";
        // ����ָ���ļ���BufferedImage����: BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File));
        // Try: fileInputStream = new FileInputStream("·��");
        try { fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) { e.printStackTrace();
            try { fileInputStream.close();
            } catch (IOException ioException) { ioException.printStackTrace(); }
        }
        // Try: bufferedImage = ImageIO.read(fileInputStream);
        try { bufferedImage = ImageIO.read(fileInputStream);
        } catch (IOException e) { e.printStackTrace(); }
        // ��BufferImage��ȡ����
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        // ���������ض�Ӧ������
        valuePixel = new int[width][height];
        blackPixel = new boolean[width][height];
        pixel = new int[width][height];
        valueRed = new int[width][height];
        valueGreen = new int[width][height];
        valueBlue = new int[width][height];
        // ������������
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                valuePixel[x][y] = bufferedImage.getRGB(x, y);
                // ��bufferedImage.getRGB(x, y)��ֵΪ-1ʱ����ʾ��ǰ�����ǰ�ɫ����
                Color c = new Color(valuePixel[x][y]);
                blackPixel[x][y] = false;
                pixel[x][y] = 255;
                valueRed[x][y] = c.getRed();
                valueGreen[x][y] = c.getGreen();
                valueBlue[x][y] = c.getBlue();
                // ����ɫ����ʱ���� �𵽽���Ч��
                if (c.getRed() + c.getGreen() + c.getBlue() < 5) {
                    blackPixel[x][y] = true;
                    pixel[x][y] = 0;
                    valueRed[x][y] = 0;
                    valueGreen[x][y] = 0;
                    valueBlue[x][y] = 0;
                }
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

    public int[][] getPixel() { return pixel; }

    public int[][] getValueRed() { return valueRed; }

    public int[][] getValueGreen() { return valueGreen; }

    public int[][] getValueBlue() { return valueBlue; }

    /**
     * @return ����all�ĳ���
     */
    public String toString() {
        int size = all.size();
        return Integer.toString(size);
    }

    /**
     * @param asImage ���Ƿ����ַ�ͼ ���򷵻ز���all�ĳ���
     * @return �ַ�ͼ�����all�ĳ���
     */
    public String toString(boolean asImage) {
        if (asImage) {
            StringBuilder re = new StringBuilder();
            for (int x = 0; x < blackPixel[0].length; x += 6) {
                for (double y = 0; y < blackPixel.length; y += 3.3) {
                    if (blackPixel[(int)y][x]) re.append("#");
                    else re.append(" ");
                }
                re.append("\n");
            }
            return re.toString();
        } else {
            int size = all.size();
            return Integer.toString(size);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            Image image = (Image) obj;
            ArrayList<Object> arrayList = image.getAll();
            for (int i = 0; i < 10; i++) {
                if (arrayList.get(i) != all.get(i)) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean toFileFolder(String superFolderPath) {
        newPath = superFolderPath + "\\" + fileName;
        // ����File�����ʾ�����������ļ��е�·��
        File folder = new File(newPath);
        // ����ļ��в����� ���Դ����ļ���
        if (!folder.exists()) return folder.mkdirs();
        else {
            newPath += " (1)";
            // i����������׺
            int i = 1;
            // ���(1)Ҳ�ظ����Ը������ֺ�׺
            while (true) {
                // �˶�������ÿ�γ���
                File test = new File(newPath);
                // ��������ظ�������½�
                if (!test.exists()) {
                    folder = test;
                    break;
                    // ��Ȼ�ظ�������ļ���׺
                } else {
                    i++;
                    newPath = newPath.substring(0, newPath.length() - Integer.toString(i - 1).length() - 1) + i + ")";
                }
            }
            // �������ظ�ʱ����while�����Դ����ļ�
            return folder.mkdirs();
        }
    }

    public void toTXT(String name, String text) {
        try {
            File txt = new File(newPath + "\\" + fileName + name + ".txt");
            txt.createNewFile(); // �������ļ�
            BufferedWriter out = new BufferedWriter(new FileWriter(txt));
            out.write(text); // \r\n��Ϊ����
            out.flush(); // �ѻ���������ѹ���ļ�
            out.close(); // ���ǵùر��ļ�

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MonoImage extends Image {

    public MonoImage(String path) {
        super(new File(path), 1);
    }

    public MonoImage(File f) {
        super(f, 1);
    }

    /**
     * @return �ַ�ͼ
     */
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

    /**
     * @param symbol �ַ�ͼʹ�õ��ַ�
     * @return �ַ�ͼ
     */
    public String toString(String symbol) {
        StringBuilder re = new StringBuilder();
        boolean[][] blackPixel = getBlackPixel();
        for (int x = 0; x < blackPixel[0].length; x += 6) {
            for (double y = 0; y < blackPixel.length; y += 3.3) {
                if (blackPixel[(int)y][x]) re.append(symbol);
                else re.append(" ");
            }
            re.append("\n");
        }
        return re.toString();
    }

    /**
     * @param symbol �ַ�ͼʹ�õ��ַ�
     * @param xRatio X�߲��� Ĭ��6
     * @param yRatio Y�߲��� Ĭ��3.3
     * @return �ַ�ͼ
     */
    public String toString(String symbol, double xRatio, double yRatio) {
        StringBuilder re = new StringBuilder();
        boolean[][] blackPixel = getBlackPixel();
        for (double x = 0; x < blackPixel[0].length; x += xRatio) {
            for (double y = 0; y < blackPixel.length; y += yRatio) {
                if (blackPixel[(int)y][(int)x]) re.append(symbol);
                else re.append(" ");
            }
            re.append("\n");
        }
        return re.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            Image image = (Image) obj;
            ArrayList<Object> arrayList = image.getAll();
            ArrayList<Object> all = getAll();
            for (int i = 0; i < 10; i++) {
                if (arrayList.get(i) != all.get(i)) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

class PolyImage extends Image {

    public PolyImage(String path) {
        super(new File(path), 2);
    }

    public PolyImage(File f) {
        super(f, 2);
    }

    public String redString() {
        StringBuilder red = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (int x = 0; x < valueRed[0].length; x += 6) {
            for (double y = 0; y < valueRed.length; y += 3.3) {
                if (valueRed[(int)y][x] > valueGreen[(int)y][x] && valueRed[(int)y][x] > valueBlue[(int)y][x]) red.append("#");
                else red.append(" ");
            }
            red.append("\n");
        }
        return red.toString();
    }

    public String greenString() {
        StringBuilder green = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (int x = 0; x < valueRed[0].length; x += 6) {
            for (double y = 0; y < valueRed.length; y += 3.3) {
                if (valueGreen[(int)y][x] > valueRed[(int)y][x] && valueGreen[(int)y][x] > valueBlue[(int)y][x]) green.append("#");
                else green.append(" ");
            }
            green.append("\n");
        }
        return green.toString();
    }

    public String blueString() {
        StringBuilder blue = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (int x = 0; x < valueRed[0].length; x += 6) {
            for (double y = 0; y < valueRed.length; y += 3.3) {
                if (valueBlue[(int)y][x] > valueRed[(int)y][x] && valueBlue[(int)y][x] > valueGreen[(int)y][x]) blue.append("#");
                else blue.append(" ");
            }
            blue.append("\n");
        }
        return blue.toString();
    }

    public String toString() {
        StringBuilder re = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (int x = 0; x < valueRed[0].length; x += 6) {
            for (double y = 0; y < valueRed.length; y += 3.3) {
                if (valueRed[(int)y][x] + valueGreen[(int)y][x] + valueBlue[(int)y][x] < 1) {
                    re.append("#");
                } else {
                    if (valueRed[(int)y][x] > valueGreen[(int)y][x] && valueRed[(int)y][x] > valueBlue[(int)y][x]) re.append("R");
                    else if (valueGreen[(int)y][x] > valueRed[(int)y][x] && valueGreen[(int)y][x] > valueBlue[(int)y][x]) re.append("G");
                    else if (valueBlue[(int)y][x] > valueRed[(int)y][x] && valueBlue[(int)y][x] > valueGreen[(int)y][x]) re.append("B");
                    else re.append(" ");
                }
            }
            re.append("\n");
        }
        return re.toString();
    }

    /**
     * @param xRatio X�߲��� Ĭ��6
     * @param yRatio Y�߲��� Ĭ��3.3
     * @return �ַ�ͼ
     */
    public String toString(double xRatio, double yRatio) {
        StringBuilder re = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (double x = 0; x < valueRed[0].length; x += xRatio) {
            for (double y = 0; y < valueRed.length; y += yRatio) {
                if (valueRed[(int)y][(int)x] + valueGreen[(int)y][(int)x] + valueBlue[(int)y][(int)x] < 1) {
                    re.append("#");
                } else {
                    if (valueRed[(int)y][(int)x] > valueGreen[(int)y][(int)x] && valueRed[(int)y][(int)x] > valueBlue[(int)y][(int)x]) re.append("R");
                    else if (valueGreen[(int)y][(int)x] > valueRed[(int)y][(int)x] && valueGreen[(int)y][(int)x] > valueBlue[(int)y][(int)x]) re.append("G");
                    else if (valueBlue[(int)y][(int)x] > valueRed[(int)y][(int)x] && valueBlue[(int)y][(int)x] > valueGreen[(int)y][(int)x]) re.append("B");
                    else re.append(" ");
                }
            }
            re.append("\n");
        }
        return re.toString();
    }

    /**
     * @param symbolR �ַ�ͼ�к�����ʹ�õ��ַ�
     * @param symbolG �ַ�ͼ��������ʹ�õ��ַ�
     * @param symbolB �ַ�ͼ��������ʹ�õ��ַ�
     * @param xRatio X�߲��� Ĭ��6
     * @param yRatio Y�߲��� Ĭ��3.3
     * @return �ַ�ͼ
     */
    public String toString(char symbolR, char symbolG, char symbolB, double xRatio, double yRatio) {
        StringBuilder re = new StringBuilder();
        int[][] valueRed = getValueRed();
        int[][] valueGreen = getValueGreen();
        int[][] valueBlue = getValueBlue();
        for (double x = 0; x < valueRed[0].length; x += xRatio) {
            for (double y = 0; y < valueRed.length; y += yRatio) {
                if (valueRed[(int)y][(int)x] + valueGreen[(int)y][(int)x] + valueBlue[(int)y][(int)x] < 1) {
                    re.append("#");
                } else {
                    if (valueRed[(int)y][(int)x] > valueGreen[(int)y][(int)x] && valueRed[(int)y][(int)x] > valueBlue[(int)y][(int)x]) re.append(symbolR);
                    else if (valueGreen[(int)y][(int)x] > valueRed[(int)y][(int)x] && valueGreen[(int)y][(int)x] > valueBlue[(int)y][(int)x]) re.append(symbolG);
                    else if (valueBlue[(int)y][(int)x] > valueRed[(int)y][(int)x] && valueBlue[(int)y][(int)x] > valueGreen[(int)y][(int)x]) re.append(symbolB);
                    else re.append(" ");
                }
            }
            re.append("\n");
        }
        return re.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            Image image = (Image) obj;
            ArrayList<Object> arrayList = image.getAll();
            ArrayList<Object> all = getAll();
            for (int i = 0; i < 10; i++) {
                if (arrayList.get(i) != all.get(i)) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
