package com.products.safetyfirst.Pojos;

/**
 * Created by profileconnect on 24/04/17.
 */

public class SliderModel {

    private String img_url;
    private String title;
    private String desc;

    public SliderModel(String img_url, String title, String desc) {
        this.img_url = img_url;
        this.title = title;
        this.desc = desc;
    }



    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
