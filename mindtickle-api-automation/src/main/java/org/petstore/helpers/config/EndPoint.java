package org.petstore.helpers.config;

public class EndPoint {
    public static String BASEURL = "https://petstore.swagger.io/v2";
    public static String PET = BASEURL+"/pet";
    public static String USER = BASEURL+"/user";
    public static String FINDBYSTATUS = PET+"/findByStatus";
    public static String CREATEWITHARRAY = USER+"/createWithArray";

}
