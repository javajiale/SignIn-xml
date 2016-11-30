package cn.zucc.edu.signin.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by blue on 16-6-1.
 */
public class FrmMain extends JFrame implements ActionListener{

    private FrmLogin dlgLogin = null;
    private JPanel workPane = new JPanel();
    private JButton btnSign = new JButton("点到");
    private JButton btnLoad = new JButton("导入");
    private JButton btnOut = new JButton("导出");
    private JButton btnSelect = new JButton("查看");


    public FrmMain(){
     //   this.setExtendedState(Frame.NORMAL);
        this.setTitle("签到管理系统");
        dlgLogin = new FrmLogin(this,"登陆",true);
        dlgLogin.setVisible(true);

        btnSign.setPreferredSize(new Dimension(150,150));
        workPane.add(btnSign,BorderLayout.CENTER);
        btnSelect.setPreferredSize(new Dimension(150,150));
        workPane.add(btnSelect,BorderLayout.CENTER);
        btnLoad.setPreferredSize(new Dimension(150,150));
        workPane.add(btnLoad,BorderLayout.CENTER);
        btnOut.setPreferredSize(new Dimension(150,150));
        workPane.add(btnOut,BorderLayout.CENTER);

        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(150,150));
        this.getContentPane().add(space,BorderLayout.NORTH);
        this.getContentPane().add(workPane,BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnSign.addActionListener(this);
        btnLoad.addActionListener(this);
        btnOut.addActionListener(this);
        btnSelect.addActionListener(this);

        this.setSize(800,500);

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        //this.validate();

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == this.btnSign){
            FrmActive dlg = new FrmActive(this,"活动创建",true);
            dlg.setVisible(true);
        } else if(actionEvent.getSource() == this.btnLoad){
            FrmLoad dlg = new FrmLoad(this,"导入数据",true);
            dlg.setVisible(true);
        } else if(actionEvent.getSource() == this.btnOut){
            FrmUpload dlg = new FrmUpload(this,"导出数据",true);
            dlg.setVisible(true);
        } else if(actionEvent.getSource() == this.btnSelect){
            FrmSelect dlg = new FrmSelect(this,"查看",true);
            dlg.setVisible(true);
        }
    }
}
