/**
 * Created by blue on 16-6-1.
 */
package cn.zucc.edu.signin.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class xmlManage {

    public static String Login(String nname, String ppwd) {
        //创建文件解析工厂
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //开始解析文件，将文件加载进内存，形成dom树
            Document doc = db.parse("user.xml");
            NodeList personlist = doc.getElementsByTagName("person");  //person list
            for (int i = 0; i < personlist.getLength(); i++) {          // 取每个person的value
                Element person = (Element) personlist.item(i);
                String name = null;
                String pwd = null;
                for(Node node = person.getFirstChild();node != null;node = node.getNextSibling()) {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        if(node.getNodeName()=="name")
                            name = node.getFirstChild().getNodeValue();
                        if(node.getNodeName()=="pwd")
                            pwd = node.getFirstChild().getNodeValue();
                    }
                }
                if (nname.equals(name))
                    if (ppwd.equals(pwd))
                        return "success";
                    else
                        return "password error";

            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Not found";

    }

    public static NodeList selectAllNode(String url,String ele){
        NodeList list = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //开始解析文件，将文件加载进内存，形成dom树
            Document doc = db.parse(url);
            list = doc.getElementsByTagName(ele);
        }catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Element getRoot(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        DocumentBuilder db = dbf.newDocumentBuilder();
        //开始解析文件，将文件加载进内存，形成dom树
        Document doc = db.parse(xmlPath);

        Element root = doc.getDocumentElement();

        return root;
    }

    public static void modify() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        DocumentBuilder db = dbf.newDocumentBuilder();
        //开始解析文件，将文件加载进内存，形成dom树
        Document doc = db.parse("user.xml");

        Element root = doc.getDocumentElement();
        Element per = (Element) selectSingleNode("/user/person[@id='666']",root);
        per.getElementsByTagName("name").item(0).setTextContent("admin");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.transform(new DOMSource(doc),new StreamResult(new File("user.xml")));
    }

    public static void delete() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        DocumentBuilder db = dbf.newDocumentBuilder();
        //开始解析文件，将文件加载进内存，形成dom树
        Document doc = db.parse("user.xml");

        Element root = doc.getDocumentElement();

        Element per = (Element) selectSingleNode("/user/person[@id='666']",root);
        root.removeChild(per);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.transform(new DOMSource(doc),new StreamResult(new File("user.xml")));
    }

    public static void createActive(String activeName,String activePerson,String time){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //开始解析文件，将文件加载进内存，形成dom树
            Document doc = db.parse("active.xml");
            Element root = doc.getDocumentElement();
            Element active = doc.createElement("active");
            //active.setAttribute("id", "777");
            Element name = doc.createElement("name");
            name.setTextContent(activeName);
            Element person = doc.createElement("person");
            person.setTextContent(activePerson);
            Element ttime = doc.createElement("time");
            ttime.setTextContent(time);
            Element shijian = doc.createElement("shijian");
            shijian.setTextContent("-");
            Element endtime = doc.createElement("endtime");
            endtime.setTextContent("-");
            active.appendChild(name);
            active.appendChild(person);
            active.appendChild(ttime);
            active.appendChild(shijian);
            active.appendChild(endtime);

            root.appendChild(active);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(doc), new StreamResult(new File("active.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void loadStudent(String id,String name,String classs){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //开始解析文件，将文件加载进内存，形成dom树
            Document doc = db.parse("student.xml");
            Element root = doc.getDocumentElement();
            Element student = doc.createElement("student");
            student.setAttribute("id", id);
            Element namee = doc.createElement("name");
            namee.setTextContent(name);
            Element classss = doc.createElement("class");
            classss.setTextContent(classs);

            student.appendChild(namee);
            student.appendChild(classss);


            root.appendChild(student);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(doc), new StreamResult(new File("student.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Node selectSingleNode(String express, Element source){
        Node result = null;
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        try{
            result = (Node)xpath.evaluate(express,source, XPathConstants.NODE);
        } catch(XPathExpressionException e ){
            e.printStackTrace();
        }
        return result;
    }

    public static String sign(String activeName, String id, String name,String classs,String time){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //开始解析文件，将文件加载进内存，形成dom树
            Document doc = db.parse("signindetaill.xml");
            NodeList list  = doc.getElementsByTagName("entry");
            for (int i = 0; i < list.getLength(); i++) {
                Element entry = (Element) list.item(i);
//                for(Node node = entry.getFirstChild();node != null;node = node.getNextSibling()) {
//                    if (node.getNodeType() == Node.ELEMENT_NODE) {
//                        if(node.getNodeName().equals("activeName"))
//                           if(node.getFirstChild().getNodeValue().equals(activeName))
//                               if(node.getNextSibling().getFirstChild().getNodeValue().equals(id)){
                if(entry.getElementsByTagName("activeName").item(0).getFirstChild().getNodeValue().equals(activeName))
                    if(entry.getElementsByTagName("personId").item(0).getFirstChild().getNodeValue().equals(id)) {
                        String oldtime = entry.getElementsByTagName("time").item(0).getFirstChild().getNodeValue();
                        String newtime = oldtime + "/" + time;
                        entry.getElementsByTagName("time").item(0).setTextContent(newtime);
                        TransformerFactory factory = TransformerFactory.newInstance();
                        Transformer former = factory.newTransformer();
                        former.transform(new DOMSource(doc), new StreamResult(new File("signindetaill.xml")));
                        return "success";
                    }


            }


  //          if(flag) {
                Element root = doc.getDocumentElement();
                Element entry = doc.createElement("entry");
                Element aName = doc.createElement("activeName");
                aName.setTextContent(activeName);
                Element iid = doc.createElement("personId");
                iid.setTextContent(id);
                Element namee = doc.createElement("personName");
                namee.setTextContent(name);
                Element classss = doc.createElement("class");
                classss.setTextContent(classs);
                Element ttime = doc.createElement("time");
                ttime.setTextContent(time);
                Element alltime = doc.createElement("alltime");
                alltime.setTextContent("-");
                entry.appendChild(aName);
                entry.appendChild(iid);
                entry.appendChild(namee);
                entry.appendChild(classss);
                entry.appendChild(ttime);
                entry.appendChild(alltime);

                root.appendChild(entry);

                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer former = factory.newTransformer();
                former.transform(new DOMSource(doc), new StreamResult(new File("signindetaill.xml")));
    //        }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public static void addShijian(String activeName, String shijian) throws ParserConfigurationException, IOException, SAXException, TransformerException{

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        DocumentBuilder db = dbf.newDocumentBuilder();
        //开始解析文件，将文件加载进内存，形成dom树
        Document doc = db.parse("active.xml");

        NodeList list = doc.getElementsByTagName("active");
        for (int i = 0; i < list.getLength(); i++) {
            Element active = (Element) list.item(i);
            for(Node node = active.getFirstChild();node != null;node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName() == "name")
                       if(node.getFirstChild().getNodeValue().equals(activeName)) {
                           active.getElementsByTagName("shijian").item(0).setTextContent(shijian);
                           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                           active.getElementsByTagName("endtime").item(0).setTextContent(df.format(new Date()));
                       }
                }
            }
        }

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.transform(new DOMSource(doc),new StreamResult(new File("active.xml")));

    }

    public static void caTime(String activeName,String shijian){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        //使用工厂创建文件解析类
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse("signindetaill.xml");
            NodeList list = doc.getElementsByTagName("entry");
            for (int i = 0; i < list.getLength(); i++) {
                Element active = (Element) list.item(i);
                if(active.getElementsByTagName("activeName").item(0).getFirstChild().getNodeValue().equals(activeName)){
                    if(active.getElementsByTagName("time").item(0).getFirstChild().getNodeValue().length()>20){
                        String[] times = active.getElementsByTagName("time").item(0).getFirstChild().getNodeValue().split("/");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            long finalcha = 0;
                            for (int j = 0; j < times.length; j = j + 2) {
                                if (times.length % 2 != 0 && j == times.length - 1) {
                                    Date date1 = sdf.parse(times[j]);
                                    long cha = new Date().getTime() - date1.getTime();
                                    long fenzhong = 1000 * 60;
                                    finalcha += cha / fenzhong;
                                } else {
                                    Date date1 = sdf.parse(times[j]);
                                    Date date2 = sdf.parse(times[j + 1]);
                                    long cha = date2.getTime() - date1.getTime();
                                    long fenzhong = 1000 * 60;
                                    finalcha += cha / fenzhong;
                                }
                            }
                            active.getElementsByTagName("alltime").item(0).setTextContent(String.valueOf(finalcha));


                    } else {
                        active.getElementsByTagName("alltime").item(0).setTextContent(shijian);
                    }

                }


            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(doc),new StreamResult(new File("signindetaill.xml")));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        //开始解析文件，将文件加载进内存，形成dom树

    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
       caTime("qq","40");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String time = df.format(new Date());
//        sign("qq", "31301131", "方佳乐","计算1302",time);
    }


}
