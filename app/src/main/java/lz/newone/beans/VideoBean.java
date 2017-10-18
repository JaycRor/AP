package lz.newone.beans;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoBean {
    //    id: "30",
    //    title: "桃花系列-三生三世背景音乐",
    //    type: "10.9MB",
    //    date: "2017-10-12 11:48:31",
    //    cover: "apk/dynamicdesk/pic/桃花系列-三生三世背景音乐.jpg",
    //    loadtime: "12",
    //    src: "apk/dynamicdesk/videos/桃花系列-三生三世背景音乐.mp4"
    private int id;
    private String title;
    private String type;
    private String date;
    private String cover;     //封面图
    private String loadtime;
    private String src;       //视频路径


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLoadtime() {
        return loadtime;
    }

    public void setLoadtime(String loadtime) {
        this.loadtime = loadtime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
