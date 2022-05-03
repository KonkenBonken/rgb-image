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
import java.util.Arrays;

class rgb {
  public static void main(String[] args) throws IOException {
    int w = 500;
    int h = 500;
    int[] matrix = new int[w * h]; // hex number in #AARRGGBB format

    int clr = Integer.parseInt(args[0], 16);
    int A = 0xFF000000;
    int R = 0xFF0000;
    int G = 0x00FF00;
    int B = 0x0000FF;

    double r = (clr >> 16) & 0xff;
    double g = (clr >> 8) & 0xff;
    double b = clr & 0xff;
    double tot = r+g+b;
    r = r / tot;
    g = g / tot + r;

    for (int i = 0; i < matrix.length; i++) {
      double rand = Math.random();
      if (rand < r)
        clr = R;
      else if (rand < g)
        clr  = G;
      else
        clr = B;

      matrix[i] = clr + A;
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
