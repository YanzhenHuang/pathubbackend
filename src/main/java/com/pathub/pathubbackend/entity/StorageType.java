package com.pathub.pathubbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The enumeration class for multiple storage types.
 */
@Getter
@RequiredArgsConstructor
public enum StorageType {
    LOCAL("Local", "file://", null),
    ALIYUN_OSS("AliyunOSS", "oss://", "oss-%s.aliyuncs.com"),
    TENCENT_COS("TencentCOS", "", "cos.%s.myqcloud.com"),
    AWS_S3("AWSS3", "s3://", "s3.%s.amazonaws.com"),
    MINIO("MinIO", "minio://", null);

    private final String description;
    private final String scheme;            // The starting point of URI
    private final String endpointTemplate;

    /**
     * @return Whether the storage type is Cloud OSS.
     */
    public boolean isCloud() {
        return this.endpointTemplate != null;
    }

    /**
     * @return Whether the storage type is Local OSS/FS.
     */
    public boolean isLocal() {
        return this.endpointTemplate == null;
    }

    /**
     * Build the endpoint string according to the region.
     * @param region The region of the storage.
     * @return The endpoint string.
     */
    public String buildEndpoint(String region) {
        if (endpointTemplate == null || region == null)
            return null;

        return String.format(endpointTemplate, region);
    }
}
