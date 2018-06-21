package tcp3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	class ReceiverAndSender implements Runnable {
		Socket socket;
		OutputStream out = null;
		DataOutputStream dos = null;
		InputStream in = null;
		DataInputStream dis = null;

		public ReceiverAndSender() {
		}

		public ReceiverAndSender(Socket socket) throws IOException {
			this.socket = socket;
			in = socket.getInputStream();
			dis = new DataInputStream(in);
			out = socket.getOutputStream();
			dos = new DataOutputStream(out);
		}

		@Override
		public void run() {
			try {
				String msg = dis.readUTF();
				System.out.println(socket.getInetAddress() + " :: " + msg);			
				dos.writeUTF("서버로부터온 메세지");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (dos!= null) {
					try {
						dos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}

	int port;
	ServerSocket serverSocket;
	boolean flag;

	public Server() throws IOException {
		port = 7777;
		flag = true;
		serverSocket = new ServerSocket(port);
	}

	public void startServer() {
		while (flag) {
			try {
				System.out.println("Ready server. . .");
				Socket socket = serverSocket.accept();
				ReceiverAndSender rns = new ReceiverAndSender(socket);
				new Thread(rns).start();
				System.out.println("Accepted client . . .");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
