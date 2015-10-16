package com.swayam.demo.stomp.server.dto;

public enum BankDetailGroups {

    JOB("job"), MARITAL_STATUS("marital"), EDUCATION("education");

    private final String columnName;

    private BankDetailGroups(String columnName) {
	this.columnName = columnName;
    }

    public String getColumnName() {
	return columnName;
    }

}
