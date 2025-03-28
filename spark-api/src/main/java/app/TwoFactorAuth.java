package app;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TwoFactorAuth {
    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String VERIFY_SERVICE_SID;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            ACCOUNT_SID = props.getProperty("ACCOUNT_SID");
            AUTH_TOKEN = props.getProperty("AUTH_TOKEN");
            VERIFY_SERVICE_SID = props.getProperty("VERIFY_SERVICE_SID");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  



    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendVerification(String phoneNumber) {
        Verification verification = Verification.creator(
            VERIFY_SERVICE_SID,
            phoneNumber,
            "sms"
        ).create();

        System.out.println("Verification sent: " + verification.getSid());
    }

    public static boolean verifyCode(String phoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(VERIFY_SERVICE_SID)
            .setTo(phoneNumber)
            .setCode(code)
            .create();

        return "approved".equals(verificationCheck.getStatus());
    }
}
