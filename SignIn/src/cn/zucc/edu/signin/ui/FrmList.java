package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by blue on 16-7-6.
 */
public class FrmList extends JDialog implements ActionListener {


    private DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);
    private Object tblTitle[]={"学号","姓名","班级","时间","在场时间"};
    private Object tblData[][];
    private String activeName;
    public FrmList(Frame f, String s, boolean b, String activeName) {
        super(f, s, b);
        this.activeName = activeName;
        tblData = select();
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();

        this.setSize(1200,520);
        this.getContentPane().add(new JScrollPane(this.dataTable),BorderLayout.CENTER);

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }

    Object[][] select(){
        ArrayList<People> list = new ArrayList<People>();
        NodeList plist = xmlManage.selectAllNode("src/signindetaill.xml","entry");
        for (int i = 0; i < plist.getLength(); i++) {
            Element entry = (Element) plist.item(i);
            for(Node node = entry.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName() == "activeName")
                        if(node.getFirstChild().getNodeValue().equals(activeName) ){
                            People p = new People();
                            node = node.getNextSibling();
                            p.id = node.getFirstChild().getNodeValue();
                            node = node.getNextSibling();
                            p.name = node.getFirstChild().getNodeValue();
                            node = node.getNextSibling();
                            p.classs = node.getFirstChild().getNodeValue();
                            node = node.getNextSibling();
                            p.time = node.getFirstChild().getNodeValue();
                            node = node.getNextSibling();
                            p.alltime = node.getFirstChild().getNodeValue();
                            list.add(p);
                        }

                }
            }
        }
        Object[][] result = new Object[list.size()][5];
        for(int i=0; i < list.size(); i++){
            result[i][0] = list.get(i).id;
            result[i][1] = list.get(i).name;
            result[i][2] = list.get(i).classs;
            result[i][3] = list.get(i).time;
            result[i][4] = list.get(i).alltime;
        }
        return result;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}

class People{
    String id;
    String name;
    String classs;
    String time;
    String alltime;
}