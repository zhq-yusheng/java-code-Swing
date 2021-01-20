package com.yu.swing.swing;

import com.yu.swing.service.QueryStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QueryStudent extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JLabel title;
    JTextField textField;
    JButton jButton;

    public QueryStudent(){
        super("查看全部学生学生");
        this.setLayout(null);//清空窗体的默认部局样式
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.pink);
        jPanel.setBounds(0,0,800,600);//定位部局的位置
        this.add(jPanel);

        title = new JLabel("查看学生信息");
        title.setBounds(360,30,200,40);
        jPanel.add(title);

        textField = new JTextField(8);
        textField.setBounds(190,70,400,400);
        jPanel.add(textField);

        jButton = new JButton("查询");
        jButton.setBounds(350,500,60,40);
        jPanel.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<String> studentNames = QueryStudentService.getStudentNames();
                String names="";
                for (int i = 0; i < studentNames.size(); i++) {
                    if(i==studentNames.size()-1) names+=studentNames.get(i);
                    else names+=studentNames.get(i)+" ";
                }
                textField.setText(names);
            }
        });

        this.setBounds((width-800)/2,(height-600)/2,800,600);
        //this.setSize(800,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口

    }
}
