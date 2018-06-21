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
		serversocket = new ServerSocket(port); // Ư����Ʈ�� �̿��Ͽ� ������Ȱ�� �Ұ��̴�. �ٸ������ ����������ϰ������� ���Ұ�
	}

	public void startServer() throws IOException {

		while (flag) {

			System.out.println("Start Server....");
			System.out.println("Ready Server....");
			try {

				socket = serversocket.accept(); // server�ʿ��� ��������(client) ������������ ����ȵ�
				System.out.println("Accepted Server...." + socket.getInetAddress());
				out = socket.getOutputStream();
				outw = new OutputStreamWriter(out);
				outw.write("��߶�ȳ�??~~~~~~~~~~���õ�ȱ����><���ñݿ礧�̴٤��������������������������Ƴѳ��ų��ܺ����ٵ��������ɶ����̸������񸸵Ա��ӱ۱�~");

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

		} //while��

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