package eu.bde.sc6.financial.ratios.impl;

import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculator;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculatorFactory;
import eu.bde.sc6.financial.ratios.api.FinancialRatioCalculatorRegistry;
import eu.bde.sc6.financial.ratios.api.UnknownCalculatorException;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.openrdf.model.URI;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.slf4j.LoggerFactory;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class FinancialRatioCalculatorRegistryImpl implements FinancialRatioCalculatorRegistry {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FinancialRatioCalculatorRegistryImpl.class);
    
    private static FinancialRatioCalculatorRegistryImpl registry;
    private final ServiceLoader<FinancialRatioCalculatorFactory> loader;
    
    public static SPARQLRepository dataRepository;
    public static SPARQLRepository pptRepository;
    
    public FinancialRatioCalculatorRegistryImpl() {     
        if(dataRepository==null || pptRepository==null){
            throw new ExceptionInInitializerError("dataRepository and pptRepository and financialRatio uri must be set statically");
        }
        loader = ServiceLoader.load(FinancialRatioCalculatorFactory.class);
        loader.forEach(parser -> LOG.warn("Available Parser: "+parser.getClass().getName()));        
    }
    
    public FinancialRatioCalculatorRegistryImpl(SPARQLRepository dataRepository, SPARQLRepository pptRepository) {     
        setDataRepository(dataRepository);
        setPPTRepository(pptRepository);
        if(dataRepository==null || pptRepository==null){
            throw new ExceptionInInitializerError("dataRepository and pptRepository and financialRatio uri must be set statically");
        }
        loader = ServiceLoader.load(FinancialRatioCalculatorFactory.class);
        loader.forEach(parser -> LOG.warn("Available Parser: "+parser.getClass().getName()));        
    }    

    public static void setDataRepository(SPARQLRepository dataRepository){
        FinancialRatioCalculatorRegistryImpl.dataRepository = dataRepository;
    }
    public static void setPPTRepository(SPARQLRepository pptRepository){
        FinancialRatioCalculatorRegistryImpl.pptRepository = pptRepository;
    }    
    
    public static synchronized FinancialRatioCalculatorRegistryImpl getInstance() {
        if (registry == null) {
            registry = new FinancialRatioCalculatorRegistryImpl();
        }
        return registry;
    }

    public Iterator<FinancialRatioCalculatorFactory> iterator(){
        return loader.iterator();
    }
    
    @Override
    public FinancialRatioCalculator getCalculatorByClassName(String className) throws UnknownCalculatorException{
        for (FinancialRatioCalculatorFactory calculatorFactory : this.loader) {            
            if(calculatorFactory.getClass().getSimpleName().equals(className)){
                return calculatorFactory.get(dataRepository, pptRepository);
            }
        } 
        throw new UnknownCalculatorException(className + " is not registered");
    }
    
}
