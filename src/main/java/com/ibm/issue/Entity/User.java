package com.ibm.issue.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//@Data
public class User {
    private String rId;

    private String rName;

    private String rEmail;

    private String rPwd;

    private String rUserid;

    private String rState;

    private Integer rCissue;

    private Integer rRissue;

    private Integer rMissue;
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date rCreatetime;

    public Date getrCreatetime() {
        return rCreatetime;
    }

    public void setrCreatetime(Date rCreatetime) {
        this.rCreatetime = rCreatetime;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrEmail() {
        return rEmail;
    }

    public void setrEmail(String rEmail) {
        this.rEmail = rEmail;
    }

    public String getrPwd() {
        return rPwd;
    }

    public void setrPwd(String rPwd) {
        this.rPwd = rPwd;
    }

    public String getrUserid() {
        return rUserid;
    }

    public void setrUserid(String rUserid) {
        this.rUserid = rUserid;
    }

    public String getrState() {
        return rState;
    }

    public void setrState(String rState) {
        this.rState = rState;
    }

    public Integer getrCissue() {
        return rCissue;
    }

    public void setrCissue(Integer rCissue) {
        this.rCissue = rCissue;
    }

    public Integer getrRissue() {
        return rRissue;
    }

    public void setrRissue(Integer rRissue) {
        this.rRissue = rRissue;
    }

    public Integer getrMissue() {
        return rMissue;
    }

    public void setrMissue(Integer rMissue) {
        this.rMissue = rMissue;
    }
}