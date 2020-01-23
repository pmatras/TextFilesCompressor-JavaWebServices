package webservicestextfilescompressor.webmodel;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
public class SingleInstanceOfWebModelGuard {
    
    /**
     * filesCompressor - instance of ServerFilesCompressor class
     */
    private static WebFilesCompressor filesCompressor;
    
    /**
     * 
     * @param fileToCompressName - name of file which will be compressed
     * @param fileToDecompressName - name of file which will be decompressed
     * @param outputFileName - name of file in which compressed/decompressed file will be saved
     * @return returns instance of ServerFilesCompressor class
     * Getter for field filesCompressor, it makes sure that instance of ServerFilesCompressor class
     * will be created only once during running of program
     */
    public static WebFilesCompressor getFilesCompressor(String fileToCompressName, String fileToDecompressName, String outputFileName) {
        
        if(filesCompressor == null) {
            filesCompressor = new WebFilesCompressor(fileToCompressName, fileToDecompressName, outputFileName);
        } else {
            filesCompressor.setFileToCompressName(fileToCompressName);
            filesCompressor.setFileToDecompressName(fileToDecompressName);
            filesCompressor.setOutputFileName(outputFileName);
        }
        
        return filesCompressor;
    }
    
}
