package com.gumo.temps.model;

import javax.persistence.*;

@Entity
@Table(name = "temperature_data")
public class TemperatureData {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "category")
    private int category;

    @Column(name = "time")
    private long time;

    //2 empty columns reserved
    @Column(name = "reserved1")
    private String reserved1;

    @Column(name = "reserved2")
    private String reserved2;

    //temp in int
    @Column(name = "temperatureI")
    private int temperatureI;

    //temp in double
    @Column(name = "temperatureD")
    private double temperatureD;



}
