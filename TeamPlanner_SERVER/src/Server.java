import java.util.ArrayList;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.*;

public class Server extends Thread{
	public static Logger log = Logger.getLogger("global");
	public static boolean running_server=false;
	public static ArrayList<SocketHandler> connected_sockets;
	
	public Server(){
		connected_sockets=new ArrayList<SocketHandler>();
	}
	
	public static Server getInstance(){
		return ServerInstantiator.INSTANCE;
	}
	
	private static class ServerInstantiator{
		private static final Server INSTANCE = new Server();
	}
	
	@Override
	public void run(){
		try{
			ServerSocket server_socket = new ServerSocket(10000);
			log.info("TeamPlanner server v1.0 - Socket instanziato sulla porta 10000; in attesa di client(s)...");
			running_server=true;
			while(running_server){
				Socket socket = server_socket.accept();
				log.info("Accettata una connessione, attendo comandi...");
				SocketHandler sh=new SocketHandler(socket);
				sh.start();
				Thread.sleep(50);
			}
			server_socket.close();
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
		}

	}
	
	public static void broadcastObject(SocketHandler sh, Object x) throws IOException{
		for(int i=0;i<connected_sockets.size();i++){
			if(connected_sockets.get(i)!=sh){
				connected_sockets.get(i).out.writeObject(x);
				connected_sockets.get(i).out.flush();
			}
		}
	}
	
	public static void broadcastInt(SocketHandler sh, int x) throws IOException{
		for(int i=0;i<connected_sockets.size();i++){
			if(connected_sockets.get(i)!=sh){
				connected_sockets.get(i).out.writeInt(x);
				connected_sockets.get(i).out.flush();
			}
		}
	}
}
