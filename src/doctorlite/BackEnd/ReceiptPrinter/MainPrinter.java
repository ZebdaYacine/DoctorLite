/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.ReceiptPrinter;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import doctorlite.BackEnd.Model.Paiement;

import java.io.CharConversionException;
import java.io.IOException;
import javax.print.PrintService;

/**
 *
 * @author rashid
 */
public class MainPrinter {

    final  static String printerName="XP-80";

    public static void Print(Paiement paiement) throws IOException {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printerName);
        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        try (EscPos escpos = new EscPos(printerOutputStream)) {
            ReceiptBitmap map = new ReceiptBitmap();
            map.exec(escpos,paiement);
            System.out.println("Printer was done");
        }    
    }
        
}