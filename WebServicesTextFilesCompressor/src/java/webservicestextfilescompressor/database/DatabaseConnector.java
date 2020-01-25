package webservicestextfilescompressor.database;

import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
            if(createTableInDatabase()) {
                System.out.println("Table created successfully!");
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
    
    private boolean createTableInDatabase() {
        try {
            userTransaction.begin();
            entityManager.createNativeQuery("CREATE TABLE OperationsHistory (id int NOT NULL, mode VARCHAR(12),"
                    + " inputFile VARCHAR(255), outputFile VARCHAR(255), PRIMARY KEY(id))");
            userTransaction.commit();
            return true;
        } catch(Exception e) {
            System.err.println("Exception occured while creating table in database, reason: " + e.getMessage());            
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
   
   public List<OperationsHistory> getAllRecordsFromDatabase() {
       List<OperationsHistory> operationsHistoryList = new ArrayList<>();
       
       try {
           Query query = entityManager.createNamedQuery("OperationsHistory.findAll");
           operationsHistoryList = query.getResultList();
       } catch(Exception e) {
           System.err.println("Exception occured while getting all records from database, reason: " + e.getMessage());
       }
       
       return operationsHistoryList;
   }
}