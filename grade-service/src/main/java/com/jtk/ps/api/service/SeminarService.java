package com.jtk.ps.api.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtk.ps.api.dto.CompanyDto;
import com.jtk.ps.api.dto.ExaminerSeminarDto;
import com.jtk.ps.api.dto.IsFinalizationDto;
import com.jtk.ps.api.dto.ParticipantDto;
import com.jtk.ps.api.dto.RecapitulationResponseDto;
import com.jtk.ps.api.dto.SeminarValueParticipantDto;
import com.jtk.ps.api.dto.SeminarCriteriaDto;
import com.jtk.ps.api.dto.SeminarCriteriaRequestDto;
import com.jtk.ps.api.dto.SeminarFormDto;
import com.jtk.ps.api.dto.SeminarFormRequestDto;
import com.jtk.ps.api.dto.SeminarFormResponseDto;
import com.jtk.ps.api.dto.SeminarTotalValueDto;
import com.jtk.ps.api.dto.SeminarValuesDto;
import com.jtk.ps.api.helper.ExcelHelper;
import com.jtk.ps.api.model.Account;
import com.jtk.ps.api.model.Company;
import com.jtk.ps.api.model.EventStore;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.model.SeminarCriteria;
import com.jtk.ps.api.model.SeminarForm;
import com.jtk.ps.api.model.SeminarValues;
import com.jtk.ps.api.repository.AccountRepository;
import com.jtk.ps.api.repository.CompanyRepository;
import com.jtk.ps.api.repository.EventStoreRepository;
import com.jtk.ps.api.repository.ParticipantRepository;
import com.jtk.ps.api.repository.SeminarCriteriaRepository;
import com.jtk.ps.api.repository.SeminarFormRepository;
import com.jtk.ps.api.repository.SeminarValuesRepository;
import com.jtk.ps.api.service.Interface.ISeminarService;

@Service
public class SeminarService implements ISeminarService{
    
    // private static final Logger LOGGER = LoggerFactory.getLogger(SeminarService.class);

    @Autowired
    @Lazy
    private CompanyRepository companyRepository;

    @Autowired
    @Lazy
    private ParticipantRepository participantRepository;

    @Autowired
    @Lazy
    private AccountRepository accountRepository;

    @Autowired
    @Lazy
    private SeminarFormRepository seminarFormRepository;

    @Autowired
    @Lazy
    private SeminarValuesRepository seminarValuesRepository;

    @Autowired
    @Lazy
    private SeminarCriteriaRepository seminarCriteriaRepository;

