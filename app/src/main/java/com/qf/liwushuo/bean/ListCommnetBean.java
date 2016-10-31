package com.qf.liwushuo.bean;

import java.util.List;

/**
 * Created by Bill on 2016/10/26.
 */

public class ListCommnetBean {

    /**
     * code : 200
     * data : {"comments":[{"content":"同感","created_at":1477450275,"id":516965,"images":[],"item_id":1069269,"replied_comment":{"content":"这个还不错，就是容易脏，打开的那条缝容易粘灰","created_at":1477138313,"id":516436,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user_id":2024627},"replied_user":{"avatar_url":"http://img03.liwushuo.com/avatar/160125/f9e5a50ab_a-w180","can_mobile_login":false,"guest_uuid":null,"id":2024627,"nickname":"我叫UJJ","role":0},"reply_to_id":516436,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160722/bcbb413ea_a-w180","can_mobile_login":false,"guest_uuid":null,"id":7117433,"nickname":"莫^^忘怀","role":0}},{"content":"这个还不错，就是容易脏，打开的那条缝容易粘灰","created_at":1477138313,"id":516436,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160125/f9e5a50ab_a-w180","can_mobile_login":false,"guest_uuid":null,"id":2024627,"nickname":"我叫UJJ","role":0}},{"content":"在用润唇球 擦口红前打下底的 涂起来会很顺  润唇球快秃了 准备回购","created_at":1477006214,"id":516005,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img01.liwushuo.com/avatar/20150927/3bapey4vx_i.png-w180","can_mobile_login":true,"guest_uuid":null,"id":2262524,"nickname":"ciel","role":0}},{"content":"有人买过吗？","created_at":1476418438,"id":514730,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151117/309d71806_a-w180","can_mobile_login":true,"guest_uuid":null,"id":5659040,"nickname":"安槿。","role":0}},{"content":"我看成了跳蛋。。。","created_at":1476198944,"id":514333,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"","can_mobile_login":true,"guest_uuid":null,"id":7148222,"nickname":"15572733962","role":0}},{"content":"还不错哦，价格也合适","created_at":1476186349,"id":514276,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img03.liwushuo.com/avatar/160824/9e87230c7_a-w180","can_mobile_login":false,"guest_uuid":null,"id":7402008,"nickname":"Forget","role":0}}],"paging":{"next_url":"http://api.liwushuo.com/v2/items/1069269/comments?limit=20&offset=20"}}
     * message : OK
     */

    private int code;
    /**
     * comments : [{"content":"同感","created_at":1477450275,"id":516965,"images":[],"item_id":1069269,"replied_comment":{"content":"这个还不错，就是容易脏，打开的那条缝容易粘灰","created_at":1477138313,"id":516436,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user_id":2024627},"replied_user":{"avatar_url":"http://img03.liwushuo.com/avatar/160125/f9e5a50ab_a-w180","can_mobile_login":false,"guest_uuid":null,"id":2024627,"nickname":"我叫UJJ","role":0},"reply_to_id":516436,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160722/bcbb413ea_a-w180","can_mobile_login":false,"guest_uuid":null,"id":7117433,"nickname":"莫^^忘怀","role":0}},{"content":"这个还不错，就是容易脏，打开的那条缝容易粘灰","created_at":1477138313,"id":516436,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160125/f9e5a50ab_a-w180","can_mobile_login":false,"guest_uuid":null,"id":2024627,"nickname":"我叫UJJ","role":0}},{"content":"在用润唇球 擦口红前打下底的 涂起来会很顺  润唇球快秃了 准备回购","created_at":1477006214,"id":516005,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img01.liwushuo.com/avatar/20150927/3bapey4vx_i.png-w180","can_mobile_login":true,"guest_uuid":null,"id":2262524,"nickname":"ciel","role":0}},{"content":"有人买过吗？","created_at":1476418438,"id":514730,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151117/309d71806_a-w180","can_mobile_login":true,"guest_uuid":null,"id":5659040,"nickname":"安槿。","role":0}},{"content":"我看成了跳蛋。。。","created_at":1476198944,"id":514333,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"","can_mobile_login":true,"guest_uuid":null,"id":7148222,"nickname":"15572733962","role":0}},{"content":"还不错哦，价格也合适","created_at":1476186349,"id":514276,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user":{"avatar_url":"http://img03.liwushuo.com/avatar/160824/9e87230c7_a-w180","can_mobile_login":false,"guest_uuid":null,"id":7402008,"nickname":"Forget","role":0}}]
     * paging : {"next_url":"http://api.liwushuo.com/v2/items/1069269/comments?limit=20&offset=20"}
     */

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
         * next_url : http://api.liwushuo.com/v2/items/1069269/comments?limit=20&offset=20
         */

