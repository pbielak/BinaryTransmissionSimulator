package pwr.bts.stream;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageInputStream extends ArrayBitInputStream {

	public ImageInputStream(String filename, String format) throws IOException {
		super(getBytesFromIMG(filename, format));
	}

	private static byte[] getBytesFromIMG(String filename, String format) throws IOException {
		BufferedImage image = ImageIO.read(new File(filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format, baos);
		baos.flush();
		return baos.toByteArray();
	}
	
	public BufferedImage getIMG() throws IOException {
		InputStream input = asInputStream();
		return ImageIO.read(input);
	}
}
