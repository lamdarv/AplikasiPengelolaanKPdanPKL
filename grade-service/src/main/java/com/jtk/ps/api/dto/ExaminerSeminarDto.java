package com.jtk.ps.api.dto;

import java.util.List;

import com.jtk.ps.api.model.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerSeminarDto {
    
    List<Account> penguji;

    List<Account> pembimbing;
}
