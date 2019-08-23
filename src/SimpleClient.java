import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 400; i++) {
//			Thread.currentThread().sleep(300);
			new Thread(new SocketSub(9909)).start();
		}
	}
}


class SocketSub implements Runnable{
//	private final String p7 = "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\r\n";
//	private final String p7 = "hello";
	private int port;
	public SocketSub(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			 final String ip = "127.0.0.1";
			 Socket socket = new Socket(ip, port);
			 OutputStream os = socket.getOutputStream();
			 InputStream is = socket.getInputStream();

//			 os.write(p7.getBytes());
		     os.write(new String("helloworld how are you\n").getBytes());
			 os.flush();
			 System.out.println("socket client send");
			 
			 int temp = -1;
			 System.out.println("Getting Response value following string");
			 StringBuffer sb = new StringBuffer();
			 for (temp = is.read(); -1 != temp ;temp = is.read() ) {
				 sb.append((char)temp);
			 }
//			 while( -1 != (temp=is.read())){
//				 sb.append((char)temp);
//			 }

			 System.out.println(sb.toString());
			 socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}