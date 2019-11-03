package br.com.microserviceDito.Data.EventBuy;

import java.util.Date;

public class EventBuyDTO {
    
    private String event;
    
    private Date timestamp;
    
    private Double revenue;
    
    private KeyValueCustomDataDTO[] custom_data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public KeyValueCustomDataDTO[] getCustom_data() {
        return custom_data;
    }

    public void setCustom_data(KeyValueCustomDataDTO[] custom_data) {
        this.custom_data = custom_data;
    }    
}
