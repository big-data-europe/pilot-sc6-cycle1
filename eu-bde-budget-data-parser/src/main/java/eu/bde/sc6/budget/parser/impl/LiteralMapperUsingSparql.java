/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.bde.sc6.budget.parser.impl;

import eu.bde.sc6.budget.parser.api.BudgetDataParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.Value;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

/**
 * A mapper that, given a list of RDF statements and a PP thesaurus, returns a
 * list of RDF statements where the literals have been substituted, when
 * possible, for URIs of concepts in the thesaurus.
 *
 * The input is scanned for statements of the form ?s P literal where P is in
 * the objects set labelsToSubstitute (given in the constructor). If the literal
 * is found in the PP thesaurus in a statement of the form ?S2 P2 literal where
 * P2 is from the objects matchingPredicates list, then the original statement
 * is substituted in the output for the statement ?s P ?S2 Otherwise, the
 * original statement is left untouched.
 *
 * @author mireleschavezv
 */
public class LiteralMapperUsingSparql {

    private String PPURL;
    private Set<String> labels;
    private SPARQLRepository rep;
    public HttpClient theClient;
    public Header authHeader;
    public LinkedList<String> matchingPredicates;

    public LiteralMapperUsingSparql(String PPHost, String PPProjectName,
            String PPUser,
            String PPPass,
            Set<String> labelsToSubstitute) {

        this.PPURL = PPHost + "/PoolParty/sparql/" + PPProjectName;

        this.theClient = new HttpClient();
        Credentials defaultcreds = new UsernamePasswordCredentials(PPUser, PPPass);
        this.theClient.getState().setCredentials(new AuthScope(PPHost, 80, AuthScope.ANY_REALM), defaultcreds);

        this.matchingPredicates = new LinkedList<>();
        this.matchingPredicates.add("http://www.w3.org/2004/02/skos/core#hiddenLabel");
        this.matchingPredicates.add("http://www.w3.org/2004/02/skos/core#prefLabel");
        this.matchingPredicates.add("http://www.w3.org/2004/02/skos/core#altLabel");

        if (labelsToSubstitute == null) {
            HashSet<String> defaultLabels = new HashSet<>();
            defaultLabels.add(DCTERMS.SUBJECT.toString());
            this.labels = defaultLabels;
        } else {
            this.labels = labelsToSubstitute;
        }
    }

    /*
    For each LITERAL that could be substituted, the thesaurus will be queried
    to find a statement of the form URI P LITERAL, where P is one of the 
    matching Predicates. Use this method to add one more mathcing predcate.
    */
    public void addMatchingPredicate(String s) {
        this.matchingPredicates.add(s);
    }

    /*
       If the input data contains a statement of the form X S LITERAL, where S
    is one of the labels to substitute, substitution will be attempted. Use this
    method to add one more label to substitute.
     */
    public void addLabelToSubstitute(String s) {
        this.labels.add(s);
    }

    /* Checks if a given string has correct parentrhesis
    From https://stackoverflow.com/a/16874430
     */
    private static boolean isParenthesisMatch(String str) {

        Stack<Character> stack = new Stack<Character>();

        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else if (c == '{') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.empty()) {
                    return false;
                } else if (stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }
            } else if (c == '}') {
                if (stack.empty()) {
                    return false;
                } else if (stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    private String stringPreprocessing(String s) {
        String newObj = s.replace("\"", "");
        newObj = newObj.replace("'", "");
        // If the stirng has non-matching parenthesis, they are removed
        if (!this.isParenthesisMatch(newObj)) {
            newObj = newObj.replace("(", "_");
            newObj = newObj.replace(")", "_");
        }
        String objNorm = Normalizer.normalize(newObj, Normalizer.Form.NFD);
        String objPure = StringUtils.stripAccents(objNorm.toLowerCase());
        return (objPure);
    }

    /*
     For a statement to be mappable it must contain a literal in the object, and
    the predicate must be one of self.labels
     */
    private Boolean statementMightBeMappable(Statement s) {
        Value obj = s.getObject();
        if (obj.getClass().getCanonicalName() != LiteralImpl.class.getCanonicalName()) {
            return false;
        }

        String predicate = s.getPredicate().toString();
        if (this.labels.contains(predicate)) {
            return true;
        }

        return false;

    }

    public List<Statement> map(List<Statement> data) {
        Set<String> literals = this.getAllLiterals(data);
        System.out.println((new Integer(literals.size())).toString() + " different literals found");
        Map<String, URIImpl> LiteralUriMap = this.computeLiteralUriMapping(literals);
        HashSet<URIImpl> SetOfUsedURIs = new HashSet<>();

        System.out.println((new Integer(LiteralUriMap.size())).toString() + " literals Mapped");

        List<Statement> result = new LinkedList<Statement>();
        for (Statement s : data) {
            Statement sNew = s;
            if (this.statementMightBeMappable(s)) {
                Value obj = s.getObject();
                String objStr = stringPreprocessing(obj.toString());
                if (LiteralUriMap.containsKey(objStr)) {
                    URIImpl mappedUri = LiteralUriMap.get(objStr);
                    sNew = new StatementImpl(s.getSubject(), s.getPredicate(), mappedUri);
                    SetOfUsedURIs.add(mappedUri);
                }
            }
            result.add(sNew);
        }
        return (result);

    }

    public Set<String> getAllLiterals(List<Statement> data) {
        HashSet<String> listOfLiterals = new HashSet<>();
        for (Statement s : data) {
            Statement sNew = s;
            if (this.statementMightBeMappable(s)) {
                String cleanLiteral = this.stringPreprocessing(s.getObject().toString());
                listOfLiterals.add(cleanLiteral);
            }
        }
        return listOfLiterals;
    }

    private Map<String, URIImpl> computeLiteralUriMapping(Set<String> literals) {
        TreeMap<String, URIImpl> theMap = new TreeMap<>();
        for (String literal : literals) {
            URIImpl uri = this.findPossibleURI(literal);
            if (uri != null) {
                theMap.put(literal, uri);
            }
        }
        return theMap;
    }

    private URIImpl findPossibleURI(String literal) {

        String query = "SELECT ?X\n WHERE {";
        for (int predN = 0; predN < this.matchingPredicates.size(); predN++) {
            String pred = this.matchingPredicates.get(predN);
            query += "\n{?X <" + pred + "> ?o. \n FILTER (regex(?o, '^" + literal + "$') )}\n";
            if (predN < this.matchingPredicates.size() - 1) {
                query += "UNION";
            }

        }
        query += "}";
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            HttpMethod method = new GetMethod(this.PPURL + "?query=" + encodedQuery + "&content-type=application%2Fjson");

            this.theClient.executeMethod(method);
            String S = method.getResponseBodyAsString();
            JSONObject jsonReponse = new JSONObject(S);
            JSONObject res = jsonReponse.getJSONObject("results");
            JSONArray bindings = res.getJSONArray("bindings");
            if (bindings.length() < 1) {
                return null;
            }
            JSONObject first = bindings.getJSONObject(0).getJSONObject("X");
            String val = first.getString("value");

            URIImpl uri = new URIImpl(val);

            return uri;

        } catch (Exception ex) {
            Logger.getLogger(LiteralMapperUsingSparql.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\nOffendingQuery:-------}\n" + query + "\n\n");
            return (null);
        }
    }

}
