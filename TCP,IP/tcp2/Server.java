package tcp2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	int port;
	ServerSocket serversocket;
	Socket socket = null;
	boolean flag = true;

	public Server() throws IOException {
		port = 7777;
		serversocket = new ServerSocket(port); // Ư����Ʈ�� �̿��Ͽ� ������Ȱ�� �Ұ��̴�. �ٸ������ ����������ϰ������� ���Ұ�
	}

	// Accept Client Socket And
	// Sender THread Create(Send Socket) and Start
	//Ŭ���̾�Ʈ ���� accept�ϰ� ���� ������ �����ؼ� 10���Ŀ� �ƿ�ǲ
	private void startServer() {

		while (true) {

			try {
				socket = serversocket.accept();
				Sender sender = new Sender(socket);
				Thread t = new Thread(sender);
				t.start();
				

			} catch (IOException e) {
				e.printStackTrace();
			}

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
