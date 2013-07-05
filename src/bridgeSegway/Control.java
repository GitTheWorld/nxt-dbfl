package bridgeSegway;

import nxtPyhtonBridge.Brick;

public class Control extends Brick {

	// Need Time to stabilized
	private int sleep = 1000;

	public Control(String name) throws Exception {
		super(name,false);
	}

	public void moveForward() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 3, -1);
	}

	public void moveBackward() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 4, -1);
	}

	public void turnRight() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 1, -1);
	}

	public void turnLeft() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 0, -1);
	}

	public void turnMoreLeft() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 5, -1);
	}

	public void turnMoreRight() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 6, -1);
	}

	public void turnMoreBackLeft() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 7, -1);
	}

	public void turnMoreBackRight() throws Exception {
		moveStop();
		Thread.sleep(sleep);
		writeMailbox(0, 8, -1);
	}

	public void moveStop() throws Exception {
		writeMailbox(0, 2, -1);
	}

	public void Segway_Start() throws Exception {
		start();
	}

	public void Segway_Stop() throws Exception {
		stop();
	}

}
