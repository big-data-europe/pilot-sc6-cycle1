package eu.bde.sc6.financial.ratios.impl.calculators;

import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.financial.ratios.api.CalculationException;
import eu.bde.sc6.financial.ratios.base.DefaultIncomeFinancialCalculatorBase;
import eu.bde.sc6.financial.ratios.base.DefaultIncomePercentageFinancialRatioCalculator;
import eu.bde.sc6.financial.ratios.base.TotalIncomeFinancialRatioCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.vocabulary.RDF;
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
public class RegularRevenuesRatioCalculator extends DefaultIncomePercentageFinancialRatioCalculator {
    

    public RegularRevenuesRatioCalculator(URI financialRatio, SPARQLRepository dataRepository, SPARQLRepository pptRepository) {
        super(financialRatio, dataRepository, pptRepository);
    }

}
