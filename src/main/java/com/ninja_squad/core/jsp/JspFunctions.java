package com.ninja_squad.core.jsp;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.io.CharStreams;
import com.ninja_squad.core.exception.ShouldNeverHappenException;

/**
 * Functions of the ninja core tag library
 * @author JB
 */
public final class JspFunctions {

    private static final Function<String, String> ESCAPE_XML = new Function<String, String>() {
        @Override
        public String apply(String input) {
            if (input == null) {
                return "";
            }
            StringBuilder out = new StringBuilder(input.length() + 20);
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c <= '>') { // optimization
                    switch (c) {
                        case '<' :
                            out.append("&lt;");
                            break;
                        case '>' :
                            out.append("&gt;");
                            break;
                        case '&' :
                            out.append("&amp;");
                            break;
                        case '"' :
                            out.append("&#034;");
                            break;
                        case '\'' :
                            out.append("&#039;");
                            break;
                        default :
                            out.append(c);
                            break;
                    }
                }
                else {
                    out.append(c);
                }
            }
            return out.toString();
        }
    };

    private JspFunctions() {
    }

    /**
     * Transforms all the newlines into <code>&lt;br/&gt;</code>, in order for multi-line text
     * to be displayed into an HTML page. Each line of text is also XML-escaped. <br/>
     * If a null string is passed as argument, an empty string is returned.
     * @param input the string to transform
     * @return the transformed string.
     */
    public static String nlToBr(@Nullable String input) {
        if (input == null) {
            return "";
        }

        try {
            List<String> lines = CharStreams.readLines(new StringReader(input));
            return Joiner.on("<br/>").join(Iterables.transform(lines, ESCAPE_XML));
        }
        catch (IOException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    /**
     * Escapes all the characters that must be escaped in a JavaScript String literal (i.e.
     * a tab character is transformed into <code>\t</code>, an apostrophe is transformed
     * into <code>\'</code>, etc.<br/>
     * If a null string is passed as argument, an empty string is returned.
     * @param input the string to escape
     * @return the escaped string.
     */
    public static String escapeJs(@Nullable String input) {
        if (input == null) {
            return "";
        }

        StringBuilder out = new StringBuilder(input.length() + 20);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '\b' :
                    out.append("\\b");
                    break;
                case '\n' :
                    out.append("\\n");
                    break;
                case '\f' :
                    out.append("\\f");
                    break;
                case '\r' :
                    out.append("\\r");
                    break;
                case '\t' :
                    out.append("\\t");
                    break;
                case '\'' :
                    out.append("\\'");
                    break;
                case '"' :
                    out.append("\\\"");
                    break;
                case '\\' :
                    out.append("\\\\");
                    break;
                default :
                    out.append(c);
                    break;
            }
        }
        return out.toString();
    }

    /**
     * Upper-cases the first letter of the given string. This can be useful when dates, for example,
     * must be displayed as (in French) "Lundi 12 mars" instead of the default "lundi 12 mars".<br/>
     * If a null string is passed as argument, an empty string is returned.
     * @param input the string to transform
     * @return the transformed string
     */
    public static String upperFirst(String input) {
        if (input == null || input.length() == 0) {
            return "";
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}
