import java.util.logging.Logger;
import java.awt.Point;
import java.io.*;
import java.net.*;

public class Client extends Thread {
	public static Logger log = Logger.getLogger("global");
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;

	public Client(){

	}
	public static Client getInstance(){
		return ClientInstantiator.INSTANCE;
	}
	
	private static class ClientInstantiator{
		private static final Client INSTANCE = new Client();
	}

	public void run(){
		// Socket che legge dal server i messaggi di sincronizzazione per le operazioni di disegno ecc.
		try {
			initSocket();

			while(true){
				Object obj = in.readObject();
				if(obj instanceof String){
					String x = (String)obj;
					if(x.equals("somma")){
						int answer = in.readInt();
						log.info("Risposta alla funzione somma: "+answer);
					}else if(x.equals("drawCircle")){

						log.info("X="+x);
						int xp = in.readInt();

						log.info("xp="+xp);
						int yp = in.readInt();

						log.info("yp="+yp);
						log.info("PUNTO:"+xp+","+yp);
						MainFrame_CLIENT.getInstance().core_panel.addCircle(new Point(xp,yp));
						log.info("Ricevuta una richiesta di disegna di un cerchio: "+x);
					}else{
						log.info("Il server ti ha inviato: "+x);
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initSocket() throws IOException{
		socket = new Socket("localhost",10000);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void closeSocket() throws IOException{
		log.info("Tento di chiudere la connessione...");
		out.writeObject("socket_close");
		out.flush();
		try {
			if(((String)in.readObject()).equals("SOCKET_CLOSED"))
				log.info("Socket chiusa.");
			else
				log.info("Errore durante la chiusura della socket!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.close();
		in.close();
		socket.close();
	}

	public static void main(String args[]){
		Client.getInstance().start();
	}
}
