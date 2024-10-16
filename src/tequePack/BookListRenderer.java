package tequePack;

import javax.swing.*;
import java.awt.*;

public class BookListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String text = value.toString();

        String[] parts = text.split("%50");
        String title = parts.length > 0 ? parts[0].trim() : "";
        String author = parts.length > 1 ? parts[1].trim() : "";
        String status = parts.length > 2 ? parts[2].trim() : "Disponible";

        int totalWidth = list.getWidth();
        int titleWidth = (int) (totalWidth * 0.5);
        int authorWidth = (int) (totalWidth * 0.3);
        int statusWidth = (int) (totalWidth * 0.2);

        FontMetrics metrics = label.getFontMetrics(label.getFont());

        String truncatedTitle = truncateTextToWidth(title, metrics, titleWidth);
        String truncatedAuthor = truncateTextToWidth(author, metrics, authorWidth);
        String truncatedStatus = truncateTextToWidth(status, metrics, statusWidth);

        label.setText(padText(truncatedTitle, titleWidth, metrics) +
                padText(truncatedAuthor, authorWidth, metrics) +
                truncatedStatus);

        return label;
    }

    private String truncateTextToWidth(String text, FontMetrics metrics, int width) {
        if (metrics.stringWidth(text) <= width) {
            return text;
        }
        String ellipsis = "...";
        int ellipsisWidth = metrics.stringWidth(ellipsis);

        for (int i = text.length() - 1; i > 0; i--) {
            String truncatedText = text.substring(0, i);
            if (metrics.stringWidth(truncatedText) + ellipsisWidth <= width) {
                return truncatedText + ellipsis;
            }
        }
        return ellipsis;
    }

    private String padText(String text, int width, FontMetrics metrics) {
        int textWidth = metrics.stringWidth(text);
        int spaceWidth = metrics.stringWidth(" ");

        int paddingSpaces = (width - textWidth) / spaceWidth;

        StringBuilder paddedText = new StringBuilder(text);
        for (int i = 0; i < paddingSpaces; i++) {
            paddedText.append(" ");
        }
        return paddedText.toString();
    }
}
