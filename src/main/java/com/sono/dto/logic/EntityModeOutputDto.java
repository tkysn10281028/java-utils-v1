package com.sono.dto.logic;

import java.util.List;

import lombok.Data;

@Data
public class EntityModeOutputDto {
	private String table_name_physical;
	private String table_name_logical;
	private List<OutputColumnDto> columnList;
}
