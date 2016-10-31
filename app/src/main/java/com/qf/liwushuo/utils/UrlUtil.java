package com.qf.liwushuo.utils;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class UrlUtil {
    //http://api.liwushuo.com/v2/channels/preset?gender=1&generation=2
    public static final String URL_HOME = "http://api.liwushuo.com";
    public static final String URL_HOMETOP = "/v2/channels/preset";
    public static final String URL_HOMEITEM = "/v2/channels";
    //    http://api.liwushuo.com/v2/banners
    public static final String URL_BANNERS = "/v2/banners";
    public static final String MRTJ = "http://api.liwushuo.com/v2/ranks_v2/ranks/1?limit=20&offset=0";
    public static final String Top100 = "http://api.liwushuo.com/v2/ranks_v2/ranks/2?limit=20&offset=0";
    public static final String Dependence = "http://api.liwushuo.com/v2/ranks_v2/ranks/3?limit=20&offset=0";
    public static final String NewStars = "http://api.liwushuo.com/v2/ranks_v2/ranks/4?limit=20&offset=0";

    public static final String Webs_List = "http://api.liwushuo.com/v2/items/";
    public static final String Comment_List = "/comments?limit=20&offset=0";
    public static final String List_recycle = "/recommend?num=20&post_num=5";

    //搜索热词
    //http://api.liwushuo.com/v2/search/hot_words_v2
    public static final String URL_SEARCH_HOT="/v2/search/hot_words_v2";
    public static final String URL_SEARCH_INFO="/v2/search/item";
    //首页item
    //http://api.liwushuo.com/v2/posts_v2/1046252
    public static final String URL_HOME_ITEM_INFO="/v2/posts_v2";
}
