package nxtPyhtonBridge;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageWriter implements Runnable {

	private BufferedImage image;
	private String path;

	public static Thread init(BufferedImage image, String path)
	{
		Runnable r = new ImageWriter(image, path);
		Thread t = new Thread(r);
		t.start();
		return t;
	}
	
	public ImageWriter(BufferedImage image, String path) {
		this.image = image;
		this.path = path;
	}

	public void run() {
		try {
			System.out.println("main: start writting picture at: "+path);
			ImageIO.write(image, "PNG", new File(path));
			System.out.println("main: finish writting pricture at: "+path);
		} catch (IOException e) {
			System.out.println("main: some error by write picture!!!");
			e.printStackTrace();
		}

	}


}