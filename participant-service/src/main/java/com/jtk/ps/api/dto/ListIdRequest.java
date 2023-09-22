package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListIdRequest {
    @JsonProperty(value = "id")
    private List<Integer> id;
}
