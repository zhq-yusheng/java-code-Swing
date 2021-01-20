package com.yu.swing.swing;

import com.yu.swing.service.QueryStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QueryQdStudent extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JLabel title;
    JLabel datesdf;
    JTextField textField;
    JButton jButton;
    JLabel date;
    JTextField jTextField;
    public QueryQdStudent(){
        super("查询签到学生");

        this.setLayout(null);//清空窗体的默认部局样式
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.pink);
        jPanel.setBounds(0,0,800,600);//定位部局的位置
        this.add(jPanel);

        title = new JLabel("查询学生签到");
        title.setBounds(360,30,200,40);
        jPanel.add(title);

        datesdf = new JLabel("时间格式:yyyy-MM-dd HH:mm");
        datesdf.setBounds(0,500,200,30);
        jPanel.add(datesdf);

        textField = new JTextField(8);
        textField.setBounds(190,70,400,400);
        jPanel.add(textField);

        date = new JLabel("上课时间：");
        jTextField = new JTextField(8);
        date.setBounds(200,500,150,30);
        jTextField.setBounds(300,500,100,30);
        jButton = new JButton("查询");
        jButton.setBounds(420,500,60,30);
        jPanel.add(jButton);
        jPanel.add(date);
        jPanel.add(jTextField);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String date = jTextField.getText();
                List<String> studentNames = QueryStudentService.queryQdStudent(date);
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
