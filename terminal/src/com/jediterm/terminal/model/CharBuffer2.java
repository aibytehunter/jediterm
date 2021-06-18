package com.jediterm.terminal.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author aijx
 */
public class CharBuffer2 extends CharBuffer {
    public CharBuffer2(CharBuffer charBuffer) {
        super(getBuf(charBuffer), 0, getBufSize(charBuffer));

    }

    private static char[] getBuf(CharBuffer charBuffer) {
        char[] buf = charBuffer.getBuf();
        List<Character> characters = Lists.newArrayList();
        for (char c : buf) {
//            if (c == CharUtils.DWC) {
//                continue;
//            }
            if (c == '\r') {
                continue;
            }
            characters.add(c);
        }
        char[] newbuf = new char[characters.size()];
        for (int i = 0; i < characters.size(); i++) {
            newbuf[i] = characters.get(i);
        }
        System.out.println(newbuf);
        return newbuf;
    }

    private static int getBufSize(CharBuffer charBuffer) {
        return getBuf(charBuffer).length;
    }

}
