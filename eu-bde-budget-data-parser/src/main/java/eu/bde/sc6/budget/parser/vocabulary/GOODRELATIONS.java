package eu.bde.sc6.budget.parser.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class GOODRELATIONS {
    
    public static final String NAMESPACE = "http://purl.org/goodrelations/v1#";
    
    /* CLASSES */
    public static final URI UNIT_PRICE_SPECIFICATION = new URIImpl(NAMESPACE.concat("UnitPriceSpecification"));
    
    /* PREDICATES */
    public static final URI HAS_CURRENCY_VALUE = new URIImpl(NAMESPACE.concat("hasCurrencyValue"));
                    
}
