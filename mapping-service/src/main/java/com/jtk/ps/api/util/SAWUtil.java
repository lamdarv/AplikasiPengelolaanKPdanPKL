package com.jtk.ps.api.util;

import com.jtk.ps.api.dto.EPoint;
import com.jtk.ps.api.dto.ranking.CalculationSAW;

import java.util.Collections;
import java.util.List;

public class SAWUtil {
    private SAWUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static int getPoint(Integer id){
        if(id == EPoint.SANGAT_KURANG.getValue()){
            return 1;
        }else if (id == EPoint.KURANG.getValue()){
            return 2;
        }else if(id == EPoint.CUKUP.getValue()){
            return 3;
            }else if(id == EPoint.BAIK.getValue()){
            return 4;
        }else if(id == EPoint.SANGAT_BAIK.getValue()){
            return 5;
        }
        return 0;
    }

    public static Double normalization(Double value, Double minMax, Boolean isCost){
        Double result;
        if(Boolean.TRUE.equals(isCost)){
            result = minMax / value;
        }else{
            result = value / minMax;
        }
        if(Double.isNaN(result)){
            return 0.0;
        }
        return result;
    }

    public static List<CalculationSAW> getSortedAscCalculationSAW(List<CalculationSAW> calculationSAWList){
        Collections.sort(calculationSAWList);
        return calculationSAWList;
    }

    public static Double getMinMax(Double value, Double minMax, Boolean isCost){
        if(Boolean.TRUE.equals(isCost)){
            if(value == 0.0){
                return minMax;
            }

            if(value < minMax){
                return value;
            }else{
                return minMax;
            }
        }else{
            if(value > minMax){
                return value;
            }else{
                return minMax;
            }
        }
    }

}
