package org.fao.ess.amis.supplydemand.dao;

import java.util.HashMap;
import java.util.Map;

public enum AMISConstants{

    // Outputs and Types of Outputs
    CHART,
    BAR, PIE, TIMESERIES, SCATTER, BAR_WITH_CATEGORIES, AREA, STACKED,

    HTML, CEREAL_BALANCE_TABLE, FOOD_BALANCE,

    MAP,
    TABLE,
    PIVOT_TABLE,
    METADATA_TABLE,
    FOOTNOTE_TABLE,

    // Exports
    EXPORT,
    EXPORT_CSV, EXPORT_EXCEL, EXPORT_EXCEL_FOOD_BALANCE,
    EXPORT_EXCEL_VALUES,

    // Retrieving Informations
    CODING_SYSTEM,
    CODING_SYSTEM_CCBS_COUNTRIES,
    CODING_SYSTEM_CCBS_ELEMENTS,
    CODING_SYSTEM_CCBS_PRODUCTS,
    CODING_SYSTEM_CCBS_YEARS,
    CODING_SYSTEM_PSD_COUNTRIES,
    CODING_SYSTEM_PSD_ELEMENTS,
    CODING_SYSTEM_PSD_PRODUCTS,
    CODING_SYSTEM_PSD_YEARS,

    // Selection/Filter Types
    TIMERANGE,
    TIMERANGE_SPLIT,
    DOWNLOAD_TIMERANGE,
    AMIS_DATASOURCE,
    CCBS_COUNTRIES, PSD_COUNTRIES,
    CCBS_TIMERANGE, PSD_TIMERANGE,
    CCBS_ELEMENTS, CCBS_PRODUCTS,
    PSD_ELEMENTS, PSD_PRODUCTS,
    CCBS_YEARS, PSD_YEARS,

    //CODING_SYSTEM_FAOSTAT_ALL_GROUPS_DOMAINS,
    CODING_SYSTEM_FAOSTAT_CROP_GROUPS_DOMAINS,
    CODING_SYSTEM_FAOSTAT_GROUPS,
    CODING_SYSTEM_FAOSTAT_DOMAINS_FOR_GROUP,
    CODING_SYSTEM_FAOSTAT_ELEMENT,
    CODING_SYSTEM_FAOSTAT_ELEMENT_LIST,
    CODING_SYSTEM_FAOSTAT_ELEMENT_BY_DOMAIN_ITEM_ELEMENT,
    CODING_SYSTEM_FAOSTAT_ITEM,
    CODING_SYSTEM_FAOSTAT_ITEM_AGGREGATIONS,
    CODING_SYSTEM_FAOSTAT_ITEM_DISAGGREGATIONS,
    CODING_SYSTEM_FAOSTAT_ITEM_BY_DOMAIN_ITEM_ELEMENT,
    CODING_SYSTEM_FAOSTAT_COUNTRIES,
    CODING_SYSTEM_FAOSTAT_AREAS_WORLD,
    CODING_SYSTEM_FAOSTAT_AREAS_FAO,
    CODING_SYSTEM_FAOSTAT_COUNTRIES_AREAS_ALL,
    CODING_SYSTEM_FAOSTAT_YEAR,

    CODING_SYSTEM_FAOSTAT_DOMAINS_FROM_FENIX,

    AMIS_DATASETS,
    AMIS_COUNTRIES,
    AMIS_COUNTRY_CROP_COUNT,
    AMIS_PRODUCT_CROP_COUNT,
    AMIS_NUM_CROP_COUNT,
    AMIS_MARKETING_YEAR,
    AMIS_ELEMENTS_CODE,
    AMIS_ITEMS,
    AMIS_PRODUCTS,
    AMIS_ELEMENTS,
    AMIS_FLAGS,
    YEARS,
    AMIS_GET_PRODUCTS_BY_ELEMENT,
    AMIS_GET_PRODUCTS_BY_ELEMENT_COMPARE,
    AMIS_PRODUCTS_WITHOUT_POPULATION,
    AMIS_GET_ELEMENTS_BY_PRODUCT,
    AMIS_GET_ELEMENTS_BY_PRODUCT_COMPARE,
    AMIS_ELEMENTS_WITH_DATABASES,
    CCBS_ELEMENTS_MULTI,
    PSD_ELEMENTS_MULTI,
    AMIS_G20_COUNTRIES_MULTI,
    AMIS_YEAR_PANEL,
    AMIS_CHART,
    OLAP_TABLE,
    AMIS_SECRETARIAT,
    AMIS_COUNTRY_USER,
    AMIS_NOT_LOGGED_USER,

    AGGREGATION_TYPE,

    //Food Balance
    AMIS_FOOD_BALANCE_PRODUCTS,
    AMIS_FOOD_BALANCE_COUNTRIES,

    // Retrieving Database/Dataset Info
    FAOSTAT_DATABASE,
    FENIX_DATABASE,
    FENIX_DATASET,

    //DATASETS
    AMIS,
    FAOSTAT,
    CBS,
    PSD,
    IGC,
    NATIONAL,
    PSD_CCBS,
    AMIS_COUNTRY_DATA,
    AMIS_HISTORICAL_COUNTRY_DATA,
    // FAOSTAT Types

