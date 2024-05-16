package com.abc.model;

import java.util.List;

import lombok.Data;

@Data
public class ExcelUploadDataReponse {
	private int totalData;
	private int sucessimport;
	private int failureimport;
	private List<ExcelUploadReason> reason;
	
	    
}
