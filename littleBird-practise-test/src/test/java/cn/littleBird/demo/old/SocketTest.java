package cn.littleBird.demo.old;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SocketTest {
//DategramSocket
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Socket demo start . . .\n");
			InetAddress interAddress = InetAddress.getByName("192.168.31.15");
			System.out.println(interAddress.getHostAddress());//192.168.31.15
			System.out.println(interAddress.getHostName());//DESKTOP-VCQFGGE
			System.out.println("\nSocket demo end . . .");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket demo occur a exception . . .");
			e.printStackTrace();
		}
	}

}
