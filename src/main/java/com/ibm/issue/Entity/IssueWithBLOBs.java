package com.ibm.issue.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
//@Data
public class IssueWithBLOBs extends Issue {
    private String iReappear;

    private String iHandlemethod;

    public String getiReappear() {
        return iReappear;
    }

    public void setiReappear(String iReappear) {
        this.iReappear = iReappear;
    }

    public String getiHandlemethod() {
        return iHandlemethod;
    }

    public void setiHandlemethod(String iHandlemethod) {
        this.iHandlemethod = iHandlemethod;
    }
}