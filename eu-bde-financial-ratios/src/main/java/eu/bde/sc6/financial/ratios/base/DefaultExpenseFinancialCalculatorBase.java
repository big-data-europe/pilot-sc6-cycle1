package eu.bde.sc6.financial.ratios.base;

import eu.bde.sc6.financial.ratios.api.CalculationException;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public abstract class DefaultExpenseFinancialCalculatorBase extends FinancialRatioCalculatorBase {
    
    String QUERY = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum)"
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?item rdf:type elod:SpendingItem. "
            + "	?item dcterms:issued ?issued. "
            + "	?item elod:buyer <<buyerSeller>>. "
            + "	?item elod:hasExpenditureLine ?expline . "
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

    
    public DefaultExpenseFinancialCalculatorBase(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }
    
    protected Literal getSum(URI dataGraph, URI buyerSeller, Literal issued) throws CalculationException{
        RepositoryConnection repCon = null;
        TupleQueryResult result = null;
        try {
            repCon = dataRepository.getConnection();
            result = repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    getQuery(dataGraph, buyerSeller, issued)
                ).evaluate();
            if(!result.hasNext()){ 
                return new LiteralImpl("0",XMLSchema.DECIMAL); 
            } else {
                return ((Literal)result.next().getValue("sum"));
            }
        } catch (RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            throw new CalculationException(ex);
        } finally {
            if(repCon!=null){
                try {
                    repCon.close();
                } catch (RepositoryException ex) {}
            }
        }        
    }
    
    protected String getQuery(URI dataGraph, URI buyerSeller, Literal issued){
        XMLGregorianCalendar cal = issued.calendarValue();
        
        StringBuilder kaeFilter = new StringBuilder();
        super.getKaes()
                .stream()
                .filter(
                    kae -> kae.stringValue().contains(cal.getYear()+"")
                ).forEach(
                    kae -> {
                        kaeFilter.append(",<").append(kae).append(">");
                    }
                );
        
        return this.QUERY
                .replaceFirst("<dataGraph>", dataGraph.stringValue())
                .replaceFirst("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue())     
                .replaceFirst("<kaeuris>", kaeFilter.toString().replaceFirst(",", ""));
    }
}
