package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;

    @NotEmpty@NotEmpty(message = "Không được để trống")
    @Size(min = 2,message = "Tiêu đề bài viết phải có ít nhất 2 ký tự")
    private String title;
    @NotEmpty@NotEmpty(message = "Không được để trống")
    @Size(min = 10,message = "Mô tả bài viết phải có ít nhất 10 ký tự")
    private String description;
    @NotEmpty(message = "Không được để trống")
    private String content;
    private Set<CommentDTO> comments;
}
