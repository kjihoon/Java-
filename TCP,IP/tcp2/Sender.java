package tcp2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Sender implements Runnable {


	OutputStream out;
	OutputStreamWriter outw;
	Socket socket;

	public Sender() {

	}

	public Sender(Socket socket) throws IOException {
		this.socket = socket;
		out = socket.getOutputStream();
		outw = new OutputStreamWriter(out);
	}

	@Override
	public void run() {

		System.out.println("Accepted Server...." + socket.getInetAddress());

		try {
			Thread.sleep(10000);
			outw.write("ぞぞぞぞ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outw != null) {
				try {
					outw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		System.out.println("End Server");

	}


}


