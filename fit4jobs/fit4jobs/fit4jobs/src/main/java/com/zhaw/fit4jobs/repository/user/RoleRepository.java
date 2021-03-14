package com.zhaw.fit4jobs.repository.user;

import com.zhaw.fit4jobs.models.user.ERole;
import com.zhaw.fit4jobs.models.user.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
	Optional<RoleModel> findByName(ERole name);
}
