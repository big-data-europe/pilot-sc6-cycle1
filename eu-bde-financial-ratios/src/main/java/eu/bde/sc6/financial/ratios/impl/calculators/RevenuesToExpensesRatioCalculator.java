package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultIncomePercentageFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class RevenuesToExpensesRatioCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    private final URI TOTAL_RATIO = new URIImpl("https://bde.poolparty.biz/SC6-Financial-Ratios/86e3c8090c92995535a4b03f120ac79f");
    
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
    
    public RevenuesToExpensesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }
    
    @Override
    protected String getTotalQueryTemplate() {
        return TOTAL_EXPENSE_QUERY_TEMPLATE;
    }
    @Override
    protected URI getTotalFinancialRatio() {
        return TOTAL_RATIO;
    }
}
