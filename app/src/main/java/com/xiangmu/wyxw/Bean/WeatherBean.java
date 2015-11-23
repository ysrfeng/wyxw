package com.xiangmu.wyxw.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class WeatherBean implements Serializable{

    /**
     * date : 2015-11-12
     * error : 0
     * results : [{"weather_data":[{"date":"周四 11月12日 (实时：11℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"阴转小雨","temperature":"11 ~ 8℃","wind":"微风"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","temperature":"12 ~ 8℃","wind":"微风"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"阴转多云","temperature":"15 ~ 9℃","wind":"微风"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"阴转小雨","temperature":"12 ~ 8℃","wind":"微风"}],"pm25":"193","index":[{"des":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","zs":"较冷","title":"穿衣","tipt":"穿衣指数"},{"des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","zs":"不宜","title":"洗车","tipt":"洗车指数"},{"des":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。","zs":"适宜","title":"旅游","tipt":"旅游指数"},{"des":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","zs":"较易发","title":"感冒","tipt":"感冒指数"},{"des":"阴天，较适宜进行各种户内外运动。","zs":"较适宜","title":"运动","tipt":"运动指数"},{"des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","zs":"最弱","title":"紫外线强度","tipt":"紫外线强度指数"}],"currentCity":"郑州"}]
     * status : success
     */
    public String date;
    public int error;
    public List<ResultsEntity> results;
    public String status;

    public class ResultsEntity implements Serializable{
        /**
         * weather_data : [{"date":"周四 11月12日 (实时：11℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"阴转小雨","temperature":"11 ~ 8℃","wind":"微风"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","temperature":"12 ~ 8℃","wind":"微风"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"阴转多云","temperature":"15 ~ 9℃","wind":"微风"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"阴转小雨","temperature":"12 ~ 8℃","wind":"微风"}]
         * pm25 : 193
         * index : [{"des":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","zs":"较冷","title":"穿衣","tipt":"穿衣指数"},{"des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","zs":"不宜","title":"洗车","tipt":"洗车指数"},{"des":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。","zs":"适宜","title":"旅游","tipt":"旅游指数"},{"des":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","zs":"较易发","title":"感冒","tipt":"感冒指数"},{"des":"阴天，较适宜进行各种户内外运动。","zs":"较适宜","title":"运动","tipt":"运动指数"},{"des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","zs":"最弱","title":"紫外线强度","tipt":"紫外线强度指数"}]
         * currentCity : 郑州
         */
        public List<Weather_dataEntity> weather_data;
        public String pm25;
        public List<IndexEntity> index;
        public String currentCity;
        
        public class Weather_dataEntity implements Serializable{
            /**
             * date : 周四 11月12日 (实时：11℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/yin.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/xiaoyu.png
             * weather : 阴转小雨
             * temperature : 11 ~ 8℃
             * wind : 微风
             */
            public String date;
            public String dayPictureUrl;
            public String nightPictureUrl;
            public String weather;
            public String temperature;
            public String wind;
        }

        public class IndexEntity implements Serializable{
            /**
             * des : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
             * zs : 较冷
             * title : 穿衣
             * tipt : 穿衣指数
             */
            public String des;
            public String zs;
            public String title;
            public String tipt;
        }
    }
}
