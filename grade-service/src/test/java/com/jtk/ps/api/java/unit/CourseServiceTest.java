package com.jtk.ps.api.java.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import com.jtk.ps.api.dto.ComponentAndCriteriasDto;
import com.jtk.ps.api.dto.ComponentCourseDto;
import com.jtk.ps.api.dto.CourseFormRequestDto;
import com.jtk.ps.api.dto.CourseFormResponseDto;
import com.jtk.ps.api.dto.CriteriaBodyDto;
import com.jtk.ps.api.dto.CriteriaEvaluationFormDto;
import com.jtk.ps.api.dto.EvaluationFormResponseDto;
import com.jtk.ps.api.dto.RecapitulationCourseDto;
import com.jtk.ps.api.dto.TypeOfAspectEvaluationDto;
import com.jtk.ps.api.model.AssessmentAspect;
import com.jtk.ps.api.model.ComponentCourse;
import com.jtk.ps.api.model.CourseForm;
import com.jtk.ps.api.model.CriteriaComponentCourse;
import com.jtk.ps.api.model.EvaluationForm;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.SelfAssessmentAspect;
import com.jtk.ps.api.model.SeminarCriteria;
import com.jtk.ps.api.model.SupervisorGradeAspect;
import com.jtk.ps.api.repository.AssessmentAspectRepository;
import com.jtk.ps.api.repository.ComponentCourseRepository;
import com.jtk.ps.api.repository.CourseFormRepository;
import com.jtk.ps.api.repository.CourseValuesRepository;
import com.jtk.ps.api.repository.CriteriaComponentCourseRepository;
import com.jtk.ps.api.repository.EvaluationFormRepository;
import com.jtk.ps.api.repository.ParticipantRepository;
import com.jtk.ps.api.repository.SelfAssessmentAspectRepository;
import com.jtk.ps.api.repository.SeminarCriteriaRepository;
import com.jtk.ps.api.repository.SupervisorGradeAspectRepository;
import com.jtk.ps.api.repository.TimelineRepository;
import com.jtk.ps.api.service.CourseService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {CourseServiceTest.class})
public class CourseServiceTest {
    
    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseFormRepository courseFormRepository;

    @Mock
    private ComponentCourseRepository componentCourseRepository;

    @Mock
    private SupervisorGradeAspectRepository supervisorGradeAspectRepository;

    @Mock
    private SelfAssessmentAspectRepository selfAssessmentAspectRepository;
    
    @Mock
    private SeminarCriteriaRepository seminarCriteriaRepository;

    @Mock
    private AssessmentAspectRepository assessmentAspectRepository;

    @Mock
    private TimelineRepository timelineRepository;

    @Mock
    private EvaluationFormRepository evaluationFormRepository;

    @Mock
    private CriteriaComponentCourseRepository criteriaComponentCourseRepository;

    @Mock
    private CourseValuesRepository courseValuesRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Test
    void getAllCourseTest(){
        Integer year = Year.now().getValue();
        List<CourseForm> courseForms = new ArrayList<>();
        courseForms.add(new CourseForm(1, 0, 1, "MK-1", "Course 1", 2023, 2024, 2, 0) );
        courseForms.add(new CourseForm(2, 0, 0, "MK-2", "Course 2", 2023, 2024, 2, 0) );

        when(courseFormRepository.findAllCourse(year)).thenReturn(courseForms);

        List<CourseFormResponseDto> result = courseService.getAllCourse(0);

        verify(courseFormRepository, times(1)).findAllCourse(year);

        assertEquals(result.size(), 2);
    }

    @Test
    void createCourseFormTest(){
        CourseFormRequestDto cFormDto = new CourseFormRequestDto(1, "XXX", "new Course", 2023, 2024, 2);
        CourseForm courseForm = new CourseForm(2,0,1,"XXX", "new Course", 2023, 2024, 2, 0);
        String[] components = {
            "EAS Praktek", "EAS Teori", "ETS Praktek", "ETS Teori", "Lain-lain Praktek", "Lain-lain Teori"
        };

        when(courseFormRepository.save(new CourseForm(null, 0,1,"XXX", "new Course", 2023, 2024, 2, 0)))
            .thenReturn(courseForm);
        for (String com : components) {
            ComponentCourse componentCourse = new ComponentCourse(null,com, 0, 2, 1);
            when(componentCourseRepository.save(componentCourse)).thenReturn(componentCourse);
        }
        
        CourseForm result = courseService.createCourseForm(cFormDto);

        verify(courseFormRepository).save(new CourseForm(null, 0,1,"XXX", "new Course", 2023, 2024, 2, 0));
        verify(componentCourseRepository, times(6)).save(any(ComponentCourse.class));

        assertEquals(courseForm, result);

    }


