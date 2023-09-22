package com.jtk.ps.api.service.Interface;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.jtk.ps.api.dto.CompanyDto;
import com.jtk.ps.api.dto.ExaminerSeminarDto;
import com.jtk.ps.api.dto.IsFinalizationDto;
import com.jtk.ps.api.dto.ParticipantDto;
import com.jtk.ps.api.dto.RecapitulationResponseDto;
import com.jtk.ps.api.dto.SeminarCriteriaDto;
import com.jtk.ps.api.dto.SeminarCriteriaRequestDto;
import com.jtk.ps.api.dto.SeminarFormRequestDto;
import com.jtk.ps.api.dto.SeminarFormResponseDto;
import com.jtk.ps.api.dto.SeminarValuesDto;
import com.jtk.ps.api.model.SeminarCriteria;
import com.jtk.ps.api.model.SeminarForm;

public interface ISeminarService {
    
    List<CompanyDto> getAllCompany(Integer roleId, Integer prodiId);

    List<ParticipantDto> getAllParticipantByCompany(Integer idCompany, Integer roleId, Integer prodiId);

    List<SeminarCriteriaDto> getAllSeminarCriteria();

    SeminarForm createSeminarForm(SeminarFormRequestDto seminarFormRequestDto);

    SeminarCriteria createSeminarCriteria(SeminarCriteriaRequestDto seminarCriteriaRequestDto) ;
    
    SeminarCriteria updateSeminarCriteria(Integer idSeminarCriteria, SeminarCriteriaRequestDto seminarCriteriaRequestDto);
    
    void deleteSeminarCriteria(Integer idSeminarCriteria);

    SeminarFormResponseDto findSeminarFormByParticipantId(Integer idParticipant);
    
    void updateSeminarForm(Integer idForm, SeminarFormRequestDto seminarFormRequestDto);

    void updateSeminarValues(Integer idForm, List<SeminarValuesDto> seminarValuesDtos);

    RecapitulationResponseDto getRecapitulation(Integer prodiId, Integer year);
    // belum dibuat

    // public void getSeminarFormBySeminarFormIdAndExaminerId(Integer formId, Integer examinerId);

    public ExaminerSeminarDto getExaminer();

    public void finalizationByForm(Integer idForm);

    public void finalizationAllForm();

    public ByteArrayInputStream loadSeminarType(Integer year, Integer prodiId, Integer formType);

    public ByteArrayInputStream loadSeminar(Integer year, Integer prodiId);

    public IsFinalizationDto isFinalization();
}
