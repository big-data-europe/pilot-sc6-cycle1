package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultExpensePercentageFinancialRatioCalculator;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class PensionContributionsToVieldRatioCalculator extends DefaultExpensePercentageFinancialRatioCalculator {

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
    
    
    public PensionContributionsToVieldRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected String getTotalQueryTemplate() {
        return this.TOTAL_INCOME_QUERY_TEMPLATE;
    }

    @Override
    protected URI getTotalFinancialRatio() {
        return new URIImpl("http://bde.poolparty.biz/hierarchicalKAE/8");
    }
}
