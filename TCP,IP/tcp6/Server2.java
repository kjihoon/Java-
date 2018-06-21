package tcp6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import java.util.Iterator;
import java.util.Map;

public class Server2 extends Thread{

 ServerSocket serverSocket;
 boolean flag = true;
 boolean rflag = true;
 HashMap<DataOutputStream,InetAddress> list =  new HashMap<>();
 
 public Server2() throws IOException {
  // Create ServerSocket ...
  serverSocket = new ServerSocket(8888);
  System.out.println("Ready Server...");
 }
 
 @Override
 public void run() {
  // Accept Client Connection ...
  try {
   while(flag) {
    System.out.println("Ready Accept");
    Socket socket =  serverSocket.accept();
    System.out.println(socket.getInetAddress()+"Connected");
    new Receiver(socket).start();
   }
  }catch(Exception e) {
   e.printStackTrace();
  }
 }

 class Receiver extends Thread{
  
  InputStream in;
  DataInputStream din;
  OutputStream out;
  DataOutputStream dout;
  Socket socket;
  InetAddress inetAddress;
  
  public Receiver(Socket socket) {

	try {

    this.socket = socket;
    in = socket.getInputStream();
    din = new DataInputStream(in);
    out = socket.getOutputStream();
    dout = new DataOutputStream(out);
    list.put(dout,socket.getInetAddress());
    
    System.out.println("Connected Count:"+list.size());
   } catch (IOException e) {
    e.printStackTrace();
   }
  } // end Recevier 
 
  
  @Override
  public void run() {
			try {
				while (rflag) {

					if (socket.isConnected() && din != null && din.available() > 0) {
						String str = din.readUTF();
						if (str != null && str.equals("q")) {
							list.remove(dout);
							System.out.println(list.size());
							break;
						}
						System.out.println(str);
						sendAll(str,socket);
					}

				}

			}

	catch(Exception e) {
    e.printStackTrace();
   } finally {
    
    try {
     Thread.sleep(200);
    } catch (InterruptedException e1) {
     e1.printStackTrace();
    }
    if(dout != null) {
     try {
      dout.close();
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
    if(din != null) {
     try {
      din.close();
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
    if(socket != null) {
     try {
      socket.close();
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
    
   }
  }
  
 }
 
 public void sendAll(String msg,Socket socket) {
  Sender sender = new Sender();
  sender.setMeg(msg,socket);
  sender.start();
 }
 
 // Send Message All Clients
 class Sender extends Thread{
  
  Socket socket;
  String msg;
  
  public void setMeg(String msg,Socket socket) {
   this.msg = msg;
   this.socket = socket;
  }
  
  @Override
  public void run() {
	
   
  Set set = list.entrySet();
  Iterator it = set.iterator();
	  
   try { 
    if(!list.isEmpty() && list.size() >= 0) {
     while(it.hasNext()) {
    	 Map.Entry e = (Map.Entry) it.next();
      if(e.getKey()!= null) {
           if(socket.getInetAddress()!=e.getValue()) { 
    	  ((DataOutputStream) e.getKey()).writeUTF(socket.getInetAddress()+": "+msg); 
           }
    	  
      }
     }
    }
   }catch(Exception e) {
    //e.printStackTrace();
   }
  }
 }

 public static void main(String[] args) {
  Server2 server = null;
  try {
   server = new Server2();
   server.start();
  } catch (IOException e) {
   e.printStackTrace();
  }

 }

}

