package vg.sales.weather.webservice;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.GZIP;

import org.jboss.resteasy.annotations.cache.Cache;
import vg.sales.weather.datasource.SalesDataSetImpl;
import vg.sales.weather.model.Sales;

/**
 *
 * @author vagner
 */
@Path("/sales")
public class SalesResource {
        
    static String xmlString = null;
    SalesDataSetImpl salesDataSet;

    public SalesResource() {
        this.salesDataSet = new SalesDataSetImpl();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Cache(mustRevalidate = true)
    @GZIP
    public ArrayList<Sales> getSales() {
        ArrayList<Sales> salesList = null;

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Fortaleza");
            System.out.println("Geting Sales... " + ZonedDateTime.now(fusoHorarioDeSaoPaulo));
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatador = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
            today.format(formatador); //08/04/2014
            salesList = (ArrayList<Sales>) salesDataSet.listSales(Date.valueOf(today), Date.valueOf(today));            
 
        return salesList;
    } 
}
