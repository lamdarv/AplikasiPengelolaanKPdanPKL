package com.jtk.ps.api.java.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jtk.ps.api.dto.CompanyDto;
import com.jtk.ps.api.dto.ExaminerSeminarDto;
import com.jtk.ps.api.dto.IsFinalizationDto;
import com.jtk.ps.api.dto.RecapitulationResponseDto;
import com.jtk.ps.api.dto.SeminarCriteriaDto;
import com.jtk.ps.api.dto.SeminarCriteriaRequestDto;
import com.jtk.ps.api.dto.SeminarFormDto;
import com.jtk.ps.api.dto.SeminarFormRequestDto;
import com.jtk.ps.api.dto.SeminarFormResponseDto;
import com.jtk.ps.api.dto.SeminarValuesDto;
import com.jtk.ps.api.model.Account;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.SeminarCriteria;
import com.jtk.ps.api.model.SeminarForm;
import com.jtk.ps.api.model.SeminarValues;
import com.jtk.ps.api.repository.AccountRepository;
import com.jtk.ps.api.repository.CompanyRepository;
import com.jtk.ps.api.repository.ParticipantRepository;
import com.jtk.ps.api.repository.SeminarCriteriaRepository;
import com.jtk.ps.api.repository.SeminarFormRepository;
import com.jtk.ps.api.repository.SeminarValuesRepository;
import com.jtk.ps.api.service.SeminarService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {SeminarServiceTest.class})
public class SeminarServiceTest {
    
    @InjectMocks
    private SeminarService seminarService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private SeminarCriteriaRepository seminarCriteriaRepository;

    @Mock
    private SeminarFormRepository seminarFormRepository;

    @Mock
    private SeminarValuesRepository seminarValuesRepository;