    @Autowired
    @Lazy
    private EventStoreRepository eventStoreRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private void eventStoreHandler(String entityId, String eventType, Object object,Integer eventDataId){
        try {
            EventStore eventStore = new EventStore();

            eventStore.setEntityId(entityId);
            eventStore.setEventType(eventType);
            eventStore.setEventTime(LocalDateTime.now());
            eventStore.setEventData(objectMapper.writeValueAsString(object));
            eventStore.setEventDataId(eventDataId);

            eventStoreRepository.save(eventStore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CompanyDto> getAllCompany(Integer roleId, Integer prodiId) {
        List<Company> company = companyRepository.findAll();

        List<CompanyDto> companyDtos = new ArrayList<>();
        company.forEach(c -> {
            CompanyDto temp = new CompanyDto();
            temp.setId(c.getId());
            temp.setName(c.getCompanyName());
            temp.setParticipants(this.getAllParticipantByCompany(c.getId(),roleId, prodiId));
            companyDtos.add(temp);
        });
        
        return companyDtos;
    }

    @Override
    public List<ParticipantDto> getAllParticipantByCompany(Integer idCompany, Integer roleId,Integer prodiId) {
        // LOGGER.info(String.format("Year now %s", "2023");
        List<Participant> participants = participantRepository.findParticipantByCompany(idCompany,Integer.parseInt(Year.now().toString()));
        List<ParticipantDto> participantDtos = new ArrayList<>();
        participants.forEach(c -> {
            Integer isFinalization = seminarFormRepository.isFinalizationByParticipantId(c.getId());
            if(!(isFinalization == 1 && roleId == 0) && c.getProdiId() == prodiId){
                ParticipantDto tempParticipantDto = new ParticipantDto();
                Optional<Account> account = accountRepository.findById(c.getAccountId());
                account.ifPresent(a -> {
                    tempParticipantDto.setNim(a.getUsername());
                });
                tempParticipantDto.setId(c.getId());
                tempParticipantDto.setName(c.getName());
                tempParticipantDto.setProdi_id(c.getProdiId());
                tempParticipantDto.setStatus_cv(c.getStatusCv());
                tempParticipantDto.setYear(c.getYear());
                tempParticipantDto.setAccount_id(c.getAccountId());
                
                participantDtos.add(tempParticipantDto);
            }

        });

        return participantDtos;
    }

    @Override
    public SeminarForm createSeminarForm(SeminarFormRequestDto seminarFormRequestDto) {
        SeminarForm newSeminarForm = new SeminarForm();

        newSeminarForm.setComment(seminarFormRequestDto.getComment());
        newSeminarForm.setDateSeminar(seminarFormRequestDto.getDateSeminar());
        newSeminarForm.setExaminerId(seminarFormRequestDto.getExaminerId());
        newSeminarForm.setParticipantId(seminarFormRequestDto.getParticipantId());
        newSeminarForm.setExaminerType(seminarFormRequestDto.getExaminerType());

        newSeminarForm = seminarFormRepository.save(newSeminarForm);

        return newSeminarForm;
    }

    @Override
    public SeminarCriteria createSeminarCriteria(SeminarCriteriaRequestDto seminarCriteriaRequestDto) {
        SeminarCriteria newSeminarCriteria = new SeminarCriteria();

        newSeminarCriteria.setCriteriaName(seminarCriteriaRequestDto.getCriteriaName());
        newSeminarCriteria.setCriteriaBobot(Float.valueOf(0));
        newSeminarCriteria.setIsDeleted(0);
        newSeminarCriteria.setIsSelected(0);
        
        newSeminarCriteria = seminarCriteriaRepository.save(newSeminarCriteria);

        eventStoreHandler("seminar_criteria", "SEMINAR_CRITERIA_ADDED", newSeminarCriteria, newSeminarCriteria.getId());
        
        return newSeminarCriteria;
    }

    @Override
    public SeminarCriteria updateSeminarCriteria(Integer idSeminarCriteria, SeminarCriteriaRequestDto criteriaRequestDto) {
        
        Optional<SeminarCriteria> criteriaUpdate = seminarCriteriaRepository.findById(idSeminarCriteria);
        SeminarCriteria temp = new SeminarCriteria();
        if(criteriaUpdate.isPresent()){
            
            if(criteriaRequestDto.getCriteriaName() != null){
                criteriaUpdate.get().setCriteriaName(criteriaRequestDto.getCriteriaName());
            }
            if(criteriaRequestDto.getCriteriaBobot() !=null){
                criteriaUpdate.get().setCriteriaBobot(criteriaRequestDto.getCriteriaBobot());
            }
            if(criteriaRequestDto.getIsSelected() != null){
                criteriaUpdate.get().setIsSelected(criteriaRequestDto.getIsSelected());
            }

            temp = seminarCriteriaRepository.save(criteriaUpdate.get());
            eventStoreHandler("seminar_criteria", "SEMINAR_CRITERIA_UPDATE", temp, temp.getId());
        }
        return temp;
    }

    @Override
    public void deleteSeminarCriteria(Integer idSeminarCriteria) {
        
        Optional<SeminarCriteria> criteriaDeleted = seminarCriteriaRepository.findById(idSeminarCriteria);
        
        criteriaDeleted.ifPresent(c ->{
            c.setIsDeleted(1);
            c.setIsSelected(0);

            // criteria deleted
            eventStoreHandler("seminar_criteria", "SEMINAR_CRITERIA_UPDATE", seminarCriteriaRepository.save(c),idSeminarCriteria);
        });
    }

    @Override
    public List<SeminarCriteriaDto> getAllSeminarCriteria() {
        
        List<SeminarCriteria> data = seminarCriteriaRepository.findAllByIsDeleted(0);

        List<SeminarCriteriaDto> response = new ArrayList<>();
        data.forEach(c ->{
            SeminarCriteriaDto temp = new SeminarCriteriaDto();            
            temp.setId(c.getId());
            temp.setCriteriaName(c.getCriteriaName());
            temp.setCriteriaBobot(c.getCriteriaBobot());
            temp.setIsSelected(c.getIsSelected());
            response.add(temp);
        });

        return response;
    }

    @Override
    public SeminarFormResponseDto findSeminarFormByParticipantId(Integer idParticipant) {
        Integer isExist = seminarFormRepository.isSeminarFormExistByParticipantId(idParticipant);

        List<SeminarFormDto> response = new ArrayList<>();
        SeminarFormResponseDto sResponseDto = new SeminarFormResponseDto();
        Optional<Participant> participant = participantRepository.findById(idParticipant);

        if(isExist == 1){
            List<SeminarForm> seminarForms = seminarFormRepository.findAllByParticipantId(idParticipant);
            seminarForms.forEach(s -> {
                SeminarFormDto temp = new SeminarFormDto();

                temp.setId(s.getId());
                temp.setParticipantId(s.getParticipantId());
                temp.setExaminerId(s.getExaminerId());
                temp.setExaminerType(s.getExaminerType());
                temp.setDateSeminar(s.getDateSeminar());
                temp.setComment(s.getComment());
                
                response.add(temp);
            });
        }else{
            for(int i = 0; i<3; i++){
                SeminarForm seminarForm = new SeminarForm();
                SeminarFormDto temp = new SeminarFormDto();

                seminarForm.setParticipantId(idParticipant);
                seminarForm.setExaminerType(i+1);
                temp.setParticipantId(idParticipant);
                temp.setExaminerType(i+1);
                temp.setId(seminarFormRepository.maxFormId()+1);
                
                seminarFormRepository.save(seminarForm);
                response.add(temp);
            }
        }

        sResponseDto.setDataForm(response);
        sResponseDto.setId(idParticipant);
        if(participant.isPresent()){
            sResponseDto.setName(participant.get().getName());
            sResponseDto.setNim(accountRepository.findNimParticipant(participant.get().getAccountId()));
        }

        return sResponseDto;
    }

    @Override
    public void updateSeminarForm(Integer idForm, SeminarFormRequestDto seminarFormRequestDto) {
        Optional<SeminarForm> seminarForm = seminarFormRepository.findById(idForm);

        seminarForm.ifPresent(c ->{
            c.setComment(seminarFormRequestDto.getComment());
            c.setDateSeminar(seminarFormRequestDto.getDateSeminar());
            c.setExaminerId(seminarFormRequestDto.getExaminerId());
            
            seminarFormRepository.save(c);
        });
    }

    @Override
    public void updateSeminarValues(Integer idForm, List<SeminarValuesDto> seminarValuesDtos){
        
        seminarValuesDtos.forEach(c -> {
            if(seminarValuesRepository.isFormWithCriteriaExist(idForm, c.getSeminarCriteriaId()) == 1){
                SeminarValues newSV = seminarValuesRepository.findByFormAndCriteria(idForm, c.getSeminarCriteriaId());
                newSV.setValue(c.getValue());

                seminarValuesRepository.save(newSV);
            }else{
                SeminarValues newSV = new SeminarValues();
                newSV.setSeminarCriteriaId(c.getSeminarCriteriaId());
                newSV.setSeminarFormId(idForm);
                newSV.setValue(c.getValue());

                seminarValuesRepository.save(newSV);
            }
        });
    }

    private List<SeminarValueParticipantDto> getRecapitulationByTypeForm(Integer year, Integer prodiId, Integer formType){
        List<Participant> participants = participantRepository.findAllByYearAndProdi(year, prodiId);

        List<SeminarValueParticipantDto> penguji = new ArrayList<>();

        participants.forEach(p ->{
            Optional<SeminarForm> sFTemp = seminarFormRepository.findByParticipantAndTypeForm(p.getId(), formType);

            sFTemp.ifPresent(sftemp -> {

                List<SeminarValues> values = seminarValuesRepository.findAllByFormv2(sftemp.getId());

                // proses memasukan identitas peserta
                ParticipantDto participantDto = new ParticipantDto();
                participantDto.setId(p.getId());
                participantDto.setAccount_id(p.getAccountId());
                participantDto.setName(p.getName());
                participantDto.setProdi_id(p.getProdiId());
                participantDto.setStatus_cv(p.getYear());
                participantDto.setYear(p.getYear());

                Optional<Account> account = accountRepository.findById(p.getAccountId());
                account.ifPresent(a -> {
                    participantDto.setNim(a.getUsername());
                });

                // proses memasukan nilai
                SeminarValueParticipantDto seminarValueParticipantDto = new SeminarValueParticipantDto();
                
                // jika menggunakan tahun sekarang
                seminarValueParticipantDto.setNilaiTotal(sftemp.getTotalValue());
                seminarValueParticipantDto.setNilai(values);
                seminarValueParticipantDto.setPeserta(participantDto);

                penguji.add(seminarValueParticipantDto);
            });
        });
        return penguji;
    }

    // private Float getTotalValueByForm(Integer formId,Integer year){
    //     List<SeminarValues> values = seminarValuesRepository.findAllByForm(formId);
    //     final Float[] total = {0f};
    //     values.forEach(v -> {
    //         String eventType = "SEMINAR_CRITERIA_UPDATE";
    //         EventStore eventStoreCriteria = eventStoreRepository.getLastUpdateEvent(v.getSeminarCriteriaId(), eventType, year);
    //         try {
    //             JsonNode rootNode = objectMapper.readTree(eventStoreCriteria.getEventData());
    //             total[0] = total[0] + (v.getValue()/100*rootNode.get("criteriaBobot").asInt());
    //         } catch (Exception e) {
    //             e.printStackTrace();;
    //         }
    //     });
    //     return total[0];
    // }

    private List<SeminarTotalValueDto> getRecapitulationTotal(Integer year, Integer prodiId){
        
        List<Participant> participants = participantRepository.findAllByYearAndProdi(year, prodiId);

        List<SeminarTotalValueDto> nilaiTotal = new ArrayList<>();

        participants.forEach(p ->{
            Optional<SeminarForm> form1 = seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 1);
            Optional<SeminarForm> form2 = seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 2);
            Optional<SeminarForm> form3 = seminarFormRepository.findByParticipantAndTypeForm(p.getId(), 3);

            if(form1.isPresent() && form2.isPresent() && form3.isPresent()){
                // proses memasukan identitas peserta
                ParticipantDto participantDto = new ParticipantDto();
                participantDto.setId(p.getId());
                participantDto.setAccount_id(p.getAccountId());
                participantDto.setName(p.getName());
                participantDto.setProdi_id(p.getProdiId());
                participantDto.setStatus_cv(p.getYear());
                participantDto.setYear(p.getYear());

                Optional<Account> account = accountRepository.findById(p.getAccountId());
                account.ifPresent(a -> {
                    participantDto.setNim(a.getUsername());
                });

                SeminarTotalValueDto seminarTotalValueDto = new SeminarTotalValueDto();
                seminarTotalValueDto.setParticipant(participantDto);
                
                seminarTotalValueDto.setNilaiTotal(
                    (
                        form1.get().getTotalValue() + form2.get().getTotalValue() + form3.get().getTotalValue()
                    )/3
                );
                nilaiTotal.add(seminarTotalValueDto);
            }
        });
        return nilaiTotal;
    }

    @Override
    public RecapitulationResponseDto getRecapitulation(Integer prodiId, Integer year) {

        RecapitulationResponseDto response = new RecapitulationResponseDto();

        response.setNilai_penguji_1(getRecapitulationByTypeForm(year,prodiId,1));
        response.setNilai_penguji_2(getRecapitulationByTypeForm(year,prodiId,2));
        response.setNilai_pembimbing(getRecapitulationByTypeForm(year,prodiId,3));
        response.setNilai_total_seminar(getRecapitulationTotal(year,prodiId));

        return response;
    }

    
    @Override
    public void finalizationAllForm() {
        seminarFormRepository.findByIsFinalization(0).forEach(sf -> {
            finalizationByForm(sf.getId());
        });
    }

    @Override
    public void finalizationByForm(Integer idForm) {
        seminarFormRepository.findById(idForm).ifPresent(sf -> {
            sf.setIsFinalization(1);
            sf.setTotalValue(seminarValuesRepository.totalSeminarValuesByForm(sf.getId()));
            seminarFormRepository.save(sf);
        });
    }

    @Override
    public ExaminerSeminarDto getExaminer() {
        ExaminerSeminarDto response = new ExaminerSeminarDto();

        Integer yearNow = Integer.parseInt(Year.now().toString());

        List<Account> penguji = accountRepository.findExaminerAccount();
        List<Account> pembimbing = accountRepository.findSupervisorAccount(yearNow);

        response.setPembimbing(pembimbing);
        response.setPenguji(penguji);
        

        return response;
    }

    public ByteArrayInputStream loadSeminarType(Integer year, Integer prodiId, Integer formType) {
        List<SeminarCriteria> criterias = seminarCriteriaRepository.findAllBySelected();
        List<SeminarValueParticipantDto> list = this.getRecapitulationByTypeForm(year, prodiId, formType);

        ByteArrayInputStream in = ExcelHelper.recapSeminartoExcelByType(list,"penguji "+formType, criterias);
        return in;
    }

    public ByteArrayInputStream loadSeminar(Integer year, Integer prodiId) {
        List<SeminarCriteria> criterias = seminarCriteriaRepository.findAllBySelected();
        List<List<SeminarValueParticipantDto>> Llist = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            List<SeminarValueParticipantDto> list = this.getRecapitulationByTypeForm(year, prodiId, i+1);
            Llist.add(list);
        }
        List<SeminarTotalValueDto> listTotal = this.getRecapitulationTotal(year, prodiId);
        ByteArrayInputStream in = ExcelHelper.recapSeminartoExcel(criterias,Llist, listTotal);
        return in;
    }

    @Override
    public IsFinalizationDto isFinalization() {
        IsFinalizationDto response = new IsFinalizationDto();

        response.setIsFinalization(seminarFormRepository.isAllFinalization(Integer.parseInt(Year.now().toString())));
        return response;
    }

    
}
