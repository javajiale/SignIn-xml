package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by blue on 16-6-1.
 */
public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnLogin = new JButton("登陆");
    private JButton btnCancel = new JButton("退出");
    private JLabel labelUser = new JLabel("用   户：");
    private JLabel labelPwd = new JLabel("密   码：");
    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);

    public FrmLogin(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(60,60));
        this.getContentPane().add(space, BorderLayout.NORTH);

        this.setSize(400, 300);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnLogin) {
            String nname = edtUserId.getText();
            String ppwd = edtPwd.getText();
            String result = xmlManage.Login(nname,ppwd);
            JOptionPane.showMessageDialog(null,result,"imf",JOptionPane.ERROR_MESSAGE);
            if(result.equals("success"))
                setVisible(false);
            else
                setVisible(true);
        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        }
    }

}

