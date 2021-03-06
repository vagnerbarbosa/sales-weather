package riodopeixe.rest.dataset;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import riodopeixe.rest.model.CellPhone;
import riodopeixe.rest.model.Invoice;
import riodopeixe.rest.model.Supplier;

/**
 * Classe de pesistência para objetos do tipo Invoice.
 *
 * @author Vagner Barbosa (contato@vagnerbarbosa.com)
 *
 * @since 24/02/2017
 *
 * @version 2.0
 */
public class InvoiceDataSetImpl implements InvoiceDataSet {

    private final EntityManager MANAGER;
    private final TransactionManager TRANSACTION;

    public InvoiceDataSetImpl() {
        MANAGER = Persistence.createEntityManagerFactory("SisNota").createEntityManager();
        this.TRANSACTION = com.arjuna.ats.jta.TransactionManager.transactionManager();
    }

    /**
     *
     * @param invoice
     */
    @Override
    public void setInvoice(Invoice invoice) {
        try {        
        TRANSACTION.begin();
        Supplier sup = MANAGER.getReference(Supplier.class, invoice.getCnpjFornecedor().getId());
        MANAGER.merge(sup);
        invoice.setCnpjFornecedor(sup);
        MANAGER.persist(invoice); 
        MANAGER.flush();
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(InvoiceDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    /**
     *
     * @param id
     */
    @Override
    public void removeInvoice(Integer id) {
        try {
        TRANSACTION.begin();
        MANAGER.remove(MANAGER.find(Invoice.class, id));
        MANAGER.flush();
        MANAGER.clear();         
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Invoice getInvoiceById(Integer id) {
        Invoice invoice = null;
        try {        
        TRANSACTION.begin();
        invoice = MANAGER.find(Invoice.class, id);
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoice;        
    }

    /**
     *
     * @param imei
     * @return
     */
    @Override
    public Invoice getInvoiceByImei(String imei) {
        Invoice invoice = null;
        try {        
        TRANSACTION.begin();
        String jpql = "SELECT notafiscal.id, notafiscal.dataEmissao, notafiscal.dataEntrada, notafiscal.id_fornecedor, notafiscal.numero, fornecedor.cnpj, fornecedor.ie, fornecedor.uf, fornecedor.bairro, fornecedor.cidade, fornecedor.endereco, fornecedor.numero, fornecedor.nomeFornecedor, celular.idcelular, celular.idproduto, celular.idgradex, celular.idgradey, celular.descricao, imei_por_celular.imei FROM notafiscal INNER JOIN fornecedor ON (fornecedor.id = notafiscal.id_fornecedor) INNER JOIN notafiscal_celular ON (notafiscal.id = notafiscal_celular.invoices_id) INNER JOIN celular ON (celular.idcelular = notafiscal_celular.cellphone_id) INNER JOIN imei_por_celular ON (imei_por_celular.cellphone_idcelular = celular.idcelular) WHERE imei_por_celular.imei iLIKE :imei";
        invoice = (Invoice) MANAGER.createNativeQuery(jpql, Invoice.class).setParameter("imei", imei).getSingleResult();
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return invoice;
    }

    @Override
    public Invoice getInvoiceByNumber(Integer number) {
        Invoice invoice = null;
        try {        
        TRANSACTION.begin();
        String jpql = "SELECT notafiscal.id, notafiscal.dataEmissao, notafiscal.dataEntrada, notafiscal.id_fornecedor, notafiscal.numero, fornecedor.cnpj, fornecedor.ie, fornecedor.uf, fornecedor.bairro, fornecedor.cidade, fornecedor.endereco, fornecedor.numero, fornecedor.nomeFornecedor, celular.idcelular, celular.idproduto, celular.idgradex, celular.idgradey, celular.descricao, imei_por_celular.imei FROM notafiscal INNER JOIN fornecedor ON (fornecedor.id = notafiscal.id_fornecedor) INNER JOIN notafiscal_celular ON (notafiscal.id = notafiscal_celular.invoices_id) INNER JOIN celular ON (celular.idcelular = notafiscal_celular.cellphone_id) INNER JOIN imei_por_celular ON (imei_por_celular.cellphone_id = celular.idcelular) WHERE notafiscal.numero = :numero";
        invoice = (Invoice) MANAGER.createNativeQuery(jpql, Invoice.class).setParameter("numero", number).getSingleResult();
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return invoice;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Invoice> getInvoices() {
        List<Invoice> invoice = null;
        try {        
        TRANSACTION.begin();
        Query query = MANAGER.createQuery("SELECT u FROM Invoice u");
        invoice = query.getResultList();
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }          
        return invoice;
    }

    /**
     *
     * @param invoice
     */
    @Override
    public void updateInvoice(Invoice invoice) {
        try {          
        TRANSACTION.begin();
        MANAGER.merge(invoice);
        TRANSACTION.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TonerDataSetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }

    @Override
    public Invoice getInvoiceByGenericSearch(String search) {
        Invoice invoice = null;
        try {
            if (Objects.equals(this.getInvoiceById(Integer.valueOf(search)).getId(), Integer.valueOf(search))) {
                invoice = this.getInvoiceById(Integer.valueOf(search));
            }
        } catch(NumberFormatException n) {
            if (this.getInvoiceByImei(search) != null) {
                invoice = this.getInvoiceByImei(search);
            }
        }
        return invoice;
    }

}
