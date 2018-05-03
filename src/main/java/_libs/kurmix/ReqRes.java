/*===== Kurmix - Java =====                          _  __   www.kurmix.com   _      
* @author    Andree Ochoa <andlody@hotmail.com>     | |/ /   _ _ __ _ __ ___ (_)_  __
* @copyright 2017-2018 Andree Ochoa                 | ' / | | | '__| '_ ` _ \| \ \/ /
* @license   The MIT license                        | . \ |_| | |  | | | | | | |>  < 
* @version   1.0.0                                  |_|\_\__,_|_|  |_| |_| |_|_/_/\_\                                  
*/
package _libs.kurmix;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReqRes {    
    HttpServletRequest request;
    HttpServletResponse response;
    String view;
    String template;
    Data data;
    boolean band;
    boolean band2;
    
    public ReqRes(){
        this.template = "default";
        this.data = new Data();
        this.band = true;        
        this.band2 = false;
    }
    
    public ReqRes(HttpServletRequest r){ 
        this.data = (Data)r.getAttribute("data");
    }
    
    public String show() throws ServletException, IOException{
        if(this.view == null) return "";
        
        String url="view/"+this.view;
        
        if(this.template!=null){
            url = "view/_templates/"+this.template;            
        }                
        
        this.request.setAttribute("kurmi_body", this.view);
        
        //============= Vista JSP =============
        
        try{ 
            this.request.getRequestDispatcher(url+".jsp").include(this.request,this.response);
        }catch(Exception e){
            String[] er = {"401",url,this.view,e.toString()}; 
            this.error(er);
        }
        
        return "";
    }
    
    public void write(String value){
        try{
            PrintWriter out = this.response.getWriter();
            out.println(value);
        }catch(Exception e){}        
    }
    
    public void setRequest(HttpServletRequest request){
        this.request=request;
        this.request.setAttribute("data", this.data);
    }
    
    public HttpServletRequest getRequest(){
        return this.request;
    }
    
    public HttpServletResponse getResponse(){
        return this.response;
    }
    
    public void setResponse(HttpServletResponse response){
        this.response=response;
    }
       
    public void setView(String view){
        this.view=view;
        this.band = false;
    }
    
    public void setTemplate(String temp){
        this.template=temp;
    }
    
    public void redirect(String url,int value){
        if(url.trim().equals("")) url = "index/index";
        try{                   
            if(value==1)
                this.write("<script>window.location = \"?k="+url+"\";</script>");
            else
                this.request.getRequestDispatcher("/index.jsp?k="+url).forward(this.request,this.response); 
                   
        }catch(Exception e){
            String[] er = {"402",url,e.toString()}; 
            this.error(er);
        }
    }
    
    public String getParameter(String name){
        return this.request.getParameter(name);
    }
    
    public void data(Object value){
        this.data.set(value);
        this.request.setAttribute("data", this.data);
    }
    public void data(String stringIndex,Object value){
        this.data.set(stringIndex,value);
        this.request.setAttribute("data", this.data);
    }
    public void data(int index,Object value){
        this.data.set(index,value);
        this.request.setAttribute("data", this.data);
    }
    
    public boolean getBand(){
        return this.band;
    }
        
    public boolean getBand2(){
        return this.band2;
    }
    
    public void error404(){
        try {
            this.setView("_templates/_404");
            this.setTemplate(null);
            this.show();
        } catch (Exception ex) { }
    }

    public void error(String[] v){        
        this.band=false;
        if(this.band2) return;
        this.band2 = true;
        if(!_config.Config.DEV){
            this.error404();
            return;
        }
        
        int e = Integer.parseInt(v[0]);
        switch(e){
            case 101:
                this.write("ERROR: En el Router, no existe la accion:<strong> "+v[1]+" </strong> en el controlador:<strong> "+v[2]+"</strong><br>");
                break;
            case 102:
                this.write("ERROR: En el Router, error al ejecutar la accion:<strong> "+v[1]+" </strong> en el controlador:<strong> "+v[2]+"</strong><br>"+v[3]+"<br>");
                break;
            case 103:
                this.write("ERROR: En el Router, no existe el controlador:<strong> "+v[1]+" </strong><br>"+v[2]+"<br>");
                break;
            case 201:
                this.write("ERROR: En el controlador al instanciar el modelo:<strong> "+v[1]+" </strong> - Posiblemente no existe.<br>"+v[2]+"<br>");
                break;
            case 202:
                this.write("ERROR: En el controlador al instanciar la libreria:<strong> "+v[1]+" </strong> Posiblemente no existe.<br>"+v[2]+"<br>");
                break;
            case 301:
                this.write("ERROR: En el modelo, al realizar la consulta:<strong> "+v[1]+" </strong>.<br>"+v[2]+"<br>");
                break;
            case 302:
                this.write("ERROR: En el modelo al llamar:<strong> this.table("+v[1]+",\""+v[2]+"\") </strong><br>"+v[3]+"<br>");
                break;
            case 303:
                this.write("ERROR: En el modelo al llamar:<strong> this.table(\""+v[1]+"\") </strong><br>"+v[2]+"<br>");
                break;
            case 304:
                this.write("ERROR: En el modelo al llamar:<strong> this.table("+v[1]+") </strong><br>"+v[2]+"<br>");
                break;
            case 305:
                this.write("ERROR: En el modelo al llamar:<strong> this.table("+v[1]+","+v[2]+") </strong><br>"+v[3]+"<br>");
                break;
            case 306:
                this.write("ERROR: En el modelo al llamar:<strong> "+v[1]+"</strong><br>"+v[2]+"<br>");
                break;
            case 401:
                this.write("ERROR: En la vista, Template:<strong> "+v[1]+" </strong> | Vista: <strong>"+v[2]+"</strong><br>"+v[3]+"<br>");
                break;
            case 402:
                this.write("ERROR: En la vista, Al redireccionar:<strong> "+v[1]+" </strong><br>"+v[2]+"<br>");
                break;
            default:
                this.write("ERROR: No ubicado.");
        }
    }
}
