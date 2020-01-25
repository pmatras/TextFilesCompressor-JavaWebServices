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
    
    public boolean insertIntoDatabase(final String mode, final String inputFileName, final String outputFileName) {
        OperationsHistory operationsHistory = new OperationsHistory(mode, inputFileName, outputFileName);
        try {
            persist(operationsHistory);
            return true;            
        } catch(Exception e) {
            System.err.println("Exception occured while inserting data into database, reason: " + e.getMessage());
            return false;
        }
    }

   public void persist(Object object) {  
       try {
           Context context = new InitialContext();
           UserTransaction userTransaction = (UserTransaction) context.lookup("java:comp/env/UserTransaction");
           userTransaction.begin();
           EntityManager entityManager = (EntityManager) context.lookup("java:comp/env/persistence/LogicalName");
           entityManager.persist(object);
           userTransaction.commit();
       } catch (Exception e) {
            throw new RuntimeException(e);
       }
   }
}