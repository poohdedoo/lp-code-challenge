package com.littlepay.code.challenge;

import java.util.Date;

public class Tap {
    private int id;
    private Date dateTime;
    private Type tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;

    public enum Type {
        ON,
        OFF
    }

    public Tap() {}

    public Tap(final int id, final Date dateTime, final Type tapType, final String stopId,
               final String companyId, final String busId, final String pan) {
        this.id = id;
        this.dateTime = dateTime;
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Type getTapType() {
        return tapType;
    }

    public void setTapType(Type tapType) {
        this.tapType = tapType;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
