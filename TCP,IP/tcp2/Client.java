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
	
	public void connect(){    //������ �����ϴµ� ���� ������ ��� �ֱ������� ��� ��û
		
		boolean flag2 = true;
		while(flag2) {
		
		try {
			socket = new Socket(ip,port);   //���� �׾������� ConnectionException/while�� ��� ���鼭 �ٽ� ���� ��û
			if(socket!=null && socket.isConnected()) {     //���� ���� �����ϸ� �� �ݺ��� ����������
				break;
			}
			
		} catch (IOException e) {
			System.out.println("Retry");
			try {
				Thread.sleep(2000);         //���������� ������ �ٽ� ��û
			} catch (InterruptedException e1) {
				
			}   
		}   //�ٱ� catch
		
		
		} //while��	
	}
	
	private void startClient() throws UnknownHostException,IOException {
		try {
			System.out.println("Connected");
			in = socket.getInputStream();
			inr = new InputStreamReader(in);
			br = new BufferedReader(inr);
			String str = br.readLine();
			System.out.println(str);
					
		} catch (UnknownHostException e) {   //�߸��� ip address 
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
