package cn.zucc.edu.signin.ui;

import cn.zucc.edu.signin.xml.xmlManage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by blue on 16-7-6.
 */
public class FrmLoad extends JDialog implements ActionListener {
    JButton loadExcelButton = new JButton("导入excel");
    private JPanel toolBar = new JPanel();


    public FrmLoad(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(loadExcelButton);
        this.getContentPane().add(toolBar, BorderLayout.CENTER);

        loadExcelButton.addActionListener(this);
        this.setSize(300, 140);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.loadExcelButton){
            File selectedFile = getSelectedOpenFile(".xls");
            if (selectedFile != null) {
                String path = selectedFile.getPath();
                loadExcel(path);
            }
        }
    }

    public void loadExcel(String filename){
        String result = "success";
        try {
            // 创建对Excel工作簿文件的引用
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    filename));
            // 创建对工作表的引用。
            // HSSFSheet sheet = workbook.getSheet("Sheet1");
            HSSFSheet sheet = workbook.getSheetAt(0);

            int j = 1;//从第2行开始读数据
            // 第在excel中读取一条数据就将其插入到数据库中
            while (j < sheet.getPhysicalNumberOfRows()) {
                HSSFRow row = sheet.getRow(j);
                //Users user = new Users();
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                Student stu = new Student();
                for (int i = 0; i < 3; i++) {
                    HSSFCell cell = row.getCell((short) i);
                    if (i == 0) {
                        stu.id = cell.getStringCellValue();
                    } else if (i == 1){
                        stu.name = cell.getStringCellValue();
                    } else if (i == 2) {
                        stu.classs = cell.getStringCellValue();
                    }
                }
                xmlManage.loadStudent(stu.id,stu.name,stu.classs);
                j++;

            }

        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            System.out.println("notfound");
            e2.printStackTrace();
        } catch (IOException e3) {
            // TODO Auto-generated catch block
            System.out.println(e3.toString());

            e3.printStackTrace();
        }
    }
    private File getSelectedOpenFile(final String type) {
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
        int showSaveDialog = pathChooser.showOpenDialog(this);
        if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
            return pathChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}

class Student{
    String id;
    String name;
    String classs;
}