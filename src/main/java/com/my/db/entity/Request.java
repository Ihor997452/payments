package com.my.db.entity;

import com.my.db.constants.RequestStatus;
import com.my.db.constants.RequestType;
import java.sql.Timestamp;
import java.util.Objects;

public class Request extends Entity {
    private long requesterId;
    private RequestStatus requestStatus;
    private RequestType requestType;
    private Timestamp time;

    public long getRequesterId() {
        return this.requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String toString() {
        return "Request{id=" + this.id + ", requesterId=" + this.requesterId + ", requestStatus=" + this.requestStatus + ", requestType=" + this.requestType + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return getRequesterId() == request.getRequesterId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRequesterId());
    }
}
