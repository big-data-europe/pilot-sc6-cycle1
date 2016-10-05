package eu.bde.sc6.budget.parser.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ELOD {
    
    public static final String NAMESPACE = "http://linkedeconomy.org/ontology#";
    
    /* CLASSES */
    public static final URI CUSTOM_KAE = new URIImpl(NAMESPACE.concat("KaeThessaloniki"));
    public static final URI SPENDING_ITEM = new URIImpl(NAMESPACE.concat("SpendingItem"));
    public static final URI COLLECTED_ITEM = new URIImpl(NAMESPACE.concat("CollectedItem"));
    public static final URI BUDGET_ITEM = new URIImpl(NAMESPACE.concat("BudgetItem"));
    public static final URI EXPENSE_APPROVAL_ITEM = new URIImpl(NAMESPACE.concat("ExpenseApprovalItem"));
    public static final URI REVENUE_RECOGNIZED_ITEM = new URIImpl(NAMESPACE.concat("RevenueRecognizedItem"));
    public static final URI EXPENDITURE_LINE = new URIImpl(NAMESPACE.concat("ExpenditureLine"));
    public static final URI CURRENCY = new URIImpl(NAMESPACE.concat("Currency"));
    public static final URI COMMITTED_ITEM = new URIImpl(NAMESPACE.concat("CommittedItem"));
    public static final URI YEAR = new URIImpl(NAMESPACE.concat("Year"));
    public static final URI MONTH = new URIImpl(NAMESPACE.concat("Month"));
    public static final URI STATISTIC = new URIImpl(NAMESPACE.concat("Statistic"));
    
    /* PREDICATES */
    public static final URI HAS_EXPENDITURE_LINE = new URIImpl(NAMESPACE.concat("hasExpenditureLine"));
    public static final URI AMOUNT = new URIImpl(NAMESPACE.concat("amount"));
    public static final URI PRICE = new URIImpl(NAMESPACE.concat("price"));
    public static final URI SELLER = new URIImpl(NAMESPACE.concat("seller"));
    public static final URI BUYER = new URIImpl(NAMESPACE.concat("buyer"));
    public static final URI HAS_CURRENCY = new URIImpl(NAMESPACE.concat("hasCurrency"));
    public static final URI HAS_KAE = new URIImpl(NAMESPACE.concat("hasKae"));
    public static final URI HAS_CUSTOM_KAE = new URIImpl(NAMESPACE.concat("hasCustomKae"));    
    
    public static final URI FINANCIAL_YEAR = new URIImpl(NAMESPACE.concat("financialYear"));    
    public static final URI KAE = new URIImpl(NAMESPACE.concat("kae"));    
    public static final URI HAS_KIND = new URIImpl(NAMESPACE.concat("hasKind"));    
    
    public static final URI PROPERTY = new URIImpl(NAMESPACE.concat("property"));
    public static final URI SUBSIDY = new URIImpl(NAMESPACE.concat("subsidy"));
    public static final URI RETRIBUTIVE = new URIImpl(NAMESPACE.concat("retributive"));
    public static final URI EXCHEQUES = new URIImpl(NAMESPACE.concat("exchequer"));
    public static final URI TAXES_REVENUE = new URIImpl(NAMESPACE.concat("taxesRevenue"));
    public static final URI REST_REVENUE = new URIImpl(NAMESPACE.concat("restRevenue"));
                
    public static final URI SUPPLY = new URIImpl(NAMESPACE.concat("supply"));
    public static final URI REWARD = new URIImpl(NAMESPACE.concat("reward"));
    public static final URI INVESTMENT = new URIImpl(NAMESPACE.concat("investment"));
    public static final URI LOAD = new URIImpl(NAMESPACE.concat("loan"));
    public static final URI TAXES_EXPENSE = new URIImpl(NAMESPACE.concat("taxesExpense"));
    public static final URI REST_EXPENSE = new URIImpl(NAMESPACE.concat("restExpense"));
                
    public static final URI EXPENSE_AGGREGATED_AMOUNT = new URIImpl(NAMESPACE.concat("expenseAggregatedAmount"));
    public static final URI REVENUE_AGGREGATED_AMOUNT = new URIImpl(NAMESPACE.concat("revenueAggregatedAmount"));
                
    public static final URI EXPENSE_AGGREGATED_SUM = new URIImpl(NAMESPACE.concat("expenseAggregatedSum"));
    public static final URI REVENUE_AGGREGATED_SUM = new URIImpl(NAMESPACE.concat("revenueAggregatedSum"));
                
    public static final URI EXPENSE_DIFFERENCE_AMOUNT = new URIImpl(NAMESPACE.concat("expenseDifferenceAmount"));
    public static final URI REVENUE_DIFFERENCE_AMOUNT = new URIImpl(NAMESPACE.concat("revenueDifferenceAmount"));
                
    public static final URI CURRENT_EXPENSE_AGGREGATED_SUM = new URIImpl(NAMESPACE.concat("currentExpenseAggregatedSum"));
    public static final URI CURRENT_REVENUE_AGGREGATED_SUM = new URIImpl(NAMESPACE.concat("currentRevenueAggregatedSum"));
                
    public static final URI REGULAR_REVENUES_RATIO = new URIImpl(NAMESPACE.concat("regularRevenuesRatio"));
    public static final URI REGULAR_REVENUES = new URIImpl(NAMESPACE.concat("regularRevenues"));
    public static final URI WINDFALL_REVENUES_RATION = new URIImpl(NAMESPACE.concat("windfallRevenuesRatio"));
    public static final URI WINDFALL_REVENUES = new URIImpl(NAMESPACE.concat("windfallRevenues"));
    public static final URI PFY_INVOICED_REVENUES = new URIImpl(NAMESPACE.concat("pfyInvoicedRevenues"));
    public static final URI EQUITY_RATIO = new URIImpl(NAMESPACE.concat("equityRatio"));
    public static final URI TOTAL_REVENUES = new URIImpl(NAMESPACE.concat("totalRevenues"));
    public static final URI CASH_BALANCE = new URIImpl(NAMESPACE.concat("cashBalance"));
    public static final URI EXTERNAL_REVENUES = new URIImpl(NAMESPACE.concat("externalRevenues"));
    public static final URI INVEST_SUBS_LOANS = new URIImpl(NAMESPACE.concat("investSubsLoans"));
    public static final URI TOTAL_EXPENSES = new URIImpl(NAMESPACE.concat("totalExpenses"));
    public static final URI EXPENSES_USE = new URIImpl(NAMESPACE.concat("expensesUse"));
    public static final URI PERSONNEL_COSTS = new URIImpl(NAMESPACE.concat("personnelCosts"));
    public static final URI TOTAL_EMPLOYMENTS_COSTS = new URIImpl(NAMESPACE.concat("totalEmploymentCosts"));
    public static final URI AMORTIZATION_COSTS = new URIImpl(NAMESPACE.concat("amortizationCosts"));
    public static final URI OPTIONAL_SUBSIDIES = new URIImpl(NAMESPACE.concat("optionalSubsidies"));
    public static final URI INVESTMENT_RATION = new URIImpl(NAMESPACE.concat("investmentRatio"));
    public static final URI PFY_LIABILITIES = new URIImpl(NAMESPACE.concat("pfyLiabilities"));
    public static final URI FINANCIAL_FORECASTS = new URIImpl(NAMESPACE.concat("financialForecasts"));
    public static final URI RESERVES = new URIImpl(NAMESPACE.concat("reserves"));
    public static final URI PFY_EXPENSES = new URIImpl(NAMESPACE.concat("pfyExpenses"));
    public static final URI PROJECT_SUBSIDIES = new URIImpl(NAMESPACE.concat("projectSubsidies"));
    public static final URI REGULAR_SUBSIDIES = new URIImpl(NAMESPACE.concat("regularSubsidies"));
    public static final URI WINDFALL_SUBSIDIES = new URIImpl(NAMESPACE.concat("windfallSubsidies"));
    public static final URI EXPENSE_BUDGET = new URIImpl(NAMESPACE.concat("expenseBudget"));
    public static final URI EXPENSE_COMMITTED = new URIImpl(NAMESPACE.concat("expenseCommitted"));
    public static final URI EXPENSE_APPROVAL = new URIImpl(NAMESPACE.concat("expenseAppproval"));
    public static final URI REVENUE_BUDGET = new URIImpl(NAMESPACE.concat("revenueBudget"));
    public static final URI REVENUE_APPROVAL = new URIImpl(NAMESPACE.concat("revenueApproval"));
    public static final URI REVENUE_SPENDING = new URIImpl(NAMESPACE.concat("revenueSpending"));
    public static final URI EXPENSE_SPENDING = new URIImpl(NAMESPACE.concat("expenseSpending"));
    public static final URI PFY_REST = new URIImpl(NAMESPACE.concat("pfyRest"));
                    
}
