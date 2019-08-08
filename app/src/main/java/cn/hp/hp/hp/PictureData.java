package cn.hp.hp.hp;

public class PictureData {

    private String id;
    private String pic_name;
    private String pic_ulr;
    private String update_time;

    public PictureData(String id, String pic_name, String pic_ulr, String update_time) {
        this.id = id;
        this.pic_name = pic_name;
        this.pic_ulr = pic_ulr;
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_ulr() {
        return pic_ulr;
    }

    public void setPic_ulr(String pic_ulr) {
        this.pic_ulr = pic_ulr;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
