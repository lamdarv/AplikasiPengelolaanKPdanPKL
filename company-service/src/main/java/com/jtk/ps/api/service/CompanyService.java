package com.jtk.ps.api.service;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.line.LineStyle;
import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.Criteria;
import com.jtk.ps.api.model.Prerequisite;
import com.jtk.ps.api.model.PrerequisiteCompetence;
import com.jtk.ps.api.model.PrerequisiteJobscope;
import com.jtk.ps.api.model.Submission;
import com.jtk.ps.api.model.SubmissionCriteria;
import com.jtk.ps.api.model.Advantage;
import com.jtk.ps.api.model.EProdi;
import com.jtk.ps.api.model.Project;
import com.jtk.ps.api.model.Proposer;
import com.jtk.ps.api.model.Evaluation;
import com.jtk.ps.api.model.FeedbackAnswer;
import com.jtk.ps.api.model.Valuation;
import com.jtk.ps.api.model.Feedback;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.DateUtil;
import com.jtk.ps.api.util.PDFUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONArray;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Scheduled;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    @Lazy
    private CompanyRepository companyRepository;

    @Autowired
    @Lazy
    private PrerequisiteRepository prerequisiteRepository;

    @Autowired
    @Lazy
    private ProposerRepository proposerRepository;

    @Autowired
    @Lazy
    private CriteriaRepository criteriaRepository;

    @Autowired
    @Lazy
    private PrerequisiteCompetenceRepository prerequisiteCompetenceRepository;

    @Autowired
    @Lazy
    private PrerequisiteJobscopeRepository prerequisiteJobscopeRepository;

    @Autowired
    @Lazy
    private SubmissionRepository submissionRepository;

    @Autowired
    @Lazy
    private AdvantageRepository advantageRepository;

    @Autowired
    @Lazy
    private ProjectRepository projectRepository;

    @Autowired
    @Lazy
    private SubmissionCriteriaRepository submissionCriteriaRepository;

    @Autowired
    @Lazy
    private EvaluationRepository evaluationRepository;

    @Autowired
    @Lazy
    private ValuationRepository valuationRepository;

    @Autowired
    @Lazy
    private FeedbackRepository feedbackRepository;

    @Autowired
    @Lazy
    private FeedbackAnswerRepository feedbackAnswerRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;


    @Override
    public List<CompanyResponse> getAllCompanies(List<Integer> listAccount) {
        List<Company> companies = companyRepository.findByAccountIdIn(listAccount);
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setIdCompany(company.getId());
            companyResponse.setCompanyName(company.getCompanyName());
            companyResponse.setAddress(company.getAddress());
            companyResponse.setCpEmail(company.getCpEmail());
            companyResponse.setCpName(company.getCpName());
            companyResponse.setStatus(company.getStatus());
            companyResponse.setCpTelp(company.getCpPhone());
            companyResponse.setCompanyEmail(company.getCompanyEmail());
            companyResponse.setSinceYear(company.getSinceYear());
            companyResponse.setIdAccount(company.getAccountId());
            companyResponse.setCpPosition(company.getCpPosition());
            companyResponse.setWebsite(company.getWebsite());
            companyResponse.setTelp(company.getNoPhone());
            companyResponse.setNumEmployee(company.getNumEmployee());
            companyResponse.setLecturerId(company.getLecturerId());
            proposerRepository.findByCompanyIdId(company.getId()).ifPresent(proposer -> companyResponse.setProposer(Arrays.asList(proposer.getProposerName().split("\\|"))));
            companyResponseList.add(companyResponse);
        }
        return companyResponseList;
    }

    public List<CompanyResponse> getCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setIdCompany(company.getId());
            companyResponse.setCompanyName(company.getCompanyName());
            companyResponse.setAddress(company.getAddress());
            companyResponse.setCpEmail(company.getCpEmail());
            companyResponse.setCpName(company.getCpName());
            companyResponse.setStatus(company.getStatus());
            companyResponse.setCpTelp(company.getCpPhone());
            companyResponse.setCompanyEmail(company.getCompanyEmail());
            companyResponse.setSinceYear(company.getSinceYear());
            companyResponse.setIdAccount(company.getAccountId());
            companyResponse.setCpPosition(company.getCpPosition());
            companyResponse.setWebsite(company.getWebsite());
            companyResponse.setTelp(company.getNoPhone());
            companyResponse.setNumEmployee(company.getNumEmployee());
            companyResponse.setLecturerId(company.getLecturerId());
            proposerRepository.findByCompanyIdId(company.getId()).ifPresent(proposer -> companyResponse.setProposer(Arrays.asList(proposer.getProposerName().split("\\|"))));
            companyResponseList.add(companyResponse);
        }
        return companyResponseList;
    }

    @Override
    public void updateCompanyPic(Integer id) {
        companyRepository.updateLecturerIdByLecturerId(id);
    }

    @Override
    public ListCompany getCompaniesAndPrerequisiteByProdi(Integer prodiId) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Company> companies = companyRepository.findAll();

        List<CompanyForCommittee> listCompany = new ArrayList<>();
        Integer countActiveCompany = 0;
        Integer countInactiveCompany = 0;
        Integer countSubmittedPrerequisite = 0;
        Integer countNotSubmittedPrerequisite = 0;
        for (Company company : companies) {
            CompanyForCommittee companyForCommittee = new CompanyForCommittee();
            companyForCommittee.setCompanyName(company.getCompanyName());
            companyForCommittee.setCpEmail(company.getCpEmail());
            companyForCommittee.setId(company.getId());
            companyForCommittee.setStatusCompany(company.getStatus());
            companyForCommittee.setCpPhone(company.getCpPhone());
            companyForCommittee.setCpName(company.getCpName());

            Prerequisite prerequisite = prerequisiteRepository.findByCompanyIdAndYear(company.getId(), currentYear);
            if (Objects.nonNull(prerequisite)) {
                companyForCommittee.setStatusPrerequisite(prerequisite.getStatus());
                listCompany.add(companyForCommittee);

                if (company.getStatus() == Boolean.TRUE) {
                    countActiveCompany++;
                } else {
                    countInactiveCompany++;
                }

                if (prerequisite.getStatus() == Boolean.TRUE) {
                    countSubmittedPrerequisite++;
                } else {
                    countNotSubmittedPrerequisite++;
                }
            }
        }

        ListCompany companyList = new ListCompany();
        companyList.setCompany(listCompany);
        companyList.setTotalCompanyActive(countActiveCompany);
        companyList.setTotalCompanyInactive(countInactiveCompany);
        companyList.setTotalPrerequisiteSubmitted(countSubmittedPrerequisite);
        companyList.setTotalPrerequisiteNotSubmitted(countNotSubmittedPrerequisite);

        return companyList;
    }

    @Override
    public Company createCompany(CompanyRequest company, String cookie) {
        Company newCompany = new Company();

        newCompany.setCompanyEmail(company.getCompanyEmail());
        newCompany.setNoPhone(company.getNoPhone());
        newCompany.setAddress(company.getAddress());

        newCompany.setCpName(company.getCpName());
        newCompany.setCpEmail(company.getCpEmail());
        newCompany.setCpPosition(company.getCpPosition());
        newCompany.setCpPhone(company.getCpPhone());

        newCompany.setWebsite(company.getWebsite());
        newCompany.setLecturerId(company.getLecturerId());
        newCompany.setStatus(company.getStatus());
        newCompany.setCompanyName(company.getCompanyName());
        newCompany.setNumEmployee(company.getNumEmployee());
        newCompany.setSinceYear(company.getSinceYear());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", company.getCompanyEmail());
        jsonObject.put("password", "1234");
        jsonObject.put("id_role", 2);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<Response<CreateAccountResponse>> response = restTemplate.exchange("http://account-service/account/create", HttpMethod.POST, request, new ParameterizedTypeReference<>() {
        });

        if (response.hasBody() && response.getBody() != null) {
            newCompany.setAccountId(response.getBody().getData().getId());
        }

        newCompany = companyRepository.saveAndFlush(newCompany);

        Prerequisite prerequisite = new Prerequisite();
        prerequisite.setRegionId(0);
        prerequisite.setYear(Calendar.getInstance().get(Calendar.YEAR));
        prerequisite.setStatus(Boolean.FALSE);
        prerequisite.setCompany(newCompany);
        prerequisiteRepository.save(prerequisite);


        return newCompany;
    }

    @Override
    public void updateCompany(Integer idCompany, CompanyRequest company) {
        Optional<Company> companyUpdate = companyRepository.findById(idCompany);

        companyUpdate.ifPresent(c -> {
            c.setCompanyName(company.getCompanyName());
            c.setCompanyEmail(company.getCompanyEmail());
            c.setNoPhone(company.getNoPhone());
            c.setAddress(company.getAddress());

            c.setCpName(company.getCpName());
            c.setCpEmail(company.getCpEmail());
            c.setCpPosition(company.getCpPosition());
            c.setCpPhone(company.getCpPhone());

            c.setWebsite(company.getWebsite());
            c.setLecturerId(company.getLecturerId());
            c.setStatus(company.getStatus());
            c.setCompanyName(company.getCompanyName());
            c.setNumEmployee(company.getNumEmployee());
            c.setSinceYear(company.getSinceYear());

            companyRepository.save(c);
        });

    }


    @Override
    public PrerequisiteCard getCardPrerequisiteByCompany(Integer idCompany, String cookie) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Prerequisite prerequisite = prerequisiteRepository.findByCompanyIdAndYear(idCompany, currentYear);

        PrerequisiteCard prerequisiteCard = new PrerequisiteCard();
        prerequisiteCard.setIdPrerequisite(prerequisite.getId());
        prerequisiteCard.setStatusPrerequisite(prerequisite.getStatus());
        prerequisiteCard.setCompanyName(prerequisite.getCompany().getCompanyName());

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmit = restTemplate.exchange("http://management-content-service/management-content/form-submit-time/3", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });
        String startDate = Objects.requireNonNull(formSubmit.getBody()).getData().getStartDate();
        String endDate = formSubmit.getBody().getData().getEndDate();
        prerequisiteCard.setStatusSubmission(DateUtil.checkNowDate(startDate, endDate));

        return prerequisiteCard;
    }

    @Override
    public List<QuotaResponse> getQuota() {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<Prerequisite> prerequisite = prerequisiteRepository.findByYear(currentYear);

        List<QuotaResponse> quotaResponse = new ArrayList<>();
        for (Prerequisite pre : prerequisite) {
            QuotaResponse quota = new QuotaResponse();
            quota.setName(pre.getCompany().getCompanyName());
            quota.setIdCompany(pre.getCompany().getId());
            quota.setQuotaD3(pre.getTotalD3());
            quota.setQuotaD4(pre.getTotalD4());
            quotaResponse.add(quota);
        }
        return quotaResponse;
    }

    @Override
    public PrerequisiteResponse getPrerequisite(Integer idPrerequisite, String cookie) {
        Prerequisite prerequisite = prerequisiteRepository.findById(idPrerequisite).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        PrerequisiteResponse p = new PrerequisiteResponse();
        if (prerequisite != null) {
            p.setFacility(prerequisite.getFacility());
            p.setId(prerequisite.getId());
            p.setCompany(prerequisite.getCompany().getId());
            p.setDescription(prerequisite.getDescription());
            p.setStatus(prerequisite.getStatus());
            p.setInAdvisorMail(prerequisite.getInAdvisorMail());
            p.setInAdvisorName(prerequisite.getInAdvisorName());
            p.setPracticalAddress(prerequisite.getPracticalAddress());
            p.setRegionId(prerequisite.getRegionId());
            p.setInAdvisorPosition(prerequisite.getInAdvisorPosition());
            p.setYear(prerequisite.getYear());
            p.setTotalD3(prerequisite.getTotalD3());
            p.setWorkSystem(prerequisite.getWorkSystem());
            p.setTotalD4(prerequisite.getTotalD4());
            p.setCompanyName(prerequisite.getCompany().getCompanyName());
            p.setProject(prerequisite.getProject());

            List<PrerequisiteCompetence> prerequisiteCompetencies = prerequisiteCompetenceRepository.findByPrerequisiteId(idPrerequisite);
            List<CompetenceAndType> competences = new ArrayList<>();
            for (PrerequisiteCompetence prerequisiteCompetence : prerequisiteCompetencies) {
                CompetenceAndType competenceAndType = new CompetenceAndType();
                competenceAndType.setId(prerequisiteCompetence.getId());
                competenceAndType.setIdCompetence(prerequisiteCompetence.getCompetenceId());
                competenceAndType.setProdiId(prerequisiteCompetence.getProdiId());

                ResponseEntity<Response<Integer>> typeId = restTemplate.exchange("http://management-content-service/management-content/competence/get-type?id=" + prerequisiteCompetence.getCompetenceId(),
                        HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                        });
                competenceAndType.setCompetenceType(Objects.requireNonNull(typeId.getBody()).getData());

                if (prerequisiteCompetence.getProdiId().equals(EProdi.D3.id)) {
                    p.getCompetenciesD3().add(competenceAndType);
                } else {
                    p.getCompetenciesD4().add(competenceAndType);
                }
            }

            List<PrerequisiteJobscope> prerequisiteJobscopes = prerequisiteJobscopeRepository.findByPrerequisiteId(idPrerequisite);
            for (PrerequisiteJobscope pj : prerequisiteJobscopes) {
                if (pj.getProdiId().equals(EProdi.D3.id)) {
                    p.getJobscopesD3().add(pj);
                } else {
                    p.getJobscopesD4().add(pj);
                }
            }
        }

        return p;
    }

    public Boolean updatePrerequisiteByCompany(PrerequisiteRequest prerequisite, Integer idCompany) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Prerequisite prerequisiteUpdate = prerequisiteRepository.findByCompanyIdAndYear(idCompany, year);
        boolean isUpdate = false;
        if (prerequisiteUpdate != null) {
            prerequisiteUpdate.setDescription(prerequisite.getDescription());
            prerequisiteUpdate.setFacility(prerequisite.getFacility());
            prerequisiteUpdate.setInAdvisorName(prerequisite.getInAdvisorName());
            prerequisiteUpdate.setInAdvisorMail(prerequisite.getInAdvisorMail());
            prerequisiteUpdate.setInAdvisorPosition(prerequisite.getInAdvisorPosition());
            prerequisiteUpdate.setPracticalAddress(prerequisite.getPracticalAddress());
            prerequisiteUpdate.setRegionId(prerequisite.getRegionId());
            prerequisiteUpdate.setTotalD3(prerequisite.getTotalD3());
            prerequisiteUpdate.setTotalD4(prerequisite.getTotalD4());
            prerequisiteUpdate.setWorkSystem(prerequisite.getWorkSystem());
            prerequisiteUpdate.setProject(prerequisite.getProject());

            prerequisiteCompetenceRepository.deleteAllByPrerequisiteId(prerequisiteUpdate.getId());
            List<UpdateCompetenceJobscope> competencies = prerequisite.getCompetencies();
            if (competencies != null) {
                List<PrerequisiteCompetence> prerequisiteCompetencies = new ArrayList<>();
                competencies.forEach(education -> {
                    PrerequisiteCompetence prerequisiteCompetence = new PrerequisiteCompetence();
                    prerequisiteCompetence.setCompetenceId(education.getId());
                    prerequisiteCompetence.setProdiId(education.getProdiId());
                    prerequisiteCompetence.setPrerequisite(prerequisiteUpdate);
                    prerequisiteCompetencies.add(prerequisiteCompetence);
                });
                prerequisiteCompetenceRepository.saveAll(prerequisiteCompetencies);
            }

            prerequisiteJobscopeRepository.deleteAllByPrerequisiteId(prerequisiteUpdate.getId());
            List<UpdateCompetenceJobscope> jobscopes = prerequisite.getJobscopes();
            if (jobscopes != null) {
                List<PrerequisiteJobscope> prerequisiteJobscopes = new ArrayList<>();
                jobscopes.forEach(jobscope -> {
                    PrerequisiteJobscope prerequisiteJobscope = new PrerequisiteJobscope();
                    prerequisiteJobscope.setJobscopeId(jobscope.getId());
                    prerequisiteJobscope.setProdiId(jobscope.getProdiId());
                    prerequisiteJobscope.setPrerequisite(prerequisiteUpdate);
                    prerequisiteJobscopes.add(prerequisiteJobscope);
                });
                prerequisiteJobscopeRepository.saveAll(prerequisiteJobscopes);
            }


            prerequisiteRepository.save(prerequisiteUpdate);
            isUpdate = true;
        }

        return isUpdate;
    }

    @Override
    public Boolean updatePrerequisiteByCommittee(Integer idPrerequisite, PrerequisiteRequest prerequisite, String cookie) {
        Optional<Prerequisite> prerequisiteUpdate = prerequisiteRepository.findById(idPrerequisite);
        AtomicReference<Boolean> isSuccess = new AtomicReference<>(Boolean.FALSE);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        prerequisiteUpdate.ifPresent(p -> {
            p.setDescription(prerequisite.getDescription());
            p.setFacility(prerequisite.getFacility());
            p.setInAdvisorName(prerequisite.getInAdvisorName());
            p.setInAdvisorMail(prerequisite.getInAdvisorMail());
            p.setInAdvisorPosition(prerequisite.getInAdvisorPosition());
            p.setPracticalAddress(prerequisite.getPracticalAddress());
            p.setRegionId(prerequisite.getRegionId());
            p.setWorkSystem(prerequisite.getWorkSystem());
            p.setProject(prerequisite.getProject());

            prerequisiteCompetenceRepository.deleteAllByPrerequisiteId(idPrerequisite);
            List<UpdateCompetenceJobscope> competencies = prerequisite.getCompetencies();
            if (competencies != null) {
                List<PrerequisiteCompetence> prerequisiteCompetencies = new ArrayList<>();
                competencies.forEach(education -> {
                    PrerequisiteCompetence prerequisiteCompetence = new PrerequisiteCompetence();
                    prerequisiteCompetence.setCompetenceId(education.getId());
                    prerequisiteCompetence.setProdiId(education.getProdiId());
                    prerequisiteCompetence.setPrerequisite(p);
                    prerequisiteCompetencies.add(prerequisiteCompetence);
                });
                prerequisiteCompetenceRepository.saveAll(prerequisiteCompetencies);
            }

            prerequisiteJobscopeRepository.deleteAllByPrerequisiteId(idPrerequisite);
            List<UpdateCompetenceJobscope> jobscopes = prerequisite.getJobscopes();
            if (jobscopes != null) {
                List<PrerequisiteJobscope> prerequisiteJobscopes = new ArrayList<>();
                jobscopes.forEach(jobscope -> {
                    PrerequisiteJobscope prerequisiteJobscope = new PrerequisiteJobscope();
                    prerequisiteJobscope.setJobscopeId(jobscope.getId());
                    prerequisiteJobscope.setProdiId(jobscope.getProdiId());
                    prerequisiteJobscope.setPrerequisite(p);
                    prerequisiteJobscopes.add(prerequisiteJobscope);
                });
                prerequisiteJobscopeRepository.saveAll(prerequisiteJobscopes);
            }

            String urlDeleteCompanyInMapping = "http://mapping-service/mapping/final/delete-company/";
            if (prerequisite.getTotalD3() < p.getTotalD3()) {
                ResponseEntity<Response<FormSubmitTimeResponse>> deleteCompany =
                        restTemplate.exchange(
                                urlDeleteCompanyInMapping + p.getCompany().getId() + "?prodi=" + Constant.Prodi.D3,
                                HttpMethod.DELETE,
                                req,
                                new ParameterizedTypeReference<>() {
                                });
                if (deleteCompany.getStatusCode().is2xxSuccessful()) {
                    p.setTotalD3(prerequisite.getTotalD3());
                }
            } else {
                p.setTotalD3(prerequisite.getTotalD3());
            }
            if (prerequisite.getTotalD4() < p.getTotalD4()) {
                ResponseEntity<Response<FormSubmitTimeResponse>> deleteCompany =
                        restTemplate.exchange(
                                urlDeleteCompanyInMapping + p.getCompany().getId() + "?prodi=" + Constant.Prodi.D4,
                                HttpMethod.DELETE,
                                req,
                                new ParameterizedTypeReference<>() {
                                });
                if (deleteCompany.getStatusCode().is2xxSuccessful()) {
                    p.setTotalD4(prerequisite.getTotalD4());
                }
            } else {
                p.setTotalD4(prerequisite.getTotalD4());
            }

            prerequisiteRepository.save(p);

            isSuccess.set(Boolean.TRUE);
        });
        return isSuccess.get();
    }

    @Override
    public Boolean markAsDoneByCommittee(Integer idPrerequisite) {
        Optional<Prerequisite> prerequisite = prerequisiteRepository.findById(idPrerequisite);
        AtomicReference<Boolean> isSuccess = new AtomicReference<>(Boolean.FALSE);
        prerequisite.ifPresent(p -> {
            if (p.getStatus() != null) {
                Boolean status = p.getStatus();
                p.setStatus(!status);
            } else {
                p.setStatus(Boolean.FALSE);
            }

            prerequisiteRepository.save(p);
            isSuccess.set(Boolean.TRUE);
        });
        return isSuccess.get();
    }

    @Override
    public Boolean markAsDoneByCompany(Integer idCompany) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Prerequisite prerequisiteUpdate = prerequisiteRepository.findByCompanyIdAndYear(idCompany, year);

        boolean isUpdate = false;
        if (prerequisiteUpdate != null) {
            if (prerequisiteUpdate.getStatus() != null) {
                prerequisiteUpdate.setStatus(!prerequisiteUpdate.getStatus());
            } else {
                prerequisiteUpdate.setStatus(Boolean.FALSE);
            }

            prerequisiteRepository.save(prerequisiteUpdate);
            isUpdate = true;
        }

        return isUpdate;
    }

    @Override
    public List<CompanyName> getCompanyNameById() {
        List<Company> company = companyRepository.findByStatus(true);

        List<CompanyName> companyIdNames = new ArrayList<>();
        company.forEach(c -> {
            CompanyName companyIdName = new CompanyName();
            companyIdName.setId(c.getId());
            companyIdName.setName(c.getCompanyName());
            companyIdNames.add(companyIdName);
        });

        return companyIdNames;
    }

    @Override
    public PrerequisiteCard getCardPrerequisiteByCommittee(String cookie, Integer idCompany) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Prerequisite p = prerequisiteRepository.findByCompanyIdAndYear(idCompany, currentYear);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        PrerequisiteCard prerequisiteCard = new PrerequisiteCard();
        prerequisiteCard.setIdPrerequisite(p.getId());
        prerequisiteCard.setStatusPrerequisite(p.getStatus());
        prerequisiteCard.setCompanyName(p.getCompany().getCompanyName());

        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmit = restTemplate.exchange("http://management-content-service/management-content/form-submit-time/3", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });
        String startDate = Objects.requireNonNull(formSubmit.getBody()).getData().getStartDate();
        String endDate = Objects.requireNonNull(formSubmit.getBody()).getData().getEndDate();
        prerequisiteCard.setStatusSubmission(DateUtil.checkNowDate(startDate, endDate));

        return prerequisiteCard;
    }

    @Override
    public CompanyResponse getCompanyById(Integer id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setIdCompany(company.getId());
            companyResponse.setCompanyName(company.getCompanyName());
            companyResponse.setAddress(company.getAddress());
            companyResponse.setCpEmail(company.getCpEmail());
            companyResponse.setCpName(company.getCpName());
            companyResponse.setStatus(company.getStatus());
            companyResponse.setCpTelp(company.getCpPhone());
            companyResponse.setCompanyEmail(company.getCompanyEmail());
            companyResponse.setSinceYear(company.getSinceYear());
            companyResponse.setIdAccount(company.getAccountId());
            companyResponse.setCpPosition(company.getCpPosition());
            companyResponse.setWebsite(company.getWebsite());
            companyResponse.setTelp(company.getNoPhone());
            companyResponse.setNumEmployee(company.getNumEmployee());
            companyResponse.setLecturerId(company.getLecturerId());
            proposerRepository.findByCompanyIdId(company.getId()).ifPresent(proposer -> companyResponse.setProposer(Arrays.asList(proposer.getProposerName().split("\\|"))));
            return companyResponse;
        }
        return null;
    }

    @Override
    public List<CompanyIdName> getNameAndIdCompanies(Integer idProdi) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Prerequisite> pList = null;

        if (idProdi == EProdi.D3.id) {
            pList = prerequisiteRepository.
                    findByYearEqualsAndTotalD3GreaterThan(currentYear, 0);
        } else if (idProdi == EProdi.D4.id) {
            pList = prerequisiteRepository.
                    findByYearEqualsAndTotalD4GreaterThan(currentYear, 0);
        }

        List<CompanyIdName> companyIdNames = new ArrayList<>();
        Objects.requireNonNull(pList).forEach(p -> {
            if (p.getCompany().getStatus() == Boolean.TRUE) {
                CompanyIdName companyIdName = new CompanyIdName();
                companyIdName.setId(p.getCompany().getId());
                companyIdName.setName(p.getCompany().getCompanyName());

                if (idProdi == EProdi.D3.id) {
                    companyIdName.setQuota(p.getTotalD3());
                } else {
                    companyIdName.setQuota(p.getTotalD4());
                }

                companyIdNames.add(companyIdName);
            }
        });
        return companyIdNames;
    }

    @Override
    public void deletePrerequisiteCompetence(Integer idCompetence) {
        prerequisiteCompetenceRepository.deleteAllByCompetenceId(idCompetence);
    }

    @Override
    public void deletePrerequisiteJobscope(Integer idJobscope) {
        prerequisiteJobscopeRepository.deleteAllByJobscopeId(idJobscope);
    }

    @Override
    public List<Criteria> getCompanyCriteria() {
        return criteriaRepository.findAll();
    }

    @Override
    public void createCriteria(CriteriaRequest criteriaRequest) {
        Criteria c = new Criteria();
        c.setCriteriaName(criteriaRequest.getCriteriaName());
        criteriaRepository.save(c);
    }

    @Override
    public void updateCriteria(Integer id, CriteriaRequest criteriaRequest) {
        Optional<Criteria> criteria = criteriaRepository.findById(id);

        criteria.ifPresent(c -> {
            c.setCriteriaName(criteriaRequest.getCriteriaName());

            criteriaRepository.save(c);
        });
    }

    @Override
    public void deleteCriteria(Integer id) {
        submissionCriteriaRepository.deleteByCriteriaId(id);
        criteriaRepository.deleteById(id);
    }

    @Override
    public List<CompanyRequirement> getCompanyRequirements(String cookie, Integer idProdi) {
        List<CompanyRequirement> companyRequirements = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);
        List<Company> companies = companyRepository.findByStatus(true);

        ResponseEntity<ResponseList<CompetenceResponse>> competenceResponse = restTemplate.exchange("http://management-content-service/management-content/competence/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        List<CompetenceResponse> competenceResponses = Objects.requireNonNull(competenceResponse.getBody()).getData();

        companies.forEach(company -> {
            CompanyRequirement companyRequirement = new CompanyRequirement();
            companyRequirement.setIdCompany(company.getId());
            companyRequirement.setCompanyName(company.getCompanyName());

            Prerequisite prerequisite;
            if (Objects.equals(idProdi, Constant.Prodi.D3)) {
                prerequisite = prerequisiteRepository.findByCompanyIdAndYearAndTotalD3GreaterThan(company.getId(), year, 0);
            } else {
                prerequisite = prerequisiteRepository.findByCompanyIdAndYearAndTotalD4GreaterThan(company.getId(), year, 0);
            }

            if (prerequisite != null) {
                companyRequirement.setIdDomicile(prerequisite.getRegionId());
                List<PrerequisiteJobscope> prerequisiteJobscopes = prerequisiteJobscopeRepository.findByPrerequisiteIdAndProdiId(prerequisite.getId(), idProdi);
                List<PrerequisiteCompetence> prerequisiteCompetencies = prerequisiteCompetenceRepository.findByPrerequisiteIdAndProdiId(prerequisite.getId(), idProdi);

                List<CompetenceAndType> competenceAndTypes = new ArrayList<>();
                for (PrerequisiteCompetence prerequisiteCompetence : prerequisiteCompetencies) {
                    CompetenceAndType competenceAndType = new CompetenceAndType();
                    competenceAndType.setId(prerequisiteCompetence.getId());
                    competenceAndType.setIdCompetence(prerequisiteCompetence.getCompetenceId());
                    competenceAndType.setProdiId(idProdi);

                    competenceResponses.stream().filter(c -> Objects.equals(c.getId(), prerequisiteCompetence.getCompetenceId())).findFirst().ifPresent(c -> {
                        competenceAndType.setCompetenceType(c.getType());
                    });
                    competenceAndTypes.add(competenceAndType);
                }

                companyRequirement.setCompetence(competenceAndTypes);
                companyRequirement.setJobscope(prerequisiteJobscopes);
                companyRequirements.add(companyRequirement);
            }

        });

        return companyRequirements;
    }

    @Override
    public List<SubmissionResponse> getCompanySubmission() {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<SubmissionResponse> srList = new ArrayList<>();
        List<Submission> submissions = submissionRepository.findAllByYearKpPklAndIsDeleted(currentYear, false);
        for (Submission s : submissions) {
            SubmissionResponse sr = new SubmissionResponse();
            sr.setId(s.getId());
            sr.setCompanyName(s.getCompanyName());
            sr.setCpName(s.getCpName());
            sr.setCpPhone(s.getCpPhone());
            sr.setCpMail(s.getCpMail());

            srList.add(sr);
        }

        return srList;
    }

    @Override
    public DetailSubmissionResponse getDetailCompanySubmission(Integer id) {
        DetailSubmissionResponse sr = new DetailSubmissionResponse();

        submissionRepository.findById(id).ifPresent(s -> {
            sr.setId(s.getId());
            sr.setCompanyName(s.getCompanyName());
            sr.setCompanyMail(s.getCompanyMail());
            sr.setAddress(s.getAddress());
            sr.setNoPhone(s.getNoPhone());
            sr.setCpName(s.getCpName());
            sr.setCpPhone(s.getCpPhone());
            sr.setCpMail(s.getCpMail());
            sr.setCpPosition(s.getCpPosition());
            sr.setSinceYear(s.getSinceYear());
            sr.setWebsite(s.getWebsite());
            sr.setNumEmployee(s.getNumEmployee());
            sr.setReceptMechanism(s.getReceptMechanism());
            sr.setProdi(s.getProdi());

            proposerRepository.findBySubmissionIdId(s.getId()).ifPresent(psr -> sr.setProposer(Arrays.asList(psr.getProposerName().split("\\|"))));

            submissionCriteriaRepository.findBySubmissionId(s.getId()).ifPresent(scList -> {
                List<String> cStringList = new ArrayList<>();
                for (SubmissionCriteria sc : scList) {
                    cStringList.add(sc.getCriteria().getCriteriaName());
                }
                sr.setCriteria(cStringList);
            });

            advantageRepository.findBySubmissionId(s.getId()).ifPresent(aList -> {
                List<String> aStringList = new ArrayList<>();
                for (Advantage a : aList) {
                    aStringList.add(a.getDescription());
                }
                sr.setAdvantages(aStringList);
            });

            projectRepository.findBySubmissionId(s.getId()).ifPresent(pList -> {
                List<ProjectResponse> prList = new ArrayList<>();
                for (Project p : pList) {
                    ProjectResponse pr = new ProjectResponse();
                    pr.setName(p.getName());
                    pr.setDescription(p.getDescription());
                    prList.add(pr);
                }
                sr.setProjects(prList);
            });
        });

        return sr;
    }

    @Override
    public void createCompanySubmission(SubmissionRequest submissionRequest) {
        Submission s = new Submission();
        s.setCompanyName(submissionRequest.getCompanyName());
        s.setCompanyMail(submissionRequest.getCompanyMail());
        s.setAddress(submissionRequest.getAddress());
        s.setNoPhone(submissionRequest.getNoPhone());
        s.setCpName(submissionRequest.getCpName());
        s.setCpPhone(submissionRequest.getCpPhone());
        s.setCpMail(submissionRequest.getCpMail());
        s.setCpPosition(submissionRequest.getCpPosition());
        s.setSinceYear(submissionRequest.getSinceYear());
        s.setWebsite(submissionRequest.getWebsite());
        s.setNumEmployee(submissionRequest.getNumEmployee());
        s.setReceptMechanism(submissionRequest.getReceptMechanism());
        s.setProdi(submissionRequest.getProdi());
        s = submissionRepository.saveAndFlush(s);

        Proposer psr = new Proposer();
        psr.setProposerName(submissionRequest.getProposer());
        psr.setSubmissionId(s);
        proposerRepository.save(psr);

        List<Integer> criteriaId = submissionRequest.getCriteria();
        if (criteriaId != null) {
            for (Integer cid : criteriaId) {
                SubmissionCriteria sc = new SubmissionCriteria();
                sc.setSubmission(s);

                criteriaRepository.findById(cid).ifPresent(c -> {
                    sc.setCriteria(c);
                    submissionCriteriaRepository.save(sc);
                });
            }
        }

        List<String> advantages = submissionRequest.getAdvantages();
        if (advantages != null) {
            for (String ad : advantages) {
                Advantage a = new Advantage();
                a.setSubmission(s);
                a.setDescription(ad);
                advantageRepository.save(a);
            }
        }

        List<Project> projects = submissionRequest.getProjects();
        if (projects != null) {
            for (Project ps : projects) {
                Project p = new Project();
                p.setSubmission(s);
                p.setName(ps.getName());
                p.setDescription(ps.getDescription());
                projectRepository.save(p);
            }
        }
    }

    @Override
    public void acceptCompanySubmission(Integer id, String cookie) {
        submissionRepository.findById(id).ifPresent(s -> {
            CompanyRequest cr = new CompanyRequest();

            cr.setCompanyName(s.getCompanyName());
            cr.setCompanyEmail(s.getCompanyMail());
            cr.setNoPhone(s.getNoPhone());
            cr.setAddress(s.getAddress());

            cr.setCpName(s.getCpName());
            cr.setCpEmail(s.getCpMail());
            cr.setCpPosition(s.getCpPosition());
            cr.setCpPhone(s.getCpPhone());

            cr.setWebsite(s.getWebsite());
            cr.setNumEmployee(s.getNumEmployee());
            cr.setSinceYear(s.getSinceYear());

            cr.setLecturerId(null);
            cr.setStatus(true);

            Company newCompany = this.createCompany(cr, cookie);
            proposerRepository.findBySubmissionIdId(s.getId()).ifPresent(psr -> {
                psr.setCompanyId(newCompany);
                proposerRepository.save(psr);
            });

            s.setIsDeleted(true);
            submissionRepository.save(s);
        });
    }

    @Override
    public void declineCompanySubmission(Integer id) {
        submissionRepository.findById(id).ifPresent(s -> {
            s.setIsDeleted(true);
            submissionRepository.save(s);
        });
    }

    @Override
    public EvaluationCardResponse getEvaluationCardByCompany(Integer idCompany, String cookie) {
        EvaluationCardResponse ecrList = new EvaluationCardResponse();
        EvaluationCard ecr;

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        // Get is final mapping (Mapping)
        ResponseEntity<Response<Integer>> isFinalMappingD3Request =
                restTemplate.exchange(
                        "http://mapping-service/mapping/get-is-final/3",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<Integer>>() {
                        });

        if (isFinalMappingD3Request.getStatusCode().is4xxClientError() || isFinalMappingD3Request.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting is final mapping D3");
        }

        Integer isFinalMappingD3 = Objects.requireNonNull(isFinalMappingD3Request.getBody()).getData();

        ResponseEntity<Response<Integer>> isFinalMappingD4Request =
                restTemplate.exchange(
                        "http://mapping-service/mapping/get-is-final/4",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<Integer>>() {
                        });

        if (isFinalMappingD4Request.getStatusCode().is4xxClientError() || isFinalMappingD4Request.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting is final mapping D4");
        }

        Integer isFinalMappingD4 = Objects.requireNonNull(isFinalMappingD4Request.getBody()).getData();

        // Get submit timeline D3 (Management Content)
        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitD3 =
                restTemplate.exchange(
                        "http://management-content-service/management-content/form-submit-time/5",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                        });

        if (formSubmitD3.getStatusCode().is4xxClientError() || formSubmitD3.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting submit timeline");
        }

        String startDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getStartDate();
        String endDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getEndDate();
        Boolean isDateAvailableD3 = DateUtil.checkNowDate(startDate, endDate);

        // Get submit timeline D4 (Management Content)
        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitD4 =
                restTemplate.exchange(
                        "http://management-content-service/management-content/form-submit-time/6",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                        });

        if (formSubmitD4.getStatusCode().is4xxClientError() || formSubmitD4.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting submit timeline");
        }

        startDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getStartDate();
        endDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getEndDate();
        Boolean isDateAvailableD4 = DateUtil.checkNowDate(startDate, endDate);
        Integer numEvaluationD4 = 1;

        if (!isDateAvailableD4) {
            formSubmitD4 =
                    restTemplate.exchange(
                            "http://management-content-service/management-content/form-submit-time/7",
                            HttpMethod.GET,
                            req,
                            new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                            });

            if (formSubmitD4.getStatusCode().is4xxClientError() || formSubmitD4.getStatusCode().is5xxServerError()) {
                throw new IllegalStateException("Error when getting submit timeline");
            }

            startDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getStartDate();
            endDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getEndDate();
            isDateAvailableD4 = DateUtil.checkNowDate(startDate, endDate);
            numEvaluationD4 = 2;

            if (!isDateAvailableD4) {
                formSubmitD4 =
                        restTemplate.exchange(
                                "http://management-content-service/management-content/form-submit-time/8",
                                HttpMethod.GET,
                                req,
                                new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                                });

                if (formSubmitD4.getStatusCode().is4xxClientError() || formSubmitD4.getStatusCode().is5xxServerError()) {
                    throw new IllegalStateException("Error when getting submit timeline");
                }

                startDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getStartDate();
                endDate = Objects.requireNonNull(formSubmitD4.getBody()).getData().getEndDate();
                isDateAvailableD4 = DateUtil.checkNowDate(startDate, endDate);
                numEvaluationD4 = 3;
            }
        }

        // Get participant-company final mapping (Mapping)
        ResponseEntity<ResponseList<ParticipantFinalMappingResponse>> participantFinalMapping =
                restTemplate.exchange(
                        "http://mapping-service/mapping/final/company/" + idCompany,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<ResponseList<ParticipantFinalMappingResponse>>() {
                        });

        if (participantFinalMapping.getStatusCode().is4xxClientError() || participantFinalMapping.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting participant-company final mapping");
        }

        List<ParticipantFinalMappingResponse> pfmrList = Objects.
                requireNonNull(participantFinalMapping.getBody()).getData();

        // Get participant's name (Participant)
        JSONArray jsonArray = new JSONArray();
        pfmrList.forEach(pfmr -> {
            jsonArray.put(pfmr.getParticipantId());
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.PayloadResponseConstant.ID, jsonArray);

        req = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                "http://participant-service/participant/get-by-id",
                HttpMethod.POST,
                req,
                new ParameterizedTypeReference<ResponseList<ParticipantResponse>>() {
                });

        if (pResponse.getStatusCode().is4xxClientError() || pResponse.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting participant's name");
        }

        List<ParticipantResponse> prList = Objects.requireNonNull(pResponse.getBody()).getData();

        for (ParticipantResponse pr : prList) {
            ecr = new EvaluationCard();
            ecr.setNumEvaluation(1);

            if (pr.getIdProdi() == EProdi.D4.id) {
                ecr.setNumEvaluation(numEvaluationD4);
            }

            Evaluation e = evaluationRepository.findByIdParticipantAndNumEvaluation(pr.getIdParticipant(), ecr.getNumEvaluation());
            if (e != null) {
                ecr.setParticipantId(e.getIdParticipant());
                ecr.setParticipantName(pr.getName());
                ecr.setParticipantProdi(pr.getIdProdi());
                ecr.setStatusSubmit(e.getStatus());

                if (pr.getIdProdi() == EProdi.D3.id) {
                    ecrList.getParticipantD3().add(ecr);
                } else {
                    ecrList.getParticipantD4().add(ecr);
                }
            }
        }

        if (isFinalMappingD3 == 0) {
            ecrList.getParticipantD3().removeIf(e -> (e.getParticipantProdi() == EProdi.D3.id));
        }

        if (isFinalMappingD4 == 0) {
            ecrList.getParticipantD3().removeIf(e -> (e.getParticipantProdi() == EProdi.D4.id));
        }

        ecrList.setIsMoreThanTimelineD3(Boolean.FALSE.equals(isDateAvailableD3));
        ecrList.setIsMoreThanTimelineD4(Boolean.FALSE.equals(isDateAvailableD4));
        return ecrList;
    }

    @Override
    public EvaluationDetailResponse getEvaluationDetail(String cookie, Integer idParticipant, Integer numEval) {
        HashMap<Integer, EvaluationDetailResponse> resMap = new HashMap<>();
        EvaluationDetailResponse edr;
        ValuationRequest vr;

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        List<Valuation> vList = valuationRepository.findByParticipantId(idParticipant);

        for (Valuation v : vList) {
            Integer numEvaluation = v.getEvaluation().getNumEvaluation();

            if (resMap.containsKey(numEvaluation)) {
                vr = new ValuationRequest();
                vr.setAspectName(v.getAspectName());
                vr.setValue(v.getValue());

                if (v.getIsCore() == Boolean.TRUE) {
                    resMap.get(numEvaluation).getValuationCore().add(vr);
                } else {
                    resMap.get(numEvaluation).getValuationNonCore().add(vr);
                }
                continue;
            }

            edr = new EvaluationDetailResponse();
            edr.setNumEvaluation(numEvaluation);
            edr.setComment(v.getEvaluation().getComment());
            edr.setPosition(v.getEvaluation().getPosition());
            edr.setNim(idParticipant);

            vr = new ValuationRequest();
            vr.setAspectName(v.getAspectName());
            vr.setValue(v.getValue());

            if (v.getIsCore() == Boolean.TRUE) {
                edr.getValuationCore().add(vr);
            } else {
                edr.getValuationNonCore().add(vr);
            }

            Optional<Company> c = companyRepository.findById(v.getEvaluation().getIdCompany());

            if (c.isPresent()) {
                edr.setCompanyName(c.get().getCompanyName());
                edr.setCompanyAddress(c.get().getAddress());
                edr.setCpName(c.get().getCpName());
                edr.setCpEmail(c.get().getCpEmail());
                edr.setCpPhone(c.get().getCpPhone());

                Integer idProdi = v.getEvaluation().getIdProdi();

                int idFormSubmit = 11; // Untuk D3

                if (idProdi == EProdi.D4.id) {
                    idFormSubmit = 12;
                }

                ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitTimeResponse =
                        restTemplate.exchange(
                                "http://management-content-service/management-content/form-submit-time/" + idFormSubmit,
                                HttpMethod.GET,
                                req,
                                new ParameterizedTypeReference<>() {
                                });

                FormSubmitTimeResponse formSubmit = Objects.requireNonNull(formSubmitTimeResponse.getBody()).getData();

                edr.setStartDate(formSubmit.getStartDate());
            }
            resMap.put(numEvaluation, edr);
        }

        return resMap.get(numEval);
    }

    @Override
    public void createEvaluation(List<CreateEvaluationRequest> createEvaluationRequest) {
        for (CreateEvaluationRequest cer : createEvaluationRequest) {
            Evaluation e = new Evaluation();
            e.setIdCompany(cer.getIdCompany());
            e.setIdParticipant(cer.getIdParticipant());
            e.setIdProdi(cer.getIdProdi());
            e.setPosition(cer.getPosition());

            if (cer.getIdProdi() == EProdi.D3.id) {
                e.setNumEvaluation(1);
                evaluationRepository.save(e);
                continue;
            }

            if (cer.getIdProdi() == EProdi.D4.id) {
                e.setNumEvaluation(1);
                evaluationRepository.save(e);

                e = new Evaluation();
                e.setIdCompany(cer.getIdCompany());
                e.setIdParticipant(cer.getIdParticipant());
                e.setIdProdi(cer.getIdProdi());
                e.setNumEvaluation(2);
                evaluationRepository.save(e);

                e = new Evaluation();
                e.setIdCompany(cer.getIdCompany());
                e.setIdParticipant(cer.getIdParticipant());
                e.setIdProdi(cer.getIdProdi());
                e.setNumEvaluation(3);
                evaluationRepository.save(e);
            }
        }
    }

    @Override
    public void updateEvaluation(Integer idParticipant, UpdateEvaluationRequest updateEvaluationRequest) {
        Evaluation e = evaluationRepository.findByIdParticipantAndNumEvaluation(
                idParticipant, updateEvaluationRequest.getNumEvaluation());

        if (e != null) {
            e.setComment(updateEvaluationRequest.getComment());
            e.setPosition(updateEvaluationRequest.getPosition());
            e.setStatus(1);
            evaluationRepository.save(e);

            for (ValuationRequest vr : updateEvaluationRequest.getValuation()) {
                Valuation v = new Valuation();
                v.setAspectName(vr.getAspectName());
                v.setValue(vr.getValue());
                v.setIsCore(vr.getIsCore());
                v.setEvaluation(e);
                valuationRepository.save(v);
            }
        }
    }

    @Override
    public void deleteAllEvaluation(Integer idProdi) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        evaluationRepository.deleteAllByYearAndIdProdi(currentYear, idProdi);
    }

    @Override
    public List<PrerequisiteTableResponse> getPrerequisitesCompany(Integer idProdi, String cookie) {
        List<PrerequisiteTableResponse> prerequisiteTableResponseList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<JobscopeResponse>> jobscopeResponse =
                restTemplate.exchange(
                        "http://management-content-service/management-content/jobscope/get-all/" + idProdi,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<>() {
                        });

        List<JobscopeResponse> jobscopeList = Objects.requireNonNull(jobscopeResponse.getBody()).getData();

        ResponseEntity<ResponseList<CompetenceResponse>> competenceResponse =
                restTemplate.exchange(
                        "http://management-content-service/management-content/competence/get-all/" + idProdi,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<>() {
                        });

        List<CompetenceResponse> competenceList = Objects.requireNonNull(competenceResponse.getBody()).getData();


        List<Prerequisite> prerequisites;
        if (idProdi == EProdi.D3.id) {
            prerequisites = prerequisiteRepository.findByYearEqualsAndTotalD3GreaterThan(currentYear, 0);
        } else {
            prerequisites = prerequisiteRepository.findByYearEqualsAndTotalD4GreaterThan(currentYear, 0);
        }

        for (Prerequisite p : prerequisites) {
            PrerequisiteTableResponse pt = new PrerequisiteTableResponse();
            pt.setId(p.getCompany().getId());
            pt.setCompanyName(p.getCompany().getCompanyName());
            pt.setAddress(p.getCompany().getAddress());
            pt.setWorkSystem(p.getWorkSystem());
            pt.setProject(p.getProject());

            if (idProdi == EProdi.D3.id) {
                pt.setQuota(p.getTotalD3());
            } else {
                pt.setQuota(p.getTotalD4());
            }

            List<PrerequisiteJobscope> prerequisiteJobscopeList = prerequisiteJobscopeRepository.findByPrerequisiteId(p.getId());
            for (PrerequisiteJobscope pjs : prerequisiteJobscopeList) {
                jobscopeList.stream().filter(j -> j.getId().equals(pjs.getJobscopeId())).findFirst().ifPresent(js -> pt.getJobScopes().add(js.getName()));
            }

            List<PrerequisiteCompetence> prerequisiteCompetenceList = prerequisiteCompetenceRepository.findByPrerequisiteId(p.getId());
            for (PrerequisiteCompetence pc : prerequisiteCompetenceList) {
                CompetenceResponse c = competenceList.stream().filter(j -> j.getId().equals(pc.getCompetenceId()))
                        .findFirst().orElse(null);
                if (c != null) {
                    if (c.getType().equals(Constant.CompetenceConstant.PROGRAMMING_LANGUAGE)) {
                        if (pt.getProgrammingLanguages() == null || pt.getProgrammingLanguages().isEmpty() || pt.getProgrammingLanguages().isBlank()) {
                            pt.setProgrammingLanguages(c.getName());
                        } else {
                            pt.setProgrammingLanguages(pt.getProgrammingLanguages() + ", " + c.getName());
                        }
                    } else if (c.getType().equals(Constant.CompetenceConstant.DATABASE)) {
                        if (pt.getDatabases() == null || pt.getDatabases().isEmpty() || pt.getDatabases().isBlank()) {
                            pt.setDatabases(c.getName());
                        } else {
                            pt.setDatabases(pt.getDatabases() + ", " + c.getName());
                        }
                    } else if (c.getType().equals(Constant.CompetenceConstant.FRAMEWORK)) {
                        if (pt.getFrameworks() == null || pt.getFrameworks().isEmpty() || pt.getFrameworks().isBlank()) {
                            pt.setFrameworks(c.getName());
                        } else {
                            pt.setFrameworks(pt.getFrameworks() + ", " + c.getName());
                        }
                    } else if (c.getType().equals(Constant.CompetenceConstant.TOOL)) {
                        if (pt.getTools() == null || pt.getTools().isEmpty() || pt.getTools().isBlank()) {
                            pt.setTools(c.getName());
                        } else {
                            pt.setTools(pt.getTools() + ", " + c.getName());
                        }
                    } else if (c.getType().equals(Constant.CompetenceConstant.MODELLING)) {
                        if (pt.getModellingTools() == null || pt.getModellingTools().isEmpty() || pt.getModellingTools().isBlank()) {
                            pt.setModellingTools(c.getName());
                        } else {
                            pt.setModellingTools(pt.getModellingTools() + ", " + c.getName());
                        }
                    } else if (c.getType().equals(Constant.CompetenceConstant.COMMUNICATION_LANGUAGE)) {
                        if (pt.getCommunicationLanguages() == null || pt.getCommunicationLanguages().isEmpty() || pt.getCommunicationLanguages().isBlank()) {
                            pt.setCommunicationLanguages(c.getName());
                        } else {
                            pt.setCommunicationLanguages(pt.getCommunicationLanguages() + ", " + c.getName());
                        }
                    }
                }
            }

            pt.setDescription(p.getDescription());
            pt.setFacility(p.getFacility());

            prerequisiteTableResponseList.add(pt);
        }
        return prerequisiteTableResponseList;
    }

    @Override
    public void generatePdfEvaluation(Integer idParticipant, HttpServletResponse response, String cookie, Integer numEval) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        List<Valuation> valuation = valuationRepository.findByParticipantIdAndNumEvaluation(idParticipant, numEval);
        String domicile;
        Company company;
        String companyName = "";
        String advisorName = "";
        String advisorPosition = "";
        String position = "";
        String participantName = "";
        String startTime = "";
        String alamat = "";
        String cpName = "";
        String cpEmail = "";
        String cpPhone = "";
        String comment = "";
        String headerWords = "";

        if (!valuation.isEmpty()) {
            company = companyRepository.findById(valuation.get(0).getEvaluation().getIdCompany()).orElse(null);
            if (company != null) {
                Prerequisite p = prerequisiteRepository.findByCompanyIdAndYear(company.getId(), currentYear);
                ResponseEntity<Response<String>> region = restTemplate.exchange("http://management-content-service/management-content/domicile/" +
                        p.getRegionId(), HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

                domicile = PDFUtil.remove(Objects.requireNonNull(region.getBody()).getData());

                companyName = PDFUtil.remove(company.getCompanyName());
                advisorName = PDFUtil.remove(p.getInAdvisorName());
                advisorPosition = PDFUtil.remove(p.getInAdvisorPosition());
                alamat = PDFUtil.remove(company.getAddress());
                cpName = PDFUtil.remove(company.getCpName());
                cpEmail = PDFUtil.remove(company.getCpEmail());
                cpPhone = PDFUtil.remove(company.getCpPhone());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(idParticipant);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.PayloadResponseConstant.ID, jsonArray);

                req = new HttpEntity<>(jsonObject.toString(), headers);
                headers.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                        "http://participant-service/participant/get-by-id",
                        HttpMethod.POST,
                        req,
                        new ParameterizedTypeReference<>() {
                        });

                if (pResponse.getBody() != null && !pResponse.getBody().getData().isEmpty()) {
                    ParticipantResponse pr = pResponse.getBody().getData().get(0);
                    participantName = PDFUtil.remove(pr.getName());

                    if (pr.getIdProdi() == 0) {
                        ResponseEntity<Response<FormSubmitTimeResponse>> timeD3 =
                                restTemplate.exchange(
                                        "http://management-content-service/management-content/form-submit-time/11",
                                        HttpMethod.GET,
                                        req,
                                        new ParameterizedTypeReference<>() {
                                        });
                        if (timeD3.getBody() != null && timeD3.getBody().getData() != null) {
                            startTime = PDFUtil.remove(timeD3.getBody().getData().getStartDate());
                        } else {
                            startTime = "";
                        }
                        headerWords = "KP D3";
                    } else {
                        ResponseEntity<Response<FormSubmitTimeResponse>> timeD4 =
                                restTemplate.exchange(
                                        "http://management-content-service/management-content/form-submit-time/12",
                                        HttpMethod.GET,
                                        req,
                                        new ParameterizedTypeReference<>() {
                                        });
                        if (timeD4.getBody() != null && timeD4.getBody().getData() != null) {
                            startTime = PDFUtil.remove(timeD4.getBody().getData().getStartDate());
                        } else {
                            startTime = "";
                        }
                        headerWords = "PKL D4";
                    }
                }


            } else {
                domicile = ".............";
            }
            position = PDFUtil.remove(valuation.get(0).getEvaluation().getPosition());
            comment = PDFUtil.remove(valuation.get(0).getEvaluation().getComment());
        } else {
            domicile = ". .............";
        }


        InputStream input = null;
        try {
            Resource resource = new ClassPathResource("pdf/Form_Evaluation_1.pdf");
            input = resource.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Load editable pdf file
        try (PDDocument pdfDoc = PDDocument.load(input)) {
            PDPage firstPage = pdfDoc.getPage(0);
//            pdfDoc.importPage(firstPage);

            int pageHeight = (int) firstPage.getTrimBox().getHeight();
            int pageWidth = (int) firstPage.getTrimBox().getWidth();

            int fontSize = 10;

            PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, firstPage, PDPageContentStream.AppendMode.APPEND, true, true);

            float[] components = new float[]{
                    0f, 0f, 0f};
            PDColor pdColor = new PDColor(components, PDDeviceRGB.INSTANCE);

            PDRectangle mediaBox = firstPage.getMediaBox();
            float marginY = 80;
            float marginX = 80;
            float width = mediaBox.getWidth() - 2 * marginX;
            float startX = mediaBox.getLowerLeftX() + marginX;
            float startY = mediaBox.getUpperRightY() - marginY;

            contentStream.setStrokingColor(pdColor);
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(startX, startY - 170, width, 70);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
            contentStream.newLineAtOffset(240, startY - 80);
            contentStream.showText("Form Evaluasi - " + numEval);

            contentStream.newLineAtOffset(-240, -(startY - 80));

            contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);

            contentStream.newLineAtOffset(startX + 10, startY - 120);
            contentStream.showText("Nama Perusahaan");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(":  " + companyName);

            contentStream.newLineAtOffset(-120, -20);
            contentStream.showText("Alamat");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(":  " + alamat);

            contentStream.newLineAtOffset(-120, -20);
            contentStream.showText("Kontak Person/ email/ Telp.");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(":  " + cpName + "/" + cpEmail + "/" + cpPhone);
            contentStream.newLineAtOffset(-1 * (startX + 10) - 120, -30);

            contentStream.newLineAtOffset(startX, 0);

            String text = "Evaluasi yang Bapak/ Ibu berikan akan sangat membantu kami dalam memperbaiki sistem pengajaran " +
                    "maupun program pada masa yang akan datang. Mohon kesediaannya untuk mengevaluasi peserta " + PDFUtil.remove(headerWords) +
                    " Teknik Informatika JTK yang ada dalam lingkungan unit Bapak/ Ibu dengan melingkari pilihan yang sesuai, " +
                    "yaitu :";

            PDFUtil.addParagraph(contentStream, width, 0, 0, text, true, 20);

            contentStream.setFont(PDType1Font.TIMES_BOLD, 11);

            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("A:     Baik Sekali");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("B:     Baik");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("C:     Cukup");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("D:     Kurang");
            contentStream.newLineAtOffset(-360, 0);

            contentStream.newLineAtOffset(0, -12);
            contentStream.showText("          (85 - 100)");

            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("          (75 - 84)");

            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("          (65 - 74)");

            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("          (55 - 64)");
            contentStream.newLineAtOffset(-360, 0);

            contentStream.endText();

            boolean drawContent = true;
            float bottomMargin = 70;
            float topMargin = 80;
            // y position is your coordinate of top left corner of the table
            float yPosition = startY - 300; //300

            BaseTable table = new BaseTable(yPosition, startY,
                    topMargin, bottomMargin, width, marginX, pdfDoc, firstPage, true, drawContent);

            Cell<PDPage> cell;
            // the parameter is the row height

            Row<PDPage> headerRow = table.createRow(12);
            // the first parameter is the cell width
            cell = headerRow.createCell(10, "No");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(fontSize);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell = headerRow.createCell(70, "Aspek Penilaian");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(fontSize);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell = headerRow.createCell(20, "Nilai");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(fontSize);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            Row<PDPage> row;
            int num = 1;
            for(int i = 0; i < valuation.size(); i++) {
                if (Boolean.FALSE.equals(valuation.get(i).getIsCore())){
                    continue;
                }
                row = table.createRow(11);
                cell = row.createCell(10, String.valueOf(num));
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(fontSize);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setAlign(HorizontalAlignment.CENTER);
                cell.setLineSpacing(1.1f);

                String aspectName = PDFUtil.remove(valuation.get(i).getAspectName());
                cell = row.createCell(70, aspectName);
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(fontSize);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);

                String aspectValuation = PDFUtil.remove(String.valueOf(valuation.get(i).getValue()));
                cell = row.createCell(20, aspectValuation);
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(fontSize);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setAlign(HorizontalAlignment.CENTER);
                cell.setLineSpacing(1.1f);
                num++;
            }

            if (numEval == 3) {
                row = table.createRow(11);
                cell = row.createCell(10, String.valueOf(num));
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(fontSize);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setAlign(HorizontalAlignment.CENTER);
                cell.setLineSpacing(1.1f);

                cell = row.createCell(90, "Kemampuan lain");
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(fontSize);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setAlign(HorizontalAlignment.LEFT);
                cell.setLineSpacing(1.1f);

                char numChar = 'a';
                for (int j = 0; j < valuation.size(); j++) {
                    if (Boolean.TRUE.equals(valuation.get(j).getIsCore())){
                        continue;
                    }
                    row = table.createRow(11);
                    cell = row.createCell(10, "");
                    cell.setFont(PDType1Font.TIMES_BOLD);
                    cell.setFontSize(fontSize);
                    cell.setValign(VerticalAlignment.MIDDLE);
                    cell.setAlign(HorizontalAlignment.CENTER);
                    cell.setLineSpacing(1.1f);

                    String aspectName = PDFUtil.remove(numChar+". "+valuation.get(j).getAspectName());
                    cell = row.createCell(70, aspectName);
                    cell.setFont(PDType1Font.TIMES_BOLD);
                    cell.setFontSize(fontSize);
                    cell.setValign(VerticalAlignment.MIDDLE);
                    cell.setLineSpacing(1.1f);

                    String aspectValuation = PDFUtil.remove(String.valueOf(valuation.get(j).getValue()));
                    cell = row.createCell(20, aspectValuation);
                    cell.setFont(PDType1Font.TIMES_BOLD);
                    cell.setFontSize(fontSize);
                    cell.setValign(VerticalAlignment.MIDDLE);
                    cell.setAlign(HorizontalAlignment.CENTER);
                    cell.setLineSpacing(1.1f);
                    numChar++;
                }
            }

            float lastY = table.draw();

            if (table.getCurrentPage() != firstPage) {
                contentStream.close();
                PDPage page = table.getCurrentPage();
                contentStream = new PDPageContentStream(pdfDoc, page, PDPageContentStream.AppendMode.APPEND, true, true);
            }

            String excellent = "";
            String worst = "";
            List<String> listString = PDFUtil.parseLines("Catatan/Masukan : " + comment, width - 20);

            int sizeBox;
            if (listString.size() > 4) {
                sizeBox = 14 * listString.size();
            } else {
                sizeBox = 70;
            }

            List<String> excellentLine = null;
            if (numEval == 3) {
                String[] arr = comment.split("\\|", 2);
                excellent = arr[0];
                worst = arr[1];

                excellentLine = PDFUtil.parseLines("Kemampuan yang paling excellent: " + excellent, width - 20);
                List<String> worstLine = PDFUtil.parseLines("Kemampuan yang paling worst: " + worst, width - 20);

                sizeBox = 14 * (4 + excellentLine.size() + worstLine.size());
            }

            if (marginY < lastY - (sizeBox + 22)) {
                contentStream.setStrokingColor(pdColor);
                contentStream.setLineWidth(0.5f);
                contentStream.addRect(startX, lastY - (sizeBox + 22), width, sizeBox);
                contentStream.stroke();

                contentStream.beginText();
                if (numEval == 3) {
                    PDFUtil.addParagraph(contentStream, width - 20, startX, lastY - 18, "Catatan/Masukan : ", true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 4, -4, "Kemampuan yang paling excellent: " + excellent, true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 0, -14 * excellentLine.size(), "Kemampuan yang paling lemah: " + worst, true, 0);
                } else {
                    PDFUtil.addParagraph(contentStream, width - 20, startX, lastY - 18, "Catatan/Masukan : ", true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 4, -4, comment, true, 0);
                }

                lastY = lastY - (sizeBox + 22);
            } else {
                PDPage blankPage = new PDPage(PDRectangle.A4);
                pdfDoc.addPage(blankPage);
                contentStream.close();
                contentStream = new PDPageContentStream(pdfDoc, blankPage, PDPageContentStream.AppendMode.APPEND, true, true);

                contentStream.setStrokingColor(pdColor);
                contentStream.setLineWidth(0.5f);
                contentStream.addRect(startX, startY - (sizeBox + 10), width, sizeBox);
                contentStream.stroke();

                contentStream.beginText();


                if (numEval == 3) {
                    PDFUtil.addParagraph(contentStream, width - 20, startX, startY - 5, "Catatan/Masukan : ", true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 4, -4, "Kemampuan yang paling excellent: " + excellent, true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 0, -14 * excellentLine.size(), "Kemampuan yang paling lemah: " + worst, true, 0);
                } else {
                    PDFUtil.addParagraph(contentStream, width - 20, startX, startY - 8, "Catatan/Masukan : ", true, 0);
                    PDFUtil.addParagraph(contentStream, width - 20, 4, -4, comment, true, 0);
                }
                lastY = startY - (sizeBox + 10);
            }
            contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
            contentStream.endText();

            contentStream.beginText();

            String[] domicileSplit = domicile.split(" ");

            StringBuilder domicileDate = null;
            for (int i = 0; i < domicileSplit.length; i++) {
                if (i != 0) {
                    if (i == 1) {
                        domicileDate = new StringBuilder(domicileSplit[i]);
                    } else {
                        if (domicileDate != null) {
                            domicileDate.append(" " + domicileSplit[i]);
                        }
                    }
                }
            }

            if (marginY > lastY - 20) {
                PDPage blankPage = new PDPage(PDRectangle.A4);
                pdfDoc.addPage(blankPage);
                contentStream.close();
                contentStream = new PDPageContentStream(pdfDoc, blankPage, PDPageContentStream.AppendMode.APPEND, true, true);

                lastY = startY;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
            }

            domicileDate.append(", " + DateUtil.getDateIdNow());
            contentStream.newLineAtOffset(startX + width - domicileDate.length() - marginX, lastY - 20);
            contentStream.showText(domicileDate.toString());
            contentStream.endText();

            lastY -= 20;

            sizeBox = 70;
            contentStream.setStrokingColor(pdColor);
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(startX, lastY - (sizeBox + 10), width, sizeBox);
            contentStream.stroke();

            contentStream.setStrokingColor(pdColor);
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(startX + width - 140, lastY - (sizeBox + 10), 140, sizeBox);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(startX + 10, lastY - 25);
            contentStream.showText("Nama Peserta KP");

            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(": " + participantName);

            contentStream.newLineAtOffset(-120, -20);
            contentStream.showText("Mulai KP");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(": " + DateUtil.parseDateToStringIdNow(DateUtil.stringToDate(startTime)));


            contentStream.newLineAtOffset(-120, -20);
            contentStream.showText("Posisi saat KP");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText(": " + position);
            contentStream.newLineAtOffset(-120 - (startX + 10), 0);

            float size = fontSize * PDType1Font.TIMES_ROMAN.getStringWidth(companyName) / 1000;

            float midTtd = ((startX + width - 140) + (startX + width)) / 2;
            contentStream.newLineAtOffset(midTtd - (size / 2), 42);
            contentStream.showText(companyName);
            contentStream.newLineAtOffset(-(midTtd - (size / 2)), 0);

            size = fontSize * PDType1Font.TIMES_ROMAN.getStringWidth(advisorPosition) / 1000;
            contentStream.newLineAtOffset(midTtd - (size / 2), -11);
            contentStream.showText(advisorPosition);

            contentStream.newLineAtOffset(-(midTtd - (size / 2)), 0);
            size = fontSize * PDType1Font.TIMES_ROMAN.getStringWidth(advisorName) / 1000;
            contentStream.newLineAtOffset(midTtd - (size / 2), -36);
            contentStream.showText(advisorName);


            contentStream.endText();
            contentStream.close();


            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Form_Evaluation_" + numEval + ".pdf");
            PDDocumentInformation info = new PDDocumentInformation();
            info.setCreationDate(Calendar.getInstance());
            info.setModificationDate(Calendar.getInstance());
            info.setTitle("Form Evaluasi " + numEval);

            pdfDoc.save(outputStream);
            pdfDoc.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EvaluationFormResponse getEvaluationForm(Integer idParticipant, Integer idProdi, Integer numEvaluation, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<String>> assessmentAspects =
                restTemplate.exchange(
                        "http://management-content-service/management-content/assessment-aspect/get-form?prodi=" + idProdi + "&numeval=" + numEvaluation,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<>() {
                        });

        List<String> assessmentAspectList = Objects.requireNonNull(assessmentAspects.getBody()).getData();

        Evaluation evaluation = evaluationRepository.findByIdParticipantAndNumEvaluation(idParticipant, numEvaluation);

        EvaluationFormResponse evaluationFormResponse = new EvaluationFormResponse();
        companyRepository.findById(evaluation.getIdCompany()).ifPresent(c -> {
            evaluationFormResponse.setCompanyName(c.getCompanyName());
            evaluationFormResponse.setCompanyAddress(c.getAddress());
            evaluationFormResponse.setCpEmail(c.getCpEmail());
            evaluationFormResponse.setCpName(c.getCpName());
            evaluationFormResponse.setCpPhone(c.getCpPhone());
            evaluationFormResponse.setAssessmentAspect(assessmentAspectList);
        });

        int idFormSubmit = 11; // Untuk D3

        if (idProdi == EProdi.D4.id) {
            idFormSubmit = 12;
        }

        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitTimeResponse =
                restTemplate.exchange(
                        "http://management-content-service/management-content/form-submit-time/" + idFormSubmit,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<>() {
                        });

        FormSubmitTimeResponse formSubmit = Objects.requireNonNull(formSubmitTimeResponse.getBody()).getData();
        evaluationFormResponse.setStartDate(formSubmit.getStartDate());

        return evaluationFormResponse;
    }

    @Override
    public Boolean changeStatus(String cookie, Integer idCompany) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        AtomicReference<Boolean> isSuccess = new AtomicReference<>(false);

        companyRepository.findById(idCompany).ifPresent(c -> {
            if (!c.getStatus() == Boolean.TRUE.equals(Boolean.TRUE)) {
                Prerequisite prerequisite = prerequisiteRepository.findByCompanyIdAndYear(idCompany, currentYear);
                if (prerequisite == null) {
                    Prerequisite pr = new Prerequisite();
                    pr.setYear(Calendar.getInstance().get(Calendar.YEAR));
                    pr.setStatus(Boolean.FALSE);
                    pr.setCompany(c);
                    prerequisiteRepository.save(pr);
                }
                isSuccess.set(true);
            } else if (!c.getStatus() == Boolean.TRUE.equals(Boolean.FALSE)) {
                ResponseEntity<Response<FormSubmitTimeResponse>> deleteCompany =
                        restTemplate.exchange(
                                "http://mapping-service/mapping/final/delete-company/" + idCompany,
                                HttpMethod.DELETE,
                                req,
                                new ParameterizedTypeReference<>() {
                                });
                if (deleteCompany.getStatusCode().is2xxSuccessful()) {
                    evaluationRepository.deleteAllByIdCompanyAndYear(idCompany, currentYear);
                    isSuccess.set(true);
                }
            }
            if (Boolean.TRUE.equals(isSuccess.get())) {
                c.setStatus(!c.getStatus());
                companyRepository.save(c);
            }
        });
        return isSuccess.get();
    }

    @Override
    public List<PrerequisiteRecapResponse> recapPrerequisite(String cookie, Integer idProdi) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<JobscopeResponse>> jobsScopeList = restTemplate.exchange("http://management-content-service/management-content/jobscope/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        ResponseEntity<ResponseList<CompetenceResponse>> competenceList = restTemplate.exchange("http://management-content-service/management-content/competence/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        List<JobscopeResponse> jobScopeList = Objects.requireNonNull(jobsScopeList.getBody()).getData();
        List<CompetenceResponse> competenceResponseList = Objects.requireNonNull(competenceList.getBody()).getData();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<PrerequisiteCompetenceRecap> prerequisiteCompetenceRecapList = prerequisiteCompetenceRepository.findRecap(currentYear, idProdi);
        List<PrerequisiteJobScopeRecap> prerequisiteJobScopeRecapList = prerequisiteJobscopeRepository.findRecap(currentYear, idProdi);

        List<PrerequisiteRecapResponse> prerequisiteRecapResponseList = new ArrayList<>();
        AtomicReference<Integer> id = new AtomicReference<>(1);

        for (PrerequisiteJobScopeRecap prerequisiteJobScopeRecap : prerequisiteJobScopeRecapList) {
            jobScopeList.stream().filter(j -> Objects.equals(j.getId(), prerequisiteJobScopeRecap.getId())).findFirst().ifPresent(j -> {
                PrerequisiteRecapResponse prerequisiteRecapResponse = new PrerequisiteRecapResponse();
                prerequisiteRecapResponse.setName(j.getName());
                prerequisiteRecapResponse.setTotal(prerequisiteJobScopeRecap.getTotal());
                prerequisiteRecapResponse.setType(0);
                prerequisiteRecapResponse.setId(id.get());

                id.updateAndGet(v -> v + 1);
                prerequisiteRecapResponseList.add(prerequisiteRecapResponse);
            });
        }

        for (PrerequisiteCompetenceRecap prerequisiteCompetenceRecap : prerequisiteCompetenceRecapList) {
            competenceResponseList.stream().filter(c -> Objects.equals(c.getId(), prerequisiteCompetenceRecap.getId())).findFirst().ifPresent(c -> {
                PrerequisiteRecapResponse prerequisiteRecapResponse = new PrerequisiteRecapResponse();
                prerequisiteRecapResponse.setName(c.getName());
                prerequisiteRecapResponse.setTotal(prerequisiteCompetenceRecap.getTotal());
                prerequisiteRecapResponse.setType(c.getType());
                prerequisiteRecapResponse.setId(id.get());

                id.updateAndGet(v -> v + 1);
                prerequisiteRecapResponseList.add(prerequisiteRecapResponse);
            });
        }
        return prerequisiteRecapResponseList;
    }

    @Override
    public EvaluationTableResponse getEvaluationTableByCommittee(Integer idProdi, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        // Get all participant (Participant)
        ResponseEntity<ResponseList<ParticipantIdName>> participantList =
                restTemplate.exchange(
                        "http://participant-service/participant/get-all?type=dropdown",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<ResponseList<ParticipantIdName>>() {
                        });

        if (participantList.getStatusCode().is4xxClientError() || participantList.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting submit timeline");
        }
        List<ParticipantIdName> pinList = Objects.requireNonNull(participantList.getBody()).getData();
        int participantTotal = pinList.size();

        List<ParticipantIdNameStatus> pinsList = new ArrayList<>();

        // Get is final mapping (Mapping)
        int id = 1;

        if (idProdi == EProdi.D4.id) {
            id = 2;
        }

        ResponseEntity<Response<Integer>> isFinalMappingD3Request =
                restTemplate.exchange(
                        "http://mapping-service/mapping/get-is-final/" + id,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<Integer>>() {
                        });

        if (isFinalMappingD3Request.getStatusCode().is4xxClientError() || isFinalMappingD3Request.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting is final mapping D3");
        }

        Integer isFinalMapping = Objects.requireNonNull(isFinalMappingD3Request.getBody()).getData();

        EvaluationTableResponse res = new EvaluationTableResponse();

        if (isFinalMapping == 0) {
            if (idProdi == EProdi.D3.id) {
                res.getTotalSubmitted().add(0);
                res.getTotalNotSubmitted().add(participantTotal);

                pinList.forEach(p -> {
                    ParticipantIdNameStatus pins = new ParticipantIdNameStatus();
                    pins.setId(p.getId());
                    pins.setName(p.getName());
                    pins.getStatusEvaluated().add(0);
                    pinsList.add(pins);
                });
            }

            if (idProdi == EProdi.D4.id) {
                res.getTotalSubmitted().add(0);
                res.getTotalNotSubmitted().add(participantTotal);

                res.getTotalSubmitted().add(0);
                res.getTotalNotSubmitted().add(participantTotal);

                res.getTotalSubmitted().add(0);
                res.getTotalNotSubmitted().add(participantTotal);

                pinList.forEach(p -> {
                    ParticipantIdNameStatus pins = new ParticipantIdNameStatus();
                    pins.setId(p.getId());
                    pins.setName(p.getName());
                    pins.getStatusEvaluated().add(0);
                    pins.getStatusEvaluated().add(0);
                    pins.getStatusEvaluated().add(0);
                    pinsList.add(pins);
                });
            }
        } else {
            Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

            pinList.forEach(p -> {
                ParticipantIdNameStatus pins = new ParticipantIdNameStatus();
                pins.setId(p.getId());
                pins.setName(p.getName());
                pinsList.add(pins);
            });

            if (idProdi == EProdi.D3.id) {
                // participant's status
                List<Evaluation> eList = evaluationRepository.findByYearAndNumEvaluationAndIdProdi(currentYear, 1, 0);
                eList.forEach(e -> {
                    pinsList.forEach(p -> {
                        if (Objects.equals(p.getId(), e.getIdParticipant())) {
                            p.getStatusEvaluated().add(e.getStatus());
                        }
                    });
                });

                // evaluation's count
                res.getTotalSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 1, currentYear, 1));
                res.getTotalNotSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 1, currentYear, 0));
            }

            if (idProdi == EProdi.D4.id) {
                // participant's status
                List<Evaluation> eList = evaluationRepository.findByYearAndNumEvaluationAndIdProdi(currentYear, 1, 1);
                eList.forEach(e -> {
                    pinsList.forEach(p -> {
                        if (Objects.equals(p.getId(), e.getIdParticipant())) {
                            p.getStatusEvaluated().add(e.getStatus());
                        }
                    });
                });

                eList = evaluationRepository.findByYearAndNumEvaluationAndIdProdi(currentYear, 2, 1);
                eList.forEach(e -> {
                    pinsList.forEach(p -> {
                        if (Objects.equals(p.getId(), e.getIdParticipant())) {
                            p.getStatusEvaluated().add(e.getStatus());
                        }
                    });
                });

                eList = evaluationRepository.findByYearAndNumEvaluationAndIdProdi(currentYear, 3, 1);
                eList.forEach(e -> {
                    pinsList.forEach(p -> {
                        if (Objects.equals(p.getId(), e.getIdParticipant())) {
                            p.getStatusEvaluated().add(e.getStatus());
                        }
                    });
                });

                // evaluation's count
                res.getTotalSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 1, currentYear, 1));
                res.getTotalNotSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 1, currentYear, 0));

                res.getTotalSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 2, currentYear, 1));
                res.getTotalNotSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 2, currentYear, 0));

                res.getTotalSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 3, currentYear, 1));
                res.getTotalNotSubmitted().add(
                        evaluationRepository.countByIdProdiAndNumEvaluationAndYearAndStatus(idProdi, 3, currentYear, 0));
            }
        }

        res.setParticipantList(pinsList);

        return res;
    }

    @Override
    public FeedbackCardResponse getFeedbackCardByCompany(Integer idCompany, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        FeedbackCardResponse fcr = new FeedbackCardResponse();
        fcr.setIdCompany(idCompany);

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // D3
        Feedback f = feedbackRepository.findByYearAndIdCompanyAndIdProdi(
                currentYear, idCompany, 0);

        if (f != null) {
            // Get submit timeline D3 (Management Content)
            ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitD3 =
                    restTemplate.exchange(
                            "http://management-content-service/management-content/form-submit-time/9",
                            HttpMethod.GET,
                            req,
                            new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                            });

            if (formSubmitD3.getStatusCode().is4xxClientError() || formSubmitD3.getStatusCode().is5xxServerError()) {
                throw new IllegalStateException("Error when getting submit timeline");
            }

            String startDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getStartDate();
            String endDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getEndDate();
            Boolean isDateAvailableD3 = DateUtil.checkNowDate(startDate, endDate);

            fcr.setIsAllowedD3(true);
            fcr.setIsMoreThanTimelineD3(!isDateAvailableD3);
            fcr.setStatusSubmitD3(f.getStatus());
        }

        // D4
        f = feedbackRepository.findByYearAndIdCompanyAndIdProdi(
                currentYear, idCompany, 1);

        if (f != null) {
            // Get submit timeline D4 (Management Content)
            ResponseEntity<Response<FormSubmitTimeResponse>> formSubmitD3 =
                    restTemplate.exchange(
                            "http://management-content-service/management-content/form-submit-time/10",
                            HttpMethod.GET,
                            req,
                            new ParameterizedTypeReference<Response<FormSubmitTimeResponse>>() {
                            });

            if (formSubmitD3.getStatusCode().is4xxClientError() || formSubmitD3.getStatusCode().is5xxServerError()) {
                throw new IllegalStateException("Error when getting submit timeline");
            }

            String startDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getStartDate();
            String endDate = Objects.requireNonNull(formSubmitD3.getBody()).getData().getEndDate();
            Boolean isDateAvailableD4 = DateUtil.checkNowDate(startDate, endDate);

            fcr.setIsAllowedD4(true);
            fcr.setIsMoreThanTimelineD4(!isDateAvailableD4);
            fcr.setStatusSubmitD4(f.getStatus());
        }

        return fcr;
    }

    @Override
    public FeedbackTableResponse getFeedbackTableByCommittee(Integer idProdi, String cookie) {
        FeedbackTableResponse ftr = new FeedbackTableResponse();

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        // Get is publish mapping (Mapping)
        int id = 3;

        if (idProdi == EProdi.D4.id) {
            id = 4;
        }

        ResponseEntity<Response<Integer>> isFinalMappingRequest =
                restTemplate.exchange(
                        "http://mapping-service/mapping/get-is-final/" + id,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<Integer>>() {
                        });

        if (isFinalMappingRequest.getStatusCode().is4xxClientError() || isFinalMappingRequest.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting is final mapping");
        }

        Integer isFinalMapping = Objects.requireNonNull(isFinalMappingRequest.getBody()).getData();

        if (isFinalMapping.equals(0)) {
            return ftr;
        }

        ftr.setIsPublished(true);

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Feedback> fList = feedbackRepository.findByYearAndIdProdi(currentYear, idProdi);
        List<Company> cList = companyRepository.findByStatus(true);

        for (Feedback f : fList) {
            CompanyIdNameStatus cins = new CompanyIdNameStatus();
            cins.setId(f.getIdCompany());
            cins.setStatusFeedback(f.getStatus());

            for (Company c : cList) {
                if (c.getId().equals(f.getIdCompany())) {
                    cins.setName(c.getCompanyName());
                }
            }

            ftr.getCompanyList().add(cins);
        }

        Integer totalSubmitted = feedbackRepository.countByIdProdiAndYearAndStatus(idProdi, currentYear, 1);
        ftr.setTotalSubmitted(totalSubmitted);

        Integer totalNotSubmitted = feedbackRepository.countByIdProdiAndYearAndStatus(idProdi, currentYear, 0);
        ftr.setTotalNotSubmitted(totalNotSubmitted);

        return ftr;
    }

    @Override
    public List<UpdateFeedbackRequest> getFeedbackDetail(Integer idCompany, Integer idProdi) {
        List<UpdateFeedbackRequest> ufrList = new ArrayList<>();
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Feedback f = feedbackRepository.findByYearAndIdCompanyAndIdProdi(
                currentYear, idCompany, idProdi);

        List<FeedbackAnswer> faList = feedbackAnswerRepository.findByFeedbackId(f.getId());
        for (FeedbackAnswer fa : faList) {
            UpdateFeedbackRequest ufr = new UpdateFeedbackRequest();
            ufr.setQuestion(fa.getQuestion());
            ufr.setValue(fa.getValue());
            ufrList.add(ufr);
        }

        return ufrList;
    }

    @Override
    public void createFeedback(List<CreateFeedbackRequest> createFeedbackRequest) {
        for (CreateFeedbackRequest cfr : createFeedbackRequest) {
            Feedback f = new Feedback();
            f.setIdCompany(cfr.getIdCompany());
            f.setIdProdi(cfr.getIdProdi());
            feedbackRepository.save(f);
        }
    }

    @Override
    public void updateFeedback(Integer idCompany, Integer idProdi, List<UpdateFeedbackRequest> updateFeedbackRequest) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Feedback f = feedbackRepository.findByYearAndIdCompanyAndIdProdi(
                currentYear, idCompany, idProdi);

        if (f != null) {
            for (UpdateFeedbackRequest ufr : updateFeedbackRequest) {
                FeedbackAnswer fa = new FeedbackAnswer();
                fa.setFeedback(f);
                fa.setQuestion(ufr.getQuestion());
                fa.setValue(ufr.getValue());
                feedbackAnswerRepository.save(fa);
            }
        }

        f.setStatus(1);
        feedbackRepository.save(f);
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void createNullPrerequisiteJob() {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Company> cList = companyRepository.findByStatus(true);

        for (Company c : cList) {
            Prerequisite prerequisite = new Prerequisite();
            prerequisite.setRegionId(0);
            prerequisite.setYear(currentYear);
            prerequisite.setStatus(Boolean.FALSE);
            prerequisite.setCompany(c);
            prerequisiteRepository.save(prerequisite);
        }
    }
}
