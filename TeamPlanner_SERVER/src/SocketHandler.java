import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketHandler extends Thread{
	public static Logger log = Logger.getLogger("global");
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;
	boolean running_socket=false;

	public SocketHandler(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run(){
		try{
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			Server.connected_sockets.add(this);
			running_socket=true;
			while(running_socket){
				Object obj = in.readObject();
				if(obj instanceof String){
					String x=(String)obj;
					if(x.equals("somma")){
						log.info("Server: comando somma OK. Eseguo la somma...");
						int a = in.readInt();
						int b = in.readInt();

						Thread.sleep(1000);
						out.writeObject("somma");
						out.writeInt((a+b));
						out.flush();
					}else if(x.equals("ciao")){
						out.writeObject("Salve!");
						out.flush();
						//Server.broadcast(this,"Non sto parlando con te!");
					}else if(x.equals("drawCircle")){
						int xp=in.readInt();
						int yp=in.readInt();
						log.info("drawCircle at: "+xp+","+yp);
						MainFrame_SERVER.getInstance().core_panel.addCircle(this,new Point(xp,yp));
						//Server.broadcast(this,"Non sto parlando con te!");
					}else if(x.equals("socket_close")){
						log.info("Server: SOCKET_CLOSE");
						out.writeObject("SOCKET_CLOSED");
						out.flush();
						running_socket=false;
					}else if(x.equals("server_close")){
						log.info("Server: CLOSE");
						out.writeObject("SERVER_CLOSED");
						out.flush();
						running_socket=false;
						Server.running_server=false;
					}
				}
				Thread.sleep(50);
			}
			out.close();
			in.close();
			socket.close();
			Server.connected_sockets.remove(this);
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
