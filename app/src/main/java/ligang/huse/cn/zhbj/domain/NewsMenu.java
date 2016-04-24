package ligang.huse.cn.zhbj.domain;

import java.util.List;

/**
 * javaBean实体类
 */
public class NewsMenu {


    /**
     * extend : [10007,10006,10008,10014,10012,10091,10009,10010,10095]
     * data : [{"children":[{"id":10007,"title":"北京","type":1,"url":"/10007/list_1.json"},{"id":10006,"title":"中国","type":1,"url":"/10006/list_1.json"},{"id":10008,"title":"国际","type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"体育","type":1,"url":"/10010/list_1.json"},{"id":10091,"title":"生活","type":1,"url":"/10091/list_1.json"},{"id":10012,"title":"旅游","type":1,"url":"/10012/list_1.json"},{"id":10095,"title":"科技","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"军事","type":1,"url":"/10009/list_1.json"},{"id":10093,"title":"时尚","type":1,"url":"/10093/list_1.json"},{"id":10011,"title":"财经","type":1,"url":"/10011/list_1.json"},{"id":10094,"title":"育儿","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"汽车","type":1,"url":"/10105/list_1.json"}],"id":10000,"title":"新闻","type":1},{"url1":"/10007/list1_1.json","id":10002,"title":"专题","type":10,"url":"/10006/list_1.json"},{"id":10003,"title":"组图","type":2,"url":"/10008/list_1.json"},{"dayurl":"","id":10004,"weekurl":"","title":"互动","type":3,"excurl":""}]
     * retcode : 200
     */
    private List<Integer> extend;
    private List<NewsMenuData> data;
    private int retcode;

    public void setExtend(List<Integer> extend) {
        this.extend = extend;
    }

    public void setData(List<NewsMenuData> data) {
        this.data = data;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List<Integer> getExtend() {
        return extend;
    }

    public List<NewsMenuData> getData() {
        return data;
    }

    public int getRetcode() {
        return retcode;
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                ", extend=" + extend +
                '}';
    }
//侧边栏对象

    public class NewsMenuData {
        /**
         * children : [{"id":10007,"title":"北京","type":1,"url":"/10007/list_1.json"},{"id":10006,"title":"中国","type":1,"url":"/10006/list_1.json"},{"id":10008,"title":"国际","type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"体育","type":1,"url":"/10010/list_1.json"},{"id":10091,"title":"生活","type":1,"url":"/10091/list_1.json"},{"id":10012,"title":"旅游","type":1,"url":"/10012/list_1.json"},{"id":10095,"title":"科技","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"军事","type":1,"url":"/10009/list_1.json"},{"id":10093,"title":"时尚","type":1,"url":"/10093/list_1.json"},{"id":10011,"title":"财经","type":1,"url":"/10011/list_1.json"},{"id":10094,"title":"育儿","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"汽车","type":1,"url":"/10105/list_1.json"}]
         * id : 10000
         * title : 新闻
         * type : 1
         */
        private List<NewsTabeData> children;
        private int id;
        private String title;
        private int type;

        public void setChildren(List<NewsTabeData> children) {
            this.children = children;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<NewsTabeData> getChildren() {
            return children;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }


        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "children=" + children +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }

        //页签对象
        public  class NewsTabeData {
            /**
             * id : 10007
             * title : 北京
             * type : 1
             * url : /10007/list_1.json
             */
            private int id;
            private String title;
            private int type;
            private String url;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public int getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }


            @Override
            public String toString() {
                return "NewsTabeData{" +
                        "title='" + title + '\'' +
                        ", type=" + type +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }
}
