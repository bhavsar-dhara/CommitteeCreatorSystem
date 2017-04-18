package main.classes;

public class XmlDatabaseTest {
//	private static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
//	private static final String JDBC_URL = "jdbc:postgresql:mem:test;DB_CLOSE_DELAY=-1";
//	private static final String USER = "sa";
//	private static final String PASSWORD = "";
//
//	@BeforeClass
//	public static void createSchema() throws Exception {
//		RunScript.execute(JDBC_URL, USER, PASSWORD, "schema.sql", UTF8, false);
//	}
//
//	@Before
//	public void importDataSet() throws Exception {
//		IDataSet dataSet = readDataSet();
//		cleanlyInsert(dataSet);
//	}
//
//	private IDataSet readDataSet() throws Exception {
//		return new FlatXmlDataSetBuilder().build(new File("dataset.xml"));
//	}
//
//	private void cleanlyInsert(IDataSet dataSet) throws Exception {
//		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
//		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		databaseTester.setDataSet(dataSet);
//		databaseTester.onSetup();
//	}
//
//	@Test
//	public void findsAndReadsExistingPersonByFirstName() throws Exception {
//		PersonRepository repository = new PersonRepository(dataSource());
//		Person charlie = repository.findPersonByFirstName("Charlie");
//
//		assertThat(charlie.getFirstName(), is("Charlie"));
//		assertThat(charlie.getLastName(), is("Brown"));
//		assertThat(charlie.getAge(), is(42));
//	}
//
//	@Test
//	public void returnsNullWhenPersonCannotBeFoundByFirstName() throws Exception {
//		PersonRepository repository = new PersonRepository(dataSource());
//		Person person = repository.findPersonByFirstName("iDoNotExist");
//
//		assertThat(person, is(nullValue()));
//	}
//
//	private DataSource dataSource() {
//		DataSource dataSource = new Postgresql;
//		dataSource.setURL(JDBC_URL);
//		dataSource.setUser(USER);
//		dataSource.setPassword(PASSWORD);
//		return dataSource;
//	}

}

//
//private IDataSet authorDataSet;
//private IDataSet publicationDataSet;
//private IDataSet candidateDataSet;
//private IDataSet numberofpbDataSet;
//private IDataSet committeecheckDataSet;
//private IDataSet universityDataSet;
//private Author author;
//private Publication publication;
//
//@BeforeClass
//public static void createSchema() throws Exception {
//	RunScript.execute("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
//	                  "sa", "", "schema.sql", UTF8, false);
//}
//
//private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
//	IDatabaseTester databaseTester = new JdbcDatabaseTester(
//			"org.postgresql.Driver", "jdbc:postgresql:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
//		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		databaseTester.setDataSet(dataSet);
//		databaseTester.onSetup();
//}
//
//@Before
//public void importDataSet() throws Exception {
//	IDataSet dataSet = getDataSet();
//	cleanlyInsertDataset(dataSet);
//}
//
//protected DatabaseOperation getSetUpOperation() throws Exception {
//	return DatabaseOperation.REFRESH;
//}
//
//protected DatabaseOperation getTearDownOperation() throws Exception {
//	return DatabaseOperation.NONE;
//}
//
//protected IDataSet getDataSet() throws Exception {
//	authorDataSet = new FlatXmlDataSetBuilder()
//			.build(this.getClass().getClassLoader().getResourceAsStream("authorSampleData.xml"));
//	return authorDataSet;
//}