package Business;

/**
 * Defines the different types of errors that can occur in the system.
 * These errors are used to identify validation, data and input problems.
 */
public enum ERROR_TYPE {
    INVALID_FIELDS,
    EMPTY_ID,
    INVALID_ID,
    DUPLICATE_ID,
    INVALID_FORMAT,
    EMPTY_NAME,
    INVALID_DATE,
    INVALID_WEIGHT,
    INVALID_WEIGHT_SUM,
    INVALID_ATHLETE,
    DUPLICATE_ATHLETE,
    NOT_ENOUGH_ATHLETES,
    FILE_ERROR,
    JSON_ERROR,
    NOT_A_NUMBER,
    OUT_OF_RANGE,
    NO_DELEGATIONS,
    INVALID_SIMULATION_DATA,
    ATHLETE_ALREADY_REGISTERED


}
