package eu.bde.sc6.budget.parser.api;

import java.util.List;
import org.openrdf.model.Statement;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface BudgetDataParser {    
    public List<Statement> transform(String fileName, byte[] file) throws TransformationException;
    public String getIdentifier();
    public boolean canHandleByFileName(String fileName);
}
