package org.keclipse.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KubeTableGenerator
 * @author https://stackoverflow.com/questions/2745206/output-in-a-table-format-in-javas-system-out
 *
 */
public class KubeTableGenerator {

	private int PADDING_SIZE = 2;
	private String NEW_LINE = "\n";
	private String TABLE_JOINT_SYMBOL = "+";
	private String TABLE_V_SPLIT_SYMBOL = "|";
	private String TABLE_H_SPLIT_SYMBOL = "-";

	public String generateTable(String fullStr) {
		String[] fullArray = fullStr.split(NEW_LINE);
		List<String> headerList = null;
		List<List<String>> strs = new ArrayList<List<String>>();
		if (fullArray.length > 0) {
			headerList =  Arrays.asList(fullArray[0].split("\\s+"));
			strs = new ArrayList<List<String>>();
			for (int i=1;i<fullArray.length;i++) {
				strs.add(Arrays.asList(fullArray[i].split("\\s+")));
			}
		}
		if (headerList != null) {
			return this.generateTable(headerList,strs,1);
		} else {
			return IKubeEclipseConstants.EMPTYSTRING;
		}
	}
	
	public String generateTable(List<String> headersList, List<List<String>> rowsList, int... overRiddenHeaderHeight) {
		StringBuilder stringBuilder = new StringBuilder();

		int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

		Map<Integer, Integer> columnMaxWidthMapping = getMaximumWidhtofTable(headersList, rowsList);

		stringBuilder.append(NEW_LINE);
		stringBuilder.append(NEW_LINE);
		createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
		stringBuilder.append(NEW_LINE);

		for (int headerIndex = 0; headerIndex < headersList.size(); headerIndex++) {
			fillCell(stringBuilder, headersList.get(headerIndex), headerIndex, columnMaxWidthMapping);
		}

		stringBuilder.append(NEW_LINE);

		createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);

		for (List<String> row : rowsList) {

			for (int i = 0; i < rowHeight; i++) {
				stringBuilder.append(NEW_LINE);
			}

			for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
				fillCell(stringBuilder, row.get(cellIndex), cellIndex, columnMaxWidthMapping);
			}

		}

		stringBuilder.append(NEW_LINE);
		createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
		stringBuilder.append(NEW_LINE);
		stringBuilder.append(NEW_LINE);

		return stringBuilder.toString();
	}

	private void fillSpace(StringBuilder stringBuilder, int length) {
		for (int i = 0; i < length; i++) {
			stringBuilder.append(" ");
		}
	}

	private void createRowLine(StringBuilder stringBuilder, int headersListSize,
			Map<Integer, Integer> columnMaxWidthMapping) {
		for (int i = 0; i < headersListSize; i++) {
			if (i == 0) {
				stringBuilder.append(TABLE_JOINT_SYMBOL);
			}

			for (int j = 0; j < columnMaxWidthMapping.get(i) + PADDING_SIZE * 2; j++) {
				stringBuilder.append(TABLE_H_SPLIT_SYMBOL);
			}
			stringBuilder.append(TABLE_JOINT_SYMBOL);
		}
	}

	private Map<Integer, Integer> getMaximumWidhtofTable(List<String> headersList, List<List<String>> rowsList) {
		Map<Integer, Integer> columnMaxWidthMapping = new HashMap<>();

		for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {
			columnMaxWidthMapping.put(columnIndex, 0);
		}

		for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

			if (headersList.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex)) {
				columnMaxWidthMapping.put(columnIndex, headersList.get(columnIndex).length());
			}
		}

		for (List<String> row : rowsList) {

			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {

				if (row.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex)) {
					columnMaxWidthMapping.put(columnIndex, row.get(columnIndex).length());
				}
			}
		}

		for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

			if (columnMaxWidthMapping.get(columnIndex) % 2 != 0) {
				columnMaxWidthMapping.put(columnIndex, columnMaxWidthMapping.get(columnIndex) + 1);
			}
		}

		return columnMaxWidthMapping;
	}

	private int getOptimumCellPadding(int cellIndex, int datalength, Map<Integer, Integer> columnMaxWidthMapping,
			int cellPaddingSize) {
		if (datalength % 2 != 0) {
			datalength++;
		}

		if (datalength < columnMaxWidthMapping.get(cellIndex)) {
			cellPaddingSize = cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
		}

		return cellPaddingSize;
	}

	private void fillCell(StringBuilder stringBuilder, String cell, int cellIndex,
			Map<Integer, Integer> columnMaxWidthMapping) {

		int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);

		if (cellIndex == 0) {
			stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
		}

		fillSpace(stringBuilder, cellPaddingSize);
		stringBuilder.append(cell);
		if (cell.length() % 2 != 0) {
			stringBuilder.append(" ");
		}

		fillSpace(stringBuilder, cellPaddingSize);

		stringBuilder.append(TABLE_V_SPLIT_SYMBOL);

	}

	public static void main(String[] args) {
		KubeTableGenerator tg = new KubeTableGenerator();
		List<String> headersList = new ArrayList<String>();
		headersList.add("Aooooooooooooooooooooo");
		headersList.add("B1111111111111");
		List<String> rList = new ArrayList<String>();
		rList.add("A1BBBBBBBBB");
		rList.add("B1BBBBBBBBB");
		List<String> rList1 = new ArrayList<String>();
		rList1.add("A1111");
		rList1.add("B1111");
		List<List<String>> mlist = new ArrayList<List<String>>();
		mlist.add(rList);
		mlist.add(rList1);
		System.out.println(tg.generateTable("Aoooooo99999ooooooooooo B1111111111111\nA1BBBBBBBBB B1BBBBBBBBB\nA1111 B1111"));
	}
}