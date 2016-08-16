package main;

import csvToRdf.CsvExpenses;
import csvToRdf.CsvIncomes;

/**
 *
 * @author giorgos, lefteris
 */
public class Main {

    public static void main(String[] args) {

        CsvIncomes process1 = new CsvIncomes();
        CsvExpenses process2 = new CsvExpenses();

        //run method which tranform income csv to rdf
        process1.executeCsvIncomes();
        //run method which tranform expense csv to rdf
        process2.executeCsvExpenses();

    }

}
