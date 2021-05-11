# Java Utilies for XML and Datadriven using MetaModel
These are the java utilities for XML and Datadriven Testing in Selenium

```java
  public static void main(String[] args) {
		compressXMLFile("Path/to/xmlFile.xml", "Path/to/xmlFilenametocompress.xml.gz");
		System.out.println("Decompressed XML: " + decompressXMLFile("Path/to/compressedxmlfile.xml.gz"));
		decompressGzipFile("Path/to/compressedXML.xml.gz", "Path/to/newfile.xml");
  }

```

Datadriven Excel file usage

```java

// Add a list of Map for getting list of data and get data
protected List<Map<String, String>> data;

protected ExcelDataReader reader = new ExcelDataReader();

data = reader.getDataFromExcel(FILE_PATH, "SheetName", "Column Names" or "'*' for all", "Filter Name", "Filter Variable");

for (Map<String, String> entry : data) {
	entry.get("Column Name");
}
```
For updating data to Excel file

```java
reader.updateDataIntoExcel(FILE_PATH, "sheetName", "updateColName", "updateValue", "filterColumn", "filterVariable")
```
