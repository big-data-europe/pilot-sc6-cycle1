package eu.bde.sc6.budget.parser.base.kalamaria;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.base.thessaloniki.CSVParserUtils;
import eu.bde.sc6.budget.parser.vocabulary.ELOD;
import eu.bde.sc6.budget.parser.vocabulary.GOODRELATIONS;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang.StringUtils;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
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
    
    private final static String IDENTIFIER = ".*(kalamaria/csv/incomes).*";
    private final static Pattern FILENAME_DATE_PATTERN = Pattern.compile(".*([0-9]{4})_([0-9]{2})_([0-9]{2}).*");
    private final static String INSTANCE_NAMESPACE = "http://linkedeconomy.org/resource/kalamaria/incomes/";
    private final static String INSTANCE_KAE_NAMESPACE = "http://linkedeconomy.org/resource/";
    
    
    public List<Statement> transform(String fileName, byte[] file, Set<Integer> unparseableLines) throws TransformationException {
        
        ByteArrayInputStream byteArrayInputStream = null;
        BufferedReader inputBufferedReader = null;
        InputStreamReader inputStreamReader = null;
        
        final CSVFormat csvFormat = CSVFormat
                        .DEFAULT
                        .withDelimiter(';');
            
        List<Statement> data = new ArrayList<>();
        Literal source = new LiteralImpl(fileName);
        try {
            byteArrayInputStream = new ByteArrayInputStream(file);
            inputStreamReader = new InputStreamReader(byteArrayInputStream, "UTF-16");
            inputBufferedReader = new BufferedReader(inputStreamReader);
            
            final AtomicInteger count = new AtomicInteger();
            String line;
            String lineCache = null;
            while((line = inputBufferedReader.readLine())!=null){
                line = lineCache!=null?lineCache.concat(line):line;
                String[] columns = line.split(Character.toString(csvFormat.getDelimiter()));
                if(columns.length<9){
                    lineCache = line;
                    continue;
                } else { lineCache = null; }
           // }
            //inputBufferedReader.lines().forEach(line -> {
                
             //   String[] columns = line.split("\";\"");

                String kae = columns[0];
                
                if(StringUtils.countMatches(kae, ".") > 0){
                    
                    String subject = columns[1];
                    String budgetformatted = columns[5];
                    String confirmed = columns[6];
                    String collected = fileName.contains("2015")?columns[8]:columns[7];
                    
                    if (!kae.contains("Κ")) {
                        
                        String customKae = kae;
                        String kaeService = null;
                        String oneDigit = null;
                        String twoDigit = null;
                        String threeDigit = null;
                        String fourDigit = null;
                        String[] customKaeParts = customKae.split("\\.");
                        if (StringUtils.countMatches(kae, ".") == 1 && customKaeParts[1].length() > 3) {
                        
                            int i = count.getAndIncrement();
                            kaeService = kae.substring(0, 2);
                            oneDigit = kae.substring(3, 4);
                            twoDigit = kae.substring(3, 5);
                            threeDigit = kae.substring(3, 6);
                            fourDigit = kae.substring(3, 7);
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

                            String budget2 = budgetformatted.replace(".", "");
                            String budgettNew = budget2.replace(",", ".");

                            String approval2 = confirmed.replace(".", "");
                            String approvalNew = approval2.replace(",", ".");
                            
                            //Creation of Resources
                            URI instanceKAE = ValueFactoryImpl.getInstance().createURI(INSTANCE_KAE_NAMESPACE + "KAE/"
                                    + year.toString() + "/Income/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                                    + "/" + fourDigit);

                            URI instanceKAECustom = ValueFactoryImpl.getInstance().createURI(INSTANCE_KAE_NAMESPACE + "KAE/"
                                    + year.toString() + "/Income/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                                    + "/" + fourDigit + "/" + kaeService);
                            
                            URI instanceBudgetItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "BudgetItem/"
                                    + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/"
                                    + i); 
                    
                            URI instanceCollectedItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "CollectedItem/"
                                    + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/" + i);  

                            URI instanceRevenueRecognizedItem = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "RevenueRecognizedItem/"
                                    + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/" 
                                    + i);

                            URI instanceBudgetUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                                    + "BudgetItem/" + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/"
                                    + i);
                            
                            URI instanceCollectedUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                                    + "CollectedItem/" + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/"
                                    + i);
                            URI instanceRevenueRecognizedUps = ValueFactoryImpl.getInstance().createURI(INSTANCE_NAMESPACE + "UnitPriceSpecification/"
                                    + "RevenueRecognizedItem/" + year.toString() + "/" + day + "-" + month + "/"
                                    + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                                    + kaeService + "/"
                                    + i);
                            

                            URI instanceCurrency = ValueFactoryImpl.getInstance().createURI("http://linkedconomy.org/resource/Currency/EUR");

                            URI instanceOrganization = ValueFactoryImpl.getInstance().createURI(
                                    "http://linkedeconomy.org/resource/Organization/090226243");

                            //Resources' s type declarations

                            data.add(new StatementImpl(instanceKAECustom, RDF.TYPE, ELOD.CUSTOM_KAE));
                            data.add(new StatementImpl(instanceBudgetItem, RDF.TYPE, ELOD.BUDGET_ITEM));
                            data.add(new StatementImpl(instanceBudgetUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                            data.add(new StatementImpl(instanceCollectedItem, RDF.TYPE, ELOD.COLLECTED_ITEM));                                                        
                            data.add(new StatementImpl(instanceCollectedUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));                            
                            data.add(new StatementImpl(instanceRevenueRecognizedItem, RDF.TYPE, ELOD.REVENUE_RECOGNIZED_ITEM));
                            data.add(new StatementImpl(instanceRevenueRecognizedUps, RDF.TYPE, GOODRELATIONS.UNIT_PRICE_SPECIFICATION));
                            data.add(new StatementImpl(instanceCurrency, RDF.TYPE, ELOD.CURRENCY));

                            /* source file as dcterms:source */

                            data.add(new StatementImpl(instanceKAECustom, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceBudgetItem, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceBudgetUps, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceCollectedItem, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceCollectedUps, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceRevenueRecognizedItem, DCTERMS.SOURCE, source));
                            data.add(new StatementImpl(instanceRevenueRecognizedUps, DCTERMS.SOURCE, source));
                            
                            //Properties addition to Resources
                            data.add(new StatementImpl(instanceBudgetItem, ELOD.SELLER, instanceOrganization));
                            data.add(new StatementImpl(instanceCollectedItem, ELOD.SELLER, instanceOrganization));
                            data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.SELLER, instanceOrganization));
                            

                            data.add(new StatementImpl(instanceBudgetItem, ELOD.PRICE, instanceBudgetUps));
                            data.add(new StatementImpl(instanceCollectedItem, ELOD.PRICE, instanceCollectedUps));
                            data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.PRICE, instanceRevenueRecognizedUps));
                            
                            data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_KAE, instanceKAE));
                            data.add(new StatementImpl(instanceCollectedItem, ELOD.HAS_KAE, instanceKAE));
                            data.add(new StatementImpl(instanceRevenueRecognizedItem, ELOD.HAS_KAE, instanceKAE));

                            data.add(new StatementImpl(instanceBudgetItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
                            data.add(new StatementImpl(instanceCollectedItem, ELOD.HAS_CUSTOM_KAE, instanceKAECustom));
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

                            data.add(new StatementImpl(instanceBudgetUps, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(budgettNew, XMLSchema.DECIMAL)));
                            data.add(new StatementImpl(instanceCollectedUps, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(collectNew, XMLSchema.DECIMAL)));
                            data.add(new StatementImpl(instanceRevenueRecognizedUps, GOODRELATIONS.HAS_CURRENCY_VALUE, ValueFactoryImpl.getInstance().createLiteral(approvalNew, XMLSchema.DECIMAL)));

                            data.add(new StatementImpl(instanceBudgetUps, ELOD.HAS_CURRENCY, instanceCurrency));
                            data.add(new StatementImpl(instanceCollectedUps, ELOD.HAS_CURRENCY, instanceCurrency));
                            data.add(new StatementImpl(instanceRevenueRecognizedUps, ELOD.HAS_CURRENCY, instanceCurrency));

                            data.add(new StatementImpl(instanceKAECustom, ELOD.KAE, ValueFactoryImpl.getInstance().createLiteral(customKae)));
                            
                        }
                    }
                }                
            }//);
            
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
            if(inputBufferedReader != null){
                try {
                    inputBufferedReader.close();
                } catch (IOException ex) {}
            }
        }
        return data;               
    }
    
    @Override
    public List<Statement> transform(String fileName, byte[] file) throws TransformationException {
        return transform(fileName, file, new HashSet<>());
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
