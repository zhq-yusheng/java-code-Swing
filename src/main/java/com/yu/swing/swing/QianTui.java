package com.yu.swing.swing;

import com.yu.swing.biometric.FingerIdent;
import com.yu.swing.service.QueryStudentService;
import com.yu.swing.service.UpdateSevice;
import com.yu.swing.utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class QianTui extends JFrame {
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
    JLabel qds;
    JTextField qdsText;
    JButton zw;
    JButton qd;

    public QianTui(){
        super("签退系统");
        this.setLayout(null);//清空窗体的默认部局样式

        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.PINK);
        jPanel.setBounds(0,0,800,600);//定位部局的位置
        this.add(jPanel);

        qds = new JLabel("上课开始时间:");
        qdsText = new JTextField(8);
        qds.setBounds(300,160,100,30);
        qdsText.setBounds(380,160,100,30);
        jPanel.add(qds);
        jPanel.add(qdsText);

        date = new JLabel("下课时间:");
        dateText = new JTextField(8);
        date.setBounds(300,200,100,30);
        dateText.setBounds(370,200,100,30);
        jPanel.add(date);
        jPanel.add(dateText);

        title = new JLabel("时间格式:yyyy-MM-dd HH:mm");
        title.setBounds(340,110,400,30);
        jPanel.add(title);


        id = new JLabel("学号：");
        idText = new JTextField(8);
        id.setBounds(300,240,60,30);
        idText.setBounds(370,240,100,30);
        jPanel.add(id);
        jPanel.add(idText);

        jzw = new JLabel("指纹:");
        zwText = new JLabel();
        zw = new JButton("录入指纹");
        jzw.setBounds(300,280,60,30);
        zwText.setBounds(370,280,100,30);
        zw.setBounds(400,280,100,30);
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

        qd = new JButton("签退");
        qd.setBounds(350,350,60,30);
        jPanel.add(qd);

        qd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String sdate = qdsText.getText();//上课时间
                String xdate = dateText.getText();//下课时间
                String xh = idText.getText();
                String zw = zwText.getText();
                String code = "";
                Date date = new Date();
                Long aLong = DateUtil.getSdf(xdate);
                if(QueryStudentService.queryCode(sdate,xh).equals("迟到")){
                    code="迟到";
                } else  if(aLong>date.getTime()){
                    code="早退";
                } else{
                    code="正常";
                }
                UpdateSevice.update(sdate,xh,date.toLocaleString(),code);
                JOptionPane.showMessageDialog(null,code);

            }
        });

        this.setBounds((width-800)/2,(height-600)/2,800,600);
        //this.setSize(800,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);//显示窗口
    }

}
