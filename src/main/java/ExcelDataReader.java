import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.UpdateSummary;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.excel.ExcelConfiguration;
import org.apache.metamodel.excel.ExcelDataContext;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.update.Update;

public class ExcelDataReader {

	ExcelConfiguration excelConfiguration;
	ExcelDataContext dataContext;
	Schema schema;
	Table table;
	DataSet dataSet;
	UpdateSummary updateData;
	UpdateableDataContext updateableDataContext;

	public ExcelDataContext setExcelFile(String fileName) throws IOException {
		if (StringUtils.isNotEmpty(fileName)) {
			return dataContext = new ExcelDataContext(new File(fileName));
		} else {
			throw new IOException();
		}
	}

	public UpdateableDataContext setUpdateExcelFile(String fileName) throws IOException {
		if (StringUtils.isNotEmpty(fileName)) {
			return updateableDataContext = new ExcelDataContext(new File(fileName));
		} else {
			throw new IOException();
		}

	}

	public Table getSheetData(String sheetName) {
		schema = dataContext.getDefaultSchema();
		this.table = schema.getTableByName(sheetName);
		return this.table;
	}

	public Table getUpdateSheetData(String fileName, String sheetName) {
		if (StringUtils.isNotEmpty(fileName)) {
			updateableDataContext = new ExcelDataContext(new File(fileName));
		}
		schema = updateableDataContext.getDefaultSchema();
		this.table = schema.getTableByName(sheetName);
		return this.table;
	}

	public List<Map<String, String>> getDataFromExcel(String fileName, String sheetName, String colNames,
			String filterColumn, String filterVariable) throws IOException {
		List<Map<String, String>> rowData = new ArrayList<>();

		dataSet = setExcelFile(fileName).query().from(getSheetData(sheetName))
				.select(colNames.isEmpty() ? "*" : colNames).where(filterColumn).eq(filterVariable).execute();
		List<Column> columns = this.table.getColumns();
		while (dataSet.next()) {
			Row row = dataSet.getRow();
			Map<String, String> rows = new HashMap<>();
			for (int i = 0; i < columns.size(); i++) {
				rows.put(columns.get(i).getName(), getColVal(columns, row, i));
			}
			rowData.add(rows);
		}
		dataSet.close();
		return rowData;
	}

	public String updateDataIntoExcel(String fileName, String sheetName, String updateColName, String updateValue,
			String filterColumn, String filterVariable) throws IOException {
		UpdateScript updateScript = new Update(getUpdateSheetData(fileName, sheetName)).where(filterColumn)
				.eq(filterVariable).value(updateColName, updateValue);
		updateData = setUpdateExcelFile(fileName).executeUpdate(updateScript);
		if (StringUtils.equalsIgnoreCase(updateData.getUpdatedRows().toString(), "Optional.empty")) {
			return "Row Updated Successfully";
		} else {
			return "Failed";
		}
	}

	public String getColVal(List<Column> columns, Row row, int i) {
		if (row.getValue(columns.get(i)) == null) {
			return null;
		} else {
			return String.valueOf(row.getValue(columns.get(i)));
		}
	}
}
