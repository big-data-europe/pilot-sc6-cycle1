package ontology;

/**
 *
 * @author giorgos, lefteris
 */
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class Ontology {
    
    /** prefixes **/
	public static final String goodRelationsPrefix;
	public static final String elodPrefix;
	public static final String instancePrefix;
        public static final String rdfsPrefix;
        public static final String dcPrefix;
        public static final String dctermsPrefix;
        public static final String skosPrefix;
        
        /** classes **/
        public static final Resource skosConceptResource;
        public static final Resource customKaeResource;
        public static final Resource spendingItemResource;
        public static final Resource collectedItemResource;        
        public static final Resource budgetItemResource;
        public static final Resource expenseItemResource;
        public static final Resource revenueRecognizedItemResource;
        public static final Resource committedItemResource;
        public static final Resource expLineResource;
        public static final Resource upsResource;
        public static final Resource currencyResource;
        public static final Resource yearResource;
        public static final Resource monthResource;
        public static final Resource statisticResource;
        
        
        /** Object Properties **/
        public static final Property hasExpenditureLine;
        public static final Property amount;
        public static final Property price;
        public static final Property seller;
        public static final Property buyer;
        public static final Property hasCurrency;
        public static final Property hasKae;
        public static final Property hasCustomKae;
        
        /** Datatype Properties **/
        public static final Property subject;
        public static final Property issued;
        public static final Property financialYear;
        public static final Property hasCurrencyValue;
        public static final Property kae;
        public static final Property hasKind;
        
        
        /** Stats Datatype Properties **/
        public static final Property property;
        public static final Property subsidy;
        public static final Property retributive;
        public static final Property exchequer;
        public static final Property taxesRevenue;
        public static final Property restRevenue;
        
        public static final Property supplies;
        public static final Property reward;
        public static final Property investment;
        public static final Property loan;
        public static final Property taxesExpense;
        public static final Property restExpense;
        
        public static final Property expenseAggregatedAmount;
        public static final Property revenueAggregatedAmount;
        
        public static final Property expenseAggregatedSum;
        public static final Property revenueAggregatedSum;
        
        public static final Property expenseDifferenceAmount;
        public static final Property revenueDifferenceAmount;
	
        public static final Property currentExpenseAggregatedSum;
        public static final Property currentRevenueAggregatedSum;
        
        
         /** Indices Stats Datatype Properties **/
        
        public static final Property regularRevenuesRatio;
        public static final Property regularRevenues;
        public static final Property windfallRevenuesRatio;
        public static final Property windfallRevenues;
        public static final Property pfyInvoicedRevenues;
        public static final Property equityRatio; 
        public static final Property totalRevenues;
        public static final Property cashBalance;
        public static final Property externalRevenues;
        public static final Property investSubsLoans;
        public static final Property totalExpenses;
        public static final Property expensesUse;
        public static final Property personnelCosts;
        public static final Property totalEmploymentCosts;
        public static final Property amortizationCosts;
        public static final Property optionalSubsidies;
        public static final Property investmentRatio;
        public static final Property pfyLiabilities; 
        public static final Property financialForecasts;
        public static final Property reserves;
        public static final Property pfyExpenses;
        public static final Property projectSubsidies;
        public static final Property regularSubsidies;
        public static final Property windfallSubsidies;
        public static final Property expenseBudget;
        public static final Property expenseCommitted;
	public static final Property expenseAppproval;
	public static final Property revenueBudget;
        public static final Property revenueApproval;
        public static final Property revenueSpending;
        public static final Property expenseSpending;
        public static final Property pfyRest;

	static {
		/** prefixes **/
		instancePrefix = "http://linkedeconomy.org/resource/";
		goodRelationsPrefix = "http://purl.org/goodrelations/v1#";
		elodPrefix = "http://linkedeconomy.org/ontology#";
                rdfsPrefix = "http://www.w3.org/2000/01/rdf-schema#";
                dcPrefix = "http://purl.org/dc/elements/1.1/";
                dctermsPrefix = "http://purl.org/dc/terms/";
                skosPrefix = "http://www.w3.org/2004/02/skos/core#";


                /** classes **/
                skosConceptResource = ResourceFactory.createResource(skosPrefix + "Concept");
                customKaeResource = ResourceFactory.createResource(elodPrefix + "KaeThessaloniki");
                spendingItemResource = ResourceFactory.createResource(elodPrefix + "SpendingItem");
                collectedItemResource = ResourceFactory.createResource(elodPrefix + "CollectedItem");
                budgetItemResource = ResourceFactory.createResource(elodPrefix + "BudgetItem");
                expenseItemResource = ResourceFactory.createResource(elodPrefix + "ExpenseApprovalItem");
                revenueRecognizedItemResource = ResourceFactory.createResource(elodPrefix + "RevenueRecognizedItem");
                expLineResource = ResourceFactory.createResource(elodPrefix + "ExpenditureLine");
                upsResource = ResourceFactory.createResource(goodRelationsPrefix + "UnitPriceSpecification");
                currencyResource = ResourceFactory.createResource(elodPrefix + "Currency");
                committedItemResource = ResourceFactory.createResource(elodPrefix + "CommittedItem");
                yearResource = ResourceFactory.createResource(elodPrefix + "Year");
                monthResource = ResourceFactory.createResource(elodPrefix + "Month");
                statisticResource = ResourceFactory.createResource(elodPrefix + "Statistic");

                
                /** Object Properties **/
                hasExpenditureLine = ResourceFactory.createProperty(elodPrefix + "hasExpenditureLine");
                amount = ResourceFactory.createProperty(elodPrefix + "amount");
                price = ResourceFactory.createProperty(elodPrefix + "price");
                seller = ResourceFactory.createProperty(elodPrefix + "seller");
                buyer = ResourceFactory.createProperty(elodPrefix + "buyer");
                hasCurrency = ResourceFactory.createProperty(elodPrefix + "hasCurrency");
                hasKae = ResourceFactory.createProperty(elodPrefix + "hasKae");
                hasCustomKae = ResourceFactory.createProperty(elodPrefix + "hasCustomKae");
                
                /** Datatype Properties **/
                subject = ResourceFactory.createProperty(dctermsPrefix + "subject");
                issued = ResourceFactory.createProperty(dctermsPrefix + "issued");
                financialYear = ResourceFactory.createProperty(elodPrefix + "financialYear");
                hasCurrencyValue = ResourceFactory.createProperty(goodRelationsPrefix + "hasCurrencyValue");
                kae = ResourceFactory.createProperty(elodPrefix + "kae");
                hasKind = ResourceFactory.createProperty(elodPrefix + "hasKind");
                
                
                
                /**Stats Datatype Properties **/
                property = ResourceFactory.createProperty(elodPrefix + "property");
                subsidy = ResourceFactory.createProperty(elodPrefix + "subsidy");
                retributive = ResourceFactory.createProperty(elodPrefix + "retributive");
                exchequer = ResourceFactory.createProperty(elodPrefix + "exchequer");
                taxesRevenue = ResourceFactory.createProperty(elodPrefix + "taxesRevenue");
                restRevenue = ResourceFactory.createProperty(elodPrefix + "restRevenue");
                
                supplies = ResourceFactory.createProperty(elodPrefix + "supply");
                reward = ResourceFactory.createProperty(elodPrefix + "reward");
                investment = ResourceFactory.createProperty(elodPrefix + "investment");
                loan = ResourceFactory.createProperty(elodPrefix + "loan");
                taxesExpense = ResourceFactory.createProperty(elodPrefix + "taxesExpense");
                restExpense = ResourceFactory.createProperty(elodPrefix + "restExpense");
                
                expenseAggregatedAmount = ResourceFactory.createProperty(elodPrefix + "expenseAggregatedAmount");
                revenueAggregatedAmount = ResourceFactory.createProperty(elodPrefix + "revenueAggregatedAmount");
                
                expenseAggregatedSum = ResourceFactory.createProperty(elodPrefix + "expenseAggregatedSum");
                revenueAggregatedSum = ResourceFactory.createProperty(elodPrefix + "revenueAggregatedSum");
                
                expenseDifferenceAmount = ResourceFactory.createProperty(elodPrefix + "expenseDifferenceAmount");
                revenueDifferenceAmount = ResourceFactory.createProperty(elodPrefix + "revenueDifferenceAmount");
                
                currentExpenseAggregatedSum = ResourceFactory.createProperty(elodPrefix + "currentExpenseAggregatedSum");
                currentRevenueAggregatedSum = ResourceFactory.createProperty(elodPrefix + "currentRevenueAggregatedSum");
                
                
                
                regularRevenuesRatio = ResourceFactory.createProperty(elodPrefix + "regularRevenuesRatio");
                regularRevenues = ResourceFactory.createProperty(elodPrefix + "regularRevenues");
                windfallRevenuesRatio = ResourceFactory.createProperty(elodPrefix + "windfallRevenuesRatio");
                windfallRevenues = ResourceFactory.createProperty(elodPrefix + "windfallRevenues");
                pfyInvoicedRevenues = ResourceFactory.createProperty(elodPrefix + "pfyInvoicedRevenues");
                equityRatio = ResourceFactory.createProperty(elodPrefix + "equityRatio");
                totalRevenues = ResourceFactory.createProperty(elodPrefix + "totalRevenues");
                cashBalance = ResourceFactory.createProperty(elodPrefix + "cashBalance");
                externalRevenues = ResourceFactory.createProperty(elodPrefix + "externalRevenues");
                investSubsLoans = ResourceFactory.createProperty(elodPrefix + "investSubsLoans");
                totalExpenses = ResourceFactory.createProperty(elodPrefix + "totalExpenses");
                expensesUse = ResourceFactory.createProperty(elodPrefix + "expensesUse");
                personnelCosts = ResourceFactory.createProperty(elodPrefix + "personnelCosts");
                totalEmploymentCosts = ResourceFactory.createProperty(elodPrefix + "totalEmploymentCosts");
                amortizationCosts = ResourceFactory.createProperty(elodPrefix + "amortizationCosts");
                optionalSubsidies = ResourceFactory.createProperty(elodPrefix + "optionalSubsidies");
                investmentRatio = ResourceFactory.createProperty(elodPrefix + "investmentRatio");
                pfyLiabilities = ResourceFactory.createProperty(elodPrefix + "pfyLiabilities");
                financialForecasts = ResourceFactory.createProperty(elodPrefix + "financialForecasts");
                reserves = ResourceFactory.createProperty(elodPrefix + "reserves");
                pfyExpenses = ResourceFactory.createProperty(elodPrefix + "pfyExpenses");
                projectSubsidies = ResourceFactory.createProperty(elodPrefix + "projectSubsidies");
                regularSubsidies = ResourceFactory.createProperty(elodPrefix + "regularSubsidies");
                windfallSubsidies = ResourceFactory.createProperty(elodPrefix + "windfallSubsidies");
                expenseBudget = ResourceFactory.createProperty(elodPrefix + "expenseBudget");
                expenseCommitted = ResourceFactory.createProperty(elodPrefix + "expenseCommitted");
	        expenseAppproval = ResourceFactory.createProperty(elodPrefix + "expenseAppproval");
	        revenueBudget = ResourceFactory.createProperty(elodPrefix + "revenueBudget");
                revenueApproval = ResourceFactory.createProperty(elodPrefix + "revenueApproval");
                revenueSpending = ResourceFactory.createProperty(elodPrefix + "revenueSpending");
                expenseSpending = ResourceFactory.createProperty(elodPrefix + "expenseSpending");
                pfyRest = ResourceFactory.createProperty(elodPrefix + "pfyRest");
                
                

		
	}
    
}
