package com.yu.swing.swing;

import com.yu.swing.mapper.AdminMapper;
import com.yu.swing.pojo.Tbluser;
import com.yu.swing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JLabel user;//用户标签
    JLabel password;//密码标签
    JTextField userText;//用户的输入文本框
    JTextField passwordText;//密码输入的文本框
    JButton Dbtn;

    public Login(){
        super("登陆系统");
        this.setLayout(null);//清空窗体的默认部局样式

        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.CYAN);
        jPanel.setBounds(0,0,450,350);//定位部局的位置
        this.add(jPanel);

        user=new JLabel("管理员账号：");
        user.setBounds(new Rectangle(60,40,90,35));
        //setBounds(60,40,90,35);
        jPanel.add(user);

        userText = new JTextField(8);
        userText.setLayout(null);
        userText.setBounds(new Rectangle(160,40,160,35));
        jPanel.add(userText);

        password = new JLabel("管理员密码：");
        password.setBounds(60,95,90,35);
        jPanel.add(password);

        passwordText = new JTextField(8);
        passwordText.setBounds(160,95,160,35);
        jPanel.add(passwordText);

        Dbtn = new JButton("登录");
        Dbtn.setBounds(170,190,80,35);
        jPanel.add(Dbtn);


        //添加事件
        Dbtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                String name=userText.getText();
                String password=passwordText.getText();
                Tbluser admin = new Tbluser(name,password);
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
                Tbluser flg = mapper.query(admin);
                if(name.equals("")){
                    JOptionPane.showMessageDialog(null,"用户名不能为空！");
                }else if(password.equals("")){
                    JOptionPane.showMessageDialog(null,"密码不能为空！");
                }else if (flg==null){
                    JOptionPane.showMessageDialog(null,"对不起，账号或密码错误！");
                }else{
                    new HomePage();
                    setVie(false);
                }
            }
        });
        this.setBounds((width-450)/2,(height-350)/2,450,350);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口
    }
     public  void setVie(boolean flg){
        this.setVisible(flg);
    }


    public static void main(String[] args) {
        new Login();
    }
}
