import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class CorePanel extends JPanel implements MouseListener{
	public static Logger log = Logger.getLogger("global");
	private static final long serialVersionUID = 1L;
	private ArrayList<Point> circles_points;
	private Client c;

	public CorePanel(){
		circles_points = new ArrayList<Point>();
		setLayout(null);
		setBounds(0,0,800,200);
		setPreferredSize(new Dimension(800,200));
		setSize(getPreferredSize());
		addMouseListener(this);
		c=Client.getInstance();
	}

	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		g2d.setBackground(Color.BLACK);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 800, 200);
		g2d.setBackground(Color.WHITE);
		g2d.setColor(Color.WHITE);

		if(!circles_points.isEmpty()){
			for(int i=0;i<circles_points.size();i++){
				Point p = circles_points.get(i);
				g2d.drawOval(p.x-10, p.y-10, 20, 20);
			}
		}
		g2d.drawLine(5, 5, 195, 195);
		g2d.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		try {
			addCircle(new Point(arg0.getX(), arg0.getY()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addCircle(Point p) throws IOException{
		circles_points.add(p);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		circles_points.add(new Point(arg0.getX(), arg0.getY()));
		try {
			c.out.writeObject("drawCircle");
			c.out.writeInt(arg0.getX());
			c.out.writeInt(arg0.getY());
			c.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
