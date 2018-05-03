package controller;

import _libs.kurmix.Controller;

public class index_controller extends Controller{
     
    public void index(){
      
    } 
    
    public void hola(){
        this.set("Hola mundo..!");
    }
    
    public void enviar_datos_vista(){
        this.set("num",123456);
        this.set("name","Andree Ochoa");
        this.set("mail","aochoa@kurmix.org");
        this.set("json", this.json());
    }
    
    public void otra_plantilla(){
        this.view("index/index");
        this.template("esmeralda");
    }
}
