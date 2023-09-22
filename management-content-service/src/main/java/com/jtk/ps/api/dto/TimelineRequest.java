package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.TimelineSetting;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"dateStart", "dateEnd"})
public class TimelineRequest {
    private Integer id;

    private String name;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private String description;

    @JsonProperty("prodi_id")
    private Integer prodiId;

    public Date getDateStart() {
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getDateEnd() {
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<TimelineRequest> getTimeline(List<TimelineSetting> timelineSetting){
        String pattern = "yyyy/MM/dd";
        DateFormat dateFormat = new SimpleDateFormat(pattern);

        List<TimelineRequest> timelineList = new java.util.ArrayList<>();
        for (TimelineSetting timeline : timelineSetting) {
            TimelineRequest tl = new TimelineRequest();
            tl.id = timeline.getId();
            tl.name = timeline.getName();
            tl.startDate = dateFormat.format(timeline.getStartDate());
            tl.endDate = dateFormat.format(timeline.getEndDate());
            tl.description = timeline.getDescription();
            tl.prodiId = timeline.getProdiId();
            timelineList.add(tl);
        }
        return timelineList;
    }
}
