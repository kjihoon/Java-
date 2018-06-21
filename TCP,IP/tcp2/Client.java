package tcp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	String ip;
	int port;
	
	Socket socket;
	InputStream in;
	InputStreamReader inr;
	BufferedReader br;
	
	public Client() {
		
	}
	
	public Client(String ip, int port) throws UnknownHostException, IOException{
		this.ip = ip;
		this.port = port;
		connect();
		startClient();
	}
	
	public void connect(){    //서버에 연결하는데 연결 실패할 경우 주기적으로 계속 요청
		
		boolean flag2 = true;
		while(flag2) {
		
		try {
			socket = new Socket(ip,port);   //서버 죽어있으면 ConnectionException/while문 계속 돌면서 다시 접속 요청
			if(socket!=null && socket.isConnected()) {     //만약 연결 성공하면 이 반복문 빠져나간다
				break;
			}
			
		} catch (IOException e) {
			System.out.println("Retry");
			try {
				Thread.sleep(2000);         //조금지나고 서버에 다시 요청
			} catch (InterruptedException e1) {
				
			}   
		}   //바깥 catch
		
		
		} //while문	
	}
	
	private void startClient() throws UnknownHostException,IOException {
		try {
			System.out.println("Connected");
			in = socket.getInputStream();
			inr = new InputStreamReader(in);
			br = new BufferedReader(inr);
			String str = br.readLine();
			System.out.println(str);
					
		} catch (UnknownHostException e) {   //잘못된 ip address 
			throw e;
		} catch (IOException e) {   
			throw e;
		}finally {
			br.close();
			socket.close();
		}
		
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub

	  Client client = null;
	  try {
		client = new Client("70.12.114.136",7777);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

				
	}
	
	
	

}
