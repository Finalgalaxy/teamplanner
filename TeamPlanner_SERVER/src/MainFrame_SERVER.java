import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class MainFrame_SERVER extends JFrame {
	public static Logger log = Logger.getLogger("global");
    private static final long serialVersionUID = 1L;
    private JTextArea jtextarea_log;
    public CorePanel core_panel;
    private OptionPanel option_panel;
 
	
	public static MainFrame_SERVER getInstance(){
		return MainFrameInstantiator.INSTANCE;
	}
	
	private static class MainFrameInstantiator{
		private static final MainFrame_SERVER INSTANCE = new MainFrame_SERVER();
	}
    
	public MainFrame_SERVER(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(200,200,1030,510);
		setAlwaysOnTop(true);

		jtextarea_log=new JTextArea();
		//jtextarea_log.setPreferredSize(new Dimension(800,100));
		//jtextarea_log.setSize(jtextarea_log.getPreferredSize());
		jtextarea_log.setBounds(0,0,jtextarea_log.getWidth(),jtextarea_log.getHeight());
		jtextarea_log.setLineWrap(true);
		JScrollPane jscrollpane = new JScrollPane(jtextarea_log,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscrollpane.setBounds(jtextarea_log.getBounds());
		jscrollpane.setPreferredSize(new Dimension(800,200));
		jscrollpane.setSize(jscrollpane.getPreferredSize());
		jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		((DefaultCaret) jtextarea_log.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(jscrollpane);
		log.addHandler(new LogHandler(jtextarea_log));
		
		core_panel = new CorePanel();
		core_panel.setBounds(0,jscrollpane.getY()+jscrollpane.getHeight()+20,800,200);
		
		option_panel = new OptionPanel();
		option_panel.setBounds(jscrollpane.getX()+jscrollpane.getWidth()+20,0,200,420);
		
		add(core_panel);
		add(option_panel);
		setVisible(true);
	}
	
	public static void main(String args[]){
		MainFrame_SERVER.getInstance();
		Server.getInstance().start();
	}

}
