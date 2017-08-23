package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.financial.ratios.api.CalculationException;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculator;
import eu.bde.sc6.financial.ratios.base.FinancialRatioCalculatorBase;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang.StringUtils;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
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
public class TaktikaCalculator extends FinancialRatioCalculatorBase {
    String QUERY = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum)"
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?item rdf:type elod:CollectedItem. "
            + "	?item dcterms:issued ?issued. "
            + "	?item elod:seller <<buyerSeller>>. "
            + "	?item elod:price ?ups. "
            + "	?item elod:hasKae ?kaeuri. "
            + "	?ups gr:hasCurrencyValue ?amount . "
            + "	FILTER("
            + "     YEAR(?issued)='<year>'^^xsd:integer && " 
            + "     MONTH(?issued)='<month>'^^xsd:integer && " 
            + "     DAY(?issued)='<day>'^^xsd:integer" 
            + " ) "
            + "	FILTER (?kaeuri in (<kaeuris>)) "            
            + "}";

    public TaktikaCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);        
    }
    
    
    private String getQuery(URI dataGraph, URI buyerSeller, Literal issued){
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
                .replaceAll("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue())
                .replaceAll("<city>", "kalamaria")
                .replaceFirst("<kaeuris>", kaeFilter.toString().replaceFirst(",", ""));
    }
    
    @Override
    public List<Statement> calculate(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws CalculationException {
        List<Statement> states = new ArrayList<>();
        
        Literal sum = this.getSum(this.getQuery(dataGraph, buyerSeller, issued));
        
        URI statistic = super.createStatisticsURI();
        URI aggregate = super.createAggregateURI();
        
        states.add(new StatementImpl(statistic, RDF.TYPE, ELOD.STATISTIC));
        states.add(new StatementImpl(statistic, ELOD.FINANCIAL_YEAR, new LiteralImpl(issued.calendarValue().getYear()+"",XMLSchema.GYEAR)));
        states.add(new StatementImpl(statistic, ELOD.HAS_RELATED_RATIO, super.origFinancialRatio));        
        states.add(new StatementImpl(statistic, ELOD.HAS_AGGREGATE, aggregate));
        states.add(new StatementImpl(statistic, ELOD.IS_STATISTIC_OF, budget));
        states.add(new StatementImpl(statistic, ELOD.REFERENCE_TIME, super.dateTimeToDate(issued)));
        states.add(new StatementImpl(statistic, ELOD.REFERENCE_TIME_DURATION, new LiteralImpl("6M",XMLSchema.DURATION)));
        try {
            states.add(new StatementImpl(statistic, ELOD.LAST_UPDATE_DATE, super.now()));
        } catch (DatatypeConfigurationException ex) {}
        
        states.add(new StatementImpl(aggregate, RDF.TYPE, ELOD.AGGREGATE));
        states.add(new StatementImpl(aggregate, ELOD.HAS_VALUE, sum));
        
        states.add(new StatementImpl(super.origFinancialRatio, DCTERMS.TITLE, super.financialRatioLabel));
        

        return states;
    }
    
    private Literal getSum(String query) throws CalculationException{
        RepositoryConnection repCon = null;
        TupleQueryResult result = null;
        try {
            repCon = dataRepository.getConnection();
            result = repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    query
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
}
