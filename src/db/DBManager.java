package db;


import db.entity.UserRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String STATISTICS="statistics";

    public static int insertGlobalRecord(String name,String date, double k_factor){
        String  query ="INSERT INTO "+STATISTICS+"(name,date,k_factor) VALUES ('"+name+"','"+date+"',"+k_factor+")RETURNING id;";
        ResultSet resultSet = DBExecutor.executeSelect(query);
        int userID=-1;
        try {
            resultSet.next();
            userID = resultSet.getInt(1);
            System.out.println("ID: "+userID);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return userID ;
    }

    public static void printStatisticsTable(){
        String  query = "SELECT* from statistics;";
        ResultSet resultSet = DBExecutor.executeSelect(query);
        try {
            while ( resultSet.next()) {

                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static  List<UserRecord>  getTop100(){
        String query = "SELECT id, name, date, k_factor FROM "+STATISTICS+"  ORDER BY k_factor DESC LIMIT 100;";
        ResultSet resultSet = DBExecutor.executeSelect(query);
        List<UserRecord> userRecordsList = new ArrayList<UserRecord>();
        try {
            while ( resultSet.next()) {
             UserRecord userRecord = new UserRecord();
                userRecord.setGlobalUserID(resultSet.getInt("id"));
                userRecord.setName(resultSet.getString("name"));
                userRecord.setDate(resultSet.getString("date"));
                userRecord.setK_factor(resultSet.getDouble("k_factor"));
               userRecordsList.add(userRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
         return userRecordsList;
    }


    public static void updateUserRecord(String date, double k_faktor, int userID){
        String query = "UPDATE statistics set date='"+date+"', k_factor = "+k_faktor+" WHERE id="+userID+";";
        DBExecutor.executeInsert(query);
    }

}
