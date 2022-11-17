package com.milkcoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkcoop.data.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
