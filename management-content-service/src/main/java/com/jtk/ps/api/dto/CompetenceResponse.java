package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.Competence;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompetenceResponse {
    private Integer id;
    private String name;
    private Integer type;
    
    @JsonProperty("pic_name")
    private String picName;
}
