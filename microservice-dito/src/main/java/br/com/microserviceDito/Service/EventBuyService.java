package br.com.microserviceDito.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.microserviceDito.Data.EventBuy.EventBuyDTO;
import br.com.microserviceDito.Data.EventBuy.EventBuyListDTO;
import br.com.microserviceDito.Data.EventBuy.KeyValueCustomDataDTO;
import br.com.microserviceDito.Data.Timeline.ProductDTO;
import br.com.microserviceDito.Data.Timeline.TimeLineDTO;
import br.com.microserviceDito.Data.Timeline.TimeLineItemDTO;
import br.com.microserviceDito.Service.Intf.IEventBuyService;


@Service
public class EventBuyService implements IEventBuyService{
	
	@Override
	public TimeLineDTO generateTimeline() throws Exception {
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EventBuyListDTO> eventBuyListDTO = restTemplate.getForEntity("https://storage.googleapis.com/dito-questions/events.json", EventBuyListDTO.class);
        EventBuyDTO[] eventBuyList = eventBuyListDTO.getBody().getEvents();
        
        if (eventBuyList == null || eventBuyList.length == 0)
            throw new Exception("Nenhum evento de compra encontrado para montar a timeline");
        
        Map<Long, TimeLineItemDTO> transactionResult = new HashMap<Long, TimeLineItemDTO>();
        
        List<EventBuyDTO> genericEventList; 
        
        //Extrai a lista de evento "comprou"
        genericEventList = Arrays.stream(eventBuyList)
                .filter(eventBuy -> eventBuy.getEvent().equals("comprou"))
                .collect(Collectors.toList());
        for (EventBuyDTO eventBuyDTO : genericEventList) {
            TimeLineItemDTO timelineItem = new TimeLineItemDTO();
            timelineItem.setProducts(new ArrayList<>());
            timelineItem.setTimestamp(eventBuyDTO.getTimestamp());
            timelineItem.setRevenue(eventBuyDTO.getRevenue());
            for (KeyValueCustomDataDTO detail : eventBuyDTO.getCustom_data()) {
                if (detail.getKey().equals("transaction_id"))
                    timelineItem.setTransaction_id(Long.valueOf((String)detail.getValue()));
                else if (detail.getKey().equals("store_name"))
                    timelineItem.setStore_name((String)detail.getValue());
            }
            transactionResult.put(timelineItem.getTransaction_id(), timelineItem);
        }
        
        //Extrai a lista de evento "comprou-produto", e adiciona para os detalhes da mesma transação
        genericEventList = Arrays.stream(eventBuyList)
                .filter(eventBuy -> eventBuy.getEvent().equals("comprou-produto"))
                .collect(Collectors.toList());
        for (EventBuyDTO eventBuyDTO : genericEventList) {
            ProductDTO product = new ProductDTO();
            for (KeyValueCustomDataDTO detail : eventBuyDTO.getCustom_data()) {
                if (detail.getKey().equals("transaction_id"))
                    product.setTransaction_id(Long.valueOf((String)detail.getValue()));
                else if (detail.getKey().equals("product_name"))
                    product.setName((String)detail.getValue());
                else if (detail.getKey().equals("product_price"))
                    product.setPrice(Double.parseDouble(detail.getValue().toString()));
            }
            if (transactionResult.containsKey(product.getTransaction_id()))
                transactionResult.get(product.getTransaction_id()).getProducts().add(product);
        }
        
        TimeLineDTO timeline = new TimeLineDTO();
        //Ordena pela data decrescente 
        timeline.setTimeline(new ArrayList<TimeLineItemDTO>(transactionResult.values()));
        timeline.getTimeline().sort(Comparator.comparing(TimeLineItemDTO::getTimestampDate).reversed());
        
        return timeline;
	}
	        
}
