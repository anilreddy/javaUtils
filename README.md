# Java Utilies for XML and Datadriven using MetaModel
These are the java utilities for XML and Datadriven Testing in Selenium

```java
  public static void main(String[] args) {
		compressXMLFile("Path/to/xmlFile.xml", "Path/to/xmlFilenametocompress.xml.gz");
		System.out.println("Decompressed XML: " + decompressXMLFile("Path/to/compressedxmlfile.xml.gz"));
		decompressGzipFile("Path/to/compressedXML.xml.gz", "Path/to/newfile.xml");
  }

```
