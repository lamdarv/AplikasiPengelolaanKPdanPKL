package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.ranking.*;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.FinalParticipantCompany;
import com.jtk.ps.api.dto.FinalMappingRequest;
import com.jtk.ps.api.dto.FinalMappingResponse;
import com.jtk.ps.api.dto.ParticipantFinalMappingResponse;
import com.jtk.ps.api.dto.ParticipantResponse;
import com.jtk.ps.api.dto.QuotaResponse;
import com.jtk.ps.api.model.CriteriaMapping;
import com.jtk.ps.api.model.FinalMapping;
import com.jtk.ps.api.model.ParticipantRanking;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.ERole;
import com.jtk.ps.api.model.EProdi;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.Utility;
import com.jtk.ps.api.model.UtilityDate;
import com.jtk.ps.api.repository.CriteriaMappingRepository;
import com.jtk.ps.api.repository.FinalMappingRepository;
import com.jtk.ps.api.repository.ParticipantRankingRepository;
import com.jtk.ps.api.repository.UtilityRepository;
import com.jtk.ps.api.repository.UtilityDateRepository;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.MappingExcel;
import com.jtk.ps.api.util.SAWUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class MappingService implements IMappingService {

    @Autowired
    private FinalMappingRepository finalMappingRepository;

    @Autowired
    private CriteriaMappingRepository criteriaMappingRepository;

    @Autowired
    private ParticipantRankingRepository participantRankingRepository;

    @Autowired
    private UtilityDateRepository utilityDateRepository;

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public FinalMappingResponse getFinalMapping(Integer idRole, Integer idProdi, String cookie) {

        FinalMappingResponse res = new FinalMappingResponse();
        List<FinalParticipantCompany> resList = new ArrayList<>();

        // Get is_final value
        int id = 1;
        int idPublish = 3;
        if (idProdi == EProdi.D4.id) {
            id = 2;
            idPublish = 4;
        }

        List<Integer> ar = new ArrayList<>();

        utilityRepository.findById(id).ifPresent(u -> {
            ar.add(u.getIsFinal());
        });

        int isFinal = ar.get(0);
        res.setIsFinal(isFinal);

        utilityRepository.findById(idPublish).ifPresent(u -> {
            ar.add(u.getIsFinal());
        });

        res.setIsPublish(ar.get(1));

        if (idRole == ERole.PARTICIPANT.id && isFinal == 0) {
            res.setFinalMapping(resList);
            return res;
        }

        // Get FinalParticipantCompany
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        HashMap<Integer, FinalParticipantCompany> resMap = new HashMap<>();
        List<FinalMapping> finalMappingList = finalMappingRepository.findByYearAndProdiId(currentYear, idProdi);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                "http://participant-service/participant/get-all?year=" + currentYear,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<ParticipantResponse> participantList = Objects.requireNonNull(pResponse.getBody()).getData();

        ResponseEntity<ResponseList<QuotaResponse>> qResponse = restTemplate.exchange(
                "http://company-service/company/prerequisite/quota/",
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<QuotaResponse> quotaList = Objects.requireNonNull(qResponse.getBody()).getData();

        for (FinalMapping fm : finalMappingList) {
            Integer companyId = fm.getCompanyId();
            Integer participantId = fm.getParticipantId();

            if (resMap.containsKey(companyId)) {
                // Get participant's name (Participant)
                Participant p = new Participant();
                p.setId(participantId);

                participantList.stream().filter(participant -> participant.getIdParticipant().equals(participantId))
                        .findFirst().ifPresent(participant -> {
                            p.setName(participant.getName());
                        });

                resMap.get(companyId).getParticipant().add(p);
                continue;
            }

            FinalParticipantCompany fmr = new FinalParticipantCompany();

            // Get company's quota (Company)
            Company c = new Company();
            c.setId(companyId);
            quotaList.stream().filter(quota -> quota.getIdCompany().equals(companyId)).findFirst().ifPresent(quota -> {
                c.setName(quota.getName());
                if (idProdi == EProdi.D4.id) {
                    c.setQuota(quota.getQuotaD4());
                } else {
                    c.setQuota(quota.getQuotaD3());
                }
            });

            fmr.setCompany(c);

            // Get participant's name (Participant)

            Participant p = new Participant();
            p.setId(participantId);
            participantList.stream().filter(participant -> participant.getIdParticipant().equals(participantId))
                    .findFirst().ifPresent(participant -> {
                        p.setName(participant.getName());
                    });

            fmr.getParticipant().add(p);

            resMap.put(fm.getCompanyId(), fmr);
        }

        UtilityDate finalDate = utilityDateRepository.findByIdUtilityAndYearAndProdiId(2, currentYear, idProdi);
        if (finalDate != null) {
            res.setFinalDate(finalDate.getUpdatedAt().toString());
        }else{
            res.setFinalDate("");
        }

        UtilityDate publishDate = utilityDateRepository.findByIdUtilityAndYearAndProdiId(3, currentYear, idProdi);
        if (publishDate != null) {
            res.setPublishDate(publishDate.getUpdatedAt().toString());
        }else{
            res.setPublishDate("");
        }

        resList.addAll(resMap.values());
        res.setFinalMapping(resList);

        return res;
    }

    @Override
    public List<ParticipantFinalMappingResponse> getParticipantFinalMapping(Integer idCompany) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<FinalMapping> finalMapping = finalMappingRepository.findByCompanyIdAndYear(idCompany, year);

        List<ParticipantFinalMappingResponse> listParticipant = new ArrayList<>();
        for (FinalMapping finalMapping1 : finalMapping) {
            ParticipantFinalMappingResponse pfmr = new ParticipantFinalMappingResponse();
            pfmr.setParticipantId(finalMapping1.getParticipantId());
            pfmr.setIdProdi(finalMapping1.getProdiId());
            listParticipant.add(pfmr);
        }
        return listParticipant;
    }

    @Override
    public Boolean generateRank(String cookie, Integer idProdi) {
        // Initialization for use rest api
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Get all requirement Company
        Mono<ResponseList<CompanyReqResponse>> companyReq = webClient.build().get()
                .uri("http://company-service/company/req-company")
                .header(Constant.PayloadResponseConstant.COOKIE, cookie)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        // Get all cv and interest Participant
        Mono<ResponseList<ParticipantReqResponse>> participantResponse = webClient.build().get()
                .uri("http://participant-service/participant/cv-interest-participant")
                .header(Constant.PayloadResponseConstant.COOKIE, cookie)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        // Get all company selection
        Mono<ResponseList<CompanySelection>> companySelection = webClient.build().get()
                .uri("http://participant-service/participant/company-selection/mapping")
                .header(Constant.PayloadResponseConstant.COOKIE, cookie)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        // Get all criteria percentage
        List<com.jtk.ps.api.model.CriteriaMapping> listCriteriaMappingMapping = criteriaMappingRepository.findAll();

        // Initialization Calculation SAW for each company (Normalization)
        List<CalculationSAW> finalScoreCalculationSaw = new ArrayList<>();

        List<CompanyReqResponse> listCompany = Objects.requireNonNull(companyReq.block()).getData();
        List<ParticipantReqResponse> listParticipant = Objects.requireNonNull(participantResponse.block()).getData();
        List<CompanySelection> listCompanySelection = Objects.requireNonNull(companySelection.block()).getData();

        // Get All bobot kriteria
        List<Float> bobotCriteria = new ArrayList<>();
        List<Float> totalBobot = new ArrayList<>();

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.JOBSCOPE))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.PROGRAMMING_LANGUAGE))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.DATABASE))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.FRAMEWORK))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.TOOL))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.MODELLING))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.COMMUNICATION_LANGUAGE))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.DOMICILE))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        listCriteriaMappingMapping.stream()
                .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.INTEREST_COMPANY))
                .findFirst()
                .ifPresent(value -> bobotCriteria.add(value.getPercentageValue()));

        Float total = bobotCriteria.get(0) + bobotCriteria.get(1) + bobotCriteria.get(2) + bobotCriteria.get(3)
                + bobotCriteria.get(4) + bobotCriteria.get(5) + bobotCriteria.get(6) + bobotCriteria.get(7)
                + bobotCriteria.get(8);
        totalBobot.add(total);

        // Looping for each company active
        for (CompanyReqResponse companyReqResponse : listCompany) {
            totalBobot.set(0, total);
            // Get all company selection in this company
            List<CompanySelection> participantChoose = listCompanySelection.stream()
                    .filter(c -> c.getCompanyId() == companyReqResponse.getIdCompany()).collect(Collectors.toList());
            // Get all id participant
            List<Integer> listParticipantId = participantChoose.stream().map(CompanySelection::getParticipantId)
                    .collect(Collectors.toList());
            // Get all data participant in this company
            List<ParticipantReqResponse> participants = listParticipant.stream()
                    .filter(p -> listParticipantId.contains(p.getIdParticipant())).collect(Collectors.toList());

            // If no one participant choose in this company
            if (participants.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(8));
            }

            if (participants.isEmpty() || participants.size() == 1)
                participants = listParticipant;

            // Initialization for min or max criteria
            CriteriaMappingValue minMaxCriteriaMapping = new CriteriaMappingValue();

            // Initialization Calculation SAW for each company (Compatibility rating)
            List<CalculationSAW> compatibilityRatingList = new ArrayList<>();

            // Get all requirement jobscope of company
            List<Integer> jobsScopeId = companyReqResponse.getJobscope().stream().map(JobscopeResponse::getJobscopeId)
                    .collect(Collectors.toList());

            // Get All specific of requirement company
            List<Integer> programmingLanguageIdList = new ArrayList<>();
            List<Integer> frameworkIdList = new ArrayList<>();
            List<Integer> databaseIdList = new ArrayList<>();
            List<Integer> modellingIdList = new ArrayList<>();
            List<Integer> communicationLanguageList = new ArrayList<>();
            List<Integer> toolList = new ArrayList<>();

            for (CompetenceCompany competenceCompany : companyReqResponse.getCompetence()) {
                if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.PROGRAMMING_LANGUAGE)) {
                    programmingLanguageIdList.add(competenceCompany.getCompetenceId());
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.FRAMEWORK)) {
                    frameworkIdList.add(competenceCompany.getCompetenceId());
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.DATABASE)) {
                    databaseIdList.add(competenceCompany.getCompetenceId());
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.MODELLING)) {
                    modellingIdList.add(competenceCompany.getCompetenceId());
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.COMMUNICATION_LANGUAGE)) {
                    communicationLanguageList.add(competenceCompany.getCompetenceId());
                } else if (Objects.equals(competenceCompany.getCompetenceType(), Constant.CompetenceConstant.TOOL)) {
                    toolList.add(competenceCompany.getCompetenceId());
                }
            }

            if (jobsScopeId.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(0));
            }
            if (programmingLanguageIdList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(1));
            }
            if (databaseIdList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(2));
            }
            if (frameworkIdList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(3));
            }
            if (toolList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(4));
            }
            if (modellingIdList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(5));
            }
            if (communicationLanguageList.isEmpty()) {
                totalBobot.set(0, totalBobot.get(0) - bobotCriteria.get(6));
            }

            // Looping for Compatibility Rating
            for (ParticipantReqResponse participantReqResponse : participants) {
                // Initialization for each company's SAW calculation (Compatibility Rating)
                CalculationSAW compatibilityRating = new CalculationSAW();
                compatibilityRating.setIdCompany(companyReqResponse.getIdCompany());

                compatibilityRating.setIdParticipant(participantReqResponse.getIdParticipant());

                // Job scope matching
                if (!jobsScopeId.isEmpty()) {
                    if (participantReqResponse.getJobscope() != null) {
                        participantReqResponse.getJobscope().forEach(jobscope -> {
                            if (jobsScopeId.contains(jobscope.getJobscopeId())) {
                                compatibilityRating.getCriteriaMapping()
                                        .setCriteria1(compatibilityRating.getCriteriaMapping().getCriteria1() + 1);
                            }
                        });
                    }
                }

                // Programming matching
                if (!programmingLanguageIdList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.PROGRAMMING_LANGUAGE)
                            .forEach(competence -> {
                                if (programmingLanguageIdList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria2(compatibilityRating.getCriteriaMapping().getCriteria2()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // database matching
                if (!databaseIdList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.DATABASE)
                            .forEach(competence -> {
                                if (databaseIdList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria3(compatibilityRating.getCriteriaMapping().getCriteria3()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // framework matching
                if (!frameworkIdList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.FRAMEWORK)
                            .forEach(competence -> {
                                if (frameworkIdList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria4(compatibilityRating.getCriteriaMapping().getCriteria4()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // tools matching
                if (!toolList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.TOOL)
                            .forEach(competence -> {
                                if (toolList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria5(compatibilityRating.getCriteriaMapping().getCriteria5()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // modelling matching
                if (!modellingIdList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.MODELLING)
                            .forEach(competence -> {
                                if (modellingIdList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria6(compatibilityRating.getCriteriaMapping().getCriteria6()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // communication language matching
                if (!communicationLanguageList.isEmpty()) {
                    participantReqResponse.getCompetence().stream().filter(
                            comp -> comp.getCompetenceType() == Constant.CompetenceConstant.COMMUNICATION_LANGUAGE)
                            .forEach(competence -> {
                                if (communicationLanguageList.contains(competence.getCompetenceId())) {
                                    compatibilityRating.getCriteriaMapping()
                                            .setCriteria7(compatibilityRating.getCriteriaMapping().getCriteria7()
                                                    + SAWUtil.getPoint(competence.getKnowledgeId()));
                                }
                            });
                }

                // Company selection matching
                List<CompanySelection> select = participantChoose.stream()
                        .filter(p -> p.getParticipantId() == participantReqResponse.getIdParticipant())
                        .collect(Collectors.toList());
                if (select.size() == 1) {
                    compatibilityRating.getCriteriaMapping().setCriteria9(Double.valueOf(select.get(0).getPriority()));
                } else if (select.size() > 1) {
                    Integer priority = select.stream().min(Comparator.comparing(CompanySelection::getPriority))
                            .map(CompanySelection::getPriority).orElse(0);
                    compatibilityRating.getCriteriaMapping().setCriteria9(Double.valueOf(priority));
                }

                // Domicile matching
                if (Objects.equals(companyReqResponse.getIdDomicile(), participantReqResponse.getIdDomicile())) {
                    compatibilityRating.getCriteriaMapping().setCriteria8(1.0);
                }

                // Save Minimal and Maximal value
                minMaxCriteriaMapping.setCriteria1(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria1(),
                        compatibilityRating.getCriteriaMapping().getCriteria1(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria2(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria2(),
                        compatibilityRating.getCriteriaMapping().getCriteria2(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria3(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria3(),
                        compatibilityRating.getCriteriaMapping().getCriteria3(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria4(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria4(),
                        compatibilityRating.getCriteriaMapping().getCriteria4(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria5(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria5(),
                        compatibilityRating.getCriteriaMapping().getCriteria5(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria6(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria6(),
                        compatibilityRating.getCriteriaMapping().getCriteria6(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria7(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria7(),
                        compatibilityRating.getCriteriaMapping().getCriteria7(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria8(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria8(),
                        compatibilityRating.getCriteriaMapping().getCriteria8(), Boolean.FALSE));
                minMaxCriteriaMapping.setCriteria9(SAWUtil.getMinMax(minMaxCriteriaMapping.getCriteria9(),
                        compatibilityRating.getCriteriaMapping().getCriteria9(), Boolean.TRUE));

                // Add to list
                compatibilityRatingList.add(compatibilityRating);
            }

            // Looping for Normalization
            for (CalculationSAW compatibilityRating : compatibilityRatingList) {
                CalculationSAW normalizationXWeight = new CalculationSAW();
                normalizationXWeight.setIdCompany(compatibilityRating.getIdCompany());
                normalizationXWeight.setIdParticipant(compatibilityRating.getIdParticipant());

                // Normalization multiple weight
                if (jobsScopeId.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.JOBSCOPE))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria1(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria1(),
                                                    minMaxCriteriaMapping.getCriteria1(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));
                }

                if (programmingLanguageIdList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.PROGRAMMING_LANGUAGE))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria2(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria2(),
                                                    minMaxCriteriaMapping.getCriteria2(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                if (databaseIdList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.DATABASE))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria3(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria3(),
                                                    minMaxCriteriaMapping.getCriteria3(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                if (frameworkIdList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.FRAMEWORK))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria4(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria4(),
                                                    minMaxCriteriaMapping.getCriteria4(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                if (toolList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.TOOL))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria5(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria5(),
                                                    minMaxCriteriaMapping.getCriteria5(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                if (modellingIdList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.MODELLING))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria6(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria6(),
                                                    minMaxCriteriaMapping.getCriteria6(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                if (communicationLanguageList.size() != 0) {
                    listCriteriaMappingMapping.stream()
                            .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.COMMUNICATION_LANGUAGE))
                            .findFirst()
                            .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                    .setCriteria7(
                                            SAWUtil.normalization(
                                                    compatibilityRating.getCriteriaMapping().getCriteria7(),
                                                    minMaxCriteriaMapping.getCriteria7(), value.getIsCost())
                                                    * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                }
                listCriteriaMappingMapping.stream()
                        .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.DOMICILE))
                        .findFirst()
                        .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                .setCriteria8(
                                        SAWUtil.normalization(compatibilityRating.getCriteriaMapping().getCriteria8(),
                                                minMaxCriteriaMapping.getCriteria8(), value.getIsCost())
                                                * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));

                listCriteriaMappingMapping.stream()
                        .filter(x -> Objects.equals(x.getId(), Constant.CriteriaConstant.INTEREST_COMPANY))
                        .findFirst()
                        .ifPresent(value -> normalizationXWeight.getCriteriaMapping()
                                .setCriteria9(
                                        SAWUtil.normalization(
                                                compatibilityRating.getCriteriaMapping().getCriteria9(),
                                                minMaxCriteriaMapping.getCriteria9(), value.getIsCost())
                                                * (value.getPercentageValue() / totalBobot.get(0) * 100) / 100));
                finalScoreCalculationSaw.add(normalizationXWeight);
            }
        }

        List<ParticipantRanking> participantRankings = new ArrayList<>();
        for (CalculationSAW calculationSAW : finalScoreCalculationSaw) {
            ParticipantRanking participantRanking = new ParticipantRanking();
            participantRanking.setParticipantId(calculationSAW.getIdParticipant());
            participantRanking.setCompanyId(calculationSAW.getIdCompany());
            participantRanking.setValue(calculationSAW.getFinalScore());
            participantRanking.setYear(currentYear);
            participantRanking.setProdiId(idProdi);
            participantRankings.add(participantRanking);
        }

        participantRankingRepository.deleteByYear(currentYear,idProdi);
        participantRankingRepository.saveAll(participantRankings);

        Date date = new Date();
        UtilityDate ud = utilityDateRepository.findByIdUtilityAndYearAndProdiId(1, currentYear, idProdi);
        if (ud != null) {
            ud.setUpdatedAt(date);
            utilityDateRepository.save(ud);
        } else {
            UtilityDate udNew = new UtilityDate();
            udNew.setIdUtility(1);
            udNew.setYear(currentYear);
            udNew.setProdiId(idProdi);
            udNew.setUpdatedAt(date);
            utilityDateRepository.save(udNew);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<CriteriaMapping> getCriteria() {
        return criteriaMappingRepository.findAll();
    }

    @Override
    public void updateCriteria(List<CriteriaMapping> criteriaMappingRequest) {
        criteriaMappingRequest.forEach(cr -> {
            Optional<CriteriaMapping> criteria = criteriaMappingRepository.findById(cr.getId());

            criteria.ifPresent(c -> {
                c.setPercentageValue(cr.getPercentageValue());
                criteriaMappingRepository.save(c);
            });
        });
    }

    @Override
    public RankingAndDateResponse getRanking(String cookie, Integer idProdi) {
        RankingAndDateResponse rankingAndDateResponse = new RankingAndDateResponse();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<RankingResponse> rankingResponse = new ArrayList<>();

        // Initialization for use rest api
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<CompanyResponse>> companyReq = restTemplate.exchange(
                "http://company-service/company/get-all",
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        List<CompanyResponse> companyResponses = Objects.requireNonNull(companyReq.getBody()).getData();
        ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                "http://participant-service/participant/get-all?year=" + currentYear,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<ParticipantResponse> participantResponses = Objects.requireNonNull(pResponse.getBody()).getData();

        ResponseEntity<ResponseList<QuotaResponse>> qResponse = restTemplate.exchange(
                "http://company-service/company/prerequisite/quota/",
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<QuotaResponse> quotaResponses = Objects.requireNonNull(qResponse.getBody()).getData();

        UtilityDate utilityDate = utilityDateRepository.findByIdUtilityAndYearAndProdiId(1, currentYear, idProdi);

        List<ParticipantRanking> participantRankingList = participantRankingRepository.findByYearAndProdiId(currentYear,
                idProdi);
        participantRankingList.forEach(pr -> {
            ParticipantValue participantValue = new ParticipantValue();

            participantResponses.stream().filter(p -> p.getIdParticipant().equals(pr.getParticipantId())).findFirst()
                    .ifPresent(p -> {
                        participantValue.setParticipantName(p.getName());
                    });

            participantValue.setResult(pr.getValue());

            final String[] companyName = new String[1];
            companyResponses.stream().filter(c -> c.getIdCompany().equals(pr.getCompanyId())).findFirst()
                    .ifPresent(c -> {
                        companyName[0] = c.getCompanyName();
                    });

            rankingResponse.stream().filter(rr -> rr.getCompanyName().equals(companyName[0])).findFirst()
                    .ifPresentOrElse(rr -> {
                        rr.getParticipants().add(participantValue);
                    }, () -> {
                        RankingResponse newRanking = new RankingResponse();
                        newRanking.setCompanyName(companyName[0]);
                        newRanking.setParticipants(new ArrayList<>());
                        quotaResponses.stream().filter(q -> q.getIdCompany().equals(pr.getCompanyId()))
                                .findFirst().ifPresent(q -> {
                                    if (pr.getProdiId() == 0) {
                                        newRanking.setQuota(q.getQuotaD3());
                                    } else {
                                        newRanking.setQuota(q.getQuotaD4());
                                    }
                                });
                        newRanking.getParticipants().add(participantValue);
                        rankingResponse.add(newRanking);
                    });
            rankingAndDateResponse.setRankingList(rankingResponse);

            if(utilityDate != null) {
                rankingAndDateResponse.setDate(utilityDate.getUpdatedAt().toString());
            }else{
                rankingAndDateResponse.setDate("");
            }
        });

        return rankingAndDateResponse;
    }

    @Override
    public void exportMapping(String cookie, HttpServletResponse response, Integer idProdi) {
        // Initialization for use rest api
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Get all requirement Company
        ResponseEntity<ResponseList<CompanyReqResponse>> companyReq = restTemplate.exchange(
                "http://company-service/company/req-company",
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });
        List<CompanyReqResponse> listCompany = Objects.requireNonNull(companyReq.getBody()).getData();

        // Get all Participant Course Value
        ResponseEntity<ResponseList<ParticipantValueList>> participantCourseValue = restTemplate.exchange(
                "http://participant-service/participant/participant-value",
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<ParticipantValueList> participantValueLists = Objects.requireNonNull(participantCourseValue.getBody())
                .getData();

        // Get all Absence
        ResponseEntity<ResponseList<AbsenceResponse>> absenceResponse = restTemplate.exchange(
                "http://participant-service/participant/all-absence",
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        List<AbsenceResponse> absenceResponses = Objects.requireNonNull(absenceResponse.getBody()).getData();

        // Get all course
        ResponseEntity<ResponseList<CourseResponse>> courseResponse = restTemplate.exchange(
                "http://participant-service/participant/course",
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });

        ResponseEntity<ResponseList<JobscopeGetAll>> jobsScopeList = restTemplate.exchange(
                "http://management-content-service/management-content/jobscope/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        ResponseEntity<ResponseList<CompetenceResponse>> competenceList = restTemplate.exchange(
                "http://management-content-service/management-content/competence/get-all/" + idProdi,
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                });

        RankingAndDateResponse rankingAndDateResponse = getRanking(cookie, idProdi);

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=records_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<CompanyReqSheetResponse> companyReqSheetResponseList = new ArrayList<>();
        for (CompanyReqResponse c : listCompany) {
            CompanyReqSheetResponse companyReqSheetResponse = new CompanyReqSheetResponse();
            companyReqSheetResponse.setCompanyName(c.getCompanyName());
            companyReqSheetResponse.setIdCompany(c.getIdCompany());
            companyReqSheetResponse.setJobscope(c.getJobscope());
            companyReqSheetResponse.setJobscope(c.getJobscope());
            companyReqSheetResponse.setCompetence(c.getCompetence());

            try {
                ResponseEntity<Response<String>> region = restTemplate
                        .exchange("http://management-content-service/management-content/domicile/" +
                                c.getIdDomicile(), HttpMethod.GET, req, new ParameterizedTypeReference<>() {
                                });
                if (region.getStatusCode().is2xxSuccessful()) {
                    companyReqSheetResponse.setDomicile(Objects.requireNonNull(region.getBody()).getData());
                }
            } catch (Exception e) {
                companyReqSheetResponse.setDomicile("");
            }
            companyReqSheetResponseList.add(companyReqSheetResponse);
        }
        MappingExcel generator = new MappingExcel(companyReqSheetResponseList, rankingAndDateResponse,
                participantValueLists, absenceResponses, Objects.requireNonNull(competenceList.getBody()).getData(),
                Objects.requireNonNull(jobsScopeList.getBody()).getData(),
                Objects.requireNonNull(courseResponse.getBody()).getData());

        try {
            generator.generate(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createFinalMapping(FinalMappingRequest finalMappingRequest, Integer idProdi) {
        FinalMapping fm = new FinalMapping();
        fm.setCompanyId(finalMappingRequest.getCompanyId());
        fm.setParticipantId(finalMappingRequest.getParticipantId());
        fm.setProdiId(idProdi);
        finalMappingRepository.save(fm);
    }

    @Override
    public void deleteFinalMapping(Integer idParticipant, Integer idProdi) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        finalMappingRepository.deleteByParticipantIdYearAndProdiId(idParticipant, currentYear, idProdi);
    }

    @Override
    public void submitFinalMapping(Integer idProdi, String cookie) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int id = 1;
        if (idProdi == EProdi.D4.id) {
            id = 2;
        }

        utilityRepository.findById(id).ifPresent(u -> {
            Integer currentFinal = u.getIsFinal();

            if (currentFinal == 0) {
                List<FinalMapping> fmList = finalMappingRepository.findByYearAndProdiId(currentYear, idProdi);

                // Create evaluation (Company)
                JSONArray jsonArray = new JSONArray();
                fmList.forEach(fm -> {
                    JSONObject cer = new JSONObject();
                    cer.put("company_id", fm.getCompanyId());
                    cer.put("participant_id", fm.getParticipantId());
                    cer.put("prodi_id", fm.getProdiId());

                    jsonArray.put(cer);
                });

                HttpHeaders headers = new HttpHeaders();
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> req = new HttpEntity<>(jsonArray.toString(), headers);

                ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                        "http://company-service/company/evaluation/create",
                        HttpMethod.POST,
                        req,
                        new ParameterizedTypeReference<ResponseList<ParticipantResponse>>() {
                        });

                if (pResponse.getStatusCode().is4xxClientError() || pResponse.getStatusCode().is5xxServerError()) {
                    throw new IllegalStateException("Error when creating company's evaluation");
                }

                u.setIsFinal(currentFinal + 1);
            }

            else if (currentFinal == 1) {
                // Delete All Evaluation (Company)
                HttpHeaders headers = new HttpHeaders();
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> req = new HttpEntity<>(headers);

                ResponseEntity<ParticipantResponse> response = restTemplate.exchange(
                        "http://company-service/company/evaluation/delete-all",
                        HttpMethod.DELETE,
                        req,
                        ParticipantResponse.class);

                if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    throw new IllegalStateException("Error when deleting company's evaluation");
                }

                u.setIsFinal(currentFinal - 1);
            }

            Date date = new Date();
            UtilityDate ud = utilityDateRepository.findByIdUtilityAndYearAndProdiId(2, currentYear, idProdi);
            if (ud != null) {
                ud.setUpdatedAt(date);
                utilityDateRepository.save(ud);
            } else {
                UtilityDate udNew = new UtilityDate();
                udNew.setIdUtility(2);
                udNew.setYear(currentYear);
                udNew.setProdiId(idProdi);
                udNew.setUpdatedAt(date);
                utilityDateRepository.save(udNew);
            }
            utilityRepository.save(u);
        });
    }

    @Override
    public void submitPublishMapping(Integer idProdi, String cookie) {
        int id = 3;
        if (idProdi == EProdi.D4.id) {
            id = 4;
        }

        utilityRepository.findById(id).ifPresent(u -> {
            Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
            List<FinalMapping> fmList = finalMappingRepository.findByYearAndProdiId(currentYear, idProdi);

            List<Integer> arr = new ArrayList<>();

            // Create feedback (Company)
            JSONArray jsonArray = new JSONArray();
            fmList.forEach(fm -> {
                if (arr.contains(fm.getCompanyId()) == false) {
                    JSONObject cer = new JSONObject();
                    cer.put("company_id", fm.getCompanyId());
                    cer.put("prodi_id", fm.getProdiId());

                    jsonArray.put(cer);
                    arr.add(fm.getCompanyId());
                }
            });

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> req = new HttpEntity<>(jsonArray.toString(), headers);

            ResponseEntity<ResponseList<ParticipantResponse>> pResponse = restTemplate.exchange(
                    "http://company-service/company/feedback/create",
                    HttpMethod.POST,
                    req,
                    new ParameterizedTypeReference<ResponseList<ParticipantResponse>>() {
                    });

            if (pResponse.getStatusCode().is4xxClientError() || pResponse.getStatusCode().is5xxServerError()) {
                throw new IllegalStateException("Error when creating company's feedback");
            }

            u.setIsFinal(1);
            utilityRepository.save(u);

            Date date = new Date();
            UtilityDate ud = utilityDateRepository.findByIdUtilityAndYearAndProdiId(3, currentYear, idProdi);
            if (ud != null) {
                ud.setUpdatedAt(date);
                utilityDateRepository.save(ud);
            } else {
                UtilityDate udNew = new UtilityDate();
                udNew.setIdUtility(3);
                udNew.setYear(currentYear);
                udNew.setProdiId(idProdi);
                udNew.setUpdatedAt(date);
                utilityDateRepository.save(udNew);
            }
        });
    }

    @Override
    public Integer getIsFinalMapping(Integer id) {
        List<Integer> ar = new ArrayList<>();

        utilityRepository.findById(id).ifPresent(u -> {
            ar.add(u.getIsFinal());
        });

        return ar.get(0);
    }

    @Override
    public void deleteCompany(Integer idCompany) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        finalMappingRepository.deleteByCompanyId(idCompany, currentYear);
    }

    @Override
    public void deleteCompanyByProdi(Integer idCompany, Integer idProdi) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        finalMappingRepository.deleteByCompanyIdAndProdiId(idCompany, currentYear, idProdi);
    }

    @Override
    public List<ParticipantByCompany> getParticipantByCompany(Integer idCompany, String cookie, Integer idProdi) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<ResponseList<ParticipantIdName>> pResponse = restTemplate.exchange(
                "http://participant-service/participant/get-all?type=dropdown&year=" + currentYear,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<>() {
                });
        // List Participant
        List<ParticipantIdName> participantResponseList = Objects.requireNonNull(pResponse.getBody()).getData();
        // List Rank
        List<ParticipantRanking> participantRankingList = participantRankingRepository
                .findByCompanyIdAndYearAndProdiId(idCompany, currentYear, idProdi);
        // List FinalMapping
        List<FinalMapping> finalMappingList = finalMappingRepository.findByYearAndProdiId(currentYear, idProdi);
        List<Integer> listIdParticipantFinal = finalMappingList.stream().map(FinalMapping::getParticipantId)
                .collect(Collectors.toList());

        participantRankingList = participantRankingList.stream()
                .filter(p -> !listIdParticipantFinal.contains(p.getParticipantId()))
                .sorted(Comparator.comparing(ParticipantRanking::getValue).reversed()).collect(Collectors.toList());
        List<ParticipantIdName> participantFilter = participantResponseList.stream()
                .filter(p -> !listIdParticipantFinal.contains(p.getId())).collect(Collectors.toList());

        List<ParticipantByCompany> participantByCompanyList = new ArrayList<>();
        for (ParticipantRanking pr : participantRankingList) {
            ParticipantByCompany pbc = new ParticipantByCompany();
            pbc.setId(pr.getParticipantId());
            participantResponseList.stream().filter(p -> p.getId().equals(pr.getParticipantId())).findFirst()
                    .ifPresent(p -> {
                        pbc.setName(p.getName());
                    });
            pbc.setValue(pr.getValue());
            participantByCompanyList.add(pbc);
        }

        List<Integer> listId = participantByCompanyList.stream().map(ParticipantByCompany::getId)
                .collect(Collectors.toList());

        for (ParticipantIdName p : participantFilter) {
            if (!listId.contains(p.getId())) {
                ParticipantByCompany pbc = new ParticipantByCompany();
                pbc.setId(p.getId());
                pbc.setName(p.getName());
                pbc.setValue(0.0);
                participantByCompanyList.add(pbc);
            }
        }

        return participantByCompanyList;
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void resetStatusMappingJob() {
        utilityRepository.findById(1).ifPresent(u -> {
            u.setIsFinal(0);
            utilityRepository.save(u);
        });

        utilityRepository.findById(2).ifPresent(u -> {
            u.setIsFinal(0);
            utilityRepository.save(u);
        });

        utilityRepository.findById(3).ifPresent(u -> {
            u.setIsFinal(0);
            utilityRepository.save(u);
        });

        utilityRepository.findById(4).ifPresent(u -> {
            u.setIsFinal(0);
            utilityRepository.save(u);
        });
    }
}
