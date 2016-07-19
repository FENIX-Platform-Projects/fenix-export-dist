package org.fao.ess.amis.supplydemand.input;

import org.fao.ess.amis.supplydemand.utils.data.ConnectionManager;
import org.fao.fenix.commons.msd.dto.data.Resource;
import org.fao.fenix.export.core.dto.data.CoreData;
import org.fao.fenix.export.core.input.plugin.Input;

import java.sql.*;
import java.util.Map;

public class SupplyDemandInput extends Input {
    @Override
    public void init(Map<String, Object> map, Resource resource) {

        ConnectionManager connectionManager = new ConnectionManager();


        connectionManager.init();



        System.out.println("Init input");

        for(String s: map.keySet()){
            if(map.get(s)!= null)
                System.out.println("value: "+map.get(s));
        }


        String query = "select count(*) as c from amis_all_datasources";

        Connection connection = connectionManager.getConnection();

        Statement statement= null;
        ResultSet rs = null;


        try {
            statement = connection.createStatement();

             rs = statement.executeQuery(query);

            while (rs.next()) {
                       System.out.println(rs.getString("c"));
                     }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public CoreData getResource() {

        System.out.println("here");

        return null;
    }
}
