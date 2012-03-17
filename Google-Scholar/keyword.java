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
public class keyword extends HttpServlet {

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
         PrintWriter out = response.getWriter();
        try {
              response.setContentType("text/html;charset=UTF-8");
         String txtWebMsg=request.getParameter("txtweb-message");
         String txtmob=request.getParameter("txtweb-mobile");
         String txtagg=request.getParameter("txtweb-aggid");
         String para=request.getParameter("no");
         String abs=request.getParameter("abs");
         String responseString=getresult(txtWebMsg,txtmob,txtagg,para,abs);
        responseString = "<html><head><meta name=\"txtweb-appkey\" content=\"bd0c2ef6-a18a-4df8-b502-b870a0bc9e09\" /></head><body>"+ responseString +"</body></html>";
        //response.setContentType("text/html;charset=UTF-8");

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

    String getresult(String txtWebMsg, String txtmob, String txtagg,String para,String abs) {
        String result="";

        try{
            String [] input=txtWebMsg.split(",");

            String author="";
            String keyword=URLEncoder.encode(input[0],"utf-8");
           
            String url="http://scholar.google.com/scholar?as_q="+keyword+"&num=20&btnG=Search+Scholar&as_epq=&as_oq=&as_eq=&as_occt=any&as_sauthors="+author+"&as_publication=&as_ylo=&as_yhi=&as_sdt=1.&as_sdtp=on&as_sdtf=&as_sdts=5&hl=en";

            Source res=new Source(new URL(url));

            List <Element> lt=res.getAllElementsByClass("gs_r");

            if(lt.size()==0)
            {
                StartTag st2=res.getFirstStartTag("p");
                if(st2==null)
                {
                    return "No result found check The spelling";
                }
                String txtweb=URLEncoder.encode(txtWebMsg,"utf-8");
                result="No result found"+"<br></br><br></br>";
                result=result+st2.getElement().getTextExtractor().toString();
                return result;
            }
            if( para!=null && Integer.parseInt(para)>=lt.size())
            {
                return " No More Results Available ";
            }
            Element e;
            if(para==null)
            {

                e=lt.get(0);

            }
            else
            {
                int para_no=Integer.parseInt(para);
                e=lt.get(para_no);
            }

            if(abs!=null)
            {
                Element e4=e.getFirstStartTag("font").getElement();
                String [] arr=(e4.getContent().toString()).split("<span");
                String content="<span"+arr[0]+arr[1];
                result="<p>Search content : <br></br><br></br>"+content+"</p><br></br>";

               String txtweb=URLEncoder.encode(txtWebMsg,"utf-8");
                if(para!=null)
            {
                String pa=URLEncoder.encode(""+(Integer.parseInt(para)+1),"utf-8");
                String pa1=URLEncoder.encode(para,"utf-8");

                result=result+"<br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no="+pa+"\"  class=\"txtweb-menu-for\">Next Result</a><br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no="+pa1+"\"  class=\"txtweb-menu-for\">Back to Same Result</a><br></br>";


            }
            else
            {
                result=result+"<br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no=1\" class=\"txtweb-menu-for\" >Next Result</a><br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"\" class=\"txtweb-menu-for\" >Back to Same Result</a><br></br>";

            }

            }
            else
            {

            Element e2=e.getFirstElementByClass("gs_rt");
            result="<p>"+"( Title ) : "+e2.getTextExtractor().toString()+"<br></br>";

            Element e1=e.getFirstElementByClass("gs_ggs gs_fl");

            if(e1!=null)
            {
                StartTag st=e1.getFirstStartTag("a");
                String link=st.getAttributeValue("href");

                String inside=st.getElement().getTextExtractor().toString();

                result=result+"( Link For full Paper ) : "+link+"<br></br> ( "+inside+" )<br></br>";
            }
            else
            {
               StartTag st=e2.getFirstStartTag("a");
                String link=st.getAttributeValue("href");

                result=result+"Link For full Paper : "+link+"<br></br>";
            }
            Element e5=e.getFirstStartTag("font").getElement();
            Element e3=e5.getFirstElementByClass("gs_fl");
            StartTag st1=e3.getFirstStartTag("a");
            if(st1!=null)
            {
                String citation=st1.getElement().getTextExtractor().toString();
                result=result+" "+citation+"<br></br>";
            }

            result=result+"<p>";

            String txtweb=URLEncoder.encode(txtWebMsg,"utf-8");
            if(para!=null)
            {
                String pa=URLEncoder.encode(""+(Integer.parseInt(para)+1),"utf-8");
                String pa1=URLEncoder.encode(para,"utf-8");

                result=result+"<br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no="+pa+"\"  class=\"txtweb-menu-for\">Next Result</a><br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no="+pa1+"&abs=true\" class=\"txtweb-menu-for\">Search Abstract</a>";

            }
            else
            {
                result=result+"<br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&no=1\" class=\"txtweb-menu-for\" >Next Result</a><br></br>";
                result=result+"<a href=\"http://www.tomar-scholar.appspot.com/keyword?txtweb-message="+txtweb+"&abs=true\" class=\"txtweb-menu-for\" >Search Abstract</a>";
            }


            }

        }catch(Exception e){

            result="Usage Instructions :<br></br>" +
                    "@gscholar authorname,publicationname,keyword<br></br>" +
                    "@gscholar.author authorname,keyword<br></br>" +
                    "@gscholar.pub publicationname,keyword<br></br>" +
                    "@gscholar.keyword keyword";
        }
        return result;
    }

}
