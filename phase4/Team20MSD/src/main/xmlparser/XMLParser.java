package main.xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class XMLParser {

    static String CONNECTIONERROR = "Failed!";
    // Create Database in your local machine.
    //static String createDatabase_msddblp = "create database if not exists msddblp";
    // Auto create tables if anyone not exists.
    static String createTable_publiction
            = "create table if not exists tb_authorProfile(title text, authorName text)";
    static String createTable_pbProfile
            = "create table if not exists tb_publication(title text, type text, pbyear integer, pages text, " +
            "journal text, ee text, url text, volume text, booktitle text, isbn text, " +
            "publisher text, editor text, school text, number text)";
    static String createTable_NumberOfPb
            = "create table if not exists tb_NumberOfPb(authorName text, numberOfPb integer)";
    static String createTable_committeeCheck
            = "create table if not exists tb_committeeCheck(authorName text, checkYear integer, role text, committee text)";
    static String create_CandidateTable = "create table if not exists tb_candidate(authorName text)";
    static String create_SaveQueryTable = "create table if not exists tb_savedQueries(query text)";


    static int count_articles = 0;
    static int count_rows = 0;
    static int count_authors = 0;
    static Connection connection = null;
    static NodeList listofarticle = null;
    static NodeList listofinproceeding = null;
    static NodeList listofincollection = null;
    static NodeList listofbook = null;
    static NodeList listofphdthesis = null;
    static NodeList listofwww = null;
    static int total_number_publication = 0;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Start count time!
        long startTime = System.currentTimeMillis();

        // Choose one document to fill into database.
        System.setProperty("jdk.xml.entityExpansionLimit", "0");
        Document xmlDoc = getDocument("./src/main/resources/dblp001.xml");

        // Basic xml data analyze before JDBC connection. All save kinds of nodelist by tag name.
        System.out.println("Root: "+xmlDoc.getDocumentElement().getNodeName());
        listofarticle = xmlDoc.getElementsByTagName("article");
        listofinproceeding = xmlDoc.getElementsByTagName("inproceedings");
        listofincollection = xmlDoc.getElementsByTagName("incollection");
        listofbook = xmlDoc.getElementsByTagName("book");
        listofphdthesis = xmlDoc.getElementsByTagName("phdthesis");
        listofwww = xmlDoc.getElementsByTagName("www");
        total_number_publication = listofarticle.getLength()
                +listofinproceeding.getLength()
                +listofincollection.getLength()
                +listofbook.getLength()
                +listofphdthesis.getLength()
                +listofwww.getLength();

        // JDBC connection

        /*
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp","luliuAWS", "1991715ll");
        System.out.println("\nSuccessful connect with database! Now pushing...");
        */

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/msddblp","postgres", "1991715");
        System.out.println("\nSuccessful connect with database!");


        // Execute queries about create four tables.
        try {
            //PreparedStatement ps = connection.prepareStatement(createDatabase_msddblp);
            // ps.executeUpdate();
            PreparedStatement ps = connection.prepareStatement(createTable_pbProfile);
            ps.executeUpdate();
            ps = connection.prepareStatement(createTable_publiction);
            ps.executeUpdate();
            ps = connection.prepareStatement(createTable_NumberOfPb);
            ps.executeUpdate();
            ps = connection.prepareStatement(createTable_committeeCheck);
            ps.executeUpdate();
            ps = connection.prepareStatement(create_CandidateTable);
            ps.executeUpdate();
            ps = connection.prepareStatement(create_SaveQueryTable);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(CONNECTIONERROR);
            e.printStackTrace();
        }


        // Call method
        if (listofarticle.getLength() != 0){
            System.out.println("\nNow we are saving article's data into database...");
            fillPublicationIntoDB(listofarticle);}
        if (listofinproceeding.getLength() != 0){
            System.out.println("\nNow we are saving inproceeding's data into database...");
            fillPublicationIntoDB(listofinproceeding);}
        if (listofincollection.getLength() != 0){
            System.out.println("\nNow we are saving incollection's data into database...");
            fillPublicationIntoDB(listofincollection);}
        if (listofbook.getLength() != 0){
            System.out.println("\nNow we are saving book's data into database...");
            fillPublicationIntoDB(listofbook);}
        if (listofphdthesis.getLength() != 0){
            System.out.println("\nNow we are saving phdthesis's data into database...");
            fillPublicationIntoDB(listofphdthesis);}
        if (listofwww.getLength() != 0){
            System.out.println("\nNow we are saving www's data into database...");
            fillPublicationIntoDB(listofwww);}

        connection.close();

        // Stop count time!
        long endTime  = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);

        // Display analyze results
        System.out.println("\nDone");
        System.out.println("\nTotal number of articles: " +count_articles);
        System.out.println("\nTotal number of rows in table articles: " +count_rows);
        System.out.println("\nTotal seconds spend: "+seconds);
        System.out.println("\nTotal minutes spend: "+minutes);
        System.out.println("\nTotal number of authors: " +count_authors);

    }

    static String getElementsByTagName(Element el, String tag){
        String result = null;
        try{
            result = el.getElementsByTagName(tag).item(0).getTextContent();
        }
        catch(NullPointerException e){
            result = null;
        }
        finally {
            return result;
        }
    }


    static void fillPublicationIntoDB(NodeList listofpublication){

        // This loop i aim at traversing all articles.
        //for(int i=10001; i<=11000; i++) {
        for(int i=0; i<=total_number_publication-1; i++) {
            count_articles += 1;
            // Get text content based on the different tags in the xml file.
            Element el = (Element) listofpublication.item(i);
            String title = getElementsByTagName(el,"title");
            String pages = getElementsByTagName(el,"pages");
            int year = Integer.MAX_VALUE;
            String temp = getElementsByTagName(el,"pbyear");
            if(temp!=null) year = Integer.parseInt(temp);
            String volume = getElementsByTagName(el,"volume");
            String journal = getElementsByTagName(el,"journal");
            String ee = getElementsByTagName(el,"ee");
            String url = getElementsByTagName(el,"url");
            String number = getElementsByTagName(el,"number");
            String booktitle = getElementsByTagName(el,"booktitle");
            String isbn = getElementsByTagName(el,"isbn");
            String publisher = getElementsByTagName(el,"publisher");
            String editor = getElementsByTagName(el,"editor");
            String school = getElementsByTagName(el,"school");

            boolean executed = false;


            NodeList authorList = null;
            boolean el_is_null = false;
            // This loop j aim at traversing all authors name in one article.
            try{
                authorList = el.getElementsByTagName("author");
            }
            catch(Exception e) {
                el_is_null = true;
            }
            if(el_is_null) continue;

            for(int j = 0; j<authorList.getLength();j++) {
                count_rows += 1;
                String author = authorList.item(j).getTextContent();
                Boolean exist_check = false;
                // In order to shor the size of data. We just add the author and their profile who have committee.
                try {
                    String existCheck = "select exists (select true from tb_committeeCheck where authorname =? )";
                    PreparedStatement ps = connection.prepareStatement(existCheck);
                    ps.setString(1, author);
                    ResultSet rs_existCheck = ps.executeQuery();
                    while(rs_existCheck.next()) {
                        exist_check = rs_existCheck.getBoolean(1);
                    }
                    ps.close();
                } catch (SQLException e) {
                    System.out.println(CONNECTIONERROR);
                    e.printStackTrace();
                }

                if (exist_check == true) {
                    // Fill data into table tb_publication
                    try {
                        String sql = "insert into tb_authorProfile values(?,?)";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, title);
                        ps.setString(2, author);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        System.out.println(CONNECTIONERROR);
                        e.printStackTrace();
                    }
                    // Fill data into table tb_NumberOfPb.
                    try {
                        Boolean check = false;
                        String pbCheck = "select exists (select true from tb_NumberOfPb where authorname = ?) ";
                        PreparedStatement ps = connection.prepareStatement(pbCheck);
                        ps.setString(1, author);
                        ResultSet rs_pbCheck = ps.executeQuery();
                        while (rs_pbCheck.next()) {
                            check = rs_pbCheck.getBoolean(1);
                        }
                        if (check == true) {
                            String numOfPB =
                                    "update tb_NumberOfPb set numberofpb = numberofpb + 1 where authorname = ?";
                            ps = connection.prepareStatement(numOfPB);
                            ps.setString(1, author);
                            ps.executeUpdate();
                        }
                        if (check == false) {
                            count_authors += 1;
                            String addNewAuthor = "insert into tb_NumberOfPb values (?,?)";
                            ps = connection.prepareStatement(addNewAuthor);
                            ps.setString(1, author);
                            ps.setInt(2, 1);
                            ps.executeUpdate();
                        }
                        ps.close();
                    } catch (SQLException e) {
                        System.out.println(CONNECTIONERROR);
                        e.printStackTrace();
                    }
                    // Fill data into table tb_pbProfile
                    if (executed == false) {
                        try {
                            // title text, type text, year integer, pages text, journal text, ee text, url text,
                            // volume text, booktitle text, isbn text, publisher text, editor text, school text, number text
                            String sql = "insert into tb_publication values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement ps = connection.prepareStatement(sql);
                            ps.setString(1, title);
                            ps.setString(2, "articles");
                            ps.setInt(3, year);
                            ps.setString(4, pages);
                            ps.setString(5, journal);
                            ps.setString(6, ee);
                            ps.setString(7, url);
                            ps.setString(8, volume);
                            ps.setString(9, booktitle);
                            ps.setString(10, isbn);
                            ps.setString(11, publisher);
                            ps.setString(12, editor);
                            ps.setString(13, school);
                            ps.setString(14, number);
                            ps.executeUpdate();
                            ps.close();
                            executed = true;
                        } catch (SQLException e) {
                            System.out.println(CONNECTIONERROR);
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
    }

    // Class for help analyze xml file.
    private static Document getDocument(String docString) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(docString));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}