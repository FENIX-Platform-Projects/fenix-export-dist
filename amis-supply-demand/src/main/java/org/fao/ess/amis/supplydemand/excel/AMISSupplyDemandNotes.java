package org.fao.ess.amis.supplydemand.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.fao.ess.amis.supplydemand.dao.AMISConstants;
import org.fao.ess.amis.supplydemand.dao.AMISQueryVO;

import java.util.LinkedHashMap;


public class AMISSupplyDemandNotes {

    private static final Logger LOGGER = Logger.getLogger(AMISSupplyDemandNotes.class);
    private static final String PSD_SOYBEANS_FOOTNOTE = "Feed Waste Domestic Consumption";
    private static final String IGC_SOYBEANS_FOOTNOTE = "Feed";

     public static boolean createSoybeansFootNotes(int rowCounter, HSSFWorkbook workbook, Sheet sheet, AMISQueryVO qvo, String product){
        //Footnotes
        //IGC & PSD Soybeans Footnote
        boolean footnoteAdded = false;

         if(qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("PRODUCT")){
             if(qvo.getDatabases().containsKey(AMISConstants.IGC.name()) || qvo.getDatabases().containsKey(AMISConstants.PSD.name())){
                 LOGGER.info("Soybeans createFootnote: start COUNTRY/PRODUCT");

                 String productCode = "";
                 if(qvo.getxLabel().equals("COUNTRY")){
                     productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
                 } else if(qvo.getxLabel().equals("PRODUCT")){
                     productCode = AMISSupplyDemandUtils.getProductCode(qvo, product);
                 }


                 if(productCode.equals("6")){
                     LOGGER.info("IS Soybeans createFootnote: ");

                     if(qvo.getDatabases().containsKey(AMISConstants.IGC.name()))   {
                         LOGGER.info(".. and IGC createFootnote ");
                         createFixedFootnotes(rowCounter, sheet, workbook, "Other Uses", IGC_SOYBEANS_FOOTNOTE, false);
                         footnoteAdded = true;
                     }
                     else if(qvo.getDatabases().containsKey(AMISConstants.PSD.name()))   {
                         LOGGER.info(".. and PSD createFootnote ");
                         createFixedFootnotes(rowCounter, sheet, workbook, "Other Uses", PSD_SOYBEANS_FOOTNOTE, false);
                         footnoteAdded = true;
                     }
                }
             }
         } else if(qvo.getxLabel().equals("DATASOURCE")){
             LOGGER.info("Soybeans createFootnote: start DATASOURCES ");

             String productCode =  AMISSupplyDemandUtils.getProductCode(qvo, "");

             if(productCode.equals("6") && product.equals(AMISConstants.IGC.name())) {
                 LOGGER.info("IS Soybeans AND IGC createFootnote: ");
                 createFixedFootnotes(rowCounter, sheet, workbook, "Other Uses", IGC_SOYBEANS_FOOTNOTE, false);
                 footnoteAdded = true;
             }  else if(productCode.equals("6") && product.equals(AMISConstants.PSD.name())){
                 LOGGER.info("IS Soybeans AND PSD createFootnote: ");
                 createFixedFootnotes(rowCounter, sheet, workbook, "Other Uses", PSD_SOYBEANS_FOOTNOTE, false);
                 footnoteAdded = true;
             }

         }

         return  footnoteAdded;
     }




    public static int createFootnotes(int rowCounter, Sheet sheet, HSSFWorkbook workbook, LinkedHashMap<String, String> elementNotes, boolean footnoteAdded){


        if(!footnoteAdded){
            LOGGER.info("createFootnote: 0");
            rowCounter = AMISSupplyDemandExcelUtils.createEmptyRow(rowCounter, sheet, workbook);

            LOGGER.info("createFootnote: 1");

            rowCounter = createInformationRow(rowCounter, sheet,  workbook, "FOOTNOTES:", "", true, 0);
        }

        LOGGER.info("createFootnote: 2");

        int t = rowCounter++;

        LOGGER.info("createFootnote: 3 elementNotesMap = "+elementNotes);

        for(String element: elementNotes.keySet()){
            sheet.addMergedRegion(new CellRangeAddress(t, (short) 0, t, (short) (2)));
            createInformationRow(t, sheet,  workbook, element+" = "+elementNotes.get(element), "", true, 0);

            t++;
        }

        LOGGER.info("createFootnote: 4 END ");
        return t;
    }


