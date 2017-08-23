package eu.bde.sc6.financial.ratios.base;

import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.financial.ratios.api.CalculationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
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
public class ArithmeticFinancialRatioCalculatorBase extends FinancialRatioCalculatorBase {
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
            //+ "         FILTER(STRSTARTS(STR(?kaeuri), \"http://linkedeconomy.org/resource/KAE/<year>/Income/0\"))"            
            + "         FILTER (?kaeuri in (<kaeuris>)) "
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

    public ArithmeticFinancialRatioCalculatorBase(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    public List<Statement> calculate(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws CalculationException {
        List<Statement> states = new ArrayList<>();
        
        Literal percentage = this.calculatePercentage(this.getPercentageBindingSet(dataGraph, buyerSeller, issued));
        
        URI statistic = super.createStatisticsURI();
        URI aggregate = super.createAggregateURI();
        
        states.add(new StatementImpl(statistic, RDF.TYPE, ELOD.STATISTIC));
        states.add(new StatementImpl(statistic, ELOD.FINANCIAL_YEAR, new LiteralImpl(issued.calendarValue().getYear()+"",XMLSchema.GYEAR)));
        states.add(new StatementImpl(statistic, ELOD.HAS_RELATED_RATIO, super.origFinancialRatio));
        states.add(new StatementImpl(super.origFinancialRatio, DCTERMS.TITLE, super.financialRatioLabel));
        states.add(new StatementImpl(statistic, ELOD.HAS_AGGREGATE, aggregate));
        states.add(new StatementImpl(statistic, ELOD.IS_STATISTIC_OF, budget));
        states.add(new StatementImpl(budget, DCTERMS.TITLE, budgetLabel));
        states.add(new StatementImpl(statistic, ELOD.REFERENCE_TIME, super.dateTimeToDate(issued)));
        states.add(new StatementImpl(statistic, ELOD.REFERENCE_TIME_DURATION, new LiteralImpl("6M",XMLSchema.DURATION)));
        try {
            states.add(new StatementImpl(statistic, ELOD.LAST_UPDATE_DATE, super.now()));
        } catch (DatatypeConfigurationException ex) {}
        
        states.add(new StatementImpl(aggregate, RDF.TYPE, ELOD.AGGREGATE));
        states.add(new StatementImpl(aggregate, ELOD.HAS_VALUE_PERCENTAGE, percentage));
        
        return states;
    }
    
    private Literal calculatePercentage(BindingSet percentageBindingSet){
      // ((((COALESCE(?nominator1,0)/?denominator1)+(COALESCE(?nominator1,0)/?denominator2))/(?denominator1+?denominator2)) as ?sum)
        Literal nominator1 = (Literal) percentageBindingSet.getValue("nominator1");        
        Literal deNominator1 = (Literal) percentageBindingSet.getValue("denominator1");
        Literal deNominator2 = (Literal) percentageBindingSet.getValue("denominator2");
        double div1 = nominator1.doubleValue()/deNominator1.doubleValue();
        double div2 = nominator1.doubleValue()/deNominator2.doubleValue();
        double add1 = div1+div2;
        double add2 = deNominator1.doubleValue()+deNominator2.doubleValue();
        double percentag = add1/add2;
        return new LiteralImpl(percentag+"",XMLSchema.DOUBLE);
    }
    
    protected BindingSet getPercentageBindingSet(URI dataGraph, URI buyerSeller, Literal issued) throws CalculationException{
        RepositoryConnection repCon = null;
        TupleQueryResult result = null;
        try {
            repCon = dataRepository.getConnection();
            result = repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    getQuery(dataGraph, buyerSeller, issued)
                ).evaluate();
            if(result.hasNext()){ 
                return result.next();
            } else {
                throw new CalculationException("Empty resultset");
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

        return this.getArithmeticQuery()
                .replaceAll("<dataGraph>", dataGraph.stringValue())
                .replaceAll("<year>", cal.getYear()+"")
                .replaceAll("<month>", cal.getMonth()+"")
                .replaceAll("<day>", cal.getDay()+"")
                .replaceAll("<buyerSeller>", buyerSeller.stringValue())
                .replaceFirst("<kaeuris>", kaeFilter.toString().replaceFirst(",", ""));
    }
    
    protected String getArithmeticQuery(){
        return this.QUERY_ARITHM;
    }
        
}
