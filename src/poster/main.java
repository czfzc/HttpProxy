package poster;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;

public class main {
	
	private final static int connectTime=5000; 

	public static void main(String[] args) throws IOException {
	
        String request = "GET / HTTP/1.1\r\n"+
                "Host: www.neu.edu.cn\r\n\r\n";
        
      //  System.out.println("".isEmpty());
       
	//	System.out.println(new String(post(request).getBytes("GBK"),"utf-8"));
		bitPost(new ByteArrayInputStream(request.getBytes()),System.out,"127.0.0.1:1028");
	}
	
	public static String post(String firstline,LinkedHashMap<String,String> map,String body) throws IOException {
		String toput="";
		toput+=firstline+"\r\n";
		for(String key:map.keySet()) {
			toput+=key+": "+map.get(key)+"\r\n";
		}
		toput+="\r\n"+body+"\r\n";
		String host=map.get("Host");
		Socket soc=new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.print(toput);
		pw.flush();
		soc.shutdownOutput();
		BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream(),"utf-8"));
		String line=null;
		String toret="";
		while((line=br.readLine())!=null) {
			toret+=line+"\r\n";
		}
		soc.close();
		return toret;
	}
	
	public static String post(String firstline,LinkedHashMap<String,String> map,String body,String host) throws IOException {
		String toput="";
		toput+=firstline+"\r\n";
		for(String key:map.keySet()) {
			toput+=key+": "+map.get(key)+"\r\n";
		}
		toput+="\r\n"+body+"\r\n";
		Socket soc=new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(toput);
		pw.flush();
		soc.shutdownOutput();
		BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream(),"utf-8"));
		String line=null;
		String toret="";
		while((line=br.readLine())!=null) {
			toret+=line+"\r\n";
		}
		soc.close();
		return toret;
	}
	
	public static String post(String raw) throws IOException {
		Socket soc=new Socket();
		String[] strs=raw.split("\n");
		String host="";
		for(int i=0;i<strs.length;i++) {
			if(strs[i].startsWith("Host:"))
				host=strs[i].replace("Host: ", "").replace("\r", "").replace("Host:", "");
		}
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(raw);
		pw.flush();
		soc.shutdownOutput();
		BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
		String line=null;
		String toret="";
		while((line=br.readLine())!=null) {
			toret+=line+"\r\n";
		}
		soc.close();
		return toret;
	}
	
	public static String post(String raw,String host) throws IOException {
		Socket soc=new Socket();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(raw);
		pw.flush();
		soc.shutdownOutput();
		BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
		String line=null;
		String toret="";
		while((line=br.readLine())!=null) {
			toret+=line+"\r\n";
		}
		soc.close();
		return toret;
	}
	
	public static void bitPost(InputStream in,OutputStream out,String ahost) throws IOException {
		Socket soc=new Socket();
		
		byte[] b=new byte[in.available()];
		
		for(int i=0;i<b.length;i++) {
			b[i]=(byte)in.read();
		}
		
		String host=ahost==null?host(b):ahost;
		
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);

		OutputStream os=soc.getOutputStream();
		
		os.write(b);
		
		InputStream is=soc.getInputStream();
		
		int a=-1;
		while((a=is.read())!=-1) {
			out.write(a);
		}
		
		soc.shutdownOutput();
		
		is.close();
		os.close();
		
		out.close();
		soc.close();
	}
	
	public static String host(byte[] b) {
		String req=new String(b);
		String[] strs=req.split("\n");
		String host="";
		for(int i=0;i<strs.length;i++) {
			if(strs[i].startsWith("Host:"))
				host=strs[i].replace("Host: ", "").replace("\r", "").replace("Host:", "");
		}
		return host;
	}

}
