package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.ArithmeticFinancialRatioCalculatorBase;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 * unknown KAE => not registered
 * @author http://www.turnguard.com/turnguard
 */
public class ParticipationOfGreenServiceOnOverallServicesRatioCalculator extends ArithmeticFinancialRatioCalculatorBase {

    String QUERY_ARITHM = ""
            + "SELECT * "
            + "FROM <<dataGraph>>  "
            + "WHERE {"
            + " {"
            + "     SELECT (COALESCE(SUM(xsd:double(?amount)),0) as ?nominator1)"
            + "     FROM <<dataGraph>>   "
            + "     WHERE {"
            + "         ?item rdf:type elod:CollectedItem ; "
            + "               dcterms:issued ?issued. "
            + "         ?item elod:seller <<buyerSeller>> . "
            + "         ?item elod:price ?ups."
            + "         ?item elod:hasKae ?kaeuri."
            + "         ?ups gr:hasCurrencyValue ?amount . "
            + "         FILTER("
            + "             YEAR(?issued)='<year>'^^xsd:integer && " 
            + "             MONTH(?issued)='<month>'^^xsd:integer && " 
            + "             DAY(?issued)='<day>'^^xsd:integer" 
            + "         ) "
            + "         FILTER(STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/3/35\"))"            
            + "     }"
            + " }"
            + " {"
            + "     SELECT (SUM(xsd:double(?amount)) as ?denominator1)"
            + "     FROM <<dataGraph>>  "
            + "     WHERE {"
            + "         ?item rdf:type elod:SpendingItem ; "
            + "               dcterms:issued ?issued."
            + "         ?item elod:buyer <<buyerSeller>>."
            + "         ?item elod:hasExpenditureLine ?expline."
            + "         ?expline elod:amount ?ups."
            + "         ?expline elod:hasKae ?kaeuri."
            + "         ?ups gr:hasCurrencyValue ?amount . "
            + "         FILTER("
            + "             YEAR(?issued)='<year>'^^xsd:integer && " 
            + "             MONTH(?issued)='<month>'^^xsd:integer && " 
            + "             DAY(?issued)='<day>'^^xsd:integer" 
            + "         ) "
            + "         FILTER (STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Expense/6\") )"
            + "     }"
            + " }"
            + " {"
            + "     SELECT (SUM(xsd:double(?amount)) as ?denominator2)"
            + "     FROM <<dataGraph>>   "
            + "     WHERE {"
            + "         ?item rdf:type elod:SpendingItem ; "
            + "               dcterms:issued ?issued."
            + "         ?item elod:buyer <<buyerSeller>>."
            + "         ?item elod:hasExpenditureLine ?expline."
            + "         ?expline elod:amount ?ups."
            + "         ?expline elod:hasKae ?kaeuri."
            + "         ?ups gr:hasCurrencyValue ?amount . "
            + "         FILTER("
            + "             YEAR(?issued)='<year>'^^xsd:integer && " 
            + "             MONTH(?issued)='<month>'^^xsd:integer && " 
            + "             DAY(?issued)='<day>'^^xsd:integer" 
            + "         ) "
            + "         FILTER(STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Expense/7\") )"
            + "     }"
            + " }"
            + "}";
    
    public ParticipationOfGreenServiceOnOverallServicesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected String getArithmeticQuery() {
        return this.QUERY_ARITHM;
    }
}
