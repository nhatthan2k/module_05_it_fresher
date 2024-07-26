package com.ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryRequest {
    @NotBlank(message = "CategoryName không được trống!")
    private String name;
    @NotBlank(message = "Hãy nhập mô tả danh mục!")
    private String description;
    private boolean status;
}
