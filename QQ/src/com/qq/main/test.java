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
/**
 * Created by uonel on 2017/6/16.
 */
public class test {
    public void test33() {
        String phoneString = "哈哈,13888889999";
        // 提取数字
        // 1
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(phoneString);
        String all = matcher.replaceAll("");
        System.out.println("phone:" + all);
        // 2
        //Pattern.compile("[^0-9]").matcher(phoneString).replaceAll("");
    }
    public void test21() {
        // 提取张三 去除数字
        String r_name3 = "张三 <13599998888 000000>";

        String s1 = r_name3.replace("<","");
        String s2 = s1.replace(">","");

        Pattern pattern = Pattern.compile("[\\d]");
        Matcher matcher = pattern.matcher(s2);
        String output = matcher.replaceAll("").trim();
        System.out.println(output);
    }
    public static void main(String[] args){
        test t1 = new test();
       // t1.test33();
        t1.test21();
    }
}
