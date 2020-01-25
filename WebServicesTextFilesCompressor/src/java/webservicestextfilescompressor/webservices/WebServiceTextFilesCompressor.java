package webservicestextfilescompressor.webservices;

import java.io.IOException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import webservicestextfilescompressor.database.DatabaseConnector;
import webservicestextfilescompressor.webmodel.Mode;
import webservicestextfilescompressor.webmodel.SingleInstanceOfWebModelGuard;
import webservicestextfilescompressor.webmodel.WebFilesCompressor;
import webservicestextfilescompressor.webmodel.WrongFilePassedException;

/**
 *
 * @author Piotr Matras
 * @version 1.0 
 * Web service which serves methods for compressing and decompresing files and getting history of operations from database 
 */
@WebService(serviceName = "WebTextFilesCompressor")
public class WebServiceTextFilesCompressor {
    
    /**
     * Web service operation which serves ability to compress files and also inserts history of compress operations into database
     */
    @WebMethod(operationName = "compressFile")
    public String compressFile(@WebParam(name = "inputFile") final String inputFile, @WebParam(name = "outputFile") final String outputFile) {
        WebFilesCompressor compressor = SingleInstanceOfWebModelGuard.getFilesCompressor(inputFile, "", outputFile);
        DatabaseConnector databaseConnector = new DatabaseConnector();
        
        String message = "";
        try {
            if(compressor.compressFile()) {
                message += inputFile + " was successfuly compressed into file " + outputFile;
                databaseConnector.insertIntoDatabase(Mode.COMPRESS.toString().toLowerCase(), inputFile, outputFile);
            }               
        } catch(WrongFilePassedException e) {
            message += "File to compress not found: " + e.getMessage();                  
        } catch(IOException e) {
            message += "Problem occured while compressing file: " + e.getMessage();    
        }
        
        return message;
    }

    /**
     * Web service operation which serves ability to decompress files and also inserts history of decompress operations into database
     */
    @WebMethod(operationName = "decompressFile")
    public String decompressFile(@WebParam(name = "inputFile") final String inputFile, @WebParam(name = "outputFile") final String outputFile) {
        WebFilesCompressor decompressor = SingleInstanceOfWebModelGuard.getFilesCompressor("", inputFile, outputFile);
        DatabaseConnector databaseConnector = new DatabaseConnector();
        
        String message = "";
        try {
            if(decompressor.decompressFile()) {
                message += inputFile + " was successfuly decompressed into file " + outputFile;
                databaseConnector.insertIntoDatabase(Mode.DECOMPRESS.toString().toLowerCase(), inputFile, outputFile);
            }               
        } catch(WrongFilePassedException e) {
            message += "File to decompress not found: " + e.getMessage();                  
        } catch(IOException e) {
            message += "Problem occured while decompressing file: " + e.getMessage();    
        }
        
        return message;
    }
}
