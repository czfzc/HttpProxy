package poster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Doer implements Runnable{
	
	private Socket socket=null;
	private boolean runnable=false;
	private int hostnum;
	private Thread t;
	
	public Doer(int hostnum) {
		this.hostnum=hostnum;
	}
	
	public void setSocket(Socket socket) {
		runnable=true;
		this.socket=socket;
	}
	
	public void rmSocket() {
		runnable=false;
		this.socket=null;
		this.t=null;
		System.out.println("runnable=false");
	}
	
	public boolean isRunnable() {
		return this.runnable;
	}

	@Override
	public void run() {
		System.out.println("hostnum is:"+this.hostnum);
			try {
			//	BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				OutputStream os=socket.getOutputStream();
				InputStream is=socket.getInputStream();
		//		PrintWriter pw=new PrintWriter(os);
				String line=null;
				String raw="";
		//		while((line=br.readLine())!=null&&!line.isEmpty()) {
		//			raw+=line+"\r\n";
		//		}
		//		System.out.println("\n====raw=====\n"+raw+"\n=====endraw======\n");
		/*		String data;
				data = main.post(raw);
				System.out.println("\n===data=======\n"+data+"\n=====enddata======\n");
				pw.println(data);
				pw.flush();
				pw.close();
		*/
				main.bitPost(is,os);
				
			} catch (IOException e) {
				e.printStackTrace();
				listener.list.remove(hostnum);
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.rmSocket();
				System.out.println("exiting...");
			}

		
	}
	
	   public void start () {
		      if (t == null) {
		         t = new Thread (this);
		         t.start ();
		      }
	   }

}
