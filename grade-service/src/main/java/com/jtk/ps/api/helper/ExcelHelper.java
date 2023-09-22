package com.jtk.ps.api.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.jtk.ps.api.dto.ComponentAndCriteriasDto;
import com.jtk.ps.api.dto.CriteriaBodyDto;
import com.jtk.ps.api.dto.RecapitulationCourseDto;
import com.jtk.ps.api.dto.RecapitulationParticipantDto;
import com.jtk.ps.api.dto.SeminarTotalValueDto;
import com.jtk.ps.api.dto.SeminarValueParticipantDto;
import com.jtk.ps.api.model.SeminarCriteria;

@Component
public class ExcelHelper {
    
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // private static Comment createComment(String keterangan,Sheet sheet, Workbook workbook, Integer col, Integer row) {
    //   // Membuat objek Drawing untuk menggambar catatan
    //         Drawing<?> drawing = sheet.createDrawingPatriarch();

    //         // Membuat objek ClientAnchor untuk mengatur posisi catatan
    //         ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
    //         anchor.setCol1(col); // Kolom A
    //         anchor.setRow1(row); // Baris ke-i

    //         // Membuat objek Comment dengan catatan yang ingin ditambahkan
    //         Comment comment = drawing.createCellComment(anchor);
    //         RichTextString commentText = workbook.getCreationHelper().createRichTextString("keterangan");
    //         comment.setString(commentText);
    //         System.out.println(comment.getString());

    //         // Mengatur pengarang catatan
    //         comment.setAuthor("Pengguna");            

    //         // Menambahkan catatan ke dalam sel
    //         return comment;
    // }

    public static ByteArrayInputStream recapSeminartoExcelByType(List<SeminarValueParticipantDto> list,String sheetName, List<SeminarCriteria> listCriteria){
        String[] headerNames = { "No", "NIM", "Nama", "Rekapitulasi Nilai Seminar", "Nilai Total" };
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
          Sheet sheet = workbook.createSheet(sheetName);
          // style
          CellStyle boldCellStyle = workbook.createCellStyle();
          Font boldFont = workbook.createFont();
          boldFont.setBold(true);
          boldCellStyle.setFont(boldFont);

          // Header
          Row headerRow = sheet.createRow(0);
    
          for (int col = 0; col < headerNames.length; col++) {
              if(col < 3){
                  Cell cell = headerRow.createCell(col);
                  cell.setCellValue(headerNames[col]);
                  sheet.addMergedRegion(new CellRangeAddress(0, 1, col,col));
                  cell.setCellStyle(boldCellStyle);
              }else if(col == 3) {
                  Cell cell = headerRow.createCell(col);
                  cell.setCellValue(headerNames[col]);
                  sheet.addMergedRegion(new CellRangeAddress(0, 0, col,(2+listCriteria.size())));
                  cell.setCellStyle(boldCellStyle);
              }else{
                  Cell cell = headerRow.createCell(col+listCriteria.size()-1);
                  cell.setCellValue(headerNames[col]);
                  sheet.addMergedRegion(new CellRangeAddress(0, 1, col+listCriteria.size()-1,col+listCriteria.size()-1));
                  cell.setCellStyle(boldCellStyle);
              }
          }

          Row subHeader = sheet.createRow(1);
          int colSub = 3;
          for (int col = 0; col < headerNames.length; col++){
            Cell cell = subHeader.createCell(colSub + col);
            cell.setCellValue(col+1);
            cell.setCellStyle(boldCellStyle);
          }
    
          int rowIdx = 2;
          for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(list.get(i).getPeserta().getName());
            row.createCell(2).setCellValue(list.get(i).getPeserta().getNim());
            for(int j = 0; j < list.get(i).getNilai().size(); j++){
              row.createCell(3+j).setCellValue(list.get(i).getNilai().get(j).getValue());
            }
            row.createCell(3+list.get(i).getNilai().size()).setCellValue(list.get(i).getNilaiTotal());
          }
    
