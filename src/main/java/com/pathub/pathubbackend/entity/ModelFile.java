package com.pathub.pathubbackend.entity;

import com.pathub.pathubbackend.entity.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "model_file",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"model_id", "file_name"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The model that the file belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Model model;

    /**
     * The version of the model.
     */
    @Column(name = "model_version")
    private String modelVersion;

    /**
     * Name of the model file.
     */
    @Column(nullable = false, length = 128)
    private String filename;

    /**
     * Size of the model file, in MB.
     */
    @Column(nullable = false)
    private Long size;

    /**
     * MIME type of the model file.
     */
    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    /**
     * URI of the file, could be native, self-deploy or online OSS.
     */
    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    /**
     * The storage type of the file.
     */
    @Column(name = "storage_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StorageType storageType = StorageType.NATIVE;

    /**
     * Timestamp of the upload time.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;
}
