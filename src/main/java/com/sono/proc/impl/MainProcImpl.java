package com.sono.proc.impl;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.sono.dto.proc.BusinessLogicOutputDto;
import com.sono.logic.MainLogic;
import com.sono.logic.impl.MainLogicImpl;
import com.sono.proc.MainProc;

public class MainProcImpl implements MainProc {
	private MainLogic mainlogic;
	private Scanner scanner;
	private boolean isFileMode;

	/**
	 * 実行プロセス
	 */
	public void execute() {
		init();
		while (true) {
			loopInit(this.isFileMode);
			var inputWord = scanner.next().replaceAll("\n", " ").replaceAll("  ", " ");
			if (inputWord.equals(":f")) {
				System.out.println("File Create Mode.");
				this.isFileMode = true;
			} else if (inputWord.equals(":o")) {
				System.out.println("Entity Mode.");
				this.isFileMode = false;
			} else if (inputWord.equals("exit")) {
				break;
			} else {
				if (returnIsBreak(process(this.isFileMode, inputWord))) {
					break;
				}
			}
		}
		after();

	}

	/**
	 * @param inputWord 標準入力の値
	 * @return BusinessLogicOutputDto 結果返却用DTO
	 */
	public BusinessLogicOutputDto process(boolean isFileMode, String inputWord) {
		BusinessLogicOutputDto output = null;
		if (isFileMode) {
			// ファイル作成モードに変更
			output = this.mainlogic.executeFileModeBusinessLogic(inputWord);
		} else {
			// エンティティ個別生成モードの処理
			output = this.mainlogic.executeEntityModeBusinessLogic(inputWord);
		}
		return output;
	}

	/**
	 * 結果返却DTOを処理する
	 * 
	 * @param 結果返却用DTO
	 * @return 処理を終了するか
	 */
	private boolean returnIsBreak(BusinessLogicOutputDto dto) {
		if (dto.getIsEnd()) {
			System.out.println(dto.getResult());
			return true;
		} else {
			System.out.println(dto.getResult());
			if (dto.getEntityList() != null) {
				dto.getEntityList().forEach((csvDto) -> System.out.println(csvDto));
			}
			return false;
		}
	}

	/**
	 * 最初に実行される処理 入力を受け付ける出力を行い、入力受付を行う
	 */
	private void init() {
		this.mainlogic = new MainLogicImpl();
		this.scanner = new Scanner(System.in);
		this.scanner.useDelimiter(Pattern.compile(";"));
		this.isFileMode = false;
	}

	/**
	 * ループ内の最初に実行する処理
	 */
	private void loopInit(boolean isFileMode) {
		if (this.isFileMode) {
			System.out.print("f->");
		} else {
			System.out.print("o->");
		}

	}

	/**
	 * 全体処理の最後に実行する後処理
	 */
	private void after() {
		this.scanner.close();
		System.out.println("ByeBye!");
	}
}
