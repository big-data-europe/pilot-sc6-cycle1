package eu.bde.sc6.budget.parser.base.thessaloniki;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.budget.parser.vocabulary.GOODRELATIONS;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CSVExpensesParser implements BudgetDataParser {
    
    private final static String IDENTIFIER = ".*(thessaloniki/csv/expenses).*";
    private final static Pattern FILENAME_DATE_PATTERN = Pattern.compile("([0-9]{4})_([0-9]{2})_([0-9]{2}).*");
    private final static String INSTANCE_NAMESPACE = "http://linkedeconomy.org/resource/";
        
    @Override
    public List<Statement> transform(String fileName, byte[] file) throws TransformationException {
        CSVParser parser = null;
        InputStreamReader inputStreamReader = null;
        ByteArrayInputStream byteArrayInputStream = null;
        List<Statement> data = new ArrayList<>();
        try {
            byteArrayInputStream = new ByteArrayInputStream(file);
            inputStreamReader = new InputStreamReader(byteArrayInputStream, "UTF-16");
            parser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT.withDelimiter(';'));
                        
            final AtomicInteger count = new AtomicInteger();

            parser.forEach( csvRecord -> {
                
                String kae = csvRecord.get(1);
                String subject = csvRecord.get(2);
                String budgetformatted = csvRecord.get(4);
                String committed = csvRecord.get(5);
                String approval = csvRecord.get(6);
                String spending = csvRecord.get(7);
                
                if (!kae.contains("Κ")) {
                    
                    int i = count.getAndIncrement();

                    //kae division
                    String customKae = kae;
                    String oneDigit = kae.substring(0, 1);
                    String twoDigit = kae.substring(0, 2);
                    String threeDigit = kae.substring(0, 3);
                    String fourDigit = kae.substring(0, 4);
                    String fifthLevelOfKae1 = kae.substring(5, 7);
                    String fifthLevelOfKae2 = kae.substring(8, 10);
                    
                    // date from fileName
                    Matcher matcher = FILENAME_DATE_PATTERN.matcher(fileName);
                    String year = "2015";
                    String month = "01";
                    String day = "01";
                    
                    if(matcher.matches()){
                        year = matcher.group(1);
                        month = matcher.group(2);
                        day = matcher.group(3);                        
                    } 
                    
                    Literal dateTime = CSVParserUtils.createDateTimeLiteral(year, month, day);
                    
                    //format financial items
                    String collect = spending.replace(".", "");
                    String collectNew = collect.replace(",", ".");

                    String budget2 = budgetformatted.replace(".", "");
                    String budgettNew = budget2.replace(",", ".");

                    String approval2 = approval.replace(".", "");
                    String approvalNew = approval2.replace(",", ".");

                    String committed2 = committed.replace(".", "");
                    String committedNew = committed2.replace(",", ".");
                     
                    //Creation of Resources
                    URI instanceKAE = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "KAE/"
                            + year.toString() + "/Expense/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit);

                    URI instanceKAECustom = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "KAE/"
                            + year.toString() + "/Expense/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit + "/" + fifthLevelOfKae1 + "/" + fifthLevelOfKae2);

                    URI instanceBudgetItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "BudgetItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);  
                    
                    URI instanceSpendingItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "SpendingItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceExpenseItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "ExpenseApprovalItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceCommittedItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "CommittedItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceSpendingExpLine = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "ExpenditureLine/"
                            + "SpendingItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceExpenseExpLine = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "ExpenditureLine/"
                            + "ExpenseApprovalItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceBudgetUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "BudgetItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceSpendingUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "SpendingItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceExpenseUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "ExpenseApprovalItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceCommittedUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "CommittedItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceCurrency = ValueFactoryImpl.getInstance().createURI("http://linkedconomy.org/resource/Currency/EUR");

                    URI instanceOrganization = ValueFactoryImpl.getInstance().createURI(
                            "http://linkedeconomy.org/resource/Organization/998082845");

                    //Resources' s type declarations
                    
                    data.add(new StatementImpl(instanceKAECustom, RDF.TYPE, ELOD.CUSTOM_KAE));
                    data.add(new StatementImpl(instanceBudgetItem, RDF.TYPE, ELOD.BUDGET_ITEM));
                    data.add(new StatementImpl(instanceSpendingItem, RDF.TYPE, ELOD.SPENDING_ITEM));
                    data.add(new StatementImpl(instanceExpenseItem, RDF.TYPE, ELOD.EXPENSE_APPROVAL_ITEM));
                    data.add(new StatementImpl(instanceCommittedItem, RDF.TYPE, ELOD.COMMITTED_ITEM));
                    data.add(new StatementImpl(instanceSpendingExpLine, RDF.TYPE, ELOD.EXPENDITURE_LINE));
                    data.add(new StatementImpl(instanceExpenseExpLine, RDF.TYPE, ELOD.EXPENDITURE_LINE));
                    data.add(new StatementImpl(instanceSpendingUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceBudgetUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceExpenseUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceCommittedUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceCurrency, RDF.TYPE, ELOD.CURRENCY));

                    //Properties addition to Resources
                    data.add(new StatementImpl(instanceSpendingItem, ELOD.HAS_EXPENDITURE_LINE, instanceSpendingExpLine));
                    data.add(new StatementImpl(instanceExpenseItem, ELOD.HAS_EXPENDITURE_LINE, instanceExpenseExpLine));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.BUYER, instanceOrganization));
                    data.add(new StatementImpl(instanceSpendingItem, ELOD.BUYER, instanceOrganization));
                    data.add(new StatementImpl(instanceExpenseItem, ELOD.BUYER, instanceOrganization));
                    data.add(new StatementImpl(instanceCommittedItem, ELOD.BUYER, instanceOrganization));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.PRICE, instanceBudgetUps));
                    data.add(new StatementImpl(instanceSpendingExpLine, ELOD.AMOUNT, instanceSpendingUps));
                    data.add(new StatementImpl(instanceExpenseExpLine, ELOD.AMOUNT, instanceExpenseUps));
                    data.add(new StatementImpl(instanceCommittedItem, ELOD.PRICE, instanceCommittedUps));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_KAE, instanceKAE));
                    data.add(new StatementImpl(instanceSpendingExpLine, ELOD.HAS_KAE, instanceKAE));
                    data.add(new StatementImpl(instanceExpenseExpLine, ELOD.HAS_KAE, instanceKAE));
                    data.add(new StatementImpl(instanceCommittedItem, ELOD.HAS_KAE, instanceKAE));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    data.add(new StatementImpl(instanceSpendingExpLine, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    data.add(new StatementImpl(instanceExpenseExpLine, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    data.add(new StatementImpl(instanceCommittedItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    
                    data.add(new StatementImpl(instanceBudgetItem, DCTERMS.ISSUED, dateTime));
                    data.add(new StatementImpl(instanceSpendingItem, DCTERMS.ISSUED, dateTime));
                    data.add(new StatementImpl(instanceExpenseItem, DCTERMS.ISSUED, dateTime));
                    data.add(new StatementImpl(instanceCommittedItem, DCTERMS.ISSUED, dateTime));
                    
                    data.add(new StatementImpl(instanceBudgetItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));
                    data.add(new StatementImpl(instanceSpendingItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));
                    data.add(new StatementImpl(instanceExpenseItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));
                    data.add(new StatementImpl(instanceCommittedItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));

                    data.add(new StatementImpl(instanceBudgetItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    data.add(new StatementImpl(instanceSpendingItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    data.add(new StatementImpl(instanceExpenseItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    data.add(new StatementImpl(instanceCommittedItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    
                    data.add(new StatementImpl(instanceBudgetItem, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(budgettNew, XMLSchema.DECIMAL)));
                    data.add(new StatementImpl(instanceSpendingItem, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(collectNew, XMLSchema.DECIMAL)));
                    data.add(new StatementImpl(instanceExpenseItem, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(approvalNew, XMLSchema.DECIMAL)));
                    data.add(new StatementImpl(instanceCommittedItem, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(committedNew, XMLSchema.DECIMAL)));
                    
                    data.add(new StatementImpl(instanceBudgetUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    data.add(new StatementImpl(instanceSpendingUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    data.add(new StatementImpl(instanceExpenseUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    data.add(new StatementImpl(instanceCommittedUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    
                    data.add(new StatementImpl(instanceKAECustom, ELOD.KAE, ValueFactoryImpl.getInstance().createLiteral(customKae)));
                    
                }
                
                
            });
        } catch (IOException ex) {
            throw new TransformationException(ex);
        } finally {
            if(inputStreamReader!=null){
                try {
                    inputStreamReader.close();
                } catch (IOException ex) {}
            }
            if(byteArrayInputStream!=null){
                try {
                    byteArrayInputStream.close();
                } catch (IOException ex) {}
            }   
            if(parser != null){
                try {
                    parser.close();
                } catch (IOException ex) {}
            }
        }
        return data;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public boolean canHandleByFileName(String fileName) {
        return fileName.matches(IDENTIFIER);
    }

}
