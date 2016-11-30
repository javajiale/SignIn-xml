package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.util.ExcelUtil;
import cn.zucc.edu.signin.xml.xmlManage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by blue on 16-7-7.
 */
public class FrmUpload extends JDialog implements ActionListener {

    JButton uploadExcelButton = new JButton("导出excel");
    private DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);
    private Object tblTitle[]={"活动名称","创建人","创建时间","持续时间","结束时间"};
    private Object tblData[][];
    private JPanel toolBar = new JPanel();


    public FrmUpload(Frame f, String s, boolean b){
        super(f, s, b);
        tblData = select();
        tablmod.setDataVector(tblData,tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();

        this.setSize(1200,520);
        this.getContentPane().add(new JScrollPane(this.dataTable),BorderLayout.CENTER);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(uploadExcelButton);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        uploadExcelButton.addActionListener(this);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }

    Object[][] select(){
        ArrayList<Active> list = new ArrayList<Active>();
        NodeList nlist =  xmlManage.selectAllNode("src/active.xml","active");
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
        if (e.getSource() == this.uploadExcelButton) {

            File selectedFile = getSelectedFile(".xls");
            if (selectedFile != null) {
                String path = selectedFile.getPath();
                ToExcel(path);
            }

        }
    }

    private File getSelectedFile(final String type) {
        String name = getName();

        JFileChooser pathChooser = new JFileChooser();
        pathChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    if (f.getName().toLowerCase().endsWith(type)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            @Override
            public String getDescription() {
                return "文件格式（" + type + "）";
            }
        });
        pathChooser.setSelectedFile(new File(name + type));
        int showSaveDialog = pathChooser.showSaveDialog(this);
        if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
            return pathChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public void ToExcel(String path) {

        NodeList list = xmlManage.selectAllNode("src/signindetaill.xml","entry");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("签到表");

        String[] n = { "学号", "姓名", "班级", "签到时间", "在场时间" };

        Object[][] value = new Object[list.getLength() + 1][5];
        for (int m = 0; m < n.length; m++) {
            value[0][m] = n[m];
        }
        int k = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Element stu = (Element) list.item(i);

            for(Node node = stu.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if(node.getNodeName() == "activeName")
                        if(!node.getFirstChild().getNodeValue().equals(dataTable.getValueAt(dataTable.getSelectedRow(),0).toString())) {
                           k--;
                            break;
                        }
                    if (node.getNodeName() == "personId")
                       value[k+1][0] = node.getFirstChild().getNodeValue();
                    if (node.getNodeName() == "personName")
                        value[k+1][1] = node.getFirstChild().getNodeValue();
                    if (node.getNodeName() == "class")
                        value[k+1][2] = node.getFirstChild().getNodeValue();
                    if (node.getNodeName() == "time")
                        value[k+1][3] = node.getFirstChild().getNodeValue();
                    if (node.getNodeName() == "alltime")
                        value[k+1][4] = node.getFirstChild().getNodeValue();
                }
            }
            k++;
        }
        ExcelUtil.writeArrayToExcel(wb, sheet, list.getLength() + 1, 5, value);

        ExcelUtil.writeWorkbook(wb, path);

    }

    class Active{
        String name;
        String person;
        String settime;
        String alltime;
        String endtime;
    }
}
