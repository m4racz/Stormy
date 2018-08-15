package com.android.m4racz.stormy;

import java.util.List;

public class OpenWeatherCurrentWeather {

    @Override
    public String toString() {
        return "OpenWeatherCurrentWeather{" +
                "base='" + base + '\'' +
                ", clouds=" + clouds +
                ", cod=" + cod +
                ", coord=" + coord +
                ", dt=" + dt +
                ", id=" + id +
                ", main=" + main +
                ", name='" + name + '\'' +
                ", sys=" + sys +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", weather=" + weather +
                '}';
    }

    /**
     * base : stations
     * clouds : {"all":0}
     * cod : 200
     * coord : {"lat":50.09,"lon":14.42}
     * dt : 1533749400
     * id : 3067696
     * main : {"humidity":64,"pressure":1014,"temp":297.15,"temp_max":297.15,"temp_min":297.15}
     * name : Prague
     * sys : {"country":"CZ","id":5898,"message":0.0043,"sunrise":1533699732,"sunset":1533753153,"type":1}
     * visibility : 10000
     * weather : [{"description":"clear sky","icon":"01d","id":800,"main":"Clear"}]
     * wind : {"deg":140,"speed":3.6}
     */

    private String base;
    private CloudsBean clouds;
    private int cod;
    private CoordBean coord;
    private int dt;
    private int id;
    private MainBean main;
    private String name;
    private SysBean sys;
    private int visibility;
    private WindBean wind;
    private List<WeatherBean> weather;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public CloudsBean getClouds() {
        return clouds;
    }

    public void setClouds(CloudsBean clouds) {
        this.clouds = clouds;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public CoordBean getCoord() {
        return coord;
    }

    public void setCoord(CoordBean coord) {
        this.coord = coord;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MainBean getMain() {
        return main;
    }

    public void setMain(MainBean main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysBean getSys() {
        return sys;
    }

    public void setSys(SysBean sys) {
        this.sys = sys;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public static class CloudsBean {
        /**
         * all : 0
         */

        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class CoordBean {
        /**
         * lat : 50.09
         * lon : 14.42
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

    public static class MainBean {
        /**
         * humidity : 64
         * pressure : 1014
         * temp : 297.15
         * temp_max : 297.15
         * temp_min : 297.15
         */

        private int humidity;
        private int pressure;
        private double temp;
        private double temp_max;
        private double temp_min;

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }
    }

    public static class SysBean {
        /**
         * country : CZ
         * id : 5898
         * message : 0.0043
         * sunrise : 1533699732
         * sunset : 1533753153
         * type : 1
         */

        private String country;
        private int id;
        private double message;
        private int sunrise;
        private int sunset;
        private int type;

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

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class WindBean {
        /**
         * deg : 140
         * speed : 3.6
         */

        private int deg;
        private double speed;

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    public static class WeatherBean {
        /**
         * description : clear sky
         * icon : 01d
         * id : 800
         * main : Clear
         */

        private String description;
        private String icon;
        private int id;
        private String main;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}