package com.jtk.ps.api.util;

public class Constant {
    private Constant(){
        throw new IllegalStateException("Utility class");
    }

    public static final class PayloadResponseConstant{
        private PayloadResponseConstant(){
            throw new IllegalStateException("Utility class");
        }

        public static final String USERNAME = "username";
        public static final String NAME = "name";
        public static final String ID_ROLE = "id_role";
        public static final String ID_PRODI = "id_prodi";
        public static final String ID_ACCOUNT = "id_account";
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String DATA = "data";
        public static final String EXPIRED = "exp";
        public static final String ISSUED_AT = "iat";
        public static final String COOKIE = "cookie";
    }

}
