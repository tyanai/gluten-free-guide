package org.celiac.util;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class ImageShrinker extends Frame {

    /**
     *
     */
    private static final long serialVersionUID = -3849222323024181468L;
    /**
     * @param args
     */

    static int percentage = 0;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //ImageShrinker iShrinker = new ImageShrinker();
        File dir = new File("pictures");

        percentage = new Integer(args[0].trim()).intValue();

        String[] children = dir.list();
        new File("pictures_" + percentage + "_percentage").mkdir();

        try {
            for (int i = 0; i < children.length; i++) {

                OutputStream outputStream = new FileOutputStream("./pictures_"
                        + percentage + "_percentage/" + children[i]);

                BufferedImage image = ImageIO.read(new File("./pictures/"
                        + children[i]));

                writeJpegImage(image, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeJpegImage(BufferedImage bufferedImage, OutputStream outputStream)
            throws IOException {

        if (outputStream == null) {
            return;
        }

        try {

            Iterator<ImageWriter> iterator
                    = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter imageWriter = iterator.next();
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(percentage);
            ImageOutputStream imageOutputStream
                    = new MemoryCacheImageOutputStream(outputStream);
            imageWriter.setOutput(imageOutputStream);
            IIOImage iioimage = new IIOImage(bufferedImage, null, null);
            imageWriter.write(null, iioimage, imageWriteParam);
            imageOutputStream.flush();

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
    }

}
