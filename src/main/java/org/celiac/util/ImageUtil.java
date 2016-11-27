package org.celiac.util;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.Transparency;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil extends Frame {

    /**
     *
     */
    private static final long serialVersionUID = 4163109566859201110L;

    /**
     * The JAI.create action name for handling a stream.
     */
    private static final String JAI_STREAM_ACTION = "stream";

    /**
     * The JAI.create action name for handling a resizing using a subsample
     * averaging technique.
     */
    private static final String JAI_SUBSAMPLE_AVERAGE_ACTION = "SubsampleAverage";

    /**
     * The JAI.create encoding format name for JPEG.
     */
    private static final String JAI_ENCODE_FORMAT_JPEG = "JPEG";

    /**
     * The JAI.create action name for encoding image data.
     */
    private static final String JAI_ENCODE_ACTION = "encode";

    /**
     * The http content type/mime-type for JPEG images.
     */
    //private static final String JPEG_CONTENT_TYPE = "image/jpeg";
    //private int mMaxWidth = 800;
    //private int mMaxWidthThumbnail = 150;
    //private Image i;
    public byte[] shrinkToSmall(byte[] file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(file));
            BufferedImage bufferedImage = ImageIO.read(iis);
            bufferedImage.setRGB(80, 60, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = bufferedImage.createGraphics();

            g.drawBytes(file, 0, 0, 80, 60);
            g.dispose(); // free resource

            /*
			JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix(“jpeg”);
                        ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
             */
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            //encoder.encode(bufferedImage);
            //return bos.toByteArray();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }

        return null;

    }

    public byte[] shrinkToMedium(byte[] file) {
        return null;
    }



    public byte[] resizeImageAsJPG(byte[] pImageData, int pMaxWidth) throws Exception {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(pImageData));
        BufferedImage scaled = getScaledInstance(image, pMaxWidth, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
        return getByteArrayJPG(scaled, 0.85f);
        
    }

    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, Object hint, boolean higherQuality) {

        //calculate the hight needed
        int targetHeight = (targetWidth * img.getHeight()) / img.getWidth();

        int type
                = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    public static byte[] getByteArrayJPG(BufferedImage bufferedImage,float quality) throws IOException {
        Iterator<ImageWriter> iterator
                = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);
        
        //ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);
        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        return baos.toByteArray();
        
    }

}
