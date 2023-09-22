package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.Participant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalParticipantCompany {
    private Company company;
    private List<Participant> participant = new ArrayList<>();
}
