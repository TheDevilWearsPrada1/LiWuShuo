package com.qf.liwushuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class BannerBean {


    /**
     * code : 200
     * data : {"banners":[{"ad_monitors":[],"channel":"all","id":752,"image_url":"http://img03.liwushuo.com/image/161024/9afgfe7xw.jpg-w720","order":800,"status":0,"target_id":1046229,"target_type":"url","target_url":"liwushuo:///page?page_action=navigation&login=false&type=post&post_id=1046229","type":"post","webp_url":"http://img03.liwushuo.com/image/161024/9afgfe7xw.jpg?imageView2/2/w/720/q/85/format/webp"},{"ad_monitors":[],"channel":"all","id":730,"image_url":"http://img03.liwushuo.com/image/160929/68bib1c1a.jpg-w720","order":756,"status":0,"target_id":null,"target_url":"liwushuo:///page?type=dailylucky","type":"url","webp_url":"http://img03.liwushuo.com/image/160929/68bib1c1a.jpg?imageView2/2/w/720/q/85/format/webp"},{"ad_monitors":[],"channel":"all","id":747,"image_url":"http://img01.liwushuo.com/image/161019/1djvtauzt.jpg-w720","order":755,"status":0,"target":{"banner_image_url":"http://img03.liwushuo.com/image/161019/9vk4l5eue.jpg-w300","banner_webp_url":"http://img03.liwushuo.com/image/161019/9vk4l5eue.jpg?imageView2/2/w/300/q/85/format/webp","cover_image_url":"http://img01.liwushuo.com/image/161019/n1sbukyiy.jpg-w720","cover_webp_url":"http://img01.liwushuo.com/image/161019/n1sbukyiy.jpg?imageView2/2/w/720/q/85/format/webp","created_at":1476847631,"id":365,"posts_count":9,"status":1,"subtitle":"赚钱有动力，花钱有底气","title":"品质钱包","updated_at":1477131067},"target_id":365,"target_type":"url","target_url":"liwushuo:///page?page_action=navigation&login=false&type=topic&topic_id=365","type":"collection","webp_url":"http://img01.liwushuo.com/image/161019/1djvtauzt.jpg?imageView2/2/w/720/q/85/format/webp"},{"ad_monitors":[],"channel":"all","id":750,"image_url":"http://img01.liwushuo.com/image/161021/t79blas7g.jpg-w720","order":754,"status":0,"target":{"banner_image_url":"http://img02.liwushuo.com/image/161021/ldr1n6gbr.jpg-w300","banner_webp_url":"http://img02.liwushuo.com/image/161021/ldr1n6gbr.jpg?imageView2/2/w/300/q/85/format/webp","cover_image_url":"http://img02.liwushuo.com/image/161021/vjvhqjbbs.jpg-w720","cover_webp_url":"http://img02.liwushuo.com/image/161021/vjvhqjbbs.jpg?imageView2/2/w/720/q/85/format/webp","created_at":1477018077,"id":376,"posts_count":8,"status":1,"subtitle":"秋冬护肤针对性攻略，来快喝下这碗\u201c护肤鸡汤\u201d","title":"秋冬护肤指南","updated_at":1477018543},"target_id":376,"target_type":"url","target_url":"liwushuo:///page?page_action=navigation&login=false&type=topic&topic_id=376","type":"collection","webp_url":"http://img01.liwushuo.com/image/161021/t79blas7g.jpg?imageView2/2/w/720/q/85/format/webp"},{"ad_monitors":[],"channel":"all","id":746,"image_url":"http://img02.liwushuo.com/image/161019/qoemwddj2.jpg-w720","order":753,"status":0,"target":{"banner_image_url":"http://img03.liwushuo.com/image/161019/n049j1mjo.jpg-w300","banner_webp_url":"http://img03.liwushuo.com/image/161019/n049j1mjo.jpg?imageView2/2/w/300/q/85/format/webp","cover_image_url":"http://img02.liwushuo.com/image/161019/y6e3b5ur4.jpg-w720","cover_webp_url":"http://img02.liwushuo.com/image/161019/y6e3b5ur4.jpg?imageView2/2/w/720/q/85/format/webp","created_at":1476847181,"id":364,"posts_count":10,"status":1,"subtitle":"填饱肚子，心才不孤单","title":"甜心零食","updated_at":1476847481},"target_id":364,"target_type":"url","target_url":"liwushuo:///page?page_action=navigation&login=false&type=topic&topic_id=364","type":"collection","webp_url":"http://img02.liwushuo.com/image/161019/qoemwddj2.jpg?imageView2/2/w/720/q/85/format/webp"}]}
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
        /**
         * ad_monitors : []
         * channel : all
         * id : 752
         * image_url : http://img03.liwushuo.com/image/161024/9afgfe7xw.jpg-w720
         * order : 800
         * status : 0
         * target_id : 1046229
         * target_type : url
         * target_url : liwushuo:///page?page_action=navigation&login=false&type=post&post_id=1046229
         * type : post
         * webp_url : http://img03.liwushuo.com/image/161024/9afgfe7xw.jpg?imageView2/2/w/720/q/85/format/webp
         */

        private List<BannersBean> banners;

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class BannersBean {
            private String channel;
            private int id;
            private String image_url;
            private int order;
            private int status;
            private int target_id;
            private String target_type;
            private String target_url;
            private String type;
            private String webp_url;
            private List<?> ad_monitors;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTarget_id() {
                return target_id;
            }

            public void setTarget_id(int target_id) {
                this.target_id = target_id;
            }

            public String getTarget_type() {
                return target_type;
            }

            public void setTarget_type(String target_type) {
                this.target_type = target_type;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWebp_url() {
                return webp_url;
            }

            public void setWebp_url(String webp_url) {
                this.webp_url = webp_url;
            }

            public List<?> getAd_monitors() {
                return ad_monitors;
            }

            public void setAd_monitors(List<?> ad_monitors) {
                this.ad_monitors = ad_monitors;
            }
        }
    }
}
