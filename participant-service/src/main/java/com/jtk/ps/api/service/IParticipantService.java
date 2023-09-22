package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.cv.*;
import com.jtk.ps.api.model.AbsenceRecap;
import com.jtk.ps.api.model.Course;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.ParticipantCompany;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IParticipantService {
    List<ParticipantIdName> getNameAndIdCompanies(Integer idProdi);

    List<Participant> getParticipantById(List<Integer> id);

    List<Participant> getParticipantByYear(Integer year, Integer prodiId);

    List<Participant> getParticipantByAccountId(List<Integer> accountId);

    CVParticipantResponse getCVParticipant(Integer idParticipant, String cookie);

    CVCommitteeResponse getCVParticipantByCommittee(Integer prodiId);

    CVGetResponse getCVDetail(Integer idCv, String cookie);

    Boolean updateCV(Integer idCv, CVUpdateRequest cvUpdateRequest, Integer idParticipant);

    Boolean markAsDoneCv(Integer idParticipant);

    Boolean markAsDoneInterest(Integer idParticipant);

    List<CVCompanyResponse> getCVParticipantByCompany(Integer idCompany, String cookie);

    CompanySelectionResponse getCompanySelection(String cookie, Integer idProdi);


    String getNameDomicile(Integer domicileId, String cookie);

    CompanySelectionCard getCompanySelectionCard(Integer idParticipant, String cookie);

    CompanySelectionDetail getCompanySelectionDetail(Integer idParticipant, String cookie);

    Boolean updateCompanySelection(Integer idParticipant, CompanySelectionUpdate companySelectionUpdate);

    List<CVInterestResponse> getCVInterestParticipant(String cookie, Integer prodiId);

    List<ParticipantCompany> getCompanySelectionMapping(Integer prodiId);

    void deleteCVCompetence(Integer id);

    void deleteCVJobscope(Integer id);

    List<CVRecap> recapCompetence(String cookie, Integer idProdi);

    List<CompanySelectionRecap> getCompanySelectionRecap(String cookie);

    List<ParticipantValueList> getParticipantValue(Integer idProdi);

    List<Course> getAllCourse(Integer idProdi);

    List<AbsenceRecap> getAllAbsence(Integer idProdi);

    void exportCV(String cookie, Integer idCv, HttpServletResponse response) throws IOException;
}
