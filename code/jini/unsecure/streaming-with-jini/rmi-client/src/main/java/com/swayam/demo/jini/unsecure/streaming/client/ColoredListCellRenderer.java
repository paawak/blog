package com.swayam.demo.jini.unsecure.streaming.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;

public class ColoredListCellRenderer extends JLabel implements ListCellRenderer<BankDetail> {

    private static final long serialVersionUID = 1L;

    private static final String[] COLORS = { "#FF0000", "#6A5ACD", "#8B0000", "#0000FF", "#B8860B", "#006400", "#FF1493", "#7FFF00", "#800080", "#A0522D" };

    @Override
    public Component getListCellRendererComponent(JList<? extends BankDetail> list, BankDetail value, int index, boolean isSelected, boolean cellHasFocus) {

	String foregroundColorHexCode;

	if (isSelected) {
	    String colorString = getColor(value);
	    Integer colorCode = Integer.parseInt(colorString.substring(1), 16);
	    Color bgColor = new Color(colorCode);
	    setBackground(bgColor);
	    foregroundColorHexCode = "#ffffff";
	} else {
	    setBackground(list.getBackground());
	    foregroundColorHexCode = getColor(value);
	}

	setFont(list.getFont());
	setOpaque(true);

	setText(getHtmlWrappedText(value, foregroundColorHexCode));

	return this;
    }

    private String getHtmlWrappedText(BankDetail value, String foregroundColorHexCode) {
	StringBuilder sb = new StringBuilder(100);
	sb.append("<html>");
	sb.append("<p style=\"color: ");
	sb.append(foregroundColorHexCode);
	sb.append(";\">");
	sb.append(value);
	sb.append("</p>");
	sb.append("</html>");

	return sb.toString();
    }

    private String getColor(BankDetail value) {
	return COLORS[value.getId() % COLORS.length];
    }

}