package org.seraph.mvprxjavaretrofit.ui.module.main.model;

import java.io.Serializable;
import java.util.List;

public class ImageClientBean implements Serializable {

    public Integer code;

    public String message;

    public DataBean data;

    public class DataBean {

       // public String session_id;

       // public Integer angle;

        public List<ItemBean> items;

        @Override
        public String toString() {
            return "DataBean{" +
                   // "session_id='" + session_id + '\'' +
                  //  ", angle=" + angle +
                    ", items=" + items +
                    '}';
        }
    }


    public class ItemBean {

       // public ItemcoordBean itemcoord;

        public List<WordBean> words;

      //  public String itemstring;

        @Override
        public String toString() {
            return "ItemBean{" +
                  //  "itemcoord=" + itemcoord +
                    ", words=" + words +
                  //  ", itemstring='" + itemstring + '\'' +
                    '}';
        }
    }


    public class WordBean {
        public String character;

        public double confidence;

        @Override
        public String toString() {
            return "WordBean{" +
                    "character='" + character + '\'' +
                    ", confidence=" + confidence +
                    '}';
        }
    }

//    public class ItemcoordBean {
//        public Integer x;
//        public Integer y;
//        public Integer width;
//        public Integer height;
//
//        @Override
//        public String toString() {
//            return "ItemcoordBean{" +
//                    "x=" + x +
//                    ", y=" + y +
//                    ", width=" + width +
//                    ", height=" + height +
//                    '}';
//        }
//    }


    @Override
    public String toString() {
        return "ImageClientBean{" +
              //  "code=" + code +
               // ", message='" + message + '\'' +
                ", dataBean=" + data +
                '}';
    }
}
