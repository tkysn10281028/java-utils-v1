package com.sono.logic;

import com.sono.dto.proc.BusinessLogicOutputDto;

public interface MainLogic {
	public BusinessLogicOutputDto executeFileModeBusinessLogic(String inputWord);

	public BusinessLogicOutputDto executeEntityModeBusinessLogic(String inputWord);
}
