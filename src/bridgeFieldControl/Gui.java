package bridgeFieldControl;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import nxtPyhtonBridge.Brick;
import nxtPyhtonBridge.Field;
import nxtPyhtonBridge.Tools;

@SuppressWarnings("serial")
class Gui extends JComponent {

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

	private static int x_scale = 1000;
	private static int y_scale = 700;

	public static JFrame Twindow;
	public static JFrame info;
	public static int count = 0;
	public static String folder;
	public static ArrayList<int[]> gui;
	public static ArrayList<Thread> pro;
	public static Image display_image;
	public static JButton stopButton;

	public static void main(String[] args) {
    try {
		Brick.path = args[0];
		
		FieldGame.init(10, 8,1,1.2);
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

		coords[1][0]=2;
		coords[1][1]=4;
		coords[1][2]=2;

		coords[2][0]=5;
		coords[2][1]=3;
		coords[2][2]=3;
		
		coords[3][0]=1;
		coords[3][1]=5;
		coords[3][2]=4;
		
		BrickGame.init(names, coords,true);
		
		Gui.init();
		
		while(true){
		System.out.println("main: this is the GUI test, wait for end...");
		if(Status.stop){Status.stop();}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		int one, two, y_plus, x_plus, x, y;
		x = (int) ((Twindow.getContentPane().getWidth()) / ((Field.size_x) + 2) * 1.428571429);
		y = (Twindow.getContentPane().getHeight()) / (Field.size_y + 2);
		if (x < y) {
			one = x;
			two = (int) (x * 1.428571429);
		} else {
			one = y;
			two = (int) (y * 1.428571429);
		}
		x_plus = (two * (Field.size_x + 2) - (Twindow.getContentPane().getWidth())) / 2;
		y_plus = (one * (Field.size_y + 2) - (Twindow.getContentPane().getHeight())) / 2;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(display_image, x_plus, y_plus, two, one, this);
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

		BufferedImage awtImage = new BufferedImage((Field.size_x + 2)
				* x_scale, (Field.size_y + 2) * y_scale,
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

		Runnable r = new Writer(awtImage, getPath());
		Thread t = new Thread(r);
		t.start();
		pro.add(t);
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

		if (x == 0 || y == 0 || x > Field.size_x || y > Field.size_y) {
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

		topPanel.setLayout(new BorderLayout());
		info.getContentPane().add(topPanel);
		info.getContentPane().add(btnPanel);
		topPanel.add(infotext, BorderLayout.CENTER);
		stopButton = new JButton("Fortsetzen");
		stopButton.addActionListener(new TheButton());
		btnPanel.add(stopButton);
		info.getContentPane().add(topPanel, BorderLayout.CENTER);
		info.getContentPane().add(btnPanel, BorderLayout.SOUTH);
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
			System.out.println(Brick.path);

			around = ImageIO.read(new File(Brick.path+ "/icons/around.png"));
			unknown = ImageIO.read(new File(Brick.path+ "/icons/unknown.png"));
			free = ImageIO.read(new File(Brick.path + "/icons/free.png"));
			blocked = ImageIO.read(new File(Brick.path+"/icons/blocked.png"));
			arrow_right = ImageIO.read(new File(Brick.path+"/icons/arrow.png"));
			arrow_down = Tools.rotateImage(arrow_right, 90);
			arrow_left = Tools.rotateImage(arrow_down, 90);
			arrow_up = Tools.rotateImage(arrow_left, 90);
			nxt = ImageIO.read(new File(Brick.path + "/icons/nxt.png"));
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
					+ new Integer(Field.size_x * Field.size_y).toString()
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

		for (int i = 0; i < (Field.size_x + 2); i++) {
			for (int p = 0; p < (Field.size_y + 2); p++) {
				int[] neu = new int[5];
				neu[0] = getRightImageNumber(i, p);
				neu[1] = i * x_scale;
				neu[2] = (Field.size_y + 1) * y_scale - y_scale * p;
				neu[3] = x_scale;
				neu[4] = y_scale;
				gui.add(neu);
			}
		}

		for (int i = 0; i < BrickGame.bricks.size(); i++) {

			int[] neu1 = new int[5];
			neu1[0] = -i;
			neu1[1] = (BrickGame.bricks.get(i).pos_x + 1) * x_scale + x_scale / 7;
			neu1[2] = (Field.size_y + 1) * y_scale - y_scale
					* (BrickGame.bricks.get(i).pos_y + 1);
			neu1[3] = y_scale;
			neu1[4] = y_scale;
			gui.add(neu1);

			if (BrickGame.bricks.get(i).direction == 1) {
				int[] neu3 = new int[5];
				neu3[0] = 6;
				neu3[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * x_scale
						+ x_scale / 2 - y_scale / 4);
				neu3[2] = (Field.size_y + 2) * y_scale - y_scale
						* (BrickGame.bricks.get(i).pos_y + 1);
				neu3[3] = y_scale / 2;
				neu3[4] = y_scale / 2;
				gui.add(neu3);
			} else if (BrickGame.bricks.get(i).direction == 2) {
				int[] neu4 = new int[5];
				neu4[0] = 7;
				neu4[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * x_scale - y_scale / 2);
				neu4[2] = (Field.size_y + 1) * y_scale - y_scale
						* (BrickGame.bricks.get(i).pos_y + 1) + y_scale / 4;
				neu4[3] = y_scale / 2;
				neu4[4] = y_scale / 2;
				gui.add(neu4);
			} else if (BrickGame.bricks.get(i).direction == 3) {
				int[] neu5 = new int[5];
				neu5[0] = 8;
				neu5[1] = (int) ((BrickGame.bricks.get(i).pos_x + 1) * x_scale
						+ x_scale / 2 - y_scale / 4);
				neu5[2] = (Field.size_y) * y_scale - y_scale
						* (BrickGame.bricks.get(i).pos_y + 1) + y_scale / 2;
				neu5[3] = y_scale / 2;
				neu5[4] = y_scale / 2;
				gui.add(neu5);
			} else if (BrickGame.bricks.get(i).direction == 4) {
				int[] neu2 = new int[5];
				neu2[0] = 5;
				neu2[1] = (BrickGame.bricks.get(i).pos_x + 2) * x_scale;
				neu2[2] = (Field.size_y + 1) * y_scale - y_scale
						* (BrickGame.bricks.get(i).pos_y + 1) + y_scale / 4;
				neu2[3] = y_scale / 2;
				neu2[4] = y_scale / 2;
				gui.add(neu2);
			}

		}
	}
}