package org.fao.ess.amis.supplydemand.excel;


import org.fao.ess.amis.supplydemand.dao.AMISQueryVO;

import java.util.Map;

public class AMISSupplyDemandUtils {


    private static AmisSupplyDemandExcelUtilsNew excelUtilsNew;


    public static String convertMapItemLabelsToString(Map<String, String> mapItems){
        StringBuilder builder = new StringBuilder();
        int i = 0;

        for(String key: mapItems.keySet()){
            builder.append(mapItems.get(key));

            if(i < mapItems.size() -1 )
                builder.append(", ");

            i++;
        }


        return builder.toString();

    }

    public static String convertMapItemCodesToString(Map<String, String> mapItems){
       StringBuilder builder = new StringBuilder();
        int i = 0;

        for(String key: mapItems.keySet()){
            builder.append(key);

            if(i < mapItems.size() -1 )
                builder.append(", ");

            i++;
        }


        return builder.toString();

    }

    public static String getProductCode(AMISQueryVO qvo, String product){
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

    public static Boolean itemsContainRice(AMISQueryVO qvo){
        if(qvo.getItems().containsKey("4")){
            return true;
        } else {
            return false;
        }

    }

    public static String formatSheetName(String country, String label, String datasource){

        String sheet_name="";

        //Fix problematic country names
        if(country.equals("United States of America")){
            country = "USA";
        }
        if(label.equals("United States of America")){
            label = "USA";
        }
        if(datasource.equals("United States of America")){
            datasource = "USA";
        }
        if(country.equals("Russian Federation")){
            country = "Russian Fed";
        }
        if(label.equals("Russian Federation")){
            label = "Russian Fed";
        }
        if(datasource.equals("Russian Federation")){
            datasource = "Russian Fed";
        }

        if(country.equals("Republic of Korea")){
            country = "Rep of Korea";
        }
        if(label.equals("Republic of Korea")){
            label = "Rep of Korea";
        }
        if(datasource.equals("Republic of Korea")){
            datasource = "Rep of Korea";
        }

        if(country.equals("China Mainland")){
            country = "China Main";
        }
        if(label.equals("China Mainland")){
            label = "China Main";
        }
        if(datasource.equals("China Mainland")){
            datasource = "China Main";
        }

        if(country.equals("European Union")){
            country = "EU";
        }
        if(label.equals("European Union")){
            label = "EU";
        }
        if(datasource.equals("European Union")){
            datasource = "EU";
        }

        if(datasource.equals("CBS")){
            datasource = "FAO-AMIS";
        }

        if(country.contains("Rice")){
           // country = country.split(",")[0];
            country = "Rice";
        }

        if(label.contains("Rice")){
            //label = label.split(",")[0];
            label = "Rice";
        }

        if(country.contains("Wheat")){
            country = country.split("\\(")[0];
        }

        if(label.contains("Wheat")){
            label = label.split("\\(")[0];
        }


        sheet_name = country + " " + label + " "+datasource;

        return sheet_name;
    }



}