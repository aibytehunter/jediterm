package com.jediterm.terminal.ui.settings;

import com.jediterm.terminal.HyperlinkStyle;
import com.jediterm.terminal.TerminalColor;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.emulator.ColorPalette;
import com.jediterm.terminal.model.LinesBuffer;
import com.jediterm.terminal.ui.TerminalActionPresentation;
import com.jediterm.terminal.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

public class DefaultSettingsProvider implements SettingsProvider {
    @Override
    public @NotNull
    TerminalActionPresentation getNewSessionActionPresentation() {
        return new TerminalActionPresentation("New Session", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getOpenUrlActionPresentation() {
        return new TerminalActionPresentation("Open as URL", Collections.emptyList());
    }

    @Override
    public @NotNull
    TerminalActionPresentation getCopyActionPresentation() {
        KeyStroke keyStroke = UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK)
                // CTRL + C is used for signal; use CTRL + SHIFT + C instead
                : KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        return new TerminalActionPresentation("Copy", keyStroke);
    }

    @Override
    public @NotNull
    TerminalActionPresentation getPasteActionPresentation() {
        KeyStroke keyStroke = UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_DOWN_MASK)
                // CTRL + V is used for signal; use CTRL + SHIFT + V instead
                : KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        return new TerminalActionPresentation("Paste", keyStroke);
    }

    @Override
    public @NotNull
    TerminalActionPresentation getClearBufferActionPresentation() {
        return new TerminalActionPresentation("Clear Buffer", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getPageUpActionPresentation() {
        return new TerminalActionPresentation("Page Up",
                KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.SHIFT_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getPageDownActionPresentation() {
        return new TerminalActionPresentation("Page Down",
                KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.SHIFT_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getLineUpActionPresentation() {
        return new TerminalActionPresentation("Line Up", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getLineDownActionPresentation() {
        return new TerminalActionPresentation("Line Down", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getCloseSessionActionPresentation() {
        return new TerminalActionPresentation("Close Session", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
    }

    @Override
    public @NotNull
    TerminalActionPresentation getFindActionPresentation() {
        return new TerminalActionPresentation("Find", UIUtil.isMac
                ? KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_DOWN_MASK)
                : KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public ColorPalette getTerminalColorPalette() {
        //WINDOWS_PALETTE
        return UIUtil.isWindows ? ColorPalette.XTERM_PALETTE : ColorPalette.XTERM_PALETTE;
    }

    @Override
    public Font getTerminalFont() {
        String fontName;
        if (UIUtil.isWindows) {
            //TODO 测试字体
            fontName = "Consolas";
            fontName = "simsun";
            fontName = "Monospaced";
            fontName = "YaHeiConsola";
            //fontName = "Cascadia Code";
        } else if (UIUtil.isMac) {
            fontName = "Menlo";
        } else {
            fontName = "Monospaced";
        }


//        return new Font(, Font.PLAIN, (int) getTerminalFontSize());
        return getSelfDefinedFont(null);
    }

    private static Font localFont;

    static {
        try {
            localFont = Font.createFont(Font.TRUETYPE_FONT, new File("D://YaHeiConsolasHybrid.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //filepath字体文件的路径
    private static java.awt.Font getSelfDefinedFont(String filepath) {
        try {
            localFont = localFont.deriveFont(java.awt.Font.PLAIN, 16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return localFont;
    }

    @Override
    public float getTerminalFontSize() {
        return 16F;
    }

    @Override
    public float getLineSpace() {
        return 0;
    }

    @Override
    public TextStyle getDefaultStyle() {
//        return new TextStyle(TerminalColor.BLACK, TerminalColor.WHITE);
        return new TextStyle(TerminalColor.rgb(235, 235, 235), TerminalColor.rgb(33, 33, 33));
    }

    @Override
    public TextStyle getSelectionColor() {
        return new TextStyle(TerminalColor.WHITE, TerminalColor.rgb(82, 109, 165));
    }

    @Override
    public TextStyle getFoundPatternColor() {
        return new TextStyle(TerminalColor.BLACK, TerminalColor.rgb(255, 255, 0));
    }

    @Override
    public TextStyle getHyperlinkColor() {
        return new TextStyle(TerminalColor.awt(Color.BLUE), TerminalColor.WHITE);
    }

    @Override
    public HyperlinkStyle.HighlightMode getHyperlinkHighlightingMode() {
        return HyperlinkStyle.HighlightMode.HOVER;
    }

    @Override
    public boolean useInverseSelectionColor() {
        return true;
    }

    @Override
    public boolean copyOnSelect() {
        return emulateX11CopyPaste();
    }

    @Override
    public boolean pasteOnMiddleMouseClick() {
        return emulateX11CopyPaste();
    }

    @Override
    public boolean emulateX11CopyPaste() {
        return false;
    }

    @Override
    public boolean useAntialiasing() {
        return true;
    }

    @Override
    public int maxRefreshRate() {
        return 50;
    }

    @Override
    public boolean audibleBell() {
        return true;
    }

    @Override
    public boolean enableMouseReporting() {
        return true;
    }

    @Override
    public int caretBlinkingMs() {
        return 505;
    }

    @Override
    public boolean scrollToBottomOnTyping() {
        return true;
    }

    @Override
    public boolean DECCompatibilityMode() {
        return true;
    }

    @Override
    public boolean forceActionOnMouseReporting() {
        return false;
    }

    @Override
    public int getBufferMaxLinesCount() {
        return LinesBuffer.DEFAULT_MAX_LINES_COUNT;
    }

    @Override
    public boolean altSendsEscape() {
        return false;
    }

    @Override
    public boolean ambiguousCharsAreDoubleWidth() {
        return false;
    }
}
