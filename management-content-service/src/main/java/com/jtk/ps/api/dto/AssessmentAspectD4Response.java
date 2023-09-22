package com.jtk.ps.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.jtk.ps.api.model.AssessmentAspect;
import java.util.List;

@Data
@NoArgsConstructor
public class AssessmentAspectD4Response {
    private List<AssessmentAspect> evaluation1;
    
    private List<AssessmentAspect> evaluation2;
    
    private List<AssessmentAspect> evaluation3;
}
