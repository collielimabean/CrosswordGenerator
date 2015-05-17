package edu.wisc.engr.ulc.crossword.util;

import java.util.Map;

import edu.wisc.engr.ulc.crossword.generator.Crossword;

public class CrosswordHTMLGenerator
{
    public static String generateAnswerKey(Crossword crossword, Map<Character, Integer> cipher)
    {
        char[][] buffer = crossword.toCharacterBuffer();
        
        if (buffer == null)
            return "";
        
        StringBuilder builder = new StringBuilder("<html><body><table style=\"table-layout:fixed;width:600px;\">");
        
        for (char[] line : buffer)
        {
            builder.append("<tr>");
            
            for (char c : line)
            {
                if (c == 0)
                    builder.append("<td bgcolor=\"black\" style=\"text-align: center\"></td>");
                else
                    builder.append("<td style=\"text-align: center\">" + c + "</td>");
            }
            
            builder.append("</tr>");
        }
        
        builder.append("</table><br><table border=\"1\">");
                
        for (Map.Entry<Character, Integer> e : cipher.entrySet())
        {
            builder.append("<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
        }
        
        builder.append("</table></body></html>");
        return builder.toString();
    }
    
    public static String generateWorksheet(Crossword crossword, Map<Character, Integer> cipher)
    {
        char[][] buffer = crossword.toCharacterBuffer();
        
        if (buffer == null)
            return "";
        
        StringBuilder builder = new StringBuilder("<html><body><table style=\"table-layout:fixed;width:850px;\">");
        
        for (char[] line : buffer)
        {
            builder.append("<tr>");
            
            for (char c : line)
            {
                if (c == 0)
                    builder.append("<td bgcolor=\"black\" style=\"text-align: center\"></td>");
                else
                    builder.append("<td style=\"text-align: center\">__<sup><sup>" + cipher.get(c) + "</sup></sup></td>");
            }
            
            builder.append("</tr>");
        }
        
        builder.append("</table></body></html>");
        return builder.toString();
    }
    
}
