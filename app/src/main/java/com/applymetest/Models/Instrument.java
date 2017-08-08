package com.applymetest.Models;

/**
 * Created by bogachov on 08.08.17.
 */

public class Instrument {
    private Integer title;
    private Integer color;
    private Integer likeCount;
    private Integer image;
    private Float percent;

    public Instrument(
            Integer title,
            Integer color,
            Integer likeCount,
            Integer image,
            Float percent
    ){
        this.title = title;
        this.color = color;
        this.likeCount = likeCount;
        this.image = image;
        this.percent = percent;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getColor() {
        return color;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getTitle() {
        return title;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Float getPercent() {
        return percent;
    }

    public void addLikeCount(){
        this.likeCount = likeCount + 1;
    }
}
