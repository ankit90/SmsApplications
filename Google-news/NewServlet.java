/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import javax.lang.model.util.Elements;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.htmlparser.jericho.*;

/**
 *
 * @author ankit
 */
public class NewServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         String txtWebMsg=request.getParameter("txtweb-message");
         String txtmob=request.getParameter("txtweb-mobile");
        String txtagg=request.getParameter("txtweb-aggid");
         String responseString=getresult(txtWebMsg,txtmob,txtagg);
        responseString = "<html><head><meta name=\"txtweb-appkey\" content=\"f2e49d36-db5f-46b3-9775-f3fbd336e188\" /></head><body>"+ responseString +"</body></html>";
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.print(responseString);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



    String form_result(Source resPage)
    {
        String result="";
        try{
       
        Element t=resPage.getElementById("top-stories");
        List <Element> lt=t.getAllElementsByClass("esc-lead-article-title-wrapper");
                int i=0;
             while(i<lt.size() && i < 6)
             {
                 //StartTag gt = lt.get(i).getFirstStartTag("span");
                    result=result + (i+1)+". "+lt.get(i).getTextExtractor().toString() + "<br></br>";

                    i++;
             }
        }catch(Exception e){ result="Omething went wrong !! Your error is logged it will be taken are of";}
        return result;
    }
    String getresult(String msg,String mob,String gg) throws IOException
{
        String result="";
        Source resPage;
        try {
            msg=msg.trim();
            
            if(msg.equalsIgnoreCase("options"))
            {
                result= " Welcome to top news <br></br>" +
                    " @topnews - for top news<br></br>" +
                    " @topnews n - for top national news<br></br>" +
                    " @topnews t - for top science nd tech news<br></br>" +
                    " @topnews s - for top sports news<br></br>" +
                    " @topnews e - for top entertinment news<br></br>" +
                    " @topnews w - for top world news<br></br>" +
                    " @topnews b - for top buisness news<br></br>" +
                    " @topnews h - for helth news <br></br>" +
                    " @topnews mp - for most popular news <br></br>" +
                    " @topnews sp - for spotlight news<br></br>" +
                    " @topnews help - for menu";
                
            }
            else if(msg.equalsIgnoreCase("n"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=n&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("t"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=t&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("e"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=e&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("s"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=s&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("h"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=m&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("b"))
            {

                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=b&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("w"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=w&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("sp"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=ir&ict=ln"));
                result=form_result(resPage);
            }
            else if(msg.equalsIgnoreCase("mp"))
            {
                resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=po&ict=ln"));
                result=form_result(resPage);
            }
            else
            {
            result= " Welcome to top news <br></br>" +
                    " @topnews - for top news<br></br>" +
                    " @topnews n - for top national news<br></br>" +
                    " @topnews t - for top science nd tech news<br></br>" +
                    " @topnews s - for top sports news<br></br>" +
                    " @topnews e - for top entertinment news<br></br>" +
                    " @topnews w - for top world news<br></br>" +
                    " @topnews b - for top buisness news<br></br>" +
                    " @topnews h - for helth news <br></br>" +
                    " @topnews mp - for most popular news <br></br>" +
                    " @topnews sp - for spotlight news<br></br>"+
                       " @topnews help - for menu";
            }

        }catch(Exception e)
        {
            resPage=new Source(new URL("http://news.google.com/news/section?pz=1&cf=all&ned=in&topic=t&ict=ln"));
                result=form_result(resPage);
        }

        return result+"<br></br>Reply @topnews options for more options";
}
}

