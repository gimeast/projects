package projects.blog.common.dto;

import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
public class UploadResultDto {

    private String fileName;
    private String uuid;
    private String folderPath;

    public UploadResultDto(String fileName, String uuid, String folderPath) {
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
    }

    public String getImageURL() {
        return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, StandardCharsets.UTF_8);
    }

    public String getThumbnailURL() {
        return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, StandardCharsets.UTF_8);
    }
}
