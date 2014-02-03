import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author siddarthr
 * Description: Servlet which handles requests to get pictures of paintings. Acts as the Controller in the MVC model.
 * Date Created: Jan 30th 2014
 */
@WebServlet(name = "PicServlet",
        urlPatterns = {"/getPic"})
public class PicServlet extends HttpServlet {

    PicModel ipm = null;  // The "business model" for this app

    @Override
    public void init() {
        ipm = new PicModel();
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
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        //redirect to landing page
        RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
        view.forward(request, response);
        
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
        // get the search parameter if it exists and replace whitespaces with + to ensure link doesn't break
        String search = request.getParameter("searchWord");
        search = search.replaceAll(" ", "+");

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        
        String nextView;
        /*
         * Check if the search parameter is present.
         * If it is not, then give the user instructions and prompt for a search
         * string.
         * If there is a search parameter, then do the search and return the result.
         */
        if (search.length() != 0) {
            // use model to do the search and choose the result view
            ipm.doImgSearch(search);
            
            //if pictures exists, then fetch
            if(!ipm.isEmptyImg()){
                request.setAttribute("pictureURL",ipm.interestingPictureSize((mobile) ? "mobile" : "desktop"));
                request.setAttribute("pictureTag", ipm.getPictureTag());
                nextView = "result.jsp";
            }
            //pictures don't exist, so send appropriate message
            else {
                request.setAttribute("pictureTag", "No images found");
                nextView = "result.jsp";
            }
                
        } else {
            // no search parameter so choose the result view
            request.setAttribute("pictureURL","");
            request.setAttribute("pictureTag", "Please enter a search term");
            nextView = "result.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
