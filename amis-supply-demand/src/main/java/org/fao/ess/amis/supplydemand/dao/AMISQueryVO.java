package org.fao.ess.amis.supplydemand.dao;

import java.util.*;


public class AMISQueryVO {

    private String database;

    /** CHART, MAP **/
    private String output;

    /** BAR, LINE, PIE */
    private String typeOfOutput;
    private List<String> codeList;
    private boolean elementsWithoutUnit = false;
    private boolean elementsUnitsWithoutBrackets = false;
    private boolean flags = false;
    private boolean totalFlag = false;

    private String countryName;
    private String countryCode;
    private String commodityName;
    private String commodityCode;

    private String elementName;
    private String elementCode;
    private String unit;
    private String year;
    private String month;
    private String value;
    private String flag;

    /* PARAMETERS used to construct a title **/
    private String title;
    private String subTitle;
    private String aggregationLabel;
    private String timerangeLabel;

    private String source;

    private String tablename;

    /* Store additional information, such as basic metadata */
    private String description;

    //Value_Type to distinguish ELEMENTS FROM SUBELEMENTS
    private ArrayList<String> valueTypeList = new ArrayList<String>();

    private String valueType;

    private String xLabel;

    private String yLabel;

    private String width;

    private String height;

    private String measurementUnit;

    private String firstSelectField;

    private String selectTypeChartOrTable;
    private List<String> selects = new ArrayList<String>();

    private List<String> froms = new ArrayList<String>();

    private List<String> orderBys = new ArrayList<String>();

    private List<String> groupBys = new ArrayList<String>();

    private Map<String, String> aggregations = new HashMap<String, String>();

    private String aggregationType = "AVG";

    private Integer limit;

    // variables used for the nested queries
    private Integer nestedLimit;

    private String nestedLimitField;


    private String langCode;

    private String sortingOrder = "DESC";

    private String groupCode;

    private String groupLabel;


    /** QUERY PARAMETERS **/

    private Map<String, String> domains = new HashMap<String, String>();

    private Map<String, String> groups = new HashMap<String, String>();

    private Map<String, String> areas = new HashMap<String, String>();

    private Map<String, String> years = new HashMap<String, String>();

    private Map<String, String> items = new HashMap<String, String>();

    private Map<String, String> elements = new HashMap<String, String>();

    private Map<String, String> elementsList = new HashMap<String, String>();

    private Map<String, String> databases = new HashMap<String, String>();

    /** Coding System **/

    private Boolean getTotalAndList = false;

    private Map<String, String> aggregationsFilter = new HashMap<String, String>();

    /** **/

    private String sql;



    // TODO: Do we need BOTH??
    private Boolean isCountryLevel = true;

    private Boolean isRegionLevel = false;

    private Boolean addFlag = true;

    private Boolean addLabels = true;

    /**
     * Date range parameters
     */
    private Boolean runMaxDateQuery = false;
    private Boolean runTimeIntervalQuery = false;
    private String maxDateLimit;
    private String lowerDateLimit;
    private Integer timeSpan;
    private String timeSpanType;
    private String timeIntervalQuery;

    /*
     * Calculation Query parameters
     */
    private Boolean runCalculationQuery = false;


    /** PIVOT TABLE **/

    // variables used in the stored procedure
    private String nestedby; // this variable is the @pivotdimension1

    private String xAxis; // this variable is the @pivotdimension2

    private String y1Axis; // this variable the first variable listed in the @extracolumns parameter

    private String y2Axis;// this variable the second variable listed in the @extracolumns parameter

    // extra table columns
    // show Codes
    private Boolean showCodes = false;

    // show MeasurementUnit // TODO: change in showUnits
    private Boolean showMeasurementUnit = true;


    // show Null
    private Boolean showNull = true;

    // show Zero values
    private Boolean showZeroes = true;

    // show Official flags
    private Boolean showOfficialflags = false;

    // thousand Separator
    private String thousandSeparator = "";

    //decimal separator
    private String decimalseparator = ".";

    // number of decimals
    private String showDec = "2";


