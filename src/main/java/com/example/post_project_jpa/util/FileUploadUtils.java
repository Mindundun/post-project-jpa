package com.example.post_project_jpa.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.example.post_project_jpa.dto.ArticleFileDto;

import jakarta.annotation.PostConstruct;

import net.coobird.thumbnailator.Thumbnailator;

@Component // 싱글톤
public class FileUploadUtils {
    @Value("${spring.file.upload.path}")   // application.yml 파일의 값을 가져옴
    private String uploadPath;      // ./upload 경로가 지정 된다. => /home/user/post-project/upload 경로

    @PostConstruct  // 스프링부트 실행 시 스프링컨테이너 초기화(빈 생성, DI) 완료가 되는데, 이때 한번만 호출
    public void init(){
        System.out.println(">>> uploadPath: " + uploadPath);
        // upload 디렉토리가 없는 경우 생성
        File file = new File(uploadPath);
        
        if (!file.exists()) {
            file.mkdir();
        }
        System.out.println("-----------------------------------------------uploadPath : " + file.getAbsolutePath());
    }
    
    public List<ArticleFileDto> uploadFiles(List<MultipartFile> multipartFiles) {

        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }
        // 로컬 내 존재 : 스레드에 안전.(싱글톤)
        // 만약 전역으로 선언 시 파일이 중복처리될 수 있는 등 문제 발생
        List<ArticleFileDto> files = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            // UUID : 32자리 16진수
            String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();

            // 업로드할 파일 경로
            Path savePath = Paths.get(uploadPath, fileName);

            try{
                // 파일 복사
                Files.copy(multipartFile.getInputStream(), savePath);

                String contentType = multipartFile.getContentType();
                
                // 업로드된 파일이 이미지인 경우
                if (contentType != null && contentType.startsWith("image")){
                    
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + fileName);
                    // 원본 이미지 파일을 읽어서 200 * 200 크기의 썸네일 이미지를 만들어 지정한 경로에 저장, 첫인자: 원본파일.
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailPath.toFile(), 200, 200);
                }
                
                // ArticleFileDto로 바꿈
                ArticleFileDto articleFileDto = ArticleFileDto.builder()
                                                    .fileName(fileName)
                                                    .filePath(uploadPath)
                                                    .fileSize(multipartFile.getSize())
                                                    .build();
                                                    
                files.add(articleFileDto);

            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        
        return files;
    }
}
