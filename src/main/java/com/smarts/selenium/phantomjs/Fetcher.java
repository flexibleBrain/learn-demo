package com.smarts.selenium.phantomjs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.smarts.selenium.phantomjs.driver.PhantomJSCommandExecutor;
import com.smarts.selenium.phantomjs.driver.PhantomJSDriver;
import com.smarts.utils.WriteFileUtils;
import com.thoughtworks.selenium.webdriven.commands.MouseEvent;


public class Fetcher {

	public void fetch(String url) throws InterruptedException{
		PhantomJSDriver driver = null;
		try {
			driver = (PhantomJSDriver)PhantomJsFacetory.getDriver();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try {
	    driver.manage().window().setSize(new Dimension(900,1400));
		driver.get(url);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		try{
			WebElement detail = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					try{
					return driver.findElement(By.id("J_Reviews"));
					}catch(Exception e){
						return null;
					}
				}
			});
			if(detail == null){
				System.out.println("元素没找到");
			}else{
				System.out.println(detail.getText());
			}
		}catch(TimeoutException e){
			System.out.println("连接打不开............");
		}
		
//		Point p = driver.manage().window().getPosition();
//		System.out.println("window point "+p.getX()+" : "+p.getY());
//		Dimension dim = driver.manage().window().getSize();
//		System.out.println(dim.getHeight()+" : "+dim.getWidth());
		WebElement name = driver.findElement(By.cssSelector("div.tb-detail-hd h1"));
//		System.out.println(name.getText());
//		Point point = name.getLocation();
//		System.out.println("name point "+point.getX()+" : "+point.getY());
//		for(int i=1;i<=10;i++){
//			setScroll(driver);
//			System.out.println("scroll to : "+(i+1));
//			TimeUnit.SECONDS.sleep(2);
//			 name = driver.findElement(By.cssSelector("div.tb-detail-hd h1"));
//			System.out.println(name.getText());
//			 point = name.getLocation();
//			System.out.println("name point "+point.getX()+" : "+point.getY());
////			WebElement comment = driver.findElement(By.className("rate-grid"));
////			if(comment!=null){
////				break;
////			}
//		}
		String page = driver.getPageSource();
			WriteFileUtils.write(page);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			driver.quit();
		}
	}
	 public  void  maxBrowser(WebDriver driver){ 
		   try { 
		    String maxBroswer = "if (window.screen) {window.moveTo(0, 0);" + 
		      "window.resizeTo(window.screen.availWidth,window.screen.availHeight);}"; 
		     
		    JavascriptExecutor jse=(JavascriptExecutor) driver; 
		    jse.executeScript(maxBroswer); 
		   } catch (Exception e) { 
		    System.out.println("Fail to  Maximization browser"); 
		   } 
	 } 
	public  void setScroll(WebDriver driver,int height){
		 try { 
			   String setscroll = "document.documentElement.scrollTop=" + height; 
			   JavascriptExecutor jse=(JavascriptExecutor) driver; 
			   jse.executeScript(setscroll); 
			  } catch (Exception e) { 
			   System.out.println("Fail to set the scroll."); 
			  }    
	}
	 
	 public static void setScroll(WebDriver driver){
			String scroll = "window.scrollBy(0,document.body.scrollHeight)";
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript(scroll);
		}
	
	public static void main(String[] args) {
		Fetcher fetcher = new Fetcher();
		try {
			String url = "http://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.RUSDq0&id=37856807809&skuId=4611686056284195713&areaId=220100&cat_id=51220040&rn=9e973f7957b31b50b6c5ae9efed05278&user_id=734520728&is_b=1";
//			url = UrlEncoded.encodeString(url, "GBK");
			fetcher.fetch(url);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
}
