package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Http1 {

	
	
	public static void main(String[] args) throws UnknownHostException  {
		// TODO Auto-generated method stub
		
	    InetAddress ia = null;
	    ia = InetAddress.getByName("localhost");
	    System.out.println(ia.getHostAddress());
	    System.out.println(ia.getHostName());
		
	    URL url = null;
	    String address = "http://127.0.0.1/index.html";
	    //스트링은 한번 설정한 내용을 변경할 수 없기 때무무네에ㅔ
	    StringBuffer sb = new StringBuffer();
	    String str = null;
	    
	    try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    InputStreamReader in = null;
	    BufferedReader br = null;
	    
	    try {
			in = new InputStreamReader(url.openStream());
			br = new BufferedReader(in);
			while(true) {
				str = br.readLine();
				if(str==null) {
					break;
				}
				sb.append(str+"\n");
			}
			
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    
	    System.out.println(sb.toString());
	    
	}

	
	
}
