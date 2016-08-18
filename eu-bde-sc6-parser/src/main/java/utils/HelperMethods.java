package utils;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author giorgos, lefteris
 */

public class HelperMethods {
        //returns current year as integer
        
        public int getCurrentYear() {
            int year;
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            return year;
        }
        
        // returns current date as string
        
        public String getCurrentDate() {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(cal.getTime());

            return currentDate;
        }
       
}
