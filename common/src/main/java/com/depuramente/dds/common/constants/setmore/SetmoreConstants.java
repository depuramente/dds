package com.depuramente.dds.common.constants.setmore;

public class SetmoreConstants {
    private SetmoreConstants() {
    }

    public static final String BEARER = "Bearer ";
    public static final String CURSOR = "cursor";
    public static final String SETMORE_TOKEN_PATH = "/api/v1/o/oauth2/token";
    public static final String REFRESH_TOKEN = "refreshToken";

    public static final String SETMORE_API_PATH = "/api/v1/bookingapi";
    public static final String SETMORE_STAFF_PATH = SETMORE_API_PATH + "/staffs";

    // CUSTOMER
    public static final String SETMORE_CUSTOMER = SETMORE_API_PATH + "/customer";
    public static final String SETMORE_CREATE_CUSTOMER = SETMORE_CUSTOMER + "/create";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME ="firstname";
    public static final String PHONE = "phone";

    // APPOINTMENT
    public static final String SETMORE_APPOINTMENT = SETMORE_API_PATH + "/appointment";
    public static final String SETMORE_CREATE_APPOINTMENT = SETMORE_APPOINTMENT + "/create";
    public static final String SETMORE_UPDATE_APPOINTMENT_LABEL = SETMORE_APPOINTMENT + "/create";



//    "/api/v1/bookingapi/customer/create"

}