    @Test
    void getAllCompaniesTest(){
        Integer roleId = 0;
        Integer prodiId = 1;
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "companyName1", "Company.1@gmail.com", 2023, 1, 1));
        companies.add(new Company(2, "companyName2", "Company.2@gmail.com", 2023, 1, 2));
        companies.add(new Company(3, "companyName3", "Company.3@gmail.com", 2023, 1, 3));

        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(1, "Ali", 2023, 1, 1, 1));
        participants.add(new Participant(2, "Hasya", 2023, 1, 1, 2));
        participants.add(new Participant(3, "Risna", 2023, 1, 0, 3));
        participants.add(new Participant(4, "Kusnadin", 2024, 1, 1, 4));
        participants.add(new Participant(5, "Sonia", 2022, 1, 1, 5));

        

        when(companyRepository.findAll()).thenReturn(companies);
        when(participantRepository.findParticipantByCompany(any(Integer.class),eq(2023))).thenReturn(participants);
        when(accountRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
            int accountId = invocation.getArgument(0);
            // Menggunakan data account yang sesuai dengan accountId
            if (accountId == 1) {
                return Optional.of(new Account(1, "201511016", 1, 0));
            } else if (accountId == 2) {
                return Optional.of(new Account(2, "201511011", 1, 0));
            } else if (accountId == 3) {
                return Optional.of(new Account(3, "201511026", 1, 0));
            } else if (accountId == 4) {
                return Optional.of(new Account(4, "212011016", 1, 0));
            } else if (accountId == 5) {
                return Optional.of(new Account(5, "191511011", 1, 0));
            } else {
                return Optional.empty();
            }
        });

        List<CompanyDto> companyDtos = seminarService.getAllCompany(roleId, prodiId);

        verify(participantRepository, times(3)).findParticipantByCompany(any(Integer.class), eq(2023));
        verify(accountRepository, times(12)).findById(any(Integer.class));
        verify(companyRepository, times(1)).findAll();

        assertEquals(3, companyDtos.size());
    }

    @Test
    void getAllSeminarCriteriaTest(){
        List<SeminarCriteria> seminarCriterias = new ArrayList<>();
        seminarCriterias.add(new SeminarCriteria(1, "Aspek 1", 80f, 0, 1));
        seminarCriterias.add(new SeminarCriteria(2, "Aspek 2", 10f, 0, 1));
        seminarCriterias.add(new SeminarCriteria(3, "Aspek 3", 10f, 0, 1));
        seminarCriterias.add(new SeminarCriteria(4, "Aspek 4", 80f, 0, 0));

        when(seminarCriteriaRepository.findAllByIsDeleted(eq(0))).thenReturn(seminarCriterias);

        List<SeminarCriteriaDto> sCriteriaDtos = seminarService.getAllSeminarCriteria();
        
        verify(seminarCriteriaRepository, times(1)).findAllByIsDeleted(eq(0));
        
        assertEquals(4, sCriteriaDtos.size());
    }

    @Test
    void createSeminarCriteriaTest(){
         SeminarCriteriaRequestDto sCriteriaDto = new SeminarCriteriaRequestDto("new Aspek", 0f, 0);
         SeminarCriteria newSeminarCriteria = new SeminarCriteria(null, "new Aspek", 0f, 0, 0);
         SeminarCriteria saveSeminarCriteria = new SeminarCriteria(1, "new Aspek", 0f, 0, 0);

         when(seminarCriteriaRepository.save(any(SeminarCriteria.class))).thenReturn(saveSeminarCriteria);

         SeminarCriteria seminarCriteria = seminarService.createSeminarCriteria(sCriteriaDto);
         
         verify(seminarCriteriaRepository).save(newSeminarCriteria);

         assertNotNull(seminarCriteria.getId());
         assertEquals(seminarCriteria.getCriteriaBobot(),0f);
         assertEquals(seminarCriteria.getCriteriaName(),"new Aspek");
    }

    @Test
    void createSeminarFormTest(){
        Date date = new Date();
        String comment = "comment";

        SeminarFormRequestDto sRequestDto = new SeminarFormRequestDto(1, date, 1, 1, "comment");

        when(seminarFormRepository.save(any(SeminarForm.class))).thenReturn(new SeminarForm(1, date, 1, 1, 1, comment, 0, 0f));

        SeminarForm seminarForm = seminarService.createSeminarForm(sRequestDto);

        verify(seminarFormRepository).save(new SeminarForm(null, date, 1, 1, 1, comment, null, null));

        assertEquals(seminarForm.getId(), 1);
        assertEquals(seminarForm.getParticipantId(), 1);
        assertEquals(seminarForm.getExaminerId(),1);
    }

    @Test
    void updateSeminarCriteriaTest(){
        SeminarCriteria existingCriteria = new SeminarCriteria(1, "Aspek 1", 0f, 0, 0);
        SeminarCriteria updateCriteria = new SeminarCriteria(1, "Aspek 1 changed", 70f, 0, 1);

        when(seminarCriteriaRepository.findById(existingCriteria.getId())).thenReturn(Optional.of(existingCriteria));
        when(seminarCriteriaRepository.save(updateCriteria)).thenReturn(updateCriteria);

        SeminarCriteria seminarCriteria = seminarService.updateSeminarCriteria(1, new SeminarCriteriaRequestDto(updateCriteria.getCriteriaName(),updateCriteria.getCriteriaBobot(),updateCriteria.getIsSelected()));

        verify(seminarCriteriaRepository, times(1)).findById(existingCriteria.getId());
        verify(seminarCriteriaRepository, times(1)).save(updateCriteria);

        assertNotNull(seminarCriteria);
        assertEquals(updateCriteria, seminarCriteria);
    }

    @Test
    void deleteSeminarCriteriaTest(){
        SeminarCriteria criteriaDeleted = new SeminarCriteria(1, "Aspek 1", 100f, 0, 1);

        when(seminarCriteriaRepository.findById(any(Integer.class))).thenReturn(Optional.of(criteriaDeleted));
        criteriaDeleted.setIsDeleted(1);
        when(seminarCriteriaRepository.save(criteriaDeleted)).thenReturn(criteriaDeleted);

        seminarService.deleteSeminarCriteria(criteriaDeleted.getId());

        verify(seminarCriteriaRepository, times(1)).findById(any(Integer.class));
        verify(seminarCriteriaRepository, times(1)).save(criteriaDeleted);
    }

    @Test
    void findSeminarFormByParticipantIdTest(){
        Optional<Participant> participant = Optional.of(new Participant(1, "Ali", 2023, 1, 1, 1));

        Integer idParticipant = 1;

        Integer isExist = 1;

        List<SeminarForm> seminarForms = new ArrayList<>();
        seminarForms.add(new SeminarForm(1, new Date(), idParticipant, 1, 1, null, 0, 0f));
        seminarForms.add(new SeminarForm(2, new Date(), idParticipant, 2, 2, null, 0, 0f));
        seminarForms.add(new SeminarForm(3, new Date(), idParticipant, 3, 3, null, 0, 0f));

        when(seminarFormRepository.isSeminarFormExistByParticipantId(idParticipant)).thenReturn(isExist);
        when(seminarFormRepository.findAllByParticipantId(idParticipant)).thenReturn(seminarForms);
        when(participantRepository.findById(idParticipant)).thenReturn(participant);

        SeminarFormResponseDto responseDto = seminarService.findSeminarFormByParticipantId(idParticipant);

        assertNotNull(responseDto);
        assertEquals(idParticipant, responseDto.getId());
        assertEquals(participant.get().getName(), responseDto.getName());
        assertNull(responseDto.getNim());

        List<SeminarFormDto> dataForm = responseDto.getDataForm();
        assertNotNull(dataForm);
        assertEquals(seminarForms.size(), dataForm.size());

        for (int i = 0; i < seminarForms.size(); i++) {
            SeminarForm seminarForm = seminarForms.get(i);
            SeminarFormDto formDto = dataForm.get(i);

            assertEquals(seminarForm.getId(), formDto.getId());
            assertEquals(seminarForm.getParticipantId(), formDto.getParticipantId());
            assertEquals(seminarForm.getExaminerId(), formDto.getExaminerId());
            assertEquals(seminarForm.getExaminerType(), formDto.getExaminerType());
            assertEquals(seminarForm.getDateSeminar(), formDto.getDateSeminar());
            assertEquals(seminarForm.getComment(), formDto.getComment());
        }

        // Verifikasi pemanggilan metode pada repository
        Mockito.verify(seminarFormRepository, times(1)).isSeminarFormExistByParticipantId(idParticipant);
        Mockito.verify(seminarFormRepository, times(1)).findAllByParticipantId(idParticipant);
        Mockito.verify(participantRepository, times(1)).findById(idParticipant);
    }

    @Test
    void updateSeminarFormTest(){
        // Persiapan data
        Integer idForm = 1;
        SeminarFormRequestDto seminarFormRequestDto = new SeminarFormRequestDto(1, new Date(), 1, 1, "comment");

        SeminarForm seminarForm = new SeminarForm(1, new Date(), 1, 2, 1, null, 0, 0f);
        SeminarForm updateForm = new SeminarForm(1, new Date(), 1, 1, 1,"comment",0, 0f);
        when(seminarFormRepository.findById(idForm)).thenReturn(Optional.of(seminarForm));
        when(seminarFormRepository.save(updateForm)).thenReturn(updateForm);

        // Memanggil metode yang akan diuji
        seminarService.updateSeminarForm(idForm, seminarFormRequestDto);

        // Verifikasi bahwa metode save() pada repository terpanggil
        verify(seminarFormRepository, times(1)).save(seminarForm);

        // Memastikan bahwa nilai-nilai pada seminarForm telah diperbarui sesuai dengan seminarFormRequestDto
        assertEquals(seminarFormRequestDto.getComment(), updateForm.getComment());
        assertEquals(seminarFormRequestDto.getDateSeminar(), updateForm.getDateSeminar());
        assertEquals(seminarFormRequestDto.getExaminerId(), updateForm.getExaminerId());
    }

    @Test
    void updateSeminarValuesTest(){
        int idForm = 1;
        List<SeminarValuesDto> sDtos = new ArrayList<>();
        sDtos.add(new SeminarValuesDto(1,70f,1,1));
        sDtos.add(new SeminarValuesDto(2,80f,2,1));
        sDtos.add(new SeminarValuesDto(3,70f,3,1));

        for (int i = 0; i < 3; i++) {
            when(seminarValuesRepository.isFormWithCriteriaExist(idForm, sDtos.get(i).getId())).thenReturn(1);
            when(seminarValuesRepository.findByFormAndCriteria(idForm,sDtos.get(i).getId())).thenReturn(new SeminarValues());
        }

        seminarService.updateSeminarValues(idForm, sDtos);

        verify(seminarValuesRepository, times(sDtos.size())).save(any(SeminarValues.class));
        sDtos.forEach(dto -> {
            verify(seminarValuesRepository, times(1)).isFormWithCriteriaExist(idForm, dto.getSeminarCriteriaId());
            verify(seminarValuesRepository, times(1)).findByFormAndCriteria(idForm, dto.getSeminarCriteriaId());
        });
    }

    @Test
    void getRecapitulationSeminarTest(){
        // Persiapan data
        Integer prodiId = 1;
        Integer year = 2023;

        List<Participant> participants = Arrays.asList(
            new Participant(1, "Ali", 2023, 1, 1, 1),
            new Participant(2, "Hasya", 2023, 1, 1, 2),
            new Participant(3, "Risna", 2023, 1, 0, 3)
        );

        List<SeminarForm> seminarForms = Arrays.asList(
            new SeminarForm(1, null, 1, 1, 1, null, 0, 0f),
            new SeminarForm(2, null, 1, 2, 2, null, 0, 0f),
            new SeminarForm(3, null, 1, 3, 2, null, 0, 0f),
            new SeminarForm(4, null, 2, 1, 1, null, 0, 0f),
            new SeminarForm(5, null, 2, 2, 2, null, 0, 0f),
            new SeminarForm(6, null, 2, 3, 2, null, 0, 0f),
            new SeminarForm(7, null, 3, 1, 1, null, 0, 0f),
            new SeminarForm(8, null, 3, 2, 2, null, 0, 0f),
            new SeminarForm(9, null, 3, 3, 2, null, 0, 0f)
        );

        when(participantRepository.findAllByYearAndProdi(year, prodiId)).thenReturn(participants);
        int indeks = 0;
        for (Participant p : participants) {
            when(seminarValuesRepository.findAllByFormv2(any(Integer.class))).thenReturn(Arrays.asList(new SeminarValues()));
            when(seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 1)).thenReturn(Optional.of(seminarForms.get(indeks++)));
            when(seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 2)).thenReturn(Optional.of(seminarForms.get(indeks++)));
            when(seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 3)).thenReturn(Optional.of(seminarForms.get(indeks++)));
            when(accountRepository.findById(p.getAccountId())).thenReturn(Optional.of(new Account()));
        }
        

        RecapitulationResponseDto result = seminarService.getRecapitulation(prodiId, year);

        verify(participantRepository, times(4)).findAllByYearAndProdi(year, prodiId);

        // Verifikasi bahwa metode findByParticipantAndTypeForm() pada seminarFormRepository terpanggil 3 kali untuk setiap participant
        participants.forEach(p -> {
            verify(seminarFormRepository, times(2)).findByParticipantAndTypeForm(p.getId(), 1);
            verify(seminarFormRepository, times(2)).findByParticipantAndTypeForm(p.getId(), 2);
            verify(seminarFormRepository, times(2)).findByParticipantAndTypeForm(p.getId(), 3);
        });

        // verify(seminarValuesRepository, times(participants.size())).findAllByFormv2(any(Integer.class));
        // Verifikasi bahwa metode findAllByFormv2() pada seminarValuesRepository terpanggil sesuai jumlah peserta yang memiliki form
        

        // Verifikasi bahwa metode findById() pada accountRepository terpanggil sesuai jumlah peserta
        // verify(accountRepository, times(participants.size())).findById(any(Integer.class));
        assertNotNull(result);
    }

    @Test
    void getExaminerTest(){
        List<Account> listPenguji = new ArrayList<>();
        listPenguji.add(new Account(1, "Ali", 0, 0));
        listPenguji.add(new Account(2, "Hasya", 0, 0));

        List<Account> listPembimbing = new ArrayList<>();
        listPembimbing.add(new Account(3, "Risna", 0, 0));

        when(accountRepository.findExaminerAccount()).thenReturn(listPenguji);
        when(accountRepository.findSupervisorAccount(Year.now().getValue())).thenReturn(listPembimbing);

        // Call the service method
        ExaminerSeminarDto result = seminarService.getExaminer();

        // Verify the response
        assertEquals(listPenguji, result.getPenguji());
        assertEquals(listPembimbing, result.getPembimbing());
    }

    @Test
    void finalizationSeminarByFormTest(){
        Integer year = Year.now().getValue();
        when(seminarFormRepository.isAllFinalization(year)).thenReturn(1);

        // Call the service method
        IsFinalizationDto result = seminarService.isFinalization();

        // Verify the response
        assertEquals(Integer.valueOf(1), result.getIsFinalization());

        when(seminarFormRepository.isAllFinalization(year)).thenReturn(0);

        // Call the service method
        result = seminarService.isFinalization();

        // Verify the response
        assertEquals(Integer.valueOf(0), result.getIsFinalization());
    }
}
