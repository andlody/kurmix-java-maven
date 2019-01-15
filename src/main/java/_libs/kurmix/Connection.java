/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;
import _config.Config;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connection {
    
    private static java.sql.Connection con;
    
    static void start(Controller cnt) {
        Connection.con=null;
        Statement query = null;
        String driver  = "Driver no encontrado";
        String conex = "";
        
        switch(Config.TYPE){
            case 1: driver  = "com.mysql.jdbc.Driver";
                    conex = "jdbc:mysql://"+Config.HOST+":"+Config.PORT+"/"+Config.DATABASE;
                break;
            case 2: driver  = "org.postgresql.Driver";
                    conex = "jdbc:postgresql://"+Config.HOST+":"+Config.PORT+"/"+Config.DATABASE;
                break;            
            case 3: driver  = "oracle.jdbc.driver.OracleDriver";
                    conex = "jdbc:oracle:thin:@"+Config.HOST+":"+Config.PORT+"/"+Config.DATABASE;
                break;
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            String[] er = {"306",driver,e.toString()}; 
            cnt.setKurmix(er);
        }
        
        try {            
            Connection.con = DriverManager.getConnection(conex,Config.USER,Config.PASS);
        } catch (SQLException e) {          
            String[] er = {"306",conex,e.toString()}; 
            cnt.setKurmix(er);
        }  
        
        try {
            query = Connection.con.createStatement();
        } catch (SQLException e) { }
        
    }
    
    static String[][] query(String sql, String[] array, Controller cnt) { 
        Connection.start(cnt);
        PreparedStatement query = null;
        if(!sql.trim().substring(0,6).equalsIgnoreCase("select")){
            try { 
                query = Connection.con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                for(int i=0;i<array.length;i++){
                    query.setString((int)i+1, array[i]);
                }
                
                query.executeUpdate(); 
                String bb = "0";
                try{
                    ResultSet generatedKeys = query.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        bb = ""+generatedKeys.getInt(1);
                    }
                }catch(Exception e){}
                String aux[][] = {{""+bb}};
                return aux;
            }catch (Exception ex) {
                String aux[][] = {{"0"}};
                return aux;
            }
        }
        
        try {
            query = Connection.con.prepareStatement(sql);
            for(int i=0;i<array.length;i++){
                query.setString((int)i+1, array[i]);
            }
            
            ResultSet tabla = query.executeQuery(); 
            ResultSetMetaData rsmd = tabla.getMetaData();
            
            int n = rsmd.getColumnCount();
            ArrayList<String[]> a = new ArrayList<String[]>();
            while(tabla.next()){  
                String[] b = new String[n];
                for (int i = 0; i < n; i++) {
                    b[i] = tabla.getString(i+1);
                }
                a.add(b);                 
            }
                
            Connection.con.close();
                
            String[][] d = new String[a.size()][n];
            for(int i = 0; i < a.size(); i++) {
                d[i] = a.get(i);
            }                  
            return d;
            
        }catch (SQLException e) { 
            String[] er = {"301",sql,e.toString()}; 
            cnt.setKurmix(er);return null; }
    }
    

    
    static Table getTable(String sql,Controller cnt){
        Table table = null;
        //Statement query = Connection.start(cnt);
        try {
            ResultSet tabla=null;// = query.executeQuery(sql);        
            ResultSetMetaData rsmd = tabla.getMetaData();
         
            int n = rsmd.getColumnCount();
            String[] columnNames = new String[n];
            for (int i = 0; i < n; i++) {
                columnNames[i]=rsmd.getColumnName(i+1);
            }
            
            table = new Table(columnNames);
   
            while(tabla.next()){ 
                String[] row = new String[n];
                for (int i = 0; i < n; i++) {
                    row[i] = tabla.getString(i+1);
                }                
                table.add(row);                 
            }
            Connection.con.close();
            return table;
        }catch (SQLException e) { 
            String[] er = {"303",sql,e.toString()}; 
            cnt.setKurmix(er);return null; }     
    } 
}    
    
