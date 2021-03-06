package org.apereo.cas.pm;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * This is {@link PasswordChangeBean}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeBean implements Serializable {
    private static final long serialVersionUID = 8885460875620586503L;

    @Size(min = 1, message = "required.password")
    private String password;

    @Size(min = 1, message = "required.confirmedPassword")
    private String confirmedPassword;
}
