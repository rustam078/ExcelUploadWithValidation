package com.abc.model;

import lombok.Data;

@Data
public class ExcelUploadReason {
	private int id;
	private String status;
    private String causeType;
    private String message;
}
