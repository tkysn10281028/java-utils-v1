package com.sono.dto.proc;

import java.util.List;

import lombok.Data;

@Data
public class BusinessLogicOutputDto {
	private Boolean isEnd;
	private String result;
	private List<String> entityList;

	public BusinessLogicOutputDto(boolean isEnd, String result, List<String> entityList) {
		this.isEnd = isEnd;
		this.result = result;
		this.entityList = entityList;
	}
}