    public static int createFixedFootnotes(int rowCounter, Sheet sheet, HSSFWorkbook workbook, String header, String value, boolean footnoteAdded){
      if(!footnoteAdded){
            LOGGER.info("createStandardFootnotes: 0");
             rowCounter = AMISSupplyDemandExcelUtils.createEmptyRow(rowCounter, sheet, workbook);

            LOGGER.info("createStandardFootnotes: 1");

            rowCounter = createInformationRow(rowCounter, sheet,  workbook, "FOOTNOTES:", "", true, 0);
        }

        LOGGER.info("createStandardFootnotes: 2");

        int t = rowCounter++;
        sheet.addMergedRegion(new CellRangeAddress(t, (short) 0, t, (short) (2)));
        createInformationRow(t, sheet,  workbook, header+" = "+value, "", true, 0);

        LOGGER.info("createStandardFootnotes: 4 END ");
        return t;
    }

    public static int createInformationRow(int rowCounter, Sheet sheet,  HSSFWorkbook workbook, String header, String headerValue, Boolean isBold, int cellIndex){
        Row row = sheet.createRow(rowCounter++);

        if(header != null && headerValue==null){
            Cell cell = row.createCell((short) cellIndex);
            cell.setCellStyle(AMISSupplyDemandExcelUtils.getSmallTextCellStyle(workbook, null, isBold));
            cell.setCellValue(header);

            row.createCell((short) 1).setCellValue("");
        }
        else {
            Cell cell = row.createCell((short) cellIndex);
            cell.setCellStyle(AMISSupplyDemandExcelUtils.getSmallTextCellStyle(workbook, null, isBold));
            cell.setCellValue(header);

            cell = row.createCell((short) (cellIndex+1));
            cell.setCellStyle(AMISSupplyDemandExcelUtils.getSmallTextCellStyle(workbook, null, isBold));
            cell.setCellValue(headerValue);
        }


         return rowCounter;
    }



    public static int createMarketingTradeNoteRow(int rowCounter, Sheet sheet, HSSFWorkbook workbook, String note){
        Row row = sheet.createRow(rowCounter++);
        Cell cell = row.createCell((short) 0);
        HSSFCellStyle cellStyle = AMISSupplyDemandExcelUtils.getLeftAlignmentStyle(workbook);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(note);

        return rowCounter;
    }


    public static String buildNote(String value, String baseYear, String period, String type){

        StringBuilder note = new StringBuilder();

        if(!value.equals("n.a")){
           note.append("For example, in the "+baseYear+" balance, the "+type+" refers to the period from "+period);
        }
        return  note.toString();
    }

    public static String buildNote(String value, String baseYear, String period, String harvestingPeriod, String type){

        StringBuilder note = new StringBuilder();

        if(!value.equals("n.a")){
          if(harvestingPeriod.equals("") || (harvestingPeriod==null))    {
            note.append("For example, in the "+baseYear+" balance, the "+type+" refers to the period from "+period);
          }
         else {
             note.append("For example, in the "+baseYear+" balance, the "+type+" refers to the crop that is harvested ");
             if(harvestingPeriod.contains(" to ")){
                 note.append("from "+harvestingPeriod+" ");
             } else {
                 note.append("in "+harvestingPeriod+" ");
             }
             note.append("and marketed between "+period);
         }
        }

        return  note.toString();
    }

    private String getProductCode(AMISQueryVO qvo, String product){
        String productCode = "";
        if(qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")){
            for(String prod: qvo.getItems().keySet()){
                productCode =  prod;
            }
        } else if(qvo.getxLabel().equals("PRODUCT")){
            if(product.equals("Rice (milled)")){
                product = "Rice";
            }
            for(String prod: qvo.getItems().keySet()){
                String prodLabel =  qvo.getItems().get(prod);
                if(prodLabel.contains(product))  {
                    productCode = prod;
                    break;
                }
            }
        }

        return productCode;

    }


}