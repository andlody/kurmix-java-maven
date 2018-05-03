/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

import javax.servlet.http.HttpServletRequest;

public class Data {
    private HttpServletRequest request;
    private Object obj;
    private Object[] objx;
    private String[] strIndex;
    private Object[] objy;
    int[] intIndex;
    
    public Data(){
        this.objx = new Object[0];
        this.objy = new Object[0];
        this.intIndex = new int[0];
        this.strIndex = new String[0];
    }
    
    public Data(HttpServletRequest r){        
        Data d = (Data)r.getAttribute("data");
        this.request = r;
        this.obj = d.obj;
        this.intIndex = d.intIndex;
        this.strIndex = d.strIndex;
        this.objx = d.objx;
        this.objy = d.objy;        
    }
        
    public <T> T get(String index){
        int aux=-1;
        for (int i = 0; i < this.strIndex.length; i++) {
            aux=i;
            if(index.equalsIgnoreCase(this.strIndex[i])) break;
        }
        return (T) this.objx[aux];
    }
    
    public <T> T get(int index){
        int aux=-1;
        for (int i = 0; i < this.intIndex.length; i++) {
            aux=i;
            if(index==this.intIndex[i]) break;
        }
        return (T) this.objy[aux];
    }
    
    public <T> T get(){
         return (T) this.obj;
    }
    
    public void set(Object value){
        this.obj = value;
    }
    
    public void set(String stringIndex,Object value){
        Object[] aux = new Object[this.objx.length+1];
        String[] str = new String[this.objx.length+1];
        for (int i = 0; i < this.objx.length; i++) {
            aux[i]=this.objx[i];
            str[i]=this.strIndex[i];
        }
        aux[this.objx.length]=value;
        str[this.objx.length]=stringIndex;
        this.objx = aux;           
        this.strIndex = str;
    }
    
    public void set(int index,Object value){
        Object[] aux = new Object[this.objy.length+1];
        int[] str = new int[this.objy.length+1];
        for (int i = 0; i < this.objy.length; i++) {
            aux[i]=this.objy[i];
            str[i]=this.intIndex[i];
        }
        aux[this.objy.length]=value;
        str[this.objy.length]=index;
        this.objy = aux;           
        this.intIndex = str;
    }
    
    public int size(String a){
        return this.strIndex.length;
    }
    public int size(){
        return this.intIndex.length;
    }
    
    public String body(){
        String aux = (String) request.getAttribute("kurmi_body");
        return "/view/"+aux+".jsp";
    }
    
    public String controller(){
        String aux = (String) request.getAttribute("kurmi_body");
        String[] a = aux.split("/");
        return a[0];
    }
    
    public String action(){
        String aux = (String) request.getAttribute("kurmi_body");
        String[] a = aux.split("/");
        return a[1];
    }
    
    public String json(){
        String[] a = this.strIndex;
        Object[] b = this.objx;
        String ini = "{";
        for(int i = 0; i < a.length; i++) {
            ini = ini + "\""+a[i]+"\":";
                               
            if(b[i].getClass().isArray()){
                String[] c = (String[]) b[i];
                String ini2 = "[";
                for(int j = 0; j < c.length; j++) {
                    ini2 = ini2 + "\""+c[j]+"\"";
                    if(j+1!=c.length) ini2=ini2+",";
                }
                ini=ini+ini2+"]";
            }else 
                ini=ini+"\""+b[i]+"\"";
            
            if(i+1!=a.length) ini=ini+",";
        }
        ini = ini + "}";
        return ini;
    }
        
}
