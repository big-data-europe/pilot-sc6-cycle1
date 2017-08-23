package eu.bde.sc6.financial.ratios.base;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculator;
import info.aduna.iteration.Iterations;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResultHandlerBase;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public abstract class FinancialRatioCalculatorBase implements FinancialRatioCalculator {
    
    protected final String KAE_QUERY_TEMPLATE = ""
            + "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> "
            + "PREFIX owl:<http://www.w3.org/2002/07/owl#> "
            + "SELECT * WHERE { "
            + " <<financialRatio>> skos:prefLabel ?financialRatioLabel . "
            + " OPTIONAL { <<financialRatio>> skos:narrower+/skos:relatedMatch ?kae }"
            + " OPTIONAL { <<financialRatio>> owl:sameAs ?origFinancialRatio }"
            + "}";
    protected final URI BASE = new URIImpl("http://linkedeconomy.org/resource");
    protected final URI BASE_STATISTIC = new URIImpl(BASE.stringValue().concat("/statistic"));
    protected final URI BASE_AGGREGATE = new URIImpl(BASE.stringValue().concat("/aggregate"));
    
    protected SPARQLRepository dataRepository;
    protected SPARQLRepository pptRepository;
    protected URI financialRatio;
    protected Literal financialRatioLabel;
    protected URI origFinancialRatio;
    protected List<URI> kaes = new ArrayList<>();
    
    public FinancialRatioCalculatorBase(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository){
        this.dataRepository = dataRepository;
        this.pptRepository = pptRepository;
        this.financialRatio = financialRatio;
        try {
            this.init();
        } catch (RepositoryException | MalformedQueryException | QueryEvaluationException | TupleQueryResultHandlerException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    protected List<URI> getKaes(){
        return this.kaes;
    }
    
    protected URI createStatisticsURI(){
        return new URIImpl(BASE_STATISTIC.stringValue().concat("/").concat(UUID.randomUUID().toString()));
    }
    protected URI createAggregateURI(){
        return new URIImpl(BASE_AGGREGATE.stringValue().concat("/").concat(UUID.randomUUID().toString()));
    }    
    protected Literal now() throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = 
                DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return ValueFactoryImpl.getInstance().createLiteral(date2);
    }
    protected Literal dateTimeToDate(Literal dateTime){
        return new LiteralImpl(dateTime.stringValue().split("T")[0],XMLSchema.DATE);
    }
    private void init() throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
        RepositoryConnection repCon = null;
        try {
            repCon = this.pptRepository.getConnection();
            repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    KAE_QUERY_TEMPLATE.replaceAll("<financialRatio>", this.financialRatio.stringValue())
            ).evaluate(new TupleQueryResultHandlerBase(){
                @Override
                public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
                    origFinancialRatio = (URI)bindingSet.getValue("origFinancialRatio");
                    URI kae = (URI)bindingSet.getValue("kae"); 
                    if(!kaes.contains(kae)){ kaes.add(kae); }
                    financialRatioLabel = (Literal)bindingSet.getValue("financialRatioLabel");
                }
            });
        } finally {
            if(repCon!=null){
                repCon.close();
            }
        }
    }    
    /**
     * 
     * @param numerator (sum)
     * @param denominator (total)
     * @return 
     */
    protected Literal calculatePercentage(Literal numerator, Literal denominator){
        System.out.println("numerator: " + numerator);
        System.out.println("denominator: " + denominator);
        double percentage = 0;
        double numeratorDouble = numerator.doubleValue();
        double denominatorDouble = denominator.doubleValue();

        if (denominatorDouble != 0) {            
            percentage = (numeratorDouble / denominatorDouble);
            percentage = (double) Math.round(percentage * 10000) / 10000;            
        }
        //System.out.println("percentage: " + percentage);
        return ValueFactoryImpl.getInstance().createLiteral(percentage);
    }
}
