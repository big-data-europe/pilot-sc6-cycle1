package eu.bde.sc6.financial.ratios.base;

import eu.bde.sc6.financial.ratios.api.CalculationException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.TupleQueryResultHandlerBase;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public abstract class TotalIncomeFinancialRatioCalculator extends DefaultIncomeFinancialCalculatorBase {
    
    private final URI TOTAL_INCOMES = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/1350d123e498e9924b7127cb98c74bca");
    
    private final String TOTAL_INCOME_QUERY_TEMPLATE = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum) "
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?Item rdf:type elod:CollectedItem. "
            + " ?Item dcterms:issued ?issued. "
            + " ?Item elod:seller <<buyerSeller>> . "
            + " ?Item elod:price ?ups. "
            + " ?Item elod:hasKae ?kaeuri. "
            + " ?ups gr:hasCurrencyValue ?amount ."
            + "	FILTER("
            + "     YEAR(?issued)='<year>'^^xsd:integer && " 
            + "     MONTH(?issued)='<month>'^^xsd:integer && " 
            + "     DAY(?issued)='<day>'^^xsd:integer" 
            + " ) " 
            + "	FILTER (?kaeuri in (<kaeuris>)) " 
            + "}"; 
    
    public TotalIncomeFinancialRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);        
    }
    
    protected Literal getTotal(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws RepositoryException, MalformedQueryException, QueryEvaluationException, MalformedQueryException, TupleQueryResultHandlerException{
        RepositoryConnection repCon = null;
        TupleQueryResult result = null;    
        try {
            repCon = super.dataRepository.getConnection();
            result = repCon.prepareTupleQuery(QueryLanguage.SPARQL, getTotalQuery(dataGraph, buyerSeller, issued)).evaluate();
            return (Literal)result.next().getValue("sum");
        } finally {
            if(repCon!=null){
                repCon.close();
            }
        }
    }
    
    protected List<URI> getTotalKaes() throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
        List<URI> totalKaes = new ArrayList<>();
        RepositoryConnection repCon = null;
        try {
            repCon = this.pptRepository.getConnection();
            repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    KAE_QUERY_TEMPLATE.replaceFirst("<financialRatio>", getTotalFinancialRatio().stringValue())
            ).evaluate(new TupleQueryResultHandlerBase(){
                @Override
                public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
                    
                    URI kae = (URI)bindingSet.getValue("kae");                     
                    if(!totalKaes.contains(kae)){ totalKaes.add(kae); }  
                }
            });
            return totalKaes;
        } finally {
            if(repCon!=null){
                repCon.close();
            }
        }
    }
    
    protected String getTotalQuery(URI dataGraph, URI buyerSeller, Literal issued) throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
        XMLGregorianCalendar cal = issued.calendarValue();
        StringBuilder kaeFilter = new StringBuilder();
        this.getTotalKaes()
                .stream()
                .filter(
                    kae -> kae.stringValue().contains(cal.getYear()+"")
                ).forEach(
                    kae -> {
                        kaeFilter.append(",<").append(kae).append(">");
                    }
                );
        return this.getTotalQueryTemplate()
                .replaceFirst("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue())                
                .replaceFirst("<kaeuris>", kaeFilter.toString().replaceFirst(",", ""));
    }
    protected URI getTotalFinancialRatio(){
        return this.TOTAL_INCOMES;
    }
    protected String getTotalQueryTemplate(){
        return this.TOTAL_INCOME_QUERY_TEMPLATE;
    }    
}
