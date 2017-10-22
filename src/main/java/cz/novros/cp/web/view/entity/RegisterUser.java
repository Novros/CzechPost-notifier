package cz.novros.cp.web.view.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUser {
	String email;
	String password;
	String checkPassword;
}