    /**
     * Dataset variables
     */
    private String datasetTableName;
    private Map<String, String> datasetCodes = new HashMap<String, String>();
    private String selectedDataset;
    private List<String> selectedDatasets;
    private String trainingDataset;
    //Tt's the file that contains the data of the country or of Cbs
    private String selectedDatasetFile;


    // FIXED QVO (TODO: should be a list of not applicable filters [filtersType] i.e. not apply the filter for the items
    // 			   instead of a Boolean)

    // TODO: change name of variable
    // applyOnlyYearFilter - isFixedAvoidingYears (APPLY_ONLY_YEAR_FILTER)
    private Boolean applyOnlyYearFilter = false; // everything is fixed (just not the years)

    // applyAllFiltersExceptYears - isFixedYears (APPLY_ALL_FILTERS_EXCEPT_YEARS)
    private Boolean applyAllFiltersExceptYears = false; // it's fixed just the years, other filters are applicable

    // applyAllFiltersExceptAggregrationType - isFixedAggregationType (APPLY_ALL_FILTERS_EXCEPT_AGGREGATION_TYPE)
    private Boolean applyAllFiltersExceptAggregrationType = false; // it's fixed just the aggregationType, other filters are appliable

    // not apply any of the filters
    private Boolean notApplyFilters = false;

    // applyAllFiltersExceptAreas - (APPLY_ALL_FILTERS_EXCEPT_AREAS)
    private Boolean applyAllFiltersExceptAreas = false; // it's fixed just the years, other filters are applicable


    // Tables variables
    private Boolean showColumnHeaders = true;
    private List<String> tableHeaders = new ArrayList<String>();

    //Result for Cbs Tool
    private List<Object[]> cbsResult;


    //National Data Source parameters
    private Map<String, String> nationalDataSourceFilters = new HashMap<String, String>();

    /** Export **/
    public String exportTitle;

    //This variable is used to create the charts for the AMIS home page
    //if chartForAmisHomePage is true ... use the AMIS DATA table because in the Home Page
    //the data are only for Cbs and Psd
    private boolean chartForAmisHomePage;


    //Food Balance
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> foodBalanceElements;
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> otherFoodBalanceElements;
    private LinkedHashMap<String, String> ityElements;
    private LinkedHashMap<String, String> balanceYears;

    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> countriesNationalMarketingYear;
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> countriesInternationalTradeYear;
    // private LinkedHashMap<String,  List<String>> nationalDataSources;
    private LinkedHashMap<String,  LinkedHashMap<String, List<String>>> nationalDataSources;

    private  LinkedHashMap<String, LinkedHashMap<String, String>> countryDatesMap;


    private String amisLastModifiedDate;
    //Map<datasource, Map<product, Map<country, Map<element, footnote>>>
    private LinkedHashMap<String, LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>>> elementFootnotes;
    private LinkedHashMap<String, LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Double>>>> populationValues;

    public String getGroupCode() {
        return groupCode;
    }

    public Boolean getRunTimeIntervalQuery() {
        return runTimeIntervalQuery;
    }

