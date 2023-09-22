package com.jtk.ps.api.unit;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.service.ManagementContentService;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {ManagementContentServiceTest.class})
class ManagementContentServiceTest {
    @InjectMocks
    private ManagementContentService managementContentService;

    @Mock
    TimelineSettingRepository timelineSettingRepository;

    @Mock
    FormSettingRepository formSettingRepository;

    @Mock
    RegionRepository regionRepository;

    @Mock
    CompetenceRepository competenceRepository;

    @Mock
    CompetenceTypeRepository competenceTypeRepository;

    @Mock
    JobscopeRepository jobscopeRepository;

    @Mock
    AssessmentAspectRepository assessmentAspectRepository;

    @Mock
    EvaluationFormRepository evaluationFormRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void createTimelineTest(){
        TimelineRequest timelineRequest = new TimelineRequest();
        timelineRequest.setName("test");
        timelineRequest.setDescription("test");
        timelineRequest.setStartDate("2020/01/01");
        timelineRequest.setEndDate("2020/01/01");
        timelineRequest.setProdiId(0);

        managementContentService.createTimeline(timelineRequest, 0);

        Mockito.verify(timelineSettingRepository).save(ArgumentMatchers.any(TimelineSetting.class));
    }

    @Test
    @Disabled
    void getTimelineWithParameterProdiTest(){}

    @Test
    @Disabled
    void getTimelineTest(){}

    @Test
    void updateTimelineTest(){
        TimelineRequest timelineRequest = new TimelineRequest();
        timelineRequest.setName("test");
        timelineRequest.setDescription("test");
        timelineRequest.setStartDate("2020/01/01");
        timelineRequest.setEndDate("2020/01/01");
        timelineRequest.setProdiId(0);

        Mockito.when(timelineSettingRepository.findById(1)).thenReturn(Optional.of(new TimelineSetting()));

        managementContentService.updateTimeline(1,timelineRequest, 0);

        Mockito.verify(timelineSettingRepository).save(ArgumentMatchers.any(TimelineSetting.class));
    }

    @Test
    void deleteTimelineTest(){
        Integer id = 1;
        managementContentService.deleteTimeline(id);

        Mockito.verify(formSettingRepository).updateTimelineSettingToNull(id);
        Mockito.verify(timelineSettingRepository).deleteById(id);
    }

    @Test
    @Disabled
    void getFormSubmitTimeTest(){}

    @Test
    void updateFormSubmitTimeTest(){
        Integer id = 1;
        FormSubmitUpdateRequest formSubmitUpdateRequest = new FormSubmitUpdateRequest();
        formSubmitUpdateRequest.setIdTimeline(2);

        FormSetting formSetting = new FormSetting();
        formSetting.setId(1);
        formSetting.setName("test");
        formSetting.setProdiId(0);
        formSetting.setTimelineSetting(new TimelineSetting());

        Mockito.when(formSettingRepository.findById(any(Integer.class))).thenReturn(Optional.of(formSetting));

        TimelineSetting timelineSetting = new TimelineSetting();
        timelineSetting.setId(2);
        timelineSetting.setName("test");
        timelineSetting.setProdiId(0);
        timelineSetting.setDescription("test");
        timelineSetting.setStartDate(new Date());
        timelineSetting.setEndDate(new Date());

        Mockito.when(timelineSettingRepository.findById(any(Integer.class))).thenReturn(Optional.of(timelineSetting));

        managementContentService.updateFormSubmitTime(id, formSubmitUpdateRequest);

        Mockito.verify(formSettingRepository).save(ArgumentMatchers.any(FormSetting.class));
    }

    @Test
    void getFormSubmitTimeByIdTest(){
        FormSetting formSetting = new FormSetting();
        formSetting.setId(1);
        formSetting.setName("test");
        formSetting.setProdiId(0);
        formSetting.setTimelineSetting(new TimelineSetting());

        Mockito.when(formSettingRepository.findById(any(Integer.class))).thenReturn(Optional.of(formSetting));
        managementContentService.getFormSubmitTime(1);

        assertEquals(1, formSetting.getId());
        assertEquals("test", formSetting.getName());
        assertEquals(0, formSetting.getProdiId());
        assertEquals(new TimelineSetting(), formSetting.getTimelineSetting());
    }

