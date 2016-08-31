import org.fao.ess.amis.supplydemand.dao.AMISQueryVO;
import org.fao.ess.amis.supplydemand.excel.AMISSupplyDemandExcel;

import java.util.*;

public class TestClassNational {


    public static void main(String[] args) {

        String[] listorderbys =  { "D.database",
        "D.region_name",
                "D.product_name",
                "D.element_name",
                "D.year_label"};

        String[] listselects =  { "D.database",
                "D.region_name",
                "D.product_name",
                "D.element_name",
                "D.year_label",
                "ROUND(CAST(D.value as numeric), 2) "};


        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> foodBalance= createSupporElements(1);
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> other= createSupporElements(2);
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> ity= createSupporElements(3);
        LinkedHashMap<String, LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>>> elementFootnotes = createFootNotes();
        AMISQueryVO qvo = new AMISQueryVO();

        // set OrderBy
        qvo.setOrderBys(Arrays.asList(listorderbys));

        //set footnotes

        qvo.setElementFootNotesByCountry(elementFootnotes);


        // set Items
        Map<String, String> items = new HashMap<>();
        items.put("6","Soybeans");
        qvo.setItems(items);

        //areas
        Map<String, String> areas = new HashMap<>();
        areas.put("12","Argentina");
        areas.put("37","Brazil");
        qvo.setAreas(areas);

        // set OrderBy
        qvo.setOrderBys(Arrays.asList(listorderbys));
        // set setFoodBalanceElements
        qvo.setFoodBalanceElements(createQVOList(1));
        // set setOtherFoodBalanceElements
        qvo.setOtherFoodBalanceElements(createQVOList(2));
        LinkedHashMap<String, String> itys = new LinkedHashMap<>();

        itys.put("8","Import (ITY) - Million tonnes");
        itys.put("12","Exports (ITY) - Million tonnes");

        qvo.setItyElements(itys);
        // set setCountryDates
        qvo.setCountryDates(setCountryDates());
        // set getCountriesInternationalTradeYear
        qvo.setCountriesInternationalTradeYear(getCountriesInternationalTradeYear());

        // set getCountriesMarketingTradeYear
         qvo.setCountriesNationalMarketingYear(getCountriesMarketingTradeYear());
        // xLabel
        qvo.setxLabel("COUNTRY");
        Map<String,String> databases = new HashMap<String, String>();
        databases.put("NATIONAL","FAO-AMIS");
        qvo.setDatabases(databases);


        AMISSupplyDemandExcel excel = new AMISSupplyDemandExcel();

        excel.createExcel(foodBalance,other,ity,qvo);

        //1) qvo.getFoodElement

        //1) qvo.getPopulationValues
        //{CBS={Argentina={1={2016/17=43847.0}}}

        //1) qvo.getxLabel ("COUNTRY")
        //1) qvo.getCountriesNationalMarketingYear
        //1) qvo. qvo.getCountryDates()
        //{Argentina={2004/05=2004-01-01, 2005/06=2005-01-01, 2006/07=2006-01-01, 2007/08=2007-01-01, 2008/09=2008-01-01, 2009/10=2009-01-01, 2010/11=2010-01-01, 2011/12=2011-01-01, 2012/13=2012-01-01, 2013/14=2013-01-01, 2014/15=2014-01-01, 2015/16=2015-01-01, 2016/17=2016-01-01}}
        //1) qvo.getElements
        // getAreas
        //getItems
        //getCountriesInternationalTradeYear
        // {CBS={5={Argentina=[July/June, 2015/16, July 2015 to June 2016]}}}







    }


    private static LinkedHashMap<String, LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>>>  createFootNotes() {

        LinkedHashMap<String, String> lowest = new LinkedHashMap<>();
        lowest.put("Food Use","Food Use asdsadas, sdsadsad");

        LinkedHashMap<String, LinkedHashMap<String, String>> second = new LinkedHashMap<>();
        second.put("Argentina",lowest);

        LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>> third = new LinkedHashMap<>();
        third.put("6", second);

        LinkedHashMap<String, LinkedHashMap<String,  LinkedHashMap<String, LinkedHashMap<String, String>>>> result = new LinkedHashMap<>();
        result.put("NATIONAL", third);

        return result;
    }

