package com.domo.selenium.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FileUtil {
	private Map<String, HSSFWorkbook> workbooks = new HashMap<String, HSSFWorkbook>();
	private List<InputStream> FISs = new ArrayList<InputStream>();
	private Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();

	public FileUtil() {
		// buildupPropertiesBundles("./src/test/resources");
	}

	public FileUtil(String resourcesLocation) {
		buildupPropertiesBundles(resourcesLocation);
	}

	private void buildupPropertiesBundles(String file) {
		File[] files = new File(file).listFiles();

		for (File f : files) {
			if (f.getName().endsWith("properties")) {
				ResourceBundle bundle = ResourceBundle.getBundle(f.getName()
						.substring(0, f.getName().indexOf("properties") - 1));
				bundles.put(f.getName(), bundle);
			}
		}
	}

	/**
	 * Loads a locale specific bundle.
	 * 
	 * @param bundleName
	 *            Name of the bundle to be loaded. This name must be fully
	 *            qualified.
	 * @param locale
	 *            Locale for which the resource bundle will be loaded.
	 */
	public void loadPropertiesBundle(String bundleName, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		bundles.put(bundleName, bundle);
	}

	/**
	 * Loads a properties bundle with the default locale.
	 * 
	 * @param bundleName
	 *            Name of the bundle to be loaded. This name must be fully
	 *            qualified.
	 */
	public void loadPropertiesBundle(String bundleName) {
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
		bundles.put(bundleName, bundle);
	}

	/**
	 * Loads the properties from a file specified as a parameter. This file may
	 * or may not be external.
	 * 
	 * @param propertiesFile
	 *            Path to the properties file.
	 */
	public void loadPropertiesFromFile(String propertiesFile) {
		File file = new File(propertiesFile);
		String bundleName = file.getPath().substring(0,
				file.getPath().indexOf("properties") - 1);
		FileInputStream inputStream;

		try {
			inputStream = new FileInputStream(file);

			ResourceBundle bundle = new PropertyResourceBundle(inputStream);
			bundles.put(bundleName, bundle);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores a property in the given properties file.
	 * <p/>
	 * If the file does not exist, it will be created. <br/>
	 * If the property already exists, it will be overridden. Otherwise, it will
	 * be added.
	 * 
	 * @param filePath
	 *            Path to the file to store the property into.
	 * @param propertyKey
	 *            The key of the property to be stored.
	 * @param propertyValue
	 *            The value of the property to be stored.
	 */
	public void storeProperty(String filePath, String propertyKey,
			String propertyValue) {
		Properties properties = new Properties();
		File file = new File(filePath);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;

		try {
			// create the properties file if it does not exist.
			if (!file.exists()) {
				file.createNewFile();
			}

			// read all properties from the file
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
			inputStream.close();

			// add the new property
			properties.put(propertyKey, propertyValue);

			// store all properties to the same file
			outputStream = new FileOutputStream(file);
			properties.store(outputStream, null);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPropertyAsString(String key) {
		for (ResourceBundle bundle : bundles.values()) {
			try {
				if (bundle.getString(key) != null) {
					return bundle.getString(key);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public int getPropertyAsInteger(String key) {
		for (ResourceBundle bundle : bundles.values()) {
			try {
				if (bundle.getString(key) != null) {
					return Integer.parseInt(bundle.getString(key));
				}
			} catch (Exception e) {
			}
		}
		return -1;
	}

	public double getPropertyAsDouble(String key) {
		for (ResourceBundle bundle : bundles.values()) {
			try {
				if (bundle.getString(key) != null) {
					return Double.parseDouble(bundle.getString(key));
				}
			} catch (Exception e) {
			}
		}
		return -1;
	}

	public String readFromExcel(String fileName, String sheetName,
			int rowNumber, int cellNumber) throws IOException {
		HSSFWorkbook workbook = getWorkbook(fileName);
		HSSFSheet payments = workbook.getSheet(sheetName);

		HSSFRow row = payments.getRow(rowNumber);
		HSSFCell cell = row.getCell(cellNumber);

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		}

		return null;
	}

	private HSSFWorkbook getWorkbook(String name) throws IOException {
		if (workbooks.get(name) == null) {
			FileInputStream fis = new FileInputStream(new File(name));
			FISs.add(fis);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			workbooks.put(name, workbook);
		}

		return workbooks.get(name);
	}

	public int getExcelRowsNumber(String fileName, String sheetName)
			throws IOException {
		// get rows number
		HSSFWorkbook workbook = getWorkbook(fileName);
		HSSFSheet payments = workbook.getSheet(sheetName);
		return payments.getPhysicalNumberOfRows();
		// return rows;

	}

	public void releaseResources() {
		for (InputStream str : FISs) {
			try {
				str.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * You must pass the xml string and the tag name. This should be called for
	 * situations like this: <age>18</age>
	 * 
	 * In this case, in order to get the 18 value we call the method like this:
	 * getTagText(xml, "age"); and this will return 18
	 * 
	 * The method will return the first tag it encounters
	 * 
	 * @param xmlString
	 * @param tagName
	 * @return
	 */
	public String getTagText(String xmlString, String tagName) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			dbf.setFeature("http://xml.org/sax/features/validation", false);
			dbf.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
					false);
			dbf.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);

			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));

			Document doc = db.parse(is);
			System.out.println("Getting tagName: " + tagName);
			NodeList nodes = doc.getElementsByTagName(tagName);

			System.out.println("Elements returned = " + nodes.getLength());

			Element element = (Element) nodes.item(0);

			System.out.println("element text = " + element.getTextContent());

			return element.getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * You must pass the xml string the tage name and the attribute of the tag
	 * that you want to be returned This should be called for situations like
	 * this: <age gender="male">18</age>
	 * 
	 * In this case, in order to get the MALE value we call the method like
	 * this: getValueOfTagAttribute(xml, "age", "gender"); and this will return
	 * MALE
	 * 
	 * The method returns the first tag it encounter
	 * 
	 * @param xmlString
	 * @param tagName
	 * @param attribute
	 * @return
	 */
	public String getValueOfTagAttribute(String xmlString, String tagName,
			String attribute) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			dbf.setFeature("http://xml.org/sax/features/validation", false);
			dbf.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
					false);
			dbf.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);

			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));

			Document doc = db.parse(is);
			System.out.println("Getting tagName: " + tagName);
			NodeList nodes = doc.getElementsByTagName(tagName);

			System.out.println("Elements returned = " + nodes.getLength());

			Element element = (Element) nodes.item(0);

			System.out.println("element text = "
					+ element.getAttribute(attribute));

			return element.getAttribute(attribute);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getFileContentsAsString(String fileName) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(this.getClass().getResourceAsStream(
					fileName));

			StringBuilder builder = new StringBuilder();

			while (bis.available() > 0) {
				builder.append((char) bis.read());
			}

			return builder.toString();
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}
}
