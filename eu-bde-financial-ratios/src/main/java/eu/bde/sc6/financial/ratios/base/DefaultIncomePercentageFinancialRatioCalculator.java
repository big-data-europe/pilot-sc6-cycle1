package eu.bde.sc6.financial.ratios.base;

import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.financial.ratios.api.CalculationException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class DefaultIncomePercentageFinancialRatioCalculator extends TotalIncomeFinancialRatioCalculator {

    public DefaultIncomePercentageFinancialRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    public List<Statement> calculate(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws CalculationException {
        List<Statement> states = new ArrayList<>();
        Literal total;
        Literal sum;
        try {
            total = super.getTotal(dataGraph, budget, budgetLabel, buyerSeller, issued);            
            sum = super.getSum(dataGraph, buyerSeller, issued);
        } catch (TupleQueryResultHandlerException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            throw new CalculationException(this.getClass().getName(), ex);
        }

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
        states.add(new StatementImpl(aggregate, ELOD.HAS_VALUE_PERCENTAGE, super.calculatePercentage(sum, total)));
        
        return states;
    }

}
