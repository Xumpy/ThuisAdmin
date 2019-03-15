package com.xumpy.finances.services;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ImageShrinker {

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private static String convertMimeTypeToImageType(String mimeType){
        if (mimeType.equals("image/jpg")){
            return "jpg";
        }
        if (mimeType.equals("image/png")){
            return "png";
        }
        throw new RuntimeException("Not a valid supported mime type");
    }

    public static byte[] shrinkImage2(byte[] image, String mimeType, int percentage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ResampleOp resizeOp = new ResampleOp(bufferedImage.getWidth() / 100 * percentage, bufferedImage.getHeight() / 100 * percentage);
        ImageIO.write(resizeOp.filter(bufferedImage, null), convertMimeTypeToImageType(mimeType), baos);
        baos.flush();
        byte[] shrinkedImage = baos.toByteArray();
        baos.close();
        return shrinkedImage;
    }

    public static byte[] shrinkImage(byte[] image, String mimeType, int percentage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resize(bufferedImage, 100, 100),  convertMimeTypeToImageType(mimeType), baos);
        baos.flush();

        byte[] shrinkedImage = baos.toByteArray();
        baos.close();
        return shrinkedImage;
    }

}
