package com.spring.shop.models;




import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title, info, image;

    public Item(){}
    public Item(String title, String info, String image, float price) {
        this.title = title;
        this.info = info;
        this.image = image;
        this.price = price;
    }

    public Item(String title, String info, String image, float price, MyUser user, String phoneNumber) {
        this.title = title;
        this.info = info;
        this.image = image;
        this.price = price;
        this.user = user;
        this.phoneNumber = phoneNumber;
    }

    private float price;

    private String phoneNumber;
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    private MyUser user;

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
