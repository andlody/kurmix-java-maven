/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller {
        ReqRes reqres;
        
    public Controller(){
        this.reqres = new ReqRes();
    }
    
    public void index(){}
    public void start(){}
    public void after(){}
    public void before(){}
    public void finish(){}
    
    public void setKurmix(HttpServletRequest request, HttpServletResponse response){
        this.reqres.setRequest(request);
        this.reqres.setResponse(response);
    }
         
    public String setKurmix(String view) throws ServletException, IOException{
        if(this.reqres.getBand()) this.reqres.setView(view);
        if(this.reqres.getBand2()) return null;
        return this.reqres.show();
    }
    
    public void setKurmix(String[] value){
        this.reqres.error(value);        
    }
    
    public boolean setKurmix(){
        return this.reqres.getBand2();
    }
     
    public void view(String view){
        this.reqres.setView(view);
    }
    
    public void view(String view,String temp){
        this.reqres.setTemplate(temp);
        this.view(view);
    }
    
    public void template(String temp){
        this.reqres.setTemplate(temp);
    }
         
    public void write(String txt){
            this.reqres.write(txt);
            this.view(null);
    }
    
    public void redirect(String url){
        this.reqres.redirect(url,0);
    }
    
    public void redirect(String url,int value){
        this.reqres.redirect(url,value);
    }
    
    public <T> T session(String name){
        HttpSession sesion = this.reqres.getRequest().getSession();
        if(name==null){ sesion.invalidate(); return null;}
        return (T)sesion.getAttribute(name);
    }
    
    public void session(String name,Object value){
        HttpSession sesion = this.reqres.getRequest().getSession();
        sesion.setAttribute(name, value);
    }   
   
    public <T> T model(String model){
        try{
            Class<?> c = Class.forName("model."+model);
            Object o = c.newInstance();
            Method method = o.getClass().getMethod("setKurmix",Controller.class);
            method.invoke(o,this); 
            return (T) o;
        }catch(Exception e){
            String[] er = {"201",model,e.toString()}; 
            this.setKurmix(er);
        }
        return null;
    }
    
    public String parameter(String name){
        return this.reqres.getParameter(name);
    }
    
    public void set(Object value){
        this.reqres.data(value);
    }
    
    public void set(String stringIndex,Object value){
        this.reqres.data(stringIndex,value);
    }

    public void set(int index,Object value){
        this.reqres.data(index,value);
    }
    
    public String json(){
        return this.reqres.data.json();
    }
    
    public boolean isNumeric(Object value){
        return Model.isNumeric(value);
    }
    
    public boolean isInteger(Object value){
        return Model.isInteger(value);
    }
    
    public <T> T lib(String lib){
        try{
            Class<?> c = Class.forName("_libs.kurmix."+lib);
            Object o = c.newInstance();
            return (T) o;
        }catch(Exception e){
            String[] er = {"202",lib,e.toString()}; 
            this.setKurmix(er);
        }
        return null;
    }
}
