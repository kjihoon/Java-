package tcp1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	int port;
	ServerSocket serversocket;
	Socket socket;
	OutputStream out;
	OutputStreamWriter outw;
	boolean flag= true;

	public Server() throws IOException {
		port = 7777;
		serversocket = new ServerSocket(port); // 특정포트를 이용하여 서버역활을 할것이다. 다른사람이 서버를사용하고있을때 사용불가
	}

	public void startServer() throws IOException {

		while (flag) {

			System.out.println("Start Server....");
			System.out.println("Ready Server....");
			try {

				socket = serversocket.accept(); // server쪽에서 누군가가(client) 들어오기전까지 실행안됨
				System.out.println("Accepted Server...." + socket.getInetAddress());
				out = socket.getOutputStream();
				outw = new OutputStreamWriter(out);
				outw.write("얘뜨라안녕??~~~~~~~~~~오늘도홧팅행><오늘금욜ㄷ이다ㅎㅎㅎㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ아넘나신낭햄보께근데오늘점심떡볶이먹을까사골만둣국머글까~");

			} catch (IOException e) {
				throw e;
			} finally {
				if(outw!=null) {
					outw.close();
				}
				if(socket!=null) {
				socket.close();
				}
			}
			
			System.out.println("End Server");

		} //while문

	}

	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server();
			server.startServer();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}