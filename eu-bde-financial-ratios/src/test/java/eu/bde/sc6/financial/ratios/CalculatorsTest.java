package eu.bde.sc6.financial.ratios;

import eu.bde.sc6.financial.ratios.api.*;
import eu.bde.sc6.financial.ratios.impl.FinancialRatioCalculatorRegistryImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 *
 * @author turnguard
 */
public class CalculatorsTest {
    static SPARQLRepository dataRepository = new SPARQLRepository(
            "https://bde-virtuoso.poolparty.biz/sparql"      
    );
    static SPARQLRepository pptRepository = new SPARQLRepository(
            "http://bde.poolparty.biz/PoolParty/sparql/hierarchicalKAE"      
    );    
    static URI DATA_GRAPH = new URIImpl("urn:debug:sc6");
    static URI BUDGET = new URIImpl("http://linkedeconomy.org/KalBudget");
    static Literal BUDGET_LABEL = new LiteralImpl("Kalamaria");
    static URI BUYER_SELLER_KALAMARIA = new URIImpl("http://linkedeconomy.org/resource/Organization/090226243");
    static Literal ISSUED = new LiteralImpl("2016-06-30T12:00:00+02:00", XMLSchema.DATETIME);
    public CalculatorsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws RepositoryException {
        dataRepository.initialize();
        pptRepository.initialize();
        FinancialRatioCalculatorRegistryImpl.setDataRepository(dataRepository);
        FinancialRatioCalculatorRegistryImpl.setPPTRepository(pptRepository);
    }
    
