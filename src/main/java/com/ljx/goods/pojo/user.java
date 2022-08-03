package com.ljx.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class user {

    //用户id
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    //用户姓名
    private String name;

    //用户性别
    private Integer sex;

    //用户邮箱
    private String email;

    //用户地址
    private String address;

    //用户账号
    private String username;

    //用户密码
    private String password;

    //用户电话
    private String telephone;


    public user() {
    }

    public user(Integer id, String name, Integer sex, String email, String address, String username, String password, String telephone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