        private PagingBean paging;
        /**
         * content : 同感
         * created_at : 1477450275
         * id : 516965
         * images : []
         * item_id : 1069269
         * replied_comment : {"content":"这个还不错，就是容易脏，打开的那条缝容易粘灰","created_at":1477138313,"id":516436,"images":[],"item_id":1069269,"reply_to_id":null,"show":true,"status":1,"user_id":2024627}
         * replied_user : {"avatar_url":"http://img03.liwushuo.com/avatar/160125/f9e5a50ab_a-w180","can_mobile_login":false,"guest_uuid":null,"id":2024627,"nickname":"我叫UJJ","role":0}
         * reply_to_id : 516436
         * show : true
         * status : 1
         * user : {"avatar_url":"http://img02.liwushuo.com/avatar/160722/bcbb413ea_a-w180","can_mobile_login":false,"guest_uuid":null,"id":7117433,"nickname":"莫^^忘怀","role":0}
         */

        private List<CommentsBean> comments;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class PagingBean {
            private String next_url;

            public String getNext_url() {
                return next_url;
            }

            public void setNext_url(String next_url) {
                this.next_url = next_url;
            }
        }

        public static class CommentsBean {
            private String content;
            private int created_at;
            private int id;
            private int item_id;
            /**
             * content : 这个还不错，就是容易脏，打开的那条缝容易粘灰
             * created_at : 1477138313
             * id : 516436
             * images : []
             * item_id : 1069269
             * reply_to_id : null
             * show : true
             * status : 1
             * user_id : 2024627
             */

            private RepliedCommentBean replied_comment;
            /**
             * avatar_url : http://img03.liwushuo.com/avatar/160125/f9e5a50ab_a-w180
             * can_mobile_login : false
             * guest_uuid : null
             * id : 2024627
             * nickname : 我叫UJJ
             * role : 0
             */

            private RepliedUserBean replied_user;
            private int reply_to_id;
            private boolean show;
            private int status;
            /**
             * avatar_url : http://img02.liwushuo.com/avatar/160722/bcbb413ea_a-w180
             * can_mobile_login : false
             * guest_uuid : null
             * id : 7117433
             * nickname : 莫^^忘怀
             * role : 0
             */

            private UserBean user;
            private List<?> images;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public RepliedCommentBean getReplied_comment() {
                return replied_comment;
            }

            public void setReplied_comment(RepliedCommentBean replied_comment) {
                this.replied_comment = replied_comment;
            }

            public RepliedUserBean getReplied_user() {
                return replied_user;
            }

            public void setReplied_user(RepliedUserBean replied_user) {
                this.replied_user = replied_user;
            }

            public int getReply_to_id() {
                return reply_to_id;
            }

            public void setReply_to_id(int reply_to_id) {
                this.reply_to_id = reply_to_id;
            }

            public boolean isShow() {
                return show;
            }

            public void setShow(boolean show) {
                this.show = show;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public List<?> getImages() {
                return images;
            }

            public void setImages(List<?> images) {
                this.images = images;
            }

            public static class RepliedCommentBean {
                private String content;
                private int created_at;
                private int id;
                private int item_id;
                private Object reply_to_id;
                private boolean show;
                private int status;
                private int user_id;
                private List<?> images;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(int created_at) {
                    this.created_at = created_at;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getItem_id() {
                    return item_id;
                }

                public void setItem_id(int item_id) {
                    this.item_id = item_id;
                }

                public Object getReply_to_id() {
                    return reply_to_id;
                }

                public void setReply_to_id(Object reply_to_id) {
                    this.reply_to_id = reply_to_id;
                }

                public boolean isShow() {
                    return show;
                }

                public void setShow(boolean show) {
                    this.show = show;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public List<?> getImages() {
                    return images;
                }

                public void setImages(List<?> images) {
                    this.images = images;
                }
            }

            public static class RepliedUserBean {
                private String avatar_url;
                private boolean can_mobile_login;
                private Object guest_uuid;
                private int id;
                private String nickname;
                private int role;

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public boolean isCan_mobile_login() {
                    return can_mobile_login;
                }

                public void setCan_mobile_login(boolean can_mobile_login) {
                    this.can_mobile_login = can_mobile_login;
                }

                public Object getGuest_uuid() {
                    return guest_uuid;
                }

                public void setGuest_uuid(Object guest_uuid) {
                    this.guest_uuid = guest_uuid;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }
            }

            public static class UserBean {
                private String avatar_url;
                private boolean can_mobile_login;
                private Object guest_uuid;
                private int id;
                private String nickname;
                private int role;

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public boolean isCan_mobile_login() {
                    return can_mobile_login;
                }

                public void setCan_mobile_login(boolean can_mobile_login) {
                    this.can_mobile_login = can_mobile_login;
                }

                public Object getGuest_uuid() {
                    return guest_uuid;
                }

                public void setGuest_uuid(Object guest_uuid) {
                    this.guest_uuid = guest_uuid;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }
            }
        }
    }
}
