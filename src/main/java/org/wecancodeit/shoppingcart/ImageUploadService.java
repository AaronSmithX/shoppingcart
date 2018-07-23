package org.wecancodeit.shoppingcart;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadService {
	
	private String getUploadDirectory() {
		// Determine where uploads should be saved
        String userHomeDirectory = System.getProperty("user.home");
        String uploadDirectory = Paths.get(userHomeDirectory, "spring-uploads").toString();
        
        // Create if needed
        new File(uploadDirectory).mkdirs();
        
        // Return path
        return uploadDirectory;
	}
	
	private String getFileExtension(String fileName) {
	    try {
	        return fileName.substring(fileName.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	public String uploadMultipartFile(MultipartFile imageFile) throws Exception {
		// Upload image - stream uploaded data to a temporary file
		String fileName = imageFile.getOriginalFilename();
		if ("".equalsIgnoreCase(fileName)) {
			throw new Exception();
		}
		File tempFile = File.createTempFile(fileName, "");
        FileOutputStream fos = new FileOutputStream(tempFile); 
        fos.write(imageFile.getBytes());
        fos.close(); 
		
        // Transfer the temporary file to its permanent location
        String fileExtension = getFileExtension(fileName);
        String virtualFileUrl = UUID.randomUUID().toString() + "." + fileExtension;
		File fileUpload = new File(getUploadDirectory(), virtualFileUrl);
		imageFile.transferTo(fileUpload);
		
		return virtualFileUrl;
	}

	public File getUploadedFile(String fileName)  {
		Path filePath = Paths.get(getUploadDirectory(), fileName);
		String filePathString = filePath.toString();
		return new File(filePathString);
	}
	
}