    @Test
    void getDetailCourseTest(){
        Integer courseId = 1;
        CourseForm course = new CourseForm(1, 0, 1, "MK-1", "Course 1", 2023, 2024, 2, 0);

        when(courseFormRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseFormResponseDto result = courseService.getDetailCourse(courseId);

        verify(courseFormRepository, times(1)).findById(courseId);

        assertEquals(course.getKode(), result.getKode());
        assertEquals(course.getName(), result.getName());
        assertEquals(course.getId(), result.getId());

    }

    @Test
    void updateCourseFormTest(){
        int idForm = 1;
        CourseFormRequestDto cfDto = new CourseFormRequestDto(1, "MK-2", "Course 2", 2023, 2024, 2);
        CourseForm oldCourseForm = new CourseForm(1, 0, 1, "MK-1", "Course 1", 2023, 2024, 2, 0);
        CourseForm newCourseForm = new CourseForm(1, 0, 1, "MK-2", "Course 2", 2023, 2024, 2, 0);

        when(courseFormRepository.findById(idForm)).thenReturn(Optional.of(oldCourseForm));
        when(courseFormRepository.save(newCourseForm)).thenReturn(newCourseForm);

        courseService.updateCourseForm(idForm, cfDto);

        verify(courseFormRepository, times(1)).findById(idForm);
        verify(courseFormRepository, times(1)).save(newCourseForm);
    }

    @Test
    void deleteCourseFormTest(){
        int idForm = 1;
        CourseForm oldCourseForm = new CourseForm(1, 0, 1, "MK-1", "Course 1", 2023, 2024, 2, 0);
        CourseForm newCourseForm = new CourseForm(1, 1, 1, "MK-1", "Course 1", 2023, 2024, 2, 0);

        when(courseFormRepository.findById(idForm)).thenReturn(Optional.of(oldCourseForm));
        when(courseFormRepository.save(newCourseForm)).thenReturn(newCourseForm);

        courseService.deleteCourseForm(idForm);

        verify(courseFormRepository, times(1)).save(newCourseForm);

    }

    @Test
    void getComponentByCourseFormTest(){
        int idForm = 1;
        List<ComponentCourse> componentCourses = new ArrayList<>();
        componentCourses.add(new ComponentCourse(1,"ETS", 70,1,1));
        componentCourses.add(new ComponentCourse(2,"EAS", 10,1,1));
        componentCourses.add(new ComponentCourse(3,"Lain-lain", 20,1,1));

        List<ComponentCourseDto> response = new ArrayList<>();
        componentCourses.forEach(c -> {
            ComponentCourseDto temp = new ComponentCourseDto();
            
            temp.setBobotComponent(c.getBobotComponent());
            temp.setCourseId(c.getCourseId());
            temp.setId(c.getId());
            temp.setIsAverage(c.getIsAverage());
            temp.setName(c.getName());

            response.add(temp);
        });
        
        when(componentCourseRepository.findAllByFormId(idForm)).thenReturn(componentCourses);
        
        List<ComponentCourseDto> result = courseService.getComponentByCourseForm(idForm);

        verify(componentCourseRepository, times(1)).findAllByFormId(idForm);

        assertEquals(response, result);
    }

    @Test
    void getEvaluationFormTest(){
        List<EvaluationFormResponseDto> result = courseService.getEvaluationForm(1);

        // Verify the response
        assertEquals(4, result.size());
    }

    @Test
    void getCriteriaByEvaluationFormTest(){
        String formNamePembimbing = "Pembimbing";
        String formNameSA = "Self Assessment";
        String formNameSeminar = "Seminar";
        String formTypeIndustri = "Industri 1";
        String[] numEvaluation = formTypeIndustri.split(" ");
        int prodiId = 1;
        
        List<SupervisorGradeAspect> listSupervisor = new ArrayList<>();
        listSupervisor.add(new SupervisorGradeAspect(1, "aspek pembimbing 1", 80f));
        listSupervisor.add(new SupervisorGradeAspect(2, "aspek pembimbing 2", 20f));

        List<SelfAssessmentAspect> listSAAspect = new ArrayList<>();
        listSAAspect.add(new SelfAssessmentAspect(1, "aspek self assessment 1", "description"));
        listSAAspect.add(new SelfAssessmentAspect(2, "aspek self assessment 2", "description"));

        List<SeminarCriteria> listSeminarCriteria = new ArrayList<>();
        listSeminarCriteria.add(new SeminarCriteria(1, "seminar 1", 70f, 0, 1));
        listSeminarCriteria.add(new SeminarCriteria(2, "seminar 2", 30f, 0, 1));

        List<AssessmentAspect> listAspectIndustry = new ArrayList<>();
        listAspectIndustry.add(new AssessmentAspect(1, "industri 1", 1, 0));
        listAspectIndustry.add(new AssessmentAspect(2, "industri 2", 1, 0));

        when(supervisorGradeAspectRepository.findAll()).thenReturn(listSupervisor);
        when(selfAssessmentAspectRepository.findAll()).thenReturn(listSAAspect);
        when(seminarCriteriaRepository.findAllBySelected()).thenReturn(listSeminarCriteria);
        when(assessmentAspectRepository.findAllByNumEvaluation(Integer.parseInt(numEvaluation[1]),prodiId)).thenReturn(listAspectIndustry);

        List<CriteriaEvaluationFormDto> resultPembimbing = courseService.getCriteriaByEvaluationForm(formNamePembimbing, prodiId);
        List<CriteriaEvaluationFormDto> resultSA = courseService.getCriteriaByEvaluationForm(formNameSA, prodiId);
        List<CriteriaEvaluationFormDto> resultSeminar = courseService.getCriteriaByEvaluationForm(formNameSeminar, prodiId);
        List<CriteriaEvaluationFormDto> resultIndustry = courseService.getCriteriaByEvaluationForm(formTypeIndustri, prodiId);

        verify(supervisorGradeAspectRepository, times(1)).findAll();
        verify(selfAssessmentAspectRepository, times(1)).findAll();
        verify(seminarCriteriaRepository, times(1)).findAllBySelected();
        verify(assessmentAspectRepository, times(1)).findAllByNumEvaluation(Integer.parseInt(numEvaluation[1]),prodiId);

        for (int i = 0; i < 2; i++) {
            assertEquals(resultPembimbing.get(i).getName(),listSupervisor.get(i).getDescription());
            assertEquals(resultSA.get(i).getName(),listSAAspect.get(i).getName());
            assertEquals(resultSeminar.get(i).getName(),listSeminarCriteria.get(i).getCriteriaName());
            assertEquals(resultIndustry.get(i).getName(),listAspectIndustry.get(i).getAspectName());
        }

    }

    @Test
    void getTypeAspectEvaluationFormTest(){
        String formTypeIndustry = "Industri";
        String formTypePembimbing = "Pembimbing";
        String formTypeSA = "Self Assessment";
        String formTypeSeminar = "Seminar";
        Integer prodiId = 1;
        String prodiName = "";
        List<EvaluationForm> industriForms = new ArrayList<>();
        industriForms.add(new EvaluationForm(1, 1, 1));
        industriForms.add(new EvaluationForm(2, 1, 2));
        industriForms.add(new EvaluationForm(3, 1, 3));

        if(prodiId == 0){
            prodiName = "KP";
        }else if(prodiId == 1){
            prodiName = "PKL";
        }

        when(timelineRepository.countWeekInSelfAssessment("%Pelaksanaan "+ prodiName+"%")).thenReturn(10);
        when(timelineRepository.countPhaseMentor("%Pembimbing "+prodiName+"%")).thenReturn(3);
        when(evaluationFormRepository.findAllByProdiId(prodiId)).thenReturn(industriForms);

        List<TypeOfAspectEvaluationDto> typesOfIndustry = courseService.getTypeAspectEvaluationForm(formTypeIndustry, prodiId);
        List<TypeOfAspectEvaluationDto> typesOfPembimbing = courseService.getTypeAspectEvaluationForm(formTypePembimbing, prodiId);
        List<TypeOfAspectEvaluationDto> typesOfSA = courseService.getTypeAspectEvaluationForm(formTypeSA, prodiId);
        List<TypeOfAspectEvaluationDto> typesOfSeminar = courseService.getTypeAspectEvaluationForm(formTypeSeminar, prodiId);

        verify(timelineRepository, times(1)).countWeekInSelfAssessment("%Pelaksanaan "+ prodiName+"%");
        verify(timelineRepository, times(1)).countPhaseMentor("%Pembimbing "+prodiName+"%");
        verify(evaluationFormRepository, times(1)).findAllByProdiId(prodiId);

        assertEquals(typesOfIndustry.size(), 3);
        assertEquals(typesOfPembimbing.size(), 3);
        assertEquals(typesOfSA.size(), 10);
        assertEquals(typesOfSeminar.size(), 3);
    }

    @Test
    void updateComponentTest(){
        ComponentCourse mockComponentCourse = new ComponentCourse(1, "component", 70, 1, 1);

        List<ComponentCourseDto> componentCourseDtos = new ArrayList<>();
        componentCourseDtos.add(new ComponentCourseDto(1, "component Test", 80, 1, 1));

        when(componentCourseRepository.findById(1)).thenReturn(Optional.of(mockComponentCourse));
        when(componentCourseRepository.save(any(ComponentCourse.class))).thenReturn(mockComponentCourse);

        courseService.updateComponent(componentCourseDtos);

        verify(componentCourseRepository).findById(1);
        verify(componentCourseRepository).save(any(ComponentCourse.class));

        // Verify the updated component course
        assertEquals("component Test", mockComponentCourse.getName());
        assertEquals(1, mockComponentCourse.getCourseId().intValue());
        assertEquals(80, mockComponentCourse.getBobotComponent());
        assertTrue(mockComponentCourse.getIsAverage() == 1?true:false);
    }

    @Test
    void getCriteriaComponentByCourseFormIdTest(){
        ComponentCourse componentCourse1 = new ComponentCourse();
        componentCourse1.setId(1);
        componentCourse1.setName("Component 1");
        componentCourse1.setIsAverage(1);
        componentCourse1.setBobotComponent(70);
        componentCourse1.setCourseId(1);

        ComponentCourse componentCourse2 = new ComponentCourse();
        componentCourse2.setId(2);
        componentCourse2.setName("Component 2");
        componentCourse2.setIsAverage(0);
        componentCourse2.setBobotComponent(30);
        componentCourse2.setCourseId(1);

        List<ComponentCourse> componentCourses = new ArrayList<>();
        componentCourses.add(componentCourse1);
        componentCourses.add(componentCourse2);

        when(componentCourseRepository.findAllByFormId(1)).thenReturn(componentCourses);

        CriteriaComponentCourse criteria1 = new CriteriaComponentCourse();
        criteria1.setId(1);
        criteria1.setComponentId(1);
        criteria1.setNameForm("Form 1");
        criteria1.setTypeForm("Type 1");
        criteria1.setBobotCriteria(30);
        criteria1.setIndustryCriteriaId(1);

        CriteriaComponentCourse criteria2 = new CriteriaComponentCourse();
        criteria2.setId(2);
        criteria2.setComponentId(1);
        criteria2.setNameForm("Form 2");
        criteria2.setTypeForm("Type 2");
        criteria2.setBobotCriteria(70);
        criteria2.setSelfAssessmentCriteriaId(2);

        List<CriteriaComponentCourse> criteriaComponentCourses = new ArrayList<>();
        criteriaComponentCourses.add(criteria1);
        criteriaComponentCourses.add(criteria2);

        when(criteriaComponentCourseRepository.findAllByComponentId(1)).thenReturn(criteriaComponentCourses);
        when(criteriaComponentCourseRepository.getNameAspectFromIndustry(1)).thenReturn("Aspect 1");
        when(criteriaComponentCourseRepository.getNameAspectFromSelfAssessment(2)).thenReturn("Aspect 2");

        // Call the service method
        List<ComponentAndCriteriasDto> result = courseService.getCriteriaComponentByCourseFormId(1);

        // Verify the repository method is called
        verify(componentCourseRepository).findAllByFormId(1);
        verify(criteriaComponentCourseRepository).findAllByComponentId(1);
        verify(criteriaComponentCourseRepository).getNameAspectFromIndustry(1);
        verify(criteriaComponentCourseRepository).getNameAspectFromSelfAssessment(2);

        // Verify the result
        assertEquals(2, result.size());

        ComponentAndCriteriasDto component1Dto = result.get(0);
        assertEquals(1, component1Dto.getId().intValue());
        assertEquals("Component 1", component1Dto.getName());
        assertTrue(component1Dto.getIsAverage() == 1?true:false);

        List<CriteriaBodyDto> criteriaForComponent1 = component1Dto.getCriteria_data();
        assertEquals(2, criteriaForComponent1.size());

        CriteriaBodyDto criteria1Dto = criteriaForComponent1.get(0);
        assertEquals(1, criteria1Dto.getId().intValue());
        assertEquals(1, criteria1Dto.getComponentId().intValue());
        assertEquals("Form 1", criteria1Dto.getNameForm());
        assertEquals("Type 1", criteria1Dto.getTypeForm());
        assertEquals(30, criteria1Dto.getBobotCriteria());
        assertEquals(1, criteria1Dto.getAspectFormId().intValue());
        assertEquals("Aspect 1", criteria1Dto.getAspectName());

        CriteriaBodyDto criteria2Dto = criteriaForComponent1.get(1);
        assertEquals(2, criteria2Dto.getId().intValue());
        assertEquals(1, criteria2Dto.getComponentId().intValue());
        assertEquals("Form 2", criteria2Dto.getNameForm());
        assertEquals("Type 2", criteria2Dto.getTypeForm());
        assertEquals(70, criteria2Dto.getBobotCriteria());
        assertEquals(2, criteria2Dto.getAspectFormId().intValue());
        assertEquals("Aspect 2", criteria2Dto.getAspectName());

        ComponentAndCriteriasDto component2Dto = result.get(1);
        assertEquals(2, component2Dto.getId().intValue());
        assertEquals("Component 2", component2Dto.getName());
        assertFalse(component2Dto.getIsAverage()== 1?true:false);
        assertTrue(component2Dto.getCriteria_data().isEmpty());
    }

    @Test 
    void updateOrInsertOrDeleteCriteriaComponentTest(){
        ComponentAndCriteriasDto newCriterias = new ComponentAndCriteriasDto(1, "componen1", 1, null);

        List<CriteriaBodyDto> criteriaData = new ArrayList<>();
        criteriaData.add(new CriteriaBodyDto(1, "Industri", "Industri 1", 1, 100, 1, "aspek 1"));
        criteriaData.add(new CriteriaBodyDto(2, "Seminar", "Penguji 2", 1, 100, 2, "aspek 2"));

        newCriterias.setCriteria_data(criteriaData);

        CriteriaComponentCourse criteriaComponent1 = new CriteriaComponentCourse(1, "Industri", "Industri 1", 100, 1, 1, null, null, null, 0);
        CriteriaComponentCourse criteriaComponent2 = new CriteriaComponentCourse(2, "Seminar", "Penguji 1", 100, 1, 1, null, null, null, 0);

        List<CriteriaComponentCourse> oldCriterias = new ArrayList<>();
        oldCriterias.add(criteriaComponent1);
        oldCriterias.add(criteriaComponent2);

        when(criteriaComponentCourseRepository.findAllByComponentId(1)).thenReturn(oldCriterias);
        when(criteriaComponentCourseRepository.save(any(CriteriaComponentCourse.class))).thenReturn(null);

        // Memanggil metode yang akan diuji
        courseService.updateOrInsertCriteriaComponent(newCriterias);

        verify(criteriaComponentCourseRepository, times(1)).findAllByComponentId(1);
        verify(criteriaComponentCourseRepository, times(2)).save(any(CriteriaComponentCourse.class));
        verify(courseValuesRepository, times(0)).deleteAllInCriteriaIdAndYear(anyInt(), anyInt());
        verify(criteriaComponentCourseRepository, times(0)).delete(any(CriteriaComponentCourse.class));

    }

    @Test
    void getAllRecapitulationCourseTest(){
        int year = 2022; // Tahun lain selain tahun sekarang
        int prodiId = 1;

        // Persiapkan data uji
        List<CourseForm> courseForms = new ArrayList<>();
        courseForms.add(new CourseForm());

        List<Participant> participants = new ArrayList<>();
        
        when(courseFormRepository.findAllOldCourseByYearAndProdiId(eq(year), eq(prodiId))).thenReturn(courseForms);
        when(participantRepository.findAllByYearAndProdi(year,prodiId)).thenReturn(participants);

        List<RecapitulationCourseDto> result = courseService.getAllRecapitulationByYearAndProdiId(year, prodiId);

        verify(courseFormRepository, times(1)).findAllOldCourseByYearAndProdiId(eq(year), eq(prodiId));

        assertEquals(courseForms.size(), result.size());
    }

    @Test
    void finalizationAllCourseFormTest(){
        List<CourseForm> courseAll = new ArrayList<>();
        courseAll.add(new CourseForm(1, 0, 1, "MK-1", "Mata Kuliah 1", 2023, 2024, 2, 0));

        when(courseFormRepository.findAllCourse(Integer.parseInt(Year.now().toString()))).thenReturn(courseAll);

        courseService.finalizationAllCourseForm();

        verify(courseFormRepository, times(1)).findAllCourse(Integer.parseInt(Year.now().toString()));

        assertEquals(courseAll.get(0).getIsFinalization(),1);
    }
}
