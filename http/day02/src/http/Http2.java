package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Http2 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String name = "´ÙÇý";
		name = URLEncoder.encode(name,"UTF-8");
		
		String surl = "http://127.0.0.1/login?id=dahye&pwd=1111&name="+name;
		
		
		
		URL url = new URL(surl);
		URLConnection con = url.openConnection();
		con.setConnectTimeout(5000);  
		
		InputStream in = con.getInputStream();
		InputStreamReader ir = new InputStreamReader(in,"UTF-8");
		BufferedReader br = new BufferedReader(ir);
		
		String str = br.readLine();
		System.out.println(str);
		
		br.close();
		
		
	}

}
