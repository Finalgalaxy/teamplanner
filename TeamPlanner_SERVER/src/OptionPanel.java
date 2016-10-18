import java.awt.*;
import javax.swing.*;

public class OptionPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public OptionPanel(){
		setLayout(null);
		setBounds(0,0,200,420);
		setPreferredSize(new Dimension(200,420));
		setSize(getPreferredSize());
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.ORANGE);
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(0, 0, 200, 420);
		g2d.dispose();
	}

}
