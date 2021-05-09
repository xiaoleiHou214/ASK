package com.example.ask.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtil {
    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */
    public static List<File> listFileSortByModifyTime(String path) {
        List<File> list = getFiles(path, new ArrayList<File>());
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
        return list;
    }

    /**
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name, String path) throws IOException {
        boolean flag = false;
        String filenameTemp = path + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr 新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr, String path, String fileName) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        String filenameTemp = path + fileName + ".txt";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
//            for (int j = 1; (temp = br.readLine()) != null; j++) {
//                buf = buf.append(temp);
//                // System.getProperty("line.separator")
//                // 行与行之间的分隔符 相当于“\n”
//                buf = buf.append(System.getProperty("line.separator"));
//            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    public static void makeTheBat(String packageName, String type, String filePath) {
        String cmd = "cd /data/data\n" +
                "run-as " + packageName + "\n" +
                "cd " + type + "\n" +
                "ls -l";
        String fileName = "bat";
        try {
            creatTxtFile(fileName, filePath);
            writeTxtFile(cmd, filePath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String executeLocalCmd(String cmd, File workpath) {
        try {
            String[] cmdA = {"cmd.exe", "/c", cmd};
            Process process = null;
            if (workpath == null) {
                process = Runtime.getRuntime().exec(cmdA);
            } else {
                process = Runtime.getRuntime().exec(cmdA, null, workpath);
            }
//             LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream(), "GBK"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveResultToFile(String result, Context context) {
        String beforeResult = getResultFromFile(context);
        if (!"".equals(result)) {
            result = beforeResult + result;
        }
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        try {
            fileOutputStream = context.openFileOutput("exploit_result.txt", Context.MODE_PRIVATE);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(result);
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResultFromFile(Context context) {
        String beforeResult = "";
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput("exploit_result.txt");
            StringBuilder builder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            bufferedReader.close();
            fileInputStream.close();
            beforeResult = builder.toString();
        } catch (FileNotFoundException fnfe) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return beforeResult;
    }

}
