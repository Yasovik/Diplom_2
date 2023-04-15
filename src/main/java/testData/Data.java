package testData;

public class Data {
    protected static String deleteUserApi = "/api/auth/user";
    protected static String loginUserApi = "/api/auth/login";
    protected String email = "teqrgu35yy1cdytieptq11@ya.ru";
    protected String password = "12345";
    protected String name = "Sasha";
    protected String apiCreateUser = "/api/auth/register";
    protected String headersRequestContentType = "Content-type";
    protected String headersRequestApplication = "application/json";
    protected String userCreateData = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\"}";
    protected String createOrdersApi = "/api/orders";

}
