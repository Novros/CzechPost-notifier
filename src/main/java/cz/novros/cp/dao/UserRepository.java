package cz.novros.cp.dao;

import org.springframework.data.repository.CrudRepository;

import lombok.NonNull;

import cz.novros.cp.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	User findByEmailAndPassword(@NonNull final String email, @NonNull final String password);
}
