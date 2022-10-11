package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Comment model information")
@Data
public class CommentDto {
    @ApiModelProperty(value = "Blog Comment id")
    private long id;

    @ApiModelProperty(value = "Blog Comment name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @ApiModelProperty(value = "Blog Comment email")
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @ApiModelProperty(value = "Blog Comment body")
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
