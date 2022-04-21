package com.gumo.temps.model;

import javax.persistence.*;

@Entity
@Table(name = "temperature_data")
public class TemperatureData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "time")
    private long time;

    //combine temp in int and temp in double
    @Column(name = "temperature")
    private double temperature;

    public TemperatureData(long id, String deviceId, long time, double temperature) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
