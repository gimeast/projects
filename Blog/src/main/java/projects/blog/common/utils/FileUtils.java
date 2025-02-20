package projects.blog.common.utils;


import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projects.blog.common.dto.UploadResultDto;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Log4j2
public class FileUtils {

    @Value("${gimeast.upload.path}")
    private String UPLOAD_PATH;

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(UPLOAD_PATH, folderPath);

        if(!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    public List<UploadResultDto> uploadFiles(MultipartFile[] uploadFiles) {
        List<UploadResultDto> uploadResultDtoList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            if(!uploadFile.getContentType().startsWith("image")) {
                log.warn("this file is not image type");
                return uploadResultDtoList;
            }

            String originFilename = uploadFile.getOriginalFilename();
            String fileName = originFilename != null ? originFilename.substring(originFilename.lastIndexOf("\\") + 1) : null;

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName = UPLOAD_PATH + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath); //이미지 저장

                String thumbnailSaveName = UPLOAD_PATH + File.separator + folderPath + File.separator
                                            + "s_" + uuid + "_" + fileName;

                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                uploadResultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));

            } catch (IOException e) {
                log.info(e.getMessage());
            }

        }

        return uploadResultDtoList;
    }

    public String uploadFile(MultipartFile uploadFile) {
        if (uploadFile == null || uploadFile.isEmpty()) {
            log.warn("Upload file is null or empty.");
            return null;
        }

        if (!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
            log.warn("This file is not an image. Type: {}", uploadFile.getContentType());
            return null;
        }

        String originFilename = uploadFile.getOriginalFilename();
        String fileName = originFilename != null
                ? originFilename.substring(originFilename.lastIndexOf("\\") + 1)
                : null;

        if (fileName == null) {
            log.error("Failed to extract valid file name from {}", originFilename);
            return null;
        }

        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveName = UPLOAD_PATH + File.separator + folderPath + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);

        try {
            // 파일 저장
            uploadFile.transferTo(savePath);
            log.info("Saved file at: {}", savePath);

            // 썸네일 생성
            String thumbnailSaveName = UPLOAD_PATH + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
            File thumbnailFile = new File(thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
            log.info("Created thumbnail at: {}", thumbnailSaveName);

            // 저장된 파일 경로 반환
            return folderPath + File.separator + uuid + "_" + fileName;

        } catch (IOException e) {
            log.error("File upload error for file '{}': {}", originFilename, e.getMessage());
        }

        return null;
    }

    public boolean deleteFiles(String fileName) {
        boolean result = false;

        String srcFileName = null;

        srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

        String originPath = UPLOAD_PATH + File.separator + srcFileName;
        File originFile = new File(originPath);
        result = originFile.delete();

        File thumbnailFile = new File(originFile.getParent(), "s_" + originFile.getName());
        result = thumbnailFile.delete();

        return result;
    }
}
