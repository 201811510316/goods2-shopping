package com.ljx.goods.pojo;

public class goodsAd {
    private Integer id;
    private String name;
    private String link;
    private String imageUrl;
    private Integer enabled;

    public goodsAd() {
    }

    public goodsAd(Integer id, String name, String link, String imageUrl, Integer enabled) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.imageUrl = imageUrl;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "goodsAd{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", enabled=" + enabled +
                '}';
    }

}
