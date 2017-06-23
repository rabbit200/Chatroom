package com.qq.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	ServerSocket server;
	static int clientNum = 0;
	
	// ���������������ϵĶ�Ӧ��Socket�������Ǳ����������ͻ���֮����������ڷ�������ÿ���ͻ��˽��лط���Ϣ
	List<Socket> clientConnection = new ArrayList<Socket>();

	public Server() {
		try {
			server = new ServerSocket(9999);
			System.out.println("�������Ѿ�����");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����������ʧ��");
		}
	}

	// �ڲ��࣬�����ͻ����Ƿ������ӵ��������������˿ͻ��˵�Socket���ݸ�HandleSocket���д���ͬʱ��client��ŵ�List�У���clientConnection��
	class SocketListener implements Runnable {
		public void run() {
			Socket client;
			try {
				while (true) {
					client = server.accept();
					// ������һ����ѹ��List�У���clientConnection��
					clientConnection.add(client);
					HandleSocket hs = new HandleSocket(client);
					// �����Ͼ���HandleSocketȥ����
					new Thread(hs).start();
				}
			} catch (IOException e) {
				System.out.println("�ͻ����ӷ�����ʧ��");
			}
		}
	}

	// �ڲ��� ����һ��Socket,����һ��Client���͹�������Ϣ�����ҷ�����ԭ�ⲻ���ķ��ظ����пͻ��ˣ��ͻ��˶���Ϣ���й���
	class HandleSocket implements Runnable {
		Socket client;
		HandleSocket(Socket client) {
			this.client = client;
		}
		public void run() {
			try {
				clientNum++;
				// ����������
				InputStream is = client.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				System.out.println("��" + clientNum + "���ͻ������ӽ��������");

				boolean flag = true;
				String s;
				do {
					// ���û���������Ϣ����Ⱥ�����ͻ���
					s = br.readLine();
					System.out.println("���ܵ�һ���ͻ�����Ϣ��" + s);
					for (int i = 0; i < clientConnection.size(); i++) {
						Socket client = clientConnection.get(i);
						OutputStream os = client.getOutputStream();
						PrintStream ps = new PrintStream(os);
						ps.println(s);
					}
				} while (flag);
				client.close();
			} catch (IOException e) {
				System.out.println("��һ���ͻ��Ͽ��������������");
			}
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		SocketListener listener = server.new SocketListener();
		new Thread(listener).start();
	}
}
