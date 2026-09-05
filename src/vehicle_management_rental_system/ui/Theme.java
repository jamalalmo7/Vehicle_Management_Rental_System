package vehicle_management_rental_system.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Central styling helper for the modern, consistent UI.
 *
 * <p>FlatLaf is preferred when available; otherwise the system look &amp; feel
 * is used and the same custom colors/fonts are applied on top so the app looks
 * consistent either way.</p>
 */
public final class Theme {

    private Theme() {
    }

    // ---- Palette ----
    public static final Color PRIMARY = new Color(79, 70, 229);      // indigo
    public static final Color PRIMARY_DARK = new Color(67, 56, 202);
    public static final Color SUCCESS = new Color(22, 163, 74);      // green
    public static final Color DANGER = new Color(220, 57, 57);       // red
    public static final Color INFO = new Color(2, 132, 199);         // blue

    public static final Color BG = new Color(245, 246, 250);
    public static final Color SIDEBAR = new Color(30, 32, 48);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(30, 32, 48);
    public static final Color MUTED = new Color(107, 114, 128);
    public static final Color BORDER = new Color(221, 224, 232);

    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    /** Standard single-line input size: 200x30. */
    public static final Dimension INPUT_SIZE = new Dimension(200, 30);

    /** Applies the global look &amp; feel (prefers FlatLaf, else system). */
    public static void installLookAndFeel() {
        boolean flat = false;
        try {
            Class.forName("com.formdev.flatlaf.FlatLightLaf");
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            flat = true;
        } catch (Exception ex) {
            flat = false;
        }
        if (!flat) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // keep default
            }
        }
    }

    /** Style a primary action button (e.g. Login, Add, Save). */
    public static JButton primaryButton(String text) {
        return styleButton(text, PRIMARY, PRIMARY_DARK, Color.WHITE);
    }

    /** Style a success/positive button (e.g. Add). */
    public static JButton successButton(String text) {
        return styleButton(text, SUCCESS, SUCCESS.darker(), Color.WHITE);
    }

    /** Style a danger/negative button (e.g. Delete). */
    public static JButton dangerButton(String text) {
        return styleButton(text, DANGER, DANGER.darker(), Color.WHITE);
    }

    /** Style an info button (e.g. Update, Search, Refresh). */
    public static JButton infoButton(String text) {
        return styleButton(text, INFO, INFO.darker(), Color.WHITE);
    }

    private static JButton styleButton(String text, Color bg, Color hover, Color fg) {
        JButton b = new JButton(text);
        b.setFont(BUTTON_FONT);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(110, 36));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(bg);
            }
        });
        return b;
    }

    /** Style a standard (secondary) button, e.g. Cancel / Back / Refresh. */
    public static JButton neutralButton(String text) {
        JButton b = new JButton(text);
        b.setFont(BUTTON_FONT);
        b.setBackground(Color.WHITE);
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(110, 36));
        return b;
    }

    /** Creates a single-line text field at a consistent 200x30 size. */
    public static JTextField inputField() {
        JTextField f = new JTextField();
        f.setFont(INPUT_FONT);
        f.setPreferredSize(INPUT_SIZE);
        return f;
    }

    /** Creates a single-line password field at a consistent 200x30 size. */
    public static JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        f.setFont(INPUT_FONT);
        f.setPreferredSize(INPUT_SIZE);
        return f;
    }

    /** Creates a styled field label. */
    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL_FONT);
        l.setForeground(TEXT);
        return l;
    }

    /** Applies a modern style to a {@link javax.swing.JTable}. */
    public static void styleTable(javax.swing.JTable table) {
        table.setFont(INPUT_FONT);
        table.setRowHeight(30);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        table.setGridColor(BORDER);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(241, 243, 250));
        header.setForeground(TEXT);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
    }
}
