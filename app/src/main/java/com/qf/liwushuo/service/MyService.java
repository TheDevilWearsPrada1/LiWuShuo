package com.qf.liwushuo.service;


import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.bean.DanpinBean;
import com.qf.liwushuo.bean.DanpinItemsBean;
import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.bean.GonglueHeaderBean;
import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.bean.HomeItemInfoBean;
import com.qf.liwushuo.bean.LanmuItemsBean;
import com.qf.liwushuo.bean.SearchHotWordBean;
import com.qf.liwushuo.bean.SearchRBean;
import com.qf.liwushuo.bean.SearchWordBean;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.utils.UrlUtil;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static android.R.attr.id;

/**
 * Created by Administrator on 2016/10/14 0014.
 */

public interface MyService {
    /*//http://dt.kkeji.com/api/1/contents?min_pub_time=0&min_show_time=0&udid=5588409082775759558&article_type=-1&xaid=a824f01324278515&sign=-535831208
    @GET("")
    Observable<String> getZhuXingData(@Query("min_pub_time") int min_pub_time,
                                      @Query("min_show_time") int min_show_time,
                                      @Query("udid") String udid,
                                      @Query("article_type") int article_type,
                                      @Query("xaid") String xaid,
                                      @Query("sign") int sign
    );
   */
    //http://api.liwushuo.com/v2/channels/preset?gender=1&generation=2
    @GET(UrlUtil.URL_HOMETOP)
    Observable<TabBean> getTab(@Query("gender") int gender,
                               @Query("generation") int generation);

    // 精选
    //http://api.liwushuo.com/v2/channels/101/items_v2?ad=2&gender=1&generation=2&limit=20&offset=0
    @GET(UrlUtil.URL_HOMEITEM + "/{id}/items_v2")
    Observable<HomeItemBean> getData(@Path("id") int id,
                                     @Query("ad") int ad,
                                     @Query("gender") int gender,
                                     @Query("generation") int generation,
                                     @Query("limit") int limit,
                                     @Query("offset") int offset);

    //分类攻略
    @GET("/v2/channel_groups/all")
    Observable<GonglueBean> getGonglueData();

    //分类栏目
    @GET("/v2/columns?limit=20&offset=0")
    Observable<GonglueHeaderBean> getGonglueHeaderData();

    //分类栏目items
    //http://api.liwushuo.com/v2/columns/101?limit=20&offset=0
    @GET("v2/columns/{id}")
    Observable<LanmuItemsBean> getLanmuItemsData(@Path("id") int id,
                                                 @Query("limit") int limit,
                                                 @Query("offset") int offset);
    //分类单品
    @GET("/v2/item_categories/tree")
    Observable<DanpinBean> getDanpinData();
    //分类单品的item
    // http://api.liwushuo.com/v2/item_subcategories/7/items?limit=20&offset=0
    @GET("v2/item_subcategories/{id}/items")
    Observable<DanpinItemsBean> getDanpinItemsData(@Path("id") int id,@Query("limit") int limit,@Query("offset") int offset);
    //精选广告轮播
    @GET(UrlUtil.URL_BANNERS)
    Observable<BannerBean> getBannersData();

    //搜索热词
    @GET(UrlUtil.URL_SEARCH_HOT)
    Observable<SearchHotWordBean> getHotWord();

    //搜索单品详情
//    http://api.liwushuo.com/v2/search/item?sort=&limit=20&offset=0&keyword=%E9%92%A2%E7%AC%94
    @GET(UrlUtil.URL_SEARCH_INFO)
    Observable<SearchRBean> getSearchInfo(
            @Query("sort") String sort,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("keyword") String keyword
    );

    //搜索文字改变响应
//    http://api.liwushuo.com/v2/search/word_completed?keyword=%E5%91%B5%E5%91%B5
    @GET("/v2/search/word_completed")
    Observable<SearchWordBean> getWord(@Query("keyword") String keyword);

    //首页item
    @GET(UrlUtil.URL_HOME_ITEM_INFO + "/{id}")
    Observable<HomeItemInfoBean> getInfo(@Path("id") int id);

}
