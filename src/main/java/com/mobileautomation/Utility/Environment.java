package com.mobileautomation.Utility;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "classpath:${testenv}.properties" })
public interface Environment extends Config {

    // Login credentials
    @Key("username")
    @DefaultValue("testuser")
    String username();

    @Key("password")
    @DefaultValue("testpassword")
    String password();
}

