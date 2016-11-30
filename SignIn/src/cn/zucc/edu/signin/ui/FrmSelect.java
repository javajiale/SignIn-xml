package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by blue on 16-7-8.
 */
public class FrmSelect extends JDialog implements ActionListener{



    private DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);
    private Object tblTitle[]={"活动名称","创建人","创建时间","持续时间","结束时间"};
    private Object tblData[][];
    private JButton activeNameList = new JButton("名单");
    private JPanel toolBar = new JPanel();
    Frame ff;

    public FrmSelect(Frame f, String s, boolean b){
        super(f,s,b);
        ff=f;
        tblData = select();
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();

        this.setSize(1200,520);
        this.getContentPane().add(new JScrollPane(this.dataTable),BorderLayout.CENTER);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(activeNameList);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        activeNameList.addActionListener(this);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }

    Object[][] select(){
        ArrayList<Active> list = new ArrayList<Active>();
        NodeList nlist =  xmlManage.selectAllNode("active.xml","active");
        for (int i = 0; i < nlist.getLength(); i++) {
            Element active = (Element) nlist.item(i);
            Active ac = new Active();
            ac.name = active.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
            ac.person = active.getElementsByTagName("person").item(0).getFirstChild().getNodeValue();
            ac.settime = active.getElementsByTagName("time").item(0).getFirstChild().getNodeValue();
            ac.alltime = active.getElementsByTagName("shijian").item(0).getFirstChild().getNodeValue();
            ac.endtime = active.getElementsByTagName("endtime").item(0).getFirstChild().getNodeValue();
            list.add(ac);
        }
        Object[][] result = new Object[list.size()][5];
        for(int i=0; i < list.size(); i++){
            result[i][0] = list.get(i).name;
            result[i][1] = list.get(i).person;
            result[i][2] = list.get(i).settime;
            result[i][3] = list.get(i).alltime;
            result[i][4] = list.get(i).endtime;
        }

        return result;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.activeNameList){
            FrmList dlg = new FrmList(this.ff,dataTable.getValueAt(dataTable.getSelectedRow(),0).toString(),true,dataTable.getValueAt(dataTable.getSelectedRow(),0).toString());
            this.dispose();
            dlg.setVisible(true);
        }
    }

    class Active{
        String name;
        String person;
        String settime;
        String alltime;
        String endtime;
    }
}
