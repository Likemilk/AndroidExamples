package main;

import java.io.*;
import java.net.*;

public class NetServer {
	
	ServerSocket serverSocket;
	Socket socket;
	boolean btn=true;
	
	
	public NetServer(int portNumber){
		try {
			serverSocket=new ServerSocket(portNumber);
			System.out.println("서버를 만들겠습니다.");
			
			while(btn){
				System.out.println("접속자를 기다리고있습니다.");
				socket = serverSocket.accept();
				System.out.println("접속자가 등장하였습니다.");
				InetAddress clientHost = socket.getLocalAddress();
				System.out.println("클라이언트에서 포트:["+socket.getPort()+"] 로 접속하였습니다."+"\n호스트명 : ["+clientHost+"]님이 접속하셧습니다.");
				
				InputStream socketIn =socket.getInputStream();
				OutputStream socketOut =socket.getOutputStream();
				DataInputStream dis = new DataInputStream(socketIn);
				DataOutputStream dos = new DataOutputStream(socketOut);
				
				
				//데이터를 클라이언트로 보낸다
				dos.writeInt(100);
				dos.writeDouble(12.34);
				dos.writeUTF("안녕하세요");
				
				int data1 = dis.readInt();
				double data2 = dis.readDouble();
				String data3 = dis.readUTF();
				System.out.println(clientHost+"가"+"data1:"+data1+"이라 보냈습니다.");
				System.out.println(clientHost+"가"+"data2:"+data2+"이라 보냈습니다.");
				System.out.println(clientHost+"가"+"data3:"+data3+"이라 보냈습니다.");
				
				dis.close();
				dos.close();
				socket.close();
				serverSocket.close();
				
			}
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			btn=false;
		}
	}
	
	public static void main(String[] args) {
		new NetServer(25555);
	}

}
