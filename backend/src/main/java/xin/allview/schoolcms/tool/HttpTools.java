package xin.allview.schoolcms.tool;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;

/**
 * HttpTools
 * Created by tudou on 17/3/23.
 */
public class HttpTools {

    /**
     * 公共方法jsoup
     *
     * @param url  地址
     * @param data 参数
     * @return 返回数据
     */
    public String get(String url, Map<String, String> data) throws Exception {
        Connection.Response response = null;
        response = Jsoup.connect(url).data(data).method(Connection.Method.GET).ignoreContentType(true)
                 .execute();
        return response.body();
    }

    public  String post(String url, Map<String, String> data) throws Exception {
        Connection.Response response = Jsoup.connect(url).data(data).method(Connection.Method.POST).ignoreContentType(true).execute();
        System.out.println(response.body());
        return response.body();
    }

    public  String put(String url, Map<String, String> data) throws Exception {
        Connection.Response response = Jsoup.connect(url).data(data).method(Connection.Method.PUT)
                .ignoreContentType(true).execute();
        System.out.println(response.body());
        return response.body();
    }

    public static String delete(String url, Map<String, String> data) throws Exception {
        Connection.Response response = Jsoup.connect(url).data(data).method(Connection.Method.DELETE)
                .ignoreContentType(true).execute();
        System.out.println(response.body());
        return response.body();
    }

}
