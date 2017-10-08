package cz.novros.cp.dao;

import javax.annotation.Nullable;

import org.springframework.data.repository.CrudRepository;

import lombok.NonNull;

import cz.novros.cp.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	@Nullable
	User findByEmailAndPassword(@NonNull final String email, @NonNull final String password);
}
