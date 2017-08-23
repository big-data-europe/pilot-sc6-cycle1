package eu.bde.sc6.financial.ratios.base;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class FixedNumberIncomePercentageCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    public FixedNumberIncomePercentageCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected Literal getTotal(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        return new LiteralImpl("91518", XMLSchema.DOUBLE);
    }

}
