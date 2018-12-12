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
		ServerSocket server=new ServerSocket(1028);

		Socket soc=null;
		while(true) {
			try {
				System.out.println("waiting...");
				soc=server.accept();
				System.out.println("accepted...");
				int i=0;
				
				while(i<list.size()) {
					if(i<list.size()&&!list.get(i).isRunnable()) {
						System.out.println(i);
						Doer doer=list.get(i);
						doer.setSocket(soc);
						doer.start();
						System.out.println("started...");
						break;
					}
					if(i<MAX_HOSTS) {
						list.add(new Doer(list.size()));
					}
					i++;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("restarting...");
			}finally {
				continue;
			}
		}
		
	}
	

}
