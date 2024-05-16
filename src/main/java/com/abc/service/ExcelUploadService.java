package com.abc.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abc.model.Customer;
import com.abc.model.ExcelUploadDataReponse;
import com.abc.model.ExcelUploadReason;
import com.abc.repo.CustomerRepo;

@Service
public class ExcelUploadService {

	@Autowired
	private CustomerRepo cusrepo;
	public ExcelUploadDataReponse uploadExcel(MultipartFile file) {
		
		ExcelUploadDataReponse reponse = new ExcelUploadDataReponse();
		List<ExcelUploadReason> list = new LinkedList<>();
		ArrayList<Customer> arrayList = new ArrayList<>();
		try (InputStream is = file.getInputStream();
				XSSFWorkbook wb = new XSSFWorkbook(is)) {
			XSSFSheet sheetAt = wb.getSheetAt(0);
			reponse.setTotalData(sheetAt.getPhysicalNumberOfRows()-1);
		for(int i=1;i<sheetAt.getPhysicalNumberOfRows();i++) {
			XSSFRow row = sheetAt.getRow(i);
			ExcelUploadReason uploadReason = new ExcelUploadReason();
			Customer customer = new Customer();

			String cid =getCellValue(row,0);
			String cname =getCellValue(row,1);
			String contactname =getCellValue(row,2);
			String caddress =getCellValue(row,3);
			String city =getCellValue(row,4);
			String postalcode =getCellValue(row,5);
			String country =getCellValue(row,6);
			
			if(cid==null||cid.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("CustomerId");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("CustomerID not be empty");
				list.add(uploadReason);
				continue;
			}
			if(cname==null||cname.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("Customer Name");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("cname not be empty");
				list.add(uploadReason);
				continue;
			}
			if(contactname==null||contactname.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("contact name");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("contactname not be empty");
				list.add(uploadReason);
				continue;
			}
			if(caddress==null||caddress.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("address");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("address not be empty");
				list.add(uploadReason);
				continue;
			}
			if(city==null||city.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("city");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("city not be empty");
				list.add(uploadReason);
				continue;
			}
			if(postalcode==null||postalcode.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("postalcode");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("postalcode not be empty");
				list.add(uploadReason);
				continue;
			}
			else if(!(postalcode.length()==5)) {
				uploadReason.setId(i);
				uploadReason.setCauseType("postalcode");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("postalcode Length must be 5 digit");
				list.add(uploadReason);
				continue;
			}
			
			if(country==null||country.isEmpty()) {
				uploadReason.setId(i);
				uploadReason.setCauseType("country");
				uploadReason.setStatus("failed");
				uploadReason.setMessage("country not be empty");
				list.add(uploadReason);
				continue;
			}
			customer.setCustomerId(Integer.parseInt(cid));
			customer.setContactName(contactname);
			customer.setCustomerName(cname);
			customer.setAddress(caddress);
			customer.setCity(city);
			customer.setPostalCode(postalcode);
			customer.setCountry(country);
			
			arrayList.add(customer);
			
		}
		List<Customer> saveAll = cusrepo.saveAll(arrayList);
		reponse.setSucessimport(saveAll.size());
		reponse.setFailureimport(reponse.getTotalData()- reponse.getSucessimport());
		List<ExcelUploadReason> collect = saveAll.stream().map(e->{
			 ExcelUploadReason excelUploadReason = new ExcelUploadReason();
			 excelUploadReason.setId(e.getCustomerId());
			 excelUploadReason.setStatus("sucess");
			 excelUploadReason.setCauseType(e.getCustomerName());
			 excelUploadReason.setMessage("sucess");
			 return excelUploadReason;
		}).collect(Collectors.toList());
		list.addAll(collect);
		reponse.setReason(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reponse;
	}

	
	private String getCellValue(XSSFRow row, int i) {
		XSSFCell cell = row.getCell(i);
		if (cell != null) {
			if (cell.getCellType().equals(CellType.STRING)) {
				return cell.getStringCellValue();
			} else {
				switch (cell.getCellType()) {
				case NUMERIC:
					return String.valueOf((int) cell.getNumericCellValue());
				case BOOLEAN:
					return String.valueOf(cell.getBooleanCellValue());
				case BLANK:
					return String.valueOf(cell.getStringCellValue());
				case FORMULA:
					return String.valueOf(cell.getStringCellValue());
				case ERROR:
					return String.valueOf(cell.getStringCellValue());
				default:
					return "";
				}

			}
		} else {
			return "";
		}
	}
}