    @Test
    void getDomicileLikeTest(){
        List<Region> regions = new ArrayList<>();
        Region region = new Region();
        region.setId(1);
        region.setRegionName("Tempat 1");
        regions.add(region);

        Mockito.when(regionRepository.searchByRegionName(any(String.class))).thenReturn(regions);

        List<Region> regionList= managementContentService.getDomicileLike("Tempat 1");

        assertEquals(1, regionList.get(0).getId());
        assertEquals("Tempat 1", regionList.get(0).getRegionName());
    }

    @Test
    void getDomicileByIdTest(){
        Region region = new Region();
        region.setId(1);
        region.setRegionName("Tempat 1");

        Mockito.when(regionRepository.findById(any(Integer.class))).thenReturn(Optional.of(region));

        Region region1 = managementContentService.getDomicileById(1);

        assertEquals(1, region1.getId());
        assertEquals("Tempat 1", region1.getRegionName());
    }

    @Test
    void getTypeCompetenceTest(){
        Competence competence = new Competence(1, "Competence 1", "PIC 1", new CompetenceType(1, "Type 1", "Description 1"), 0);

        Mockito.when(competenceRepository.findById(any(Integer.class))).thenReturn(Optional.of(competence));

        Integer type = managementContentService.getTypeCompetence(1);

        assertEquals(1, type);
    }

    @Test
    @Disabled
    void getAllCompetenceTest(){}

    @Test
    void getAllTypeCompetenceTest(){
        List<CompetenceType> competenceTypes = new ArrayList<>();
        competenceTypes.add( new CompetenceType(1, "Type 1", "Description 1"));
        competenceTypes.add( new CompetenceType(2, "Type 2", "Description 2"));

        Mockito.when(competenceTypeRepository.findAll()).thenReturn(competenceTypes);

        List<CompetenceType> typeCompetenceList = managementContentService.getAllTypeCompetence();

        assertEquals(2, typeCompetenceList.size());
        assertEquals(1, typeCompetenceList.get(0).getId());
        assertEquals("Type 1", typeCompetenceList.get(0).getName());
        assertEquals("Description 1", typeCompetenceList.get(0).getDescription());
    }

    @Test
    void createCompetenceTest(){
        CompetenceRequest competenceRequest = new CompetenceRequest();
        competenceRequest.setName("Competence 1");
        competenceRequest.setIdCompetenceType(1);

        Mockito.when(competenceTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(new CompetenceType(1, "Name 1", "Description 1")));

        managementContentService.createCompetence(competenceRequest, "PIC 1");

        Mockito.verify(competenceRepository).save(ArgumentMatchers.any(Competence.class));
    }

    @Test
    void updateCompetenceTest(){
        CompetenceRequest competenceRequest = new CompetenceRequest();
        competenceRequest.setName("Competence 1");
        competenceRequest.setIdCompetenceType(1);

        Mockito.when(competenceRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Competence(1, "Competence 1", "PIC 1", new CompetenceType(1, "Type 1", "Description 1"), 0)));

        managementContentService.updateCompetence(1, competenceRequest, "PIC 1");

