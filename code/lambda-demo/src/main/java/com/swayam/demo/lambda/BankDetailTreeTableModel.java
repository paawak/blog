package com.swayam.demo.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class BankDetailTreeTableModel extends AbstractTreeTableModel {

	private static final String[] COLUMN_NAMES = new String[] { "id", "age",
			"job", "marital", "education", "default", "balance" };

	private static final String ROOT = "_ROOT_";

	private final Map<String, List<BankDetail>> groupedBankDetails;
	private final List<String> groups;

	public BankDetailTreeTableModel(
			Map<String, List<BankDetail>> groupedBankDetails) {
		super(ROOT);
		this.groupedBankDetails = groupedBankDetails;
		groups = getGroups(groupedBankDetails);
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length + 1;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Group";
		}
		return COLUMN_NAMES[column - 1];
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof String) {
			if (column == 0) {
				return node;
			}
			return COLUMN_NAMES[column - 1];
		} else if (node instanceof BankDetail) {
			if (column == 0) {
				return null;
			}
			BankDetail bankDetail = (BankDetail) node;
			String[] row = createTableRow(bankDetail);
			return row[column - 1];
		}

		return null;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (ROOT.equals(parent)) {
			return groups.get(index);
		} else if (parent instanceof String) {
			return groupedBankDetails.get(parent).get(index);
		}

		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (ROOT.equals(parent)) {
			return groups.size();
		} else if (parent instanceof String) {
			return groupedBankDetails.get(parent).size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return 0;
	}

	private List<String> getGroups(
			Map<String, List<BankDetail>> groupedBankDetails) {
		List<String> groups = new ArrayList<>(groupedBankDetails.keySet());
		Collections.sort(groups);
		return groups;
	}

	private String[] createTableRow(BankDetail bankDetail) {
		String[] row = new String[18];
		row[0] = Integer.toString(bankDetail.getId());
		row[1] = Integer.toString(bankDetail.getAge());
		row[2] = bankDetail.getJob();
		row[3] = bankDetail.getMarital();
		row[4] = bankDetail.getEducation();
		row[5] = bankDetail.getDefaulted();
		row[6] = bankDetail.getBalance().toPlainString();
		return row;
	}

}
