package com.xw.baselib.utils;

public class Gps {

    private float Radius;
    private double wgLat;
    private double wgLon;

    public Gps(double wgLat, double wgLon) {
        this.wgLat = wgLat;
        this.wgLon = wgLon;
    }

    public Gps(float radius, double wgLat, double wgLon) {
        Radius = radius;
        this.wgLat = wgLat;
        this.wgLon = wgLon;
    }

    public float getRadius() {
        return Radius;
    }

    public void setRadius(float radius) {
        Radius = radius;
    }

    public double getWgLat() {
        return wgLat;
    }

    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }

    public double getWgLon() {
        return wgLon;
    }

    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }
}
