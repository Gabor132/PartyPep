package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "IT")
public class GetAllUsersRequestTestIT extends AbstractControllerRequestTest {

    public static Logger logger = Logger.getLogger(GetAllUsersRequestTestIT.class.getName());

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.defaultTestStatusCode();
    }

    @Test
    @Override
    public void testMessageType(){
        this.defaultTestMessageType();
    }

    @Test
    @Override
    public void testPayload(){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            Object object = JSONRetriver.retrieveClass(ArrayList.class, httpResponse);
            if(object instanceof java.util.List) {
                List<UserDTO> users = (List<UserDTO>) object;
                Assert.assertTrue(users.size() > 0);
            }else{
                Assert.fail("Failed to parse response to " + List.class.getName());
            }
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.toString());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.toString());
        }
    }

    @Override
    public String getUrl(){
        return mainURL + "users/all";
    }
}
