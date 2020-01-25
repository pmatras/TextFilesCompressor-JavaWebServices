package webservicestextfilescompressor.database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
@Entity
@Table(name = "OPERATIONSHISTORY", catalog = "", schema = "DEV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperationsHistory.findAll", query = "SELECT o FROM OperationsHistory o")
    , @NamedQuery(name = "OperationsHistory.findById", query = "SELECT o FROM OperationsHistory o WHERE o.id = :id")
    , @NamedQuery(name = "OperationsHistory.findByMode", query = "SELECT o FROM OperationsHistory o WHERE o.mode = :mode")
    , @NamedQuery(name = "OperationsHistory.findByInputfile", query = "SELECT o FROM OperationsHistory o WHERE o.inputfile = :inputfile")
    , @NamedQuery(name = "OperationsHistory.findByOutputfile", query = "SELECT o FROM OperationsHistory o WHERE o.outputfile = :outputfile")})
public class OperationsHistory implements Serializable {
    
    /**
     * serialVersionUID - serial number of class
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * id - primary key in database table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    /**
     * mode - mode of operation
     */
    @Size(max = 12)
    @Column(name = "MODE")
    private String mode;
    /**
     * inputFile - path to input file
     */
    @Size(max = 255)
    @Column(name = "INPUTFILE")
    private String inputfile;
    /**
     * outputFile - path to outputFile
     */
    @Size(max = 255)
    @Column(name = "OUTPUTFILE")
    private String outputfile;
    
    public OperationsHistory() {        
    }
    
    /**
     * Constructor of class, which sets mode, inputFile and outputFile fields from passed arguments
     * @param mode mode of operation
     * @param inputFile path to input file
     * @param outputFile path to output file
     */

    public OperationsHistory(final String mode, final String inputFile, final String outputFile) {
        this.mode = mode;
        this.inputfile = inputFile;
        this.outputfile = outputFile;
    }
    
    /**
     * Constructor of class, which sets id field from passed argument
     * @param id number to set in id field
     */

    public OperationsHistory(Integer id) {
        this.id = id;
    }
    
    /**
     * Getter for id field
     * @return id
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Setter for id field
     * @param id number to set in id field
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Getter for mode field
     * @return mode
     */
    public String getMode() {
        return mode;
    }
    
    /**
     * Setter for mode field
     * @param mode string to set into mode field
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    /**
     * Getter for inputfile field
     * @return inputfile
     */
    public String getInputfile() {
        return inputfile;
    }
    
    /**
     * Setter for inputfile field
     * @param inputfile string to set into inputfile field
     */
    public void setInputfile(String inputfile) {
        this.inputfile = inputfile;
    }
    
    /**
     * Getter fir outputfile field
     * @return outputfile
     */
    public String getOutputfile() {
        return outputfile;
    }
    
    /**
     * Setter for outpurfile field
     * @param outputfile string to set into outputfile field
     */
    public void setOutputfile(String outputfile) {
        this.outputfile = outputfile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OperationsHistory)) {
            return false;
        }
        OperationsHistory other = (OperationsHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Operationshistory[ id=" + id + " ]";
    }

}
