package webservicestextfilescompressor.webmodel;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
public class WrongFilePassedException extends Exception{
    
    /**
     * 
     * @param exceptionMessage message of exception, which will be used to initialize Exception
     */
    public WrongFilePassedException(String exceptionMessage) {
        
        super(exceptionMessage);
    }    
}
