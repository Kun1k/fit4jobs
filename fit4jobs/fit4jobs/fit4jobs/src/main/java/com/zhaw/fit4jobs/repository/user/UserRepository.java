package com.zhaw.fit4jobs.repository.user;

import com.zhaw.fit4jobs.models.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	UserModel findByEmail(String email);

	Boolean existsByEmail(String email);
}
