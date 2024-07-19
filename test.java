import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.jonathanlink.PDFLayoutTextStripper;



public class test {

	public static void main(String[] args) {
        File dir = new File("input/");
        File[] directoryListing = dir.listFiles();
        // delete .DS_Store file
        for (File child : directoryListing) {
            if (child.getName().equals(".DS_Store")) {
                child.delete();
            }
        }
        if (directoryListing != null) {
            System.out.println(directoryListing.length + " files found in the directory.");
            for (File child : directoryListing) {
                System.out.println(child.getName());
            }
            for (File child : directoryListing) {
                String string = null;
                PDDocument pdDocument = null;
                try {
                    PDFParser pdfParser = new PDFParser(new RandomAccessFile(child, "r"));
                    pdfParser.parse();
                    pdDocument = new PDDocument(pdfParser.getDocument());
                    PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
                    string = pdfTextStripper.getText(pdDocument);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (pdDocument != null) {
                        try {
                            pdDocument.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // System.out.println(string);
                // save to file
                File file = new File("./output/" + child.getName().replace(".pdf", ".txt"));
                try {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(string);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No files found in the directory.");
        }
    }

}
