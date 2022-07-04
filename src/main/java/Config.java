import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class Config {

    // TODO: Catch exception if file isn't found and log msg
    public static Map<String, Object> load(String filename) {
        InputStream inputStream =
                Config.class.getClassLoader().getResourceAsStream(filename);

        Yaml yaml = new Yaml();

        Map<String, Object> data = yaml.load(inputStream);
        System.out.println(data);

        return data;
    }
}