        Mockito.verify(competenceRepository).save(ArgumentMatchers.any(Competence.class));
    }

    @Test
    @Disabled
    void deleteCompetenceTest(){}

    @Test
    void getAllJobscopeTest(){
        List<Jobscope> jobscopes = new ArrayList<>();
        jobscopes.add(new Jobscope(1, "Jobscope 1", "Description 1", 0));
        jobscopes.add(new Jobscope(2, "Jobscope 2", "Description 2", 0));

        Mockito.when(jobscopeRepository.findAll()).thenReturn(jobscopes);

        List<Jobscope> jobscopeList = managementContentService.getAllJobscope(0);

        assertEquals(2, jobscopeList.size());
        assertEquals(1, jobscopeList.get(0).getId());
        assertEquals("Jobscope 1", jobscopeList.get(0).getName());
        assertEquals("Description 1", jobscopeList.get(0).getPic());
    }

    @Test
    void createJobscopeTest(){
        JobscopeRequest jobscopeRequest = new JobscopeRequest();
        jobscopeRequest.setName("Jobscope 1");

        managementContentService.createJobscope(jobscopeRequest, "PIC 1");

        Mockito.verify(jobscopeRepository).save(ArgumentMatchers.any(Jobscope.class));
    }

    @Test
    void updateJobscopeTest(){
        JobscopeRequest jobscopeRequest = new JobscopeRequest();
        jobscopeRequest.setName("Jobscope 1");

        Mockito.when(jobscopeRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Jobscope(1, "Jobscope 1", "PIC 1", 0)));

        managementContentService.updateJobscope(1, jobscopeRequest, "PIC 1");

        Mockito.verify(jobscopeRepository).save(ArgumentMatchers.any(Jobscope.class));
    }

    @Test
    @Disabled
    void deleteJobscopeTest(){}

    @Test
    void getAssessmentAspectFormTest(){
        Integer idProdi = 1;
        Integer numEvaluation = 1;

        List<AssessmentAspect> assessmentAspects = new ArrayList<>();
        assessmentAspects.add(new AssessmentAspect(1, "Aspect 1", new EvaluationForm()));
        assessmentAspects.add(new AssessmentAspect(2, "Aspect 2", new EvaluationForm()));


        Mockito.when(assessmentAspectRepository.findByProdiIdAndNumEvaluation(idProdi, numEvaluation)).thenReturn(assessmentAspects);

        List<String> assessmentAspectList = managementContentService.getAssessmentAspectForm(idProdi, numEvaluation);

        assertEquals(2, assessmentAspectList.size());
        assertEquals("Aspect 1", assessmentAspectList.get(0));
        assertEquals("Aspect 2", assessmentAspectList.get(1));
    }

    @Test
    void getAssessmentAspectD3Test(){
        List<AssessmentAspect> assessmentAspects = new ArrayList<>();
        assessmentAspects.add(new AssessmentAspect(1, "Aspect 1", new EvaluationForm()));
        assessmentAspects.add(new AssessmentAspect(2, "Aspect 2", new EvaluationForm()));

        Mockito.when(assessmentAspectRepository.findByProdiIdAndNumEvaluation(0, 1)).thenReturn(assessmentAspects);

        List<AssessmentAspect> assessmentAspectList = managementContentService.getAssessmentAspectD3();
        assertEquals(2, assessmentAspectList.size());
        assertEquals(1, assessmentAspectList.get(0).getId());
        assertEquals("Aspect 1", assessmentAspectList.get(0).getAspectName());
        assertEquals(2, assessmentAspectList.get(1).getId());
        assertEquals("Aspect 2", assessmentAspectList.get(1).getAspectName());
    }

    @Test
    @Disabled
    void getAssessmentAspectD4Test(){}

    @Test
    void createAssessmentAspectTest(){
        AssessmentAspectRequest assessmentAspectRequest = new AssessmentAspectRequest();
        assessmentAspectRequest.setAspectName("Aspect 1");
        assessmentAspectRequest.setEvaluationFormId(1);

        Mockito.when(evaluationFormRepository.findById(any(Integer.class))).thenReturn(Optional.of(new EvaluationForm(1, 0, 1)));

        managementContentService.createAssessmentAspect(assessmentAspectRequest);

        Mockito.verify(assessmentAspectRepository, Mockito.times(1)).save(ArgumentMatchers.any(AssessmentAspect.class));
    }

    @Test
    void updateAssessmentAspectTest(){
        AssessmentAspectRequest assessmentAspectRequest = new AssessmentAspectRequest();
        assessmentAspectRequest.setAspectName("Aspect 1");
        assessmentAspectRequest.setEvaluationFormId(1);

        Mockito.when(assessmentAspectRepository.findById(any(Integer.class))).thenReturn(Optional.of(new AssessmentAspect(1, "Aspect 1", new EvaluationForm())));

        managementContentService.updateAssessmentAspect(1, assessmentAspectRequest);

        Mockito.verify(assessmentAspectRepository, Mockito.times(1)).save(ArgumentMatchers.any(AssessmentAspect.class));
    }

    @Test
    void deleteAssessmentAspectTest(){
        managementContentService.deleteAssessmentAspect(1);

        Mockito.verify(assessmentAspectRepository, Mockito.times(1)).deleteById(1);
    }


}
