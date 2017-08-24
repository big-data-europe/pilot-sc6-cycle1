package eu.bde.sc6.financial.ratios.base;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class FixedNumberIncomePercentageCalculator extends DefaultIncomePercentageFinancialRatioCalculator {

    private final String POPULATION_QUERY = ""
            + "PREFIX sc6:<http://schema.big-data-europe.eu/sc6/core#> "
            + "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> "
            + "SELECT ?population WHERE {"
            + " <<budget>> skos:related ?municipality . "
            + " ?municipality sc6:population ?population "
            + "}";
        
    public FixedNumberIncomePercentageCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

    @Override
    protected Literal getTotal(URI dataGraph, URI budget, Literal budgetLabel, URI buyerSeller, Literal issued) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        RepositoryConnection repCon = null;
        TupleQueryResult result = null;
        try {
            repCon = super.pptRepository.getConnection();
            result = repCon.prepareTupleQuery(
                    QueryLanguage.SPARQL, 
                    POPULATION_QUERY.replaceFirst("<budget>", budget.stringValue())
            ).evaluate();
            return (Literal)result.next().getValue("population");
        } finally {
            if(repCon!=null){
                repCon.close();
            }
        }        
    }
}
