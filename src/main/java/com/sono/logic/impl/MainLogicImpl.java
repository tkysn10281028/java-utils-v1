package com.sono.logic.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.sono.dto.logic.CsvDto;
import com.sono.dto.logic.EntityFormat;
import com.sono.dto.logic.EntityModeOutputDto;
import com.sono.dto.logic.OutputColumnDto;
import com.sono.dto.proc.BusinessLogicOutputDto;
import com.sono.logic.MainLogic;

public class MainLogicImpl implements MainLogic {
	private List<String> entityList = new ArrayList<>();
	private final String CSV_PATH = "/Users/entakuya/DeskTop/sample.csv";
	private Integer sqlAddCount = 0;

	@Override
	public BusinessLogicOutputDto executeFileModeBusinessLogic(String inputWord) {
		return new BusinessLogicOutputDto(false, "not ready.", null);
	}

	@Override
	public BusinessLogicOutputDto executeEntityModeBusinessLogic(String inputWord) {
		var list = analyzeInputWord(inputWord);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				var csvDto = list.get(i);
				var concatWord = new EntityFormat().getConcatWord(csvDto.getTable_name_physical(),
						csvDto.getTable_name_logical(), csvDto.getColumnList(), i, this.sqlAddCount);
				entityList.add(concatWord);
			}
			sqlAddCount++;
			return new BusinessLogicOutputDto(false, "entity count:" + entityList.size(), this.entityList);
		}
		return new BusinessLogicOutputDto(false, "Error!No Entity Created.", null);
	}

	/**
	 * テーブルのリストを取得する
	 * 
	 * @param inputWord
	 * @return
	 */
	private List<EntityModeOutputDto> analyzeInputWord(String inputWord) {
		var csvList = readCsv();
		if (csvList != null) {

			List<String> list = Arrays.asList(inputWord.split(" ")).stream().filter((str) -> !StringUtils.isEmpty(str))
					.map((str) -> {
						return str.toLowerCase();
					}).collect(Collectors.toList());

			List<String> tableList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				var str = list.get(i);
				if ((str.equals("from") || str.equals("join") || str.equals("into")) && i != list.size() - 1) {
					tableList.add(list.get(i + 1));
				}
			}
			return tableList.stream().map((table) -> {
				var entity = new EntityModeOutputDto();
				var columnlist = list.stream().filter(str -> str.contains(table + ".")).map((str) -> {
					var outputColumn = new OutputColumnDto();
					var columnNameLogical = csvList.stream()
							.filter(csvdto -> csvdto.getTable_name_physical().equals(table))
							.filter(csvdto -> csvdto.getColumn_name_physical()
									.equals(str.replaceAll(table + ".", "").replaceAll(",", "")))
							.map((csvdto) -> {
								return csvdto.getColumn_name_logical();
							}).findFirst().orElse(null);
					outputColumn.setColumn_name_logical(columnNameLogical);
					outputColumn.setColumn_name_physical(str.replaceAll(table + ".", "").replaceAll(",", ""));
					return outputColumn;
				}).collect(Collectors.toList());
				entity.setColumnList(columnlist);
				var tableNameLogical = csvList.stream().filter(csvdto -> csvdto.getTable_name_physical().equals(table))
						.map((csvdto) -> {
							return csvdto.getTable_name_logical();
						}).findFirst().orElse(null);
				entity.setTable_name_logical(tableNameLogical);
				entity.setTable_name_physical(table);
				return entity;
			}).collect(Collectors.toList());
		} else {
			return new ArrayList<EntityModeOutputDto>();
		}

	}

	private List<CsvDto> readCsv() {
		Path path = Paths.get(CSV_PATH);
		try {
			// CSVファイルの読み込み
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			List<CsvDto> outputList = new ArrayList<>();
			for (int i = 1; i < lines.size(); i++) {
				String[] data = lines.get(i).split(",");
				if (data.length > 3) {
					outputList.add(new CsvDto(data[0].replaceAll("\"", ""), data[1].replaceAll("\"", ""),
							data[2].replaceAll("\"", ""), data[3].replaceAll("\"", "")));
				}
			}
			return outputList;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot Read Csv File.");
			return null;
		}
	}

}
