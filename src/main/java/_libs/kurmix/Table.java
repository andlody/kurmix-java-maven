/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

public class Table {
    String[] columnNames;
    String[][] content;
    
    public Table(String[] columnNames){
        this.columnNames = columnNames;
        this.content = new String[0][0];
    }
    
    public void add(String[] row){      
        String[][] aux = new String[this.content.length+1][this.columnNames.length];
        for (int i = 0; i < this.content.length; i++) {
            for (int j = 0; j < this.columnNames.length; j++) {
                aux[i][j] = this.content[i][j]; 
            }
        }
        aux[aux.length-1] = row;
        this.content = aux;
    }
    
    public void setContent(String[][] content){
        this.content = content;
    }
    
    public String get(int row,int column){
        return this.content[row][column];
    }
    
    public String get(int row,String columnName){
        int column = -1;
        for (int i = 0; i < this.columnNames.length; i++) {
            if(this.columnNames[i].equalsIgnoreCase(columnName)){
                column = i;break;
            }
        }
        return this.content[row][column];
    }
    
    public String[] get(int row){
        return this.content[row];
    }
    
    public int rows(){
        return this.content.length;
    }
    
    public int columns(){
        return this.columnNames.length;
    }
    
    public String[][] get(){
        return this.content;
    }
   
}
