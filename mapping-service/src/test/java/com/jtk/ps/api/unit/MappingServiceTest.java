package com.jtk.ps.api.unit;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.ranking.*;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.CriteriaMappingRepository;
import com.jtk.ps.api.repository.FinalMappingRepository;
import com.jtk.ps.api.repository.ParticipantRankingRepository;
import com.jtk.ps.api.repository.UtilityRepository;
import com.jtk.ps.api.service.MappingService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {MappingServiceTest.class})
class MappingServiceTest {
    @InjectMocks
    private MappingService mappingService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FinalMappingRepository finalMappingRepository;

    @Mock
    private CriteriaMappingRepository criteriaMappingRepository;

    @Mock
    private ParticipantRankingRepository participantRankingRepository;

    @Mock
    private UtilityRepository utilityRepository;

    @Mock
    WebClient.Builder webClient;

    @Mock
    WebClient webClientMock;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.ResponseSpec responseSpec;


    @Test
    void getFinalMappingTest() {
    }

    @Test
    void getParticipantFinalMappingTest() {
        List<FinalMapping> finalMappings = new ArrayList<>();
        finalMappings.add(new FinalMapping(1, 2022, 1, 1, 1));
        finalMappings.add(new FinalMapping(2, 2022, 1, 2, 1));
        finalMappings.add(new FinalMapping(3, 2022, 1, 3, 1));

        Mockito.when(finalMappingRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(finalMappings);

        List<ParticipantFinalMappingResponse> result = mappingService.getParticipantFinalMapping(1);

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getParticipantId());
        assertEquals(2, result.get(1).getParticipantId());
        assertEquals(3, result.get(2).getParticipantId());

        assertEquals(1, result.get(0).getIdProdi());
        assertEquals(1, result.get(1).getIdProdi());
        assertEquals(1, result.get(2).getIdProdi());
    }

