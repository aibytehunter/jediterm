package com.jediterm.terminal.ui;

import com.jediterm.terminal.StyledTextConsumer;
import com.jediterm.terminal.SubstringFinder;
import com.jediterm.terminal.TerminalDisplay;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.model.CharBuffer;
import com.jediterm.terminal.model.SubCharBuffer;
import com.jediterm.terminal.model.TerminalTextBuffer;
import com.jediterm.terminal.util.CharUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class TerminalSearchUtil {

    static @Nullable SubstringFinder.FindResult searchInTerminalTextBuffer(TerminalDisplay display, @NotNull TerminalTextBuffer textBuffer, @NotNull String pattern, boolean ignoreCase) {
        if (pattern.isEmpty()) {
            return null;
        }
        if (pattern.length() == 0) {
            return null;
        }
        pattern = stringToDoubleWidthCharacter(display, pattern);
        final SubstringFinder finder = new SubstringFinder(pattern, ignoreCase);

        textBuffer.processHistoryAndScreenLines(-textBuffer.getHistoryLinesCount(), -1, new StyledTextConsumer() {
            @Override
            public void consume(int x, int y, @NotNull TextStyle style, @NotNull CharBuffer characters, int startRow) {
                int offset = 0;
                int length = characters.length();
                if (characters instanceof SubCharBuffer) {
                    SubCharBuffer subCharBuffer = (SubCharBuffer) characters;
                    characters = subCharBuffer.getParent();
                    offset = subCharBuffer.getOffset();
                }
                for (int i = offset; i < offset + length; i++) {
                    finder.nextChar(x, y - startRow, characters, i);
                }
            }

            @Override
            public void consumeNul(int x, int y, int nulIndex, @NotNull TextStyle style, @NotNull CharBuffer characters, int startRow) {
            }

            @Override
            public void consumeQueue(int x, int y, int nulIndex, int startRow) {
            }
        });

        return finder.getResult();
    }

    private static String stringToDoubleWidthCharacter(TerminalDisplay display, String str) {
        char[] chars = str.toCharArray();
        int dwcCount = CharUtils.countDoubleWidthCharacters(chars, 0, chars.length, display.ambiguousCharsAreDoubleWidth());

        char[] buf;

        if (dwcCount > 0) {
            // Leave gaps for the private use "DWC" character, which simply tells the rendering code to advance one cell.
            buf = new char[chars.length + dwcCount];

            int j = 0;
            for (int i = 0; i < chars.length; i++) {
                buf[j] = chars[i];
                int codePoint = Character.codePointAt(chars, i);
                boolean doubleWidthCharacter = CharUtils.isDoubleWidthCharacter(codePoint, display.ambiguousCharsAreDoubleWidth());
                if (doubleWidthCharacter) {
                    j++;
                    //TODO 此处控制输出字符
                    buf[j] = CharUtils.FILL_CHAR;
                }
                j++;
            }
        } else {
            buf = chars;
        }
        return new String(buf, 0, buf.length);
    }

}
