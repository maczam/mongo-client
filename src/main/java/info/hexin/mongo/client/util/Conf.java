package info.hexin.mongo.client.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析配置文件，默认优先级为先解析jmp-config.xml然后app-config.xml<br>
 * 
 * @author hexin
 * 
 */
public class Conf {

    private static final String jmp_config = "jmp-config.xml";
    private static final String app_config = "app-config.xml";

    private static Conf sysConf = initSysConf();
    private final Map<String, Object> valuseMap = new HashMap<String, Object>();
    private Document doc;

    private Conf() {
    }

    /**
     * 初始化默认配置文件
     * 
     * @return
     */
    private static Conf initSysConf() {
        Conf conf = new Conf();
        InputStream inputStream = Conf.class.getClassLoader().getResourceAsStream(jmp_config);
        if (inputStream == null) {
            inputStream = Conf.class.getClassLoader().getResourceAsStream(app_config);
        }

        if (inputStream != null) {
            Document document = parseDocument(inputStream);
            conf.parseDoc(document);
        }
        return conf;
    }

    /**
     * 按照配置文件进行解析
     * 
     * @param fileName
     * @return
     */
    public static Conf parseFile(String fileName) {
        InputStream inputStream = Conf.class.getClassLoader().getResourceAsStream(fileName);
        return parse(inputStream);
    }

    /**
     * 将传递进来的配置文件和默认配置文件合并
     * 
     * @param in
     * @return
     */
    public synchronized static Conf parse(InputStream in) {
        try {
            Conf conf = new Conf();
            conf.doc = parseDocument(in);
            conf.parseDoc(conf.doc);
            return sysConf().addAllConfig(conf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Conf addAllConfig(Conf conf) {
        return conf;
    }

    /**
     * 生成Document
     * 
     * @param in
     * @return
     */
    private static Document parseDocument(InputStream in) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析xml字符串
     * 
     * @param contxt
     * @return
     */
    public static Conf parse(String contxt) {
        return parse(contxt.getBytes());
    }

    public static Conf parse(byte[] b) {
        ByteArrayInputStream stream = new ByteArrayInputStream(b);
        return parse(stream);
    }

    /**
     * 动态的增加配置，默认不会覆盖之前的配置。只能为config增加子节点
     * 
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        add(key, value, false);
    }

    /**
     * 动态的增加配置，如果之前存在，那么修改成新值
     * 
     * @param key
     * @param value
     */
    public void add(String key, Object value, boolean overWrite) {
        if (valuseMap.containsKey(key)) {
            if (!overWrite) {
                return;
            }
        }
        valuseMap.put(key, value);
        NodeList rootList = doc.getElementsByTagName("config");
        Node root = rootList.item(0);
        Element valueNode = doc.createElement(key);
        valueNode.appendChild(doc.createTextNode(String.valueOf(value)));
        root.appendChild(valueNode);
    }

    /**
     * 专门负责 解析doc
     * 
     * @param doc
     */
    private void parseDoc(Document doc) {
        if (doc.hasChildNodes()) {
            NodeList nodeList = doc.getChildNodes();
            Node root = nodeList.item(0);
            if (root.getNodeName().equals("config")) {
                NodeList valueNodeList = root.getChildNodes();
                for (int i = 0; i < valueNodeList.getLength(); i++) {
                    Node valueNode = valueNodeList.item(i);
                    String nodeName = valueNode.getNodeName();
                    String value = valueNode.getTextContent();
                    if ("#text".equals(nodeName) || "#comment".equals(nodeName)) {
                        // 跳过注释和空行
                        continue;
                    }
                    valuseMap.put(nodeName, value);
                }
            }
        }
    }

    /**
     * 默认调用jmp-config.xml app-config.xml
     * 
     * @return
     */
    public static Conf sysConf() {
        return sysConf;
    }

    // // 下面为获取值
    public String getString(String key) {
        return String.valueOf(valuseMap.get(key));
    }

    public long getLong(String key) {
        return Long.valueOf(getString(key));
    }

    public int getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    /**
     * 将内存中配置文件处处为xml字符串
     */
    public String toString() {
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer t = tf.newTransformer();
            t.setOutputProperty("encoding", "utf-8");// 解决中文问题，试过用GBK不行
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(doc), new StreamResult(bos));
            return bos.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
