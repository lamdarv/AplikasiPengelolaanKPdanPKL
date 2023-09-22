package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.Criteria;
import com.jtk.ps.api.model.FeedbackAnswer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICompanyService {

    List<CompanyResponse> getAllCompanies(List<Integer> listAccount);

    List<CompanyResponse> getCompanies();

    void updateCompanyPic(Integer id);

    ListCompany getCompaniesAndPrerequisiteByProdi(Integer prodiId);

    Company createCompany(CompanyRequest company, String cookie);

    void updateCompany(Integer idCompany, CompanyRequest company);

    PrerequisiteCard getCardPrerequisiteByCompany(Integer idCompany, String cookie);

    List<QuotaResponse> getQuota();
    
    PrerequisiteResponse getPrerequisite(Integer idPrerequisite, String cookie);

    Boolean updatePrerequisiteByCompany(PrerequisiteRequest prerequisite, Integer idCompany);

    Boolean updatePrerequisiteByCommittee(Integer idPrerequisite, PrerequisiteRequest prerequisite, String cookie);

    Boolean markAsDoneByCommittee(Integer idPrerequisite);

    Boolean markAsDoneByCompany(Integer idCompany);

    List<CompanyName> getCompanyNameById();

    PrerequisiteCard getCardPrerequisiteByCommittee(String cookie, Integer idCompany);

    CompanyResponse getCompanyById(Integer id);

    List<CompanyIdName> getNameAndIdCompanies(Integer idProdi);

    List<CompanyRequirement> getCompanyRequirements(String cookie, Integer idProdi);
    
    void deletePrerequisiteCompetence(Integer id);
    
    void deletePrerequisiteJobscope(Integer id);
    
    List<Criteria> getCompanyCriteria();
    
    void createCriteria(CriteriaRequest criteriaRequest);
    
    void updateCriteria(Integer id, CriteriaRequest criteriaRequest);
    
    void deleteCriteria(Integer id);
    
    List<SubmissionResponse> getCompanySubmission();
    
    DetailSubmissionResponse getDetailCompanySubmission(Integer id);
    
    void createCompanySubmission(SubmissionRequest submissionRequest);
    
    void acceptCompanySubmission(Integer id, String cookie);
    
    void declineCompanySubmission(Integer id);
    
    EvaluationDetailResponse getEvaluationDetail(String cookie,Integer idParticipant, Integer numEval);
    
    EvaluationCardResponse getEvaluationCardByCompany(Integer idCompany, String cookie);
    
    EvaluationTableResponse getEvaluationTableByCommittee(Integer idProdi, String cookie);
    
    void createEvaluation(List<CreateEvaluationRequest> createEvaluationRequest);
    
    void updateEvaluation(Integer idParticipant, UpdateEvaluationRequest updateEvaluationRequest);
    
    void deleteAllEvaluation(Integer idProdi);

    List<PrerequisiteTableResponse> getPrerequisitesCompany(Integer idProdi, String cookie);


    void generatePdfEvaluation(Integer idParticipant, HttpServletResponse response, String cookie, Integer numEval);

    EvaluationFormResponse getEvaluationForm(Integer idParticipant, Integer idProdi, Integer numEvaluation, String cookie);

    Boolean changeStatus(String cookie, Integer idCompany);

    List<PrerequisiteRecapResponse> recapPrerequisite(String cookie, Integer idProdi);
    
    FeedbackCardResponse getFeedbackCardByCompany(Integer idCompany, String cookie);
    
    FeedbackTableResponse getFeedbackTableByCommittee(Integer idProdi, String cookie);
    
    List<UpdateFeedbackRequest> getFeedbackDetail(Integer idCompany, Integer idProdi);
    
    void createFeedback(List<CreateFeedbackRequest> createFeedbackRequest);
    
    void updateFeedback(Integer idCompany, Integer idProdi, List<UpdateFeedbackRequest> updateFeedbackRequest);
}
