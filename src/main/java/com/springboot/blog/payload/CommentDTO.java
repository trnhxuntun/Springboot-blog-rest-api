package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty(message = "Không được để trống!")
    @Size(min = 2,message = "Tên phải có ít nhất 2 ký tự!")
    private String name;
    @NotEmpty(message = "Không được để trống!")
    @Email(message = "Định dạng Email không đúng!")
    private String email;
    @NotEmpty(message = "Không được để trống!")
    private String body;
}
