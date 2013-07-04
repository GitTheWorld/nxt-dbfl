package field;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;


	class fieldComponent extends JComponent {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	
	        int one,two,y_plus,x_plus,x,y;
	        
			  x=(int) (this.getWidth()/((fieldConfig.field_Sx+2)*1.428571429));
			  y=this.getHeight()/(fieldConfig.field_Sy+2);
			  if(x<y){one=x; two=(int)(x*1.428571429);}else{one=y;two=(int)(y*1.428571429);}
			  x_plus=(two*(fieldConfig.field_Sx+2)-this.getWidth())/2;
			  y_plus=(one*(fieldConfig.field_Sy+2)-this.getHeight())/2;
	        
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.drawImage(fieldGui.display_image, -x_plus, -y_plus,two*(fieldConfig.field_Sx+2),one*(fieldConfig.field_Sy+2),this);
	    
	    }
	}
