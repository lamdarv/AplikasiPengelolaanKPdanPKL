package com.jtk.ps.api.util;

public class Constant {
    private Constant(){
        throw new IllegalStateException("Utility class");
    }

    public static final class VerifyConstant {
        private VerifyConstant(){
            throw new IllegalStateException("Utility class");
        }

        public static final String STATUS = "status";
        public static final String ID_PRODI = "id_prodi";
        public static final String SUB = "sub";
        public static final String ID_ROLE = "id_role";
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static final class Role{
        private Role(){
            throw new IllegalStateException("Utility class");
        }

        public static final String COMMITTEE = "COMMITTEE";
        public static final String PARTICIPANT = "PARTICIPANT";
        public static final String COMPANY = "COMPANY";
        public static final String HEAD_STUDY_PROGRAM = "HEAD_STUDY_PROGRAM";
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
        public static final String STATUS = "status";
        public static final String DATA = "data";
        public static final String EXPIRED = "exp";
        public static final String ISSUED_AT = "iat";
        public static final String COOKIE = "cookie";
        public static final String ID_LECTURER = "id_lecturer";
        public static final String ID = "id";
    }

    public static final class CompetenceConstant {
        private CompetenceConstant(){
            throw new IllegalStateException("Utility class");
        }

        public static final Integer PROGRAMMING_LANGUAGE = 1;
        public static final Integer DATABASE = 2;
        public static final Integer FRAMEWORK = 3;
        public static final Integer TOOL = 4;
        public static final Integer MODELLING = 5;
        public static final Integer COMMUNICATION_LANGUAGE = 6;
    }

    public static final class CriteriaConstant {
        private CriteriaConstant(){
            throw new IllegalStateException("Utility class");
        }
        public static final Integer JOBSCOPE = 1;
        public static final Integer PROGRAMMING_LANGUAGE = 2;
        public static final Integer DATABASE = 3;
        public static final Integer FRAMEWORK = 4;
        public static final Integer TOOL = 5;
        public static final Integer MODELLING = 6;
        public static final Integer COMMUNICATION_LANGUAGE = 7;
        public static final Integer DOMICILE = 8;
        public static final Integer INTEREST_COMPANY = 9;
    }
}
