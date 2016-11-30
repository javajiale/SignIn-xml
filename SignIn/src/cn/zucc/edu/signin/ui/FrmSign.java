package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by blue on 16-6-2.
 */
public class FrmSign extends JDialog implements ActionListener {

    private JTextField edtUserId = new JTextField(20);
    private JLabel labelspace = new JLabel("             ");
    private JLabel labelIdd = new JLabel("      学   号： ");
    private JLabel labelId = new JLabel("");
    private JLabel labelNamee = new JLabel("      姓   名： ");
    private JLabel labelName = new JLabel("");
    private JLabel labelClasss = new JLabel("      班   级： ");
    private JLabel labelClass = new JLabel("");

    private JLabel labelSituation = new JLabel();

    private JPanel toolBar = new JPanel();
    private JButton end = new JButton("结束签到");
    private JButton activeNameList = new JButton("名单");
    private JButton submit = new JButton("签到");
    private JPanel workPane = new JPanel();
    private String activeName = null;
    Frame ff;

    public FrmSign(Frame f, String s, boolean b){
        super(f, s, b);
        ff=f;
        activeName = s;
        workPane.add(edtUserId);
        workPane.add(labelspace);
        workPane.add(labelIdd);
        workPane.add(labelId);
        labelId.setPreferredSize(new Dimension(200,50));
        workPane.add(labelNamee);
        workPane.add(labelName);
        labelName.setPreferredSize(new Dimension(200,50));
        workPane.add(labelClasss);
        workPane.add(labelClass);
        labelClass.setPreferredSize(new Dimension(200,50));
        workPane.add(labelSituation);
        labelClass.setPreferredSize(new Dimension(200,50));

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(end);
        toolBar.add(activeNameList);
        toolBar.add(submit);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        this.getContentPane().add(workPane,BorderLayout.CENTER);
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(60,60));
        this.getContentPane().add(space, BorderLayout.NORTH);


        this.setSize(460,400);
        activeNameList.addActionListener(this);
        submit.addActionListener(this);
        end.addActionListener(this);
        edtUserId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    select(edtUserId.getText());
                    submit.doClick();
                }

            }
        });

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }

    private void select(String userId){
         labelId.setText(userId);
        // labelName.setText("le");
        // labelClass.setText("cs1302");
        try {
            Node person = xmlManage.selectSingleNode("/allstudent/student[@id='" + userId + "']", xmlManage.getRoot("student.xml"));
         //   labelId.setText(person.getAttributes().item());
            for(Node node = person.getFirstChild();node != null;node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if(node.getNodeName()=="name")
                        labelName.setText(node.getFirstChild().getNodeValue());
                    if(node.getNodeName()=="class")
                        labelClass.setText(node.getFirstChild().getNodeValue());
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.submit){
            String id = labelId.getText();
            String name = labelName.getText();
            String classs = labelClass.getText();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date());
            if(name.equals("")){
                JOptionPane.showMessageDialog(null,"不能为空","imf",JOptionPane.ERROR_MESSAGE);
            }else {
                String result = xmlManage.sign(activeName, id, name, classs, time);
                if(result.equals("success")){
                    labelSituation.setText("签到成功！");
                    edtUserId.setText("");
                }else {
                    JOptionPane.showMessageDialog(null, result, "imf", JOptionPane.ERROR_MESSAGE);
                    labelId.setText("");
                    labelName.setText("");
                    labelClass.setText("");
                    edtUserId.setText("");
                }
                setVisible(true);
            }
        } else if(e.getSource() == this.activeNameList){
            FrmList dlg = new FrmList(this.ff,activeName,true,activeName);
            dlg.setVisible(true);
        } else if(e.getSource() == this.end){
           String shijian = JOptionPane.showInputDialog("请输入会议持续时间(单位：分钟)");
            if(shijian!=null)
                try {
                    xmlManage.addShijian(activeName,shijian);
                    xmlManage.caTime(activeName,shijian);
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (SAXException e1) {
                    e1.printStackTrace();
                } catch (TransformerException e1) {
                    e1.printStackTrace();
                }
        }
    }
}
