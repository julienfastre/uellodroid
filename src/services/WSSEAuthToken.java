package services;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Base64;
import android.util.Log;


public class WSSEAuthToken {


    public static HttpURLConnection  addWSSEHeader(HttpURLConnection connection, String username, String password) throws WSSEException {
        try {
        	
        	String header = getWSSEHeader(username,password);
        	
        	Log.d("WSSEAuthToken", header);

            connection.addRequestProperty("X-WSSE",
                    header);
            
            return connection;

        } catch (WSSEException e) {
            throw e;
        }
    }

    public static String getWSSEHeader(String username,
                                       String password)  throws WSSEException {

        try {
            byte[] nonceB = generateNonce();
            String nonce = base64Encode(nonceB);

            String created = generateTimestamp();

            String password64 =
                    getBase64Digest(nonceB, created.getBytes("UTF-8"), password.getBytes("UTF-8"));
            StringBuffer header = new StringBuffer("UsernameToken Username=\"");
            header.append(username);
            header.append("\", ");
            header.append("PasswordDigest=\"");
            header.append(password64);
            header.append("\", ");
            header.append("Created=\"");
            header.append(created);
            header.append("\", ");
            header.append("Nonce=\"");
            header.append(nonce);
            header.append("\"");
            
            return header.toString();
        }
        catch (UnsupportedEncodingException e) {
            throw new WSSEException();
        }
    }

    private static byte[] generateNonce() {
        String nonce = Long.toString(new Date().getTime());
        return nonce.getBytes();
    }

    private static String generateTimestamp() {
        SimpleDateFormat dateFormatter =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateFormatter.format(new Date());
    }

    private static synchronized String getBase64Digest(byte[] nonce,
                                                       byte[] created,
                                                       byte[] password) {
        try {
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");
            // SHA-1 ( nonce + created + password )
            messageDigester.reset();
            messageDigester.update(nonce);
            messageDigester.update(created);
            messageDigester.update(password);
            return base64Encode(messageDigester.digest());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    private static String base64Encode(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
