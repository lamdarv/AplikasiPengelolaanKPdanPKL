package com.jtk.ps.api.unit;

import com.jtk.ps.api.dto.CompanySelectionDetail;
import com.jtk.ps.api.dto.CompanySelectionUpdate;
import com.jtk.ps.api.dto.ParticipantIdName;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.model.CV;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.ParticipantCompany;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.service.ParticipantService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {ParticipantServiceTest.class})
class ParticipantServiceTest {
    @InjectMocks
    private ParticipantService participantService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private CVRepository cvRepository;

    @Mock
    private ParticipantCompanyRepository participantCompanyRepository;

    @Mock
    private EducationRepository educationRepository;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private CVCompetenceRepository cvCompetenceRepository;

    @Mock
    private CVJobScopeRepository cvJobScopeRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SeminarRepository seminarRepository;

    @Mock
    private ChampionshipRepository championshipRepository;

    @Test
    void getNameAndIdCompaniesTest(){
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0));
        participants.add(new Participant(2, "name 2", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0));
        participants.add(new Participant(3, "name 3", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0));

        Mockito.when(participantRepository.findByYearAndProdiId(any(Integer.class), any(Integer.class))).thenReturn(participants);

        List<ParticipantIdName> participantIdNameList = participantService.getNameAndIdCompanies(0);
        assertEquals(3, participantIdNameList.size());
        assertEquals(1, participantIdNameList.get(0).getId());
        assertEquals("name 1", participantIdNameList.get(0).getName());
        assertEquals(2, participantIdNameList.get(1).getId());
        assertEquals("name 2", participantIdNameList.get(1).getName());
        assertEquals(3, participantIdNameList.get(2).getId());
        assertEquals("name 3", participantIdNameList.get(2).getName());
    }

    @Test
    void getParticipantByIdTest(){
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);
        List<Participant> participantList = new ArrayList<>();
        participantList.add(participant);
        Mockito.when(participantRepository.findByIdIn(ArgumentMatchers.anyList())).thenReturn(participantList);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        List<Participant> participants = participantService.getParticipantById(integerList);

        assertEquals(1, participants.size());
        assertEquals(1, participants.get(0).getId());
        assertEquals("name 1", participants.get(0).getName());
        assertEquals(3.43, participants.get(0).getIpk());
        assertEquals("WFH", participants.get(0).getWorkSystem());
        assertEquals(2022, participants.get(0).getYear());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusCv());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusInterest());
        assertEquals(1, participants.get(0).getAccountId());
        assertEquals(0, participants.get(0).getProdiId());
    }

    @Test
    void getParticipantByYearTest(){
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);
        List<Participant> participantList = new ArrayList<>();
        participantList.add(participant);

        Mockito.when(participantRepository.findByYearAndProdiId(any(Integer.class), any(Integer.class))).thenReturn(participantList);

        List<Participant> participants = participantService.getParticipantByYear(2022, 0);

        assertEquals(1, participants.size());
        assertEquals(1, participants.get(0).getId());
        assertEquals("name 1", participants.get(0).getName());
        assertEquals(3.43, participants.get(0).getIpk());
        assertEquals("WFH", participants.get(0).getWorkSystem());
        assertEquals(2022, participants.get(0).getYear());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusCv());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusInterest());
        assertEquals(1, participants.get(0).getAccountId());
        assertEquals(0, participants.get(0).getProdiId());
    }

    @Test
    void getParticipantByAccountIdTest(){
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);
        List<Participant> participantList = new ArrayList<>();
        participantList.add(participant);

        Mockito.when(participantRepository.findByAccountIdIn(anyList())).thenReturn(participantList);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        List<Participant> participants = participantService.getParticipantByAccountId(integerList);

        assertEquals(1, participants.size());
        assertEquals(1, participants.get(0).getId());
        assertEquals("name 1", participants.get(0).getName());
        assertEquals(3.43, participants.get(0).getIpk());
        assertEquals("WFH", participants.get(0).getWorkSystem());
        assertEquals(2022, participants.get(0).getYear());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusCv());
        assertEquals(Boolean.TRUE, participants.get(0).getStatusInterest());
        assertEquals(1, participants.get(0).getAccountId());
        assertEquals(0, participants.get(0).getProdiId());
    }

    @Test
    @Disabled
    void getCVParticipantTest(){}

    @Test
    @Disabled
    void getCVParticipantByCommitteeTest(){}

    @Test
    @Disabled
    void getCVDetailTest(){}

    @Test
    @Disabled
    void updateCVTest(){}

    @Test
    void markAsDoneCvTest(){
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);
        Mockito.when(participantRepository.findById(any(Integer.class))).thenReturn(Optional.of(participant));

        Boolean isResult = participantService.markAsDoneCv(1);

        assertEquals(Boolean.TRUE, isResult);
        Mockito.verify(participantRepository, Mockito.times(1)).save(any(Participant.class));
    }

    @Test
    void markAsDoneInterestTest(){
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);
        Mockito.when(participantRepository.findById(any(Integer.class))).thenReturn(Optional.of(participant));

        Boolean isResult = participantService.markAsDoneInterest(1);

        assertEquals(Boolean.TRUE, isResult);
        Mockito.verify(participantRepository, Mockito.times(1)).save(any(Participant.class));
    }

    @Test
    @Disabled
    void getCVParticipantByCompanyTest(){}

    @Test
    @Disabled
    void getCompanySelectionTest(){}

    @Test
    void getNameDomicileTest(){
        Integer id = 1;
        String cookie = "cookie";

        String domicile = "Bandung";
        Response<String> res = new Response<>(domicile,200, "OK");


        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/management-content/domicile/"+id),
                        any(HttpMethod.class),any(), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(res, HttpStatus.OK));

        String result = participantService.getNameDomicile(id,cookie);
        assertEquals(domicile, result);
    }

    @Test
    void getCompanySelectionCardTest(){
        Integer idParticipant = 1;
        String cookie = "cookie";
        Participant participant = new Participant(1, "name 1", 3.43, "WFH", 2022, Boolean.TRUE, Boolean.TRUE, 1, new CV(), 0);

        Mockito.when(participantRepository.findById(any(Integer.class))).thenReturn(Optional.of(participant));

        List<ParticipantCompany> listParticipantCompany = new ArrayList<>();

        listParticipantCompany.add(new ParticipantCompany(1, 1, 1, 1));
        listParticipantCompany.add(new ParticipantCompany(2, 2, 1, 2));
        listParticipantCompany.add(new ParticipantCompany(3, 3, 1, 3));

        Mockito.when(participantCompanyRepository.findByParticipantId(any(Integer.class))).thenReturn(listParticipantCompany);

        CompanySelectionDetail companySelectionDetail = participantService.getCompanySelectionDetail(idParticipant, cookie);

        assertEquals("WFH", companySelectionDetail.getWorkSystem());
        assertEquals(1 ,companySelectionDetail.getPriority1());
        assertEquals(2 ,companySelectionDetail.getPriority2());
        assertEquals(3 ,companySelectionDetail.getPriority3());
    }

    @Test
    @Disabled
    void updateCompanySelectionTest(){
        CompanySelectionUpdate companySelectionUpdate = new CompanySelectionUpdate("WFH", 1, 2, 3, 4, 5);
    }

    @Test
    void deleteCVCompetenceTest(){
        participantService.deleteCVCompetence(1);
        Mockito.verify(cvCompetenceRepository, Mockito.times(1)).deleteAllByCompetenceId(any(Integer.class));
    }

    @Test
    void deleteCVJobscopeTest(){
        participantService.deleteCVJobscope(1);
        Mockito.verify(cvJobScopeRepository, Mockito.times(1)).deleteAllByJobscopeId(any(Integer.class));
    }

    @Test
    @Disabled
    void getCVInterestParticipantTest(){}

    @Test
    void getCompanySelectionMappingTest(){
        List<ParticipantCompany> listParticipantCompany = new ArrayList<>();
        listParticipantCompany.add(new ParticipantCompany(1, 1, 1, 1));
        listParticipantCompany.add(new ParticipantCompany(2, 2, 1, 2));

        Mockito.when(participantCompanyRepository.findByProdiIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(listParticipantCompany);

        List<ParticipantCompany> list = participantService.getCompanySelectionMapping(0);

        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(2, list.get(1).getId());
        assertEquals(1, list.get(0).getParticipantId());
        assertEquals(1, list.get(0).getParticipantId());

        Mockito.verify(participantCompanyRepository, Mockito.times(1)).findByProdiIdAndYear(any(Integer.class), any(Integer.class));
    }
}
