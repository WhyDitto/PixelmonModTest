package your.domain.path.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;

import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
@ConfigPath("config/ModId/config.yml")
public class ExampleConfig extends AbstractYamlConfig {

    private String exampleField = "Hello World";
    private List<String> blacklist = new ArrayList<>();

    public ExampleConfig() {
        super();
    }

    public String getExampleField() {
        return this.exampleField;
    }
    public List<String> getBlacklist() {
        return this.blacklist;
    }
}
