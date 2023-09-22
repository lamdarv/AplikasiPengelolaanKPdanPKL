package com.jtk.ps.api.dto.ranking;

import lombok.Data;

@Data
public class CriteriaMappingValue {
    private Double criteria1;
    private Double criteria2;
    private Double criteria3;
    private Double criteria4;
    private Double criteria5;
    private Double criteria6;
    private Double criteria7;
    private Double criteria8;
    private Double criteria9;

    public CriteriaMappingValue(){
        this.criteria1 = 0.0;
        this.criteria2 = 0.0;
        this.criteria3 = 0.0;
        this.criteria4 = 0.0;
        this.criteria5 = 0.0;
        this.criteria6 = 0.0;
        this.criteria7 = 0.0;
        this.criteria8 = 0.0;
        this.criteria9 = 0.0;
    }
}
