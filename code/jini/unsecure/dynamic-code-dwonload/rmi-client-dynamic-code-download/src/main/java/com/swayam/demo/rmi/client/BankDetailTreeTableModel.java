package com.swayam.demo.rmi.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class BankDetailTreeTableModel extends AbstractTreeTableModel {

    private static final String[] COLUMN_NAMES = new String[] { "id", "age", "job", "marital", "education", "default", "balance" };

    private static final String ROOT = "_ROOT_";

    private final Map<String, List<?>> groupedBankDetails;
    private final List<String> groups;

    public BankDetailTreeTableModel(Map<String, List<?>> groupedBankDetails) {
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
        } else if (node instanceof Object) {
            if (column == 0) {
                return null;
            }
            return displayColumnValue(node, column - 1);
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

    private List<String> getGroups(Map<String, List<?>> groupedBankDetails) {
        List<String> groups = new ArrayList<>(groupedBankDetails.keySet());
        Collections.sort(groups);
        return groups;
    }

    private String displayColumnValue(Object bankDetail, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return invokeMethodOnBankDetail(bankDetail, "getId").toString();
        case 1:
            return invokeMethodOnBankDetail(bankDetail, "getAge").toString();
        case 2:
            return (String) invokeMethodOnBankDetail(bankDetail, "getJob");
        case 3:
            return (String) invokeMethodOnBankDetail(bankDetail, "getMarital");
        case 4:
            return (String) invokeMethodOnBankDetail(bankDetail, "getEducation");
        case 5:
            return (String) invokeMethodOnBankDetail(bankDetail, "getDefaulted");
        case 6:
            return invokeMethodOnBankDetail(bankDetail, "getBalance").toString();
        default:
            throw new IllegalArgumentException("columnIndex " + columnIndex + " is not handled");
        }
    }

    private Object invokeMethodOnBankDetail(Object bankDetail, String methodName) {
        Method method;
        try {
            method = bankDetail.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        try {
            return method.invoke(bankDetail);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
