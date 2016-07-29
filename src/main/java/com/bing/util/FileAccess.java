package com.bing.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Administrator on 2015/6/29.
 */
public class FileAccess {
    private static final FileAccess fileAccess = new FileAccess();

    private FileAccess() {
    }

    public static FileAccess getInstance() {
        return fileAccess;
    }

    public void saveFile(String filePath, MultipartFile image)
            throws FileUploadException {
        try {
            File file = new File(filePath);
            FileUtils.writeByteArrayToFile(file, image.getBytes());
        } catch (IOException e) {
            throw new FileUploadException("Unable to save image", e);
        }
    }

    public boolean removeFile(String filePath) {
        File file = new File(filePath);
        System.out.println(">>>" + filePath);
        if (!file.exists()) {
            System.out.println("ɾ���ļ�ʧ�ܣ�" + filePath + "�ļ�������");
            return false;
        } else {
            file.delete();
            System.out.println("ɾ�������ļ�" + filePath + "�ɹ���");
        }
        return true;
    }

    public void copy(String oldPath, String newPath) {
        File oldfile = new File(oldPath);
        copy(oldfile, newPath);
    }

    public void copy(File oldfile, String newPath) {
        try {
            int byteSum = 0;
            int byteRead = 0;
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead;
                    System.out.println(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }
}

