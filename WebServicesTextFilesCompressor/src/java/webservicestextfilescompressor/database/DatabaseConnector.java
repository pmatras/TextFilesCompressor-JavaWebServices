package webservicestextfilescompressor.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
public class DatabaseConnector {
    
    private static Context context = null;
    private static UserTransaction userTransaction = null;
    private static EntityManager entityManager = null;
    
    public DatabaseConnector() {
        if(entityManager == null) {
            if(initializeConnectionWithDatabase()) {
                System.out.println("Connection with database initialized successfully!");
            }
        }
    }
    
    private boolean initializeConnectionWithDatabase() {
        try {
            context = new InitialContext();
            userTransaction = (UserTransaction) context.lookup("java:comp/env/UserTransaction");
            entityManager = (EntityManager) context.lookup("java:comp/env/persistence/LogicalName");   
            return true;
        } catch(Exception e) {
            System.err.println("Exception occured, while initialize connection with database, reason: " + e.getMessage());
        }
        
        return false;       
    }
    
    public boolean insertIntoDatabase(final String mode, final String inputFileName, final String outputFileName) {
        OperationsHistory operationsHistory = new OperationsHistory(mode, inputFileName, outputFileName);
        try {
            persist(operationsHistory);
            return true;            
        } catch(Exception e) {
            System.err.println("Exception occured while inserting data into database, reason: " + e.getMessage());
        }
        
        return false;
    }
    
   public void persist(Object object) {  
       try {
           userTransaction.begin();           
           entityManager.persist(object);
           userTransaction.commit();
       } catch (Exception e) {
            throw new RuntimeException(e);
       }
   }
}