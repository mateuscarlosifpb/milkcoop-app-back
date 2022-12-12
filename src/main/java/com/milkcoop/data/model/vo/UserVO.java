package com.milkcoop.data.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