          workbook.write(out);
          return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    
    public static ByteArrayInputStream recapSeminartoExcel(List<SeminarCriteria> listCriteria, List<List<SeminarValueParticipantDto>> listValues, List<SeminarTotalValueDto> listTotal){
      String sheetName;
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
          // style
          CellStyle boldCellStyle = workbook.createCellStyle();
          Font boldFont = workbook.createFont();
          boldFont.setBold(true);
          boldCellStyle.setFont(boldFont);
          
          // sheetname
          for(int i = 0; i < 4; i++){
            if(i <= 1){
              sheetName = "penguji " + (i+1);
            }else if(i == 2){
              sheetName = "pembimbing";
            }else{
              sheetName = "total";
            }
            
            Sheet sheet = workbook.createSheet(sheetName);

            if(i<=2){
              rowHeaderPenguji(sheet, boldCellStyle, listCriteria, listValues.get(i));
            }else{
              rowHeaderTotal(sheet, boldCellStyle, listTotal);
            }
          }
          
          workbook.write(out);
          return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static void rowHeaderPenguji(Sheet sheet, CellStyle boldCellStyle, List<SeminarCriteria> listCriteria, List<SeminarValueParticipantDto> listValues){
      String[] headerPenguji = { "No", "NIM", "Nama", "Rekapitulasi Nilai Seminar", "Nilai Total" };
      Row headerRow = sheet.createRow(0);
            
      for (int col = 0; col < headerPenguji.length; col++) {
        if(col < 3){
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(headerPenguji[col]);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, col,col));
            cell.setCellStyle(boldCellStyle);
        }else if(col == 3) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(headerPenguji[col]);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, col,(2+listCriteria.size())));
            cell.setCellStyle(boldCellStyle);
        }else{
            Cell cell = headerRow.createCell(col+listCriteria.size()-1);
            cell.setCellValue(headerPenguji[col]);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, col+listCriteria.size()-1,col+listCriteria.size()-1));
            cell.setCellStyle(boldCellStyle);
        }
      }

      Row subHeader = sheet.createRow(1);
      int colSub = 3;
      for (int col = 0; col < headerPenguji.length; col++){
        Cell cell = subHeader.createCell(colSub + col);
        cell.setCellValue(col+1);
        cell.setCellStyle(boldCellStyle);
      }

      int rowIdx = 2;
      for (int i = 0; i < listValues.size(); i++) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(i+1);
        row.createCell(1).setCellValue(listValues.get(i).getPeserta().getName());
        row.createCell(2).setCellValue(listValues.get(i).getPeserta().getNim());
        for(int j = 0; j < listValues.get(i).getNilai().size(); j++){
          row.createCell(3+j).setCellValue(listValues.get(i).getNilai().get(j).getValue());
        }
        row.createCell(3+listValues.get(i).getNilai().size()).setCellValue(listValues.get(i).getNilaiTotal());
      }

    }

    public static void rowHeaderTotal(Sheet sheet, CellStyle boldCellStyle,List<SeminarTotalValueDto> listTotal){
      String[] headerTotal = { "No", "NIM", "Nama", "Nilai Total" };
      Row headerRow = sheet.createRow(0);
            
      for (int col = 0; col < headerTotal.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(headerTotal[col]);
        cell.setCellStyle(boldCellStyle);
      }

      int rowIdx = 1;
      for (int i = 0; i < listTotal.size(); i++) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(i+1);
        row.createCell(1).setCellValue(listTotal.get(i).getParticipant().getName());
        row.createCell(2).setCellValue(listTotal.get(i).getParticipant().getNim());
        row.createCell(3).setCellValue(listTotal.get(i).getNilaiTotal());
      }
    }
  
    public static ByteArrayInputStream recapCoursetoExcelByType(String sheetName, List<RecapitulationCourseDto> recap,List<List<ComponentAndCriteriasDto>> listCriterias){
      String[] headerNames = { "No", "NIM", "Nama", "Akhir" };
      String[] headerContentNames = {"Lain-lain Teori","Lain-lain Praktek","UTS Teori", "UTS Praktek","UAS Teori","UAS Praktek"};
       try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
          Sheet sheet = workbook.createSheet(sheetName);
          // style
          CellStyle boldCellStyle = workbook.createCellStyle();
          Font boldFont = workbook.createFont();
          boldFont.setBold(true);
          boldCellStyle.setFont(boldFont);
          boldCellStyle.setWrapText(true);
          boldCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
          boldCellStyle.setAlignment(HorizontalAlignment.CENTER);

          // Header
          Row headerRow = sheet.createRow(0);
          List<ComponentAndCriteriasDto> criterias = listCriterias.get(0);

          for (int col = 0; col < headerNames.length; col++) {
              if(col < 3){
                  Cell cell = headerRow.createCell(col);
                  cell.setCellValue(headerNames[col]);
                  sheet.addMergedRegion(new CellRangeAddress(0, 1, col,col));
                  cell.setCellStyle(boldCellStyle);
              }
          }
          int colBawah = 3;
          for(int col = 0; col < headerContentNames.length;col++){
            int merge = criterias.get(5-col).getCriteria_data().size();
            Cell cell = headerRow.createCell(colBawah);
            cell.setCellValue(headerContentNames[col]);
            if(merge != 0){
              sheet.addMergedRegion(new CellRangeAddress(0, 0, colBawah,(colBawah+merge-1)));
            }
            cell.setCellStyle(boldCellStyle);
            colBawah += merge==0?1:merge;

            Cell cell2 = headerRow.createCell(colBawah);
            cell2.setCellValue("Total "+headerContentNames[col]);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, colBawah,colBawah));
            cell2.setCellStyle(boldCellStyle);
            colBawah +=1;
          }

          // buat header nilai akhir
          Cell cellAkhir = headerRow.createCell(colBawah);
          cellAkhir.setCellValue(headerNames[3]);
          sheet.addMergedRegion(new CellRangeAddress(0, 1, colBawah,colBawah));
          cellAkhir.setCellStyle(boldCellStyle);

          // membuat subheader
          Row subHeader = sheet.createRow(1);
          int colSub = 3;
          int jmlCriteria = 1;
          for (int col = 0; col < headerContentNames.length; col++){
            int merge = criterias.get(5-col).getCriteria_data().size();
            for(int i = 0; i < merge; i++){
              Cell cell = subHeader.createCell(colSub);
              cell.setCellValue(jmlCriteria++);
              cell.setCellStyle(boldCellStyle);
              colSub += 1;
            }
            if(merge == 0){
              Cell cell = subHeader.createCell(colSub);
              cell.setCellValue(jmlCriteria++);
              cell.setCellStyle(boldCellStyle);
              colSub += 1;
            }
            colSub += 1;
          }
    
          // membuat nilai
          int rowIdx = 2;
          for (int i = 0; i < recap.get(0).getParticipant_data().size(); i++) {
            RecapitulationParticipantDto participant = recap.get(0).getParticipant_data().get(i);
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(participant.getNim());
            row.createCell(2).setCellValue(participant.getName());
            int col = 3;
            for(int j = participant.getComponent_data().size() -1; j >= 0; j--){
              int merge = participant.getComponent_data().get(j).getCriteria_data().size();
              for (int k = 0; k < merge; k++) {
                row.createCell(col++).setCellValue(participant.getComponent_data().get(j).getCriteria_data().get(k).getValue());
              }
              if(merge == 0){
                row.createCell(col++).setCellValue(0);
              }
              row.createCell(col++).setCellValue(participant.getComponent_data().get(j).getTotalValueComponent());
            }
            row.createCell(col++).setCellValue(participant.getTotal_course());
          }
    
          // membuat keterangan
          int rowKet = rowIdx+3;
          Row rowHeaderKet = sheet.createRow(rowKet++);
          rowHeaderKet.createCell(0).setCellValue("No");
          rowHeaderKet.createCell(1).setCellValue("Form");
          rowHeaderKet.createCell(2).setCellValue("Aspek");
          rowHeaderKet.setRowStyle(boldCellStyle);
          int no = 1;
          for(int col = 0; col < headerContentNames.length;col++){
            int jmlAspek = criterias.get(5-col).getCriteria_data().size();
            List<CriteriaBodyDto> cList = criterias.get(5-col).getCriteria_data();
            
            for (int i = 0; i < jmlAspek; i++) {
              Row row = sheet.createRow(rowKet++);
              row.createCell(0).setCellValue(no++);
              row.createCell(1).setCellValue(cList.get(i).getNameForm()+" ("+cList.get(i).getTypeForm()+")");
              row.createCell(2).setCellValue(cList.get(i).getAspectName());
            }
            if(jmlAspek == 0){
              no++;
            }
          }
          workbook.write(out);
          return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
  
    public static ByteArrayInputStream recapCoursetoExcel(List<RecapitulationCourseDto> recap,List<List<ComponentAndCriteriasDto>> listCriterias){
      String[] headerNames = { "No", "NIM", "Nama", "Akhir" };
      String[] headerContentNames = {"Lain-lain Teori","Lain-lain Praktek","UTS Teori", "UTS Praktek","UAS Teori","UAS Praktek"};
      String sheetName;
       try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
          // style
          CellStyle boldCellStyle = workbook.createCellStyle();
          Font boldFont = workbook.createFont();
          boldFont.setBold(true);
          boldCellStyle.setFont(boldFont);
          boldCellStyle.setWrapText(true);
          boldCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
          boldCellStyle.setAlignment(HorizontalAlignment.CENTER);

          for(int i = 0; i < recap.size(); i++){
            
            sheetName = recap.get(i).getNameCourse();
            
            Sheet sheet = workbook.createSheet(sheetName);
            System.out.println("listCriterias nilai ==>"+listCriterias.get(i));
            rowCourses(sheet, boldCellStyle, listCriterias.get(i), headerNames, headerContentNames, recap.get(i).getParticipant_data());
          }
          
          workbook.write(out);
          return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
  
    private static void rowCourses(Sheet sheet, CellStyle boldCellStyle, List<ComponentAndCriteriasDto> criterias, String[] headerNames, String[] headerContentNames, List<RecapitulationParticipantDto> participants){
      Row headerRow = sheet.createRow(0);

          for (int col = 0; col < headerNames.length; col++) {
              if(col < 3){
                  Cell cell = headerRow.createCell(col);
                  cell.setCellValue(headerNames[col]);
                  sheet.addMergedRegion(new CellRangeAddress(0, 1, col,col));
                  cell.setCellStyle(boldCellStyle);
              }
          }
          int colBawah = 3;
          System.out.println("nilai criterias"+criterias.size());
          for(int col = 0; col < headerContentNames.length;col++){
            int merge = 0;
            if(!criterias.isEmpty()){
              merge = criterias.get(5-col).getCriteria_data().size();
            }
            Cell cell = headerRow.createCell(colBawah);
            cell.setCellValue(headerContentNames[col]);
            if(merge != 0){
              sheet.addMergedRegion(new CellRangeAddress(0, 0, colBawah,(colBawah+merge-1)));
            }
            cell.setCellStyle(boldCellStyle);
            colBawah += merge==0?1:merge;

            Cell cell2 = headerRow.createCell(colBawah);
            cell2.setCellValue("Total "+headerContentNames[col]);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, colBawah,colBawah));
            cell2.setCellStyle(boldCellStyle);
            colBawah +=1;
          }

          // buat header nilai akhir
          Cell cellAkhir = headerRow.createCell(colBawah);
          cellAkhir.setCellValue(headerNames[3]);
          sheet.addMergedRegion(new CellRangeAddress(0, 1, colBawah,colBawah));
          cellAkhir.setCellStyle(boldCellStyle);

          // membuat subheader
          Row subHeader = sheet.createRow(1);
          int colSub = 3;
          int jmlCriteria = 1;
          for (int col = 0; col < headerContentNames.length; col++){
            int merge = 0;
            if(!criterias.isEmpty()){
              merge = criterias.get(5-col).getCriteria_data().size();
            }
            for(int i = 0; i < merge; i++){
              Cell cell = subHeader.createCell(colSub);
              cell.setCellValue(jmlCriteria++);
              cell.setCellStyle(boldCellStyle);
              colSub += 1;
            }
            if(merge == 0){
              Cell cell = subHeader.createCell(colSub);
              cell.setCellValue(jmlCriteria++);
              cell.setCellStyle(boldCellStyle);
              colSub += 1;
            }
            colSub += 1;
          }
    
          // membuat nilai
          int rowIdx = 2;
          for (int i = 0; i < participants.size(); i++) {
            RecapitulationParticipantDto participant = participants.get(i);
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(participant.getNim());
            row.createCell(2).setCellValue(participant.getName());
            int col = 3;
            for(int j = participant.getComponent_data().size() -1; j >= 0; j--){
              int merge = participant.getComponent_data().get(j).getCriteria_data().size();
              for (int k = 0; k < merge; k++) {
                row.createCell(col++).setCellValue(participant.getComponent_data().get(j).getCriteria_data().get(k).getValue());
              }
              if(merge == 0){
                row.createCell(col++).setCellValue(0);
              }
              row.createCell(col++).setCellValue(participant.getComponent_data().get(j).getTotalValueComponent());
            }
            row.createCell(col++).setCellValue(participant.getTotal_course());
          }
    
          // membuat keterangan
          int rowKet = rowIdx+3;
          Row rowHeaderKet = sheet.createRow(rowKet++);
          rowHeaderKet.createCell(0).setCellValue("No");
          rowHeaderKet.createCell(1).setCellValue("Form");
          rowHeaderKet.createCell(2).setCellValue("Aspek");
          rowHeaderKet.setRowStyle(boldCellStyle);
          int no = 1;
          for(int col = 0; col < headerContentNames.length;col++){
            int jmlAspek = 0;
            List<CriteriaBodyDto> cList = new ArrayList<>();
            if(!criterias.isEmpty()){
              jmlAspek = criterias.get(5-col).getCriteria_data().size();
              cList = criterias.get(5-col).getCriteria_data();
            }
            
            for (int i = 0; i < jmlAspek; i++) {
              Row row = sheet.createRow(rowKet++);
              row.createCell(0).setCellValue(no++);
              row.createCell(1).setCellValue(cList.get(i).getNameForm()+" ("+cList.get(i).getTypeForm()+")");
              row.createCell(2).setCellValue(cList.get(i).getAspectName());
            }
            if(jmlAspek == 0){
              no++;
            }
          }
    }
  }

