package csvToRdf;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import ontology.Ontology;
import utils.HelperMethods;

/**
 *
 * @author giorgos, lefteris
 */
public class CsvExpenses {

    public void executeCsvExpenses() {

        //Generic Declarations      
        HelperMethods hm = new HelperMethods();
        Integer year = hm.getCurrentYear();
        String currentDate = hm.getCurrentDate();

        // CSV Declarations
        BufferedReader br = null;
        String line = "";
        String splitBy = ";";
        File csvFileToRead = new File("/home/turnguard/code/workspace_bde/pilot-sc6-cycle1/eu-bde-sc6-parser/Input CSV/Expenses/2016/06June/2016_06_01_21.29.csv");
        System.out.println("Input -expense csv file: " + csvFileToRead.getName());
        // RDF Declarations
        Model model = ModelFactory.createDefaultModel();
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        model.setNsPrefix("elod", Ontology.elodPrefix);
        model.setNsPrefix("gr", Ontology.goodRelationsPrefix);
        model.setNsPrefix("dcterms", Ontology.dctermsPrefix);

        try {
            int i = 1;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFileToRead), "UTF16"));
            while (((line = br.readLine()) != null)) {
                i++;
                String[] dataGov = line.split(splitBy);
                String kae = dataGov[1];
                String subject = dataGov[2];
                String budgetformatted = dataGov[4];
                String committed = dataGov[5];
                String approval = dataGov[6];
                String spending = dataGov[7];

                if (!kae.contains("Κ")) {

                    //kae division
                    String customKae = kae;
                    String oneDigit = kae.substring(0, 1);
                    String twoDigit = kae.substring(0, 2);
                    String threeDigit = kae.substring(0, 3);
                    String fourDigit = kae.substring(0, 4);
                    String fifthLevelOfKae1 = kae.substring(5, 7);
                    String fifthLevelOfKae2 = kae.substring(8, 10);

                    //read file name for date
                    String fileName = csvFileToRead.getName();
                    String dateFormat = fileName.substring(0, 10);
                    String dateFormatted = dateFormat.replace("_", "-");
                    String dateFormatNew = dateFormat.substring(5, 10);
                    List<String> items = Arrays.asList(dateFormatNew.split("_"));
                    String dateuri = items.get(1) + "-" + items.get(0);
                    if (csvFileToRead.getName().contains("2015")) {
                        year = 2015;
                    }

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
                    Resource instanceKAE = infModel.createResource(Ontology.instancePrefix + "KAE/"
                            + year.toString() + "/Expense/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit);

                    Resource instanceKAECustom = infModel.createResource(Ontology.instancePrefix + "KAE/"
                            + year.toString() + "/Expense/" + oneDigit + "/" + twoDigit + "/" + threeDigit
                            + "/" + fourDigit + "/" + fifthLevelOfKae1 + "/" + fifthLevelOfKae2);

                    Resource instanceBudgetItem = infModel.createResource(Ontology.instancePrefix + "BudgetItem/"
                            + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceSpendingItem = infModel.createResource(Ontology.instancePrefix + "SpendingItem/"
                            + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceExpenseItem = infModel.createResource(Ontology.instancePrefix + "ExpenseApprovalItem/"
                            + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceCommittedItem = infModel.createResource(Ontology.instancePrefix + "CommittedItem/"
                            + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceSpendingExpLine = infModel.createResource(Ontology.instancePrefix + "ExpenditureLine/"
                            + "SpendingItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceExpenseExpLine = infModel.createResource(Ontology.instancePrefix + "ExpenditureLine/"
                            + "ExpenseApprovalItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceBudgetUps = infModel.createResource(Ontology.instancePrefix + "UnitPriceSpecification/"
                            + "BudgetItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceSpendingUps = infModel.createResource(Ontology.instancePrefix + "UnitPriceSpecification/"
                            + "SpendingItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceExpenseUps = infModel.createResource(Ontology.instancePrefix + "UnitPriceSpecification/"
                            + "ExpenseApprovalItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceCommittedUps = infModel.createResource(Ontology.instancePrefix + "UnitPriceSpecification/"
                            + "CommittedItem/" + year.toString() + "/" + dateuri + "/"
                            + oneDigit + "/" + twoDigit + "/" + threeDigit + "/" + fourDigit + "/"
                            + fifthLevelOfKae1 + "/" + fifthLevelOfKae2 + "/"
                            + i);

                    Resource instanceCurrency = infModel.createResource("http://linkedconomy.org/resource/Currency/EUR");

                    Resource instanceOrganization = infModel.createResource(
                            "http://linkedeconomy.org/resource/Organization/998082845");

                    //Resources' s type declarations
                    infModel.add(instanceKAECustom, RDF.type, Ontology.customKaeResource);
                    infModel.add(instanceBudgetItem, RDF.type, Ontology.budgetItemResource);
                    infModel.add(instanceSpendingItem, RDF.type, Ontology.spendingItemResource);
                    infModel.add(instanceExpenseItem, RDF.type, Ontology.expenseItemResource);
                    infModel.add(instanceCommittedItem, RDF.type, Ontology.committedItemResource);
                    infModel.add(instanceSpendingExpLine, RDF.type, Ontology.expLineResource);
                    infModel.add(instanceExpenseExpLine, RDF.type, Ontology.expLineResource);
                    infModel.add(instanceSpendingUps, RDF.type, Ontology.upsResource);
                    infModel.add(instanceBudgetUps, RDF.type, Ontology.upsResource);
                    infModel.add(instanceExpenseUps, RDF.type, Ontology.upsResource);
                    infModel.add(instanceCommittedUps, RDF.type, Ontology.upsResource);
                    infModel.add(instanceCurrency, RDF.type, Ontology.currencyResource);

                    //Properties addition to Resources
                    instanceSpendingItem.addProperty(Ontology.hasExpenditureLine, instanceSpendingExpLine);
                    instanceExpenseItem.addProperty(Ontology.hasExpenditureLine, instanceExpenseExpLine);

                    instanceBudgetItem.addProperty(Ontology.buyer, instanceOrganization);
                    instanceSpendingItem.addProperty(Ontology.buyer, instanceOrganization);
                    instanceExpenseItem.addProperty(Ontology.buyer, instanceOrganization);
                    instanceCommittedItem.addProperty(Ontology.buyer, instanceOrganization);

                    instanceBudgetItem.addProperty(Ontology.price, instanceBudgetUps);
                    instanceSpendingExpLine.addProperty(Ontology.amount, instanceSpendingUps);
                    instanceExpenseExpLine.addProperty(Ontology.amount, instanceExpenseUps);
                    instanceCommittedItem.addProperty(Ontology.price, instanceCommittedUps);

                    instanceBudgetItem.addProperty(Ontology.hasKae, instanceKAE);
                    instanceSpendingExpLine.addProperty(Ontology.hasKae, instanceKAE);
                    instanceExpenseExpLine.addProperty(Ontology.hasKae, instanceKAE);
                    instanceCommittedItem.addProperty(Ontology.hasKae, instanceKAE);

                    instanceBudgetItem.addProperty(Ontology.hasCustomKae, instanceKAECustom);
                    instanceSpendingExpLine.addProperty(Ontology.hasCustomKae, instanceKAECustom);
                    instanceExpenseExpLine.addProperty(Ontology.hasCustomKae, instanceKAECustom);
                    instanceCommittedItem.addProperty(Ontology.hasCustomKae, instanceKAECustom);

                    instanceBudgetItem.addProperty(Ontology.issued, dateFormatted, XSDDatatype.XSDdateTime);
                    instanceSpendingItem.addProperty(Ontology.issued, dateFormatted, XSDDatatype.XSDdateTime);
                    instanceExpenseItem.addProperty(Ontology.issued, dateFormatted, XSDDatatype.XSDdateTime);
                    instanceCommittedItem.addProperty(Ontology.issued, dateFormatted, XSDDatatype.XSDdateTime);

                    instanceBudgetItem.addProperty(Ontology.subject, subject, XSDDatatype.XSDstring);
                    instanceSpendingItem.addProperty(Ontology.subject, subject, XSDDatatype.XSDstring);
                    instanceExpenseItem.addProperty(Ontology.subject, subject, XSDDatatype.XSDstring);
                    instanceCommittedItem.addProperty(Ontology.subject, subject, XSDDatatype.XSDstring);

                    instanceBudgetItem.addProperty(Ontology.financialYear, year.toString(), XSDDatatype.XSDgYear);
                    instanceSpendingItem.addProperty(Ontology.financialYear, year.toString(), XSDDatatype.XSDgYear);
                    instanceExpenseItem.addProperty(Ontology.financialYear, year.toString(), XSDDatatype.XSDgYear);
                    instanceCommittedItem.addProperty(Ontology.financialYear, year.toString(), XSDDatatype.XSDgYear);

                    instanceBudgetUps.addProperty(Ontology.hasCurrencyValue, String.valueOf(budgettNew), XSDDatatype.XSDfloat);
                    instanceSpendingUps.addProperty(Ontology.hasCurrencyValue, String.valueOf(collectNew), XSDDatatype.XSDfloat);
                    instanceExpenseUps.addProperty(Ontology.hasCurrencyValue, String.valueOf(approvalNew), XSDDatatype.XSDfloat);
                    instanceCommittedUps.addProperty(Ontology.hasCurrencyValue, String.valueOf(committedNew), XSDDatatype.XSDfloat);

                    instanceBudgetUps.addProperty(Ontology.hasCurrency, instanceCurrency);
                    instanceSpendingUps.addProperty(Ontology.hasCurrency, instanceCurrency);
                    instanceExpenseUps.addProperty(Ontology.hasCurrency, instanceCurrency);
                    instanceCommittedUps.addProperty(Ontology.hasCurrency, instanceCurrency);

                    instanceKAECustom.addProperty(Ontology.kae, String.valueOf(customKae), XSDDatatype.XSDstring);
                }

            }

        } catch (Exception err) {
            System.out.println("Exception caught" + err.getMessage());
        }
        try {
            BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/turnguard/Downloads/2016_06_01_21.29.rdf"), "UTF8"));
            model.write(fout);
            System.out.println("Transformation of expense csv to rdf file completed!!!!");
        } catch (IOException e) {
            System.out.println("Exception caught" + e.getMessage());
        }

    }

}
