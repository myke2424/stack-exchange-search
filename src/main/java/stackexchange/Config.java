import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * App configuration class
 */
public class Config {

    /**
     * Load YAML configuration file
     *
     * @param filename YAML config file located in src/main/resources/<config.yaml>
     * @return Map representation of the config
     */
    public static Map<String, Object> load(String filename) throws FileNotFoundException {
        InputStream inputStream =
                Config.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new FileNotFoundException("Config file doesn't exist... exiting");
        }
        Yaml yaml = new Yaml();

        Map<String, Object> config = yaml.load(inputStream);

        return config;
    }
}