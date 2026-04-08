package edu.eci.cvds.labReserves.model;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user in LabReserves system.
 * A user has an ID, name, email, password, and a role.
 * The allowed roles are "teacher" and "admin".
 */
@NoArgsConstructor
@Getter
@Setter
public class User {

    /** Set of valid role types for a user. */
    private static Set<String> rolType = Set.of("teacher", "admin");

    /** Unique identifier for the user. */
    private int id;

    /** Full name of the user. */
    private String name;

    /** Email address of the user. */
    private String mail;

    /** Password associated with the user account. */
    private String password;

    /** Role assigned to the user. */
    private String rol;

    /**
     * Constructs a new User with the given attributes.
     *
     * @param pId       Unique identifier for the user.
     * @param pName     Full name of the user.
     * @param pMail     Email address of the user.
     * @param pPassword Password associated with the account.
     * @param pRol      Role assigned to the user.
     * @throws LabReserveException If the provided role is not valid.
     */
    public User(final int pId, final String pName, final String pMail,
                final String pPassword, final String pRol)
            throws LabReserveException {
        this.id = pId;
        this.name = pName;
        this.mail = pMail;
        this.password = pPassword;
        if (rolType.contains(pRol)) {
            this.rol = pRol;
        } else {
            throw new LabReserveException(LabReserveException.INVALID_ROL_TYPE);
        }
    }

    /**
     * Sets a new role for a user.
     *
     * @param pNewRol The new role to assign.
     * @throws LabReserveException If the role is invalid.
     */
    public final void setRol(final String pNewRol) throws LabReserveException {
        if (rolType.contains(pNewRol)) {
            this.rol = pNewRol;
        } else {
            throw new LabReserveException(LabReserveException.INVALID_ROL_TYPE);
        }
    }
}
