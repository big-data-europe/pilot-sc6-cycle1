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
public abstract class TotalExpenseFinancialRatioCalculator extends DefaultExpenseFinancialCalculatorBase {
    
    private final URI TOTAL_EXPENSES = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/86e3c8090c92995535a4b03f120ac79f");
    
    String TOTAL_EXPENSE_QUERY_TEMPLATE = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum) "
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?item rdf:type elod:SpendingItem. "
            + " ?item dcterms:issued ?issued. "
            + "	?item elod:buyer <<buyerSeller>>. "
            + "	?item elod:hasExpenditureLine ?expline. "
            + "	?expline elod:amount ?ups. "
            + "	?expline elod:hasKae ?kaeuri. "
            + "	?ups gr:hasCurrencyValue ?amount . "
            + "	FILTER("
            + "     YEAR(?issued)='<year>'^^xsd:integer && " 
            + "     MONTH(?issued)='<month>'^^xsd:integer && " 
            + "     DAY(?issued)='<day>'^^xsd:integer" 
            + " ) "
            + "	FILTER (?kaeuri in (<kaeuris>)) "  
            + "}";    

    public TotalExpenseFinancialRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    protected Literal getTotal(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
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

    protected List<URI> getTotalKaes() throws RepositoryException, MalformedQueryException, QueryEvaluationException, TupleQueryResultHandlerException{
        List<URI> totalKaes = new ArrayList<>();
        RepositoryConnection repCon = null;        
        try {
            repCon = this.pptRepository.getConnection();
            repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    KAE_QUERY_TEMPLATE.replaceAll("<financialRatio>", getTotalFinancialRatio().stringValue())
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
    
    protected URI getTotalFinancialRatio(){
        return this.TOTAL_EXPENSES;
    }
    protected String getTotalQueryTemplate(){
        return this.TOTAL_EXPENSE_QUERY_TEMPLATE;
    }
}
