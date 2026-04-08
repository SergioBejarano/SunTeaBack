package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

/**
 * Represents a reservation for a laboratory made by a user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserve {

    /** Types of reserves allowed. */
    private static String[] typeReserve = new String[] {"lesson", "available"};

    /** States of reserves allowed. */
    private static String[] stateReserve =
        new String[] {"reserved", "occupied", "free"};

    /** Type of this reserve. */
    private String type;

    /** Reason why this reserve was created. */
    private String reason;

    /** State of this reserve. */
    private String state;

    /** ID of user that made it. */
    private int userId;

    /** ID of its schedule. */
    private String scheduleId;

    /** Priority of Reserve. */
    private int priority;

    /**
     * Constructs a Reserve object with specified details.
     *
     * @param pType   The type of the reserve.
     * @param pReason The reason for the reserve.
     * @param pUserId The ID of the user.
     * @throws LabReserveException If any parameter is invalid.
     */
    public Reserve(final String pType, final String pReason,
                   final int pUserId) throws LabReserveException {
        if (!Arrays.asList(typeReserve).contains(pType)) {
            throw new LabReserveException(
                LabReserveException.TYPE_RESERVE_NOT_FOUND);
        } else if (pReason.isEmpty()) {
            throw new LabReserveException(
                LabReserveException.REASON_RESERVE_NOT_FOUND);
        } else if (pUserId < 0) {
            throw new LabReserveException(
                LabReserveException.USER_RESERVE_NOT_FOUND);
        }
        this.type = pType;
        this.reason = pReason;
        this.userId = pUserId;
        this.state = "reserved";
    }

    /**
     * Set the type of the reserve.
     *
     * @param pType The new type.
     * @throws LabReserveException If the type is invalid.
     */
    public final void setType(final String pType) throws LabReserveException {
        if (Arrays.asList(typeReserve).contains(pType)) {
            this.type = pType;
        } else {
            throw new LabReserveException(
                LabReserveException.TYPE_RESERVE_NOT_FOUND);
        }
    }

    /**
     * Set the state of the reserve.
     *
     * @param pState The new state.
     * @throws LabReserveException If the state is invalid.
     */
    public final void setState(final String pState) throws LabReserveException {
        if (Arrays.asList(stateReserve).contains(pState)) {
            this.state = pState;
        } else {
            throw new LabReserveException(
                LabReserveException.STATE_RESERVE_NOT_FOUND);
        }
    }

    /**
     * Get the user who created the reserve.
     *
     * @return The user ID.
     */
    public final int getUser() {
        return userId;
    }

    /**
     * Set the user for the reserve.
     *
     * @param pUser The user ID.
     */
    public final void setUser(final int pUser) {
        this.userId = pUser;
    }

    /**
     * Get the schedule associated with this reserve.
     *
     * @return The schedule ID.
     */
    public final String getSchedule() {
        return scheduleId;
    }

    /**
     * Set the schedule of the reserve.
     *
     * @param pSchedule The new schedule ID.
     */
    public final void setSchedule(final String pSchedule) {
        this.scheduleId = pSchedule;
    }

    /**
     * Sets the priority of the reserve.
     *
     * @param pPriority The priority value (1-5).
     * @throws LabReserveException If out of range.
     */
    public final void setPriority(final int pPriority)
            throws LabReserveException {
        final int maxPriority = 6;
        if (pPriority > 0 && pPriority < maxPriority) {
            this.priority = pPriority;
        } else {
            throw new LabReserveException(
                LabReserveException.PRIORITY_NOT_IN_RANGE);
        }
    }
}
