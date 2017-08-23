package eu.bde.sc6.financial.ratios.base;

import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class SumEpixorPercentageFinancialCalculator extends DefaultIncomePercentageFinancialRatioCalculator {
    
    private final String EPIXOR_INCOME_QUERY_TEMPLATE = ""
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
            + " FILTER("
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/0/06\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/1/12\") || "
            + "     STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/1/13\") "
            + " ) "
            + "}";
    
    public SumEpixorPercentageFinancialCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected String getTotalQuery(URI dataGraph, URI buyerSeller, Literal issued) {
        XMLGregorianCalendar cal = issued.calendarValue();
        
        return this.EPIXOR_INCOME_QUERY_TEMPLATE
                .replaceFirst("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceFirst("<month>", cal.getMonth()+"")
                .replaceFirst("<day>", cal.getDay()+"")
                .replaceFirst("<buyerSeller>", buyerSeller.stringValue());        
    }


}
