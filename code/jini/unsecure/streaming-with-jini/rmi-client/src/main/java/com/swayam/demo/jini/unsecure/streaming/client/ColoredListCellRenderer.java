package com.swayam.demo.jini.unsecure.streaming.client;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;

public class ColoredListCellRenderer extends JLabel implements ListCellRenderer<BankDetail> {

    private static final long serialVersionUID = 1L;

    private static final String[] COLORS = { "red", "#6A5ACD", "#8B0000", "blue", "#B8860B", "#006400", "#FF1493", "#7FFF00", "#800080", "#A0522D" };

    @Override
    public Component getListCellRendererComponent(JList<? extends BankDetail> list, BankDetail value, int index, boolean isSelected, boolean cellHasFocus) {
	if (isSelected) {
	    setBackground(list.getSelectionBackground());
	    setForeground(list.getSelectionForeground());
	} else {
	    setBackground(list.getBackground());
	    setForeground(list.getForeground());
	}

	setFont(list.getFont());
	setOpaque(true);

	setText(getHtmlWrappedText(value));

	return this;
    }

    private String getHtmlWrappedText(BankDetail value) {
	StringBuilder sb = new StringBuilder(300);
	sb.append("<html>");
	sb.append("<p style=\"color: " + getColor(value) + ";\">");
	sb.append(value.toString());
	sb.append("</p>");
	sb.append("</html>");

	return sb.toString();
    }

    private String getColor(BankDetail value) {
	return COLORS[value.getId() % COLORS.length];
    }

}