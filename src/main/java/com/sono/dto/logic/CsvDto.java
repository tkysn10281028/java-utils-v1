package com.sono.dto.logic;

import lombok.Data;

@Data
public class CsvDto {
	private String table_name_logical;
	private String table_name_physical;
	private String column_name_logical;
	private String column_name_physical;

	public CsvDto(String table_name_logical, String table_name_physical, String column_name_logical,
			String column_name_physical) {
		this.table_name_logical = table_name_logical;
		this.table_name_physical = table_name_physical;
		this.column_name_logical = column_name_logical;
		this.column_name_physical = column_name_physical;
	}
}
