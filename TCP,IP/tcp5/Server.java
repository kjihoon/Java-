package tcp5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Server {

	ServerSocket serverSocket;
	boolean flag = true;

	// Ŭ���̾�Ʈ ������ DataoutputStream �־��ٰŴ�
	ArrayList<DataOutputStream> list = new ArrayList<>();

	public Server() throws IOException {

		serverSocket = new ServerSocket(9999);
		System.out.println("Server Ready");

	}

	// Client Accept
	// while�� ���鼭 Ŭ���̾�Ʈ ���� ������ ���� �����ϵ���
	// socket���� Receiver ������ ���� ����
	public void start() throws IOException {

		while (flag) {
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress() + " Connected");
			
			Receiver r = new Receiver(socket);
			r.start();
			

		}
	}

	// ������ ���ϸ��� ��ǲ��Ʈ�� �����ϰ� ������ �޵��� / ������ ���ϸ��� �ƿ�ǲ��Ʈ���� ���⼭ �޾Ƽ� arraylist�� �־��ֱ⸸ �Ѵ�
	class Receiver extends Thread {

		InputStream in;
		DataInputStream dis;
		OutputStream out;
		DataOutputStream dos;

		public Receiver(Socket socket) throws IOException {

			in = socket.getInputStream();
			dis = new DataInputStream(in);

			out = socket.getOutputStream();
			dos = new DataOutputStream(out);

			list.add(dos);
			System.out.println();

		}

		@Override
		public void run() {

			while (dis != null) {

				String str="";
				
				try {
					str = dis.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (str.equals("q")&&str!=null) {

					break;
				}

				System.out.println(str);
			
				//send Spring Server
				SendHttp http = new SendHttp(str);
				http.start();
				

			} //while
			
			//Ŭ���̾�Ʈ�� �����ϰڴٰ��ϸ� receiver���� ����޼��� �ް� ����Ʈ�� ����Ǿ��ִ� dataoutputstream ����
		    list.remove(dos);
		    if(dos!=null) {
		    	try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    
		    if(dis!=null) {
		    	try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		}//run

	}

	

	//Ŭ���̾�Ʈ�� �Է��� ���ڿ� ,�� split�ؼ� speed�� temp��� �̸����� webServer�� �Ѱ���
	class SendHttp extends Thread{
		String speed;
		String temp;
		String msg[];
		String urls="http://127.0.0.1/ws/main.do";
		
		public SendHttp(String msg) {
			this.msg = msg.split(",");
			speed = this.msg[0].trim();
			temp = this.msg[1].trim();
			
		}
		
		@Override
		public void run() {
		
			
			urls += "?speed="+speed+"&temp="+temp;
			
			try {
				URL url = new URL(urls);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setConnectTimeout(5000);
				con.getInputStream();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = null;

		try {
			server = new Server();
			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
