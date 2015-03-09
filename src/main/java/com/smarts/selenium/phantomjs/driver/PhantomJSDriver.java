package com.smarts.selenium.phantomjs.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import static org.openqa.selenium.remote.http.HttpMethod.POST;

public class PhantomJSDriver extends RemoteWebDriver implements TakesScreenshot{
	/**
     * Creates a new PhantomJSDriver instance. The instance will have a
     * default set of desired capabilities.
     *
     * @see org.openqa.selenium.phantomjs.PhantomJSDriverService#createDefaultService() for configuration details.
     */
    public PhantomJSDriver() {
        this(DesiredCapabilities.phantomjs());
    }

    /**
     * Creates a new PhantomJSDriver instance.
     *
     * @param desiredCapabilities The capabilities required from PhantomJS/GhostDriver.
     * @see org.openqa.selenium.phantomjs.PhantomJSDriverService#createDefaultService() for configuration details.
     */
    public PhantomJSDriver(Capabilities desiredCapabilities) {
        this(PhantomJSDriverService.createDefaultService(desiredCapabilities), desiredCapabilities);
    }

    /**
     * Creates a new PhantomJSDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service             The service to use.
     * @param desiredCapabilities The capabilities required from PhantomJS/GhostDriver.
     */
    public PhantomJSDriver(PhantomJSDriverService service, Capabilities desiredCapabilities) {
        super(new PhantomJSCommandExecutor(service), desiredCapabilities);
    }

    /**
     * Take screenshot of the current window.
     *
     * @param target The target type/format of the Screenshot
     * @return Screenshot of current window, in the requested format
     * @see TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)
     */
    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        // Get the screenshot as base64 and convert it to the requested type (i.e. OutputType<T>)
        String base64 = (String) execute(DriverCommand.SCREENSHOT).getValue();
        return target.convertFromBase64Png(base64);
    }

    /**
     * Execute a PhantomJS fragment.  Provides extra functionality not found in WebDriver
     * but available in PhantomJS.
     * <p/>
     * See the <a href="http://phantomjs.org/api/">PhantomJS API<</a>
     * for details on what is available.
     * <p/>
     * A 'page' variable pointing to currently selected page is available for use.
     * If there is no page yet, one is created.
     * <p/>
     * When overriding any callbacks be sure to wrap in a try/catch block, as failures
     * may cause future WebDriver calls to fail.
     * <p/>
     * Certain callbacks are used by GhostDriver (the PhantomJS WebDriver implementation)
     * already.  Overriding these may cause the script to fail.  It's a good idea to check
     * for existing callbacks before overriding.
     *
     * @param script The fragment of PhantomJS JavaScript to execute.
     * @param args List of arguments to pass to the function that the script is wrapped in.
     *             These can accessed in the script as 'arguments[0]', 'arguments[1]',
     *             'arguments[2]', etc
     * @return The result of the evaluation.
     */
    public Object executePhantomJS(String script, Object... args) {
        script = script.replaceAll("\"", "\\\"");

        Iterable<Object> convertedArgs = Iterables.transform(
                Lists.newArrayList(args), new WebElementToJsonConverter());
        Map<String, ?> params = ImmutableMap.of(
                "script", script, "args", Lists.newArrayList(convertedArgs));

        return execute(COMMAND_EXECUTE_PHANTOM_SCRIPT, params).getValue();
    }

    private static final String COMMAND_EXECUTE_PHANTOM_SCRIPT = "executePhantomScript";

    protected static Map<String, CommandInfo> getCustomCommands() {
        Map<String, CommandInfo> customCommands = new HashMap<String, CommandInfo>();

        customCommands.put(COMMAND_EXECUTE_PHANTOM_SCRIPT,
                new CommandInfo("/session/:sessionId/phantom/execute", POST));

        return customCommands;
    }
}