    @AfterClass
    public static void tearDownClass() throws RepositoryException {
        dataRepository.shutDown();
        pptRepository.shutDown();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    //@Test
    public void testTaktikaCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TaktikaCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRegularRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RegularRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }    
    //@Test
    public void testWindfallRevenuesRatio_RegularlyOccurringCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("WindfallRevenuesRatio_RegularlyOccurringCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }    
    //@Test
    public void testWindfallRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("WindfallRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPreviousFinancialYearInvoicedRevenuesToTotalRenvenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PreviousFinancialYearInvoicedRevenuesToTotalRenvenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    
    /* check percentage */
    //@Test
    public void testEquityToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("EquityToTotalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testLoansToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("LoansToTotalRevenuesRatioCalculatorFactory");    
        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testBalancesToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("BalancesToTotalRevenuesRatioCalculatorFactory");    
        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testCashBalanceToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("CashBalanceToTotalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testReciprocalRevenuesToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ReciprocalRevenuesToTotalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testExternalRevenuesToTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ExternalRevenuesToTotalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testFiscalYearsExpensesToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("FiscalYearsExpensesToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testInvestmentToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testPreviousFinancialYearExpensesToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PreviousFinancialYearExpensesToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    
    //@Test
    public void testSubsidiesToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("SubsidiesToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testSubsidiesRevenuesToSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("SubsidiesRevenuesToSubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testOperatingSubsidiesToSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OperatingSubsidiesToSubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testRegularSubsidiesToSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RegularSubsidiesToSubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testWindfallSubsidiesToSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("WindfallSubsidiesToSubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testRevenuesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RevenuesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }    
    //@Test
    public void testExpensesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ExpensesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRegularRevenuesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RegularRevenuesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testWindfallRevenuesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("WindfallRevenuesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }     
    //@Test
    public void testOwnRevenuePerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OwnRevenuePerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testReciprocalRevenuesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ReciprocalRevenuesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testSubsidiesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("SubsidiesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testTaxesAndFeesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TaxesAndFeesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testCleaningFeesPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("CleaningFeesPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    @Test
    public void testExpensesUsePerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ExpensesUsePerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testInvestmentPerCapitaRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentPerCapitaRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testEstablishedRevenuesToBudgetedRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("EstablishedRevenuesToBudgetedRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testTotalRevenuesCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TotalRevenuesCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testTotalExpensesCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TotalExpensesCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRegularRevenuesCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RegularRevenuesCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testWindfallRevenuesCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("WindfallRevenuesCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPreviousFinancialYearInvoicedRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PreviousFinancialYearInvoicedRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testEquityRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("EquityRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testReciprocalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ReciprocalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    } 
    //@Test
    public void testTaxesAndFeesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TaxesAndFeesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testCleaningFeesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("CleaningFeesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testLoansRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("LoansRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testBalancesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("BalancesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testExternalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ExternalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testFiscalYearsExpensesCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("FiscalYearsExpensesCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPersonnelCostsRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PersonnelCostsRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testCostOfTotalEmploymentRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("CostOfTotalEmploymentRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("SubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testInvestmentSubsidiesAndLoansRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentSubsidiesAndLoansRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testInvestmentCostsAndCorrespondingAmortizationRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentCostsAndCorrespondingAmortizationRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testOptionalSubsidiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OptionalSubsidiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testInvestmentRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPreviousFinancialYearLiabilitiesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PreviousFinancialYearLiabilitiesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testFinancialForecastsRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("FinancialForecastsRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testReservesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ReservesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPersonnelCostToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PersonnelCostToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPersonnelCostToTotalFiscalYearExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PersonnelCostToTotalFiscalYearExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testTotalEmployementCostToTotalExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TotalEmployementCostToTotalExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testTotalEmployementCostToRegularRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TotalEmployementCostToRegularRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }    
    
    //@Test
    public void testFiscalYearExpensesToRegularRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("FiscalYearExpensesToRegularRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testEmploymentCostsToRegularRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("EmploymentCostsToRegularRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testCIRToOperatingExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("CIRToOperatingExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testInvestmentSubsidiesAndLoansToInvestmentExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InvestmentSubsidiesAndLoansToInvestmentExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testOptionalSubsidiesRatio56Calculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OptionalSubsidiesRatio56CalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testReservesRatio57Calculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ReservesRatio57CalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRevenuesToExpensesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RevenuesToExpensesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testOtherYieldsRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OtherYieldsRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testPensionContributionsToVieldRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("PensionContributionsToVieldRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testTaxesAndOtherChargesToYieldRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("TaxesAndOtherChargesToYieldRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testInsuranceContributionsToYieldRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("InsuranceContributionsToYieldRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testOtherDeductionsForThirdPartiesToYieldCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("OtherDeductionsForThirdPartiesToYieldCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testFixedAdvancePaymentRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("FixedAdvancePaymentRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfGeneralServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfGeneralServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfFinancialAndAdministrativeServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfFinancialAndAdministrativeServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfCultureSportAndSocialPolicyServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfCultureSportAndSocialPolicyServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfCleaningAndElectrolightingServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfCleaningAndElectrolightingServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfWaterIrrigationDrainageServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfWaterIrrigationDrainageServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfConstructionServiceOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfConstructionServiceOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfGreenServiceOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfGreenServiceOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfPlanningServiceOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfPlanningServiceOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfOfficeCemeteriesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfOfficeCemeteriesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfMunicipalPoliceOnOverallServicesRatioCalculatorFactory() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfMunicipalPoliceOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testParticipationOfOtherServicesOnOverallServicesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("ParticipationOfOtherServicesOnOverallServicesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRepaymentOfLoanCommitmentsFromRegularRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RepaymentOfLoanCommitmentsFromRegularRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }
    //@Test
    public void testRepaymentOfLoanCommitmentsFromTotalRevenuesRatioCalculator() throws UnknownCalculatorException, CalculationException {
        FinancialRatioCalculator frc = FinancialRatioCalculatorRegistryImpl.getInstance()
                .getCalculatorByClassName("RepaymentOfLoanCommitmentsFromTotalRevenuesRatioCalculatorFactory");        
        frc.calculate(DATA_GRAPH, BUDGET, BUDGET_LABEL, BUYER_SELLER_KALAMARIA, ISSUED).forEach(System.out::println);                
    }


}
