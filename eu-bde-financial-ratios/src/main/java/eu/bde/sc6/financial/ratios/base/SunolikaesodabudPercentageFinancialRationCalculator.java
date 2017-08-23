package eu.bde.sc6.financial.ratios.base;

import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class SunolikaesodabudPercentageFinancialRationCalculator extends DefaultIncomePercentageFinancialRatioCalculator {
    String SUNO_QUERY = ""
            + "SELECT (SUM(xsd:decimal(?amount)) as ?sum) "
            + "FROM <<dataGraph>> "
            + "WHERE { "
            + " ?item rdf:type elod:BudgetItem. "
            + "	?item dcterms:issued ?issued. "
            + "	?item elod:seller <<buyerSeller>> . "
            + "	?item elod:price ?ups. "
            + "	?item elod:hasKae ?kaeuri. "
            + "	?ups gr:hasCurrencyValue ?amount . "
            + "	FILTER("
            + "     YEAR(?issued)='<year>'^^xsd:integer && " 
            + "     MONTH(?issued)='<month>'^^xsd:integer && " 
            + "     DAY(?issued)='<day>'^^xsd:integer" 
            + " ) " 
            + " FILTER  ("
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/0\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/1\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/2\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/3\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/4\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/5\")"
            + " )"
            + "}";

    public SunolikaesodabudPercentageFinancialRationCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }
    @Override
    protected String getTotalQuery(URI dataGraph, URI buyerSeller, Literal issued) {
        XMLGregorianCalendar cal = issued.calendarValue();
        
        return this.SUNO_QUERY
                .replaceFirst("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue());        
    }
}
