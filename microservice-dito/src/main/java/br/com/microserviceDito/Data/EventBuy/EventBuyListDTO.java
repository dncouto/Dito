package br.com.microserviceDito.Data.EventBuy;

public class EventBuyListDTO {

    private EventBuyDTO[] events;

    public EventBuyDTO[] getEvents() {
        return events;
    }

    public void setEvents(EventBuyDTO[] events) {
        this.events = events;
    }
}
