import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferInt;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Math;

class rgb {
  public static void main(String[] args) throws IOException {
    int w = 50;
    int h = 50;
    int[] matrix = new int[w * h]; // hex number in #AARRGGBB format

    int clr = Integer.parseInt(args[0], 16);
    int a = 255 * 0x1000000;
    
    for (int i = 0; i < matrix.length; i++) {
      int r = ((clr >> 16) & 0xff) * 0x10000;
      int g = ((clr >> 8) & 0xff) * 0x100;
      int b = (clr & 0xff) * 0x1;
      matrix[i] = a + r + g + b;
    }

    DataBufferInt buffer = new DataBufferInt(matrix, matrix.length);

    int[] bandMasks = {
      0xFF0000,
      0xFF00,
      0xFF,
      0xFF000000
    };
    WritableRaster raster = Raster.createPackedRaster(buffer, w, h, w, bandMasks, null);

    ColorModel cm = ColorModel.getRGBdefault();
    BufferedImage image = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);

    File f = new File("image.png");
    if (!ImageIO.write(image, "PNG", f)) {
      throw new RuntimeException("Unexpected error writing image");
    }
  }
}
