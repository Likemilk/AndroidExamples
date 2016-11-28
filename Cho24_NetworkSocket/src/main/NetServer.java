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
			System.out.println("������ ����ڽ��ϴ�.");
			
			while(btn){
				System.out.println("�����ڸ� ��ٸ����ֽ��ϴ�.");
				socket = serverSocket.accept();
				System.out.println("�����ڰ� �����Ͽ����ϴ�.");
				InetAddress clientHost = socket.getLocalAddress();
				System.out.println("Ŭ���̾�Ʈ���� ��Ʈ:["+socket.getPort()+"] �� �����Ͽ����ϴ�."+"\nȣ��Ʈ�� : ["+clientHost+"]���� �����ϼ˽��ϴ�.");
				
				InputStream socketIn =socket.getInputStream();
				OutputStream socketOut =socket.getOutputStream();
				DataInputStream dis = new DataInputStream(socketIn);
				DataOutputStream dos = new DataOutputStream(socketOut);
				
				
				//�����͸� Ŭ���̾�Ʈ�� ������
				dos.writeInt(100);
				dos.writeDouble(12.34);
				dos.writeUTF("�ȳ��ϼ���");
				
				int data1 = dis.readInt();
				double data2 = dis.readDouble();
				String data3 = dis.readUTF();
				System.out.println(clientHost+"��"+"data1:"+data1+"�̶� ���½��ϴ�.");
				System.out.println(clientHost+"��"+"data2:"+data2+"�̶� ���½��ϴ�.");
				System.out.println(clientHost+"��"+"data3:"+data3+"�̶� ���½��ϴ�.");
				
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
