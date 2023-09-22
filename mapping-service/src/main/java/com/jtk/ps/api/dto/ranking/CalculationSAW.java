package com.jtk.ps.api.dto.ranking;

import lombok.Data;

@Data
public class CalculationSAW implements Comparable<CalculationSAW>{
    private Integer idParticipant;
    private Integer idCompany;
    private CriteriaMappingValue criteriaMapping;

    public CalculationSAW(){
        this.criteriaMapping = new CriteriaMappingValue();
    }

    public Double getFinalScore(){
        Double result = 0.0;
        if(this.criteriaMapping.getCriteria1() != null && !this.criteriaMapping.getCriteria1().isNaN()){
            result += this.criteriaMapping.getCriteria1();
        }
        if (this.criteriaMapping.getCriteria2() != null && !this.criteriaMapping.getCriteria2().isNaN()){
            result += this.criteriaMapping.getCriteria2();
        }
        if (this.criteriaMapping.getCriteria3() != null && !this.criteriaMapping.getCriteria3().isNaN()){
            result += this.criteriaMapping.getCriteria3();
        }
        if (this.criteriaMapping.getCriteria4() != null && !this.criteriaMapping.getCriteria4().isNaN()){
            result += this.criteriaMapping.getCriteria4();
        }
        if (this.criteriaMapping.getCriteria5() != null && !this.criteriaMapping.getCriteria5().isNaN()){
            result += this.criteriaMapping.getCriteria5();
        }
        if (this.criteriaMapping.getCriteria6() != null && !this.criteriaMapping.getCriteria6().isNaN()){
            result += this.criteriaMapping.getCriteria6();
        }
        if (this.criteriaMapping.getCriteria7() != null && !this.criteriaMapping.getCriteria7().isNaN()){
            result += this.criteriaMapping.getCriteria7();
        }
        if (this.criteriaMapping.getCriteria8() != null && !this.criteriaMapping.getCriteria8().isNaN()){
            result += this.criteriaMapping.getCriteria8();
        }
        if (this.criteriaMapping.getCriteria9() != null && !this.criteriaMapping.getCriteria9().isNaN()){
            result += this.criteriaMapping.getCriteria9();
        }
        return result;
    }


    @Override
    public int compareTo(CalculationSAW o) {
        return this.getFinalScore().compareTo(o.getFinalScore());
    }
}
