package br.com.microserviceDito.Repository.Intf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.microserviceDito.Model.EventBrowserModel;

/**
 *
 * @author Daniel Couto
 */

@Repository
public interface IEventBrowserRepository extends MongoRepository<EventBrowserModel, Long> {
	
    @Query("{eventName : {$regex : ?0 , '$options' : 'i'}}")
    public List<EventBrowserModel> findByEventNameRegexQuery(String partEventName, Pageable pageable);
    
    public Optional<EventBrowserModel> findByEventId(String eventId);
}
