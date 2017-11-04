package com.swayam.demo.spring.springbootdemo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.After;
import org.junit.Before;
import org.springframework.util.StringUtils;

import com.swayam.demo.jpa.one2many.model.Author;
import com.swayam.demo.jpa.one2many.model.Book;
import com.swayam.demo.jpa.one2many.model.Chapter;
import com.swayam.demo.jpa.one2many.model.Genre;
import com.swayam.demo.jpa.one2many.model.Section;

public abstract class JpaIntegrationTestParent {

	protected EntityManager entityManager;

	@Before
	public void setup() throws IOException {
		Properties props = new Properties();
		props.load(JpaIntegrationTestParent.class.getResourceAsStream("/application-test.properties"));
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

	private EntityManager createEntityManager(String dbUrl, String dbUser, String dbPassword) {

		Properties props = new Properties();
		props.put("hibernate.connection.url", dbUrl);
		props.put("hibernate.connection.username", dbUser);

		if (StringUtils.hasText(dbPassword)) {
			props.put("hibernate.connection.password", dbPassword);
		}

		PersistenceUnitInfo persistenceUnitInfo = new PersistenceUnitInfo() {

			@Override
			public Properties getProperties() {
				return props;
			}

			@Override
			public List<String> getManagedClassNames() {
				return Arrays.asList(Author.class.getName(), Book.class.getName(), Chapter.class.getName(),
						Genre.class.getName(), Section.class.getName());
			}

			@Override
			public String getPersistenceUnitName() {
				return "TestUnit";
			}

			@Override
			public String getPersistenceProviderClassName() {
				return HibernatePersistenceProvider.class.getName();
			}

			@Override
			public PersistenceUnitTransactionType getTransactionType() {
				return null;
			}

			@Override
			public DataSource getJtaDataSource() {
				return null;
			}

			@Override
			public DataSource getNonJtaDataSource() {
				return null;
			}

			@Override
			public List<String> getMappingFileNames() {
				return null;
			}

			@Override
			public List<URL> getJarFileUrls() {
				return null;
			}

			@Override
			public URL getPersistenceUnitRootUrl() {
				return null;
			}

			@Override
			public boolean excludeUnlistedClasses() {
				return false;
			}

			@Override
			public SharedCacheMode getSharedCacheMode() {
				return null;
			}

			@Override
			public ValidationMode getValidationMode() {
				return null;
			}

			@Override
			public String getPersistenceXMLSchemaVersion() {
				return null;
			}

			@Override
			public ClassLoader getClassLoader() {
				return null;
			}

			@Override
			public void addTransformer(ClassTransformer transformer) {

			}

			@Override
			public ClassLoader getNewTempClassLoader() {
				return null;
			}
		};

		HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();

		EntityManagerFactory entityManagerFactory = hibernatePersistenceProvider
				.createContainerEntityManagerFactory(persistenceUnitInfo, Collections.EMPTY_MAP);

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
