package lz.newone.video;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import lz.newone.beans.VideoBean;
import lz.newone.utils.JsonUtils;
import lz.newone.utils.LogUtils;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoJsonUtils {
    private final static String TAG = "VideoJsonUtils";

    /**
     * 将获取的视频json转换为视频列表对象
     */
    public static List<VideoBean> getVideoList(String res){
        List<VideoBean> beanList = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray  = parser.parse(res).getAsJsonArray();
            if(jsonArray == null) {
                return null;
            }
            for (int i = 0;i<jsonArray.size();i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                VideoBean video = JsonUtils.deserialize(jo,VideoBean.class);
                beanList.add(video);

            }

        }catch (Exception e){
            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }

        return beanList;
    }
}
