package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.financial.ratios.base.DefaultIncomePercentageFinancialRatioCalculator;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;
/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class LoansToTotalRevenuesRatioCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    String QUERY = ""
            + "SELECT (COALESCE(?sum1,0)+COALESCE(?sum2,0) AS ?sum) "
            + "WHERE { "
            + "{ "
            + " SELECT (SUM(xsd:decimal(?amount)) as ?sum1) "
            + " FROM <<dataGraph>>   "
            + "	WHERE {  "
            + "     ?Item rdf:type elod:CollectedItem. "
            + "     ?Item dcterms:issued ?issued. "
            + "     ?Item elod:seller <<buyerSeller>>. "
            + "     ?Item elod:price ?ups. "
            + "     ?Item elod:hasKae ?kaeuri. "
            + "     ?ups gr:hasCurrencyValue ?amount .  "
            + "     FILTER("
            + "         YEAR(?issued)='<year>'^^xsd:integer && " 
            + "         MONTH(?issued)='<month>'^^xsd:integer && " 
            + "         DAY(?issued)='<day>'^^xsd:integer" 
            + "     ) "
            + "     FILTER (?kaeuri in (<kaeuris_sum1>)) "
            + " } "
            + "} "            
            + "{ "
            + " SELECT (SUM(xsd:decimal(?amount)) as ?sum2) "
            + " FROM <<dataGraph>>   "
            + "	WHERE {  "
            + "     ?Item rdf:type elod:SpendingItem. "
            + "     ?Item dcterms:issued ?issued. "
            + "     ?Item elod:buyer <<buyerSeller>>. "
            + "     ?Item elod:hasExpenditureLine ?expline. "
            + "     ?expline elod:amount ?ups. "
            + "     ?expline elod:hasKae ?kaeuri. "
            + "     ?ups gr:hasCurrencyValue ?amount .  "
            + "     FILTER("
            + "         YEAR(?issued)='<year>'^^xsd:integer && " 
            + "         MONTH(?issued)='<month>'^^xsd:integer && " 
            + "         DAY(?issued)='<day>'^^xsd:integer" 
            + "     ) "
            + "     FILTER (?kaeuri in (<kaeuris_sum2>)) "            
            + " } "
            + "} "
            + "}";

    public LoansToTotalRevenuesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected String getQuery(URI dataGraph, URI buyerSeller, Literal issued){
        XMLGregorianCalendar cal = issued.calendarValue();
        
        StringBuilder kaeFilter_sum1 = new StringBuilder();
        StringBuilder kaeFilter_sum2 = new StringBuilder();
        super.getKaes()
                .stream()
                .filter(
                    kae -> kae.stringValue().contains(cal.getYear()+"")
                ).forEach(
                    kae -> {
                        if(kae.stringValue().contains("Income/3/31")){
                            kaeFilter_sum1.append(",<").append(kae).append(">");
                        }
                        if(kae.stringValue().contains("Expense/6/65")){
                            kaeFilter_sum2.append(",<").append(kae).append(">");
                        }
                    }
                );
        System.out.println(this.QUERY
                .replaceAll("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceAll("<month>", cal.getMonth()+"")
                .replaceAll("<day>", cal.getDay()+"")
                .replaceAll("<buyerSeller>", buyerSeller.stringValue())     
                .replaceFirst("<kaeuris_sum1>", kaeFilter_sum1.toString().replaceFirst(",", ""))
                .replaceFirst("<kaeuris_sum2>", kaeFilter_sum2.toString().replaceFirst(",", "")));
        return this.QUERY
                .replaceAll("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceAll("<month>", cal.getMonth()+"")
                .replaceAll("<day>", cal.getDay()+"")
                .replaceAll("<buyerSeller>", buyerSeller.stringValue())     
                .replaceFirst("<kaeuris_sum1>", kaeFilter_sum1.toString().replaceFirst(",", ""))
                .replaceFirst("<kaeuris_sum2>", kaeFilter_sum2.toString().replaceFirst(",", ""));
    }

}
