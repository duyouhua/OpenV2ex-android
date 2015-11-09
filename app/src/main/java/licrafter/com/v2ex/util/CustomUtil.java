package licrafter.com.v2ex.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shell on 15-11-7.
 */
public class CustomUtil {

    public static String streamFormToString(InputStream inputStream){
        try {
            int count = inputStream.available();
            byte[] bytes = new byte[count];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
