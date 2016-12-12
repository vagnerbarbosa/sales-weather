package vg.sales.weather.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vagner
 */
@Entity
@XmlRootElement(name = "salesOrder")
public class SalesOrder implements Serializable {

    @Id
    @Column(name = "idpedidovenda")
    private Integer idSalesOrder; //idpedidovenda
    @Column(name = "numerofilial")
    private Integer branchNumber; //numerofilial
    @Column(name = "fantasia")
    private String trade; //fantasia
    @Column(name = "nome")
    private String clientName; //nome    
    @Column(name = "datamovimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date movingDate; //datamovimento  
    @Column(name = "situacaopedidoloja")
    private String shopRequestSituation; //situacaopedidoloja  
    @OneToMany
    @JoinColumn(name = "prodId") 
    private Set<Product> products;
    
    public SalesOrder() {
    }

    @XmlElement
    public Integer getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(Integer branchNumber) {
        this.branchNumber = branchNumber;
    }

    @XmlElement
    public Integer getIdSalesOrder() {
        return idSalesOrder;
    }

    public void setIdSalesOrder(Integer idSalesOrder) {
        this.idSalesOrder = idSalesOrder;
    }

    @XmlElement
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    @XmlElement
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @XmlElement
    public Date getMovingDate() {
        return movingDate;
    }

    public void setMovingDate(Date movingDate) {
        this.movingDate = movingDate;
    }

    @XmlElement
    public String getShopRequestSituation() {
        return shopRequestSituation;
    }

    public void setShopRequestSituation(String shopRequestSituation) {
        this.shopRequestSituation = shopRequestSituation;
    }

    @XmlElement
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
    

}