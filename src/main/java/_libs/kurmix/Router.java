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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@WebServlet(name = "Router", urlPatterns = {"/Router"})
public class Router extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String k = request.getParameter("k");
       
        if(k==null) k="";
        
        PrintWriter out = response.getWriter();
 
        String a = main(k,request,response);      
            
        if(a!=null) out.println(a);
    }
    
    protected static String main(String k,HttpServletRequest request, HttpServletResponse response){
        String controller = "index";
        String action = "index";
        
        String[] a = k.split("/");
        
        if(a.length>0) if(!a[0].trim().equals("")) controller = a[0];
        if(a.length>1) action = a[1];
        
        controller.index_controller u = new controller.index_controller();
        u.setKurmix(request, response);
            
        try{
            Class<?> c = Class.forName("controller."+controller+"_controller");
            Object o = c.newInstance();
            Method method;
            
            String z = methodd(c,action);
            
            if(z.split(":")[0].equals("0") && !action.equals("index")){
                String[] er = {"101",action,controller}; 
                u.setKurmix(er); return"";
            }          
                        
            try {                
                
                u.start();               
                
                method = o.getClass().getMethod("setKurmix",HttpServletRequest.class,HttpServletResponse.class);
                method.invoke(o,request,response);  
                
                method = o.getClass().getMethod("before",null);
                method.invoke(o,null);  
                 
                int n = Integer.parseInt(z.split(":")[1]);
                String[] params = action.equals("index")? null:new String[n];
                Class[] types   = action.equals("index")? null:new Class[n];
                
                int j=2;
                for (int i = 0; i < n; i++) {   
                    params[i] = (j<a.length)?a[j]:"";  
                    types[i]=String.class;
                    j++;
                }                              
                                
                method = o.getClass().getMethod(action,types);                  
                method.invoke(o,params);  
                                
                method = o.getClass().getMethod("after",null);
                method.invoke(o,null); 
                  
                u.finish();
                  
                method = o.getClass().getMethod("setKurmix",String.class);
                return (String)method.invoke(o,controller+"/"+action);
                
            }catch (Exception e) { 
               String[] er = {"102",action,controller,e.toString()}; 
               method = o.getClass().getMethod("setKurmix",null);
               if(!(Boolean)method.invoke(o,null)) u.setKurmix(er); 
               return"";
            }
        }catch(Exception e){ 
            String[] er = {"103",controller,e.toString()}; 
            u.setKurmix(er); return"";
        }
    }  
  
    public static String methodd(Class clazz, String methodName) {
        int r = 0;
        int n = -1;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                r = 1;                
                n = method.getParameterCount();
                break;
            }
        }
        return ""+r+":"+n;
    }
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
