package br.com.microserviceDito.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.microserviceDito.Data.EventBrowserDTO;
import br.com.microserviceDito.Model.EventBrowserModel;
import br.com.microserviceDito.Repository.Intf.IEventBrowserRepository;
import br.com.microserviceDito.Service.Intf.IEventBrowserService;


@Service
public class EventBrowserService implements IEventBrowserService{

	@Autowired
	private IEventBrowserRepository eventBrowserRepository;
	
	
	@Override
	public List<EventBrowserModel> getMatchEventList(String contains) throws Exception {
	    Pageable top5 = PageRequest.of(0, 5);
	    return eventBrowserRepository.findByEventNameRegexQuery(contains, top5);
	}
	
	@Async
	public void registerEvent(EventBrowserDTO event) {

		try {
		    Optional<EventBrowserModel> existsEvent = eventBrowserRepository.findByEventId(event.getEventId());
		    
		    EventBrowserModel model;
		    if (existsEvent.isPresent()) {
		        model = existsEvent.get();
		        if (model.getVisits() == null)
		            model.setVisits(new ArrayList<Date>());
		    } else {
		        model = new EventBrowserModel();
		        model.setVisits(new ArrayList<Date>());
    			model.setEventId(event.getEventId());
		    }
	
		    model.getVisits().add(new Date());
            model.setEventName(event.getEventName());
            model.setUrl(event.getUrl());
            
            eventBrowserRepository.save(model);
			
			//System.out.println(new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.sss").format(new Date()));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
        
}
