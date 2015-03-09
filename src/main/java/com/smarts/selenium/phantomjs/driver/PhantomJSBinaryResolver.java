package com.smarts.selenium.phantomjs.driver;

import java.io.File;
import java.io.IOException;

public interface PhantomJSBinaryResolver {
	/**
     * Resolves a phantomjs binary file to the given destination. If
     * the destination file exists, it tries to check whether the file is up to date
     * and if it isn't, it is overridden.
     *
     * @throws IOException if there is a problem with resolving
     */
    PhantomJSBinary resolve(String destination) throws IOException;

    /**
     * Resolves a phantomjs binary file to the given destination. If
     * the destination file exists, it tries to check whether the file is up to date
     * and if it isn't, it is overridden.
     *
     * @throws IOException if there is a problem with resolving
     */
    PhantomJSBinary resolve(File destination) throws IOException;

}
