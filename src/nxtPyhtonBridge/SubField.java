package nxtPyhtonBridge;

public class SubField {
		public int x;
		public int y;
		public int direction;
		public double distance;
		public boolean inList;
		public boolean endPoint;

		
		public SubField(int x, int y, int direction, double distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
			this.direction = direction;
			this.inList = true;
			this.endPoint = false;
		}
}
