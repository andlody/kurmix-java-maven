/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

public class Model {
    Table table;
    Controller c;
    
    public Model(){
        
    }
    
    public void setKurmix(Controller c){
        this.c = c;
    }
    
    protected void table(String sql){
        this.table = Connection.getTable(sql,this.c);
    }
    
    protected String table(int row,int columnIndex){
        try{
            return  this.table.get(row,columnIndex);
        }catch(Exception e){
            String[] er = {"305",""+row,""+columnIndex,e.toString()}; 
            c.setKurmix(er);return null;
        }    
    }
    
    protected String table(int row,String columnName){
        try{
            return  this.table.get(row,columnName);
        }catch(Exception e){
            String[] er = {"302",""+row,columnName,e.toString()}; 
            c.setKurmix(er);return null;
        }
    }
    
    protected String[] table(int row){
        try{
            return this.table.get(row);
        }catch(Exception e){
            String[] er = {"304",""+row,e.toString()}; 
            c.setKurmix(er);return null;
        }
    }
    
    protected Table table(){
        return this.table;
    }
    
    protected String[][] query(String query){
        return Connection.query(query,this.c);
    }
    
    protected String filterSql(Object string){        
        String str = ""+string;
         if (str == null) {
             return null;
         }

         if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
             return str;
         }

         String clean_string = str;
         clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
         clean_string = clean_string.replaceAll("\\n","\\\\n");
         clean_string = clean_string.replaceAll("\\r", "\\\\r");
         clean_string = clean_string.replaceAll("\\t", "\\\\t");
         clean_string = clean_string.replaceAll("\\00", "\\\\0");
         clean_string = clean_string.replaceAll("'", "\\\\'");
         clean_string = clean_string.replaceAll("\\\"", "\\\\\"");

         if (clean_string.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/?\\\\\"' ]"
           ,"").length() < 1) 
         {
             return clean_string;
         }

        return null; 
    }
    
    protected <T> T lib(String lib){
        return (T) this.c.lib(lib);
    }

    protected static boolean isInteger(Object value){
        try{
            int a=Integer.parseInt(""+value);
            return true;
        }catch(NumberFormatException e){ return false;}
    }
    
    protected static boolean isNumeric(Object value){
        String s = ""+value; 
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }
}
