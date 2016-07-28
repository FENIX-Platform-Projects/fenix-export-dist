package org.fao.ess.amis.supplydemand.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.fao.ess.amis.supplydemand.dao.AMISConstants;
import org.fao.ess.amis.supplydemand.dao.AMISQueryVO;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class AMISSupplyDemandExcel {

    private static final Logger LOGGER = Logger.getLogger(AMISSupplyDemandExcel.class);
    private static final String FOOD_BALANCE_TABLE_TITLE = "Supply and Demand Balance";
    private static final String OTHER_TABLE_TITLE = "Other";
    private static final String ITY_TABLE_TITLE = "";
    private static final String DISTRIBUTED_BY_AMIS_STATISTICS = "Distributed by AMIS Statistics";
    private static final String NATIONAL_MARKETING_YEAR = "National Marketing Year";
    private static final String INTERNATIONAL_TRADE_YEAR = "International Trade Year";
    private static final String DATA_SUBJECT_TO_REVISIONS = "This data may be subject to revisions";
    //    private static final String CBS_RICE_NOTE = "Trade estimates correspond to January-December of the first year shown in the case of a Southern Hemisphere country, or to January-December of the second year shown in the case of a Northern Hemisphere country.";
    private static final String CBS_RICE_NOTE = null;


    //Supply, Opening Stocks, Production, imports nmy, total utilization, exports nmy,  closing stocks
    private final static String[] FOOD_BALANCE_ELEMENTS = {"19", "18", "5", "7", "35", "10", "16"};

    //supply, total utilization
    public static final Set<String> FOOD_BALANCE_ELEMENTS_HIGHLIGHTED = new HashSet<String>(Arrays.asList(
            new String[]{"19", "35"}
    ));

    // ORIGINAL: food use, feed use, other uses, crush, feed and residual, food/seeds/industrial use
    // public static final Set<String> FOOD_BALANCE_ELEMENTS_INDENTED = new HashSet<String>(Arrays.asList(
    //        new String[] {"14", "13", "15", "36", "38", "24"}
    // ));

    // food use, feed use, other uses, crush, feed and residual, food/seeds/industrial use, seeds, industrial use, residual, post harvest losses
    public static final Set<String> FOOD_BALANCE_ELEMENTS_INDENTED = new HashSet<String>(Arrays.asList(
            new String[]{"14", "13", "15", "36", "38", "24", "21", "28", "39", "34"}
    ));

    //seeds, post harvest losses, industrial use
    public static final Set<String> FOOD_BALANCE_ELEMENTS_MORE_INDENTED = new HashSet<String>(Arrays.asList(
            new String[]{"21", "34", "28"}
    ));

    // wheat, maize, soybeans, rice
    public static final Set<String> FOOD_BALANCE_PRODUCTS = new HashSet<String>(Arrays.asList(
            new String[]{"1", "5", "6", "4"}
    ));


    //imports ity, exports ity
    private final static String[] ITY_ELEMENTS = {"8", "12"};

    private AmisSupplyDemandExcelUtilsNew utils;

    public String createExcel(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> foodBalanceResults, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> otherResults, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> ityResults, AMISQueryVO qvo) {
        LOGGER.info("**************** START  createFoodBalanceExcel ****************");
        LOGGER.info("**************** otherResults = " + otherResults);

        System.out.println("**************** START  createFoodBalanceExcel ****************");
        System.out.println(foodBalanceResults.toString());
        System.out.println(otherResults.toString());
        System.out.println(ityResults.toString());
        String title = "";
        // initiate filename
        StringBuilder filename = new StringBuilder();
       /* filename.append(Setting.getSystemPath());
        filename.append("/exportObject/");
        String title = "" + BirtUtil.randomChartName();
        filename.append(title);
        filename.append(".xls");*/


        // create the Excel file
        HSSFWorkbook workbook = new HSSFWorkbook();

        utils = new AmisSupplyDemandExcelUtilsNew();
        utils.initStyles(workbook);

      /*  AMISSupplyDemandExcelUtils.setCustomizedPalette(workbook);
        AMISSupplyDemandExcelUtils.initializeFontStyles(workbook);
*/
/*
        ///// OLD excel utils //////////////////

        AMISSupplyDemandExcelUtils.setCustomizedPalette(workbook);
                AMISSupplyDemandExcelUtils.initializeFontStyles(workbook);


*/


        //Initialize font

        // create Country or Product Workbook
        if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("PRODUCT")) {
            LOGGER.info("**************** qvo.getDatabases() = " + qvo.getDatabases());
            String datasource = utils.convertMapItemCodesToString(qvo.getDatabases());
            LOGGER.info("**************** datasource = " + datasource);
            workbook = createStandardWorkbook(workbook, foodBalanceResults.get(datasource), otherResults.get(datasource), ityResults.get(datasource), qvo);
        }
        // create Datasource Workbook
        else if (qvo.getxLabel().equals("DATASOURCE")) {
            workbook = createDatasourceWorkbook(workbook, foodBalanceResults, otherResults, ityResults, qvo);
        }


        LOGGER.info("**************** END  createFoodBalanceExcel ****************");

        try {
            // Write the output to a file
            // TODO:test
            //FileOutputStream fileOut = new FileOutputStream(filename.toString());

            FileOutputStream fileOut = new FileOutputStream("/home/faber-cst/Desktop/test_new_1234.xls");

            System.out.println("Exported EXCEL File name = " + filename.toString());
            workbook.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }


        return title + ".xls";
//		return title + ".xlsx";
    }


    private HSSFWorkbook createStandardWorkbook(HSSFWorkbook workbook, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> foodBalanceResults, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> otherResults, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> ityResults, AMISQueryVO qvo) {


        LOGGER.info("**************** qvo.getFoodBalanceElements() = " + qvo.getFoodBalanceElements());
        LOGGER.info("**************** foodBalanceResults = " + foodBalanceResults);

        //Prepare HashMaps: Food Balance
        LinkedHashMap<String, Map<String, String>> countryDatesMapFB = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, String> countriesMapFB = new LinkedHashMap<String, String>();

        if ((foodBalanceResults != null) && (!foodBalanceResults.isEmpty())) {
            LOGGER.info("****************  foodBalanceResults NOT EMPTY ");
            for (String country : foodBalanceResults.keySet()) {
                // countryDatesMapFB.put(country, qvo.getYears());
                countryDatesMapFB.put(country, qvo.getCountryDates().get(country));
                countriesMapFB.put(country, country);
            }
        }
        //Prepare HashMaps: Other
        LinkedHashMap<String, Map<String, String>> countryDatesMapOther = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, String> countriesMapOther = new LinkedHashMap<String, String>();

        if ((otherResults != null) && (!otherResults.isEmpty())) {
            LOGGER.info("**************** otherResults NOT EMPTY ");
            for (String country : otherResults.keySet()) {
                System.out.println("YYY country = " + country);
                LinkedHashMap<String, Map<String, Double>> years = otherResults.get(country);
                // countryDatesMapOther.put(country, qvo.getYears());
                countryDatesMapOther.put(country, qvo.getCountryDates().get(country));

                countriesMapOther.put(country, country);
            }
        }


        //Prepare HashMaps: ITY
        LinkedHashMap<String, String> ityElements = qvo.getItyElements();
        if (qvo.getItyElements().isEmpty()) {
            for (String element : qvo.getElements().keySet()) {
                ityElements.put(element, element);
            }
        }

        LinkedHashMap<String, Map<String, String>> countryDatesMapITY = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, String> countriesMapITY = new LinkedHashMap<String, String>();

        if ((ityResults != null) && (!ityResults.isEmpty())) {
            LOGGER.info("**************** ityResults NOT EMPTY ");
            for (String country : ityResults.keySet()) {
                LinkedHashMap<String, Map<String, Double>> years = ityResults.get(country);
                countryDatesMapITY.put(country, qvo.getCountryDates().get(country));
                //countryDatesMapITY.put(country,  qvo.getYears());
                countriesMapITY.put(country, country);
            }
        } else {
            //so empty cells can created
            for (String country : foodBalanceResults.keySet()) {
                //countryDatesMapITY.put(country,  qvo.getYears());
                countryDatesMapITY.put(country, qvo.getCountryDates().get(country));
                countriesMapITY.put(country, country);
            }
        }

        //Re-initialize  the country map if Food Balance result is null or zero
        if (countriesMapFB.isEmpty() && !countriesMapOther.isEmpty()) {
            countriesMapFB = countriesMapOther;
        }


        for (String country : countriesMapFB.keySet()) {
            LOGGER.info("... START .." + country + " || " + qvo.getxLabel());
            String label = "";
            String datasource = utils.convertMapItemCodesToString(qvo.getDatabases());

            if (qvo.getxLabel().equals("COUNTRY")) {
                label = utils.convertMapItemLabelsToString(qvo.getItems());

                LOGGER.info("... label .." + label);
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                label = utils.convertMapItemLabelsToString(qvo.getAreas());
            }


            String sheet_name = AMISSupplyDemandUtils.formatSheetName(country, label, datasource);

            LinkedHashMap<String, String> foodBalanceElements = getFoodBalanceElements(qvo, country, datasource);
            LinkedHashMap<String, String> otherElements = getOtherElements(qvo, country, datasource);

            LOGGER.info("... sheet_name .." + sheet_name);
            //Create a separate sheet for each country
            HSSFSheet sheet = workbook.createSheet(sheet_name);
            int rowCounter = 0;

            LOGGER.info("... BEFORE SUMMARY ..");

            rowCounter = createSummary(rowCounter, sheet, workbook, qvo, country, label, "");

            LOGGER.info("... AFTER SUMMARY .. country  " + country);
            LOGGER.info("... AFTER SUMMARY .. countryDatesMapFB.isEmpty() " + countryDatesMapFB.isEmpty());
            LOGGER.info("... AFTER SUMMARY .. countriesMapFB.isEmpty() " + !countriesMapFB.isEmpty());

            if (!countryDatesMapFB.isEmpty() && !countriesMapFB.isEmpty()) {
                LOGGER.info("... datesMapFB && countryDatesMapFB && countriesMapFB IS NOT EMPTY  " + country);

                //Build FB Table
                if (foodBalanceResults.get(country) != null && !foodBalanceResults.get(country).isEmpty()) {
                    LOGGER.info("----------- FOOD BALANCE " + country);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, foodBalanceResults, FOOD_BALANCE_TABLE_TITLE, countryDatesMapFB.get(country), country, label, datasource, foodBalanceElements, qvo);
                } else {
                    rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, FOOD_BALANCE_TABLE_TITLE);
                }

            } else {
                LOGGER.info("... BUILD EMPTY TABLE   " + country);
                rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, FOOD_BALANCE_TABLE_TITLE);
            }


            if (!countryDatesMapITY.isEmpty() && !countriesMapITY.isEmpty()) {
                LOGGER.info("... countryDatesMapITY && countriesMapITY IS NOT EMPTY  " + country);
                //Build ITY Table
                if (ityResults != null && ityResults.containsKey(country)) {
                    LOGGER.info("----------- ITY " + country);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, ityResults, ITY_TABLE_TITLE, countryDatesMapITY.get(country), country, label, datasource, ityElements, qvo);
                } else {
                    LOGGER.info("----------- ELSE ITY " + country);
                    LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> emptyAreaResults = new LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>();
                    LinkedHashMap<String, Map<String, Double>> emptyYearMap = new LinkedHashMap<String, Map<String, Double>>();
                    Map<String, Double> emptyValues = new HashMap<String, Double>();
                    LinkedHashMap<String, String> countryYears = qvo.getCountryDates().get(country);

                    //for(String yearVal:  qvo.getYears().keySet()){
                    for (String yearVal : countryYears.keySet()) {
                        for (String element : ityElements.keySet()) {
                            emptyValues.put(element, null);
                        }
                        emptyYearMap.put(yearVal, emptyValues);
                    }
                    emptyAreaResults.put(country, emptyYearMap);

                    LOGGER.info("----------- ITY ELSE " + country);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, emptyAreaResults, ITY_TABLE_TITLE, countryDatesMapFB.get(country), country, label, datasource, ityElements, qvo);
                    //  rowCounter =  createNoAvailableDataTable(rowCounter, sheet, workbook, ITY_TABLE_TITLE) ;
                }
            } else {
                LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> emptyAreaResults = new LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>();
                LinkedHashMap<String, Map<String, Double>> emptyYearMap = new LinkedHashMap<String, Map<String, Double>>();
                Map<String, Double> emptyValues = new HashMap<String, Double>();
                LinkedHashMap<String, String> countryYears = qvo.getCountryDates().get(country);

                // for(String yearVal:  qvo.getYears().keySet()){
                for (String yearVal : countryYears.keySet()) {
                    for (String element : ityElements.keySet()) {
                        emptyValues.put(element, null);
                    }
                    emptyYearMap.put(yearVal, emptyValues);
                }
                emptyAreaResults.put(country, emptyYearMap);

                LOGGER.info("----------- ITY ELSE " + country);
                rowCounter = createDataTable(rowCounter, sheet, workbook, emptyAreaResults, ITY_TABLE_TITLE, countryDatesMapFB.get(country), country, label, datasource, ityElements, qvo);

                // rowCounter =  AMISSupplyDemandExcelUtils.createNoAvailableDataTable(rowCounter, sheet, workbook, ITY_TABLE_TITLE) ;
            }

            if (!countryDatesMapOther.isEmpty() && !countriesMapOther.isEmpty()) {
                //Build Other Table
                LOGGER.info("... countryDatesMapOther && countriesMapOther IS NOT EMPTY  " + country);

                if (otherResults.get(country) != null && !otherResults.get(country).isEmpty()) {
                    LOGGER.info("----------- OTHER " + country);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, otherResults, OTHER_TABLE_TITLE, countryDatesMapOther.get(country), country, label, datasource, otherElements, qvo);
                } else {
                    rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, OTHER_TABLE_TITLE);
                }
            } else {
                LOGGER.info("... countryDatesMapOther && countriesMapOther IS EMPTY  " + country);
                rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, OTHER_TABLE_TITLE);
            }

            //Footnotes
            //IGC & PSD Soybeans Footnote
            boolean footnoteAdded = AMISSupplyDemandNotes.createSoybeansFootNotes(rowCounter, workbook, sheet, qvo, country);


            //Footnotes
            String productCode = "";
            String countryLabel = "";

            if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
                countryLabel = country;
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, country);
                countryLabel = label;
            }

         /*TODO:false
         if( qvo.getElementFootNotesByCountry().get(datasource)!=null && qvo.getElementFootNotesByCountry().get(datasource).get(productCode)!=null  && !qvo.getElementFootNotesByCountry().get(datasource).get(productCode).isEmpty())   {
                if(qvo.getElementFootNotesByCountry().get(datasource).get(productCode).get(countryLabel)!=null)
                    AMISSupplyDemandNotes.createFootnotes(rowCounter, sheet, workbook, qvo.getElementFootNotesByCountry().get(datasource).get(productCode).get(countryLabel), footnoteAdded);

            }*/


            //Fix the width of the first two columns
            sheet.setColumnWidth((short) 0, (short) 7500);
            sheet.setColumnWidth((short) 1, (short) 5000);

            //Fix to landscape
            sheet.getPrintSetup().setLandscape(true);

            //Fit to one page
            PrintSetup ps = sheet.getPrintSetup();
            sheet.setAutobreaks(true);
            ps.setFitHeight((short) 1);
            ps.setFitWidth((short) 1);

        }

        return workbook;
    }


    private HSSFWorkbook createDatasourceWorkbook(HSSFWorkbook workbook, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> foodBalanceResults, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> otherResults, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> ityResults, AMISQueryVO qvo) {

        LOGGER.info("**************** createDatasourceWorkbook START .............");

        LinkedHashMap<String, Map<String, String>> countryDatesMapF = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> databaseCountriesMapFB = new LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>();

        if ((foodBalanceResults != null) && (!foodBalanceResults.isEmpty())) {
            LOGGER.info("****************  foodBalanceResults NOT EMPTY ");
            for (String datasource : foodBalanceResults.keySet()) {
                for (String country : foodBalanceResults.get(datasource).keySet()) {
                    countryDatesMapF.put(country, qvo.getCountryDates().get(country));
                    // countryDatesMapF.put(country, qvo.getYears());
                }
                databaseCountriesMapFB.put(datasource, countryDatesMapF);
            }
        }
        //Prepare HashMaps: Other

        LinkedHashMap<String, Map<String, String>> countryDatesMapO = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> databaseCountriesMapOther = new LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>();

        if ((otherResults != null) && (!otherResults.isEmpty())) {
            LOGGER.info("**************** otherResults NOT EMPTY ");
            for (String datasource : otherResults.keySet()) {
                for (String country : otherResults.get(datasource).keySet()) {
                    countryDatesMapO.put(country, qvo.getCountryDates().get(country));
                    //countryDatesMapO.put(country, qvo.getYears());
                }

                databaseCountriesMapOther.put(datasource, countryDatesMapO);
            }
        }


        //Prepare HashMaps: ITY
        LinkedHashMap<String, String> ityElements = qvo.getItyElements();
        if (qvo.getItyElements().isEmpty()) {
            for (String element : qvo.getElements().keySet()) {
                ityElements.put(element, element);
            }
        }

        LinkedHashMap<String, Map<String, String>> countryDatesMapI = new LinkedHashMap<String, Map<String, String>>();
        LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> databaseCountriesMapITY = new LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>();

        if ((ityResults != null) && (!ityResults.isEmpty())) {
            LOGGER.info("**************** ityResults NOT EMPTY ");
            for (String datasource : ityResults.keySet()) {
                for (String country : ityResults.get(datasource).keySet()) {
                    countryDatesMapI.put(country, qvo.getCountryDates().get(country));
                    // countryDatesMapI.put(country,  qvo.getYears());
                }

                databaseCountriesMapITY.put(datasource, countryDatesMapI);
            }
        } else {
            //so empty cells can created
            for (String datasource : foodBalanceResults.keySet()) {
                for (String country : foodBalanceResults.get(datasource).keySet()) {
                    countryDatesMapI.put(country, qvo.getCountryDates().get(country));
                    //countryDatesMapI.put(country,  qvo.getYears());
                }
                databaseCountriesMapITY.put(datasource, countryDatesMapI);
            }
        }

        //Re-initialize  the country map if Food Balance result is null or zero
        if (databaseCountriesMapFB.isEmpty() && !databaseCountriesMapOther.isEmpty()) {
            databaseCountriesMapFB = databaseCountriesMapOther;
        }

        for (String datasource : databaseCountriesMapFB.keySet()) {

            LinkedHashMap<String, Map<String, String>> countryDatesMapFB = databaseCountriesMapFB.get(datasource);
            LinkedHashMap<String, Map<String, String>> countryDatesMapOther = new LinkedHashMap<String, Map<String, String>>();
            LinkedHashMap<String, Map<String, String>> countryDatesMapITY = new LinkedHashMap<String, Map<String, String>>();


            if (!databaseCountriesMapOther.isEmpty() && databaseCountriesMapOther.containsKey(datasource))
                countryDatesMapOther = databaseCountriesMapOther.get(datasource);

            if (!databaseCountriesMapITY.isEmpty() && databaseCountriesMapITY.containsKey(datasource))
                countryDatesMapITY = databaseCountriesMapITY.get(datasource);

            LOGGER.info("... START .." + datasource + " || " + qvo.getxLabel());
            String prod = utils.convertMapItemLabelsToString(qvo.getItems());
            String area = utils.convertMapItemLabelsToString(qvo.getAreas());

            String sheet_name = AMISSupplyDemandUtils.formatSheetName(datasource, prod, area);
            LOGGER.info("... sheet_name .." + sheet_name);

            LinkedHashMap<String, String> foodBalanceElements = getFoodBalanceElements(qvo, "", datasource);
            LOGGER.info("... foodBalanceElements .." + foodBalanceElements);
            LinkedHashMap<String, String> otherElements = getOtherElements(qvo, "", datasource);
            LOGGER.info("... otherElements .." + otherElements);


            //Create a separate sheet for each country
            HSSFSheet sheet = workbook.createSheet(sheet_name);
            int rowCounter = 0;

            LOGGER.info("... BEFORE SUMMARY ..");

            rowCounter = createSummary(rowCounter, sheet, workbook, qvo, datasource, prod, area);

            LOGGER.info("... AFTER SUMMARY .. country  " + datasource);
            LOGGER.info("... AFTER SUMMARY .. countryDatesMapFB.isEmpty() " + countryDatesMapFB.isEmpty());

            if (!countryDatesMapFB.isEmpty()) {
                LOGGER.info("... datesMapFB && countryDatesMapFB && countriesMapFB IS NOT EMPTY  " + datasource);

                //Build FB Table
                if (foodBalanceResults.get(datasource).get(area) != null && !foodBalanceResults.get(datasource).get(area).isEmpty()) {
                    LOGGER.info("----------- FOOD BALANCE TABLE: " + datasource);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, foodBalanceResults.get(datasource), FOOD_BALANCE_TABLE_TITLE, countryDatesMapFB.get(area), area, prod, datasource, foodBalanceElements, qvo);
                } else {
                    rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, FOOD_BALANCE_TABLE_TITLE);
                }

                LOGGER.info("----------- FOOD BALANCE TABLE FINISHED ");


            } else {
                LOGGER.info("... FOOD BALANCE TABLE BUILD EMPTY TABLE   " + datasource);
                rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, FOOD_BALANCE_TABLE_TITLE);
            }

            LOGGER.info("... FOOD BALANCE TABLE DONE   " + datasource);

            if (countryDatesMapITY != null && !countryDatesMapITY.isEmpty()) {
                LOGGER.info("----------- ITY TABLE: countryDatesMapITY " + countryDatesMapITY);
                //Build ITY Table
                if (ityResults.containsKey(datasource) && ityResults.get(datasource).containsKey(area)) {
                    //  if(!ityResults.get(datasource).get(area).isEmpty()){
                    LOGGER.info("----------- ITY TABLE 2: " + datasource);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, ityResults.get(datasource), ITY_TABLE_TITLE, countryDatesMapITY.get(area), area, prod, datasource, ityElements, qvo);
                    // }
                } else {
                    // create empty results map
                    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> emptyITYResults = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>>();
                    LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> emptyAreaResults = new LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>();
                    LinkedHashMap<String, Map<String, Double>> emptyYearMap = new LinkedHashMap<String, Map<String, Double>>();
                    Map<String, Double> emptyValues = new HashMap<String, Double>();

                    LinkedHashMap<String, String> countryYears = qvo.getCountryDates().get(area);

                    for (String yearVal : countryYears.keySet()) {
                        //for(String yearVal:  qvo.getYears().keySet()){
                        for (String element : ityElements.keySet()) {
                            emptyValues.put(element, null);
                        }
                        emptyYearMap.put(yearVal, emptyValues);
                    }
                    emptyAreaResults.put(area, emptyYearMap);
                    emptyITYResults.put(datasource, emptyAreaResults);

                    LOGGER.info("----------- ITY TABLE 3: " + datasource);
                    rowCounter = createDataTable(rowCounter, sheet, workbook, emptyITYResults.get(datasource), ITY_TABLE_TITLE, countryDatesMapFB.get(area), area, prod, datasource, ityElements, qvo);
                    //  rowCounter =  createNoAvailableDataTable(rowCounter, sheet, workbook, ITY_TABLE_TITLE) ;
                }

                LOGGER.info("----------- ITY TABLE FINISHED ");
            } else {
                // create empty results map
                LOGGER.info("----------- ITY  TABLE BUILD EMPTY TABLE!: " + datasource);
                LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> emptyITYResults = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>>();
                LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> emptyAreaResults = new LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>();
                LinkedHashMap<String, Map<String, Double>> emptyYearMap = new LinkedHashMap<String, Map<String, Double>>();
                Map<String, Double> emptyValues = new HashMap<String, Double>();

                LinkedHashMap<String, String> countryYears = qvo.getCountryDates().get(area);

                for (String yearVal : countryYears.keySet()) {
                    // for(String yearVal:  qvo.getYears().keySet()){
                    for (String element : ityElements.keySet()) {
                        emptyValues.put(element, null);
                    }
                    emptyYearMap.put(yearVal, emptyValues);
                }
                emptyAreaResults.put(area, emptyYearMap);
                emptyITYResults.put(datasource, emptyAreaResults);

                LOGGER.info("----------- ITY TABLE 3: " + datasource);
                rowCounter = createDataTable(rowCounter, sheet, workbook, emptyITYResults.get(datasource), ITY_TABLE_TITLE, countryDatesMapFB.get(area), area, prod, datasource, ityElements, qvo);

                // rowCounter =  createNoAvailableDataTable(rowCounter, sheet, workbook, ITY_TABLE_TITLE) ;

            }

            LOGGER.info("... ITY TABLE DONE   " + datasource);

            if (countryDatesMapOther != null && !countryDatesMapOther.isEmpty()) {
                //Build Other Table
                if (otherResults.containsKey(datasource) && otherResults.get(datasource).containsKey(area)) {
                    // if(!otherResults.get(datasource).get(area).isEmpty()){
                    //if(otherResults.get(datasource).get(area) != null && !otherResults.get(datasource).get(area).isEmpty()){
                    LOGGER.info("----------- OTHER TABLE: " + countryDatesMapOther.isEmpty());
                    rowCounter = createDataTable(rowCounter, sheet, workbook, otherResults.get(datasource), OTHER_TABLE_TITLE, countryDatesMapOther.get(area), area, prod, datasource, otherElements, qvo);
                    // }
                } else {
                    rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, OTHER_TABLE_TITLE);
                }

                LOGGER.info("----------- OTHER TABLE FINISHED ");
            } else {
                LOGGER.info("----------- OTHER TABLE BUILD EMPTY TABLE !: " + datasource);
                rowCounter = utils.createNoAvailableDataTable(rowCounter, sheet, workbook, OTHER_TABLE_TITLE);
            }

            LOGGER.info("... OTHER TABLE DONE   " + datasource);

            //Footnotes
            boolean footnoteAdded = AMISSupplyDemandNotes.createSoybeansFootNotes(rowCounter, workbook, sheet, qvo, datasource);

            //Footnotes
            String productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
            String countryLabel = area;

            if (qvo.getElementFootNotesByCountry().get(datasource) != null && qvo.getElementFootNotesByCountry().get(datasource).get(productCode) != null && !qvo.getElementFootNotesByCountry().get(datasource).get(productCode).isEmpty()) {
                if (qvo.getElementFootNotesByCountry().get(datasource).get(productCode).get(countryLabel) != null)
                    AMISSupplyDemandNotes.createFootnotes(rowCounter, sheet, workbook, qvo.getElementFootNotesByCountry().get(datasource).get(productCode).get(countryLabel), footnoteAdded);

            }

            LOGGER.info("**************** createDatasourceWorkbook END .............");

            //Fix the width of the first two columns
            sheet.setColumnWidth((short) 0, (short) 7500);
            sheet.setColumnWidth((short) 1, (short) 5000);

            sheet.getPrintSetup().setLandscape(true);

            //Fit to one page
            HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
            sheet.setAutobreaks(true);
            ps.setFitHeight((short) 1);
            ps.setFitWidth((short) 1);

        }

        return workbook;
    }


    private LinkedHashMap<String, String> getFoodBalanceElements(AMISQueryVO qvo, String product, String datasource) {
        String productCode = "";
        LOGGER.info("qvo get food balance elements = " + qvo.getFoodBalanceElements());

        LinkedHashMap<String, String> foodBalEl = new LinkedHashMap<String, String>();

        if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
        } else if (qvo.getxLabel().equals("PRODUCT")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, product);
        }
        LOGGER.info("product = " + product);
        LOGGER.info("productCode = " + productCode);
        LOGGER.info("datasource = " + datasource);
        if (qvo.getFoodBalanceElements().containsKey(datasource)) {
            LOGGER.info("datasource FOUND = ");
            if (qvo.getFoodBalanceElements().get(datasource).containsKey(productCode)) {
                LOGGER.info("productCode FOUND ");
                foodBalEl = qvo.getFoodBalanceElements().get(datasource).get(productCode);
            }
        }

        LOGGER.info("foodBalEl = " + foodBalEl);


        return foodBalEl;
        // return qvo.getFoodBalanceElements().get(datasource).get(productCode);

    }


    private LinkedHashMap<String, String> getOtherElements(AMISQueryVO qvo, String product, String datasource) {
        String productCode = "";
        LinkedHashMap<String, String> otherEl = new LinkedHashMap<String, String>();

        if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
        } else if (qvo.getxLabel().equals("PRODUCT")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, product);
        }
        LOGGER.info("qvo.getOtherFoodBalanceElements() = " + qvo.getOtherFoodBalanceElements());
        LOGGER.info("productCode = " + productCode);
        LOGGER.info("datasource = " + datasource);
        if (qvo.getOtherFoodBalanceElements().containsKey(datasource)) {
            LOGGER.info("datasource FOUND = ");
            if (qvo.getOtherFoodBalanceElements().get(datasource).containsKey(productCode)) {
                LOGGER.info("productCode FOUND ");
                otherEl = qvo.getOtherFoodBalanceElements().get(datasource).get(productCode);
            }
        }
        return otherEl;
    }


    private int createSummary(int rowCounter, HSSFSheet sheet, HSSFWorkbook workbook, AMISQueryVO qvo, String country, String label, String area) {

        LOGGER.info("createSummary START: " + qvo.getDatabases());

        //Create Date Last Updated
        // BY COUNTRY: region_name = country; product_name = AMISSupplyDemandExcelUtils.convertMapItemLabelsToString(qvo.getItems());
        // BY PRODUCT: region_name = label; product_name = country;
        // BY DATASOURCE: region_name = area; product_name = label;

        String dateLastUpdated = createDateLastUpdated(qvo, country, label, area);

        rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "Data Last Updated on:", dateLastUpdated);

        if (qvo.getxLabel().equals("COUNTRY")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COUNTRY:", country.toUpperCase());
        } else if (qvo.getxLabel().equals("PRODUCT")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COUNTRY:", label.toUpperCase());
        } else if (qvo.getxLabel().equals("DATASOURCE")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COUNTRY:", area.toUpperCase());
        }

        if (qvo.getxLabel().equals("COUNTRY")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COMMODITY:", utils.convertMapItemLabelsToString(qvo.getItems()).toUpperCase());
        } else if (qvo.getxLabel().equals("PRODUCT")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COMMODITY:", country.toUpperCase());
        } else if (qvo.getxLabel().equals("DATASOURCE")) {
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "COMMODITY:", label.toUpperCase());
        }

        //Create Source
        String source = createSource(qvo, country, label, area);

        rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, "SOURCE", source);

        rowCounter = utils.createEmptyRow(rowCounter, sheet, workbook);

        LOGGER.info("createSummary: END");

        return rowCounter;
    }

    private String createDateLastUpdated(AMISQueryVO qvo, String country, String label, String area) {
        String dateLastUpdated = "";
        // BY COUNTRY: region_name = country; product_name = AMISSupplyDemandExcelUtils.convertMapItemLabelsToString(qvo.getItems());
        // BY PRODUCT: region_name = label; product_name = country;
        // BY DATASOURCE: region_name = area; product_name = label;

        if (!qvo.getxLabel().equals("DATASOURCE")) {
            if (qvo.getDatabases().containsKey(AMISConstants.NATIONAL.name())) {
                if (qvo.getNationalDataSources() != null && !qvo.getNationalDataSources().isEmpty()) {
                    if (qvo.getxLabel().equals("COUNTRY")) {
                        if (qvo.getNationalDataSources().get(country) != null && !qvo.getNationalDataSources().get(country).isEmpty()) {
                            String product_name = utils.convertMapItemLabelsToString(qvo.getItems());
                            if (qvo.getNationalDataSources().get(country).get(product_name) != null && !qvo.getNationalDataSources().get(country).get(product_name).isEmpty())
                                dateLastUpdated = qvo.getNationalDataSources().get(country).get(product_name).get(1);
                        }
                    } else if (qvo.getxLabel().equals("PRODUCT")) {
                        if (qvo.getNationalDataSources().get(label) != null && !qvo.getNationalDataSources().get(label).isEmpty()) {
                            if (qvo.getNationalDataSources().get(label).get(country) != null && !qvo.getNationalDataSources().get(label).get(country).isEmpty())
                                dateLastUpdated = qvo.getNationalDataSources().get(label).get(country).get(1);
                        }

                    }
                }
            } else {
                if (qvo.getAmisLastModifiedDate() != null)
                    dateLastUpdated = qvo.getAmisLastModifiedDate();
            }
        } else {
            if (country.equals("NATIONAL")) {
                if (qvo.getNationalDataSources().get(area) != null && !qvo.getNationalDataSources().get(area).isEmpty()) {
                    if (qvo.getNationalDataSources().get(area).get(label) != null && !qvo.getNationalDataSources().get(area).get(label).isEmpty())
                        dateLastUpdated = qvo.getNationalDataSources().get(area).get(label).get(1);
                }
            } else {
                if (qvo.getAmisLastModifiedDate() != null)
                    dateLastUpdated = qvo.getAmisLastModifiedDate();
            }
        }

        if (dateLastUpdated.length() > 0)
            dateLastUpdated += " (" + DATA_SUBJECT_TO_REVISIONS + ")";

        return dateLastUpdated;
    }

    /**
     * private String createDateLastUpdatedOriginal(AMISQueryVO qvo, String country, String label, String area){
     * String dateLastUpdated = "";
     * <p/>
     * if(!qvo.getxLabel().equals("DATASOURCE")){
     * if(qvo.getDatabases().containsKey(AMISConstants.NATIONAL.name())){
     * if( qvo.getNationalDataSources()!=null && !qvo.getNationalDataSources().isEmpty()){
     * if(qvo.getxLabel().equals("COUNTRY")){
     * if(qvo.getNationalDataSources().get(country)!=null && !qvo.getNationalDataSources().get(country).isEmpty())
     * dateLastUpdated = qvo.getNationalDataSources().get(country).get(1);
     * } else if(qvo.getxLabel().equals("PRODUCT")){
     * if(qvo.getNationalDataSources().get(label)!=null && !qvo.getNationalDataSources().get(label).isEmpty())
     * dateLastUpdated = qvo.getNationalDataSources().get(label).get(1);
     * }
     * }
     * }
     * else {
     * if(qvo.getAmisLastModifiedDate()!=null)
     * dateLastUpdated = qvo.getAmisLastModifiedDate();
     * }
     * } else {
     * if(country.equals("NATIONAL")){
     * if(qvo.getNationalDataSources().get(area)!=null && !qvo.getNationalDataSources().get(area).isEmpty())
     * dateLastUpdated = qvo.getNationalDataSources().get(area).get(1);
     * }
     * else {
     * if(qvo.getAmisLastModifiedDate()!=null)
     * dateLastUpdated = qvo.getAmisLastModifiedDate();
     * }
     * }
     * <p/>
     * if(dateLastUpdated.length() > 0)
     * dateLastUpdated += " ("+DATA_SUBJECT_TO_REVISIONS+")";
     * <p/>
     * return dateLastUpdated;
     * }
     **/


    // BY COUNTRY: region_name = country; product_name = AMISSupplyDemandExcelUtils.convertMapItemLabelsToString(qvo.getItems());
    // BY PRODUCT: region_name = label; product_name = country;
    // BY DATASOURCE: region_name = area; product_name = label;
    private String createSource(AMISQueryVO qvo, String country, String label, String area) {
        String source = "";

        if (!qvo.getxLabel().equals("DATASOURCE")) {
            if (qvo.getDatabases().containsKey(AMISConstants.NATIONAL.name())) {
                if (qvo.getNationalDataSources() != null && !qvo.getNationalDataSources().isEmpty()) {
                    if (qvo.getxLabel().equals("COUNTRY")) {
                        if (qvo.getNationalDataSources().get(country) != null && !qvo.getNationalDataSources().get(country).isEmpty()) {
                            String product_name = utils.convertMapItemLabelsToString(qvo.getItems());
                            if (qvo.getNationalDataSources().get(country).get(product_name) != null && !qvo.getNationalDataSources().get(country).get(product_name).isEmpty())
                                source = qvo.getNationalDataSources().get(country).get(product_name).get(0).toUpperCase();
                        }
                    } else if (qvo.getxLabel().equals("PRODUCT")) {
                        if (qvo.getNationalDataSources().get(label) != null && !qvo.getNationalDataSources().get(label).isEmpty()) {
                            if (qvo.getNationalDataSources().get(label).get(country) != null && !qvo.getNationalDataSources().get(label).get(country).isEmpty())
                                source = qvo.getNationalDataSources().get(label).get(country).get(0).toUpperCase();
                        }
                    } else if (qvo.getxLabel().equals("DATASOURCE")) {
                        if (qvo.getNationalDataSources().get(area) != null && !qvo.getNationalDataSources().get(area).isEmpty()) {
                            if (qvo.getNationalDataSources().get(area).get(label) != null && !qvo.getNationalDataSources().get(area).get(label).isEmpty())
                                source = qvo.getNationalDataSources().get(area).get(label).get(0).toUpperCase();
                        }
                    }
                }
            } else {
                if (qvo.getDatabases().size() == 1) {
                    for (String db : qvo.getDatabases().keySet()) {
                        source = qvo.getDatabases().get(db).toUpperCase();
                    }
                }
            }
        } else {
            LOGGER.info(qvo.getxLabel() + " | country = " + country + " | " + area);
            if (country.equals("NATIONAL")) {
                if (qvo.getNationalDataSources().get(area) != null && !qvo.getNationalDataSources().get(area).isEmpty()) {
                    if (qvo.getNationalDataSources().get(area).get(label) != null && !qvo.getNationalDataSources().get(area).get(label).isEmpty())
                        source = qvo.getNationalDataSources().get(area).get(label).get(0).toUpperCase();
                }
            } else {
                source = qvo.getDatabases().get(country).toUpperCase();
            }
        }

        LOGGER.info("SOURCE = " + source);

        if (source.length() > 0)
            source += " (" + DISTRIBUTED_BY_AMIS_STATISTICS + ")";

        return source;
    }

    /**
     * private String createSourceOriginal(AMISQueryVO qvo, String country, String label, String area){
     * String source = "";
     * <p/>
     * if(!qvo.getxLabel().equals("DATASOURCE")){
     * if(qvo.getDatabases().containsKey(AMISConstants.NATIONAL.name())){
     * if( qvo.getNationalDataSources()!=null && !qvo.getNationalDataSources().isEmpty())       {
     * if(qvo.getxLabel().equals("COUNTRY")){
     * if(qvo.getNationalDataSources().get(country)!=null && !qvo.getNationalDataSources().get(country).isEmpty())    {
     * source = qvo.getNationalDataSources().get(country).get(0).toUpperCase();
     * }
     * } else if(qvo.getxLabel().equals("PRODUCT")){
     * if(qvo.getNationalDataSources().get(label)!=null && !qvo.getNationalDataSources().get(label).isEmpty())    {
     * source = qvo.getNationalDataSources().get(label).get(0).toUpperCase();
     * }
     * }  else if(qvo.getxLabel().equals("DATASOURCE")){
     * if(qvo.getNationalDataSources().get(area)!=null && !qvo.getNationalDataSources().get(area).isEmpty())    {
     * source = qvo.getNationalDataSources().get(area).get(0).toUpperCase();
     * }
     * }
     * }
     * } else {
     * if(qvo.getDatabases().size() == 1){
     * for(String db: qvo.getDatabases().keySet()){
     * source = qvo.getDatabases().get(db).toUpperCase();
     * }
     * }
     * }
     * } else {
     * LOGGER.info(qvo.getxLabel()+ " | country = "+country + " | "+area);
     * if(country.equals("NATIONAL")){
     * if(qvo.getNationalDataSources().get(area)!=null && !qvo.getNationalDataSources().get(area).isEmpty()) {
     * source = qvo.getNationalDataSources().get(area).get(0).toUpperCase();
     * }
     * }
     * else {
     * source = qvo.getDatabases().get(country).toUpperCase();
     * }
     * }
     * <p/>
     * LOGGER.info("SOURCE = "+source);
     * <p/>
     * if(source.length() > 0)
     * source += " ("+DISTRIBUTED_BY_AMIS_STATISTICS+")";
     * <p/>
     * return source;
     * }
     **/

    private int createDataTable(int rowCounter, HSSFSheet sheet, HSSFWorkbook workbook, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> results, String tableTitle, Map<String, String> datesMap, String country, String label, String datasource, LinkedHashMap<String, String> elementsUnitsMap, AMISQueryVO qvo) {

        //empty row
        Row row = sheet.createRow(rowCounter);
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("");

        LOGGER.info("--- createDataTable elements Map = " + elementsUnitsMap);
        LOGGER.info("--- createDataTable dates Map - " + datesMap);

        int finalRow = createDataRows(rowCounter++, sheet, workbook, country, label, datasource, datesMap, elementsUnitsMap, results, tableTitle, qvo);

        LOGGER.info("--- createDataTable finalRow = " + finalRow);

        return finalRow;
    }

    private int createDataRows(int rowCounter, HSSFSheet sheet, HSSFWorkbook workbook, String country, String label, String datasource, Map<String, String> datesMap, LinkedHashMap<String, String> elementsMap, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> resultsMap, String title, AMISQueryVO qvo) {

        //Check if Population Values Exist
        boolean populationValuesExist = false;
        if (qvo.getPopulationValues() != null && !qvo.getPopulationValues().isEmpty()) {
            LOGGER.info("qvo.getPopulationValues() " + qvo.getPopulationValues());
            if (qvo.getPopulationValues().containsKey(datasource)) {
                if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                    if (qvo.getPopulationValues().get(datasource).containsKey(country) && !qvo.getPopulationValues().get(datasource).get(country).isEmpty())
                        populationValuesExist = true;
                } else if (qvo.getxLabel().equals("PRODUCT")) {
                    if (qvo.getPopulationValues().get(datasource).containsKey(label) && !qvo.getPopulationValues().get(datasource).get(label).isEmpty())
                        populationValuesExist = true;
                }
            }
        }

        LOGGER.info("populationValuesExist = " + populationValuesExist);
        LOGGER.info("country = " + country);
        LOGGER.info("datasource = " + datasource);
        LOGGER.info("label = " + label);

        if (!title.equals(ITY_TABLE_TITLE)) {
            //Empty Row
            rowCounter = utils.createEmptyRow(rowCounter, sheet, workbook);

            //Title Row
            rowCounter = utils.createHeadingRow(rowCounter, sheet, workbook, title, null);

            //Empty Row
            rowCounter = utils.createEmptyRow(rowCounter, sheet, workbook);

            rowCounter = createYearRow(qvo, rowCounter, sheet, workbook, country, label, datesMap, title, datasource);
        } else {
        /*  sheet.addMergedRegion(new CellRangeAddress(rowCounter, (short) 0, rowCounter+1, (short) 0));
          sheet.addMergedRegion(new CellRangeAddress(rowCounter, (short) 1, rowCounter+1, (short) 1));*/

            sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter + 1, (short) 0, (short) 0));
            sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter + 1, (short) 1, (short) 1));


            rowCounter = createITYRow(qvo, rowCounter, sheet, workbook, country, label, datasource);
            rowCounter = utils.createEmptyRow(rowCounter, sheet, workbook);
        }

        // LOGGER.info("buildCountryTable: createYearRow ");

        LinkedHashMap<String, Map<String, Double>> year = resultsMap.get(country);


        // Data Rows
        int t = rowCounter++;

        // HSSFCellStyle cellStyle = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();

        //Other Uses Check
        boolean otherUsesPresent = false;
        if (elementsMap.containsKey("15")) {
            otherUsesPresent = true;
        }

        LOGGER.info("DataRows START  &&&&&&&&&&&&&&&&&&");

        for (String elementCode : elementsMap.keySet()) {

            Row row = sheet.createRow(t);

            String[] elementNameUnit = elementsMap.get(elementCode).split("-");
            String elementName = elementNameUnit[0];
            String elementUnit = elementNameUnit[1];

            HSSFCellStyle elementCellStyle = utils.getGreyCellStyle();

            if (FOOD_BALANCE_ELEMENTS_HIGHLIGHTED.contains(elementCode)) {
                elementName = elementName.toUpperCase();
                elementCellStyle.cloneStyleFrom(utils.getBoldTextCellStyle(workbook, elementCellStyle));
            }
            //Apply first indentation
            else if (FOOD_BALANCE_ELEMENTS_INDENTED.contains(elementCode)) {
                elementCellStyle = utils.getGreyItalicCellStyle();
                elementName = "     " + elementName;
            }
            //Append a second indentation
            else if (FOOD_BALANCE_ELEMENTS_MORE_INDENTED.contains(elementCode) && otherUsesPresent) {
                elementCellStyle.setFont(utils.getItalicisedSmallFont());
                elementName = "          " + elementName;
            }
            else {
                elementCellStyle = utils.getGreyNormalCellStyle();
            }


            LOGGER.info("DataRows row index = " + t + " | elementCode: " + elementCode + " | elementName: '" + elementName + "' | elementUnit: " + elementUnit);
            //Element
            Cell cell = row.createCell((short) 0);
            cell.setCellStyle(elementCellStyle);
            cell.setCellValue(elementName);


            //Unit
            cell = row.createCell((short) 1);

            cell.setCellStyle(utils.getBordersStyle(workbook, null));
            cell.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellValue(elementUnit);
            int j = 2;
            for (String dateCode : datesMap.keySet()) {
                // String date = datesMap.get(dateCode);
                String date = dateCode;
                cell = row.createCell((short) j);

                if (year != null) {
                    if (year.get(date) != null) {
                        // LOGGER.info(date+" in year MAP %% NOT NULL %% ");
                        if (year.get(date).get(elementCode) != null) {

                            HSSFCellStyle elementValueCellStyle = utils.getBasicWithRightAlWithBorders();

                            cell.setCellStyle(elementValueCellStyle);

                          /*  // Total Utilization (35) - Supply (19)
                            if (elementCode.equals("35") || elementCode.equals("19")) {
                                cell.setCellStyle(utils.getBigBoldTextCellStyle(workbook, elementValueCellStyle));
                            } else {
*//*
                                cell.setCellStyle(elementValueCellStyle);
*//*

                                if (FOOD_BALANCE_ELEMENTS_INDENTED.contains(elementCode)) {
                                    cell.getCellStyle().setFont(utils.getItalicFont());
                                }

                                //if other uses elements is present then level one elements in italics
                                // and level two elements in italics and small text
                                if (otherUsesPresent) {
                                    if (FOOD_BALANCE_ELEMENTS_MORE_INDENTED.contains(elementCode)) {
                                        cell.getCellStyle().setFont(utils.getItalicisedSmallFont());
                                    }
                                } else {
                                    if (FOOD_BALANCE_ELEMENTS_MORE_INDENTED.contains(elementCode)) {
                                        cell.getCellStyle().setFont(utils.getItalicFont());
                                    }
                                }
                            }*/


                            // Total Utilization (35) - Supply (19)
                            if (elementCode.equals("35") || elementCode.equals("19")) {
                                cell.setCellStyle(utils.getBigBoldCellStyle());
                            } else {

                                if (FOOD_BALANCE_ELEMENTS_INDENTED.contains(elementCode)) {
                                    cell.setCellStyle(utils.getItalicCellStyle());
                                }
                                else if (FOOD_BALANCE_ELEMENTS_MORE_INDENTED.contains(elementCode)) {
                                    //if other uses elements is present then level one elements in italics
                                    // and level two elements in italics and small text
                                    if (otherUsesPresent) {
                                        cell.setCellStyle(utils.getSmallItalicCellStyle());
                                    } else {
                                        cell.setCellStyle(utils.getItalicCellStyle());
                                    }
                                } else {
                                    cell.setCellStyle(utils.getNormalCellStyle());
                                }

                            }


                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(year.get(date).get(elementCode));

                            //Population
                            if (elementCode.equals("1"))
                                cell.getCellStyle().setDataFormat(df.getFormat("0")); //Round value
                            else
                                cell.getCellStyle().setDataFormat(df.getFormat("0.00")); //set to 1 decimal place

                        } else {
                            cell.setCellStyle(utils.getRightAlignmentWithBordersStyle());

                            //Population
                            if (populationValuesExist && elementCode.equals("1")) {
                                if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                                    if (qvo.getPopulationValues().get(datasource).get(country).get(elementCode).containsKey(date)) {
                                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                        if (qvo.getPopulationValues().get(datasource).get(country).get(elementCode).get(date) == null) {
                                            cell.setCellValue("");
                                        } else {
                                            cell.setCellValue(qvo.getPopulationValues().get(datasource).get(country).get(elementCode).get(date));
                                        }
                                        cell.getCellStyle().setDataFormat(df.getFormat("0")); //Round value
                                    } else {
                                        cell.setCellValue("");
                                    }
                                } else if (qvo.getxLabel().equals("PRODUCT")) {
                                    if (qvo.getPopulationValues().get(datasource).get(label).get(elementCode).containsKey(date)) {
                                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                        if (qvo.getPopulationValues().get(datasource).get(label).get(elementCode).get(date) == null) {
                                            cell.setCellValue("");
                                        } else {
                                            cell.setCellValue(qvo.getPopulationValues().get(datasource).get(label).get(elementCode).get(date));
                                        }
                                        cell.getCellStyle().setDataFormat(df.getFormat("0")); //Round value
                                    } else {
                                        cell.setCellValue("");
                                    }
                                }
                            } else {
                                cell.setCellValue("");
                            }
                        }
                    } else {
                        // LOGGER.info(date+" NOT IN  year MAP %%  NULL %% ");
                        CellStyle cellStyle = cell.getCellStyle();
                        cell.setCellStyle(cellStyle);
                        utils.getBasicWithBorders();
                        cell.setCellValue("");
                    }
                } else {
                    cell.setCellStyle(utils.getBasicWithBorders());
                    cell.setCellValue("");
                }
                j++;
            }

            t++;
        }

        // LOGGER.info("buildCountryTable: BEFORE title.equals(FOOD_BALANCE_TABLE_TITLE) title = "+title);
        if (title != null && title.equals(FOOD_BALANCE_TABLE_TITLE)) {
            t = createUnbalancedRow(t, sheet, workbook, datesMap, year, elementsMap);

            //Unit merge
           /* sheet.addMergedRegion(new CellRangeAddress(
                    9, //rowFrom(0-based)
                    (short)1, //colFrom  (0-based)
                    (t-2), //rowTo (0-based)
                    (short) 1 //colTo  (0-based)
            ));*/

            sheet.addMergedRegion(new CellRangeAddress(
                    9, //rowFrom(0-based)
                    (t - 2), //rowTo (0-based)
                    (short) 1, //colFrom  (0-based)
                    (short) 1 //colTo  (0-based)
            ));

            //Marketing Year Note
          /*  sheet.addMergedRegion(new CellRangeAddress(
                    t, //rowFrom(0-based)
                    (short)0, //colFrom  (0-based)
                    t, //rowTo (0-based)
                    (short) 14 //colTo  (0-based)
            ));*/

            sheet.addMergedRegion(new CellRangeAddress(
                    t, //rowFrom(0-based)
                    t, //rowTo (0-based)
                    (short) 0, //colFrom  (0-based)
                    (short) 14 //colTo  (0-based)
            ));


            //National Marketing Year Note
            String productCode = "";
            String countryLabel = "";

            if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
                countryLabel = country;
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, country);
                countryLabel = label;
            }

            if( qvo.getCountriesNationalMarketingYear().get(datasource)!=null && qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode)!=null  &&!qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).isEmpty())   {
                if(qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel)!=null)
                    t = AMISSupplyDemandNotes.createMarketingTradeNoteRow(t, sheet, workbook, AMISSupplyDemandNotes.buildNote(qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel).get(0), qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel).get(1), qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel).get(2), qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel).get(3), "NMY"));

            }
            //FAO-CBS Rice Note
            if (qvo.getxLabel().equals("DATASOURCE")) {
                if (AMISSupplyDemandUtils.itemsContainRice(qvo) && datasource.equals(AMISConstants.CBS.name())) {
                    sheet.addMergedRegion(new CellRangeAddress(t, (short) 0, t, (short) 15));
                    t = AMISSupplyDemandNotes.createInformationRow(t, sheet, workbook, CBS_RICE_NOTE, "", false, 0);
                }
            } else if (qvo.getxLabel().equals("COUNTRY")) {
                if (AMISSupplyDemandUtils.itemsContainRice(qvo) && qvo.getDatabases().containsKey(AMISConstants.CBS.name())) {
                    sheet.addMergedRegion(new CellRangeAddress(t, (short) 0, t, (short) 15));
                    t = AMISSupplyDemandNotes.createInformationRow(t, sheet, workbook, CBS_RICE_NOTE, "", false, 0);
                }
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                LOGGER.info("Rice has been selected " + AMISSupplyDemandUtils.itemsContainRice(qvo) + " | datasource = " + datasource);
                if (country.contains("Rice") && qvo.getDatabases().containsKey(AMISConstants.CBS.name())) {
                    sheet.addMergedRegion(new CellRangeAddress(t, (short) 0, t, (short) 15));
                    t = AMISSupplyDemandNotes.createInformationRow(t, sheet, workbook, CBS_RICE_NOTE, "", false, 0);
                }
            }

            t = utils.createEmptyRow(t, sheet, workbook);
        }


        if (title != null && title.equals(ITY_TABLE_TITLE)) {
            System.out.println(" ITY_TABLE_TITLE ROW INDEX: " + t);
            //Unit merge
          /*   sheet.addMergedRegion(new CellRangeAddress(
                   t-2, //rowFrom(0-based)
                     (short)1, //colFrom  (0-based)
                     (t-1), //rowTo (0-based)
                     (short) 1 //colTo  (0-based)
             ));*/

            sheet.addMergedRegion(new CellRangeAddress(
                    t - 2, //rowFrom(0-based)
                    (t - 1), //rowTo (0-based)
                    (short) 1, //colFrom  (0-based)
                    (short) 1 //colTo  (0-based)
            ));


            //International Trade Year Note
          /*  sheet.addMergedRegion(new CellRangeAddress(
                    t, //rowFrom(0-based)
                    (short)0, //colFrom  (0-based)
                    t, //rowTo (0-based)
                    (short) 14 //colTo  (0-based)
            ));*/

            sheet.addMergedRegion(new CellRangeAddress(
                    t, //rowFrom(0-based)
                    t, //rowTo (0-based)
                    (short) 0, //colFrom  (0-based)
                    (short) 14 //colTo  (0-based)
            ));

            //International Trade Year Note
            String productCode = "";
            String countryLabel = "";

            if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
                countryLabel = country;
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, country);
                countryLabel = label;
            }

            if (qvo.getCountriesInternationalTradeYear().get(datasource) != null && qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode) != null && !qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).isEmpty()) {
                if (qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel) != null)
                    t = AMISSupplyDemandNotes.createMarketingTradeNoteRow(t, sheet, workbook, AMISSupplyDemandNotes.buildNote(qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel).get(0), qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel).get(1), qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel).get(2), "ITY"));

            }

        }

        return t;
    }

    private int createITYRow(AMISQueryVO qvo, int rowCounter, Sheet sheet, HSSFWorkbook workbook, String country, String label, String datasource) {
        LOGGER.info("CREATE ITY YEAR START ");

        Row row = sheet.createRow(rowCounter++);

        Cell cell = row.createCell((short) 0);
        cell.getCellStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);

        String header = INTERNATIONAL_TRADE_YEAR + " (ITY):";
        cell.setCellValue(header);

        cell = row.createCell((short) 1);
        cell.setCellStyle(utils.getBoldTextCellStyleWithAlignment(workbook, null));

        LOGGER.info("qvo.getCountriesInternationalTradeYear() " + qvo.getCountriesInternationalTradeYear());

        String productCode = "";
        String countryLabel = "";
        String value = "";

        if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
            countryLabel = country;
        } else if (qvo.getxLabel().equals("PRODUCT")) {
            productCode = AMISSupplyDemandUtils.getProductCode(qvo, country);
            countryLabel = label;
        }
        LOGGER.info("datasource " + datasource);
        LOGGER.info("productCode " + productCode);
        LOGGER.info("countryLabel " + countryLabel);


        if (qvo.getCountriesInternationalTradeYear().get(datasource) != null && qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode) != null && !qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).isEmpty()) {
            if (qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel) != null)
                value = qvo.getCountriesInternationalTradeYear().get(datasource).get(productCode).get(countryLabel).get(0);
        }

        cell.setCellValue(value);

        return rowCounter;
    }

    private int createUnbalancedRow(int rowCounter, Sheet sheet, HSSFWorkbook workbook, Map<String, String> datesMap, LinkedHashMap<String, Map<String, Double>> year, LinkedHashMap<String, String> elementsMap) {

        LOGGER.info("createUnbalancedRow: START .....");
        //LOGGER.info("buildUnbalancedRow: START ..... year "+year);
        // LOGGER.info("buildUnbalancedRow: START ..... elementsMap.get(35) "+elementsMap.get("35"));
        //LOGGER.info("buildUnbalancedRow: START ..... elementsMap.get(19) "+elementsMap.get("19"));


        // Total Utilization (35) - Supply (19)
        String[] totUtilNameUnit = elementsMap.get("35").split("-");
        String totalUtilName = totUtilNameUnit[0];
        String totalUtilUnit = totUtilNameUnit[1];


        String[] supplyNameUnit = elementsMap.get("19").split("-");
        String supplyName = supplyNameUnit[0];


        //Unbalanced Row
        Row unbalancedRow = sheet.createRow(rowCounter++);
        Cell cell = unbalancedRow.createCell((short) 0);


        HSSFCellStyle elementCellStyle = utils.getGreyCellStyle();
        cell.setCellStyle(utils.getBoldTextCellStyle(workbook, elementCellStyle));
        cell.setCellValue("UNBALANCED");


        //Unit
        cell = unbalancedRow.createCell((short) 1);
        cell.setCellStyle(utils.getBordersStyle(workbook, null));
        cell.setCellValue(totalUtilUnit);

        int m = 2;
        //LOGGER.info("buildUnbalancedRow: year ... "+year);


        for (String dateCode : datesMap.keySet()) {
            //String date = datesMap.get(dateCode);
            String date = dateCode;
            cell = unbalancedRow.createCell((short) m);
            HSSFCellStyle elementValueCellStyle = utils.getRightAlignmentWithBordersStyle();
            cell.setCellStyle(utils.getBigBoldTextCellStyle(workbook, elementValueCellStyle));

            // Total Utilization (35) - Supply (19)
            if (year.get(date) != null) {
                Double total_util = year.get(date).get("35");
                Double supply = year.get(date).get("19");

                // LOGGER.info("buildUnbalancedRow: START ... 5ii total_util = "+total_util + " | supply = "+supply);

                if (total_util != null && supply != null) {
                    //LOGGER.info("buildUnbalancedRow: START ... 5iii total_util = "+total_util + " | supply = "+supply);

                    cell.setCellValue(Math.round(total_util - supply));
                } else {
                    cell.setCellValue("");
                }
            } else {
                //LOGGER.info("buildUnbalancedRow: START ... year.get("+date+") is NULL");

                cell.setCellValue("");
            }
            m++;
        }

        //Explanation
        Row unbalancedExplanationRow = sheet.createRow(rowCounter++);
        cell = unbalancedExplanationRow.createCell((short) 0);
        cell.setCellStyle(utils.getSmallTextCellStyle(null, true));
        cell.setCellValue(" UNBALANCED = (" + (totalUtilName.trim()).toUpperCase() + ") - (" + (supplyName.trim()).toUpperCase() + ")");

        LOGGER.info("createUnbalancedRow: END .....");

        return rowCounter;
    }


    private int createYearRow(AMISQueryVO qvo, int rowCounter, Sheet sheet, HSSFWorkbook workbook, String country, String label, Map<String, String> datesMap, String title, String datasource) {
        Row row = sheet.createRow(rowCounter++);

        Cell cell = row.createCell((short) 0);
        cell.getCellStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);


        if (title.equals(FOOD_BALANCE_TABLE_TITLE)) {
            String header = NATIONAL_MARKETING_YEAR + " (NMY):";

            cell.setCellValue(header);
        } else {
            cell.setCellValue("");
        }

        cell = row.createCell((short) 1);
        cell.setCellStyle(utils.getBoldTextCellStyle(workbook, null));
        cell.getCellStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);


        if (title.equals(FOOD_BALANCE_TABLE_TITLE)) {
            String value = "";
            //National Marketing Year Note
            String productCode = "";
            String countryLabel = "";

            if (qvo.getxLabel().equals("COUNTRY") || qvo.getxLabel().equals("DATASOURCE")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, "");
                countryLabel = country;
            } else if (qvo.getxLabel().equals("PRODUCT")) {
                productCode = AMISSupplyDemandUtils.getProductCode(qvo, country);
                countryLabel = label;
            }

          /*  if( qvo.getCountriesNationalMarketingYear().get(datasource)!=null && qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode)!=null  &&!qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).isEmpty())   {
                if(qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel)!=null)
                    value = qvo.getCountriesNationalMarketingYear().get(datasource).get(productCode).get(countryLabel).get(0);

            }
*/

            cell.setCellValue(value);
        } else {
            cell.setCellValue("");
        }

        int i = 2;
        int j = 0;

        for (String dateCode : datesMap.keySet()) {
            // String date = datesMap.get(dateCode);
            String date = dateCode;

            cell = row.createCell((short) i);
            cell.setCellStyle(utils.getBlueCellStyle());

            if (j == datesMap.size() - 1) {
                date += " (forecast)";
            }

            cell.setCellValue(date);

            i++;
            j++;
        }

        return rowCounter;
    }


}