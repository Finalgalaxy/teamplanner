import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

public class MainFrame_CLIENT extends JFrame {
	public static Logger log = Logger.getLogger("global");
	private static final long serialVersionUID = 1L;
	private JTextArea jtextarea_log;
	public CorePanel core_panel;
    private OptionPanel option_panel;
	private Client c;
	
	public static MainFrame_CLIENT getInstance(){
		return MainFrameInstantiator.INSTANCE;
	}
	
	private static class MainFrameInstantiator{
		private static final MainFrame_CLIENT INSTANCE = new MainFrame_CLIENT();
	}

	public MainFrame_CLIENT(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(200,100,820,530);
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

		JButton button_somma = new JButton("Invia comando somma");
		button_somma.setBounds(0,200,300,50);
		add(button_somma);
		button_somma.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						execute_somma();
					}
				});
			}
		});

		JButton button_ciao = new JButton("Invia comando ciao");
		button_ciao.setBounds(button_somma.getX()+button_somma.getWidth()+20,200,300,50);
		add(button_ciao);
		button_ciao.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						execute_ciao();
					}
				});
			}
		});

		log.addHandler(new LogHandler(jtextarea_log));

		core_panel = new CorePanel();
		core_panel.setBounds(0,button_ciao.getY()+button_ciao.getHeight()+20,800,200);
		
		option_panel = new OptionPanel();
		option_panel.setBounds(jscrollpane.getX()+jscrollpane.getWidth()+20,0,200,420);
		
		add(core_panel);
		add(option_panel);
		
		setVisible(true);

		c = Client.getInstance();
		c.start();



	}

	private void execute_somma(){
		new Thread(new Runnable() {
			public void run() {
				execute_somma_dispatched();
			}
		}).start();
		//jtextarea_log.setCaretPosition(jtextarea_log.getDocument().getLength());
	}

	private void execute_somma_dispatched(){
		try{
			//c.initSocket();
			for(int i=0;i<5;i++){
				c.out.writeObject(new String("somma"));
				c.out.writeInt(2+i);
				c.out.writeInt(5+i);
				c.out.flush();
				
				//if(answer==-1) log.info("(errore nell'invocazione del metodo somma!)");
			}
			//c.closeSocket();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.info("Errore UnknownHostException (host sconosciuto?): "+e.getMessage());
			//e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.info("Errore Throwable "+e.getMessage());
			//e.printStackTrace();
		}
	}


	private void execute_ciao(){
		new Thread(new Runnable() {
			public void run() {
				execute_ciao_dispatched();
			}
		}).start();
		//jtextarea_log.setCaretPosition(jtextarea_log.getDocument().getLength());
	}

	private void execute_ciao_dispatched(){
		try{
			//c.initSocket();
			c.out.writeObject(new String("ciao"));
			c.out.flush();

			//c.closeSocket();
		}catch(UnknownHostException e){
			// TODO Auto-generated catch block
			log.info("Errore UnknownHostException (host sconosciuto?): "+e.getMessage());
			//e.printStackTrace();
		}catch(Throwable e){
			// TODO Auto-generated catch block
			log.info("Errore Throwable "+e.getMessage());
			//e.printStackTrace();
		}
	}


	public static void main(String args[]){
		MainFrame_CLIENT.getInstance();
	}

}
