package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.api.CalculationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class EstablishedRevenuesToBudgetedRevenuesRatioCalculator extends eu.bde.sc6.financial.ratios.base.SunolikaesodabudPercentageFinancialRationCalculator {
    String RECOGNIZED_ITEM_QUERY = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum) "
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?item rdf:type elod:RevenueRecognizedItem. "
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

    public EstablishedRevenuesToBudgetedRevenuesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
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
        
        return this.RECOGNIZED_ITEM_QUERY
                .replaceFirst("<dataGraph>", dataGraph.stringValue())
                .replaceFirst("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue())     
                .replaceFirst("<kaeuris>", kaeFilter.toString().replaceFirst(",", ""));
    }
}
