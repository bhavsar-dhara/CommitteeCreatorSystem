package frontEnd;

import java.io.*;
import java.sql.*;

/**
 * This class aim at edit files and rearrange their structure then fill data
 * into database The table committeeCheck include four things: authorname,
 * checkyear, role, committee) authorname: a publication's author's name
 * checkyear: in which year this author ever checked at committee role: in that
 * year, what kind of role he is committee: the committee name related this
 * author experience Not all author include committee info.
 */
public class CommitteeEditor {
	static String CONNECTIONERROR = "Failed!";
	static String directory = "/Users/lululillian/Desktop/MSD_HomeWork/committees copy/";
	static String createTable_committeeCheck = "create table " + "if not exists "
			+ "tb_committeeCheck(authorName text, checkYear integer, role text, committee text)";
	static Connection connection = null;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
				"jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp", "luliuAWS",
				"1991715ll");
		System.out.println("\nSuccessful connect with database!");

		File folder = new File("/Users/lululillian/Desktop/MSD_HomeWork/committees copy/");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				String wholeFileName = listOfFiles[i].getName();
				if (wholeFileName.charAt(0) == '.')
					continue;
				String fileName = wholeFileName.substring(0, wholeFileName.lastIndexOf("-") + 1);
				String fileYear = fileName.replaceAll("[^0-9]", "");
				int checkYear = Integer.parseInt(fileYear);
				int j = 0;
				while (j < fileName.length() && !Character.isDigit(fileName.charAt(j)))
					j++;
				String committee = fileName.substring(0, j);
				File file = new File(directory + wholeFileName);
				String role = "";
				String name = "";
				try (FileReader fileStream = new FileReader(file);
						BufferedReader bufferedReader = new BufferedReader(fileStream)) {
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						if (line.length() >= 2)
							if (line.charAt(1) != ':') {
								role = "Member";
								name = line;
							} else if (line.charAt(0) == 'G') {
								role = "General Chair";
								name = line.substring(2);
							} else if (line.charAt(0) == 'P') {
								role = "Program Chair";
								name = line.substring(2);
							} else if (line.charAt(0) == 'C') {
								role = "Conference Chair";
								name = line.substring(2);
							} else if (line.charAt(0) == 'E') {
								role = "External Review Committee";
								name = line.substring(2);
							}
					}
					System.out.println("\n---------\nCommittee:" + committee);
					System.out.println("\nCheckYear: " + checkYear);
					System.out.println("\nRole: " + role);
					System.out.println("\nName: " + name);
				} catch (FileNotFoundException ex) {
					// exception Handling
				} catch (IOException ex) {
					// exception Handling
				}
				try {
					// authorName text, checkYear integer, role text, committee
					// text
					String sql = "insert into tb_committeeCheck values(?,?,?,?)";
					PreparedStatement ps_createTable = connection.prepareStatement(createTable_committeeCheck);
					ps_createTable.executeUpdate();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, name);
					ps.setInt(2, checkYear);
					ps.setString(3, role);
					ps.setString(4, committee);
					ps.executeUpdate();
					ps.close();
				} catch (SQLException e) {
					System.out.println(CONNECTIONERROR);
					e.printStackTrace();
				}

			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

}