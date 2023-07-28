package com.littlepay.code.challenge;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Trip {
    private Date startTime;
    private Date endTime;
    private String origin;
    private String destination;
    private double fee;
    private String companyId;
    private String busId;
    private String pan;
    private Status status;

    public enum Status {
        COMPLETED,
        INCOMPLETE,
        CANCELLED
    }

    public static final String DESTINATION_UNKNOWN = "Unknown";
    public static final String DESTINATION_NA = "N/A";

    public Trip() {}

    public Trip(final Date startTime, final Date endTime, final String origin, final String destination,
                final Double fee, final String companyId, final String busId, final String pan,
                final Status status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.origin = origin;
        this.destination = destination;
        this.fee = fee;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDuration() {
        return TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime());
    }
}
