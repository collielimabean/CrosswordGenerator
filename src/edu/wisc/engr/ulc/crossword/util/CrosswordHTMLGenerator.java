package edu.wisc.engr.ulc.crossword.util;

import edu.wisc.engr.ulc.crossword.generator.Crossword;

public class CrosswordHTMLGenerator
{
    public static String generateAnswerKey(Crossword crossword)
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
        
        builder.append("</table></body></html>");
        return builder.toString();
    }
    
}
