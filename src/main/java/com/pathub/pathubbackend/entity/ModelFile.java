package com.pathub.pathubbackend.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Model model;

    @Column(name = "model_version")
    private String modelVersion;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(nullable = false)
    private Long size;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "storage_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StorageType storageType = StorageType.LOCAL;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;
}
