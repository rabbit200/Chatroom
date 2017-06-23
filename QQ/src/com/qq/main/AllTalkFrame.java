package com.qq.main;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class AllTalkFrame extends JFrame {

	TextArea oldMessageTextArea;
	TextArea sendMessageTextArea;
	JList userList;
	JScrollPane userListPane;
	JButton btSend;
	JButton btClosed;
	JButton upLine;
	String doubleClickedName;
	Client client;
	String clientName;
	JLabel userlistTitle;
	Vector users;

	// ֻҪ������������Ϣ���ͽ���Ϣ��ʾ��oldMessageTextArea
	class showOldMessageThread implements Runnable {
		public void run() {
			boolean flag = true;
			while (flag) {
				try {
					// ����Ⱥ�ķ������˻ط���������Ϣ
					String serverOutput = client.br.readLine() + "\r\n";
					if (!serverOutput.startsWith("˽��")
							&& !serverOutput.startsWith("*")
							&& !(serverOutput.substring(serverOutput
									.indexOf("��") + 1).equals("\r\n"))) {
						String s1 = serverOutput.replace('˵', ' ');
                        //String s2 = s1.replaceAll("����", "**");
						//String s = s2.replaceAll("��", "\r\n     ");
                        String s = s1.replaceAll("��", "\r\n     ");
						oldMessageTextArea.append(s);
					}

					// ��ӿͻ��˵��û������б�
					if (!serverOutput.startsWith("*")
							&& !serverOutput.startsWith("˽��")
							&& (serverOutput.indexOf("˵") != -1)) {
						String listName = serverOutput.substring(0,
								serverOutput.indexOf('˵'));
						// ���JList������ͬ���ֵ��û�������ӣ��������
						if (!users.contains(listName)) {
							System.out.println("�û�" + listName + "������");
							users.add(listName);
							userList.setListData(users);
						}
					}

					// �жϷ������ط���������Ϣ�ǲ�����"˽��"��ͷ�ģ��ǵĻ�����ȡ���������û���
					if (serverOutput.startsWith("˽��")) {
						String siliaoName1 = serverOutput.substring(
								serverOutput.indexOf("*") + 1, serverOutput
										.indexOf("��"));
						String siliaoName2 = serverOutput.substring(
								serverOutput.indexOf("��") + 1, serverOutput
										.indexOf("\r"));
						String siliaoBenshen = "";
						String siliaoDuixiangName = "";
						if (siliaoName1.equals(clientName)) {
							siliaoBenshen = siliaoName1;
							siliaoDuixiangName = siliaoName2;
						} else {
							siliaoBenshen = siliaoName2;
							siliaoDuixiangName = siliaoName1;
						}
						// �ж��������������Ƿ������Լ�ͬ���ģ��еĻ��͵�����˽�Ĵ���
						if (siliaoName1.equals(clientName)
								|| siliaoName2.equals(clientName)) {
							new PointToPointTalkFrame(siliaoBenshen + "��"
									+ siliaoDuixiangName).setVisible(true);
						}
					}
				} catch (IOException e1) {
					System.out.println("��ȡ����������Ϣ����");
				}
			}
		}
	}

	AllTalkFrame(final String clientName) {
		this.clientName = clientName;
		client = new Client(clientName);
		this.setTitle("��ӭ����" + clientName);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 600) / 2, (y - 600) / 2, 600, 600);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �����Ѿ�����ȥ����Ϣ���ڵ�����
		oldMessageTextArea = new TextArea();
		oldMessageTextArea.setBounds(0, 0, 390, 360);

		// ����׼��������Ϣ���ڵ�����
		sendMessageTextArea = new TextArea(3, 3);
		sendMessageTextArea.setBounds(0, 380, 390, 140);
		sendMessageTextArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out
							.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				}
			}

		});

		// ����<����>��ť������
		upLine = new JButton("����");
		upLine.setBounds(0, 530, 70, 30);
		
		// ע��<����>��ť�ĵ���¼�
		upLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upLine.setEnabled(false);
				String s = sendMessageTextArea.getText();
				client.ps.println(clientName + "˵��" + s);
				sendMessageTextArea.setText("");
			}
		});

		// ����<����>��ť������
		btSend = new JButton("����");
		btSend.setBounds(240, 530, 70, 30);
		
		// ע��<����>��ť�ĵ���¼�
		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upLine.setEnabled(false);
				String text = sendMessageTextArea.getText();
				String s = text.replaceAll("\r\n", "��");

				Pattern pattern = Pattern.compile("[^0-9]");
				Matcher matcher = pattern.matcher(s);
				String numstr = matcher.replaceAll("");
				int num = Integer.parseInt(numstr);

				String s1 = s.replace("<","");
				String s2 = s1.replace(">","");

				Pattern pattern2 = Pattern.compile("[\\d]");
				Matcher matcher2 = pattern2.matcher(s2);
				String output = matcher2.replaceAll("").trim();

				for(int i =0; i<num;i++){
					client.ps.println(clientName + "˵��" + output);
				}

				sendMessageTextArea.setText("");
			}
		});
		
		// ����<�ر�>��ť������
		btClosed = new JButton("�ر�");
		btClosed.setBounds(320, 530, 70, 30);
		
		// ע��<�ر�>��ť�ĵ���¼�
		btClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// AllTalkFrame.this.dispose();
				System.exit(0);
			}
		});

		// �����û��б�ı���
		userlistTitle = new JLabel("��ǰ�����û��б�,˫������˽��");
		userlistTitle.setBounds(400, 0, 200, 20);
		
		// �����û��б�JList����
		userList = new JList();
		userList.setBounds(400, 20, 200, 600);
		users = new Vector();
		
		// ע��JList�ĵ���¼�
		userList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (AllTalkFrame.this.userList.getSelectedValue()
							.toString().equals(clientName)) {
						JOptionPane.showMessageDialog(null, "���ܺ��Լ�����");
					} else {
						String PToPMemberName = "˽��"
								+ "*"
								+ clientName
								+ "��"
								+ AllTalkFrame.this.userList.getSelectedValue()
										.toString();
						client.ps.println(PToPMemberName);
					}
				}
			}
		});

		// �����û��б�JScrollPane������
		userListPane = new JScrollPane(userList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		userListPane.setBounds(400, 0, 200, 600);

		// new һ��AllTalkFrameʱ�����߳���������ȡ�������˻ط�����Ϣ
		new Thread(new showOldMessageThread()).start();

		// �����������ӵ�������
		this.add(oldMessageTextArea);
		this.add(sendMessageTextArea);
		this.add(btSend);
		this.add(upLine);
		this.add(btClosed);
		this.add(userListPane);
		this.add(userlistTitle);
	}
}
