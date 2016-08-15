package com.example.text.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 刘海风 on 2016/8/1.
 */

public class science implements Serializable{
    private String now;
    private String ok;
    private String limit;
    private ArrayList<result> result;
    private String offset;
    private String total;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public ArrayList<com.example.text.beans.result> getResult() {
        return result;
    }

    public void setResult(ArrayList<com.example.text.beans.result> result) {
        this.result = result;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
