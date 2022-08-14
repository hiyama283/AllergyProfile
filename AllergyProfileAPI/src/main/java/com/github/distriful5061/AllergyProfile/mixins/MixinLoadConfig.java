package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Initializer;
import com.github.distriful5061.AllergyProfile.Utils.GsonUtils;
import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;

public class MixinLoadConfig {
    public static void main() {
        String config = ResourceUtils.getPlainTextResourcesByName("config.json");
        Initializer.config = GsonUtils.fromJson(config, ConfigJson.class);
    }
}
