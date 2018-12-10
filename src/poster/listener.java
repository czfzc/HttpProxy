package poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class listener {
	public static void main(String[] args) throws IOException{
		ServerSocket server=new ServerSocket(1027);
		while(true) {
			try {
				Socket soc=server.accept();
				System.out.println("waiting...");
				BufferedReader br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
				String line=null;
				String raw="";
				while((line=br.readLine())!=null&&!line.isEmpty()) {
					raw+=line+"\r\n";
				}
				String data=main.post(raw);
				PrintWriter pw=new PrintWriter(soc.getOutputStream());
				pw.println(data);
				pw.close();
				soc.close();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("restarting...");
			}finally {
				continue;
			}
		}
	}
}
