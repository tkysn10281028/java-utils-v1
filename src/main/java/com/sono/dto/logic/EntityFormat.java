package com.sono.dto.logic;

import java.util.List;

import lombok.Data;

@Data
public class EntityFormat {
	private String format1 = "[Entity]";
	private String format2Pname = "\nPname=";
	private String format2Lname = "\nLname=";
	private String format2 = "\nComment=\n" + "TableOption=\n" + "Page=MAIN";
	private String format3SizeLeft = "\nleft=";
	private String format3SizeTop = "\nTop=";
	private String format3Field = "";
	private String format3 = "\nEffectmode=None\n" + "Color=$000000\n" + "Bkcolor=$ffffff\n"
			+ "Modifieddatetime=20230120113854";
	private String format4Left = "\nPosition=“MAIN” ";
	private String format4Top = " ";
	private String format4 = " 100 50\nZorder=";
	private String format4Zorder = "";

	public String getConcatWord(String Pname, String Lname, List<OutputColumnDto> dtoList, Integer listCount,
			Integer entityListSize) {
		this.format2Pname += Pname;
		this.format2Lname += Lname;
		this.format3SizeLeft += (entityListSize * 3 + 1) * 100;
		this.format3SizeTop += (listCount + 5) * 100;
		dtoList.forEach((dto) -> {
			var format3FieldLogicalName = "\nField=“" + dto.getColumn_name_logical();
			var format3FieldPhysicalName = "”,“" + dto.getColumn_name_physical();
			var format3 = "”,“”,,,,””,””,$ffffffff,””";
			format3Field += format3FieldLogicalName + format3FieldPhysicalName + format3;
		});

		this.format4Left += (entityListSize * 3 + 1) * 100;
		this.format4Top += (listCount + 5) * 100;
		this.format4Zorder += (listCount + 3);
		return format1 + format2Pname + format2Lname + format2 + format3SizeLeft + format3SizeTop + format3Field
				+ format3 + format4Left + format4Top + format4 + format4Zorder;
	}
}
