package stackexchange.config;


import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * App configuration class
 */
public class Config {
    public Api api;
    public Redis redis;
    public Logging logging;


    /**
     * Load YAML configuration file
     *
     * @param filename YAML config file located in src/main/resources/<config.yaml>
     * @return Config obj
     */

    public static Config fromYaml(String filename) throws FileNotFoundException {
        // Note: snakeyaml requires nested objects to be public with public fields for
        // deserialization, as well as camelCase for key notation in the yaml file
        InputStream inputStream =
                Config.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new FileNotFoundException("Config file doesn't exist... exiting");
        }

        Yaml yaml = new Yaml(new Constructor(Config.class));
        Config config = yaml.load(inputStream);

        return config;
    }
}

