package com.moon.website.springboot.web;

import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile_isChecked() {
        //given
        String expectedProfile = "real";
        MockEnvironment environment = new MockEnvironment();
        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("oauth");
        environment.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(environment);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile_isEmpty_return_first() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment mockEnvironment = new MockEnvironment();

        mockEnvironment.addActiveProfile(expectedProfile);
        mockEnvironment.addActiveProfile("real-db");

        ProfileController profileController = new ProfileController(mockEnvironment);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile_isEmpty_return_default() {
        //given
        String expectedProfile = "default";
        MockEnvironment mockEnvironment = new MockEnvironment();
        ProfileController profileController = new ProfileController(mockEnvironment);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
