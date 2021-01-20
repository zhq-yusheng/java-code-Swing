package com.yu.swing.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JMenuBar menuBar; // 导航栏对象
    JMenu menuOne;//导航栏上的一级菜单 录入数据的一级菜单
    JMenu menuTwo; //签到一级菜单
    JMenu menuThree; // 查看签到情况一级菜单
    JMenu oneExit; // 退出程序
    JMenuItem jMenuItemOne; // 一级菜单上的子菜单 录入数据上的录入学生信息
    JMenuItem jMenuItemTwo; //  签到
    JMenuItem jMenuItemTwo1;// 签退
    JMenuItem jMenuItemThree;// 查看签到情况
    JMenuItem jMenuItemFour;// 查看班上的全部学生
    JMenuItem exit;// 退出程序

    public HomePage(){
        super("飞签系统");
        this.setLayout(null);//清空窗体的默认部局样式
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.pink);
        jPanel.setBounds(0,0,1000,800);//定位部局的位置
        this.add(jPanel);



        menuBar=new JMenuBar(); //导航栏
        menuBar.setBackground(Color.cyan);
        menuBar.setBounds(0,0,1000,30);
        menuOne = new JMenu("添加"); // 一级菜单
        menuTwo = new JMenu("签到");
        menuThree = new JMenu("查看");
        oneExit = new JMenu("退出");
        menuBar.add(menuOne);
        menuBar.add(menuTwo);
        menuBar.add(menuThree);
        menuBar.add(oneExit);

        jMenuItemOne = new JMenuItem("添加学生");
        jMenuItemTwo = new JMenuItem("签到");
        jMenuItemTwo1 = new JMenuItem("签退");
        jMenuItemThree = new JMenuItem("查看签到学生");
        jMenuItemFour = new JMenuItem("查看班上的同学");
        exit = new JMenuItem("退出");

        jMenuItemTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new QianDao();
            }
        });

        jMenuItemTwo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new QianTui();
            }
        });


        jMenuItemOne.addActionListener(new ActionListener() { // 添加学生的事件 创建一个AddStudent对象
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AddStudent();
            }
        });

        jMenuItemThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new QueryQdStudent();
            }
        });

        jMenuItemFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new QueryStudent();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });


        menuOne.add(jMenuItemOne);
        menuTwo.add(jMenuItemTwo);
        menuTwo.add(jMenuItemTwo1);
        menuThree.add(jMenuItemThree);
        menuThree.add(jMenuItemFour);
        oneExit.add(exit);



        jPanel.add(menuBar);



        this.setBounds((width-1000)/2,(height-800)/2,1000,800);
        //this.setSize(800,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口


    }

    public static void main(String[] args) {
        new HomePage();
    }
}
