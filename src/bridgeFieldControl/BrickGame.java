package bridgeFieldControl;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import nxtPyhtonBridge.Brick;
import nxtPyhtonBridge.Tools;

public class BrickGame extends Brick {

	public static int d_move_power;
	public static int d_rotate_power;
	public static double d_start_points;
	public static double d_distance_from_me;
	public static double d_distance_from_enemy;
	public static double d_distance_to_other_base;
	public static double d_distance_to_other_multi;
	
	// Bluetooth configuration
	public static int bluetooth_mailboxSystem = 0; // Statusmeldungen (in/out)
	public static int bluetooth_mailboxGotIt = 1; // Empfing etwas (in/out)
	
	// brick and field control
	public static ArrayList<BrickGame> bricks;
	
	// Ai Konfiguration
	public int move_power;
	public int rotate_power;
	public double start_points;
	public double distance_from_me;
	public double distance_from_enemy;
	public double distance_to_other_base;
	public double distance_to_other_multi;
	// AI Konfiguration END

	// Individual files
	int id;
	public Image my_image;
	
	// position and direction
	public int pos_x;
	public int pos_y;
	public int direction;

	// statistic
	public int status_moves = 0;
	public int status_foundBlock = 0;
	
	private boolean needStart=true;
	private boolean isEnd=false;
	
	public static void init(String[] names, int[][] coords, boolean fake) throws Exception
	{
	bricks = new ArrayList<BrickGame>();
	for(int i=0;i<names.length;i++)
	{
	try {
	bricks.add(new BrickGame(names[i], coords[i][0], coords[i][1], coords[i][2], i,fake));
	} catch (Exception e) {
		System.out.println("main: cannot connect with "+names[i]);
		e.printStackTrace();
		Tools.displayOutput("Konnte nicht mit: "+ names[i] + " verbinden!!!","Error");
		System.exit(1);
	}
	}
	}
	

	// Basis Funktionen //
	public BrickGame(String name, int pos_x, int pos_y,int direction, int id,boolean fake) throws Exception {
		super(name,fake);
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.direction = direction;
		this.id = id;

		if(fake){return;}

		File f = new File(BrickGame.path + "/custom/" + name + ".png");
		if (f.exists()) {my_image = ImageIO.read(f);}

		try {
			System.out.println(name + ": load custom AI configuration");
			BufferedReader in = new BufferedReader(new FileReader(BrickGame.path + "/custom/" + name + ".txt"));
			String zeile = null;
			int i = 0;
			while ((zeile = in.readLine()) != null) {

				try {
					if (i == 0) {
						start_points = Double.parseDouble(zeile);
					}
					if (i == 1) {
						distance_from_me = Double.parseDouble(zeile);
					}
					if (i == 2) {
						distance_from_enemy = Double.parseDouble(zeile);
					}
					if (i == 3) {
						distance_to_other_base = Double.parseDouble(zeile);
					}
					if (i == 4) {
						distance_to_other_multi = Double.parseDouble(zeile);
					}
					System.out.println(name + ": " + i + "-"
							+ Double.parseDouble(zeile));
					i++;
				} catch (NumberFormatException ex) {
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println(name + ": load backup configuration because error");
						start_points = d_start_points;
						distance_from_me = d_distance_from_me;
						distance_from_enemy = d_distance_from_enemy;
						distance_to_other_base = d_distance_to_other_base;
						distance_to_other_multi = d_distance_to_other_multi;
		}

	}

	private void updatePosition() {
		if (direction == 1) {
			pos_y--;
		}
		if (direction == 2) {
			pos_x--;
		}
		if (direction == 3) {
			pos_y++;
		}
		if (direction == 4) {
			pos_x++;
		}
		System.out.println(name + ": new position (up) " + pos_x + " " + pos_y);
	}

	private void downdatePosition() {
		if (direction == 1) {
			pos_y++;
		}
		if (direction == 2) {
			pos_x++;
		}
		if (direction == 3) {
			pos_y--;
		}
		if (direction == 4) {
			pos_x--;
		}
		System.out.println(name + ": new position (down) " + pos_x + " "
				+ pos_y);
	}

	// Startup Sequenz zur Kontrollübernahme über Bluetooth
	public void startup() throws Exception {
		System.out.println(name + ": try to start software on NXT");
		Thread.sleep(300);
		stop();
		Thread.sleep(1000);
		start();
		Thread.sleep(300);
		downdatePosition();
		System.out.println(name + ": the nxt is now ready to start");

	}

	private void receive_id() throws Exception {
		status_moves++;
		int field_id = readMailbox(BrickGame.bluetooth_mailboxSystem, false, -1);
		if (field_id < 1 || field_id > 2) {
			return;
		}
		if (field_id == 2) {
			updatePosition();
			FieldGame.setField(2, pos_x, pos_y, id);
			System.out.println(name + ": receive field: " + field_id + " " + pos_x
					+ " " + pos_y);
			downdatePosition();
			direction = 0;
			status_foundBlock++;
			Gui.update();
		} else {
			updatePosition();
			FieldGame.setField(1, pos_x, pos_y, id);
			direction = 0;
			System.out.println(name + ": receive field: " + field_id + " " + pos_x
					+ " " + pos_y);
			System.out.println(name + ": new position: " + pos_x + " " + pos_y);
			Gui.update();
		}
	}

	public void action() throws Exception {
		if (isEnd){return;}
		
		if (needStart) {
			updatePosition();
			if (FieldGame.isFreeFromBricks(id, pos_x, pos_y)) {
				writeMailbox(BrickGame.bluetooth_mailboxSystem, direction, -1);
				needStart = false;
			}
			downdatePosition();
			return;
		}

		if (direction == 0) {
			if (Status.is_win()) {
				writeMailbox(BrickGame.bluetooth_mailboxSystem, 0, -1);
				isEnd = true;
				return;
			}
			int newdirection = FieldGame.getMove(id);
			if (newdirection < 1 || newdirection > 4) {return;}
			direction = newdirection;
			writeMailbox(BrickGame.bluetooth_mailboxSystem, newdirection, -1);
			Gui.update();
		} else {
			receive_id();
		}

	}

	public boolean is_free(int x, int y) {
		if (needStart) {return true;}
		if (pos_x == x && pos_y == y) {return false;}
		updatePosition();
		if (pos_x == x && pos_y == y) {downdatePosition();return false;}
		downdatePosition();
		return true;
	}

}
