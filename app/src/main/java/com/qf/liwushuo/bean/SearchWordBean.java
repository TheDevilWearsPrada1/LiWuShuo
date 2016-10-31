package com.qf.liwushuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchWordBean {

    /**
     * code : 200
     * data : {"words":["混合","红褐","黑盒子","仙鹤","吓人","和人","赫本","贺卡","核桃","盒子","言和","薄荷","合服","综合","贺卡","喝酒","几何","饭盒","闭合","赫拉","和服","喝茶","组合","几何","锁和"]}
     * message : OK
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<String> words;

        public List<String> getWords() {
            return words;
        }

        public void setWords(List<String> words) {
            this.words = words;
        }
    }
}
