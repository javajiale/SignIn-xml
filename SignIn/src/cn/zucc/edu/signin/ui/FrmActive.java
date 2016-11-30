package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by blue on 16-6-2.
 */
public class FrmActive extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnCreate = new JButton("创建");
    private JButton btnLoad = new JButton("导入");
    private JLabel labelActiveName = new JLabel("活    动    名");
    private JLabel labelActivePerson = new JLabel("组    织    人");
    private JLabel labelTime = new JLabel("  时     间  ");
    private JTextField edtActiveName = new JTextField(20);
    private JTextField edtActivePerson = new JTextField(20);
    private JTextField edtTime = new JTextField(20);
    Frame ff;

    public FrmActive(Frame f, String s, boolean b){
        super(f, s, b);
        ff = f;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCreate);
        toolBar.add(btnLoad);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelActiveName);
        workPane.add(edtActiveName);
        edtActiveName.setPreferredSize(new Dimension(500,40));
        workPane.add(labelActivePerson);
        workPane.add(edtActivePerson);
        workPane.add(labelTime);
        workPane.add(edtTime);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(60,60));
        this.getContentPane().add(space, BorderLayout.NORTH);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        edtTime.setText(df.format(new Date()));

        btnCreate.addActionListener(this);
        btnLoad.addActionListener(this);
        this.setSize(500,320);

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == btnCreate){
            String activeName = edtActiveName.getText();
            String activePerson= edtActivePerson.getText();
            String time = edtTime.getText();
            boolean flag = true;
            NodeList list = xmlManage.selectAllNode("src/active.xml","active");
            for (int i = 0; i < list.getLength(); i++) {
                Element active = (Element) list.item(i);

                for (Node node = active.getFirstChild(); node != null; node = node.getNextSibling()) {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        if(node.getNodeName()=="name")
                            if(node.getFirstChild().getNodeValue().equals(activeName)) {
                                JOptionPane.showMessageDialog(null, "重复", "error", JOptionPane.ERROR_MESSAGE);
                                flag = false;
                                break;

                            }
                    }
                }
            }
            if(flag) {
                xmlManage.createActive(activeName, activePerson, time);
                FrmSign dlg = new FrmSign(this.ff, edtActiveName.getText(), true);
                this.dispose();
                dlg.setVisible(true);
            }
        } else if(actionEvent.getSource() == this.btnLoad){
            FrmLoadActive dlg = new FrmLoadActive(this.ff,"活动导入",true);
            this.dispose();
            dlg.setVisible(true);
        }

    }
}