    private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> createSupporElements(int number)
    {


        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>>> result= new LinkedHashMap<>();
        Map<String, Double> values = new HashMap<>();
        Map<String, Double> valuesSecondColumn = new HashMap<>();

        LinkedHashMap<String, Map<String, Double>> secondValues = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, Map<String, Double>>> third = new LinkedHashMap<>();

        switch (number) {

            case 1:
                values.put("16",(Double)4.0);
                values.put("20",(Double)16.4);
                values.put( "10",(Double)21.5);
                values.put("24",(Double)0.3);
                values.put("7",(Double)0.0);
                values.put("18",(Double)4.0);
                values.put("38",(Double)3.5);
                values.put("5",(Double)37.9);
                values.put("19",(Double)41.9);
                values.put("35",(Double)41.9);


                valuesSecondColumn.put("16",(Double)4.0);
                valuesSecondColumn.put("20",(Double)16.4);
                valuesSecondColumn.put( "10",(Double)21.5);
                valuesSecondColumn.put("24",(Double)0.3);
                valuesSecondColumn.put("7",(Double)0.0);
                valuesSecondColumn.put("18",(Double)4.0);
                valuesSecondColumn.put("38",(Double)3.5);
                valuesSecondColumn.put("5",(Double)37.9);
                valuesSecondColumn.put("19",(Double)41.9);
                valuesSecondColumn.put("35",(Double)41.9);



                secondValues.put("2015/16",values);
                secondValues.put("2016/17",valuesSecondColumn);

                third.put("Argentina", secondValues);
                third.put("Brazil", secondValues);
                result.put("NATIONAL", third);
                break;

            // other
            case 2:

                values.put("2",(Double)5.99);
                values.put("25",(Double)16.4);
                values.put( "4",(Double)21.5);

                valuesSecondColumn.put("2",(Double)5.99);
                valuesSecondColumn.put("25",(Double)16.4);
                valuesSecondColumn.put( "4",(Double)21.5);


                secondValues.put("2015/16",values);
                secondValues.put("2016/17",valuesSecondColumn);


                third.put("Argentina", secondValues);
                third.put("Brazil", secondValues);
                result.put("NATIONAL", third);

                break;

            case 3:
                values.put("12",(Double)4.0);
                values.put("8",(Double)16.4);

                valuesSecondColumn.put("12",(Double)4.0);
                valuesSecondColumn.put("8",(Double)16.4);


                secondValues.put("2015/16",values);
                secondValues.put("2016/17",valuesSecondColumn);


                third.put("Argentina", secondValues);
                third.put("Brazil", secondValues);
                result.put("NATIONAL", third);
                break;
        }


        return result;
    }


    private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> createQVOList(int number) {

        //{NATIONAL={5={1=Population - 1000s, 25=Per Capita Food Use - Kg/Yr, 37=Area Planted - Million Ha, 2=Area Harvested - Million Ha, 4=Yield - Tonnes/Ha}}}

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> result = new LinkedHashMap<>();

        LinkedHashMap<String, LinkedHashMap<String, String>> second = new LinkedHashMap<>();
        LinkedHashMap<String, String> third = new LinkedHashMap<>();
        switch (number) {
// foodblacne
            case 1:

                third.put("19","Total Supply - Million tonnes");
                third.put("18","Opening Stocks - Million tonnes");
                third.put("5","Production - Million tonnes");
                third.put("7","Imports (NMY) - Million tonnes");
                third.put("35","Total Utilization - Million tonnes");
                third.put("20","Domestic Utilization - Million tonnes");
                third.put("24","Food/Seeds/Industrial Use - Million tonnes");
                third.put("38","Feed and residual - Million tonnes");
                third.put("10","Exports (NMY) - Million tonnes");
                third.put("16","Closing Stocks - Million tonnes");
                second.put("6",third);
                result.put("NATIONAL",second);

                break;

            //others
            case 2:
                third.put("1","Population - 1000s");
                third.put("25","Per Capita Food Use - Kg/Yr");
                third.put("37","Area Planted - Million Ha");
                third.put("2","Area Harvested - Million Ha");
                third.put("4","Yield - Tonnes/Ha");
                second.put("6",third);
                result.put("NATIONAL",second);
                break;

            case 3:
                third.put("1","Population - 1000s");
                third.put("25","Per Capita Food Use - Kg/Yr");
                third.put("37","Area Planted - Million Ha");
                third.put("2","Area Harvested - Million Ha");
                third.put("4","Yield - Tonnes/Ha");
                second.put("6",third);
                result.put("NATIONAL",second);
                break;
        }
        return result;
    }

    private static LinkedHashMap<String, LinkedHashMap<String, String>> setCountryDates() {

        LinkedHashMap<String, LinkedHashMap<String, String>> result = new LinkedHashMap<>();
        LinkedHashMap<String, String> second = new LinkedHashMap<>();
        second.put("2015/16","2016-03-01");
        second.put("2016/17","2016-01-01");
        result.put("Argentina",second);
        result.put("Brazil",second);
        return result;
    }

    private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> getCountriesInternationalTradeYear() {
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> result = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>> second = new LinkedHashMap<>();
        LinkedHashMap<String,  LinkedList<String>> third = new LinkedHashMap<>();
        String[] list = {"July/June", "2015/16", "July 2015 to June 2016"};
        LinkedList<String> last = new LinkedList<>(Arrays.asList(list));
        third.put("Argentina",last);
        third.put("Brazil",last);
        second.put("6",third);
        result.put("NATIONAL",second);
        return result;

    }

    private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> getCountriesMarketingTradeYear() {
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>> result = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>>();
        LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>> second = new LinkedHashMap<String, LinkedHashMap<String,  LinkedList<String>>>();
        LinkedHashMap<String,  LinkedList<String>> third = new LinkedHashMap<String,  LinkedList<String>>();
        String[] list = {"July/June", "2015/16", "July 2015 to June 2016", "test"};
        LinkedList<String> last = new LinkedList<String>(Arrays.asList(list));
        third.put("Argentina",last);
        third.put("Brazil",last);
        second.put("6",third);
        result.put("NATIONAL",second);
        return result;

    }


}
