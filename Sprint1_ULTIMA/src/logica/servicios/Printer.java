package logica.servicios;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Printer {
 private int con=0;
	
	public  void print(String  nombre) throws IOException {

		PDDocument document=null;
		do {
			if(con!=0)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		try {
		
			document = PDDocument.load(new File(nombre));
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(document));
			

	        if (job.printDialog())
	        {
	            job.print();
	        }
	    } catch (Exception e) {
			e.getStackTrace();
			System.err.println(e+"Se ha producido un error mietras se imprimia");
			con++;
			}
	    }while (con>0);
		
			
			document.close();
		
			
		
		
		
			
			
		
		
		}

}
