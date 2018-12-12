package poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;

public class main {
	
	private final static int connectTime=5000; 

	public static void main(String[] args) throws IOException {
	
        String request = "GET / HTTP/1.1\r\n"+
                "Host: mifanblog.cn\r\n";
      //  System.out.println("".isEmpty());
		System.out.println(post(request,"127.0.0.1:1028"));
		//bitPost(System.out,request);
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
	
	public static void bitPost(InputStream in,OutputStream out) throws IOException {
		Socket soc=new Socket();
		
		byte[] b=new byte[in.available()];
		
		int a=-1,i=0;
		while(i<in.available()) {
			b[i++]=(byte)in.read();
		}
		
		String host=host(b);
		
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,connectTime);

		OutputStream os=soc.getOutputStream();
		
		os.write(b);
		
		soc.shutdownOutput();
		
		InputStream is=soc.getInputStream();
		
		a=-1;
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
		return "mifanblog.cn";
	}

}
