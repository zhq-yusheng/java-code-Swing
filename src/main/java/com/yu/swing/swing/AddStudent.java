package com.yu.swing.swing;

import com.yu.swing.biometric.FingerIdent;
import com.yu.swing.pojo.Student;
import com.yu.swing.service.AddStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudent extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JLabel id;//用户标签
    JLabel name;//密码标签
    JLabel fingerprint;//指纹标签
    JLabel qs;//寝室号
    JTextField qsText;//寝室号输入文本框

    JLabel sex; //性别标签

    JTextField idText;//学号输入文本框
    JTextField nameText;//姓名输入的文本框
    JLabel fingerprintText;//指纹输入的文本框
    // 自定义set方法，供于子窗口调用赋值
    public void setJLabel(JLabel aLabel){
        this.fingerprintText = aLabel;
    }
    public JLabel getJLabel(){
        return fingerprintText;
    }
    JButton fingerprintBtn; // 指纹录入按钮
    JButton lbtn; // 录入按钮

    public  AddStudent(){
        super("学生管理");
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.pink);
        jPanel.setBounds(20,20,780,580);//定位部局的位置
        this.add(jPanel);

        id = new JLabel("学号：");
        idText = new JTextField(8);
        id.setBounds(300,200,40,30);
        idText.setBounds(360,200,150,30);
        jPanel.add(id);
        jPanel.add(idText);


        name = new JLabel("姓名：");
        nameText = new JTextField(8);
        name.setBounds(300,240,40,30);
        nameText.setBounds(360,240,150,30);
        jPanel.add(name);
        jPanel.add(nameText);

        qs = new JLabel("寝室号：");
        qsText = new JTextField(8);
        qs.setBounds(300,280,60,30);
        qsText.setBounds(360,280,150,30);
        jPanel.add(qs);
        jPanel.add(qsText);

        sex= new JLabel("性别：");
        // 创建单选钮
        final JRadioButton rdoMan = new JRadioButton("男");
        final JRadioButton rdoWoman = new JRadioButton("女");
        rdoMan.setBackground(Color.pink);
        rdoWoman.setBackground(Color.pink);
        rdoMan.setSelected(true);
        
        rdoMan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(rdoWoman.isSelected()) rdoWoman.setSelected(false);
            }
        });
        rdoWoman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(rdoMan.isSelected())rdoMan.setSelected(false);
            }
        });
        rdoMan.setBounds(360,320,50,30);
        rdoWoman.setBounds(420,320,50,30);
        sex.setBounds(300,320,50,30);
        jPanel.add(sex);
        jPanel.add(rdoMan);
        jPanel.add(rdoWoman);




        fingerprint = new JLabel("指纹：");
        //指纹特征码输入框
        fingerprintText = new JLabel();
        fingerprintBtn = new JButton("录入指纹");
        fingerprint.setBounds(300,360,40,30);
        fingerprintText.setBounds(360,360,150,30);
        fingerprintBtn.setBounds(540,360,100,30);
        jPanel.add(fingerprint);
        jPanel.add(fingerprintText);
        jPanel.add(fingerprintBtn);

        fingerprintBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FingerIdent window = new FingerIdent(fingerprintText);
                window.setVisible(true);
            }
        });

            lbtn = new JButton("添加学生");
            lbtn.setBounds(380,410,100,30);
            jPanel.add(lbtn);

            lbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String id = idText.getText();
                    String name = nameText.getText();
                    int qs = Integer.parseInt(qsText.getText());
                    String sex="";

                    if(rdoMan.isSelected()) sex="男";
                    else sex="女";

                    String zw = fingerprintText.getText();
                    Student student = new Student(id,name,sex,qs,zw);
                    AddStudentService.addStudent(student);
                    JOptionPane.showMessageDialog(null,"学生添加成功！");
                }
            });

        this.setBounds((width-800)/2,(height-600)/2,800,600);
        this.setSize(800,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口

    }

}
