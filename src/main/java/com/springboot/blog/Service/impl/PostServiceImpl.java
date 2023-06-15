package com.springboot.blog.Service.impl;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.ResponsePostsDTO;
import com.springboot.blog.Service.PostService;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        //Chuyển DTO sang Entity
        Post post=MaptoEntity(postDTO);
        Post newPost=postRepository.save(post);
        //Chuyển Entity sang DTO
        PostDTO PostResponse= MaptoDTO(newPost);

        return PostResponse;
    }
    private PostDTO MaptoDTO(Post post){
        PostDTO postDTO= modelMapper.map(post,PostDTO.class);
        return postDTO;
    }
    private Post MaptoEntity(PostDTO postDTO){
        Post post= modelMapper.map(postDTO,Post.class);
        return post;
    }
    @Override
    public ResponsePostsDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postList= postRepository.findAll(pageable);
        ResponsePostsDTO responsePostsDTO=new ResponsePostsDTO(pageNo,pageSize,sortBy,sortDir,postList.stream().map(post -> MaptoDTO(post)).collect(Collectors.toSet()));
        return responsePostsDTO;
    }

    @Override
    public PostDTO GetbyId(Long id) {
        PostDTO postDTO=MaptoDTO(postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id) ));
        return postDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post=postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id) );
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return MaptoDTO(postRepository.save(post));
    }

    @Override
    public void DeletePost(Long id) {
        Post post=postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id) );
        postRepository.delete(post);
    }
}
