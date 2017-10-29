package com.swayam.demo.spring.springbootdemo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.util.StringUtils;

import com.swayam.demo.jpa.one2many.model.Author;
import com.swayam.demo.jpa.one2many.model.Book;
import com.swayam.demo.jpa.one2many.model.Chapter;
import com.swayam.demo.jpa.one2many.model.Genre;
import com.swayam.demo.jpa.one2many.model.Section;

public class JpaIntegrationTest {

	private EntityManager entityManager;

	@Before
	public void setup() throws IOException {
		Properties props = new Properties();
		props.load(JpaIntegrationTest.class.getResourceAsStream("/application-test.properties"));
		String dbUrl = props.getProperty("jdbc.url");
		String dbUser = props.getProperty("jdbc.username");
		String dbPassword = props.getProperty("jdbc.password");

		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
			Statement stat = con.createStatement();
			stat.executeQuery(getLines("/sql/schema_hsql.sql"));
			stat.executeQuery(getLines("/sql/data_hsql.sql"));
			stat.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		entityManager = createEntityManager(dbUrl, dbUser, dbPassword);
	}

	@After
	public void cleanup() {
		entityManager.close();
		entityManager = null;
	}

	@Test
	public void testBookQuery() {
		Book book = entityManager.find(Book.class, 1L);
		System.out.println(book);
	}

	private EntityManager createEntityManager(String dbUrl, String dbUser, String dbPassword) {

		MutablePersistenceUnitInfo mutablePersistenceUnitInfo = new MutablePersistenceUnitInfo() {
			@Override
			public ClassLoader getNewTempClassLoader() {
				return null;
			}
		};

		mutablePersistenceUnitInfo.setPersistenceUnitName("TestUnit");
		mutablePersistenceUnitInfo.setPersistenceProviderClassName(HibernatePersistenceProvider.class.getName());

		Properties props = new Properties();
		props.put("hibernate.connection.url", dbUrl);
		props.put("hibernate.connection.username", dbUser);

		if (StringUtils.hasText(dbPassword)) {
			props.put("hibernate.connection.password", dbPassword);
		}

		mutablePersistenceUnitInfo.setProperties(props);

		mutablePersistenceUnitInfo.addManagedClassName(Author.class.getName());
		mutablePersistenceUnitInfo.addManagedClassName(Book.class.getName());
		mutablePersistenceUnitInfo.addManagedClassName(Chapter.class.getName());
		mutablePersistenceUnitInfo.addManagedClassName(Genre.class.getName());
		mutablePersistenceUnitInfo.addManagedClassName(Section.class.getName());

		PersistenceUnitDescriptor persistenceUnitDescriptor = new PersistenceUnitInfoDescriptor(
				mutablePersistenceUnitInfo);

		EntityManagerFactoryBuilder entityManagerFactoryBuilder = new EntityManagerFactoryBuilderImpl(
				persistenceUnitDescriptor, Collections.EMPTY_MAP);

		EntityManagerFactory entityManagerFactory = entityManagerFactoryBuilder.build();

		return entityManagerFactory.createEntityManager();

	}

	private String getLines(String fileClasspath) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(JpaIntegrationTest.class.getResource(fileClasspath).toURI()));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return StringUtils.collectionToDelimitedString(lines, "\n");
	}

}
