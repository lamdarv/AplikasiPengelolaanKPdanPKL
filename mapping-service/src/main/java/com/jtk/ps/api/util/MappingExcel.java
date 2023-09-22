package com.jtk.ps.api.util;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.ranking.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MappingExcel {
    private List<CompanyReqSheetResponse> companyReqResponseList;
    private List<RankingResponse> rankingResponseList;
    private List<ParticipantValueList> participantValueList;
    private List<CompetenceResponse> competenceResponseList;
    private List<JobscopeGetAll> jobscopeGetAllList;
    private List<CourseResponse> courseResponseList;
    private List<AbsenceResponse> absenceResponseList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private String dateRank;

    public MappingExcel(List<CompanyReqSheetResponse> companyReqResponseList, RankingAndDateResponse rankingAndDateResponse,
                        List<ParticipantValueList> participantValueList , List<AbsenceResponse> absenceResponses,
                        List<CompetenceResponse> competenceResponses, List<JobscopeGetAll> jobscopeResponses,
                        List<CourseResponse> courseResponses) {
        this.companyReqResponseList = companyReqResponseList;
        this.rankingResponseList = rankingAndDateResponse.getRankingList();
        this.participantValueList = participantValueList;
        this.competenceResponseList = competenceResponses;
        this.jobscopeGetAllList = jobscopeResponses;
        this.courseResponseList = courseResponses;
        this.absenceResponseList = absenceResponses;
        this.dateRank = rankingAndDateResponse.getDate();
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writePrerequisite() {
        int rowCount = 1;
        sheet = workbook.createSheet("Prerequisite");

        int maxColumn = 0;
        for (CompanyReqSheetResponse companyReqResponse : companyReqResponseList) {
            if(companyReqResponse.getCompetence().size() > maxColumn) {
                maxColumn = companyReqResponse.getCompetence().size();
            }
            // Get All specific of requirement company
            int programmingLanguageId = 0;
            int frameworkId= 0;
            int databaseId = 0;
            int modellingIdList = 0;
            int communicationLanguageList = 0;
            int toolList = 0;

            for (CompetenceCompany competenceCompany : companyReqResponse.getCompetence()) {
                if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.PROGRAMMING_LANGUAGE)) {
                    programmingLanguageId++;
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.FRAMEWORK)) {
                    frameworkId++;
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.DATABASE)) {
                    databaseId++;
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.MODELLING)) {
                    modellingIdList++;
                } else if (Objects.equals(competenceCompany.getCompetenceType(),
                        Constant.CompetenceConstant.COMMUNICATION_LANGUAGE)) {
                    communicationLanguageList++;
                } else if (Objects.equals(competenceCompany.getCompetenceType(), Constant.CompetenceConstant.TOOL)) {
                    toolList++;
                }
            }

            if(programmingLanguageId > maxColumn) {
                maxColumn = programmingLanguageId;
            }
            if(frameworkId > maxColumn) {
                maxColumn = frameworkId;
            }
            if(databaseId > maxColumn) {
                maxColumn = databaseId;
            }
            if(modellingIdList > maxColumn) {
                maxColumn = modellingIdList;
            }
            if(communicationLanguageList > maxColumn) {
                maxColumn = communicationLanguageList;
            }
            if(toolList > maxColumn) {
                maxColumn = toolList;
            }
        }

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        if(maxColumn!=0){
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 3+(maxColumn - 1)));
        }

        createCell(row, 0, "No", style);
        createCell(row, 1, "Nama Perusahaan", style);
        createCell(row, 2, "Kriteria", style);
        createCell(row, 3, "Requirement Perusahaan", style);

        int columnDomisili = 3+maxColumn;
        createCell(row, columnDomisili, "Domisili", style);

        style = workbook.createCellStyle();
        font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        for (CompanyReqSheetResponse record : companyReqResponseList) {
            row = sheet.createRow(rowCount);
            int columnCount = 0;

            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+6, columnCount, columnCount));
            createCell(row, columnCount++, rowCount, style);

            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+6, columnCount, columnCount));
            createCell(row, columnCount++, record.getCompanyName(), style);

            createCell(row, columnCount++, "Minat Pekerjaan", style);

            if(record.getJobscope() != null && !record.getJobscope().isEmpty()) {
                int column = 0;
                for (JobscopeResponse jobscopeResponse :record.getJobscope()){
                    Row finalRow1 = row;
                    int finalColumnCount1 = columnCount;
                    CellStyle finalStyle1 = style;
                    jobscopeGetAllList.stream().filter(x -> Objects.equals(x.getId(), jobscopeResponse.getId())).findFirst().ifPresent(x -> {
                        if(jobscopeResponse.getProdiId() == 0){
                            createCell(finalRow1, finalColumnCount1 + column, x.getName()+" (D3)", finalStyle1);
                        }else if(jobscopeResponse.getProdiId() == 1) {
                            createCell(finalRow1, finalColumnCount1 + column, x.getName() + " (D4)", finalStyle1);
                        }else{
                            createCell(finalRow1, finalColumnCount1 + column, x.getName(), finalStyle1);
                        }

                    });
                }
            }

            columnCount = 2;
            row = sheet.createRow(rowCount+1);
            createCell(row, columnCount, "Bahasa Pemograman", style);
            row = sheet.createRow(rowCount+2);
            createCell(row, columnCount, "Database", style);
            row = sheet.createRow(rowCount+3);
            createCell(row, columnCount, "Framework", style);
            row = sheet.createRow(rowCount+4);
            createCell(row, columnCount, "Tools", style);
            row = sheet.createRow(rowCount+5);
            createCell(row, columnCount, "Modelling Tools", style);
            row = sheet.createRow(rowCount+6);
            createCell(row, columnCount, "Bahasa yang Dikuasai", style);

            if(record.getCompetence() != null && !record.getCompetence().isEmpty()) {
                int columnCompetence1 = 1;
                int columnCompetence2 = 1;
                int columnCompetence3 = 1;
                int columnCompetence4 = 1;
                int columnCompetence5 = 1;
                int columnCompetence6 = 1;

                for (CompetenceCompany competenceCompany : record.getCompetence()) {
                    if(competenceCompany.getCompetenceType() == 1){
                        Row finalRow = sheet.getRow(rowCount+1);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence1 = columnCompetence1;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence1, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence1, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence1, competenceResponse.getName(), finalStyle);
                            }

                        });
                        columnCompetence1++;
                    }
                    if(competenceCompany.getCompetenceType() == 2){
                        Row finalRow = sheet.getRow(rowCount+2);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence2 = columnCompetence2;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0) {
                                createCell(finalRow, finalColumnCount + finalColumnCompetence2, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence2, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence2, competenceResponse.getName(), finalStyle);
                            }
                        });
                        columnCompetence2++;
                    }
                    if(competenceCompany.getCompetenceType() == 3){
                        Row finalRow = sheet.getRow(rowCount+3);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence3 = columnCompetence3;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0) {
                                createCell(finalRow, finalColumnCount + finalColumnCompetence3, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence3, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence3, competenceResponse.getName(), finalStyle);
                            }
                        });
                        columnCompetence3++;
                    }
                    if(competenceCompany.getCompetenceType() == 4) {
                        Row finalRow = sheet.getRow(rowCount + 4);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence4 = columnCompetence4;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0) {
                                createCell(finalRow, finalColumnCount + finalColumnCompetence4, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence4, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence4, competenceResponse.getName(), finalStyle);
                            }
                        });
                        columnCompetence4++;
                    }
                    if (competenceCompany.getCompetenceType() == 5) {
                        Row finalRow = sheet.getRow(rowCount + 5);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence5 = columnCompetence5;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0) {
                                createCell(finalRow, finalColumnCount + finalColumnCompetence5, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence5, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence5, competenceResponse.getName(), finalStyle);
                            }
                        });
                        columnCompetence5++;
                    }
                    if (competenceCompany.getCompetenceType() == 6) {
                        Row finalRow = sheet.getRow(rowCount + 6);
                        int finalColumnCount = columnCount;
                        int finalColumnCompetence6 = columnCompetence6;
                        CellStyle finalStyle = style;
                        competenceResponseList.stream().filter(competenceResponse -> Objects.equals(competenceResponse.getId(), competenceCompany.getCompetenceId())).findFirst().ifPresent(competenceResponse -> {
                            if(competenceCompany.getProdiId() == 0) {
                                createCell(finalRow, finalColumnCount + finalColumnCompetence6, competenceResponse.getName() + " (D3)", finalStyle);
                            }else if(competenceCompany.getProdiId() == 1){
                                createCell(finalRow, finalColumnCount + finalColumnCompetence6, competenceResponse.getName() + " (D4)", finalStyle);
                            }else{
                                createCell(finalRow, finalColumnCount + finalColumnCompetence6, competenceResponse.getName(), finalStyle);
                            }
                        });
                        columnCompetence6++;
                    }
                }
            }

            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+6, columnDomisili, columnDomisili));
            row = sheet.getRow(rowCount);
            createCell(row, columnDomisili, record.getDomicile(), style);

            rowCount+=7;
        }

        for(int i = 0; i < rowCount; i++){
            row = sheet.getRow(i);
            for(int j = 0; j < columnDomisili+1; j++){
                if(row.getCell(j) == null){
                    row.createCell(j);
                    row.getCell(j).setCellStyle(style);
                }
            }
        }
    }

    public void writeSheetRankingResult(){
        sheet = workbook.createSheet("Hasil Perangkingan");
        Row row = sheet.createRow(0);
        sheet.setColumnWidth(0, (int) (260 * 15.12));
        sheet.setColumnWidth(1, (int) (260 * 28.30));
        sheet.setColumnWidth(2, (260 * 10));

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        headerStyle.setFont(font);

        createCell(row, 0, "Di-generate pada tanggal: "+dateRank, headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        font.setFontHeight(10);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.DOUBLE);
        style.setBorderRight(BorderStyle.DOUBLE);

        row = sheet.createRow(2);


        createCell(row, 0, "Nama", style);
        createCell(row, 1, "Hasil Pembobotan", style);
        createCell(row, 2, "Nilai Akhir", style);
        createCell(row, 3, "Ranking", style);

        int rowCount = 3;

        style = workbook.createCellStyle();
        font = workbook.createFont();
        font.setFontHeight(10);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);


        int startRow = 3;
        for (RankingResponse r :rankingResponseList){
            int ranking = 0;
            int count = 0;
            Double prevValue = null;
            for (ParticipantValue p : r.getParticipants().stream().sorted(Comparator.comparing(ParticipantValue::getResult).reversed()).collect(Collectors.toList())){
                row = sheet.createRow(rowCount++);
                int columnCount = 0;
                createCell(row, columnCount++, r.getCompanyName(), style);
                createCell(row, columnCount++, p.getParticipantName(), style);
                createCell(row, columnCount, String.valueOf(p.getResult()), style);

                count ++;
                if(prevValue == null || !Objects.equals(prevValue, p.getResult())){
                    ranking = count;
                }
                prevValue = p.getResult();
                createCell(row, columnCount+1, String.valueOf(ranking), style);
            }
            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+r.getParticipants().size() - 1, 0, 0));
            startRow = rowCount;
        }
    }

    public void writeSheetCourseValue(){
        sheet = workbook.createSheet("Nilai Mahasiswa");
        Row row = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) (260 * 15.12));

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        createCell(row, 0, "Nama", style);
        createCell(row, 1, "NIM", style);
        createCell(row, 2, "IPK", style);
        createCell(row, 3, "Nama Mata Kuliah", style);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 3+courseResponseList.size()));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        row = sheet.createRow(1);
        for (int i = 0; i < courseResponseList.size(); i++){
            createCell(row, 3+i, courseResponseList.get(i).getName(), style);
        }

        int rowCount = 2;
        for (ParticipantValueList p : participantValueList){
            row = sheet.createRow(rowCount++);

            createCell(row, 0, p.getName(), style);
            createCell(row, 1, String.valueOf(p.getId()), style);
            createCell(row, 2, String.valueOf(p.getIpk()), style);

            for (CourseNameAndValue c : p.getCourseNameAndValue()){
                Row r = sheet.getRow(1);
                for (int i = 0; i < courseResponseList.size(); i++){
                    if(Objects.equals(r.getCell(3 + i).getStringCellValue(), c.getName())){
                        createCell(row, 3 + i, c.getValue(), style);
                    }
                }
            }
        }
    }

    public void writeSheetAbsenceRecap(){
        sheet = workbook.createSheet("Rekap Absen");

        Row row = sheet.createRow(0);





        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        createCell(row, 0, "Jurusan", style);
        createCell(row, 1, ":  Teknik Komputer dan Informatika", style);

        row = sheet.createRow(1);

        createCell(row, 0, "Program Studi", style);
        createCell(row, 1, ":  Teknik Informatika", style);

        row = sheet.createRow(2);


        row = sheet.createRow(4);

        createCell(row, 0, "NIM", style);
        createCell(row, 1, "NAMA", style);
        createCell(row, 2, "SAKIT", style);
        createCell(row, 3, "IZIN", style);
        createCell(row, 4, "ALPHA", style);
        createCell(row, 5, "JUMLAH", style);

        int rowCount = 5;
        for (AbsenceResponse p : absenceResponseList){
            row = sheet.createRow(rowCount++);

            createCell(row, 0, p.getParticipantId(), style);

            Row finalRow = row;
            participantValueList.stream().filter(x -> Objects.equals(x.getId(), p.getParticipantId())).findFirst().ifPresent(
                    x -> createCell(finalRow, 1, x.getName(), style));

            int sick =  (p.getSick() != null) ? p.getSick() : 0;
            int excused = (p.getExcused() != null) ? p.getExcused() : 0;
            int unexcused = (p.getUnexcused() != null) ? p.getUnexcused() : 0;

            createCell(row, 2, String.valueOf(sick), style);
            createCell(row, 3, String.valueOf(excused), style);
            createCell(row, 4, String.valueOf(unexcused), style);

            int total = sick + excused + unexcused;
            createCell(row, 5, String.valueOf(total), style);
        }

    }

    public void generate(HttpServletResponse response) throws IOException {
//        writeHeader();
//        write();

        writeSheetRankingResult();
        writePrerequisite();
        writeSheetCourseValue();
        writeSheetAbsenceRecap();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
