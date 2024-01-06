package store.greeting.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class FileService { // 이미지 파일이 실제 저장소에 생성

  public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    String savedFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + UUID.randomUUID().toString().substring(0,4)+extension;

//    File staticFolder = ResourceUtils.getFile("classpath:static");
//    System.out.println(staticFolder.getAbsolutePath() + "/img/" + savedFileName);
    String fileUploadFullUrl = uploadPath + "/" + savedFileName;

    Path path = Paths.get(fileUploadFullUrl);
    Path parentDir = path.getParent();
    if (parentDir != null && !Files.exists(parentDir)) {
      Files.createDirectories(parentDir);
    }

//    FileOutputStream fos = new FileOutputStream(staticFolder.getAbsolutePath() + "/img/" + savedFileName);
    FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
    fos.write(fileData);
    fos.close();
    return savedFileName;
  }

  public void deleteFile(String filePath) {
    File deleteFile = new File(filePath);
    if (deleteFile.exists()) {
      deleteFile.delete();
      log.info("파일을 삭제했습니다.");
    } else {
      log.info("파일이 존재하지 않습니다.");
    }
  }
}