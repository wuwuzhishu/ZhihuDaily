package sptpc.com.zhihudaily.model;

import android.graphics.Bitmap;

import java.util.List;

public class News {

    /**
     * date : 20190319
     * stories : [{"title":"瞧瞧，这些奇葩的进化啊","ga_prefix":"031909","images":["https://pic4.zhimg.com/v2-9ea7f914da080936b48f1dfd7d435ed7.jpg"],"multipic":true,"type":0,"id":9708910}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * title : 瞧瞧，这些奇葩的进化啊
         * ga_prefix : 031909
         * images : ["https://pic4.zhimg.com/v2-9ea7f914da080936b48f1dfd7d435ed7.jpg"]
         * multipic : true
         * type : 0
         * id : 9708910
         */

        private String title;
        private String ga_prefix;
        private boolean multipic;
        private int type;
        private int id;
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

    }
}
