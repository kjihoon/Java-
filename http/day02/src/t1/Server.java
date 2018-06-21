package t1;

import java.util.Scanner;

public class Server {

	
	public void startServer(){
		
		  boolean flag = true;
		
          Scanner client = new Scanner(System.in);
          while(flag) {
        
        	  System.out.println("Server Ready");
        	  String msg = client.nextLine();
        	  System.out.println(msg);
        	  
        	  //Receiver Thread
        	  Receiver receiver = new Receiver(msg);
        	  receiver.start();
        	  
        	  
          }
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Server Start");
		new Server().startServer();
		System.out.println("Server stop");
	}

	
	
	//요청을 받아 처리하고 전송
	class Receiver extends Thread{

		String msg;
		
		public Receiver(){
			
		}
		public Receiver(String msg){
			this.msg = msg;
		}
		
		@Override
		public void run() {
		
			for(int i=0; i<10; i++) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(i);
			}
			
			System.out.println(msg+" Completed");
			
			//Sender Thread를 통해 Client에게 전송
			Sender sender = new Sender(msg);
			sender.start();
			
		}
		
		
		
	}  //Recever 클래스
	
	
    class Sender extends Thread{

    	String msg;
    	public Sender(){
    		
    	}
    	
    	public Sender(String msg) {
    	   this.msg=  msg;	
    	}
    	
		@Override
		public void run() {
			
			for(int i=0; i<10; i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(i);
			}
			
			System.out.println(msg+": Send Completed");

			
			
		}
    	
    	
    	
    }
	
	
	
} //Server 클래스




