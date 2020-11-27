package com.ibm.issue.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;
import lombok.Data;
import java.util.Date;
@Component
//@Data
public class Issue {
    private Integer iNo;

    private String iTitle;
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date iCdate;

    private String iType;

    private String iLevel;

    private String iVesion;

    private String iCreator;

    private String iChangeperson;

    private String iIssuestate;
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date iPlantime;
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date iFinishtime;

    public Integer getiNo() {
        return iNo;
    }

    public void setiNo(Integer iNo) {
        this.iNo = iNo;
    }

    public String getiTitle() {
        return iTitle;
    }

    public void setiTitle(String iTitle) {
        this.iTitle = iTitle;
    }

    public Date getiCdate() {
        return iCdate;
    }

    public void setiCdate(Date iCdate) {
        this.iCdate = iCdate;
    }

    public String getiType() {
        return iType;
    }

    public void setiType(String iType) {
        this.iType = iType;
    }

    public String getiLevel() {
        return iLevel;
    }

    public void setiLevel(String iLevel) {
        this.iLevel = iLevel;
    }

    public String getiVesion() {
        return iVesion;
    }

    public void setiVesion(String iVesion) {
        this.iVesion = iVesion;
    }

    public String getiCreator() {
        return iCreator;
    }

    public void setiCreator(String iCreator) {
        this.iCreator = iCreator;
    }

    public String getiChangeperson() {
        return iChangeperson;
    }

    public void setiChangeperson(String iChangeperson) {
        this.iChangeperson = iChangeperson;
    }

    public String getiIssuestate() {
        return iIssuestate;
    }

    public void setiIssuestate(String iIssuestate) {
        this.iIssuestate = iIssuestate;
    }

    public Date getiPlantime() {
        return iPlantime;
    }

    public void setiPlantime(Date iPlantime) {
        this.iPlantime = iPlantime;
    }

    public Date getiFinishtime() {
        return iFinishtime;
    }

    public void setiFinishtime(Date iFinishtime) {
        this.iFinishtime = iFinishtime;
    }
}