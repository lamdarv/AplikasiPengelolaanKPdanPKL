package com.jtk.ps.api.service;

import be.quodlibet.boxable.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.cv.*;
import com.jtk.ps.api.dto.jsonpolban.CourseCodeValue;
import com.jtk.ps.api.dto.jsonpolban.ParticipantJsonResponse;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.DateUtil;
import com.jtk.ps.api.util.PDFUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ParticipantService implements IParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private ParticipantCompanyRepository participantCompanyRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CVCompetenceRepository cvCompetenceRepository;

    @Autowired
    private CVJobScopeRepository cvJobScopeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private CourseValueRepository courseValueRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AbsenceRecapRepository absenceRecapRepository;

    @Override
    public List<ParticipantIdName> getNameAndIdCompanies(Integer idProdi) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Participant> pList = participantRepository.findByYearAndProdiId(currentYear, idProdi);

        List<ParticipantIdName> pinList = new ArrayList<>();
        pList.forEach(p -> {
            ParticipantIdName pin = new ParticipantIdName();
            pin.setId(p.getId());
            pin.setName(p.getName());
            pinList.add(pin);
        });

        return pinList;
    }

    @Override
    public List<Participant> getParticipantById(List<Integer> id) {
        return participantRepository.findByIdIn(id);
    }

    @Override
    public List<Participant> getParticipantByYear(Integer year, Integer prodiId) {
        if (year == null) {
            return participantRepository.findAll();
        }
        return participantRepository.findByYearAndProdiId(year, prodiId);
    }

    @Override
    public List<Participant> getParticipantByAccountId(List<Integer> accountId) {
        return participantRepository.findByAccountIdIn(accountId);
    }

    @Override
    public CVParticipantResponse getCVParticipant(Integer idParticipant, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmit = restTemplate.exchange("http://management-content-service/management-content/form-submit-time/2", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        String startDate = Objects.requireNonNull(formSubmit.getBody()).getData().getStartDate();
        String endDate = Objects.requireNonNull(formSubmit.getBody()).getData().getEndDate();

        Optional<Participant> oParticipant = participantRepository.findById(idParticipant);

        CVParticipantResponse cvParticipantResponse = new CVParticipantResponse();
        oParticipant.ifPresent(participant -> {
            cvParticipantResponse.setStatusUpdate(DateUtil.checkNowDate(startDate, endDate));
            cvParticipantResponse.setIdCV(participant.getCv().getId());
            cvParticipantResponse.setStatusSubmit(participant.getStatusCv());
        });

        return cvParticipantResponse;
    }

    @Override
    public CVCommitteeResponse getCVParticipantByCommittee(Integer prodiId) {
        List<Participant> participant = participantRepository.findByProdiId(prodiId);

        List<CVCommittee> cvCommittee = new ArrayList<>();
        int totalSubmitted = 0;
        int totalNotSubmitted = 0;
        for (Participant p : participant) {
            CVCommittee cv = new CVCommittee();
            cv.setName(p.getName());
            cv.setNim(p.getId());
            cv.setStatusCv(p.getStatusCv());
            cv.setIdCv(p.getCv().getId());
            cvCommittee.add(cv);

            if (Objects.equals(p.getStatusCv(), Constant.StatusCV.DONE)) {
                totalSubmitted++;
            } else {
                totalNotSubmitted++;
            }
        }

        CVCommitteeResponse cvCommitteeResponse = new CVCommitteeResponse();
        cvCommitteeResponse.setCvCommittee(cvCommittee);
        cvCommitteeResponse.setTotalSubmitted(totalSubmitted);
        cvCommitteeResponse.setTotalNotSubmitted(totalNotSubmitted);

        return cvCommitteeResponse;
    }

    @Override
    public CVGetResponse getCVDetail(Integer idCv, String cookie) {
        CV cv = cvRepository.findById(idCv).orElse(null);
        if (cv != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
            HttpEntity<String> req = new HttpEntity<>(headers);

            List<Education> educations = educationRepository.findByCvId(idCv);
            List<Experience> experiences = experienceRepository.findByCvId(idCv);
            List<Organization> organizations = organizationRepository.findByCvId(idCv);
            List<CVJobScope> cvJobScopes = cvJobScopeRepository.findByCvId(idCv);
            List<Skill> skills = skillRepository.findByCvId(idCv);
            List<Seminar> seminars = seminarRepository.findByCvId(idCv);
            List<Championship> championships = championshipRepository.findByCvId(idCv);

            List<CVCompetence> cvCompetencies = cvCompetenceRepository.findByCvId(idCv);
            List<CVCompetenceAndType> cvCompetenceAndTypes = new ArrayList<>();
            for (CVCompetence cvCompetence : cvCompetencies) {
                CVCompetenceAndType cvCompetenceAndType = new CVCompetenceAndType();
                cvCompetenceAndType.setId(cvCompetence.getId());
                cvCompetenceAndType.setKnowledgeId(cvCompetence.getKnowledgeId());
                cvCompetenceAndType.setCompetenceId(cvCompetence.getCompetenceId());

                ResponseEntity<Response<Integer>> typeId = restTemplate.exchange("http://management-content-service/management-content/competence/get-type?id=" + cvCompetence.getCompetenceId(),
                        HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                        });

                cvCompetenceAndType.setCompetenceType(Objects.requireNonNull(typeId.getBody()).getData());
                cvCompetenceAndTypes.add(cvCompetenceAndType);
            }

            CVGetResponse cvGetResponse = new CVGetResponse();
            cvGetResponse.setEducations(educations);
            cvGetResponse.setExperiences(experiences);
            cvGetResponse.setOrganizations(organizations);
            cvGetResponse.setCompetencies(cvCompetenceAndTypes);
            cvGetResponse.setJobScopes(cvJobScopes);
            cvGetResponse.setSkills(skills);
            cvGetResponse.setSeminars(seminars);
            cvGetResponse.setChampionships(championships);

            cvGetResponse.setAddress(cv.getAddress());
            cvGetResponse.setBirthday(DateUtil.parseDateToString(cv.getBirthday()));
            cvGetResponse.setEmail(cv.getEmail());
            cvGetResponse.setGender(cv.getGender());
            cvGetResponse.setId(cv.getId());
            cvGetResponse.setCitizenship(cv.getCitizenship());
            cvGetResponse.setReligion(cv.getReligion());
            cvGetResponse.setMarriage(cv.getMarriage());
            cvGetResponse.setNickname(cv.getNickname());
            cvGetResponse.setNoPhone(cv.getNoPhone());
            cvGetResponse.setPlace(cv.getPlace());
            cvGetResponse.setDomicileId(cv.getDomicileId());

            Participant participant = participantRepository.findByCvId(idCv);
            cvGetResponse.setName(participant.getName());

            return cvGetResponse;
        }
        return null;
    }

    @Override
    public Boolean updateCV(Integer idCv, CVUpdateRequest cvUpdateRequest, Integer idParticipant) {
        Optional<Participant> oParticipant = participantRepository.findById(idParticipant);
        AtomicBoolean isUpdate = new AtomicBoolean(false);
        oParticipant.ifPresent(participant -> {
            if (participant.getCv().getId() != idCv) {
                return;
            }
            Optional<CV> cv = cvRepository.findById(idCv);
            cv.ifPresent(c -> {
                c.setAddress(cvUpdateRequest.getAddress());
                c.setBirthday(DateUtil.stringToDate(cvUpdateRequest.getBirthday()));
                c.setCitizenship(cvUpdateRequest.getCitizenship());
                c.setEmail(cvUpdateRequest.getEmail());
                c.setDomicileId(cvUpdateRequest.getDomicileId());
                c.setGender(cvUpdateRequest.getGender());
                c.setMarriage(cvUpdateRequest.getMarriage());
                c.setNickname(cvUpdateRequest.getNickname());
                c.setNoPhone(cvUpdateRequest.getNoPhone());
                c.setPlace(cvUpdateRequest.getPlace());
                c.setReligion(cvUpdateRequest.getReligion());

                educationRepository.deleteAllByCvId(idCv);

                List<Education> educations = cvUpdateRequest.getEducations();
                if (educations != null) {
                    educations.forEach(education -> education.setCv(c));
                    educationRepository.saveAll(educations);
                }

                experienceRepository.deleteAllByCvId(idCv);
                List<Experience> experiences = cvUpdateRequest.getExperiences();
                if (experiences != null) {
                    experiences.forEach(experience -> experience.setCv(c));
                    experienceRepository.saveAll(experiences);
                }


                organizationRepository.deleteAllByCvId(idCv);
                List<Organization> organizations = cvUpdateRequest.getOrganizations();
                if (organizations != null) {
                    organizations.forEach(organization -> organization.setCv(c));
                    organizationRepository.saveAll(organizations);
                }


                cvCompetenceRepository.deleteAllByCvId(idCv);
                List<CVCompetence> cvCompetencies = cvUpdateRequest.getCompetencies();
                if (cvCompetencies != null) {
                    cvCompetencies.forEach(cvCompetence -> cvCompetence.setCv(c));
                    cvCompetenceRepository.saveAll(cvCompetencies);
                }

                cvJobScopeRepository.deleteAllByCvId(idCv);
                List<CVJobScope> cvJobScopes = cvUpdateRequest.getJobscopes();
                if (cvJobScopes != null) {
                    cvJobScopes.forEach(cvJobScope -> cvJobScope.setCv(c));
                    cvJobScopeRepository.saveAll(cvJobScopes);
                }

                skillRepository.deleteAllByCvId(idCv);
                List<Skill> skills = cvUpdateRequest.getSkills();
                if (skills != null) {
                    skills.forEach(skill -> skill.setCv(c));
                    skillRepository.saveAll(skills);
                }

                seminarRepository.deleteAllByCvId(idCv);
                List<Seminar> seminars = cvUpdateRequest.getSeminars();
                if (seminars != null) {
                    seminars.forEach(seminar -> seminar.setCv(c));
                    seminarRepository.saveAll(seminars);
                }

                championshipRepository.deleteAllByCvId(idCv);
                List<Championship> championships = cvUpdateRequest.getChampionships();
                if (championships != null) {
                    championships.forEach(championship -> championship.setCv(c));
                    championshipRepository.saveAll(championships);
                }

                cvRepository.save(c);
                isUpdate.set(true);
            });
        });
        return isUpdate.get();
    }

    @Override
    public Boolean markAsDoneCv(Integer idParticipant) {
        Optional<Participant> oParticipant = participantRepository.findById(idParticipant);
        AtomicBoolean isUpdate = new AtomicBoolean(false);

        oParticipant.ifPresent(participant -> {
            participant.setStatusCv(!participant.getStatusCv());
            participantRepository.save(participant);
            isUpdate.set(true);
        });
        return isUpdate.get();
    }

    @Override
    public Boolean markAsDoneInterest(Integer idParticipant) {
        Optional<Participant> oParticipant = participantRepository.findById(idParticipant);
        AtomicBoolean isUpdate = new AtomicBoolean(false);

        oParticipant.ifPresent(participant -> {
            participant.setStatusInterest(!participant.getStatusInterest());
            participantRepository.save(participant);
            isUpdate.set(true);
        });
        return isUpdate.get();
    }

    @Override
    public List<CVCompanyResponse> getCVParticipantByCompany(Integer idCompany, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Get is final mapping (Mapping)
        ResponseEntity<Response<Integer>> isFinalMappingD3Request =
                restTemplate.exchange(
                        "http://mapping-service/mapping/get-is-final/1",
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
                        "http://mapping-service/mapping/get-is-final/2",
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<Response<Integer>>() {
                        });

        if (isFinalMappingD4Request.getStatusCode().is4xxClientError() || isFinalMappingD4Request.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when getting is final mapping D4");
        }

        Integer isFinalMappingD4 = Objects.requireNonNull(isFinalMappingD4Request.getBody()).getData();

        if (isFinalMappingD3 == 0 && isFinalMappingD4 == 0) {
            return Collections.emptyList();
        }

        ResponseEntity<ResponseList<ParticipantFinalMappingResponse>> responseMapping =
                restTemplate.exchange(
                        "http://mapping-service/mapping/final/company/" + idCompany,
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<ResponseList<ParticipantFinalMappingResponse>>() {
                        });

        if (responseMapping.hasBody() && responseMapping.getBody() != null) {
            List<ParticipantFinalMappingResponse> pfmrList = Objects.requireNonNull(responseMapping.getBody()).getData();
            List<Integer> listIdParticipant = new ArrayList<>();

            pfmrList.forEach(pfmr -> {
                listIdParticipant.add(pfmr.getParticipantId());
            });

            List<Participant> participantList = participantRepository.findByIdIn(listIdParticipant);

            List<CVCompanyResponse> cvCompanyResponseList = new ArrayList<>();
            for (Participant participant : participantList) {
                if (isFinalMappingD3 == 0 && participant.getProdiId() == EProdi.D3.id) {
                    continue;
                }

                if (isFinalMappingD4 == 0 && participant.getProdiId() == EProdi.D4.id) {
                    continue;
                }

                CVCompanyResponse cvCompanyResponse = new CVCompanyResponse();
                cvCompanyResponse.setIdCv(participant.getCv().getId());
                cvCompanyResponse.setName(participant.getName());
                cvCompanyResponse.setStatusCv(participant.getStatusCv());
                cvCompanyResponseList.add(cvCompanyResponse);
            }

            return cvCompanyResponseList;
        }


        return Collections.emptyList();
    }

    @Override
    public CompanySelectionResponse getCompanySelection(String cookie, Integer idProdi) {
        CompanySelectionResponse companySelectionResponse = new CompanySelectionResponse();
        List<Participant> participantList = participantRepository.findByProdiId(idProdi);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<CompanyIdName>> responseCompany = restTemplate.exchange("http://company-service/company/get-name", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        List<CompanyIdName> companyIdNameList = Objects.requireNonNull(responseCompany.getBody()).getData();

        List<CompanySelection> companySelectionList = new ArrayList<>();
        int countSelected = 0;
        int countNotSelected = 0;
        for (Participant participant : participantList) {
            CompanySelection companySelection = new CompanySelection();
            companySelection.setParticipantName(participant.getName());
            companySelection.setNim(participant.getId());
            companySelection.setStatus(participant.getStatusInterest());

            if (companySelection.getStatus() == Boolean.TRUE) {
                countSelected++;
            } else {
                countNotSelected++;
            }

            List<ParticipantCompany> participantCompanyList = participantCompanyRepository.findByParticipantId(participant.getId());
            participantCompanyList.sort(Comparator.comparing(ParticipantCompany::getPriority));
            List<String> companyNameList = new ArrayList<>();

            for (ParticipantCompany participantCompany : participantCompanyList) {
                if (companyIdNameList != null) {
                    companyIdNameList.stream().filter(companyIdName -> companyIdName.getId().equals(participantCompany.getCompanyId())).findFirst().ifPresent(c -> {
                        companyNameList.add(c.getName());
                        System.out.println(c.getName());
                    });


                }
            }
            companySelection.setCompanyName(companyNameList);
            companySelectionList.add(companySelection);
        }

        companySelectionResponse.setTotalSubmitted(countSelected);
        companySelectionResponse.setTotalNotSubmitted(countNotSelected);
        companySelectionResponse.setCompanySelection(companySelectionList);
        return companySelectionResponse;
    }

    @Override
    public String getNameDomicile(Integer domicileId, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<Response<String>> region = restTemplate.exchange("http://management-content-service/management-content/domicile/" +
                domicileId, HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        if (region.hasBody() && region.getBody() != null) {
            return Objects.requireNonNull(region.getBody()).getData();
        }

        return null;
    }

    @Override
    public CompanySelectionCard getCompanySelectionCard(Integer idParticipant, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<Response<FormSubmitTimeResponse>> formSubmit = restTemplate.exchange("http://management-content-service/management-content/form-submit-time/4", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        CompanySelectionCard companySelectionCard = new CompanySelectionCard();
        if (formSubmit.hasBody() && formSubmit.getBody() != null) {
            String startDate = Objects.requireNonNull(formSubmit.getBody()).getData().getStartDate();
            String endDate = Objects.requireNonNull(formSubmit.getBody()).getData().getEndDate();

            companySelectionCard.setStatusUpdate(DateUtil.checkNowDate(startDate, endDate));
            participantRepository.findById(idParticipant).ifPresent(participant -> companySelectionCard.setStatusSubmission(participant.getStatusInterest()));
        }


        return companySelectionCard;

    }

    @Override
    public CompanySelectionDetail getCompanySelectionDetail(Integer idParticipant, String cookie) {
        CompanySelectionDetail companySelectionDetail = new CompanySelectionDetail();
        participantRepository.findById(idParticipant).ifPresent(participant -> companySelectionDetail.setWorkSystem(participant.getWorkSystem()));
        List<ParticipantCompany> participantCompany = participantCompanyRepository.findByParticipantId(idParticipant);
        participantCompany.sort(Comparator.comparing(ParticipantCompany::getPriority));

        int i = 0;
        for (ParticipantCompany p : participantCompany) {
            if (i == 0) {
                companySelectionDetail.setPriority1(p.getCompanyId());
            } else if (i == 1) {
                companySelectionDetail.setPriority2(p.getCompanyId());
            } else if (i == 2) {
                companySelectionDetail.setPriority3(p.getCompanyId());
            } else if (i == 3) {
                companySelectionDetail.setPriority4(p.getCompanyId());
            } else if (i == 4) {
                companySelectionDetail.setPriority5(p.getCompanyId());
            }
            i++;
        }

        return companySelectionDetail;
    }

    @Override
    public Boolean updateCompanySelection(Integer idParticipant, CompanySelectionUpdate companySelectionUpdate) {
        if (companySelectionUpdate.getPriority1() != null) {
            participantCompanyRepository.findByParticipantIdAndPriority(idParticipant, 1).ifPresent(participantCompany -> {
                participantCompany.setCompanyId(companySelectionUpdate.getPriority1());
                participantCompanyRepository.save(participantCompany);
            });
        }
        if (companySelectionUpdate.getPriority2() != null) {
            participantCompanyRepository.findByParticipantIdAndPriority(idParticipant, 2).ifPresent(participantCompany -> {
                participantCompany.setCompanyId(companySelectionUpdate.getPriority2());
                participantCompanyRepository.save(participantCompany);
            });
        }
        if (companySelectionUpdate.getPriority3() != null) {
            participantCompanyRepository.findByParticipantIdAndPriority(idParticipant, 3).ifPresent(participantCompany -> {
                participantCompany.setCompanyId(companySelectionUpdate.getPriority3());
                participantCompanyRepository.save(participantCompany);
            });
        }
        if (companySelectionUpdate.getPriority4() != null) {
            participantCompanyRepository.findByParticipantIdAndPriority(idParticipant, 4).ifPresent(participantCompany -> {
                participantCompany.setCompanyId(companySelectionUpdate.getPriority4());
                participantCompanyRepository.save(participantCompany);
            });
        }
        if (companySelectionUpdate.getPriority5() != null) {
            participantCompanyRepository.findByParticipantIdAndPriority(idParticipant, 5).ifPresent(participantCompany -> {
                participantCompany.setCompanyId(companySelectionUpdate.getPriority5());
                participantCompanyRepository.save(participantCompany);
            });
        }

        participantRepository.findById(idParticipant).ifPresent(participant -> {
            participant.setWorkSystem(companySelectionUpdate.getWorkSystem());
            participantRepository.save(participant);
        });

        return Boolean.TRUE;
    }

    @Override
    public void deleteCVCompetence(Integer idCompetence) {
        cvCompetenceRepository.deleteAllByCompetenceId(idCompetence);
    }

    @Override
    public void deleteCVJobscope(Integer idJobscope) {
        cvJobScopeRepository.deleteAllByJobscopeId(idJobscope);
    }

    @Override
    public List<CVRecap> recapCompetence(String cookie, Integer idProdi) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<JobScopeResponse>> jobsScopeList = restTemplate.exchange("http://management-content-service/management-content/jobscope/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        ResponseEntity<ResponseList<CompetenceResponse>> competenceList = restTemplate.exchange("http://management-content-service/management-content/competence/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        List<JobScopeResponse> jobScopeList = Objects.requireNonNull(jobsScopeList.getBody()).getData();
        List<CompetenceResponse> competenceResponseList = Objects.requireNonNull(competenceList.getBody()).getData();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<CVJobScopeRecap> cvJobScopeRecaps = cvJobScopeRepository.findCVRecap(currentYear, idProdi);
        List<CVCompetenceRecap> cvCompetenceRecaps = cvCompetenceRepository.findCVRecap(currentYear, idProdi);

        List<CVRecap> cvRecaps = new ArrayList<>();
        Integer id = 1;

        for (CVJobScopeRecap cvJobScopeRecap : cvJobScopeRecaps) {
            for (JobScopeResponse jobScopeResponse : jobScopeList) {
                if (cvJobScopeRecap.getId().equals(jobScopeResponse.getId())) {
                    CVRecap cvRecap = new CVRecap();
                    cvRecap.setName(jobScopeResponse.getName());
                    cvRecap.setTotal(cvJobScopeRecap.getTotal());
                    cvRecap.setType(0);
                    cvRecap.setId(id++);
                    cvRecaps.add(cvRecap);
                }
            }
        }

        for (CVCompetenceRecap cvCompetenceRecap : cvCompetenceRecaps) {
            for (CompetenceResponse competenceResponse : competenceResponseList) {
                if (cvCompetenceRecap.getId().equals(competenceResponse.getId())) {
                    CVRecap cvRecap = new CVRecap();
                    cvRecap.setName(competenceResponse.getName());
                    cvRecap.setTotal(cvCompetenceRecap.getTotal());
                    cvRecap.setType(competenceResponse.getType());
                    cvRecap.setId(id++);
                    cvRecaps.add(cvRecap);
                }
            }
        }
        return cvRecaps;
    }

    @Override
    public List<CompanySelectionRecap> getCompanySelectionRecap(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<CompanyIdName>> responseCompany = restTemplate.exchange("http://company-service/company/get-name", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        List<CompanyIdName> companyResponseList = Objects.requireNonNull(responseCompany.getBody()).getData();

        ResponseEntity<ResponseList<QuotaResponse>> quotaResponse = restTemplate.exchange("http://company-service/company/prerequisite/quota", HttpMethod.GET, req, new ParameterizedTypeReference<>() {
        });

        List<QuotaResponse> quotaResponseList = Objects.requireNonNull(quotaResponse.getBody()).getData();


        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<ParticipantCompanyRecap> companySelectionRecaps = participantCompanyRepository.findRecap(currentYear);
        AtomicReference<Integer> id = new AtomicReference<>(1);

        List<CompanySelectionRecap> companySelectionRecapList = new ArrayList<>();
        for (ParticipantCompanyRecap participantCompanyRecap : companySelectionRecaps) {
            companyResponseList.stream().filter(c -> Objects.equals(c.getId(), participantCompanyRecap.getId())).findFirst().ifPresent(c -> {
                CompanySelectionRecap companySelectionRecap = new CompanySelectionRecap();
                companySelectionRecap.setCompanyName(c.getName());
                companySelectionRecap.setPriority(participantCompanyRecap.getPriority());
                companySelectionRecap.setTotal(participantCompanyRecap.getTotal());
                companySelectionRecap.setId(id.getAndSet(id.get() + 1));
                companySelectionRecapList.add(companySelectionRecap);
            });
        }

        List<Integer> listIdCompanyRecap = companySelectionRecaps.stream().map(ParticipantCompanyRecap::getId).collect(Collectors.toList());
        List<Integer> finalListIdCompanyRecap = listIdCompanyRecap.stream().distinct().collect(Collectors.toList());

        quotaResponseList = quotaResponseList.stream().filter(c -> !finalListIdCompanyRecap.contains(c.getIdCompany())).collect(Collectors.toList());

        for (QuotaResponse qr : quotaResponseList) {
            if (qr.getQuotaD3() != 0 || qr.getQuotaD4() != 0) {
                companyResponseList.stream().filter(c -> Objects.equals(c.getId(), qr.getIdCompany())).findFirst().ifPresent(c -> {
                    CompanySelectionRecap companySelectionRecap = new CompanySelectionRecap();
                    companySelectionRecap.setCompanyName(c.getName());
                    companySelectionRecap.setPriority(0);
                    companySelectionRecap.setTotal(0L);
                    companySelectionRecap.setId(id.getAndSet(id.get() + 1));
                    companySelectionRecapList.add(companySelectionRecap);
                });
            }
        }

        return companySelectionRecapList;
    }

    @Override
    public List<ParticipantValueList> getParticipantValue(Integer idProdi) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        List<Participant> participants = participantRepository.findByYearAndProdiId(currentYear, idProdi);
//        List<Integer> idParticipants = participants.stream().map(Participant::getId).collect(Collectors.toList());

        List<CourseValue> courseValues = courseValueRepository.findAll();
//        List<ParticipantValueResponse> courseValuess = courseValueRepository.getCourseNameAndValue(currentYear, idProdi);
        List<ParticipantValueResponse> participantValueResponses = courseValueRepository.getCourseNameAndValue(currentYear, idProdi);

        List<ParticipantValueList> participantValueList = new ArrayList<>();
        for (ParticipantValueResponse participantValueResponse : participantValueResponses) {
            participantValueList.stream().filter(c -> Objects.equals(c.getId(), participantValueResponse.getIdParticipant())).findFirst().ifPresentOrElse(c -> {
                CourseNameAndValue courseNameAndValue = new CourseNameAndValue();
                courseNameAndValue.setValue(participantValueResponse.getValue());
                courseNameAndValue.setName(participantValueResponse.getName());
                courseNameAndValue.setId(participantValueResponse.getId());
                courseNameAndValue.setCourseId(participantValueResponse.getCourseId());
                c.getCourseNameAndValue().add(courseNameAndValue);
            }, () -> {
                ParticipantValueList participantValue = new ParticipantValueList();
                participantValue.setId(participantValueResponse.getIdParticipant());
                participantValue.setName(participantValueResponse.getNameParticipant());
                participantValue.setIpk(participantValueResponse.getIpkParticipant());

                CourseNameAndValue courseNameAndValue = new CourseNameAndValue();
                courseNameAndValue.setValue(participantValueResponse.getValue());
                courseNameAndValue.setName(participantValueResponse.getName());
                courseNameAndValue.setId(participantValueResponse.getId());
                courseNameAndValue.setCourseId(participantValueResponse.getCourseId());
                participantValue.getCourseNameAndValue().add(courseNameAndValue);
                participantValueList.add(participantValue);
            });
        }
        return participantValueList;
    }

    @Override
    public List<Course> getAllCourse(Integer idProdi) {
        return courseRepository.findAllByProdiId(idProdi);
    }

    @Override
    public List<AbsenceRecap> getAllAbsence(Integer idProdi) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return absenceRecapRepository.findAllByParticipantYearAndParticipantProdiId(currentYear, idProdi);
    }

    @Override
    public void exportCV(String cookie, Integer idCv, HttpServletResponse response) {
        CVGetResponse cvGetResponse = getCVDetail(idCv, cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Participant participant = participantRepository.findByCvId(cvGetResponse.getId());

        ResponseEntity<ResponseList<JobScopeResponse>> jobscopeResponse =
                restTemplate.exchange(
                        "http://management-content-service/management-content/jobscope/get-all/" + participant.getProdiId(),
                        HttpMethod.GET,
                        req,
                        new ParameterizedTypeReference<>() {
                        });
        List<JobScopeResponse> jobScopeResponses = Objects.requireNonNull(jobscopeResponse.getBody()).getData();

        ResponseEntity<ResponseList<CompetenceResponse>> competenceList = restTemplate.exchange(
                "http://management-content-service/management-content/competence/get-all/" + participant.getProdiId(),
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });
        List<CompetenceResponse> competenceResponses = Objects.requireNonNull(competenceList.getBody()).getData();

        //Data
        String name = cvGetResponse.getName();
        String nickname = cvGetResponse.getNickname();
        String address = cvGetResponse.getAddress();
        String phone = cvGetResponse.getNoPhone();
        String email = cvGetResponse.getEmail();
        String birthDate = cvGetResponse.getBirthday();
        String place = cvGetResponse.getPlace();
        String citizenship = cvGetResponse.getCitizenship();
        String religion = cvGetResponse.getReligion();
        String status = "";

        if (cvGetResponse.getMarriage().equals(Boolean.TRUE)) {
            status = "Menikah";
        } else {
            status = "Belum Menikah";
        }

        String jobScopeTitle = "Cakupan tugas terkait pengembangan software yang ingin dilakukan di tempat *KP/PKL ";

        List<String> jobScope = new ArrayList<>();
        for (CVJobScope cvJobScope : cvGetResponse.getJobScopes()) {
            jobScopeResponses.stream().filter(j -> j.getId().equals(cvJobScope.getJobScopeId())).findFirst().ifPresent(j -> {
                jobScope.add(j.getName());
            });
        }

        List<String> skill = new ArrayList<>();
        for (Skill s : cvGetResponse.getSkills()) {
            skill.add(s.getSkillName());
        }

        List<Education> educations = cvGetResponse.getEducations();
        List<Organization> organizations = cvGetResponse.getOrganizations();
        List<Experience> experiences = cvGetResponse.getExperiences();

        List<CVCompetenceNameValue> programmingLanguages = new ArrayList<>();
        List<CVCompetenceNameValue> databases = new ArrayList<>();
        List<CVCompetenceNameValue> frameworks = new ArrayList<>();
        List<CVCompetenceNameValue> tools = new ArrayList<>();
        List<CVCompetenceNameValue> modellingTools = new ArrayList<>();
        List<CVCompetenceNameValue> languages = new ArrayList<>();


        for (CVCompetenceAndType cr : cvGetResponse.getCompetencies()) {
            competenceResponses.stream().filter(c -> Objects.equals(c.getId(), cr.getCompetenceId())).findFirst().ifPresent(c -> {
                CVCompetenceNameValue cvCompetenceNameValue = new CVCompetenceNameValue();
                cvCompetenceNameValue.setId(c.getId());
                cvCompetenceNameValue.setName(c.getName());
                cvCompetenceNameValue.setType(c.getType());

                if(cr.getKnowledgeId() == 1){
                    cvCompetenceNameValue.setValue("Sangat Buruk");
                }else if (cr.getKnowledgeId() == 2) {
                    cvCompetenceNameValue.setValue("Kurang Baik");
                }else if(cr.getKnowledgeId() == 3) {
                    cvCompetenceNameValue.setValue("Cukup Baik");
                }else if(cr.getKnowledgeId() == 4) {
                    cvCompetenceNameValue.setValue("Baik");
                }else if(cr.getKnowledgeId() == 5) {
                    cvCompetenceNameValue.setValue("Sangat Baik");
                }

                if(c.getType() == 1) {
                    programmingLanguages.add(cvCompetenceNameValue);
                }else if(c.getType() == 2) {
                    databases.add(cvCompetenceNameValue);
                }else if(c.getType() == 3) {
                    frameworks.add(cvCompetenceNameValue);
                }else if(c.getType() == 4) {
                    tools.add(cvCompetenceNameValue);
                }else if(c.getType() == 5) {
                    modellingTools.add(cvCompetenceNameValue);
                }else if(c.getType() == 6) {
                    languages.add(cvCompetenceNameValue);
                }
            });
        }

        List<Seminar> seminars = cvGetResponse.getSeminars();
        List<Championship> championships = cvGetResponse.getChampionships();


        //==============

        try (PDDocument pdfDocument = new PDDocument()) {
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            float[] components = new float[]{
                    102f, 153f, 255f};
            PDColor pdColor = new PDColor(components, PDDeviceRGB.INSTANCE);

            PDFont font = PDType1Font.TIMES_ROMAN;
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            PDRectangle mediaBox = page.getMediaBox();
            float marginY = 80;
            float marginX = 80;
            float width = mediaBox.getWidth() - 2 * marginX;
            float startX = mediaBox.getLowerLeftX() + marginX;
            float startY = mediaBox.getUpperRightY() - marginY;
            float newLine = PDFUtil.getNewLine(16);


            contentStream.beginText();

            contentStream.setFont(PDType1Font.TIMES_BOLD, 16);

            String titleLine1 = "DAFTAR RIWAYAT HIDUP";
            String titleLine2 = "( CURRICULUM VITAE )";
            float size = PDFUtil.stringLength(16, titleLine1);

            contentStream.newLineAtOffset((mediaBox.getWidth() / 2) - (size / 2), startY);
            contentStream.showText(titleLine1);
            contentStream.newLine();

            contentStream.newLineAtOffset(-((mediaBox.getWidth() / 2) - (size / 2)), 0);
            size = PDFUtil.stringLength(16, titleLine2);
            contentStream.newLineAtOffset((mediaBox.getWidth() / 2) - (size / 2), newLine);
            contentStream.showText(titleLine2);
            contentStream.newLineAtOffset(-((mediaBox.getWidth() / 2) - (size / 2)), 0);

            int heightOfBox = 0;
            int widthOfColon = 120;

            contentStream.newLineAtOffset(startX, -50);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.showText("Nama");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, name, 0, false, true) - 5;
            contentStream.newLineAtOffset(-(widthOfColon + 10), -5);
            contentStream.showText("Nama Panggilan");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, nickname, 0, false, true) - 5;
            contentStream.newLineAtOffset(-(widthOfColon + 10), -5);
            contentStream.showText("Alamat");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, address, 0, false, true) - 5;
            contentStream.newLineAtOffset(-(widthOfColon + 10), -5);
            contentStream.showText("No. HP/Telepon");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, phone, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.showText("Email");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, email, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.showText("Tempat, Tanggal Lahir");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, place + ", " + birthDate, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.showText("Agama");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, religion, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.showText("Status");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, status, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.showText("Kewarganegaraan");
            contentStream.newLineAtOffset(widthOfColon, 0);
            contentStream.showText(": ");
            heightOfBox += PDFUtil.addParagraph(contentStream, width - startX, 10, 0, citizenship, 0, false, true) - 5;
            contentStream.newLineAtOffset(-widthOfColon - 10, -5);
            contentStream.endText();

            boolean drawContent = true;
            float bottomMargin = 70;
            float topMargin = 80;
            // y position is your coordinate of top left corner of the table
            float yPosition = startY + heightOfBox - 80; //300

            BaseTable table = new BaseTable(yPosition, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            Cell<PDPage> cell;
            // the parameter is the row height
            Color blueHeader = new Color(102, 153, 255);

            Row<PDPage> headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Cakupan tugas terkait pengembangan software yang ingin " +
                    "dilakukan di tempat *KP/PKL ");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            Row<PDPage> row;
            for (int i = 0; i < jobScope.size(); i++) {
                row = table.createRow(12);
                cell = row.createCell(100, jobScope.get(i));
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);
            }
            float lastY = table.draw();
            page = table.getCurrentPage();

            table = new BaseTable(lastY - 20, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Hardskill & Softkill yang ingin dikembangkan di tempat *KP/PKL ");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            for (String s : skill) {
                row = table.createRow(12);
                cell = row.createCell(100, s);
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);
            }

            lastY = table.draw();
            page = table.getCurrentPage();

            table = new BaseTable(lastY - 20, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Jenjang Pendidikan ");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(30, "Tahun");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = headerRow.createCell(70, "Tempat");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            for (int i = 0; i < educations.size(); i++) {
                row = table.createRow(12);
                cell = row.createCell(30, educations.get(i).getStartYear() + " - " + educations.get(i).getEndYear());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);

                cell = row.createCell(70, educations.get(i).getInstitutionName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);

            }

            lastY = table.draw();
            page = table.getCurrentPage();

            table = new BaseTable(lastY - 20, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Pengalaman Berorganisasi");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(30, "Tahun");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = headerRow.createCell(70, "Informasi");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            for (int i = 0; i < organizations.size(); i++) {
                row = table.createRow(12);
                cell = row.createCell(30, organizations.get(i).getStartYear() + " - " + organizations.get(i).getEndYear());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);

                cell = row.createCell(70, organizations.get(i).getOrganizationName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setLineSpacing(1.1f);

            }

            lastY = table.draw();
            page = table.getCurrentPage();

            for (int i = 0; i < experiences.size(); i++) {
                table = new BaseTable(lastY - 20, startY,
                        topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

                headerRow = table.createRow(12);
                cell = headerRow.createCell(100, "Pengalaman Pengerjaan Tugas dalam Mata Kuliah");
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
                cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.CENTER);
                table.addHeaderRow(headerRow);

                row = table.createRow(12);
                cell = row.createCell(30, "Nama Mata Kuliah");
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.CENTER);

                cell = row.createCell(70, experiences.get(i).getCourseName());
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.CENTER);

                row = table.createRow(12);
                cell = row.createCell(30, "Nama Tugas");
                cell.setFont(PDType1Font.TIMES_BOLD);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.CENTER);

                cell = row.createCell(70, experiences.get(i).getTaskName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.CENTER);

                row = table.createRow(12);
                cell = row.createCell(30, "Deskripsi");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getDescription());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                row = table.createRow(12);
                cell = row.createCell(30, "Teknologi dan Alat");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getTechTool());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                row = table.createRow(12);
                cell = row.createCell(30, "Peran dalam Tim");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getRoleDescription());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                row = table.createRow(12);
                cell = row.createCell(30, "Pencapaian");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getAchievement());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                row = table.createRow(12);
                cell = row.createCell(30, "Pembelajaran");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getLessonLearned());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                row = table.createRow(12);
                cell = row.createCell(30, "Waktu Pengerjaan");
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                cell = row.createCell(70, experiences.get(i).getTimeDescription());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
//            cell.setFillColor(blueHeader);
                cell.setAlign(HorizontalAlignment.LEFT);

                lastY = table.draw();
                page = table.getCurrentPage();
            }

            contentStream.close();
            contentStream = new PDPageContentStream(pdfDocument, page, PDPageContentStream.AppendMode.APPEND, true, true);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(startX, lastY - 20);
            contentStream.showText("INFORMASI TAMBAHAN");
            contentStream.endText();


            table = new BaseTable(lastY - 30, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(20, "Kolom");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = headerRow.createCell(80, "Informasi");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            for (int i = 0; i < programmingLanguages.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Bahasa Pemograman");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, programmingLanguages.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, programmingLanguages.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            for (int i = 0; i < databases.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Database");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, databases.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, databases.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }
            for (int i = 0; i < frameworks.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Frameworks");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, frameworks.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, frameworks.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            for (int i = 0; i < tools.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Tools");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, tools.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, tools.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            for (int i = 0; i < modellingTools.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Modelling Tools");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, modellingTools.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, modellingTools.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            for (int i = 0; i < languages.size(); i++) {
                if(i == 0){
                    row = table.createRow(12);
                    cell = row.createCell(20, "Bahasa Komunikasi");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }else{
                    row = table.createRow(12);
                    cell = row.createCell(20, "");
                    cell.setFont(PDType1Font.TIMES_ROMAN);
                    cell.setFontSize(11);
                    cell.setValign(VerticalAlignment.MIDDLE);
                }

                cell = row.createCell(55, languages.get(i).getName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(25, languages.get(i).getValue());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            lastY = table.draw();
            page = table.getCurrentPage();

            table = new BaseTable(lastY - 30, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Seminar/ Tutorial ");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            row = table.createRow(12);
            cell = row.createCell(20, "Tahun");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            cell = row.createCell(50, "Nama Seminar/Tutorial");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            cell = row.createCell(30, "Peran");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            for (int i = 0; i < seminars.size(); i++) {
                row = table.createRow(12);

                cell = row.createCell(20, String.valueOf(seminars.get(i).getYear()));
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(50, seminars.get(i).getSeminarName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(30, seminars.get(i).getRoleDescription());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            lastY = table.draw();
            page = table.getCurrentPage();

            table = new BaseTable(lastY - 20, startY,
                    topMargin, bottomMargin, width, marginX, pdfDocument, page, true, drawContent);

            headerRow = table.createRow(12);
            cell = headerRow.createCell(100, "Kejuaraan");
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.CENTER);
            table.addHeaderRow(headerRow);

            row = table.createRow(12);
            cell = row.createCell(20, "Tahun");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            cell = row.createCell(50, "Nama Kejuaraan");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            cell = row.createCell(30, "Prestasi");
            cell.setFont(PDType1Font.TIMES_ROMAN);
            cell.setFontSize(11);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(blueHeader);
            cell.setAlign(HorizontalAlignment.LEFT);

            for (int i = 0; i < championships.size(); i++) {
                row = table.createRow(12);

                cell = row.createCell(20, String.valueOf(championships.get(i).getYear()));
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(50, championships.get(i).getChampionshipName());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);

                cell = row.createCell(30, championships.get(i).getAchievement());
                cell.setFont(PDType1Font.TIMES_ROMAN);
                cell.setFontSize(11);
                cell.setValign(VerticalAlignment.MIDDLE);
            }

            lastY = table.draw();
            page = table.getCurrentPage();

            contentStream.close();
            contentStream = new PDPageContentStream(pdfDocument, page, PDPageContentStream.AppendMode.APPEND, true, true);


            if(marginY > lastY - 150){
                PDPage blankPage = new PDPage(PDRectangle.A4);
                pdfDocument.addPage(blankPage);
                contentStream.close();
                contentStream = new PDPageContentStream(pdfDocument, blankPage, PDPageContentStream.AppendMode.APPEND, true, true);

                lastY = startY;
            }

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
            contentStream.newLineAtOffset(startX, lastY - 30);
            contentStream.showText("Demikian CV ini saya buat dengan sebenarnya.");
            contentStream.newLineAtOffset(-startX, 0);

            contentStream.newLineAtOffset((startX + width) - 130,  -15);
            contentStream.showText(".............., ....................");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("Hormat saya, ");
            contentStream.newLineAtOffset(0, -50);
            contentStream.showText("....................................");

            contentStream.endText();
            contentStream.close();

            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=CV_" + name + ".pdf");
//            PDDocumentInformation info = new PDDocumentInformation();
//            info.setCreationDate(Calendar.getInstance());
//            info.setModificationDate(Calendar.getInstance());
//            info.setTitle(name);
//
            pdfDocument.save(outputStream);
            pdfDocument.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CVInterestResponse> getCVInterestParticipant(String cookie, Integer idProdi) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<CVInterestResponse> cvInterestResponseList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<CompetenceResponse>> competenceResponse = restTemplate.exchange("http://management-content-service/management-content/competence/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        List<CompetenceResponse> competenceResponses = Objects.requireNonNull(competenceResponse.getBody()).getData();

        List<Participant> participants = participantRepository.findByYearAndProdiId(currentYear, idProdi);
        for (Participant participant : participants) {
            CVInterestResponse cvInterestResponse = new CVInterestResponse();
            cvInterestResponse.setIdParticipant(participant.getId());
            CV cv = cvRepository.findById(participant.getCv().getId()).orElse(null);
            if (cv != null) {
                cvInterestResponse.setIdDomicile(cv.getDomicileId());

                List<CVJobScope> cvJobScopes = cvJobScopeRepository.findByCvId(cv.getId());
                List<CVCompetence> cvCompetencies = cvCompetenceRepository.findByCvId(cv.getId());

                List<CompetenceAndType> competenceAndTypes = new ArrayList<>();
                for (CVCompetence cvCompetence : cvCompetencies) {
                    CompetenceAndType competenceAndType = new CompetenceAndType();
                    competenceAndType.setId(cvCompetence.getId());
                    competenceAndType.setIdCompetence(cvCompetence.getCompetenceId());
                    competenceAndType.setIdKnowledge(cvCompetence.getKnowledgeId());

                    competenceResponses.stream().filter(c -> Objects.equals(c.getId(), cvCompetence.getCompetenceId())).findFirst().ifPresent(c -> {
                        competenceAndType.setCompetenceType(c.getType());
                    });
                    competenceAndTypes.add(competenceAndType);
                }
                cvInterestResponse.setJobscope(cvJobScopes);
                cvInterestResponse.setCompetence(competenceAndTypes);
            }
            cvInterestResponseList.add(cvInterestResponse);
        }
        return cvInterestResponseList;
    }

    @Override
    public List<ParticipantCompany> getCompanySelectionMapping(Integer prodiId) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return participantCompanyRepository.findByProdiIdAndYear(prodiId, currentYear);
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateParticipant() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ParticipantJsonResponse>> typeReference = new TypeReference<List<ParticipantJsonResponse>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/APIPolban.json");


        try {
            List<ParticipantJsonResponse> participantJsonResponsesList = mapper.readValue(inputStream, typeReference);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", "panitiad3");
            jsonObject.put("password", "1234");

            HttpEntity<String> req = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<Response<LoginResponse>> loginResponse = restTemplate.exchange("http://account-service/account/login",
                    HttpMethod.POST, req, new ParameterizedTypeReference<>() {
                    });

            String cookie = loginResponse.getHeaders().get("Set-Cookie").get(0);

            for (ParticipantJsonResponse p : participantJsonResponsesList) {
                jsonObject = new JSONObject();
                jsonObject.put("id_role", 1);
                jsonObject.put("name", p.getName());
                jsonObject.put("password", "1234");
                jsonObject.put("username", p.getNim());

                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
                req = new HttpEntity<>(jsonObject.toString(), headers);


                try {
                    ResponseEntity<Response<CreateAccountResponse>> createAccount = restTemplate.exchange("http://account-service/account/create",
                            HttpMethod.POST, req, new ParameterizedTypeReference<>() {
                            });

                    if (createAccount.getStatusCode() == HttpStatus.OK) {
                        CV cv = new CV();
                        CV c = cvRepository.save(cv);


                        int idParticipant = Integer.parseInt(p.getNim());
                        Participant participant = new Participant();
                        participant.setId(idParticipant);
                        participant.setName(p.getName());
                        participant.setStatusInterest(false);
                        participant.setStatusCv(false);
                        participant.setYear(currentYear);
                        if (p.getProdi().equals("D3")) {
                            participant.setProdiId(0);
                        } else if (p.getProdi().equals("D4")) {
                            participant.setProdiId(1);
                        }
                        participant.setAccountId(Objects.requireNonNull(createAccount.getBody()).getData().getId());
                        participant.setWorkSystem("");
                        participant.setIpk(p.getIpk());
                        participant.setCv(c);
                        Participant newParticipant = participantRepository.save(participant);

                        for (CourseCodeValue ccv : p.getCourse()) {
                            Course course = courseRepository.findByCode(ccv.getCode());
                            if (course != null) {
                                CourseValue courseValue = new CourseValue();
                                courseValue.setCourse(course);
                                courseValue.setValue(ccv.getValue());
                                courseValue.setParticipant(newParticipant);
                                courseValueRepository.save(courseValue);
                            }
                        }

                        List<ParticipantCompany> participantCompanies = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            ParticipantCompany participantCompany = new ParticipantCompany();
                            participantCompany.setParticipantId(newParticipant.getId());
                            participantCompany.setCompanyId(null);
                            participantCompany.setPriority(i + 1);
                            participantCompanies.add(participantCompany);
                        }
                        participantCompanyRepository.saveAll(participantCompanies);
                    }
                } catch (Exception e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }
//
//            headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
//            req = new HttpEntity<>(headers);
        } catch (IOException e) {
            System.out.println("Unable to save users: " + e.getMessage());
        }

    }
}
