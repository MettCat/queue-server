package com.nettyserversql.entity;

/**
 * Created by Administrator on 2017/8/8.
 */
public class TestMode {

    private String cityname;

    private int stationnum;

    private String pollutantcode;

    private int pollutantnum;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getStationnum() {
        return stationnum;
    }

    public void setStationnum(int stationnum) {
        this.stationnum = stationnum;
    }

    public String getPollutantcode() {
        return pollutantcode;
    }

    public void setPollutantcode(String pollutantcode) {
        this.pollutantcode = pollutantcode;
    }

    public int getPollutantnum() {
        return pollutantnum;
    }

    public void setPollutantnum(int pollutantnum) {
        this.pollutantnum = pollutantnum;
    }

    @Override
    public String toString() {
        return "TestMode{" +
                "cityname='" + cityname + '\'' +
                ", stationnum=" + stationnum +
                ", pollutantcode='" + pollutantcode + '\'' +
                ", pollutantnum=" + pollutantnum +
                '}';
    }
}
