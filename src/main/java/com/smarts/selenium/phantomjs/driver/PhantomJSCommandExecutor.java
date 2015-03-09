package com.smarts.selenium.phantomjs.driver;

import java.io.IOException;
import java.net.ConnectException;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;

import com.google.common.base.Throwables;

public class PhantomJSCommandExecutor extends HttpCommandExecutor {
	 private final PhantomJSDriverService service;

	    /**
	     * Creates a new PhantomJSCommandExecutor.
	     * The PhantomJSCommandExecutor will communicate with the PhantomJS/GhostDriver through the given {@code service}.
	     *
	     * @param service The PhantomJSDriverService to send commands to.
	     */
	    public PhantomJSCommandExecutor(PhantomJSDriverService service) {
	        super(PhantomJSDriver.getCustomCommands(), service.getUrl());
	        this.service = service;
	    }

	    /**
	     * Sends the {@code command} to the PhantomJS/GhostDriver server for execution.
	     * The server will be started if requesting a new session.
	     * Likewise, if terminating a session, the server will be shutdown once a response is received.
	     *
	     * @param command The command to execute.
	     * @return The command response.
	     * @throws java.io.IOException If an I/O error occurs while sending the command.
	     */
	    @Override
	    public Response execute(Command command) throws IOException {
	        if (DriverCommand.NEW_SESSION.equals(command.getName())) {
	            service.start();
	        }

	        try {
	            return super.execute(command);
	        } catch (Throwable t) {
	            Throwable rootCause = Throwables.getRootCause(t);
	            if (rootCause instanceof ConnectException &&
	                    "Connection refused".equals(rootCause.getMessage()) &&
	                    !service.isRunning()) {
	                throw new WebDriverException("The PhantomJS/GhostDriver server has unexpectedly died!", t);
	            }
	            Throwables.propagateIfPossible(t);
	            throw new WebDriverException(t);
	        } finally {
	            if (DriverCommand.QUIT.equals(command.getName())) {
	                service.stop();
	            }
	        }
	    }
}
