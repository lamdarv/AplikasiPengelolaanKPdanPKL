package com.jtk.ps.api.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jtk.ps.api.dto.ValuationV2Dto;
import com.jtk.ps.api.service.Interface.IEvaluationIndustryService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;

@Controller
@RequestMapping("/evaluation")
public class EvaluationIndustryController {

    @Autowired
    private IEvaluationIndustryService evaluationIndustryService;

    @GetMapping("/") //checked
    public ResponseEntity<Object> getAllEvaluation(HttpServletRequest request){
        Integer prodiId = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
        return ResponseHandler.generateResponse("Get all Evaluation Industry succeed",HttpStatus.OK, evaluationIndustryService.getAllEvaluation(prodiId));
    }

    @GetMapping("/evaluation/{id}") //checked
    public ResponseEntity<Object> getValuationByEvaluation(@PathVariable("id") Integer idEvaluation){
        return ResponseHandler.generateResponse("Get Valuation By Evaluation Form succeed",HttpStatus.OK, evaluationIndustryService.getAllValuationV2ByEvaluation(idEvaluation));
    }

    @PutMapping("/evaluation/{id}") //checked
    public ResponseEntity<Object> updateValuationByEvaluation(@PathVariable("id") Integer idEvaluation, @RequestBody List<ValuationV2Dto> request){
        evaluationIndustryService.quantizationEvaluation(request, idEvaluation);
        return ResponseHandler.generateResponse("Update Valuation succeed",HttpStatus.OK);
    }
}