    @Test
    void generateRankTest() {
        String cookie = "cookie";
        Integer idProdi = 0;


        List<CompanyReqResponse> companyReqResponseArrayList = new ArrayList<>();
        companyReqResponseArrayList.add(new CompanyReqResponse(1, "Company A", 1,
                Arrays.asList(new JobscopeResponse(1, 1,1), new JobscopeResponse(2,2,1)),
                Arrays.asList(new CompetenceCompany(1, 1, 1,1), new CompetenceCompany(2, 2, 1,1))));
        companyReqResponseArrayList.add(new CompanyReqResponse(2, "Company B", 1,
                Arrays.asList(new JobscopeResponse(1, 1,1), new JobscopeResponse(2,2,1)),
                Arrays.asList(new CompetenceCompany(1, 1, 1,1), new CompetenceCompany(2, 2, 1,1),
                        new CompetenceCompany(3, 3, 2,1))));

        ResponseList responseList = new ResponseList(companyReqResponseArrayList, HttpStatus.OK.value(), "Success");
        Mono<ResponseList> monoResponseList = Mono.just(responseList);


        List<ParticipantReqResponse> participantReqResponseArrayList = new ArrayList<>();
        participantReqResponseArrayList.add(new ParticipantReqResponse(1,1,
                Arrays.asList(new JobscopeResponse(1, 1,1), new JobscopeResponse(2,2,1)),
                Arrays.asList(new CompetenceParticipant(1, 1, 3,1), new CompetenceParticipant(2, 2, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(2,3,
                Arrays.asList(new JobscopeResponse(1, 1,1), new JobscopeResponse(2,2,1)),
                Arrays.asList(new CompetenceParticipant(1, 3, 3,1), new CompetenceParticipant(2, 4, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(3,4,
                Arrays.asList(new JobscopeResponse(1, 1,1), new JobscopeResponse(2,2,1)),
                Arrays.asList(new CompetenceParticipant(1, 5, 3,1), new CompetenceParticipant(2, 6, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(4,5,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 1, 3,1), new CompetenceParticipant(2, 2, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(5,6,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 3, 3,1), new CompetenceParticipant(2, 4, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(6,7,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 5, 3,1), new CompetenceParticipant(2, 6, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(7,8,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 1, 3,1), new CompetenceParticipant(2, 2, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(8,9,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 3, 3,1), new CompetenceParticipant(2, 4, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(9,10,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 5, 3,1), new CompetenceParticipant(2, 6, 3,1))));

        participantReqResponseArrayList.add(new ParticipantReqResponse(10,11,
                Arrays.asList(new JobscopeResponse(1, 2,1), new JobscopeResponse(2,4,1)),
                Arrays.asList(new CompetenceParticipant(1, 1, 3,1), new CompetenceParticipant(2, 2, 3,1))));

        ResponseList responseList2 = new ResponseList(participantReqResponseArrayList, HttpStatus.OK.value(), "Success");
        Mono<ResponseList> monoResponseList2 = Mono.just(responseList2);

        List<CompanySelection> companySelectionArrayList = new ArrayList<>();
        companySelectionArrayList.add(new CompanySelection(1, 1, 1, 1));
        companySelectionArrayList.add(new CompanySelection(2, 2, 2, 1));
        companySelectionArrayList.add(new CompanySelection(3, 3, 3, 1));
        companySelectionArrayList.add(new CompanySelection(4, 4, 4, 1));
        companySelectionArrayList.add(new CompanySelection(5, 5, 5, 1));
        companySelectionArrayList.add(new CompanySelection(6, 4, 6, 2));
        companySelectionArrayList.add(new CompanySelection(7, 3, 7, 2));
        companySelectionArrayList.add(new CompanySelection(8, 2, 8, 2));
        companySelectionArrayList.add(new CompanySelection(9, 1, 9, 2));
        companySelectionArrayList.add(new CompanySelection(10, 5, 10, 2));

        ResponseList responseList3 = new ResponseList(companySelectionArrayList, HttpStatus.OK.value(), "Success");
        Mono<ResponseList> monoResponseList3 = Mono.just(responseList3);


        List<CriteriaMapping> criteriaMappingArrayList = new ArrayList<>();
        criteriaMappingArrayList.add(new CriteriaMapping(1, "Minat Pekerjaan", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(2, "Bahasa Pemrograman", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(3, "Database", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(4, "Framework", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(5, "Tools", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(6, "Modelling Tools", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(7, "Bahasa yang Dikuasai", Boolean.FALSE, 10f));
        criteriaMappingArrayList.add(new CriteriaMapping(8, "Domisili", Boolean.FALSE, 5f));
        criteriaMappingArrayList.add(new CriteriaMapping(9, "Minat Peserta Terhadap Perusahaan", Boolean.TRUE, 25f));

        Mockito.when(criteriaMappingRepository.findAll()).thenReturn(criteriaMappingArrayList);

        Mockito.when(webClient.build()).thenReturn(webClientMock);
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(webClient.build().get().uri(anyString()).header(anyString(), anyString()).retrieve().bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(monoResponseList).thenReturn(monoResponseList2).thenReturn(monoResponseList3);
        Boolean result = mappingService.generateRank(cookie, idProdi);

        assertEquals(true, result);

        Mockito.verify(participantRankingRepository, Mockito.times(1)).deleteAll();
        Mockito.verify(participantRankingRepository, Mockito.times(1)).saveAll(anyList());

    }

    @Test
    void getCriteriaTest() {
        List<CriteriaMapping> mappingList = new ArrayList<>();
        mappingList.add(new CriteriaMapping(1, "Criteria 1", false, 20.0f));
        mappingList.add(new CriteriaMapping(2, "Criteria 2", false, 30.0f));
        mappingList.add(new CriteriaMapping(3, "Criteria 3", false, 40.0f));
        mappingList.add(new CriteriaMapping(4, "Criteria 4", true, 10.0f));

        Mockito.when(criteriaMappingRepository.findAll()).thenReturn(mappingList);

        List<CriteriaMapping> criteriaMappings = mappingService.getCriteria();

        assertEquals(4, criteriaMappings.size());
        assertEquals(1, criteriaMappings.get(0).getId());
        assertEquals(2, criteriaMappings.get(1).getId());
        assertEquals(3, criteriaMappings.get(2).getId());
        assertEquals(4, criteriaMappings.get(3).getId());

        assertEquals("Criteria 1", criteriaMappings.get(0).getName());
        assertEquals("Criteria 2", criteriaMappings.get(1).getName());
        assertEquals("Criteria 3", criteriaMappings.get(2).getName());
        assertEquals("Criteria 4", criteriaMappings.get(3).getName());

        assertEquals(false, criteriaMappings.get(0).getIsCost());
        assertEquals(false, criteriaMappings.get(1).getIsCost());
        assertEquals(false, criteriaMappings.get(2).getIsCost());
        assertEquals(true, criteriaMappings.get(3).getIsCost());

        assertEquals(20.0f, criteriaMappings.get(0).getPercentageValue());
        assertEquals(30.0f, criteriaMappings.get(1).getPercentageValue());
        assertEquals(40.0f, criteriaMappings.get(2).getPercentageValue());
        assertEquals(10.0f, criteriaMappings.get(3).getPercentageValue());
    }

    @Test
    void updateCriteriaTest() {
        List<CriteriaMapping> criteriaMappingRequest = new ArrayList<>();
        criteriaMappingRequest.add(new CriteriaMapping(1, "Criteria 1", false, 20.0f));
        criteriaMappingRequest.add(new CriteriaMapping(2, "Criteria 2", false, 30.0f));

        Mockito.when(criteriaMappingRepository.findById(1)).thenReturn(Optional.of(new CriteriaMapping(1, "Criteria 1", false, 20.0f)));
        Mockito.when(criteriaMappingRepository.findById(2)).thenReturn(Optional.of(new CriteriaMapping(2, "Criteria 2", false, 30.0f)));

        mappingService.updateCriteria(criteriaMappingRequest);

        Mockito.verify(criteriaMappingRepository, Mockito.times(2)).save(any(CriteriaMapping.class));
    }

    @Test
    @Disabled
    void getRankingTest() {
    }

    @Test
    @Disabled
    void exportMappingTest() {
    }

    @Test
    void createFinalMappingTest() {
        FinalMappingRequest finalMappingRequest = new FinalMappingRequest(1, 1);
        Integer idProdi = 0;

        mappingService.createFinalMapping(finalMappingRequest, idProdi);

        Mockito.verify(finalMappingRepository, Mockito.times(1)).save(any(FinalMapping.class));
    }

    @Test
    void deleteFinalMappingTest() {
        mappingService.deleteFinalMapping(1, 1);

        Mockito.verify(finalMappingRepository, Mockito.times(1)).deleteByParticipantIdYearAndProdiId(any(Integer.class), any(Integer.class), any(Integer.class));
    }

    @Test
    @Disabled
    void submitFinalMappingTest() {
    }

    @Test
    void getIsFinalMappingTest() {
        Mockito.when(utilityRepository.findById(1)).thenReturn(Optional.of(new Utility(1, 1, 1)));
        mappingService.getIsFinalMapping(1);

        Mockito.verify(utilityRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void deleteCompanyTest() {
        mappingService.deleteCompany(1);

        Mockito.verify(finalMappingRepository, Mockito.times(1)).deleteByCompanyId(any(Integer.class), any(Integer.class));
    }


}
