package webservicestextfilescompressor.webmodel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 * Tests for all public methods in Model class
 */
public class WebFilesCompressorTest {
    /**
     * fileToCompressName - name of file which will be compressed
     */
    private static String fileToCompressName;
    /**
     * compressedOutFileName - name of file to save compressed content
     */
    private static String compressedOutFileName;
    /**
     * fileToDecompressName - name of file which will be decompressed
     */
    private static String fileToDecompressName;
    /**
     * decompressedOutFileName - name of file to save decompressed content
     */
    private static String decompressedOutFileName;
    /**
     * compressor - object of Model class to compress file
     */
    private CompressorInterface compressor;
    /**
     * decompressor - object of Model class to decompress file
     */
    private CompressorInterface decompressor;
    
    public WebFilesCompressorTest() {
        
        WebFilesCompressorTest.fileToCompressName = "test.txt";
        WebFilesCompressorTest.compressedOutFileName = "out.txt";
        WebFilesCompressorTest.fileToDecompressName = "out.txt";
        WebFilesCompressorTest.decompressedOutFileName = "decompressed.txt";
    }
    
    @Before
    public void setUp() {
        
        compressor = new WebFilesCompressor(fileToCompressName, "", compressedOutFileName);
        decompressor = new WebFilesCompressor("", fileToDecompressName, decompressedOutFileName);  
    }

    /**
     * Test of compressFile method, of class Model.
     * Tests if compression of example file is succesfully
     */
    @Test
    public void testCompressFile() throws Exception {
       
        boolean expResult = true;
        boolean result = compressor.compressFile();
        assertEquals(expResult, result);
    }

    /**
     * Test of decompressFile method, of class Model.
     * Tests if decompression of example file is succesfully
     */
    @Test
    public void testDecompressFile() throws Exception {
       
        boolean expResult = true;
        boolean result = decompressor.decompressFile();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of compressFile method, of class Model
     * Tests if method handles passing to method non-existent file
     * @throws java.io.IOException - thrown when error occured while reading/writing to file, mustn't occur here
     */
    @Test
    public void testCompressFileWithNonExistentFile() throws IOException {
        
        CompressorInterface compressor = new WebFilesCompressor("nonexistentfile.txt", "", compressedOutFileName);
        
        try {
            
            compressor.compressFile();  
            fail("Exception should be thrown when trying to compress un-existent file");
        } catch(WrongFilePassedException e) {
            
        }     
    }
    
     /**
     * Test of decompressFile method, of class Model
     * Tests if method handles passing to method non-existent file
     * @throws java.io.IOException - thrown when error occured while reading/writing to file, mustn't occur here
     */
    @Test
    public void testDecompressFileWithNonExistentFile() throws IOException {
        
        CompressorInterface decompressor = new WebFilesCompressor("", "nonexistentfile.txt", decompressedOutFileName);
        
        try {
            
            decompressor.decompressFile();  
            fail("Exception should be thrown when trying to decompress un-existent file");
        } catch(WrongFilePassedException e) {
            
        }     
    }
    /**
     * Test of compressFile method, of class Model
     * Tests if method handles passing to method null file name
     * @throws java.io.IOException - thrown when error occured while reading/writing to file, mustn't occur here
     */ 
    @Test
    public void testCompressFileWithNullFileName() throws IOException {
        
        CompressorInterface compressor = new WebFilesCompressor(null, "", compressedOutFileName);
        
        try {
            
            compressor.compressFile();  
            fail("Exception should be thrown when trying to compress null file");
        } catch(WrongFilePassedException e) {
            
        }            
    }
    
     /**
     * Test of decompressFile method, of class Model
     * Tests if method handles passing to method non-existent file
     * @throws java.io.IOException - thrown when error occured while reading/writing to file, mustn't occur here
     */
    @Test
    public void testDecompressFileWithNullFileName() throws IOException {
        
        CompressorInterface decompressor = new WebFilesCompressor("", null, decompressedOutFileName);
        
        try {
            
            decompressor.compressFile();  
            fail("Exception should be thrown when trying to decompress null file");
        } catch(WrongFilePassedException e) {
            
        }            
    }
    /**
     * This is not pure unit test - Test both compressFile and decompressFile methods, of class Model
     * Tests if decompression of file which was compressed by compressFile method by decompressFile method gets original file
     * @throws java.io.IOException - thrown when error occured while reading/writing to file
     */
    @Test
    public void testIfDecompressedCompressedFileIsTheSameAsOriginal() throws IOException {
        
        try {
            
            boolean compressedSuccessfully = compressor.compressFile();
            boolean decompressedSuccessfully = decompressor.decompressFile();            
        } catch(WrongFilePassedException e) {
            
            fail("Failed to compress and then decompress this file");
        }
        
        byte[] originalFile = Files.readAllBytes(Paths.get(fileToCompressName));
        byte[] decompressedFile = Files.readAllBytes(Paths.get(decompressedOutFileName));
        
        boolean expResult = true;
        assertEquals(expResult, Arrays.equals(originalFile, decompressedFile));        
    }    
}
