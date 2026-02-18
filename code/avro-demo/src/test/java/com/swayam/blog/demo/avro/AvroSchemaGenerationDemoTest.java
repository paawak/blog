package com.swayam.blog.demo.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.avro.Schema;
import org.apache.avro.SchemaFormatter;
import org.apache.avro.SchemaParser;
import org.apache.avro.data.TimeConversions.LocalTimestampMillisConversion;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.file.DataFileWriter.AppendWriteException;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import com.swayam.blog.demo.avro.model.Book;

class AvroSchemaGenerationDemoTest {

	@Test
	void generateSimpleSchema() throws IOException, URISyntaxException {
		// given
		Function<String, String> strip = text -> text.replaceAll("[\r\n\t\\s]", "");
		String expected = strip.apply(Files
				.readString(Path.of(AvroSchemaGenerationDemoTest.class.getResource("/expected/book.avsc").toURI())));
		AvroSchemaGenerationDemo testClass = new AvroSchemaGenerationDemo();

		// when
		Schema result = testClass.generateSimpleSchema(Book.class);

		// then
		String actual = SchemaFormatter.getInstance("json/pretty").format(result);
		System.out.println(actual);
		assertEquals(expected, strip.apply(actual));
	}

	@Test
	void generateCustomizedSchema() throws IOException, URISyntaxException {
		// given
		Function<String, String> strip = text -> text.replaceAll("[\r\n\t\\s]", "");
		String expected = strip.apply(Files.readString(
				Path.of(AvroSchemaGenerationDemoTest.class.getResource("/expected/book_customized.avsc").toURI())));
		AvroSchemaGenerationDemo testClass = new AvroSchemaGenerationDemo();

		// when
		Schema result = testClass.generateCustomizedSchema(Book.class);

		// then
		String actual = SchemaFormatter.getInstance("json/pretty").format(result);
		System.out.println(actual);
		assertEquals(expected, strip.apply(actual));
	}

	@Test
	void pojoWithSpecificDatumWriterThrowsException() throws IOException, URISyntaxException {
		// given
		Schema bookSchema = new SchemaParser()
				.parse(AvroSchemaGenerationDemoTest.class.getResourceAsStream("/expected/book.avsc")).mainSchema();

		Book book = Instancio.create(Book.class);

		Path bookAvroFileLocation = Path.of("build", "book.avro");

		// when
		DatumWriter<Book> datumWriter = new SpecificDatumWriter<>();
		@SuppressWarnings("resource")
		DataFileWriter<Book> dataFileWriter = new DataFileWriter<>(datumWriter);
		dataFileWriter.create(bookSchema, bookAvroFileLocation.toFile());
		AppendWriteException result = assertThrows(AppendWriteException.class, () -> dataFileWriter.append(book));

		// then
		assertTrue(result.getMessage().startsWith("java.lang.ClassCastException: value Book"));
	}

	@Test
	void validatePojoAgainstSchema() throws IOException, URISyntaxException {
		// given
		Schema bookSchema = new SchemaParser()
				.parse(AvroSchemaGenerationDemoTest.class.getResourceAsStream("/expected/book_customized.avsc"))
				.mainSchema();

		List<Book> booksInput = Instancio.createList(Book.class);

		Path bookAvroFileLocation = Path.of("build", "book.avro");

		// when
		ReflectDatumWriter<Book> datumWriter = new ReflectDatumWriter<>();
		datumWriter.getData().addLogicalTypeConversion(new DateConversion());
		datumWriter.getData().addLogicalTypeConversion(new LocalTimestampMillisConversion());
		DataFileWriter<Book> dataFileWriter = new DataFileWriter<>(datumWriter);
		dataFileWriter.create(bookSchema, bookAvroFileLocation.toFile());
		booksInput.forEach(book -> {
			try {
				dataFileWriter.append(book);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		dataFileWriter.close();

		// then
		ReflectDatumReader<Book> datumReader = new ReflectDatumReader<>(bookSchema);
		datumReader.getData().addLogicalTypeConversion(new DateConversion());
		datumReader.getData().addLogicalTypeConversion(new LocalTimestampMillisConversion());
		DataFileReader<Book> dataFileReader = new DataFileReader<>(bookAvroFileLocation.toFile(), datumReader);
		List<Book> booksOutput = new ArrayList<>();
		while (dataFileReader.hasNext()) {
			Book book = dataFileReader.next();
			booksOutput.add(book);
		}
		dataFileReader.close();
		assertEquals(booksInput, booksOutput);
	}

}
