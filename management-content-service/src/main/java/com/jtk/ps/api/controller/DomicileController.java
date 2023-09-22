package com.jtk.ps.api.controller;

import com.jtk.ps.api.model.Region;
import com.jtk.ps.api.service.IManagementContentService;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/domicile")
public class DomicileController {
    @Autowired
    private IManagementContentService managementContentService;


    @GetMapping("")
    public ResponseEntity<Object> getDomicile(@RequestParam("domicile-like") String domicileLike) {
        try {
            List<Region> listRegion = managementContentService.getDomicileLike(domicileLike);
            return ResponseHandler.generateResponse("Get domicile succeed", HttpStatus.OK, listRegion , null);
        }catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id_domicile}")
    public ResponseEntity<Object> getDomicileById(@PathVariable("id_domicile") Integer idDomicile) {
        try {
            Region region = managementContentService.getDomicileById(idDomicile);
            if(region != null) {
                return ResponseHandler.generateResponse("Get domicile succeed", HttpStatus.OK, region.getRegionName());
            }
            return ResponseHandler.generateResponse("Get domicile failed", HttpStatus.NOT_FOUND);

        }catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
