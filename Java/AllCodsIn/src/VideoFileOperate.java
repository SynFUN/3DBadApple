/**
 * @Time : 2021/5/13 1:03
 * @Author : Synthesis ��Ʒ��
 * @File : VideoFileOperate
 * @Software : IntelliJ IDEA
 * https://github.com/ddzbxh
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leo on 2017/2/10.
 * ��Ƶ�ļ��ָ������
 */
public class VideoFileOperate {
    //�ָ���Ƶ�Ĵ�С��װ������Ϊ�˱���Խ�硣longӦ�ù�ʹ�ˡ�����
//    private long blockSize = 1 * new Long(1024) * 1024 * 1024;
    private long blockSize = 400 * 1024 * 1024;
    private loadingListener mListener;
    private boolean ffmpegWorkingFlag = false;

    /**
     * ��ȡ��Ƶ�ļ�ʱ��
     *
     * @param file �ļ�
     * @return ʱ�� ��ʽhh:MM:ss
     * @throws FileNotFoundException ��Ƶ�������׳����쳣
     */
    private String getVideoTime(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "������");
        }
        List<String> commands = new ArrayList<String>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(file.getAbsolutePath());
        CmdResult result = runCommand(commands);
        String msg = result.getMsg();
        if (result.isSuccess()) {
            //\d{2}:\d{2}:\d{2}
            Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
            Matcher matcher = pattern.matcher(msg);
            String time = "";
            while (matcher.find()) {
                time = matcher.group();
            }
            return time;
        } else {
            return "";
        }
    }

    /**
     * ��ȡ�ļ���С
     *
     * @param file ȥ���ļ����ȣ���λΪ�ֽ�b
     * @return �ļ����ȵ��ֽ���
     * @throws FileNotFoundException �ļ�δ�ҵ��쳣
     */
    private long getVideoFileLength(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "������");
        }
        return file.length();
    }

    /**
     * @param filePath Ҫ������ļ�·��
     * @return �ָ����ļ�·��
     * @throws Exception �ļ�
     */
    List<String> cutVideo(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath + "�ļ�������");
        }
        if (!filePath.endsWith(".mp4")) {
            throw new Exception("�ļ���ʽ����");
        }
        //��ffmpeg��õ�ʱ�䳤��00:00:00��ʽ
        String videoTimeString = getVideoTime(file);
        //��ʱ��ת��Ϊ����
        int videoSecond = parseTimeToSecond(videoTimeString);
        //��Ƶ�ļ��Ĵ�С
        long fileLength = getVideoFileLength(file);
        List<String> cutedVideoPaths = new ArrayList<String>();
        if (fileLength <= blockSize) {//�����Ƶ�ļ���С������Ԥ��ֵ����ֱ�ӷ���ԭ��Ƶ�ļ�
            cutedVideoPaths.add(filePath);
        } else {//�������Ԥ���С������Ҫ�и�
            int partNum = (int) (fileLength / blockSize);//�ļ���С���Էֿ��С����
            long remainSize = fileLength % blockSize;//����
            int cutNum;
            if (remainSize > 0) {
                cutNum = partNum + 1;
            } else {
                cutNum = partNum;
            }
            int eachPartTime = videoSecond / cutNum;
            List<String> commands = new ArrayList<String>();
            String fileFolder = file.getParentFile().getAbsolutePath();
            String fileName[] = file.getName().split("\\.");
            commands.add("ffmpeg");
            for (int i = 0; i < cutNum; i++) {
                commands.add("-i");
                commands.add(filePath);
                commands.add("-ss");
                commands.add(parseTimeToString(eachPartTime * i));
                if (i != cutNum - 1) {
                    commands.add("-t");
                    commands.add(parseTimeToString(eachPartTime));
                }
                commands.add("-acodec");
                commands.add("copy");
                commands.add("-vcodec");
                commands.add("copy");
                commands.add(fileFolder + File.separator + fileName[0] + "_part" + i + "." + fileName[1]);
                commands.add("-y");
                cutedVideoPaths.add(fileFolder + File.separator + fileName[0] + "_part" + i + "." + fileName[1]);
            }
            runCommand(commands);
        }
        return cutedVideoPaths;
    }

    /**
     * ִ��Cmd�����
     *
     * @param command �������
     * @return ִ�н��
     */
    private synchronized CmdResult runCommand(List<String> command) {
        CmdResult cmdResult = new CmdResult(false, "");
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            final StringBuilder stringBuilder = new StringBuilder();
            final InputStream inputStream = process.getInputStream();
            new Thread(new Runnable() {//�������߳�Ϊ�첽��ȡ����������ֹ�߳�����

                @Override
                public void run() {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
//                          mListener.isLoading(true);
                        }
//                        mListener.isLoading(false);
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }).start();
            process.waitFor();
            cmdResult.setSuccess(true);
            cmdResult.setMsg(stringBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException("ffmpegִ���쳣" + e.getMessage());
        }
        return cmdResult;
    }

    /**
     * ���ַ���ʱ���ʽת��Ϊ���ͣ�����Ϊ��λ
     *
     * @param timeString �ַ���ʱ��ʱ��
     * @return ʱ������Ӧ������
     */
    private int parseTimeToSecond(String timeString) {
        Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(timeString);
        if (!matcher.matches()) {
            try {
                throw new Exception("ʱ���ʽ����ȷ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] time = timeString.split(":");
        return Integer.parseInt(time[0]) * 3600 + Integer.parseInt(time[1]) * 60 + Integer.parseInt(time[2]);
    }

    /**
     * �����ʾʱ��תΪ00:00:00��ʽ
     *
     * @param second ����ʱ��
     * @return �ַ�����ʽʱ��
     */
    private String parseTimeToString(int second) {
        int end = second % 60;
        int mid = second / 60;
        if (mid < 60) {
            return mid + ":" + end;
        } else if (mid == 60) {
            return "1:00:" + end;
        } else {
            int first = mid / 60;
            mid = mid % 60;
            return first + ":" + mid + ":" + end;
        }

    }

    interface loadingListener {
        void isLoading(boolean loading);
    }

//    /**
//     * �����ж�ffmpeg�Ƿ��ڹ���
//     *
//     * @return true�ڹ��� ��ʱ�޷���֤�Ƿ�׼ȷ
//     */
//    public boolean isFFmpegWorking() {
//
//        mListener = new loadingListener() {
//            @Override
//            public void isLoading(boolean loading) {
//                ffmpegWorkingFlag = loading;
//            }
//        };
//        return ffmpegWorkingFlag;
//    }
}

/**
 * Created by leo on 2017/2/10.
 * cmd����ִ�к�����
 */
class CmdResult {
    private boolean success;
    private String msg;

    public CmdResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public CmdResult() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

