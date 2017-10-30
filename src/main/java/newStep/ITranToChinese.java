package newStep;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import experiment.AAppearedFacet;
import experiment.BResult_delete4;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author 郭朝彤
 * @date 2017/9/20.
 */
public class ITranToChinese {
    static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    static String domain = "Data_structure";

    public static void main(String args[]) {
//        TransToChinese();
//        change();
    }

    /**
     * 用于改掉里面翻译的不好的内容。这个步骤要在翻译之后执行。
     */
    public static void change() {
        String OutputFilePath = oriPath + "9_TransToChinese\\";
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        for (String name : fileName) {
            String cont = "";
            try {
                cont = FileUtils.readFileToString(new File(OutputFilePath + name + ".txt"), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            cont = cont.replaceAll("财产", "特征");
            cont = cont.replaceAll("\"型", "\"类型");
            cont = cont.replaceAll("男朋友", "广度优先搜索");
            try {
                FileUtils.write(new File(OutputFilePath + name + ".txt"), cont, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于把一个主题翻译成中文的分面结构。
     */
    public static void TransToChinese() {
        String InputFilePath = oriPath + "8_AddType\\";
        String OutputFilePath = oriPath + "9_TransToChinese\\";
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        HashSet<String> facets = new HashSet<>();
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            HashSet<String> facetOfOneTopic = AAppearedFacet.FindFacetOfOneTopic(topic);
            facets.addAll(facetOfOneTopic);
            facets.add(name.replaceAll("_", " "));
        }
        HashMap<String, String> map = new HashMap<>();
        for (String s : facets) {
            map.put(s, trans(s));
        }
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            Topic curtopic = TransTopic(topic, map);
            TxtToObject.writeObjToTxt(curtopic, OutputFilePath + name + ".txt");
        }
    }

    /**
     * 把一个主题翻译成中文。由于API限制了翻译次数，所以每个分面翻译一次，保存在一个map结构中。
     *
     * @param oneTopic 需要翻译的主题
     * @param map      中英文分面对照
     * @return 中文的主题分面结构
     */
    public static Topic TransTopic(Topic oneTopic, HashMap<String, String> map) {
        List<Facet> facetList1 = new ArrayList<>();
        if (!oneTopic.getFacets().isEmpty()) {
            for (Facet facet : oneTopic.getFacets()) {
                String f1 = map.get(facet.getName());
                List<Facet> facetList2 = new ArrayList<>();
                if (!facet.getNextFacets().isEmpty()) {
                    for (Facet secFacet : facet.getNextFacets()) {
                        String f2 = map.get(secFacet.getName());
                        List<Facet> facetList3 = new ArrayList<>();
                        List<Facet> empList = new ArrayList<>();
                        if (!secFacet.getNextFacets().isEmpty()) {
                            for (Facet thiFacet : secFacet.getNextFacets()) {
                                String f3 = map.get(thiFacet.getName());
                                Facet facet3 = new Facet(f3, empList);
                                facetList3.add(facet3);
                            }
                        }
                        Facet facet2 = new Facet(f2, facetList3);
                        facetList2.add(facet2);
                    }
                }
                Facet facet1 = new Facet(f1, facetList2);
                facetList1.add(facet1);
            }
        }
        Topic topic = new Topic(map.get(oneTopic.getName()), facetList1);
        return topic;
    }

    /**
     * 把需要翻译的文本放进去，输出翻译后的文本
     * @param query 需要翻译的文本
     * @return 翻译后的文本
     */
    public static String trans(String query) {
        String appKey = "4785de1c9e84789c";
        String salt = String.valueOf(System.currentTimeMillis());
        String from = "EN";
        String to = "zh-CHS";
        String sign = md5(appKey + query + salt + "M79J2srQE41s1voLT8JCgtTCdd5wbSVq");
        Map params = new HashMap();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);
        try {
            System.err.println(requestForHttp("https://openapi.youdao.com/api", params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = "";
        try {
            res = GetTranslation(requestForHttp("https://openapi.youdao.com/api", params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String GetTranslation(String s){
        String res = "";
        JsonParser parse = new JsonParser();
        JsonObject json = (JsonObject) parse.parse(s);
        res = json.get("translation").getAsString();
        return res;
    }


    public static String requestForHttp(String url, Map requestParams) throws Exception {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /**HttpPost*/
        HttpPost httpPost = new HttpPost(url);
        List params = new ArrayList();
        Iterator<Map.Entry<String, String>> it = requestParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry en = it.next();
            String key = (String) en.getKey();
            String value = (String) en.getValue();
            if (value != null) {
                params.add(new BasicNameValuePair(key, value));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        /**HttpResponse*/
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 生成32位MD5摘要
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        byte[] btInput = new byte[0];
        try {
            btInput = string.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /** 获得MD5摘要算法的 MessageDigest 对象 */
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        /** 使用指定的字节更新摘要 */
        mdInst.update(btInput);
        /** 获得密文 */
        byte[] md = mdInst.digest();
        /** 把密文转换成十六进制的字符串形式 */
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 根据api地址和参数生成请求URL
     *
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (Object key : params.keySet()) {
            String value = (String) params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    /**
     * 进行URL编码
     *
     * @param input
     * @return
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
}
