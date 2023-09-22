package com.jtk.ps.api.util;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PDFUtil {
    private PDFUtil(){throw new IllegalStateException("PDFUtil class");}
    private static final PDFont FONT = PDType1Font.TIMES_ROMAN;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = -1.07f * FONT_SIZE;
    private static float WIDTH_TAB = 0;

    public static void addParagraph(PDPageContentStream contentStream, float width, float sx,
                                     float sy, String text, float widthTab) throws IOException {
        addParagraph(contentStream, width, sx, sy, text, false, widthTab);
    }

    public static void addParagraph(PDPageContentStream contentStream, float width, float sx,
                                     float sy, String text, boolean justify, float widthTab) throws IOException {
        List<String> lines = parseLines(text, width);
        boolean firstLine = true;
        WIDTH_TAB = widthTab;

        contentStream.setFont(FONT, FONT_SIZE);
        contentStream.newLineAtOffset(sx+WIDTH_TAB, sy);
        for (String line: lines) {
            float charSpacing = 0;
            if (justify){
                if (line.length() > 1) {
                    float size = FONT_SIZE * FONT.getStringWidth(line) / 1000;
                    float free = width - size;
                    if(firstLine) {
                        free = width - size - WIDTH_TAB;
                    }

                    if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
                        charSpacing = free / (line.length() - 1);
                    }
                }
            }
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(line);
            if(!firstLine) {
                contentStream.newLineAtOffset(0, LEADING);
            }else{
                contentStream.newLineAtOffset(-WIDTH_TAB, LEADING);
                firstLine = false;
            }
        }

    }

    public static List<String> parseLines(String text, float widthh) throws IOException {
        List<String> lines = new ArrayList<String>();

        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = FONT_SIZE * FONT.getStringWidth(subString) / 1000;

            float width;
            if(!lines.isEmpty()) {
                width = widthh;
            }else{
                width = widthh - WIDTH_TAB;
            }
            if (size > width) {
                if (lastSpace < 0){
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }


    public static String remove(String test) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < test.length(); i++) {
            if (WinAnsiEncoding.INSTANCE.contains(test.charAt(i))) {
                b.append(test.charAt(i));
            }
        }
        return b.toString();
    }
}
