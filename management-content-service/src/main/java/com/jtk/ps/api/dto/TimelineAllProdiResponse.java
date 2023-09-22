package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TimelineAllProdiResponse {
    @JsonProperty("timeline_d3")
    private List<TimelineRequest> timelineD3;

    @JsonProperty("timeline_d4")
    private List<TimelineRequest> timelineD4;

    public TimelineAllProdiResponse(){
        this.timelineD3 = new ArrayList<>();
        this.timelineD4 = new ArrayList<>();
    }
}
