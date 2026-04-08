package edu.eci.cvds.labReserves.model;

/**
 * Excepción personalizada para errores relacionados con la reserva.
 */
public class LabReserveException extends Exception {

    /** Error cuando el tipo de reserva no se encuentra. */
    public static final String TYPE_RESERVE_NOT_FOUND =
            "type reserve not found";

    /** Error cuando la razón de la reserva es nula. */
    public static final String REASON_RESERVE_NOT_FOUND =
            "reason reserve not found";

    /** Error cuando el usuario de la reserva es nulo. */
    public static final String USER_RESERVE_NOT_FOUND =
            "user reserve not found";

    /** Error cuando el estado de la reserva no es válido. */
    public static final String STATE_RESERVE_NOT_FOUND =
            "state reserve not found";

    /** Error cuando el tiempo del horario es inválido o ha expirado. */
    public static final String INVALID_SCHEDULE_TIME =
            "schedule time is invalid";

    /** Error cuando la reserva ya existe en el sistema. */
    public static final String RESERVE_ALREADY_EXIST =
            "this reserve already exist";

    /** Error cuando el rol del usuario no está admitido. */
    public static final String INVALID_ROL_TYPE = "this rol is not admited";

    /** Error cuando no se encuentra el usuario. */
    public static final String USER_NOT_FOUND = "user not found";

    /** Error cuando la prioridad está fuera del rango permitido (1 a 5). */
    public static final String PRIORITY_NOT_IN_RANGE =
            "Priority isn't in range (1 to 5)";

    /**
     * Constructor de la excepción con un mensaje específico.
     *
     * @param message El mensaje que describe el error.
     */
    public LabReserveException(final String message) {
        super(message);
    }
}
