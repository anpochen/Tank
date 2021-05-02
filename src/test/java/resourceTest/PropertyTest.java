package resourceTest;

import com.anpo.config.PropertyManager;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyTest {

    static Properties properties = new Properties();

    @Test
    public void propertyTest(){
        try {
            ClassLoader classLoader = PropertyManager.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("config");
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
