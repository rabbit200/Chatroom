package com.qq.regist;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regist extends JDialog {

	private JLabel userId;
	private JLabel userPassword;
	private JLabel userPasswordConfirm;
	private JTextField inputId;
	private JPasswordField inputPassword;
	private JPasswordField inputPasswordConfirm;
	private JButton btSubmit;
	private JButton btCancel;

	public Regist() {
		// ��ʼ��JLabel��JButton
		userId = new JLabel("�ʺ�(�ǳ�)");
		userPassword = new JLabel("����");
		userPasswordConfirm = new JLabel("�����ظ�");
		inputId = new JTextField("����������");
		inputPassword = new JPasswordField();
		inputPasswordConfirm = new JPasswordField();
		btSubmit = new JButton("�ύ");
		btCancel = new JButton("ȡ��");

		// ���ô�JFrame������
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 240) / 2, (y - 600) / 2, 240, 600);
		this.setLayout(null);
		this.setModal(true);

		// ����JLabel����
		userId.setBounds(30, 100, 60, 20);
		userPassword.setBounds(30, 140, 40, 20);
		userPasswordConfirm.setBounds(30, 180, 60, 20);
		// �����ı�������
		inputId.setBounds(90, 100, 100, 20);
		inputPassword.setBounds(90, 140, 100, 20);
		inputPassword.setEchoChar('*');
		inputPasswordConfirm.setBounds(90, 180, 100, 20);
		inputPasswordConfirm.setEchoChar('*');
		// ����JButton����
		btSubmit.setBounds(50, 240, 60, 20);
		btCancel.setBounds(120, 240, 60, 20);

		// ���inputId�ļ�����
		inputId.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				inputId.setText("");
			}
		});

		// ���inputPassword�ļ�����
		inputPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				inputPassword.setText("");
			}
		});
		inputPassword.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if ((new String(inputPassword.getPassword())).length() < 6) {
					JOptionPane.showMessageDialog(null, "���볤�ȱ������6");
					inputPassword.setText("");
				}
			}
		});
		// ע��inputPasswordConfirm�ļ�����
		inputPasswordConfirm.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inputPasswordConfirm.setText("");
			}
		});

		// ע�ᡶ�ύ����ť�ļ�����
		btSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = inputId.getText();
				String userPassword = new String(inputPassword.getPassword());
				String userPasswordConfirm = new String(inputPasswordConfirm
						.getPassword());
				System.out.println("��������ύ��ť");
                String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                if(!regex.matcher(userName).matches())

				//if (userName.equals(""))
				{

					JOptionPane.showMessageDialog(null, "�û���ֻ��������");
				} else if ("".equals(userPassword)
						|| "".equals(userPasswordConfirm)) {
					JOptionPane.showMessageDialog(null, "����������ظ�������Ϊ��");
				} else if (!userPassword.equals(userPasswordConfirm)) {
					JOptionPane.showMessageDialog(null, "����������ظ���һ��");
				} else {
					UserInformation user = new UserInformation();
					if (user.isExist(userName)) {
						JOptionPane.showMessageDialog(null, "���û����Ѵ���");
					} else {
						JOptionPane.showMessageDialog(null, "ע��ɹ�");
						user.insert(userName, userPassword);
						Regist.this.dispose();
					}

				}
			}
		});

		// ע�ᡶȡ������ť�ļ�����
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("�������ȡ����ť");
				Regist.this.dispose();
			}
		});

		this.add(userId);
		this.add(userPassword);
		this.add(userPasswordConfirm);
		this.add(inputId);
		this.add(inputPassword);
		this.add(inputPasswordConfirm);
		this.add(btSubmit);
		this.add(btCancel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Regist();
	}

}
