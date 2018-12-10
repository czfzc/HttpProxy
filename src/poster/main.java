package poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;

public class main {

	public static void main(String[] args) throws IOException {
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
        String request = "GET /index.jsp HTTP/1.1\r\n"+
                "Host: mifanblog.cn\r\n";
		System.out.println(post(request,"mifanblog.cn:1027"));
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
		soc.connect(inetSocketAddress,1000);
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
		soc.connect(inetSocketAddress,1000);
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
			if(strs[i].startsWith("Host:"));
				host=strs[i].replace("Host: ", "").replace("\r", "").replace("Host:", "");
		}
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,1000);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(raw);
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
	
	public static String post(String raw,String host) throws IOException {
		Socket soc=new Socket();
		String[] strs=raw.split("\n");
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host.split(":")[0],!host.contains(":")?80:Integer.parseInt(host.split(":")[1]));
		soc.connect(inetSocketAddress,1000);
		PrintWriter pw=new PrintWriter(soc.getOutputStream());
		pw.println(raw);
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

}
