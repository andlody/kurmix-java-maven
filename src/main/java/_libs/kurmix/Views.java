/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

import javax.servlet.http.HttpServletRequest;

public class Views {
    private Data data;
    public String body;
    public String action;
    public String controller;
    
    public Views(HttpServletRequest r){        
        this.data = new Data(r);
        this.body = this.data.body();
        this.action = this.data.action();
        this.controller = this.data.controller();
    }
    
    public <T> T get(String index){
        return (T) this.data.get(index);
    }
    
    public <T> T get(int index){
        return (T) this.data.get(index);
    }
    
    public <T> T get(){
         return (T) this.data.get();
    }
     
    public int size(String a){
        return this.data.size(a);
    }
    
    public int size(){
        return this.data.size();
    }

    public String partial(String a){
        return "/view/_partials/"+a+".jsp";
    }      
}
