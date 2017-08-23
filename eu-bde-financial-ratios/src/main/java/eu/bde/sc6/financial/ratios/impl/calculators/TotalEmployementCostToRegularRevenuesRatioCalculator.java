package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultExpensePercentageFinancialRatioCalculator;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class TotalEmployementCostToRegularRevenuesRatioCalculator extends DefaultExpensePercentageFinancialRatioCalculator {
    
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
            + "FILTER(?kaeuri IN (<kaeuris>)) "
            + "}"; 
    
    private final URI TOTAL_FINANCIAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/5d86d0b8e9beee2dcc39e8c6dd8500c6");
    
    public TotalEmployementCostToRegularRevenuesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected URI getTotalFinancialRatio() {
        return TOTAL_FINANCIAL_RATIO;
    }

    @Override
    protected String getTotalQueryTemplate() {
        return this.TOTAL_INCOME_QUERY_TEMPLATE;
    }
    
}
