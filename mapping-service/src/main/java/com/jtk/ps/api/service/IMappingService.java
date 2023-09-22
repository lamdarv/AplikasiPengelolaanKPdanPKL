package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.FinalMappingRequest;
import com.jtk.ps.api.dto.ParticipantByCompany;
import com.jtk.ps.api.dto.ranking.RankingAndDateResponse;
import com.jtk.ps.api.dto.ranking.RankingResponse;

import javax.servlet.http.HttpServletResponse;
import com.jtk.ps.api.dto.FinalMappingResponse;
import com.jtk.ps.api.dto.ParticipantFinalMappingResponse;
import com.jtk.ps.api.model.CriteriaMapping;
import java.util.List;

public interface IMappingService {
    FinalMappingResponse getFinalMapping(Integer idRole, Integer idProdi, String cookie);
    
    List<ParticipantFinalMappingResponse> getParticipantFinalMapping(Integer idCompany);
    
    void createFinalMapping(FinalMappingRequest finalMappingRequest, Integer idProdi);
    
    void deleteFinalMapping(Integer idParticipant, Integer idProdi);
    
    void submitFinalMapping(Integer idProdi, String cookie);
    
    void submitPublishMapping(Integer idProdi, String cookie);

    Boolean generateRank(String cookie, Integer idProdi);

    List<CriteriaMapping> getCriteria();

    RankingAndDateResponse getRanking(String cookie, Integer idProdi);

    void exportMapping(String cookie,HttpServletResponse response, Integer idProdi);

    void updateCriteria(List<CriteriaMapping> criteriaRequest);
    
    Integer getIsFinalMapping(Integer idProdi);

    void deleteCompany(Integer idCompany);

    void deleteCompanyByProdi(Integer idCompany, Integer idProdi);

    List<ParticipantByCompany> getParticipantByCompany(Integer idCompany, String cookie, Integer idProdi);
}
