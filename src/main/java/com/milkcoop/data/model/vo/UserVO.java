package com.milkcoop.data.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends RepresentationModel<UserVO> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;

    private String userName;

    private String fullName;

    private String password;

    private String telephone;

    private Long cooperative;

    private List<PermissionVO> permissions;

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (PermissionVO permission : this.permissions) {
            roles.add(permission.getDescription());
        }
        return roles;
    }

}
