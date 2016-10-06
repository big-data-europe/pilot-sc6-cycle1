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
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
public class CSVIncomesParser implements BudgetDataParser {
    
    private final static String IDENTIFIER = ".*(thessaloniki/csv/incomes).*";
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
            parser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT.withDelimiter(';').withAllowMissingColumnNames().withFirstRecordAsHeader());

            final AtomicInteger count = new AtomicInteger();

            parser.forEach( csvRecord -> {
                
                String kae = csvRecord.get(1);
                String subject = csvRecord.get(2);
                String budgetformat = csvRecord.get(4);
                String confirmed = csvRecord.get(5);
                String collected = csvRecord.get(6);
                
                if(!kae.contains("K")){
                    
                    int i = count.getAndIncrement();
                            
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
                    String collect = collected.replace(".", "");
                    String collectNew = collect.replace(",", ".");

                    String budget2 = budgetformat.replace(".", "");
                    String budgettNew = budget2.replace(",", ".");

                    String approval2 = confirmed.replace(".", "");
                    String approvalNew = approval2.replace(",", ".");
                    
                    URI instanceKAE = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "KAE/"
                            + year + "/Income/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit);
                    
                    URI instanceKAECustom = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "KAE/"
                            + year + "/Income/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit + "/" + fifthLevelOfKae1 + "/" + fifthLevelOfKae2);
                    
                    URI instanceBudgetItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "BudgetItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i); 
                    
                    URI instanceCollectedItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "CollectedItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);  
                    
                    URI instanceRevenueRecognizedItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "RevenueRecognizedItem/"
                            + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceBudgetUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "BudgetItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceCollectedUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "CollectedItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);  
                    
                    URI instanceRevenueRecognizedUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                            + "RevenueRecognizedItem/" + year.toString() + "/" + month + "-" + year + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    URI instanceCurrency = ValueFactoryImpl.getInstance().createURI("http://linkedconomy.org/resource/Currency/EUR");

                    URI instanceOrganization = ValueFactoryImpl.getInstance().createURI(
                            "http://linkedeconomy.org/resource/Organization/998082845");   
                    
                    data.add(new StatementImpl(instanceKAECustom, RDF.TYPE, ELOD.CUSTOM_KAE));
                    data.add(new StatementImpl(instanceBudgetItem, RDF.TYPE, ELOD.BUDGET_ITEM));
                    data.add(new StatementImpl(instanceCollectedItem, RDF.TYPE, ELOD.COLLECTED_ITEM));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, RDF.TYPE, ELOD.REVENUE_RECOGNIZED_ITEM));
                    data.add(new StatementImpl(instanceCollectedUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceBudgetUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceRevenueRecognizedUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                    data.add(new StatementImpl(instanceCurrency, RDF.TYPE, ELOD.CURRENCY));
                    
                    //Properties addition to Resources
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.SELLER, instanceOrganization));
                    data.add(new StatementImpl(instanceCollectedItem, ELOD.SELLER, instanceOrganization));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.SELLER, instanceOrganization));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.PRICE, instanceBudgetUps));
                    data.add(new StatementImpl(instanceCollectedItem, ELOD.PRICE, instanceBudgetUps));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.PRICE, instanceBudgetUps));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_KAE, instanceKAE));
                    data.add(new StatementImpl(instanceCollectedItem, ELOD.HAS_KAE, instanceKAE));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.HAS_KAE, instanceKAE));
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    data.add(new StatementImpl(instanceCollectedItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                    
                    data.add(new StatementImpl(instanceBudgetItem, DCTERMS.ISSUED, dateTime));
                    data.add(new StatementImpl(instanceCollectedItem, DCTERMS.ISSUED, dateTime));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, DCTERMS.ISSUED, dateTime));
                    
                    data.add(new StatementImpl(instanceBudgetItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));
                    data.add(new StatementImpl(instanceCollectedItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));                    
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, DCTERMS.SUBJECT, ValueFactoryImpl.getInstance().createLiteral(subject)));                    
                    
                    data.add(new StatementImpl(instanceBudgetItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    data.add(new StatementImpl(instanceCollectedItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.FINANCIAL_YEAR, ValueFactoryImpl.getInstance().createLiteral(year, XMLSchema.GYEAR)));
                    
                    data.add(new StatementImpl(instanceBudgetItem, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(budgettNew,XMLSchema.DECIMAL)));
                    data.add(new StatementImpl(instanceCollectedUps, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(collectNew,XMLSchema.DECIMAL)));                    
                    data.add(new StatementImpl(instanceRevenueRecognizedUps, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(approvalNew,XMLSchema.DECIMAL)));                    
                    
                    data.add(new StatementImpl(instanceBudgetUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    data.add(new StatementImpl(instanceCollectedUps, ELOD.HAS_CURRENCY, instanceCurrency));
                    data.add(new StatementImpl(instanceRevenueRecognizedUps, ELOD.HAS_CURRENCY, instanceCurrency));

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
