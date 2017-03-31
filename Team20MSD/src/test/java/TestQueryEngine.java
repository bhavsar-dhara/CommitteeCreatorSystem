package test.java;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import main.java.classes.Author;

public class TestQueryEngine {
	
	@Test
	public void testHibernate() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
 
		Author user = new Author("testAuthor", null);
		session.save(user);
 
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testSearchAuthorQuery() {
		
	}

}
