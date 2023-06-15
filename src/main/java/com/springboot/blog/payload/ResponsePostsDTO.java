package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostsDTO {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortDir;
    private Set<PostDTO> content;
}
