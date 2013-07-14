package bridgeFieldControl;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import nxtPyhtonBridge.Brick;
import nxtPyhtonBridge.Tools;
import nxtPyhtonBridge.ImageWriter;

class Gui {

	private static JTextArea infotext;
	private static BufferedImage around;
	private static BufferedImage unknown;
	private static BufferedImage free;
	private static BufferedImage blocked;
	private static BufferedImage nxt;
	private static BufferedImage arrow_right;
	private static BufferedImage arrow_down;
	private static BufferedImage arrow_left;
	private static BufferedImage arrow_up;
	private static Component comp;

	public static double dif_xy;
	public static int max_x;
	public static int max_y;
	public static int scale_x;
	public static int scale_y;

	public static JFrame Twindow;
	public static JFrame info;
	public static int count = 0;
	public static String folder;
	public static ArrayList<int[]> gui;
	public static ArrayList<Thread> pro;
	public static Image display_image;
	public static JButton stopButton;
	public static JButton resetButton;

	public static void main(String[] args) {
    try {
		BrickGame.path = args[0];
		Master.setConfig();
		FieldGame.init_sub(10, 8);
				
		Status.ifWin = -1;
		BrickGame.error = new String[2];
		BrickGame.error[0] = "fatal";
		BrickGame.error[1] = "writeproblem";
		
		String[] names = new String[4];
		names[0]="Test1";
		names[1]="Test2";
		names[2]="Test3";
		names[3]="Test4";
		
		int[][] coords = new int[4][3];
		
		coords[0][0]=1;
		coords[0][1]=1;
		coords[0][2]=1;
		FieldGame.setField(1, 1, 1, -1);

		
		coords[1][0]=2;
		coords[1][1]=4;
		coords[1][2]=2;
		FieldGame.setField(1, 2, 4, -1);

		coords[2][0]=5;
		coords[2][1]=3;
		coords[2][2]=3;
		FieldGame.setField(1, 5, 3, -1);

		coords[3][0]=1;
		coords[3][1]=5;
		coords[3][2]=4;
		FieldGame.setField(1, 1, 5, -1);

		FieldGame.setField(2, 2, 2, -1);
		FieldGame.setField(1, 1, 2, -1);

		
		BrickGame.init(names, coords,true);
		
		Gui.init();
		
		while(true){
		Thread.sleep(500);
		if(Status.stop){Status.stop();}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	public static void writeStatus() throws FileNotFoundException {
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
	
	public static void getScale()
	{
		int[] out = Tools.getOptiScaleForField(FieldGame.size_x+2, FieldGame.size_y+2, max_x, max_y, dif_xy);
			Gui.scale_x = out[0];
			Gui.scale_y = out[1];
	}

	public static String getPath() {
		java.util.Date date = new java.util.Date();
		if (count == 0) {
			folder = String.valueOf(date.getTime());
			new File(Brick.path + "/plays/" + folder).mkdir();
		}

		String out = new Integer(count).toString();
		if (count < 10) {out = "0" + out;}
		if (count < 100) {out = "0" + out;}
		if (count < 1000) {out = "0" + out;}
		count++;

		return Brick.path + "/plays/" + folder + "/" + out + ".png";
	}

	public static void genImage() throws IOException {

		BufferedImage awtImage = new BufferedImage((FieldGame.size_x + 2)
				* scale_x, (FieldGame.size_y + 2) * scale_y,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) awtImage.getGraphics();

		while (gui.size() != 0) {
			if (gui.get(0)[0] <= 0) {
				g.drawImage(getRightNXT(-gui.get(0)[0]), gui.get(0)[1],
				gui.get(0)[2], gui.get(0)[3], gui.get(0)[4], Twindow);
			} else {
				g.drawImage(getRightImage(gui.get(0)[0]), gui.get(0)[1],
				gui.get(0)[2], gui.get(0)[3], gui.get(0)[4], Twindow);
			}
			gui.remove(0);
		}

		pro.add(ImageWriter.init(awtImage, getPath()));
		display_image = awtImage;
	}

	public static Image getRightNXT(int id) {
		if (BrickGame.bricks.get(id).my_image != null) {
			return BrickGame.bricks.get(id).my_image;
		}
		return nxt;
	}

	public static Image getRightImage(int number) {

		if (number == 1) {
			return around;
		} else if (number == 2) {
			return unknown;
		} else if (number == 3) {
			return free;
		} else if (number == 4) {
			return nxt;
		} else if (number == 5) {
			return arrow_right;
		} else if (number == 6) {
			return arrow_down;
		} else if (number == 7) {
			return arrow_left;
		} else if (number == 8) {
			return arrow_up;
		} else {
			return blocked;
		}
	}

	public static int getRightImageNumber(int x, int y) {

		if (x == 0 || y == 0 || x > FieldGame.size_x || y > FieldGame.size_y) {
			return 1;
		} else if (FieldGame.theField[x - 1][y - 1] == 0) {
			return 2;
		} else if (FieldGame.theField[x - 1][y - 1] == 1) {
			return 3;
		} else {
			return 99;
		}
	}

	public static void init() throws IOException {
		gui = new ArrayList<int[]>();
		pro = new ArrayList<Thread>();

		Twindow = new JFrame();
		Twindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Twindow.setSize(500, 500);
		Twindow.setTitle("NXT-Projekt - Feld Anzeige - Marcel Link");
		info = new JFrame();
		info.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		info.setSize(200, 500);
		info.setTitle("Roboter Status");
		comp = new Component();
		Twindow.getContentPane().add(comp);
		Twindow.setVisible(true);
		infotext = new JTextArea();
		infotext.setLineWrap(true);
		infotext.setWrapStyleWord(true);
		infotext.setEditable(false);

		JPanel topPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel resPanel = new JPanel();

		topPanel.setLayout(new BorderLayout());
		info.getContentPane().add(topPanel);
		info.getContentPane().add(btnPanel);
		info.getContentPane().add(resPanel);

		
		stopButton = new JButton("Fortsetzen");
		stopButton.addActionListener(new TheButton());
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new TheButton());
		btnPanel.add(stopButton);
		resPanel.add(resetButton);
		topPanel.add(infotext);


		info.getContentPane().add(topPanel, BorderLayout.NORTH);
		info.getContentPane().add(btnPanel, BorderLayout.EAST);
		info.getContentPane().add(resPanel, BorderLayout.WEST);

		info.setVisible(true);

		info.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("main: visible info false");
				if (Twindow.isVisible()) {Twindow.setVisible(false);}else{Status.stop_init();}
			}
		});

		Twindow.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("main: visible Twindow false");
				if (info.isVisible()) {info.setVisible(false);}else{Status.stop_init();}
			}
		});

		try {
			getScale();
			
			around = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/around.png")), scale_x,scale_y);
			unknown = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/unknown.png")), scale_x,scale_y);
			free = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/free.png")), scale_x,scale_y);
			blocked = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/blocked.png")), scale_x,scale_y);
			arrow_right = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/arrow.png")), scale_y,scale_y);

			arrow_down = Tools.rotateImage(arrow_right, 90);
			arrow_left = Tools.rotateImage(arrow_down, 90);
			arrow_up = Tools.rotateImage(arrow_left, 90);
			
			nxt = Tools.scaleImage(ImageIO.read(new File(Brick.path+ "/icons/nxt.png")), scale_y,scale_y);

		} catch (IOException e) {e.printStackTrace();}
		update();
	}

	public static void update() throws IOException {
		if (Status.win) {return;}
		newElements();
		genImage();
		comp.paintComponent(comp.getGraphics());
		infotext.setText(getText());
	}

	public static String getText() {
		String out = "Siegbedingung: \n";
		if (Status.ifWin == -1) {
			out = out + "Alle Felder aufdecken\nVerbleibend: "
					+ new Integer(FieldGame.unknown).toString() + "/"
					+ new Integer(FieldGame.size_x * FieldGame.size_y).toString()
					+ "\n\n";
		}
		if (Status.ifWin == 0) {
			out = out + "Keine Festgelegt\n";
		}
		if (Status.ifWin > 0) {
			out = out + "Alle Hindernisse finden\nVerbleibend: "
					+ new Integer(FieldGame.objects).toString() + "/"
					+ new Integer(Status.ifWin).toString() + "\n\n";
		}

		for (int i = 0; i < BrickGame.bricks.size(); i++) {
			out = out
					+ "Name: "
					+ BrickGame.bricks.get(i).name
					+ "\nPosition: "
					+ new Integer(BrickGame.bricks.get(i).pos_x).toString()
					+ " "
					+ new Integer(BrickGame.bricks.get(i).pos_y).toString()
					+ " "
					+ new Integer(BrickGame.bricks.get(i).direction).toString()
					+ "\nBewegungen: "
					+ new Integer(BrickGame.bricks.get(i).status_moves).toString()
					+ "\nHindernisse Entdeckt: "
					+ new Integer(BrickGame.bricks.get(i).status_foundBlock)
							.toString() + "\n\n";
		}
		return out;
	}

	private static void newElements() {

		for (int i = 0; i < (FieldGame.size_x + 2); i++) {
			for (int p = 0; p < (FieldGame.size_y + 2); p++) {
				int[] neu = new int[5];
				neu[0] = getRightImageNumber(i, p);
				neu[1] = i * scale_x;
				neu[2] = (FieldGame.size_y + 1) * scale_y - scale_y * p;
				neu[3] = scale_x;
				neu[4] = scale_y;
				gui.add(neu);
			}
		}

		for (int i = 0; i < BrickGame.bricks.size(); i++) {

			int[] neu1 = new int[5];
			neu1[0] = -i;
			neu1[1] = (BrickGame.bricks.get(i).pos_x + 1) * scale_x + scale_x / 7;
			neu1[2] = (FieldGame.size_y + 1) * scale_y - scale_y
					* (BrickGame.bricks.get(i).pos_y + 1);
			neu1[3] = scale_y;
			neu1[4] = scale_y;
			gui.add(neu1);

			if (BrickGame.bricks.get(i).direction == 1) {
				int[] neu3 = new int[5];
				neu3[0] = 6;
				neu3[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * scale_x
						+ scale_x / 2 - scale_y / 4);
				neu3[2] = (FieldGame.size_y + 2) * scale_y - scale_y
						* (BrickGame.bricks.get(i).pos_y + 1);
				neu3[3] = scale_y / 2;
				neu3[4] = scale_y / 2;
				gui.add(neu3);
			} else if (BrickGame.bricks.get(i).direction == 2) {
				int[] neu4 = new int[5];
				neu4[0] = 7;
				neu4[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * scale_x - scale_y / 2);
				neu4[2] = (FieldGame.size_y + 1) * scale_y - scale_y
						* (BrickGame.bricks.get(i).pos_y + 1) + scale_y / 4;
				neu4[3] = scale_y / 2;
				neu4[4] = scale_y / 2;
				gui.add(neu4);
			} else if (BrickGame.bricks.get(i).direction == 3) {
				int[] neu5 = new int[5];
				neu5[0] = 8;
				neu5[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * scale_x
						+ scale_x / 2 - scale_y / 4);
				neu5[2] = (FieldGame.size_y) * scale_y - scale_y
						* (BrickGame.bricks.get(i).pos_y + 1) + scale_y / 2;
				neu5[3] = scale_y / 2;
				neu5[4] = scale_y / 2;
				gui.add(neu5);
			} else if (BrickGame.bricks.get(i).direction == 4) {
				int[] neu2 = new int[5];
				neu2[0] = 5;
				neu2[1] = (BrickGame.bricks.get(i).pos_x + 2) * scale_x;
				neu2[2] = (FieldGame.size_y + 1) * scale_y - scale_y
						* (BrickGame.bricks.get(i).pos_y + 1) + scale_y / 4;
				neu2[3] = scale_y / 2;
				neu2[4] = scale_y / 2;
				gui.add(neu2);
			}

		}
	}
}