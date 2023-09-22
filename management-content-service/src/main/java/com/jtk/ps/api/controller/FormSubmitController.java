package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.FormSubmitTimeResponse;
import com.jtk.ps.api.dto.FormSubmitUpdateRequest;
import com.jtk.ps.api.model.FormSetting;
import com.jtk.ps.api.service.IManagementContentService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.DateUtil;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/form-submit-time")
public class FormSubmitController {

    @Autowired
    private IManagementContentService managementContentService;

    @GetMapping("")
    public ResponseEntity<Object> getFormSubmitTime(HttpServletRequest request) {
        try {
            List<FormSubmitTimeResponse> formSubmitTimeResponseList = listFormSettingToListFormSubmit(managementContentService.getFormSubmitTime((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI))));
            return ResponseHandler.generateResponse("form-submit-time", HttpStatus.OK, formSubmitTimeResponseList);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFormSubmitTimeById(@PathVariable("id") Integer id) {
        try {
            FormSubmitTimeResponse formSubmitTimeResponse = formSettingToFormSubmit(managementContentService.getFormSubmitTimeById(id));
            return ResponseHandler.generateResponse("form-submit-time", HttpStatus.OK, formSubmitTimeResponse);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateFormSubmitTime(@PathVariable("id") Integer id, @RequestBody FormSubmitUpdateRequest formSubmitUpdateRequest) {
        try {
            managementContentService.updateFormSubmitTime(id, formSubmitUpdateRequest);
            return ResponseHandler.generateResponse("form-submit-time succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private List<FormSubmitTimeResponse> listFormSettingToListFormSubmit(List<FormSetting> formSettingList) {
        List<FormSubmitTimeResponse> formSubmitTimeResponseList = new ArrayList<>();
        for (FormSetting formSetting : formSettingList) {
            FormSubmitTimeResponse formSubmitTimeResponse = new FormSubmitTimeResponse();
            formSubmitTimeResponse.setId(formSetting.getId());
            formSubmitTimeResponse.setName(formSetting.getName());
            formSubmitTimeResponse.setDayBefore(formSetting.getDayBefore());
            formSubmitTimeResponse.setStartDate(DateUtil.parseDateToString(formSetting.getTimelineSetting().getStartDate()));
            formSubmitTimeResponse.setEndDate(DateUtil.parseDateToString(formSetting.getTimelineSetting().getEndDate()));
            formSubmitTimeResponse.setIdTimeline(formSetting.getTimelineSetting().getId());
            formSubmitTimeResponse.setProdiId(formSetting.getProdiId());

            formSubmitTimeResponseList.add(formSubmitTimeResponse);
        }
        return formSubmitTimeResponseList;
    }

    private FormSubmitTimeResponse formSettingToFormSubmit(FormSetting formSetting) {
        FormSubmitTimeResponse formSubmitTimeResponse = new FormSubmitTimeResponse();
        formSubmitTimeResponse.setId(formSetting.getId());
        formSubmitTimeResponse.setIdTimeline(formSetting.getTimelineSetting().getId());
        formSubmitTimeResponse.setName(formSetting.getName());
        formSubmitTimeResponse.setStartDate(DateUtil.parseDateToString(formSetting.getTimelineSetting().getStartDate()));
        formSubmitTimeResponse.setEndDate(DateUtil.parseDateToString(formSetting.getTimelineSetting().getEndDate()));

        return formSubmitTimeResponse;
    }
}
