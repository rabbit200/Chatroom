package com.qq.main;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PointToPointTalkFrame extends JFrame {

	TextArea oldMessageTextArea;
	TextArea sendMessageTextArea;
	JButton btSend;
	JButton btClosed;
	JButton sendFile;
	JLabel upShow;
	JLabel downShow;
	Client client;
	String clientName;

	// �̣߳�ֻҪ������������Ϣ���ͽ���Ϣ��ʾ��oldMessageTextArea
	class showOldMessageThread implements Runnable {
		public void run() {
			boolean flag = true;
			while (flag) {
				try {
					// ���շ������˻ط���������Ϣ
					String serverOutput = client.br.readLine() + "\r\n";
					System.out.println("˽�ķ���������������Ϣ��" + serverOutput);
					// ����Ϣ�е������û�����ȡ����
					String s1 = "";
					if (serverOutput.startsWith("˽��")) {
						String[] s;
						if (serverOutput.startsWith("˽��*")) {
							s = serverOutput.substring(3,
									serverOutput.indexOf("\r")).split("��");
						} else {
							s = serverOutput.substring(2,
									serverOutput.indexOf("˵")).split("��");
						}
						for (int i = 0; i < s.length; i++) {
							s1 = s1 + s[i];
						}
					}
					// �������е������û�����ȡ����,������˳��
					String[] title = clientName.split("��");
					String s2 = "";
					for (int i = 0; i < title.length; i++) {
						s2 = s2 + title[i];
					}
					String s3 = "";
					for (int i = title.length - 1; i >= 0; i--) {
						s3 = s3 + title[i];
					}
					// �жϷ������˷�����Ϣ�е������û������ڿͻ��˵������û���������˳�򣩣�����Ϣ��ʾ������Ҳ������ʾ��˽�Ĵ����ϣ�
					if (s1.equals(s2) || s1.equals(s3)) {
						String ss1 = serverOutput.substring(2, serverOutput
								.indexOf("��"));
						String ss2 = serverOutput.substring(serverOutput
								.indexOf("��"));
						// ��˽�Ŀͻ��˷�������һ���ַ�����"�i"���в�ֳɶ��У���ʾ��˽�ĵ�oldMessageArea��
						if (ss2.indexOf("�i") != -1) {
							ss2 = ss2.replaceAll("�i", "\r\n     ");
						}
						oldMessageTextArea.append(ss1 + ss2);
					}
				} catch (IOException e1) {
					System.out.println("��ȡ����������Ϣ����");
				}
			}
		}
	}

	PointToPointTalkFrame(final String clientName) {
		// �����clientName��XXX��XXX����ʽ
		this.clientName = clientName;
		client = new Client(clientName);
		this.setTitle("�롾" + clientName.substring(clientName.indexOf("��") + 1)
				+ "��˽����");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 600) / 2, (y - 600) / 2, 600, 600);
		this.setResizable(false);
		this.setLayout(null);
		this.setState(JFrame.EXIT_ON_CLOSE);

		// �����Ѿ�����ȥ����Ϣ���ڵ�����
		oldMessageTextArea = new TextArea();
		oldMessageTextArea.setBounds(0, 0, 390, 360);

		// ����׼��������Ϣ���ڵ�����
		sendMessageTextArea = new TextArea(3, 3);
		sendMessageTextArea.setBounds(0, 380, 390, 140);

		// ���ø�������Label
		upShow = new JLabel(new ImageIcon(this.getClass().getClassLoader()
				.getResource("images/���.JPG")));
		upShow.setBounds(400, 0, 180, 300);
		downShow = new JLabel(new ImageIcon(this.getClass().getClassLoader()
				.getResource("images/��Ա.gif")));
		downShow.setBounds(390, 280, 200, 300);

		// ����<����>��ť������
		btSend = new JButton("����");
		btSend.setBounds(240, 530, 70, 30);

		// ע��<����>��ť�ĵ���¼�
		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// String s = sendMessageTextArea.getText();
				String s1 = sendMessageTextArea.getText();
				// ��Ҫ���͵Ķ����ı��е�"�س�"�滻��"�i",�γ�һ���ַ������͵���������
				String s = s1.replaceAll("\r\n", "�i");
				client.ps.println("˽��" + PointToPointTalkFrame.this.clientName
						+ "˵��" + s);
				sendMessageTextArea.setText("");
			}
		});

		// ����<�ر�>��ť������
		btClosed = new JButton("�ر�");
		btClosed.setBounds(320, 530, 70, 30);

		// ע��<�ر�>��ť�ĵ���¼�
		btClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PointToPointTalkFrame.this.dispose();
			}
		});

		// ����<�����ļ�>��ť������
		/*
		 * sendFile = new JButton("�����ļ�"); sendFile.setBounds(0, 530, 70, 30);
		 */

		// ����һ��PointToPointTalkFrame����ʱ���������̣߳�����ȡ�������˵���Ϣ
		new Thread(new showOldMessageThread()).start();

		this.add(oldMessageTextArea);
		this.add(sendMessageTextArea);
		this.add(upShow);
		this.add(downShow);
		// this.add(sendFile);
		this.add(btSend);
		this.add(btClosed);

	}
}
