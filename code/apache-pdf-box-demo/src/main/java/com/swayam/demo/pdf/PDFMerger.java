package com.swayam.demo.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 * 
 * <a href=
 * "https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/util/PDFMergerExample.java?view=markup">https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/util/PDFMergerExample.java?view=markup</a>
 */
public class PDFMerger {

    private static final String BASE_DIRECTORY = "D:/personal/putputi_boi/school/english";

    private static void merge(List<InputStream> sources, OutputStream mergedPDFOutputStream) throws IOException {
	try {
	    PDFMergerUtility pdfMerger = new PDFMergerUtility();
	    pdfMerger.addSources(sources);
	    pdfMerger.setDestinationStream(mergedPDFOutputStream);
	    pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
	} finally {
	    for (InputStream source : sources) {
		IOUtils.closeQuietly(source);
	    }
	    IOUtils.closeQuietly(mergedPDFOutputStream);
	}

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

	for (File subDir : Paths.get(BASE_DIRECTORY).toFile().listFiles()) {
	    List<InputStream> sources = Arrays.asList(subDir.listFiles()).stream().map(file -> {
		try {
		    return new FileInputStream(file);
		} catch (FileNotFoundException e) {
		    throw new RuntimeException(e);
		}
	    }).collect(Collectors.toList());

	    File mergedFile = new File(subDir, subDir.getName() + ".pdf");

	    merge(sources, new FileOutputStream(mergedFile));
	}

    }

}
