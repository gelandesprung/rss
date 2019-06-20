package top.froms.www.repo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class IMGRepo {
//    private final String url = "https://rsshub.app/tits-guru/home";
    private final static String url = "https://rsshub.app/jandan/ooxx";
    private final Pattern pattern = Pattern.compile("\\\"([http|https]\\S+)\\\"");
    private OkHttpClient okHttpClient;

    public IMGRepo() {
        okHttpClient = new OkHttpClient.Builder()
                .build();
    }


    public List<ImgData> data() {
//
//    }
//    private List<ImgData> dbLoader(){
//
//    }
//    public List<ImgData> netLoader() {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response resp = okHttpClient.newCall(request).execute();
            String result = resp.body().string();
            resp.close();
            return parse(result);
        } catch (IOException e) {
            return new ArrayList<>(0);
        }
    }

    private List<ImgData> parse(String data) {
        List<ImgData> infos = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(data.getBytes()));
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                //通过 item(i)方法 获取一个book节点，nodelist的索引值从0开始
                Node book = nodeList.item(i);
                //解析book节点的子节点
                NodeList childNodes = book.getChildNodes();
                //遍历childNodes获取每个节点的节点名和节点值
                ImgData imgData = new ImgData();
                for (int k = 0; k < childNodes.getLength(); k++) {
                    //区分出text类型的node以及element类型的node
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        //获取了element类型节点的节点名
                        switch (childNodes.item(k).getNodeName()) {
                            case "title":
                                imgData.setTitle(childNodes.item(k).getFirstChild().getNodeValue());
                                break;
                            case "description":
                                Matcher m = pattern.matcher(childNodes.item(k).getFirstChild().getNodeValue());
                                if (m.find()) {
                                    imgData.setImgUrl(m.group(1));
                                }
                                break;
                            case "pubDate":
                                imgData.setPublishTime(childNodes.item(k).getFirstChild().getNodeValue());
                                break;
                            case "guid":
                                break;
                            case "link":
                                break;
                            default:
                                break;
                        }
                    }
                }
                infos.add(imgData);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return infos;
    }
}
