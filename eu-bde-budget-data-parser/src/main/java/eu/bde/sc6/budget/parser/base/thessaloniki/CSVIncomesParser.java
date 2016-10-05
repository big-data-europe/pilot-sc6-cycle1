package eu.bde.sc6.budget.parser.base.thessaloniki;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import eu.bde.sc6.budget.parser.api.TransformationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.openrdf.model.Statement;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class CSVIncomesParser implements BudgetDataParser {
    
    private final static String IDENTIFIER = "thessaloniki.incomes.csv";
    
    @Override
    public List<Statement> transform(String fileName, byte[] file) throws TransformationException {
        CSVParser parser = null;
        InputStreamReader inputStreamReader = null;
        ByteArrayInputStream byteArrayInputStream = null;
        
        try {
            byteArrayInputStream = new ByteArrayInputStream(file);
            inputStreamReader = new InputStreamReader(byteArrayInputStream, "UTF-16");
            parser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT);
            parser.forEach( csvRecord -> {
                System.err.println(csvRecord.get(0));
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
        return new ArrayList<>();
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

}
