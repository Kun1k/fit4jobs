package com.zhaw.fit4jobs.repository.user;

import com.zhaw.fit4jobs.models.user.UserDetailsModel;
import com.zhaw.fit4jobs.models.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, Long> {
	
	UserDetailsModel findByUserId(UserModel userId);

	@Query("SELECT p FROM UserDetailsModel p where lower(p.name)  like lower(concat('%', :searchInput,'%')) or lower(p.surname) like lower(concat('%', :searchInput,'%')) ")
	List<UserDetailsModel> searchForUsers(@Param("searchInput") String searchInput);

	Boolean existsByUserId(UserModel userId);
}
