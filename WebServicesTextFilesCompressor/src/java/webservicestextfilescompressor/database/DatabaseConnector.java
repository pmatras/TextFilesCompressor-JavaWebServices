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
    
    /**
     * context - object of Context class from JPA
     */
    private static Context context = null;
    /**
     * userTransaction - object of UserTransaction class from JPA
     */
    private static UserTransaction userTransaction = null;
    /**
     * entityManager - object of EntityManager class from JPA
     */
    private static EntityManager entityManager = null;
    
    /**
     * constructor of class, which initializes connection with database and creates new table in it,
     * only when previously it was not made
     */
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
    
    /**
     * Initializes connection with database
     * @return true, when connection initialized successfully, false otherwise
     */
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
    
    /**
     * Creates table in database if already does not exist
     * @return true, if table created successfully, false otherwise
     */
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
    
    /**
     * Inserts data into database
     * @param mode mode of operation
     * @param inputFileName path to input file
     * @param outputFileName path to output file
     * @return 
     */
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
    
    /**
     * Method which persists data in database, served by JPA
     * @param object object which will be persisted in database
     */
    public void persist(Object object) {  
        try {
            userTransaction.begin();      
            entityManager.persist(object);
            userTransaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method for obtaining all records from database
     * @return list of OperationsHistory objects, which are stored in database
     */   
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