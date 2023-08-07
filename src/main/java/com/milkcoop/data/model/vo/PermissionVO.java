package com.milkcoop.data.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionVO extends RepresentationModel<PermissionVO> implements Serializable {

    private Long id;
    private String description;
}
