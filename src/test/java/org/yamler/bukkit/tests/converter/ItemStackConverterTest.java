package org.yamler.bukkit.tests.converter;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yamler.bukkit.Converter.ItemStack;
import org.yamler.bukkit.tests.converter.config.ItemStackTestConfig;
import org.yamler.yamler.InvalidConfigurationException;
import org.yamler.bukkit.tests.base.BaseTest;

/**
 * A basic ItemStack converter test to ensure the proper saving and
 * loading of a ItemStack.
 *
 * @author bibo38
 * @see ItemStackTestConfig
 * @see ItemStack
 */
public class ItemStackConverterTest extends BaseTest {
    /**
     * The setup method, which creates the configuration file
     * and the Configuration used for the following tests.
     *
     * @throws Exception
     */
    @Override
    @BeforeSuite
    public void setup() throws Exception {
        config = new ItemStackTestConfig();
        filename = "itemstackConverterTest.yml";

        before();
    }

    /**
     * A basic test to ensure the proper loading and saving of the Config.
     *
     * @throws InvalidConfigurationException If an configuration error occurs
     */
    @Test
    public void testInitLoad() throws InvalidConfigurationException {
        config.init(file);
        config.load();
    }
}
