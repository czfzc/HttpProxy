package poster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class listener{
	
	public static int MAX_HOSTS=20;
	
	protected static LinkedList<Doer> list=new LinkedList<>();
	static {
		for(int i=0;i<MAX_HOSTS;i++) {
			list.add(new Doer(i));
		}
	}
	
	public static void main(String[] args) throws IOException{
		ServerSocket server=new ServerSocket(1027);

		Socket soc=null;
		while(true) {
			try {
				System.out.println("waiting...");
				soc=server.accept();
				
				int i=0;
				while(i<list.size()&&list.get(i++).isRunnable()) {
					Doer doer=list.get(i-1);
					doer.setSocket(soc);
					new Thread(doer).start();
					break;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("restarting...");
			}finally {
				soc.close();
				continue;
			}
		}
	}
	

}
