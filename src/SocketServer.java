
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

	static final int port = 9909;
	static ServerSocket server;
	static boolean shutdown = false;
	static ExecutorService pool = Executors.newCachedThreadPool();
	
	public static void main(String[] msg) throws Exception {
		server = new ServerSocket(port);
		System.out.print("Socket Server Start!");
		while(!shutdown) {
			pool.execute(new Handler(server.accept())); ;
		}
		
	}
	
	static class Handler implements Runnable{
		
		private final Socket socket;
		private final File f = new File("data.log");
		private final FileOutputStream out;
		
		public Handler(Socket socket) throws FileNotFoundException {
			System.out.print("New Incomming Socket: " + socket.toString());
			this.socket = socket;
			this.out = new FileOutputStream(f);
		}

		@Override
		public void run() {
			try (DataInputStream in = new DataInputStream(socket.getInputStream()) ){
				ByteArrayOutputStream o = new ByteArrayOutputStream();
				for (byte b = in.readByte(); ;b = in.readByte() ) {
					switch(b) {
					case 0x0A: o.write(b);
							   parser(o.toByteArray());
							   o.reset();
							   break;
					default: o.write(b);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void parser(byte[] b) {
			try {
				System.out.print("Data in size: " + b.length);
				out.write(b);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
