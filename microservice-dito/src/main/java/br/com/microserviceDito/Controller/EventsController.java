package br.com.microserviceDito.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.microserviceDito.Data.EventBrowserDTO;
import br.com.microserviceDito.Data.ResponseWrapper;
import br.com.microserviceDito.Data.Timeline.TimeLineDTO;
import br.com.microserviceDito.Model.EventBrowserModel;
import br.com.microserviceDito.Service.Intf.IEventBrowserService;
import br.com.microserviceDito.Service.Intf.IEventBuyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="EventsController", description="Controla os endpoints do Micro-serviço de Eventos")
@Controller
public class EventsController {

    @Autowired
    IEventBrowserService eventBrowserService;

    @Autowired
    IEventBuyService eventBuyService;
    

    @ApiOperation(value = "Gera um evento de navegação (assíncrono)", response = String.class)
	@ResponseBody
	@PostMapping("/generateEvent")
    public ResponseEntity<?> generateEvent(@RequestBody EventBrowserDTO objectToinsert) {
	    
        try {
            eventBrowserService.registerEvent(objectToinsert);
            
            return ResponseEntity.ok(true);
                    
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @ApiOperation(value = "Busca lista de eventos que contenha parte do texto", response = String.class)
    @ResponseBody
    @PostMapping(path = "/searchEvents", produces = "application/json; charset=utf-8")
    public ResponseWrapper searchEvents(@RequestBody EventBrowserDTO objectToSearch) {
        
        ResponseWrapper response = new ResponseWrapper();
        
        try {
            List<EventBrowserModel> eventsMatching = eventBrowserService.getMatchEventList(objectToSearch.getEventName());
            
            response.setResult("SUCCESS");
            response.setMsgSaida(eventsMatching);
                    
        } catch (Exception e) {
            response.setResult("ERROR");
            response.setError(new ArrayList<>());
            response.getError().add(e.getMessage());
        }

        return response;
    }
    
    @ApiOperation(value = "Monta a Timeline de compras do cliente", response = String.class)
    @ResponseBody
    @GetMapping(path = "/getTimeline", produces = "application/json; charset=utf-8")
    public TimeLineDTO getTimeline() {
        
        try {
            return eventBuyService.generateTimeline();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro na gera geração da Timeline: " + e.getMessage(), e);
        }
    }
}