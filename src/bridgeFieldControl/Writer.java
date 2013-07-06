package bridgeFieldControl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import nxtPyhtonBridge.Brick;

public class Writer implements Runnable {

	private BufferedImage file;
	private String path;
	private static boolean written = false;

	public Writer(BufferedImage file, String path) {
		this.file = file;
		this.path = path;
	}

	public static void write() throws FileNotFoundException {
		if (Gui.pro.size() > 0) {
			if (!written) {
				String out3 = new Integer(Gui.count).toString();
				if (Gui.count < 10) {
					out3 = "0" + out3;
				}
				if (Gui.count < 100) {
					out3 = "0" + out3;
				}
				if (Gui.count < 1000) {
					out3 = "0" + out3;
				}

				PrintStream out = new PrintStream(new FileOutputStream(
						Brick.path + "/plays/" + Gui.folder + "/" + out3
								+ ".txt"));
				out.print(Gui.getText());
				out.close();
			}
			written = true;
		}
	}

	public void run() {
		try {
			System.out.println("main: start write picture at: "+path);
			ImageIO.write(file, "PNG", new File(path));
			System.out.println("main: finish write pricture at: "+path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}