    FAOSTAT_GROUPS, FAOSTAT_DOMAINS, FAOSTAT_DOMAINS_FOR_GROUP,
    FAOSTAT_COUNTRIES, FAOSTAT_AREAS_WORLD, FAOSTAT_AREAS_FAO, FAOSTAT_AREAS_ALL,
    FAOSTAT_ELEMENTS, FAOSTAT_ELEMENTS_LIST, FAOSTAT_ELEMENTS_FOR_ITEM,
    FAOSTAT_ITEMS,
    FAOSTAT_YEARS,
    FAOSTAT_TIMERANGE,
    FAOSTAT_ITEMS_MULTI,
    FAOSTAT_COUNTRIES_MULTI,
    FAOSTAT_AREAS_WORLD_MULTI,
    FAOSTAT_AREAS_FAO_MULTI,
    FAOSTAT_YEARS_MULTI,
    FAOSTAT_ITEMS_COMPARE, CODING_SYSTEM_AMIS_ITEM_BY_DOMAIN_ITEM_ELEMENT_UNIT,

    //This is the name of VIEW for amis
    amis_all_datasources,

    //GOOGLE ANALYTICS TRACKING ACTIONS (EVENTS)
    DOWNLOADING, MODIFYING, VIEWING;

    // Faostat pivot tables variables this convers the dimension to the Store Procedure accepted dimension
    public static HashMap<String, String> dimension_to_sp = new HashMap<String, String>();

    static {
        dimension_to_sp = new HashMap<String, String>();

        dimension_to_sp.put(FAOSTAT_ELEMENTS.toString(), "extra");
        dimension_to_sp.put(FAOSTAT_COUNTRIES.toString(), "countries");
        dimension_to_sp.put(FAOSTAT_YEARS.toString(), "years");
        dimension_to_sp.put(FAOSTAT_ITEMS.toString(), "items");

    }

    public static Map<String, String> codingSystemMap;

    static {
        codingSystemMap = new HashMap<String, String>();

        codingSystemMap.put(CCBS_COUNTRIES.toString(),CODING_SYSTEM_CCBS_COUNTRIES.toString());
        codingSystemMap.put(PSD_COUNTRIES.toString(), CODING_SYSTEM_PSD_COUNTRIES.toString());

        codingSystemMap.put(CCBS_YEARS.toString(), CODING_SYSTEM_CCBS_YEARS.toString());
        codingSystemMap.put(PSD_YEARS.toString(), CODING_SYSTEM_PSD_YEARS.toString());
        codingSystemMap.put(CCBS_TIMERANGE.toString(), CODING_SYSTEM_CCBS_YEARS.toString());
        codingSystemMap.put(PSD_TIMERANGE.toString(), CODING_SYSTEM_PSD_YEARS.toString());


        codingSystemMap.put(CCBS_ELEMENTS.toString(), CODING_SYSTEM_CCBS_ELEMENTS.toString());
        codingSystemMap.put(PSD_ELEMENTS.toString(), CODING_SYSTEM_PSD_ELEMENTS.toString());
        codingSystemMap.put(CCBS_PRODUCTS.toString(), CODING_SYSTEM_CCBS_PRODUCTS.toString());
        codingSystemMap.put(PSD_PRODUCTS.toString(), CODING_SYSTEM_PSD_PRODUCTS.toString());

        //same used in PSD and CCBS
        codingSystemMap.put(AMIS_COUNTRIES.toString(),AMIS_COUNTRIES.toString());
        codingSystemMap.put(AMIS_PRODUCTS.toString(),AMIS_PRODUCTS.toString());
        codingSystemMap.put(AMIS_PRODUCTS_WITHOUT_POPULATION.toString(),AMIS_PRODUCTS_WITHOUT_POPULATION.toString());

        //Timerange
        codingSystemMap.put(TIMERANGE.toString(),TIMERANGE.toString());


        // MULTI Selection (probably will not need)
        codingSystemMap.put(CCBS_ELEMENTS_MULTI.toString(), CODING_SYSTEM_CCBS_ELEMENTS.toString());
        codingSystemMap.put(AMIS_G20_COUNTRIES_MULTI.toString(), AMIS_COUNTRIES.toString());
        codingSystemMap.put(PSD_ELEMENTS_MULTI.toString(), CODING_SYSTEM_PSD_ELEMENTS.toString());

    }


    public static String defaultLanguage = "E";

    static HashMap<String, String> languages;

    static HashMap<String, String> faostatAlias;

    static HashMap<String, String> aggregationTypeAlias;


    static {
        languages = new HashMap<String, String>();

        languages.put("EN", "E");
        languages.put("FR", "F");
        languages.put("ES", "S");
        languages.put("CH", "C");
        languages.put("AR", "A");
        languages.put("RU", "R");
    }


    static {
        faostatAlias = new HashMap<String, String>();

        faostatAlias.put(AMISTableConstants.Data.toString(), "D");
        faostatAlias.put(AMISTableConstants.DomainItem.toString(), "DI");
        faostatAlias.put(AMISTableConstants.Element.toString(), "E");
        faostatAlias.put(AMISTableConstants.DomainElement.toString(), "DE");
        faostatAlias.put(AMISTableConstants.Item.toString(), "I");
        faostatAlias.put(AMISTableConstants.Flag.toString(), "F");
        faostatAlias.put(AMISTableConstants.DomainItemElement.toString(), "DIE");
    }


    static {
        aggregationTypeAlias = new HashMap<String, String>();

        aggregationTypeAlias.put(AMISAggregationConstants.SUM.toString(), "SUM");
        aggregationTypeAlias.put(AMISAggregationConstants.SUBTRACT.toString(), "-");
    }

}