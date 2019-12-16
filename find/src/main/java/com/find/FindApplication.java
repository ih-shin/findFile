package com.find;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindApplication implements CommandLineRunner{
	
	// 파일 확장자
	private static final String FILE_REG_EXP    = "^([\\S]+(\\.(?i)(xml|java))$)";
	
	// 포함 텍스트
	private static final String TEXT_REG_EXP_01 = ".*brd.*||.*BRD.*";
	
	// 포함 텍스트2
	private static final String TEXT_REG_EXP_02 = ".*\"S\".*||.*\"T\".*||.*\'S\'.*||.*\'T\'.*";
	
	// 루트 경로
	private static final String ROOT_FULL_PATH  = "최상위 경로";
	
	// 생성파일
	private static final String FILE_NAME_00 = "";
	private static final String FILE_NAME_01 = "";
	private static final String FILE_NAME_02 = "";
	
	ArrayList<String> addFileList = new ArrayList<String>();
	
	public static void main(String[] args) {
		SpringApplication.run(FindApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		File saveFile = new File(FILE_NAME_01);
		
		if(saveFile.isFile()) {
			saveFile.delete();
			new File(FILE_NAME_01);
		}
		
		File saveFile2 = new File(FILE_NAME_02);
		
		if(saveFile2.isFile()) {
			saveFile2.delete();
			new File(FILE_NAME_02);
		}

		findText(ROOT_FULL_PATH);
		
		if(addFileList.size() > 0) {
			
			for(String filePath : addFileList) {

				FileReader filereader = new FileReader(filePath);
				BufferedReader bufReader = new BufferedReader(filereader);
				String text = "";
	            while((text = bufReader.readLine()) != null){
	            	if(text.matches(TEXT_REG_EXP_02) && text.trim().length() > 0) {

	            		try {
	                        FileWriter fw = new FileWriter(saveFile2, true);
	                        fw.write(filePath + "\r\n");
	                        fw.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	            		break;
	            	}
	            }
			}
		} 
	}
	
	public void findText(String filePath) throws Exception {
		
		File path = new File(filePath);
		File[] fileList = path.listFiles();
		
		for(File file : fileList) {
			if(file.isDirectory()) {
				findText(file.getAbsolutePath());
			}
			else {
				if(file.getName().matches(FILE_REG_EXP)) {
					FileReader filereader = new FileReader(file);
					BufferedReader bufReader = new BufferedReader(filereader);
					String text = "";
					
					try {
            			File totFile = new File(FILE_NAME_00);
                        FileWriter fw = new FileWriter(totFile, true);
                        fw.write(file.getAbsolutePath() + "||" + file.getName() + "\r\n");
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
					
		            while((text = bufReader.readLine()) != null){
		            	if(text.matches(TEXT_REG_EXP_01) && text.trim().length() > 0) {
		            		addFileList.add(file.getAbsolutePath());
		            		try {
		            			File saveFile = new File(FILE_NAME_01);
		                        FileWriter fw = new FileWriter(saveFile, true);
		                        fw.write(file.getAbsolutePath() + "||" + file.getName() + "\r\n");
		                        fw.close();
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		            		break;
		            	}
		            }
				}
			}
		}
	} 
}
