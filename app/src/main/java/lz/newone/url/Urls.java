package lz.newone.url;

/**
 * Description : 接口API的URL
 * Author : lauren and KevinAo
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/13
 */
public class Urls {

    //http://c.m.163.com/nc/article/headline/T1348647909107/0-5.html  新闻

    public static final int PAZE_SIZE = 20;

    public static final String HOST = "http://c.m.163.com/";
    public static final String END_URL = "-" + PAZE_SIZE + ".html";
    public static final String END_DETAIL_URL = "/full.html";
    // 头条
    public static final String TOP_URL = HOST + "nc/article/headline/";
    public static final String TOP_ID = "T1348647909107";
    // 新闻详情
    public static final String NEW_DETAIL = HOST + "nc/article/";

    public static final String COMMON_URL = HOST + "nc/article/list/";

    // 汽车
    public static final String CAR_ID = "T1348654060988";
    // 笑话
    public static final String JOKE_ID = "T1350383429665";
    // nba
    public static final String NBA_ID = "T1348649145984";


    // 天气预报url
    public static final String WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";

    //阿里云定位
    public static final String INTERFACE_LOCATION = "http://gc.ditu.aliyun.com/regeocoding?l=";

    //视频，个人服务器数据...
    public static final String ZX_WEB = "http://www.easydone.top/";
    public static final String ZX_VIDEO = "http://www.easydone.top/app/video.json";

}
