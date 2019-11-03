package br.com.desafioDito.Data;

import io.swagger.annotations.ApiModel;

@ApiModel( value = "Evento", description = "Informações do evento gerado na navegação" )
public class EventDTO {
    
    private String eventName;
    
    private String eventId;
    
    private String url;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
}
