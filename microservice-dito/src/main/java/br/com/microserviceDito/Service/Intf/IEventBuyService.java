package br.com.microserviceDito.Service.Intf;

import br.com.microserviceDito.Data.Timeline.TimeLineDTO;

public interface IEventBuyService {

    TimeLineDTO generateTimeline() throws Exception;

}
