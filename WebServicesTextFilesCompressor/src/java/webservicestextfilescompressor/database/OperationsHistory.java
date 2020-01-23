package webservicestextfilescompressor.database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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

    private static final long serialVersionUID = 1L;
    
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 12)
    @Column(name = "MODE")
    private String mode;
    @Size(max = 255)
    @Column(name = "INPUTFILE")
    private String inputfile;
    @Size(max = 255)
    @Column(name = "OUTPUTFILE")
    private String outputfile;

    public OperationsHistory() {
    }

    public OperationsHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getInputfile() {
        return inputfile;
    }

    public void setInputfile(String inputfile) {
        this.inputfile = inputfile;
    }

    public String getOutputfile() {
        return outputfile;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
