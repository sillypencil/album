package com.bing.util;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public final class ImageCompress {
    private Image image;
    private int imageWidth;
    private int imageHeight;
    private String sourceImagePath;
    private String destPath;

    public static void main(String[] args) throws Exception {
//        System.out.println("开始：" + new Date().toLocaleString());
//        ImageCompress imageCompress = new ImageCompress("./src/main/webapp/resources/images/1/2/5/1435844363616.jpg",
//                "./src/main/webapp/resources/images/compress/1/2/5/1435844363616.jpg");
//        imageCompress.resizeFix(200, 200);
//        System.out.println("结束：" + new Date().toLocaleString());
    }

    public ImageCompress(String sourceImagePath, String destPath) throws IOException{
        File file = new File(sourceImagePath);// 读入文件
        this.image = ImageIO.read(file);
        this.imageWidth = image.getWidth(null);
        this.imageHeight = image.getHeight(null);
        this.destPath = destPath;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getSourceImagePath() {
        return sourceImagePath;
    }

    public void setSourceImagePath(String sourceImagePath) {
        this.sourceImagePath = sourceImagePath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    /**
     * 按照宽度还是高度进行压缩
     * @param width int 最大宽度
     * @param height int 最大高度
     */
    public void resizeFix(int width, int height) throws IOException {
        if (imageWidth / imageHeight > width / height) {
            resizeByWidth(width);
        } else {
            resizeByHeight(height);
        }
    }
    /**
     * 以宽度为基准，等比例放缩图片
     * @param width int 新宽度
     */
    public void resizeByWidth(int width) throws IOException {
        int height = (int) (imageHeight * width / imageWidth);
        resize(width, height);
    }
    /**
     * 以高度为基准，等比例缩放图片
     * @param height int 新高度
     */
    public void resizeByHeight(int height) throws IOException {
        int width = (int) (imageWidth * height / imageHeight);
        resize(width, height);
    }
    /**
     * 强制压缩/放大图片到固定的大小
     * @param width int 新宽度
     * @param height int 新高度
     */
    public void resize(int width, int height) throws IOException {
        Image tmpImage = this.image.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(tmpImage.
                getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

        File destFile = new File(destPath);
        FileOutputStream out = FileUtils.openOutputStream(destFile);

        ImageIO.write(bufferedImage, "JPG", out);
        out.close();
    }
}
