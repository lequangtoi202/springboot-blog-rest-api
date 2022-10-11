package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post model information")
@Data
public class PostDto {
    @ApiModelProperty(value = "Blog Post id")
    private Long id;

    @ApiModelProperty(value = "Blog Post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters ")
    private String title;

    @ApiModelProperty(value = "Blog Post description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters ")
    private String description;

    @ApiModelProperty(value = "Blog Post content")
    @NotEmpty
    private String content;

    @ApiModelProperty(value = "Blog Post comments")
    private Set<CommentDto> comments;
}
