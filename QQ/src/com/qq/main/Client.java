package com.qq.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	PrintStream ps;
	BufferedReader br;
	Socket clientSocket;

	public Client(String clientName) {
		try {
			// �����ͻ���sSocket
			clientSocket = new Socket("127.0.0.1", 9999);
			System.out.println(clientName + "���ӷ������ɹ�");
			// ���������
			OutputStream os = clientSocket.getOutputStream();
			ps = new PrintStream(os);

			// ����������
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

		} catch (UnknownHostException e) {
			System.out.println("��δ������������");
		} catch (IOException e) {
			System.out.println("��δ������������");
		} finally {
			// clientSocket.close();
		}

	}
}
