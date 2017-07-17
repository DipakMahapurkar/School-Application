package com.schoolapp.network;

import static com.schoolapp.utils.Constant.BASE_URL;

public class APIUtils {
    public static APICallInterface getSOService() {
        return APIClient.getClient(BASE_URL).create(APICallInterface.class);
    }
}
