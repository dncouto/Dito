package br.com.microserviceDito.Model;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Events")
public class EventBrowserModel {

	@Id
	@GeneratedValue
	private String id;
	  
	private List<Date> visits;
	
	private String eventId;
	  
	private String eventName;
	
	private String url;
	  
	  
	public EventBrowserModel() {
	}
   
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

    public List<Date> getVisits() {
        return visits;
    }

    public void setVisits(List<Date> visits) {
        this.visits = visits;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
	  
}
