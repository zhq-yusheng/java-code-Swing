package com.yu.swing.swing;

import com.yu.swing.biometric.FingerIdent;
import com.yu.swing.pojo.tblesmp;
import com.yu.swing.service.AddStudentService;
import com.yu.swing.utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class QianDao extends JFrame {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height= Toolkit.getDefaultToolkit().getScreenSize().height;
    JPanel jPanel;//桌布对象
    JLabel date;
    JLabel id;
    JTextField dateText;
    JTextField idText;
    JLabel jzw;
    JLabel zwText;
    public void setJLabel(JLabel aLabel){
        this.zwText = aLabel;
    }
    public JLabel getJLabel(){
        return zwText;
    }
    JLabel title;
    JButton zw;
    JButton qd;

    public QianDao(){
        super("签到系统");
        this.setLayout(null);//清空窗体的默认部局样式

        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.PINK);
        jPanel.setBounds(0,0,800,600);//定位部局的位置
        this.add(jPanel);

        date = new JLabel("上课时间:");
        dateText = new JTextField(8);
        date.setBounds(300,200,100,30);
        dateText.setBounds(360,200,100,30);
        jPanel.add(date);
        jPanel.add(dateText);


        title = new JLabel("时间格式:yyyy-MM-dd HH:mm");
        title.setBounds(340,140,400,30);
        jPanel.add(title);

        id = new JLabel("学号：");
        idText = new JTextField(8);
        id.setBounds(300,260,60,30);
        idText.setBounds(360,260,100,30);
        jPanel.add(id);
        jPanel.add(idText);

        jzw = new JLabel("指纹:");
        zwText = new JLabel();
        zw = new JButton("录入指纹");
        jzw.setBounds(300,320,60,30);
        zwText.setBounds(360,320,100,30);
        zw.setBounds(400,320,100,30);
        jPanel.add(jzw);
        jPanel.add(zwText);
        jPanel.add(zw);

        zw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FingerIdent window = new FingerIdent(zwText);
                window.setVisible(true);
            }
        });

        qd = new JButton("签到");
        qd.setBounds(350,380,60,30);
        jPanel.add(qd);

        qd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String data = dateText.getText();//上课时间
                String xh = idText.getText();//学号
                String zw = zwText.getText();//指纹
                String code="";
                Long aLong = DateUtil.getSdf(data);
                Date date = new Date();
                if(aLong>date.getTime()) code="迟到";
                else code="正常";
                tblesmp tbleps = new tblesmp(zw, xh, data, code);
                AddStudentService.addQStudent(tbleps);
                JOptionPane.showMessageDialog(null,"签到完成！");
                qd.removeAll();
                qd.repaint();

            }
        });

        this.setBounds((width-800)/2,(height-600)/2,800,600);
        //this.setSize(800,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口

    }

}
