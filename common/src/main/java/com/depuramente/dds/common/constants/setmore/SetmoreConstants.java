package com.depuramente.dds.common.constants.setmore;

public class SetmoreConstants {
    private SetmoreConstants() {
    }

    public static final String BEARER = "Bearer ";
    public static final String CURSOR = "cursor";
    public static final String SETMORE_TOKEN_PATH = "/api/v1/o/oauth2/token";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String LABEL_PARAM = "%s";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String CUSTOMER_DETAILS = "customerDetails";
    public static final String STAFF_KEY = "staff_key";
    public static final String APPOINTMENTS = "/appointments";
    public static final String APPOINTMENT_KEY = "/{appointmentKey}";
    public static final String LABEL = "/label";

    public static final String SETMORE_API_PATH = "/api/v1/bookingapi";
    public static final String SETMORE_STAFF_PATH = SETMORE_API_PATH + "/staffs";

    // CUSTOMER
    public static final String SETMORE_CUSTOMER = SETMORE_API_PATH + "/customer";
    public static final String SETMORE_CREATE_CUSTOMER = SETMORE_CUSTOMER + "/create";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstname";
    public static final String PHONE = "phone";

    // APPOINTMENT
    public static final String SETMORE_APPOINTMENT = SETMORE_API_PATH + "/appointment";
    public static final String SETMORE_CREATE_APPOINTMENT = SETMORE_APPOINTMENT + "/create";
    public static final String SETMORE_UPDATE_APPOINTMENT_LABEL = SETMORE_API_PATH + APPOINTMENTS + APPOINTMENT_KEY + LABEL;

    // SERVICES
    public static final String SETMORE_SERVICES = SETMORE_API_PATH + "/services";
    public static final String SETMORE_SERVICES_CATEGORIES = SETMORE_API_PATH + "/services/categories";
    public static final String CATEGORY_KEY = "categoryKey";
    public static final String SETMORE_SERVICES_CATEGORY_KEY = SETMORE_SERVICES_CATEGORIES + "/{" + CATEGORY_KEY + "}";

    // SLOTS
    public static final String SETMORE_SLOTS = SETMORE_API_PATH + "/slots";


//    "/api/v1/bookingapi/customer/create"

}
