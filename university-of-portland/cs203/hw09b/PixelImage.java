import java.awt.image.*;
/**
 * PixelImage - 
 * Provides a picture as an array of Pixels
 * 
 * @author Richard Dunn, modified by Donald Chinn, 
 * modified by Parag, modified by Tammy VanDeGrift
 * modified kw
 * @version Oct. 31, 2007
 * @version March 2014
 * 
 * !!! DO NOT MODIFY THIS CLASS !!!
 */
public class PixelImage {
    private BufferedImage myImage; //image corresponding to this pixel image
    private int width;             // width of the image
    private int height;            // height of the image
    private Pixel pixels[][];      // the pixels

    /**
     * PixelImage
     * maps a real image to a PixelImage object
     * @param bi The buffered image object used to
     * create the PixelImageObject
     */
    public PixelImage(BufferedImage bi) {
        // initialise instance variables
        myImage = bi;
        width = bi.getWidth();
        height = bi.getHeight();
        pixels = new Pixel[height][width];
        initializePixels();
    }

    /**
     * getWidth
     * Returns the width of the image
     * @return image width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * getHeight
     * Returns the height of the image
     * @return image height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * getImage
     * Returns the buffered image
     * @return buffered image
     */
    public BufferedImage getImage() {
        return this.myImage;
    }

    /** 
     * initializePixels
     * initializes pixels based on information
     * in the buffered image
     */
    private void initializePixels() {

        Raster r = this.myImage.getRaster();
        int[] samples = new int[3];

        for (int row = 0; row < r.getHeight(); row++) {
            for (int col = 0; col < r.getWidth(); col++) {
                samples = r.getPixel(col, row, samples);
                Pixel newPixel = new Pixel(samples[0], samples[1], samples[2]);
                pixels[row][col] = newPixel;
            }
        }
    }

    /**
     * getData
     * Return the image's pixel data as an array of Pixels.  The
     * first coordinate is the x-coordinate, so the size of the 
     * array is [width][height], where width and height are the 
     * dimensions of the array
     * @return The array of pixels
     */
    public Pixel[][] getData() {
        return pixels;
    }

    /**
     * setData
     * Sets the image's pixel data from the pixels array.  
     * It is an error for a filter to change the dimensions of pixels
     * or that add Pixel objects with invalid values (not 0-255)
     */
    public void setData() throws IllegalArgumentException {
        int[] pixelValues = new int[3];     // a temporary array to hold r,g,b values
        WritableRaster wr = this.myImage.getRaster();

        if (pixels.length != wr.getHeight()) {
            throw new IllegalArgumentException("Array size does not match");
        } else {
            if (pixels[0].length != wr.getWidth()) {
                throw new IllegalArgumentException("Array size does not match");
            }
        }
        for (int row = 0; row < wr.getHeight(); row++) {
            for (int col = 0; col < wr.getWidth(); col++) {
                pixelValues[0] = pixels[row][col].getRed();
                pixelValues[1] = pixels[row][col].getGreen();
                pixelValues[2] = pixels[row][col].getBlue();
                wr.setPixel(col, row, pixelValues);
            }
        }
    }
}
