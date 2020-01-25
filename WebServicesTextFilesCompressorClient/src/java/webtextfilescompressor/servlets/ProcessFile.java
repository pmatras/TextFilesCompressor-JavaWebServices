package webtextfilescompressor.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import webtextfilescompressor.webservicesclient.WebTextFilesCompressor;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
public class ProcessFile extends HttpServlet {
    
    /**
     * mode - mode of file compressor (compress/decompress)
     */
    private String mode = "";
    /**
     * inputFileName - path to file which will be compressed/decompressed
     */
    private String inputFileName = "";
    /**
     * outputFileName - path to File in which compressed/decompressed input file will be saved
     */
    private String outputFileName = "";

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/TextFilesCompressor/WebTextFilesCompressor.wsdl")
    private WebTextFilesCompressor service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        this.mode = request.getParameter("mode");
        this.inputFileName = request.getParameter("inputFile");
        this.outputFileName = request.getParameter("outputFile");
        
        PrintWriter out = response.getWriter();
        
        Cookie[] cookies = request.getCookies();
        String previousMode = "";
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("prevMode")) {
                    previousMode = cookie.getValue();
                    break;
                }                
            }            
        }
        
        if(this.mode.length() == 0 || this.inputFileName.length() == 0 || this.outputFileName.length() == 0) {
            response.sendError(response.SC_BAD_REQUEST, "Wrong parameters passed!");
        } else {
            if(this.mode.equals("compress")) {
                String message = compressFile(inputFileName, outputFileName);
                out.println("<html>\n<body>\n<div> File <b>" + message + " </b>\n</div>\n</body>\n</html>");             
            } else if(mode.equals("decompress")) {
                String message = decompressFile(inputFileName, outputFileName);
                out.println("<html>\n<body>\n<div> File <b>" + message + " <b>\n</div>\n</body>\n</html>");
            } else {
                out.println("<html>\n<body>\n<div>Wrong working mode passed! Try again...</div>\n</body>\n</html>");
            }
        }   
        
        if(!previousMode.equals("")) {
            out.println("<html><body><br><br><div> Mode of previous operation request was: <b>" + previousMode + "</b></div></body></html>");            
        }
        
        Cookie cookie = new Cookie("prevMode", mode.toLowerCase());
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }
    
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
        return "WebTextFilesCompressor";
    }
    
    /**
     * Method for compressing file, which uses functionality served by WebService
     * @param inputFile
     * @param outputFile
     * @return a String containing response message from WebService's compress method 
     */
    private String compressFile(String inputFile, String outputFile) {
        webtextfilescompressor.webservicesclient.WebServiceTextFilesCompressor port = service.getWebServiceTextFilesCompressorPort();
        
        return port.compressFile(inputFile, outputFile);
    }
    
    /**
     * Method for decompressing file, which uses functionality served by WebService
     * @param inputFile
     * @param outputFile
     * @return a String containing response message from WebService's decompress method 
     */

    private String decompressFile(String inputFile, String outputFile) {
        webtextfilescompressor.webservicesclient.WebServiceTextFilesCompressor port = service.getWebServiceTextFilesCompressorPort();
       
        return port.decompressFile(inputFile, outputFile);
    }
}