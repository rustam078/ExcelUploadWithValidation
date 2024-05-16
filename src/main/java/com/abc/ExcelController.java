package com.abc;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abc.model.ExcelUploadDataReponse;
import com.abc.service.ExcelUploadService;


@RestController
public class ExcelController {

	@Autowired
	private ExcelUploadService excelUploadService;
	@PostMapping("/upload")
	public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file){
		
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if("xlsx".equals(extension)||"csv".equals(extension)) {
			 ExcelUploadDataReponse excel = excelUploadService.uploadExcel(file);
			 return new ResponseEntity<>(excel,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("please upload only csv or xlsx file only !",HttpStatus.BAD_REQUEST);
		}
	}
}
