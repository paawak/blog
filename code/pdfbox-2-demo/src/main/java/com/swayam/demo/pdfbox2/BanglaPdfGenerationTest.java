package com.swayam.demo.pdfbox2;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 * https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/pdmodel/HelloWorldTTF.java?view=markup
 * 
 * @author paawak
 *
 */
public class BanglaPdfGenerationTest
{

    static
    {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    }

    public static void main(String[] args) throws IOException
    {

        String filename = System.getProperty("user.home") + "/a.pdf";

        System.err.println("filename=" + filename);

        String message = "\u0986\u09ae\u09bf  \u0995\u09cb\u09a8 \u09aa\u09a5\u09c7  \u0995\u09cd\u09b7\u09c0\u09b0\u09c7\u09b0 \u09b7\u09a8\u09cd\u09a1  \u09aa\u09c1\u09a4\u09c1\u09b2 \u09b0\u09c1\u09aa\u09cb  \u0997\u0999\u09cd\u0997\u09be \u098b\u09b7\u09bf";

        PDDocument doc = new PDDocument();
        try
        {
            PDPage page = new PDPage();
            doc.addPage(page);

            // PDFont font = PDType0Font.load(doc, BanglaPdfGenerationTest.class
            // .getResourceAsStream("/SOLAIMANLIPI_22-02-2012.TTF"), false);

            PDFont font = PDType0Font.load(doc,
                    BanglaPdfGenerationTest.class.getResourceAsStream("/Lohit-Bengali.ttf"), false);

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(message);
            contents.endText();
            contents.close();

            doc.save(filename);
        }
        finally
        {
            doc.close();
        }
    }

}
