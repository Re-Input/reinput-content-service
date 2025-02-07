package info.reinput.reinput_content_service.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class S3FilePathUtil {

    /**
     * 랜덤 해시(SHA-256) + UUID 기반 S3 파일 경로 생성
     * @param originalFilename 원본 파일명 (확장자 포함)
     * @return S3 저장 경로
     */
    public static String generateFilePath(String originalFilename) {
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(uuid.getBytes());

            String hashPrefix = String.format("%02x/%02x/%02x", hash[0], hash[1], hash[2]);

            String extension = "";
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex != -1) {
                extension = originalFilename.substring(dotIndex);
            }

            return String.format("%s/%s%s", hashPrefix, uuid, extension);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
