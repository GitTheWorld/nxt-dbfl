package bridgeFieldControl;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

class Component extends JComponent {

	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int one, two, y_plus, x_plus, x, y;

		x = (int) (this.getWidth() / ((FieldGame.size_x + 2) * Gui.dif_xy));
		y = this.getHeight() / (FieldGame.size_y + 2);
		if (x < y) {
			one = x;
			two = (int) (x * Gui.dif_xy);
		} else {
			one = y;
			two = (int) (y * Gui.dif_xy);
		}
		x_plus = (two * (FieldGame.size_x + 2) - this.getWidth()) / 2;
		y_plus = (one * (FieldGame.size_y + 2) - this.getHeight()) / 2;

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Gui.display_image, -x_plus, -y_plus, two
				* (FieldGame.size_x + 2), one * (FieldGame.size_y + 2), this);

	}
}
