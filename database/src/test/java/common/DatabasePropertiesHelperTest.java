package common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** 
* DatabasePropertiesHelper Tester. 
* 
* @author Dragos-Alexandru Gabor
* @since <pre>Jun 30, 2019</pre> 
* @version 1.0 
*/ 
public class DatabasePropertiesHelperTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: filterProperties(String profile, Properties properties) 
* 
*/ 
@Test
public void testFilterProperties(){
//TODO: Test goes here... 
} 

/** 
* 
* Method: getJDBCProperties() 
* 
*/ 
@Test
public void testGetJDBCProperties(){
    Properties prop = DatabasePropertiesHelper.getJDBCProperties();
    Assert.assertNotNull(prop);
    Assert.assertTrue(prop.keySet().toArray().length > 0);
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, prop.toString());
}

@Test
public void testGetJDBCPropertiesNoEmptyConfigurations(){
    Properties prop = DatabasePropertiesHelper.getJDBCProperties();
    for(String key : prop.keySet().stream().map(x -> (String) x).collect(Collectors.toCollection(ArrayList::new))){
        String value = prop.getProperty(key);
        Assert.assertNotSame("", value);
    }
}

/** 
* 
* Method: getJDBCProperties(boolean filtered, String profile) 
* 
*/ 
@Test
public void testGetJDBCPropertiesForFilteredProfileDEV() {
    Properties prop = DatabasePropertiesHelper.getJDBCProperties(true, "DEV");
    Assert.assertTrue(prop.keySet().toArray().length > 0);
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, prop.toString());
}

/**
 *
 * Method: getJDBCProperties(boolean filtered, String profile)
 *
 */
@Test
public void testGetJDBCPropertiesForFilteredProfileIT() {
    Properties prop = DatabasePropertiesHelper.getJDBCProperties(true, "IT");
    Assert.assertTrue(prop.keySet().toArray().length > 0);
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, prop.toString());
}

/**
 *
 * Method: getJDBCProperties(boolean filtered, String profile)
 *
 */
@Test
public void testGetJDBCPropertiesForFilteredProfilePROD() {
    Properties prop = DatabasePropertiesHelper.getJDBCProperties(true, "PROD");
    Assert.assertTrue(prop.keySet().toArray().length > 0);
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, prop.toString());
}


    /**
     *
     * Method: getJDBCProperties(boolean filtered, String profile)
     *
     */
    @Test
    public void testGetJDBCPropertiesForFilteredProfileNonExistent() {
        Properties prop = DatabasePropertiesHelper.getJDBCProperties(true, "WHATEVER");
        Assert.assertTrue(prop.keySet().toArray().length == 0);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, prop.toString());
    }


} 
