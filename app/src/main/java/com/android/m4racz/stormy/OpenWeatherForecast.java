package com.android.m4racz.stormy;

import java.util.ArrayList;

public class OpenWeatherForecast {

    /**
     * city : {"coord":{"lat":55.7522,"lon":37.6156},"country":"none","id":524901,"name":"Moscow"}
     * cnt : 40
     * cod : 200
     * list : [{"clouds":{"all":8},"dt":1485799200,"dt_txt":"2017-01-30 18:00:00","main":{"grnd_level":1023.48,"humidity":79,"pressure":1023.48,"sea_level":1045.39,"temp":261.45,"temp_kf":2.37,"temp_max":261.45,"temp_min":259.086},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"02n","id":800,"main":"Clear"}],"wind":{"deg":232.505,"speed":4.77}},{"clouds":{"all":32},"dt":1485810000,"dt_txt":"2017-01-30 21:00:00","main":{"grnd_level":1022.41,"humidity":76,"pressure":1022.41,"sea_level":1044.35,"temp":261.41,"temp_kf":1.78,"temp_max":261.41,"temp_min":259.638},"snow":{"3h":0.011},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"01n","id":800,"main":"Clear"}],"wind":{"deg":240.503,"speed":4.76}},{"clouds":{"all":68},"dt":1485820800,"dt_txt":"2017-01-31 00:00:00","main":{"grnd_level":1021.34,"humidity":84,"pressure":1021.34,"sea_level":1043.21,"temp":261.76,"temp_kf":1.18,"temp_max":261.76,"temp_min":260.571},"snow":{"3h":0.058},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":243,"speed":4.71}},{"clouds":{"all":68},"dt":1485831600,"dt_txt":"2017-01-31 03:00:00","main":{"grnd_level":1019.95,"humidity":82,"pressure":1019.95,"sea_level":1041.79,"temp":261.46,"temp_kf":0.59,"temp_max":261.46,"temp_min":260.865},"snow":{"3h":0.05225},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":244.5,"speed":4.46}},{"clouds":{"all":80},"dt":1485842400,"dt_txt":"2017-01-31 06:00:00","main":{"grnd_level":1018.96,"humidity":81,"pressure":1018.96,"sea_level":1040.84,"temp":260.981,"temp_kf":0,"temp_max":260.981,"temp_min":260.981},"snow":{"3h":0.19625},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":245.005,"speed":4.21}},{"clouds":{"all":88},"dt":1485853200,"dt_txt":"2017-01-31 09:00:00","main":{"grnd_level":1018.1,"humidity":91,"pressure":1018.1,"sea_level":1039.77,"temp":262.308,"temp_kf":0,"temp_max":262.308,"temp_min":262.308},"snow":{"3h":0.535},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":249.006,"speed":4.1}},{"clouds":{"all":68},"dt":1485864000,"dt_txt":"2017-01-31 12:00:00","main":{"grnd_level":1016.86,"humidity":87,"pressure":1016.86,"sea_level":1038.4,"temp":263.76,"temp_kf":0,"temp_max":263.76,"temp_min":263.76},"snow":{"3h":0.21},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":254.5,"speed":3.87}},{"clouds":{"all":76},"dt":1485874800,"dt_txt":"2017-01-31 15:00:00","main":{"grnd_level":1016.19,"humidity":89,"pressure":1016.19,"sea_level":1037.77,"temp":264.182,"temp_kf":0,"temp_max":264.182,"temp_min":264.182},"snow":{"3h":0.1375},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":257.001,"speed":3.67}},{"clouds":{"all":88},"dt":1485885600,"dt_txt":"2017-01-31 18:00:00","main":{"grnd_level":1015.32,"humidity":86,"pressure":1015.32,"sea_level":1036.94,"temp":264.67,"temp_kf":0,"temp_max":264.67,"temp_min":264.67},"snow":{"3h":0.1425},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":262.503,"speed":3.61}},{"clouds":{"all":80},"dt":1485896400,"dt_txt":"2017-01-31 21:00:00","main":{"grnd_level":1014.27,"humidity":90,"pressure":1014.27,"sea_level":1035.76,"temp":265.436,"temp_kf":0,"temp_max":265.436,"temp_min":265.436},"snow":{"3h":0.1625},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":266.5,"speed":3.67}},{"clouds":{"all":80},"dt":1485907200,"dt_txt":"2017-02-01 00:00:00","main":{"grnd_level":1013.1,"humidity":90,"pressure":1013.1,"sea_level":1034.62,"temp":266.104,"temp_kf":0,"temp_max":266.104,"temp_min":266.104},"snow":{"3h":0.1025},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":269.004,"speed":3.81}},{"clouds":{"all":76},"dt":1485918000,"dt_txt":"2017-02-01 03:00:00","main":{"grnd_level":1011.96,"humidity":89,"pressure":1011.96,"sea_level":1033.47,"temp":266.904,"temp_kf":0,"temp_max":266.904,"temp_min":266.904},"snow":{"3h":0.12},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":274.002,"speed":4.26}},{"clouds":{"all":76},"dt":1485928800,"dt_txt":"2017-02-01 06:00:00","main":{"grnd_level":1011.23,"humidity":89,"pressure":1011.23,"sea_level":1032.62,"temp":268.102,"temp_kf":0,"temp_max":268.102,"temp_min":268.102},"snow":{"3h":0.13},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":283.501,"speed":4.4}},{"clouds":{"all":64},"dt":1485939600,"dt_txt":"2017-02-01 09:00:00","main":{"grnd_level":1010.85,"humidity":92,"pressure":1010.85,"sea_level":1032.1,"temp":270.269,"temp_kf":0,"temp_max":270.269,"temp_min":270.269},"snow":{"3h":0.1875},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":297.5,"speed":4.53}},{"clouds":{"all":76},"dt":1485950400,"dt_txt":"2017-02-01 12:00:00","main":{"grnd_level":1010.49,"humidity":89,"pressure":1010.49,"sea_level":1031.65,"temp":270.585,"temp_kf":0,"temp_max":270.585,"temp_min":270.585},"snow":{"3h":0.065},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":302.004,"speed":4.31}},{"clouds":{"all":68},"dt":1485961200,"dt_txt":"2017-02-01 15:00:00","main":{"grnd_level":1010.22,"humidity":89,"pressure":1010.22,"sea_level":1031.49,"temp":269.661,"temp_kf":0,"temp_max":269.661,"temp_min":269.661},"snow":{"3h":0.0825},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":296.5,"speed":4.91}},{"clouds":{"all":80},"dt":1485972000,"dt_txt":"2017-02-01 18:00:00","main":{"grnd_level":1009.95,"humidity":89,"pressure":1009.95,"sea_level":1031.3,"temp":269.155,"temp_kf":0,"temp_max":269.155,"temp_min":269.155},"snow":{"3h":0.11},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":310.501,"speed":5.7}},{"clouds":{"all":68},"dt":1485982800,"dt_txt":"2017-02-01 21:00:00","main":{"grnd_level":1011.21,"humidity":89,"pressure":1011.21,"sea_level":1032.49,"temp":268.056,"temp_kf":0,"temp_max":268.056,"temp_min":268.056},"snow":{"3h":0.225},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":333,"speed":5.56}},{"clouds":{"all":8},"dt":1485993600,"dt_txt":"2017-02-02 00:00:00","main":{"grnd_level":1013.79,"humidity":83,"pressure":1013.79,"sea_level":1035.06,"temp":265.803,"temp_kf":0,"temp_max":265.803,"temp_min":265.803},"snow":{"3h":0.03},"sys":{"pod":"n"},"weather":[{"description":"light snow","icon":"13n","id":600,"main":"Snow"}],"wind":{"deg":355.004,"speed":4.8}},{"clouds":{"all":0},"dt":1486004400,"dt_txt":"2017-02-02 03:00:00","main":{"grnd_level":1015.66,"humidity":84,"pressure":1015.66,"sea_level":1037.16,"temp":263.381,"temp_kf":0,"temp_max":263.381,"temp_min":263.381},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"01n","id":800,"main":"Clear"}],"wind":{"deg":348.503,"speed":4.2}},{"clouds":{"all":0},"dt":1486015200,"dt_txt":"2017-02-02 06:00:00","main":{"grnd_level":1017.63,"humidity":76,"pressure":1017.63,"sea_level":1039.22,"temp":261.85,"temp_kf":0,"temp_max":261.85,"temp_min":261.85},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}],"wind":{"deg":345.502,"speed":3.81}},{"clouds":{"all":0},"dt":1486026000,"dt_txt":"2017-02-02 09:00:00","main":{"grnd_level":1019.32,"humidity":84,"pressure":1019.32,"sea_level":1040.84,"temp":263.455,"temp_kf":0,"temp_max":263.455,"temp_min":263.455},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}],"wind":{"deg":344.004,"speed":3.06}},{"clouds":{"all":0},"dt":1486036800,"dt_txt":"2017-02-02 12:00:00","main":{"grnd_level":1020.41,"humidity":85,"pressure":1020.41,"sea_level":1041.88,"temp":264.015,"temp_kf":0,"temp_max":264.015,"temp_min":264.015},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}],"wind":{"deg":334.501,"speed":2.52}},{"clouds":{"all":0},"dt":1486047600,"dt_txt":"2017-02-02 15:00:00","main":{"grnd_level":1021.52,"humidity":76,"pressure":1021.52,"sea_level":1043.21,"temp":259.684,"temp_kf":0,"temp_max":259.684,"temp_min":259.684},"snow":{"3h":0.0024999999999999},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"01n","id":800,"main":"Clear"}],"wind":{"deg":320.501,"speed":2.48}},{"clouds":{"all":24},"dt":1486058400,"dt_txt":"2017-02-02 18:00:00","main":{"grnd_level":1022.09,"humidity":66,"pressure":1022.09,"sea_level":1044.09,"temp":255.188,"temp_kf":0,"temp_max":255.188,"temp_min":255.188},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"few clouds","icon":"02n","id":801,"main":"Clouds"}],"wind":{"deg":283.003,"speed":1.23}},{"clouds":{"all":48},"dt":1486069200,"dt_txt":"2017-02-02 21:00:00","main":{"grnd_level":1022.03,"humidity":64,"pressure":1022.03,"sea_level":1044.12,"temp":255.594,"temp_kf":0,"temp_max":255.594,"temp_min":255.594},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"scattered clouds","icon":"03n","id":802,"main":"Clouds"}],"wind":{"deg":244.502,"speed":1.22}},{"clouds":{"all":44},"dt":1486080000,"dt_txt":"2017-02-03 00:00:00","main":{"grnd_level":1021.8,"humidity":66,"pressure":1021.8,"sea_level":1043.77,"temp":256.96,"temp_kf":0,"temp_max":256.96,"temp_min":256.96},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"scattered clouds","icon":"03n","id":802,"main":"Clouds"}],"wind":{"deg":237.506,"speed":1.23}},{"clouds":{"all":80},"dt":1486090800,"dt_txt":"2017-02-03 03:00:00","main":{"grnd_level":1020.97,"humidity":77,"pressure":1020.97,"sea_level":1042.99,"temp":258.109,"temp_kf":0,"temp_max":258.109,"temp_min":258.109},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"broken clouds","icon":"04n","id":803,"main":"Clouds"}],"wind":{"deg":234.502,"speed":1.21}},{"clouds":{"all":68},"dt":1486101600,"dt_txt":"2017-02-03 06:00:00","main":{"grnd_level":1020.56,"humidity":76,"pressure":1020.56,"sea_level":1042.53,"temp":259.533,"temp_kf":0,"temp_max":259.533,"temp_min":259.533},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"broken clouds","icon":"04d","id":803,"main":"Clouds"}],"wind":{"deg":229.509,"speed":1.21}},{"clouds":{"all":56},"dt":1486112400,"dt_txt":"2017-02-03 09:00:00","main":{"grnd_level":1020.46,"humidity":84,"pressure":1020.46,"sea_level":1042.15,"temp":263.438,"temp_kf":0,"temp_max":263.438,"temp_min":263.438},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"broken clouds","icon":"04d","id":803,"main":"Clouds"}],"wind":{"deg":242.503,"speed":1.51}},{"clouds":{"all":56},"dt":1486123200,"dt_txt":"2017-02-03 12:00:00","main":{"grnd_level":1019.58,"humidity":89,"pressure":1019.58,"sea_level":1041.24,"temp":264.228,"temp_kf":0,"temp_max":264.228,"temp_min":264.228},"snow":{},"sys":{"pod":"d"},"weather":[{"description":"broken clouds","icon":"04d","id":803,"main":"Clouds"}],"wind":{"deg":242.503,"speed":1.58}},{"clouds":{"all":76},"dt":1486134000,"dt_txt":"2017-02-03 15:00:00","main":{"grnd_level":1019.63,"humidity":80,"pressure":1019.63,"sea_level":1041.42,"temp":261.153,"temp_kf":0,"temp_max":261.153,"temp_min":261.153},"snow":{"3h":0.0049999999999999},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"01n","id":800,"main":"Clear"}],"wind":{"deg":198.501,"speed":1.21}},{"clouds":{"all":64},"dt":1486144800,"dt_txt":"2017-02-03 18:00:00","main":{"grnd_level":1020.18,"humidity":73,"pressure":1020.18,"sea_level":1042.03,"temp":258.818,"temp_kf":0,"temp_max":258.818,"temp_min":258.818},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"broken clouds","icon":"04n","id":803,"main":"Clouds"}],"wind":{"deg":209.002,"speed":1.21}},{"clouds":{"all":44},"dt":1486155600,"dt_txt":"2017-02-03 21:00:00","main":{"grnd_level":1020.43,"humidity":65,"pressure":1020.43,"sea_level":1042.38,"temp":257.218,"temp_kf":0,"temp_max":257.218,"temp_min":257.218},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"scattered clouds","icon":"03n","id":802,"main":"Clouds"}],"wind":{"deg":194.501,"speed":1.17}},{"clouds":{"all":56},"dt":1486166400,"dt_txt":"2017-02-04 00:00:00","main":{"grnd_level":1020.57,"humidity":73,"pressure":1020.57,"sea_level":1042.75,"temp":255.782,"temp_kf":0,"temp_max":255.782,"temp_min":255.782},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"broken clouds","icon":"04n","id":803,"main":"Clouds"}],"wind":{"deg":175.001,"speed":1.21}},{"clouds":{"all":44},"dt":1486177200,"dt_txt":"2017-02-04 03:00:00","main":{"grnd_level":1020.99,"humidity":68,"pressure":1020.99,"sea_level":1043.11,"temp":254.819,"temp_kf":0,"temp_max":254.819,"temp_min":254.819},"snow":{"3h":0.0049999999999999},"sys":{"pod":"n"},"weather":[{"description":"clear sky","icon":"01n","id":800,"main":"Clear"}],"wind":{"deg":122.001,"speed":1.22}},{"clouds":{"all":68},"dt":1486188000,"dt_txt":"2017-02-04 06:00:00","main":{"grnd_level":1021.31,"humidity":63,"pressure":1021.31,"sea_level":1043.48,"temp":257.488,"temp_kf":0,"temp_max":257.488,"temp_min":257.488},"snow":{"3h":0.04},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":155.501,"speed":2.13}},{"clouds":{"all":68},"dt":1486198800,"dt_txt":"2017-02-04 09:00:00","main":{"grnd_level":1021.81,"humidity":90,"pressure":1021.81,"sea_level":1043.67,"temp":259.827,"temp_kf":0,"temp_max":259.827,"temp_min":259.827},"snow":{"3h":0.03},"sys":{"pod":"d"},"weather":[{"description":"light snow","icon":"13d","id":600,"main":"Snow"}],"wind":{"deg":170.005,"speed":2.07}},{"clouds":{"all":76},"dt":1486209600,"dt_txt":"2017-02-04 12:00:00","main":{"grnd_level":1021.31,"humidity":86,"pressure":1021.31,"sea_level":1043.05,"temp":261.256,"temp_kf":0,"temp_max":261.256,"temp_min":261.256},"snow":{"3h":0.0049999999999999},"sys":{"pod":"d"},"weather":[{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}],"wind":{"deg":175.001,"speed":2.32}},{"clouds":{"all":56},"dt":1486220400,"dt_txt":"2017-02-04 15:00:00","main":{"grnd_level":1021,"humidity":86,"pressure":1021,"sea_level":1042.96,"temp":260.26,"temp_kf":0,"temp_max":260.26,"temp_min":260.26},"snow":{},"sys":{"pod":"n"},"weather":[{"description":"broken clouds","icon":"04n","id":803,"main":"Clouds"}],"wind":{"deg":180.501,"speed":2.47}}]
     * message : 0.0036
     */

    private CityBean city;
    private int cnt;
    private String cod;
    private double message;
    private java.util.List list;

    public static class CityBean {
        /**
         * coord : {"lat":55.7522,"lon":37.6156}
         * country : none
         * id : 524901
         * name : Moscow
         */

        private CoordBean coord;
        private String country;
        private int id;
        private String name;

        public CoordBean getCoord() {
            return coord;
        }

        public void setCoord(CoordBean coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class CoordBean {
            /**
             * lat : 55.7522
             * lon : 37.6156
             */

            private double lat;
            private double lon;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
}
