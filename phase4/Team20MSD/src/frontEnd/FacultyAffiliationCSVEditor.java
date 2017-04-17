package frontEnd;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Each author may have a affiliate, so this aim at creat table tb_university
 * and save their details into this table. The data come from dblp. Not all
 * author include a affiliate.
 */
public class FacultyAffiliationCSVEditor {

	static String CONNECTIONERROR = "Failed!";
	static String directory = "/Users/lululillian/Desktop/MSD_HomeWork/committees copy/";
	static String createTable_committeeCheck = "create table if not exists tb_university(authorName text, university text)";
	static Connection connection = null;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException {

		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
				"jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp", "luliuAWS",
				"1991715ll");
		System.out.println("\nSuccessful connect with database!");

		String sql = "INSERT INTO tb_university VALUES(?,?) ";
		try {
			String line = "";
			File temp = new File("");
			InputStream is = new FileInputStream(
					new File("").getAbsolutePath() + "/src/main/resources/csvFiles/faculty-affiliations.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			System.out.print(reader);
			while ((line = reader.readLine()) != null) {
				System.out.print(line);
				try {
					if (line != null) {
						String[] array = line.split(" , ");
						PreparedStatement ps = connection.prepareStatement(sql);
						ps.setString(1, array[0]);
						ps.setString(2, array[1]);
						ps.executeUpdate();
						ps.close();
					}
				} catch (SQLException e) {
					System.out.println(CONNECTIONERROR);
					e.printStackTrace();
				} finally {
					if (reader == null) {
						reader.close();
					}
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
