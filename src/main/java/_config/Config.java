/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT licensed                       | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _config;

public class Config {
    //Si la pagina esta en produccion, cambiar el valor a false.
    public static boolean DEV = true;
            
    //Base de Datos | type > 1:mysql   2:postgres   3:oracle
    public static int    TYPE       = 1;  
    public static String HOST       = "localhost";
    public static String PORT       = "3306";
    public static String USER       = "root";
    public static String PASS       = "";
    public static String DATABASE   = "kurmix";
    
    
}
