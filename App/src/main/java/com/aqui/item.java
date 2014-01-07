package com.aqui;

/**
 * Created by Jorge on 19/12/13.
 */
public class item {

    private Integer logo;
    private String place;

    public item() {
        super();
    }

    public item(Integer image, String title) {
        super();
        this.logo = image;
        this.place = title;

    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer image) {
        this.logo = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String title) {
        this.place = title;
    }
}
