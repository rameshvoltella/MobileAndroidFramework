package core.helpers;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    public static final int DIFFERENCE_SQUARE_SIZE = 40;
    private final static Logger log = Logger.getLogger(Image.class);
    private static final int WIDTH_OF_UPPER_CUT_SECTION = 30;
    private static int photocount = 0;

    /**
     * Crops a given image by removing notifications section at the top of it.
     * Resulting image should be used later to compare to screenshots in
     * application.
     *
     * @param newFilePathAndName
     * @param originalImagePathAndName
     * @throws IOException
     */
    public static void cropImageForTesting(String newFilePathAndName,
                                           String originalImagePathAndName) throws IOException {

        BufferedImage originalBI = getBufferedImageFromFile(originalImagePathAndName);

        cropImageForTesting(newFilePathAndName, originalBI);
    }

    /**
     * Crops a given image by removing notifications section at the top of it.
     * Resulting image should be used later to compare to screenshots in
     * application.
     *
     * @param newFilePathAndName
     * @param originalImageBufferedImage
     */
    public static void cropImageForTesting(String newFilePathAndName,
                                           BufferedImage originalImageBufferedImage) {

        if (originalImageBufferedImage != null) {
            BufferedImage croppedBI = cropBufferedImageForTesting(originalImageBufferedImage);
            File newFile = new File(newFilePathAndName);
            try {
                ImageIO.write(croppedBI, "png", newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads file on disc and returns the BufferedImage. If any exceptions
     * occur, null is returned.
     *
     * @param imagePathAndName
     * @return
     * @throws IOException
     */
    public static BufferedImage getBufferedImageFromFile(String imagePathAndName) throws IOException {

        File imageFile = new File(imagePathAndName);
        return ImageIO.read(imageFile);
    }

    /**
     * Crops a given BufferedImage by removing notifications section at the top
     * of it.
     *
     * @param originalBufferedImage
     * @return cropped BufferedImage
     */
    public static BufferedImage cropBufferedImageForTesting(
            BufferedImage originalBufferedImage) {

        return originalBufferedImage.getSubimage(0, WIDTH_OF_UPPER_CUT_SECTION,
                originalBufferedImage.getWidth(),
                originalBufferedImage.getHeight() - WIDTH_OF_UPPER_CUT_SECTION);
    }

    /**
     * Compares two images stored in BufferedImage objects, pixel by pixel. An
     * offset is acceptable, meaning that the R, G, B and Alpha values can
     * differ by the given offset.
     *
     * @param image1BI
     * @param image2BI
     * @param offset
     * @return
     */
    public static boolean compareImagesByPixels(BufferedImage image1BI,
                                                BufferedImage image2BI, int offset) {

        Graphics2D gc1 = image1BI.createGraphics();
        Graphics2D gc2 = image2BI.createGraphics();
        gc1.setColor(Color.RED);
        gc2.setColor(Color.RED);

        if ((image1BI == null) || (image2BI == null)) {
            log.info("One of the buffered image is null");
        }
        boolean isSame = true;

        int width1 = image1BI.getWidth();
        int width2 = image2BI.getWidth();
        int height1 = image1BI.getHeight();
        int height2 = image2BI.getHeight();

        if ((width1 == width2) && (height1 == height2)) {

            for (int i = 0; i < width1; i++) {
                for (int j = 0; j < height1; j++) {

                    if (!isPixelTheSame(i, j, image1BI, image2BI, offset)) {
                        isSame = false;
                        // draw an indicator on the change image to show
                        // where change was detected.
                        int x1 = i / DIFFERENCE_SQUARE_SIZE;
                        x1 = x1 * DIFFERENCE_SQUARE_SIZE;
                        int w = DIFFERENCE_SQUARE_SIZE;
                        int y1 = j / DIFFERENCE_SQUARE_SIZE;
                        y1 = y1 * DIFFERENCE_SQUARE_SIZE;
                        int h = DIFFERENCE_SQUARE_SIZE;
                        gc1.drawRect(x1, y1, w, h);
                        gc2.drawRect(x1, y1, w, h);
                    }
                }
            }
        } else {
            System.out
                    .println(String
                            .format("Comared images have different sizes: Image1 w:%d, h:%d; Image2 w:%d, h:%d",
                                    width1, height1, width2, height2));
            isSame = false;
        }
        return isSame;
    }

    public static void saveImageAsArtefact(File f, String name) {

        try {
            photocount++;
            String fileName = String.format("target/snapshot/ios%02d%s.png",
                    photocount, name);
            FileUtils.copyFile(f, new File(fileName));
            log.info("Image stored in " + fileName);
        } catch (IOException e) {
            log.info("Error saving image");
            e.printStackTrace();
        }
    }

    public static void saveImageAsArtefact(BufferedImage bi, String name) {

        try {
            photocount++;

            File snapshotDir = new File("target/snapshot");
            if (!snapshotDir.exists()) {
                FileUtils.forceMkdir(snapshotDir);
            }
            String fileName = String.format("target/snapshot/ios%02d%s.png",
                    photocount, name);
            ImageIO.write(bi, "PNG", new File(fileName));
            log.info("Image stored in " + fileName);
        } catch (IOException e) {
            log.info("Error saving image");
            e.printStackTrace();
        }
    }

    public static boolean isPixelTheSame(int xCoordinate, int yCoordinate,
                                         BufferedImage image1BI, BufferedImage image2BI, int offset) {

        return isPixelTheSame(xCoordinate, yCoordinate, xCoordinate,
                yCoordinate, image1BI, image2BI, offset);
    }

    public static boolean isPixelTheSame(int xCoordinate1BI,
                                         int yCoordinate1BI, int xCoordinate2BI, int yCoordinate2BI,
                                         BufferedImage image1BI, BufferedImage image2BI, int offset) {

        Color c1 = new Color(image1BI.getRGB(xCoordinate1BI, yCoordinate1BI));
        Color c2 = new Color(image2BI.getRGB(xCoordinate2BI, yCoordinate2BI));

        if (isColorTheSame(c1, c2, offset)) {
            return true;
        } else {
            // look +-1 pixel around
            boolean pixelAroundIsTheSame = false;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if ((i == 0) && (j == 0)) {
                            break;
                        }
                        c1 = new Color(image1BI.getRGB(xCoordinate1BI + i,
                                yCoordinate1BI + j));
                        c2 = new Color(image2BI.getRGB(xCoordinate2BI + i,
                                yCoordinate2BI + j));
                        // compare colors at given coordinates
                        if (isColorTheSame(c1, c2, offset)) {
                            pixelAroundIsTheSame = true;
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // do nothing, since comparison may be on pixels outside
                        // the screen
                    }
                }
                if (pixelAroundIsTheSame) {
                    break;
                }
            }
            return pixelAroundIsTheSame;
        }
    }

    private static boolean isColorTheSame(Color c1, Color c2, int offset) {
        // differences should be more than offset to be a difference
        if (c1.getRGB() == c2.getRGB()) {
            return true;
        } else if ((Math.abs(c1.getRed() - c2.getRed()) < offset)
                && (Math.abs(c1.getGreen() - c2.getGreen()) < offset)
                && (Math.abs(c1.getBlue() - c2.getBlue()) < offset)
                && (Math.abs(c1.getAlpha() - c2.getAlpha()) < offset)) {
            return true;
        }

        return false;
    }
}
