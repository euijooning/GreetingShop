package store.greeting.product.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@RequiredArgsConstructor
@Log
public class FileService {


  public String uploadFile(String uploadPath, String originalFileName, byte[] fileData)
      throws Exception {
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    String savedFileName =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + extension;

    File staticFolder = ResourceUtils.getFile("classpath:static");
    System.out.println(staticFolder.getAbsolutePath() + "/img/" + savedFileName);

    FileOutputStream fos = new FileOutputStream(
        staticFolder.getAbsolutePath() + "/img/" + savedFileName);
    fos.write(fileData);
    fos.close();
    return savedFileName;
  }

  public void deleteFile(String filePath) throws Exception {
    File deleteFile = new File(filePath);
    if (deleteFile.exists()) {
      deleteFile.delete();
      log.info("파일을 삭제했습니다.");
    } else {
      log.info("파일이 존재하지 않습니다.");
    }
  }
}