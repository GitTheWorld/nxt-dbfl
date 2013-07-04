package segway;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class segwayGui extends JFrame{

	public segwayGui() {

    	this.setTitle("Segway Bluetooth Steuerung - (o) Start - (p) Stop - Marcel Link");
	    String[][] data = {new String[]{"(q)Vorwärts Links", "(w)Vorwärts", "(e)Vorwärts Rechts"},
                new String[]{"(a)Linksdrehend","(s)Stop","(d)Rechtsdrehend"}, 
                new String[]{"(y)Rückwärts Links","(x)Rückwärts","(c)Rückwärts Rechts"}};
                
        String[] colNames = new String[]{"a","b","c"};
	    
        DefaultTableModel model = new DefaultTableModel(data,colNames);
        JTable table = new JTable(model);
        table.setFocusable(true);

        table.getTableHeader().setVisible(false);
        table.setRowHeight(100);
        table.getColumn("a").setCellRenderer(new ButtonRenderer());
        table.getColumn("a").setCellEditor(new ButtonEditor());
        table.getColumn("b").setCellRenderer(new ButtonRenderer());
        table.getColumn("b").setCellEditor(new ButtonEditor());
        table.getColumn("c").setCellRenderer(new ButtonRenderer());
        table.getColumn("c").setCellEditor(new ButtonEditor());
        
        JScrollPane sPane = new JScrollPane(table);
        sPane.setFocusable(true);

        getContentPane().add(sPane);
        this.setSize(700, 355);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setVisible(true);
        
		
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {

            	try {
            	if(e.getKeyCode() == KeyEvent.VK_Q){
				segwayMaster.brick.turnMoreLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_W){
            	segwayMaster.brick.moveForward();
            	 }else if(e.getKeyCode() == KeyEvent.VK_E){
            	segwayMaster.brick.turnMoreRight();
            	 }else if(e.getKeyCode() == KeyEvent.VK_A){
                segwayMaster.brick.turnLeft();
            	 }else if(e.getKeyCode() == KeyEvent.VK_S){
                segwayMaster.brick.moveStop();
            	 }else if(e.getKeyCode() == KeyEvent.VK_D){
                segwayMaster.brick.turnRight();
            	 }else if(e.getKeyCode() == KeyEvent.VK_Y){
            	segwayMaster.brick.turnMoreBackLeft();
            	 }else if(e.getKeyCode() == KeyEvent.VK_X){
            	segwayMaster.brick.moveBackward();
            	 }else if(e.getKeyCode() == KeyEvent.VK_C){
                segwayMaster.brick.turnMoreBackRight();
           	     }else if(e.getKeyCode() == KeyEvent.VK_O){
           	    segwayMaster.brick.Segway_Stop();
                segwayMaster.brick.Segway_Start();
            	 }else if(e.getKeyCode() == KeyEvent.VK_P){
                segwayMaster.brick.Segway_Stop();
         	    }
    			} catch (Exception e1) {}
            
            }
            public void keyReleased(KeyEvent e) {
              
            }
        }); 
        
    }

}

class ButtonRenderer implements TableCellRenderer {
    
    JButton button = new JButton();

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        button.setText(value.toString());
        button.setToolTipText("Press " + value.toString());
        return button;
    }
}

@SuppressWarnings("serial")
class ButtonEditor  extends AbstractCellEditor implements TableCellEditor {
    JButton button;
    String txt;
    public ButtonEditor(){
        super();
        button = new JButton();
        button.setFocusable(true);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  try {
				if(button.getText().equals("(a)Linksdrehend"))
				{
				segwayMaster.brick.turnLeft();
				}
				else if(button.getText().equals("(d)Rechtsdrehend"))
				{
				segwayMaster.brick.turnRight();
				}
				else if(button.getText().equals("(s)Stop"))
				{
				segwayMaster.brick.moveStop();
				}
				else if(button.getText().equals("(w)Vorwärts"))
				{
				segwayMaster.brick.moveForward();
				}
				else if(button.getText().equals("(x)Rückwärts"))
				{
				segwayMaster.brick.moveBackward();
				}
				else if(button.getText().equals("(q)Vorwärts Links"))
				{
				segwayMaster.brick.turnMoreLeft();
				}
				else if(button.getText().equals("(e)Vorwärts Rechts"))
				{
				segwayMaster.brick.turnMoreRight();
				}
				else if(button.getText().equals("(y)Rückwärts Links"))
				{
				segwayMaster.brick.turnMoreBackLeft();
				}
				else if(button.getText().equals("(c)Rückwärts Rechts"))
				{
				segwayMaster.brick.turnMoreBackRight();
				}
			} catch (Exception e1) {}
          }
        });
    }

    public Object getCellEditorValue() {
        return null;
    }

    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    public void cancelCellEditing() {}

    public void addCellEditorListener(CellEditorListener l) {}

    public void removeCellEditorListener(CellEditorListener l) {}

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        txt = (value == null) ? "" : value.toString();
        button.setText(txt);
        return button;
    }
} 