    public void setRunTimeIntervalQuery(Boolean runTimeIntervalQuery) {
        this.runTimeIntervalQuery = runTimeIntervalQuery;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public Map<String, String> getDomains() {
        return domains;
    }

    public void setDomains(Map<String, String> domains) {
        this.domains = domains;
    }

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public Map<String, String> getAreas() {
        return areas;
    }

    public void setAreas(Map<String, String> areas) {
        this.areas = areas;
    }

    public Map<String, String> getYears() {
        return years;
    }

    public void setYears(Map<String, String> years) {
        this.years = years;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public Map<String, String> getElements() {
        return elements;
    }

    public void setElements(Map<String, String> elements) {
        this.elements = elements;
    }


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<String> getSelectedDatasets() {
        return selectedDatasets;
    }

    public void setSelectedDatasets(List<String> selectedDatasets) {
        this.selectedDatasets = selectedDatasets;
    }

    public String getTypeOfOutput() {
        return typeOfOutput;
    }

    public void setTypeOfOutput(String typeOfOutput) {
        this.typeOfOutput = typeOfOutput;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public String getExportTitle() {
        return exportTitle;
    }

    public void setExportTitle(String exportTitle) {
        this.exportTitle = exportTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTimerangeLabel() {
        return timerangeLabel;
    }

    public void setTimerangeLabel(String timerangeLabel) {
        this.timerangeLabel = timerangeLabel;
    }

    public String getAggregationLabel() {
        return aggregationLabel;
    }

    public void setAggregationLabel(String aggregationLabel) {
        this.aggregationLabel = aggregationLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }



    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public List<String> getSelects() {
        return selects;
    }

    public void setSelects(List<String> selects) {
        this.selects = selects;
    }

    public Map<String, String> getAggregations() {
        return aggregations;
    }

    public void setAggregations(Map<String, String> aggregations) {
        this.aggregations = aggregations;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }


    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public List<String> getOrderBys() {
        return orderBys;
    }

    public void setOrderBys(List<String> orderBys) {
        this.orderBys = orderBys;
    }

    public Boolean getIsCountryLevel() {
        return isCountryLevel;
    }

    public void setIsCountryLevel(Boolean isCountryLevel) {
        this.isCountryLevel = isCountryLevel;
    }

    public Boolean getIsRegionLevel() {
        return isRegionLevel;
    }

    public void setIsRegionLevel(Boolean isRegionLevel) {
        this.isRegionLevel = isRegionLevel;
    }

    public void setFroms(List<String> froms) {
        this.froms = froms;
    }

    public List<String> getFroms() {
        return froms;
    }

    public Boolean getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Boolean addFlag) {
        this.addFlag = addFlag;
    }

    public Boolean getAddLabels() {
        return addLabels;
    }

    public void setAddLabels(Boolean addLabels) {
        this.addLabels = addLabels;
    }

    public Integer getNestedLimit() {
        return nestedLimit;
    }

    public void setNestedLimit(Integer nestedLimit) {
        this.nestedLimit = nestedLimit;
    }

    public String getNestedLimitField() {
        return nestedLimitField;
    }

    public void setNestedLimitField(String nestedLimitField) {
        this.nestedLimitField = nestedLimitField;
    }


    public Boolean getRunMaxDateQuery() {
        return runMaxDateQuery;
    }

    public void setRunMaxDateQuery(Boolean runMaxDateQuery) {
        this.runMaxDateQuery = runMaxDateQuery;
    }

    public String getMaxDateLimit() {
        return maxDateLimit;
    }

    public void setMaxDateLimit(String maxDateLimit) {
        this.maxDateLimit = maxDateLimit;
    }

    public Integer getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public Boolean getGetTotalAndList() {
        return getTotalAndList;
    }

    public void setGetTotalAndList(Boolean getTotalAndList) {
        this.getTotalAndList = getTotalAndList;
    }

    public Map<String, String> getAggregationsFilter() {
        return aggregationsFilter;
    }

    public void setAggregationsFilter(Map<String, String> aggregationsFilter) {
        this.aggregationsFilter = aggregationsFilter;
    }

    public Boolean getRunCalculationQuery() {
        return runCalculationQuery;
    }

    public void setRunCalculationQuery(Boolean runCalculationQuery) {
        this.runCalculationQuery = runCalculationQuery;
    }

    public String getTimeSpanType() {
        return timeSpanType;
    }

    public void setTimeSpanType(String timeSpanType) {
        this.timeSpanType = timeSpanType;
    }


    public Map<String, String> getElementsList() {
        return elementsList;
    }

    public void setElementsList(Map<String, String> elementsList) {
        this.elementsList = elementsList;
    }

    public String getNestedby() {
        return nestedby;
    }

    public void setNestedby(String nestedby) {
        this.nestedby = nestedby;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getY1Axis() {
        return y1Axis;
    }

    public void setY1Axis(String y1Axis) {
        this.y1Axis = y1Axis;
    }

    public String getY2Axis() {
        return y2Axis;
    }

    public void setY2Axis(String y2Axis) {
        this.y2Axis = y2Axis;
    }



    public String getDatasetTableName() {
        return datasetTableName;
    }

    public void setDatasetTableName(String datasetTableName) {
        this.datasetTableName = datasetTableName;
    }

    public Map<String, String> getDatasetCodes() {
        return datasetCodes;
    }

    public void setDatasetCodes(Map<String, String> datasetCodes) {
        this.datasetCodes = datasetCodes;
    }

    public List<String> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(List<String> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }



    public Boolean getShowCodes() {
        return showCodes;
    }

    public void setShowCodes(Boolean showCodes) {
        this.showCodes = showCodes;
    }

    public Boolean getShowMeasurementUnit() {
        return showMeasurementUnit;
    }

    public void setShowMeasurementUnit(Boolean showMeasurementUnit) {
        this.showMeasurementUnit = showMeasurementUnit;
    }

    public Boolean getShowNull() {
        return showNull;
    }

    public void setShowZeroValues(Boolean showZeroes) {
        this.showZeroes = showZeroes;
    }

    public Boolean getShowZeroValues() {
        return showZeroes;
    }

    public void setShowNull(Boolean showNull) {
        this.showNull = showNull;
    }

    public Boolean getShowOfficialflags() {
        return showOfficialflags;
    }

    public void setShowOfficialflags(Boolean showOfficialflags) {
        this.showOfficialflags = showOfficialflags;
    }

    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public String getDecimalseparator() {
        return decimalseparator;
    }

    public void setDecimalseparator(String decimalseparator) {
        this.decimalseparator = decimalseparator;
    }

    public String getShowDec() {
        return showDec;
    }

    public void setShowDec(String showDec) {
        this.showDec = showDec;
    }



    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Boolean getApplyOnlyYearFilter() {
        return applyOnlyYearFilter;
    }

    public void setApplyOnlyYearFilter(Boolean applyOnlyYearFilter) {
        this.applyOnlyYearFilter = applyOnlyYearFilter;
    }

    public Boolean getApplyAllFiltersExceptYears() {
        return applyAllFiltersExceptYears;
    }

    public void setApplyAllFiltersExceptYears(Boolean applyAllFiltersExceptYears) {
        this.applyAllFiltersExceptYears = applyAllFiltersExceptYears;
    }

    public Boolean getApplyAllFiltersExceptAggregrationType() {
        return applyAllFiltersExceptAggregrationType;
    }

    public void setApplyAllFiltersExceptAggregrationType(
            Boolean applyAllFiltersExceptAggregrationType) {
        this.applyAllFiltersExceptAggregrationType = applyAllFiltersExceptAggregrationType;
    }

    public Boolean getShowColumnHeaders() {
        return showColumnHeaders;
    }

    public void setShowColumnHeaders(Boolean showColumnHeaders) {
        this.showColumnHeaders = showColumnHeaders;
    }

    public Boolean getNotApplyFilters() {
        return notApplyFilters;
    }

    public void setNotApplyFilters(Boolean notApplyFilters) {
        this.notApplyFilters = notApplyFilters;
    }

    public Boolean getApplyAllFiltersExceptAreas() {
        return applyAllFiltersExceptAreas;
    }

    public void setApplyAllFiltersExceptAreas(Boolean applyAllFiltersExceptAreas) {
        this.applyAllFiltersExceptAreas = applyAllFiltersExceptAreas;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }


    public String getSelectedDataset() {
        return selectedDataset;
    }

    public void setSelectedDataset(String selectedDataset) {
        this.selectedDataset = selectedDataset;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public List<String> getGroupBys() {
        return groupBys;
    }

    public void setGroupBys(List<String> groupBys) {
        this.groupBys = groupBys;
    }

    public String getTimeIntervalQuery() {
        return timeIntervalQuery;
    }

    public void setTimeIntervalQuery(String timeIntervalQuery) {
        this.timeIntervalQuery = timeIntervalQuery;
    }

    public String getLowerDateLimit() {
        return lowerDateLimit;
    }

    public void setLowerDateLimit(String lowerDateLimit) {
        this.lowerDateLimit = lowerDateLimit;
    }

    public void setFirstSelectField(String firstSelectField) {
        this.firstSelectField = firstSelectField;

    }

    public String getFirstSelectField() {
        return firstSelectField;
    }

    public Map<String, String> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, String> databases) {
        this.databases = databases;
    }

    public boolean isElementsWithoutUnit() {
        return elementsWithoutUnit;
    }

    public void setElementsWithoutUnit(boolean elementsWithoutUnit) {
        this.elementsWithoutUnit = elementsWithoutUnit;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }

    public boolean isFlags() {
        return flags;
    }

    public void setFlags(boolean flags) {
        this.flags = flags;
    }

    public boolean isTotalFlag() {
        return totalFlag;
    }

    public void setTotalFlag(boolean totalFlag) {
        this.totalFlag = totalFlag;
    }

    public boolean isElementsUnitsWithoutBrackets() {
        return elementsUnitsWithoutBrackets;
    }

    public void setElementsUnitsWithoutBrackets(boolean elementsUnitsWithoutBrackets) {
        this.elementsUnitsWithoutBrackets = elementsUnitsWithoutBrackets;
    }

    public ArrayList<String> getValueTypeList() {
        return valueTypeList;
    }

    public void setValueTypeList(ArrayList<String> valueTypeList) {
        this.valueTypeList = valueTypeList;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getTrainingDataset() {
        return trainingDataset;
    }

    public void setTrainingDataset(String trainingDataset) {
        this.trainingDataset = trainingDataset;
    }

    public String getSelectedDatasetFile() {
        return selectedDatasetFile;
    }

    public void setSelectedDatasetFile(String selectedDatasetFile) {
        this.selectedDatasetFile = selectedDatasetFile;
    }

    public List<Object[]> getCbsResult() {
        return cbsResult;
    }

    public void setCbsResult(List<Object[]> cbsResult) {
        this.cbsResult = cbsResult;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public AMISQueryVO createCopy()
    {
        AMISQueryVO qvo = this;
        AMISQueryVO qvoNew = new AMISQueryVO();
        qvoNew.setOutput(AMISConstants.FOOTNOTE_TABLE.toString());
        qvoNew.setRunCalculationQuery(qvo.getRunCalculationQuery());
        qvoNew.setElements(qvo.getElements());
        qvoNew.setRunMaxDateQuery(qvo.getRunMaxDateQuery());
        qvoNew.setRunTimeIntervalQuery(qvo.getRunTimeIntervalQuery());
        qvoNew.setTimeIntervalQuery(qvo.getTimeIntervalQuery());
        qvoNew.setFroms(qvo.getFroms());
        qvoNew.setAreas(qvo.getAreas());
        qvoNew.setItems(qvo.getItems());
        qvoNew.setMaxDateLimit(qvo.getMaxDateLimit());
        qvoNew.setTimeSpan(qvo.getTimeSpan());
        qvoNew.setLowerDateLimit(qvo.getLowerDateLimit());
        qvoNew.setMaxDateLimit(qvo.getMaxDateLimit());
        qvoNew.setYears(qvo.getYears());
        qvoNew.setValueType(qvo.getValueType());
        qvoNew.setDatabases(qvo.getDatabases());
        qvoNew.setAddLabels(qvo.getAddLabels());
        qvoNew.setNestedLimit(qvo.getNestedLimit());
        qvoNew.setNestedLimitField(qvo.getNestedLimitField());
        qvoNew.setOrderBys(qvo.getOrderBys());
        qvoNew.setSortingOrder(qvo.getSortingOrder());
        qvoNew.setLimit(qvo.getLimit());
        qvoNew.setSelectedDataset(qvo.getSelectedDataset());
        qvoNew.setDatasetTableName(qvo.getDatasetTableName());
        qvoNew.setSelects(qvo.getSelects());
        qvoNew.setTypeOfOutput(qvo.getTypeOfOutput());
        qvoNew.setShowColumnHeaders(qvo.getShowColumnHeaders());
        qvoNew.setWidth(qvo.getWidth());
        qvoNew.setHeight(qvo.getHeight());
        qvoNew.setDatabase(qvo.getDatabase());
        qvoNew.setTitle(qvo.getTitle());
        qvoNew.setAddFlag(qvo.getAddFlag());
        qvoNew.setAggregationLabel(qvo.getAggregationLabel());
        qvoNew.setAggregations(qvo.getAggregations());
        qvoNew.setAggregationsFilter(qvo.getAggregationsFilter());
        qvoNew.setAggregationType(qvo.getAggregationType());
        qvoNew.setApplyAllFiltersExceptAggregrationType(qvo.getApplyAllFiltersExceptAggregrationType());
        qvoNew.setApplyAllFiltersExceptAreas(qvo.getApplyAllFiltersExceptAreas());
        qvoNew.setApplyAllFiltersExceptYears(qvo.getApplyAllFiltersExceptYears());
        qvoNew.setApplyOnlyYearFilter(qvo.getApplyOnlyYearFilter());
        qvoNew.setCbsResult(qvo.getCbsResult());
        qvoNew.setCodeList(qvo.getCodeList());
        qvoNew.setCommodityCode(qvo.getCommodityCode());
        qvoNew.setCommodityName(qvo.getCommodityName());
        qvoNew.setCountryCode(qvo.getCountryCode());
        qvoNew.setCountryCode(qvo.getCountryCode());
        qvoNew.setDatasetCodes(qvo.getDatasetCodes());
        qvoNew.setDatasetTableName(qvo.getDatasetTableName());
        qvoNew.setDecimalseparator(qvo.getDecimalseparator());
        qvoNew.setDescription(qvo.getDescription());
        qvoNew.setDomains(qvo.getDomains());
        qvoNew.setElementCode(qvo.getElementCode());
        qvoNew.setElementName(qvo.getElementName());
        qvoNew.setElementsList(qvo.getElementsList());
        qvoNew.setExportTitle(qvo.getExportTitle());
        qvoNew.setFirstSelectField(qvo.getFirstSelectField());
        qvoNew.setFlag(qvo.getFlag());
        qvoNew.setGetTotalAndList(qvo.getGetTotalAndList());
        qvoNew.setGroupBys(qvo.getGroupBys());
        qvoNew.setGroupCode(qvo.getGroupCode());
        qvoNew.setGroupLabel(qvo.getGroupLabel());
        qvoNew.setGroups(qvo.getGroups());
        qvoNew.setIsCountryLevel(qvo.getIsCountryLevel());
        qvoNew.setIsRegionLevel(qvo.getIsRegionLevel());
        qvoNew.setLangCode(qvo.getLangCode());
        qvoNew.setMeasurementUnit(qvo.getMeasurementUnit());
        qvoNew.setMonth(qvo.getMonth());
        qvoNew.setNestedby(qvo.getNestedby());
        qvoNew.setNotApplyFilters(qvo.getNotApplyFilters());
        qvoNew.setSelectedDatasetFile(qvo.getSelectedDatasetFile());
        qvoNew.setSelectedDatasets(qvo.getSelectedDatasets());
        qvoNew.setShowCodes(qvo.getShowCodes());
        qvoNew.setShowColumnHeaders(qvo.getShowColumnHeaders());
        qvoNew.setShowDec(qvo.getShowDec());
        qvoNew.setShowMeasurementUnit(qvo.getShowMeasurementUnit());
        qvoNew.setShowNull(qvo.getShowNull());
        qvoNew.setShowOfficialflags(qvo.getShowOfficialflags());
        qvoNew.setShowZeroValues(qvo.getShowZeroValues());
        qvoNew.setSortingOrder(qvo.getSortingOrder());
        qvoNew.setSql(qvo.getSql());
        qvoNew.setSubTitle(qvo.getSubTitle());
        qvoNew.setTableHeaders(qvo.getTableHeaders());
        qvoNew.setThousandSeparator(qvo.getThousandSeparator());
        qvoNew.setTimerangeLabel(qvo.getTimerangeLabel());
        qvoNew.setTimeSpanType(qvo.getTimeSpanType());
        qvoNew.setTrainingDataset(qvo.getTrainingDataset());
        qvoNew.setUnit(qvo.getUnit());
        qvoNew.setValue(qvo.getValue());
        qvoNew.setValueType(qvo.getValueType());
        qvoNew.setValueTypeList(qvo.getValueTypeList());
        qvoNew.setxAxis(qvo.getxAxis());
        qvoNew.setxLabel(qvo.getxLabel());
        qvoNew.setY1Axis(qvo.getY1Axis());
        qvoNew.setY2Axis(qvo.getY2Axis());
        qvoNew.setYear(qvo.getYear());
        qvoNew.setyLabel(qvo.getyLabel());
        return qvoNew;
    }


    public Map<String, String> getNationalDataSourceFilters() {
        return nationalDataSourceFilters;
    }

    public void setNationalDataSourceFilters(Map<String, String> nationalDataSourceFilters) {
        this.nationalDataSourceFilters = nationalDataSourceFilters;
    }

    public String getSelectTypeChartOrTable() {
        return selectTypeChartOrTable;
    }

    public void setSelectTypeChartOrTable(String selectTypeChartOrTable) {
        this.selectTypeChartOrTable = selectTypeChartOrTable;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public boolean isChartForAmisHomePage() {
        return chartForAmisHomePage;
    }

    public void setChartForAmisHomePage(boolean chartForAmisHomePage) {
        this.chartForAmisHomePage = chartForAmisHomePage;
    }

    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> getFoodBalanceElements() {
        return this.foodBalanceElements;
    }

    public void setFoodBalanceElements(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> foodBalanceElements) {
        this.foodBalanceElements = foodBalanceElements;
    }


    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> getOtherFoodBalanceElements() {
        return this.otherFoodBalanceElements;
    }

    public void setOtherFoodBalanceElements(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> otherFoodBalanceElements) {
        this.otherFoodBalanceElements = otherFoodBalanceElements;
    }


    public LinkedHashMap<String, String> getBalanceYears() {
        return this.balanceYears;
    }

    public void setBalanceYears(LinkedHashMap<String, String> balanceYears) {
        this.balanceYears = balanceYears;
    }


    public LinkedHashMap<String, String> getItyElements() {
        return this.ityElements;
    }

    public void setItyElements(LinkedHashMap<String, String> ityElements) {
        this.ityElements = ityElements;
    }


    public   LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> getCountriesNationalMarketingYear() {
        return this.countriesNationalMarketingYear;
    }

    public void setCountriesNationalMarketingYear(  LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> countriesNationalMarketingYear) {
        this.countriesNationalMarketingYear = countriesNationalMarketingYear;
    }

    public   LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> getCountriesInternationalTradeYear() {
        return this.countriesInternationalTradeYear;
    }

    public void setCountriesInternationalTradeYear(  LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> countriesInternationalTradeYear) {
        this.countriesInternationalTradeYear = countriesInternationalTradeYear;
    }

    // public LinkedHashMap<String,  List<String>> getNationalDataSources() {
    //    return nationalDataSources;
    // }

    //public void setNationalDataSources(LinkedHashMap<String,  List<String>> nationalDataSources) {
    // this.nationalDataSources = nationalDataSources;
    // }

    public LinkedHashMap<String,  LinkedHashMap<String, List<String>>> getNationalDataSources() {
        return nationalDataSources;
    }

    public void setNationalDataSources(LinkedHashMap<String,  LinkedHashMap<String, List<String>>> nationalDataSources) {
        this.nationalDataSources = nationalDataSources;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getCountryDates() {
        return countryDatesMap;
    }

    public void setCountryDates(LinkedHashMap<String, LinkedHashMap<String, String>> countryDates) {
        this.countryDatesMap = countryDates;
    }


    public String getAmisLastModifiedDate() {
        return amisLastModifiedDate;
    }

    public void setAmisLastModifiedDate(String amisLastModifiedDate) {
        this.amisLastModifiedDate = amisLastModifiedDate;
    }

    public void setElementFootNotesByCountry(LinkedHashMap<String, LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>>> elementFootnotes){
        this.elementFootnotes = elementFootnotes;
    }


    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>> getElementFootNotesByCountry() {
        return elementFootnotes;

    }

    // Map<database, Map<country, Map<element code, Map<year, value>>>>
    public void setPopulationValues(LinkedHashMap<String, LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Double>>>> populationValues){
        this.populationValues = populationValues;
    }


    public LinkedHashMap<String, LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Double>>>> getPopulationValues() {
        return populationValues;

    }


}