package br.com.microserviceDito.Service.Intf;

import java.util.List;

import br.com.microserviceDito.Data.EventBrowserDTO;
import br.com.microserviceDito.Model.EventBrowserModel;

public interface IEventBrowserService {

    void registerEvent(EventBrowserDTO event);

    List<EventBrowserModel> getMatchEventList(String contains) throws Exception;
}