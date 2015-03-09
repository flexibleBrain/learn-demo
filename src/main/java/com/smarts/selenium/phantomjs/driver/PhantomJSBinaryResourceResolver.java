package com.smarts.selenium.phantomjs.driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.ZipFile;

public class PhantomJSBinaryResourceResolver implements PhantomJSBinaryResolver {

	 public static final String CHECKSUM_EXTENSION = "sha1";
	    public static final String PHANTOMJS = "phantomjs" + (isWindows() ? ".exe" : "");
	    public static final String PHANTOMJS_CHECKSUM = PHANTOMJS + "." + CHECKSUM_EXTENSION;

	    @Override
	    public PhantomJSBinary resolve(String destination) throws IOException {
	        return resolve(new File(destination));
	    }

	    @Override
	    public PhantomJSBinary resolve(File destination) throws IOException {
	        File realDestination = destination.isDirectory() ? new File(destination, PHANTOMJS) : destination;
	        if (alreadyExists(realDestination)) {
	            return new PhantomJSBinary(realDestination, new File(realDestination.getPath() + "." + CHECKSUM_EXTENSION));
	        }
	        return resolveFreshExtracted(realDestination);
	    }

	    protected boolean alreadyExists(File destination) throws IOException {
	        File extractedChecksumFile = new File(destination.getAbsolutePath() + "." + CHECKSUM_EXTENSION);
	        if (!extractedChecksumFile.exists()) {
	            return false;
	        }
	        URL inArchiveChecksumURL = PhantomJSBinaryResourceResolver.class.getClassLoader().getResource(PHANTOMJS_CHECKSUM);
	        BufferedReader extractedReader = null;
	        BufferedReader inArchiveReader = null;
	        try {
	            extractedReader = new BufferedReader(new InputStreamReader(new FileInputStream(extractedChecksumFile)));
	            inArchiveReader = new BufferedReader(new InputStreamReader(inArchiveChecksumURL.openStream()));
	            String extracted = extractedReader.readLine();
	            String inArchive = inArchiveReader.readLine();
	            return extracted != null && extracted.equals(inArchive);
	        } finally {
	            FileUtils.close(extractedReader);
	            FileUtils.close(inArchiveReader);
	        }
	    }

	    protected File getJavaArchive(URL resource) {
	        return new File(resource.getPath().split("!")[0].replace("file:", ""));
	    }

	    protected PhantomJSBinary resolveFromClassPath() throws IOException {
	        return new PhantomJSBinary(
	                PhantomJSBinaryResourceResolver.class.getClassLoader().getResource(PHANTOMJS).getFile(),
	                PhantomJSBinaryResourceResolver.class.getClassLoader().getResource(PHANTOMJS).getFile() + "." + CHECKSUM_EXTENSION);
	    }

	    protected PhantomJSBinary resolveFreshExtracted(File destination) throws IOException {
	        File checksum = new File(destination.getAbsolutePath() + "." + CHECKSUM_EXTENSION);
	        if (!destination.exists()) {
	            destination.delete();
	        }
	        if (checksum.exists()) {
	            checksum.delete();
	        }
	        ZipFile jar = new ZipFile(getJavaArchive(PhantomJSBinaryResourceResolver.class.getClassLoader().getResource(PHANTOMJS)));
	        FileUtils.extract(jar, PHANTOMJS, destination);
	        FileUtils.extract(jar, PHANTOMJS_CHECKSUM, checksum);
	        return new PhantomJSBinary(destination, checksum);
	    }

	    protected static boolean isWindows() {
	        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	    }
}
