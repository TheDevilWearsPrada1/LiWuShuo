package com.qf.liwushuo.bean;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class QQLoginBean {

    /**
     * ret : 0
     * openid : 2747D160EB62A72429CA215D7D917828
     * access_token : CD1D6BF52A6109618C0AFFCBCA13F964
     * pay_token : B59DC0C770A6140BD6E5843908FED766
     * expires_in : 7776000
     * pf : desktop_m_qq-10000144-android-2002-
     * pfkey : c61e48aa21a96d5e4ede525031bcb105
     * msg :
     * login_cost : 272
     * query_authority_cost : 484
     * authority_cost : 0
     */

    private int ret;
    private String openid;
    private String access_token;
    private String pay_token;
    private int expires_in;
    private String pf;
    private String pfkey;
    private String msg;
    private int login_cost;
    private int query_authority_cost;
    private int authority_cost;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(int login_cost) {
        this.login_cost = login_cost;
    }

    public int getQuery_authority_cost() {
        return query_authority_cost;
    }

    public void setQuery_authority_cost(int query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }

    public int getAuthority_cost() {
        return authority_cost;
    }

    public void setAuthority_cost(int authority_cost) {
        this.authority_cost = authority_cost;
    }
}
