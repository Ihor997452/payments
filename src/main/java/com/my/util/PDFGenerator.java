package com.my.util;

import com.my.db.entity.Transaction;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

public class PDFGenerator {
    private static Logger log = Logger.getLogger(PDFGenerator.class);

    private PDFGenerator() {
    }

    public static ByteArrayOutputStream createPDFDocument(Transaction transaction) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            String fontPath = "../webapps/demo6_war/fonts";
            PDFont font = PDTrueTypeFont.load(doc, new File(fontPath + "/SegoeUIBold.ttf"), WinAnsiEncoding.INSTANCE);
            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
                cont.addRect(0.0F, 700.0F, 650.0F, 100.0F);
                cont.setNonStrokingColor(new Color(33, 37, 41));
                cont.fill();
                cont.beginText();
                cont.setNonStrokingColor(Color.WHITE);
                cont.setFont(font, 24.0F);
                cont.setLeading(20.0D);
                cont.newLineAtOffset(60.0F, 740.0F);
                cont.showText("Transaction receipt");
                cont.setNonStrokingColor(new Color(33, 37, 41));
                font = PDTrueTypeFont.load(doc, new File(fontPath + "/SegoeUI.ttf"), WinAnsiEncoding.INSTANCE);
                cont.setFont(font, 14.0F);
                cont.setLeading(20.0D);
                cont.newLine();
                cont.newLine();
                cont.newLine();
                cont.newLine();
                cont.newLine();
                cont.showText("Transaction Id: " + transaction.getId());
                cont.newLine();
                cont.newLine();
                if (transaction.getFrom() == 0L) {
                    cont.showText("From: Top Up Service");
                } else {
                    cont.showText("From: " + transaction.getFrom());
                }

                cont.newLine();
                cont.newLine();
                cont.showText("To: " + transaction.getTo());
                cont.newLine();
                cont.newLine();
                cont.showText("Amount: " + transaction.getAmount().setScale(4, RoundingMode.HALF_DOWN) + transaction.getCurrency());
                cont.newLine();
                cont.newLine();
                String text = "Message: " + transaction.getMessage();
                int newLinePos = text.length();

                while(text.length() > 0) {
                    String subString = text.substring(0, newLinePos);
                    if (14.0F * font.getStringWidth(subString) / 1000.0F > myPage.getMediaBox().getWidth() - 100.0F) {
                        --newLinePos;
                    } else if (subString.trim().length() == 0) {
                        cont.newLine();
                    } else {
                        text = "                 " + text.substring(newLinePos);
                        newLinePos = text.length();
                        cont.showText(subString);
                        cont.newLine();
                        if (text.equals("                 ")) {
                            break;
                        }
                    }
                }

                cont.newLine();
                cont.showText("Time: " + transaction.getTime());
                cont.newLine();
                cont.newLine();
                cont.showText("Status: " + transaction.getStatus());
                cont.endText();
            }

            doc.save(outputStream);

            return outputStream;
        }
    }
}
