package com.pathub.pathubbackend.entity;

import com.pathub.pathubbackend.entity.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "model")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the model.
     */
    @Column(nullable = false, length = 100)
    private String modelName;

    /**
     * A short description of the model.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The model's list of tags. e.g.: NLP, BERT, etc.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "model_tags", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    /**
     * The visibility of the model: Public or Private.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    /**
     * The README markdown string of the model.
     */
    @Column(columnDefinition = "TEXT")
    private String readme;

    /**
     * The downloads of the model.
     */
    @Column(nullable = false)
    private Long downloads = 0L;

    /**
     * The author of the model.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User author;

    /**
     * The list of files the model belongs to.
     */
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<ModelFile> files = new ArrayList<>();

    /**
     * Timestamp of the creation time.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update time.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void addTag(String tag) {
        if (this.tags == null)
            this.tags = new ArrayList<>();

        if (!this.tags.contains(tag))
            this.tags.add(tag);
    }

    public void removeTag(String tag) {
        if (this.tags != null)
            this.tags.remove(tag);
    }

    public void addFile(ModelFile file) {
        this.files.add(file);
        file.setModel(this);
    }

    public void removeFile(ModelFile file) {
        this.files.remove(file);
        file.setModel(null);    // TODO: Should be NULL or default?
    }
}
