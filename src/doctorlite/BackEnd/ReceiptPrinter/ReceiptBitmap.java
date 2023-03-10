package doctorlite.BackEnd.ReceiptPrinter;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.image.*;
import doctorlite.BackEnd.Controller.CommunController;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Service.PaiementService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.CharConversionException;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ReceiptBitmap {

    public void exec(EscPos escpos, Paiement paiement) throws IOException {
        System.out.println(paiement.getStd().getSectionName());
        final int FontSize = 30;
        String schoolName = "مدرسة المتمكن        ";
        String sectionName = " فرع :  "+paiement.getStd().getSectionName();
        String lines = "--------------------------------------------";
        String studentName = "التلميذ(ة) :"+ paiement.getStd().getLastName()+" "+paiement.getStd().getFirstName();
//        String offerName = "العرض  "+paiement.getGrp().getNameOffer();
        String groupName ="الفوج :"+paiement.getGrp().getNameGroup();

        String date ="   التاريخ :"+paiement.getDate();
        int nbrSeanceOfOffer= CommunController.getnbrSeanceInOffer(paiement.getTypeOfOffer());
        int nbrSeanceOfPaiement= PaiementService.getPaiementForThisGroupIfExist(paiement).getNbrSeance();
/*
        float priceSeance=CommunController.getAmountSeance(paiement);
*/
       
        String around ="الدورة :"+paiement.getAround();
        String amountC ="المبلغ المدفوع :"+paiement.getAmountC()+" Da";

        BufferedImage image1 = new BufferedImage(576, 250, TYPE_INT_RGB);
        BufferedImage image2 = new BufferedImage(576, 100, TYPE_INT_RGB);
        BufferedImage image3 = new BufferedImage(576, 70, TYPE_INT_RGB);
        BufferedImage image4 = new BufferedImage(576, 250, TYPE_INT_RGB);
        BufferedImage image5 = new BufferedImage(576, 70, TYPE_INT_RGB);


        Graphics2D g1 = image1.createGraphics();
        Graphics2D g2 = image2.createGraphics();
        Graphics2D g3 = image3.createGraphics();
        Graphics2D g4 = image4.createGraphics();
        Graphics2D g5 = image5.createGraphics();


        // change background and foregroud colors
        g1.setColor(Color.white);
        g1.fillRect(0, 0, g1.getDeviceConfiguration().getBounds().width, g1.getDeviceConfiguration().getBounds().height);
        g1.setColor(Color.BLACK);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, g2.getDeviceConfiguration().getBounds().width, g2.getDeviceConfiguration().getBounds().height);
        g2.setColor(Color.WHITE);

        g3.setColor(Color.BLACK);
        g3.fillRect(0, 0, g3.getDeviceConfiguration().getBounds().width, g3.getDeviceConfiguration().getBounds().height);
        g3.setColor(Color.WHITE);

        g4.setColor(Color.WHITE);
        g4.fillRect(0, 0, g4.getDeviceConfiguration().getBounds().width, g4.getDeviceConfiguration().getBounds().height);
        g4.setColor(Color.BLACK);

        g5.setColor(Color.BLACK);
        g5.fillRect(0, 0, g5.getDeviceConfiguration().getBounds().width, g5.getDeviceConfiguration().getBounds().height);
        g5.setColor(Color.WHITE);

        Font fontMonoSpacePlan = new Font(Font.MONOSPACED, Font.PLAIN, FontSize);
        Font fontMonoSpaceBold1 = new Font(Font.MONOSPACED, Font.BOLD, FontSize);
        Font fontMonoSpaceBold2 = new Font(Font.MONOSPACED, Font.BOLD, 50);
        Font fontMonoSpaceBold3 = new Font(Font.MONOSPACED, Font.BOLD, 40);
        Font fontMonoSpaceBoldItalic = new Font(Font.MONOSPACED, Font.ITALIC | Font.BOLD, FontSize);
        Font fontSerifPlan = new Font(Font.SERIF, Font.PLAIN, FontSize);
        Font fontSerifBold = new Font(Font.SERIF, Font.BOLD, FontSize);
        Font fontSansSerif = new Font(Font.SANS_SERIF, Font.PLAIN, FontSize);
        Font fontSansSerifBold = new Font(Font.SANS_SERIF, Font.BOLD, FontSize);

        if (fontMonoSpaceBold1.canDisplayUpTo(schoolName) != -1) {
            throw new CharConversionException("the font doesn't work with these glyphs");
        }

        ImageHelper helper = new ImageHelper();
        Bitonal algorithm = new BitonalThreshold();
        RasterBitImageWrapper imageWrapper = new RasterBitImageWrapper();

        // set the font to be used
        g1.setFont(fontMonoSpaceBold1);
        g2.setFont(fontMonoSpaceBold2);
        g3.setFont(fontMonoSpaceBold3);
        g4.setFont(fontMonoSpaceBold1);
        g5.setFont(fontMonoSpaceBold1);

        g2.drawString(schoolName, -140, 60);
        helper.write(escpos, new CoffeeImageImpl(image2), imageWrapper, algorithm);

        g1.drawString(lines, 1, 60);
        g1.drawString(sectionName, 50, 120);
        g1.drawString(lines, 1, 160);
        g1.drawString(studentName, 110, 220);

        helper.write(escpos, new CoffeeImageImpl(image1), imageWrapper, algorithm);

//        g3.drawString(offerName, 200, 40);
        g3.drawString(groupName, 30, 40);

        helper.write(escpos, new CoffeeImageImpl(image3), imageWrapper, algorithm);

        g4.drawString(lines, 1, 60);
        g4.drawString(around, 260, 100);
        g4.drawString(amountC, 120, 140);
        g4.drawString(lines, 1, 180);
        helper.write(escpos, new CoffeeImageImpl(image4), imageWrapper, algorithm);

        g5.drawString(date, 50, 40);
/*
        g5.drawString(secritID, 200, 65);
*/
        helper.write(escpos, new CoffeeImageImpl(image5), imageWrapper, algorithm);

        escpos.feed(5).cut(EscPos.CutMode.FULL);
        escpos.close();

    }

    public void printCodeTable(EscPos escpos, int codeTable, int codMin, int codMax) throws IOException {
        escpos.setCharacterCodeTable(EscPos.CharacterCodeTable.CP437_USA_Standard_Europe);
        escpos.writeLF(String.format("character code table for code [%d], "
                + "\nbetween %d and %d", codeTable, codMin, codMax));
        escpos.setPrinterCharacterTable(codeTable);
        for (int code = codMin; code <= codMax; code++) {
            escpos.write(code);
            escpos.write("  ");

        }
        escpos.writeLF("");
        escpos.feed(5).cut(EscPos.CutMode.FULL);

    }

}