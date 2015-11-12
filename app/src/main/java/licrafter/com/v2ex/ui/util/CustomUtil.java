package licrafter.com.v2ex.ui.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import licrafter.com.v2ex.model.Topic;

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

    public static List<Topic> collectionToList(Collection<Topic> collections){
        List<Topic> topics = new ArrayList<>();
        Iterator<Topic> iterator = collections.iterator();
        while (iterator.hasNext()){
            topics.add(iterator.next());
        }
        return topics;
    }
}
