package br.com.microserviceDito.Data.Timeline;

import java.util.List;

public class TimeLineDTO {

    private List<TimeLineItemDTO> timeline;

    public List<TimeLineItemDTO> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeLineItemDTO> timeline) {
        this.timeline = timeline;
    }

}
