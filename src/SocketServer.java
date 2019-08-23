
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
		private final OutputStream os;
		private final InputStream is;
		
		public Handler(Socket socket) throws IOException {
			System.out.print("New Incomming Socket: " + socket.toString());
			this.socket = socket;
			this.out = new FileOutputStream(f);
			os = socket.getOutputStream();
			is = socket.getInputStream();
		}

		@Override
		public void run() {
			try (DataInputStream in = new DataInputStream(is);
					PrintWriter pw = new PrintWriter(os);
					ByteArrayOutputStream o = new ByteArrayOutputStream()){
				
				for (byte b = in.readByte(); ;b = in.readByte() ) {
					if(0x0A == b) {
					   String reqMsg = writeDown(o.toByteArray());
					   o.reset();
					   pw.write(reqMsg);
					   pw.flush();
					   socket.close();
					   break;
					}else {
						o.write(b);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public String writeDown(byte[] b) {
			String msg = new String(b);
			try {
				out.write(b);
				System.out.println("Data in size: " + b.length);
				System.out.println("Data is : " + msg);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return msg;
		}
		
	}
	
	
}
