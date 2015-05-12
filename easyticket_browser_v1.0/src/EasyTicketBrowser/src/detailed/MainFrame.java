// Copyright (c) 2013 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package EasyTicketBrowser.src.detailed;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.cef.CefApp;
import org.cef.CefApp.CefVersion;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefRequestContext;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandler;
import org.cef.handler.CefLoadHandler.ErrorCode;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.handler.CefRequestContextHandler;
import org.cef.network.CefCookieManager;
import org.omg.CORBA.Environment;
import EasyTicketBrowser.src.detailed.handler.AppHandler;
import EasyTicketBrowser.src.detailed.handler.ContextMenuHandler;
import EasyTicketBrowser.src.detailed.handler.DragHandler;
import EasyTicketBrowser.src.detailed.handler.GeolocationHandler;
import EasyTicketBrowser.src.detailed.handler.JSDialogHandler;
import EasyTicketBrowser.src.detailed.handler.KeyboardHandler;
import EasyTicketBrowser.src.detailed.handler.MessageRouterHandler;
import EasyTicketBrowser.src.detailed.handler.MessageRouterHandlerEx;
import EasyTicketBrowser.src.detailed.handler.RequestHandler;
import EasyTicketBrowser.src.detailed.ui.ControlPanel;
import EasyTicketBrowser.src.detailed.ui.MenuBar;
import EasyTicketBrowser.src.detailed.ui.StatusPanel;
import EasyTicketBrowser.src.dialog.DownloadDialog;

public class MainFrame extends JFrame {
	
  private static final long serialVersionUID = -2295538706810864538L;
  
  //private static String parentDirectory = "C:\\Users\\nikos7\\Desktop\\files";
  //private static String parentDirectory = "D:\\";
  //private static String parentDirectory = (System.getProperty("user.home") + "/Desktop").replace("\\", "/");
  private static String parentDirectory = (System.getProperty("user.dir") + "").replace("\\", "/");
  private static String deleteExtension = ".log";
  
  public static void main(String [] args) {
    // OSR mode is enabled by default on Linux.
    // and disabled by default on Windows and Mac OS X.
	  
	
    boolean osrEnabledArg = OS.isLinux();
    
    String cookiePath = null;
    for (String arg : args) {
      arg = arg.toLowerCase();
      if (!OS.isLinux() && arg.equals("--off-screen-rendering-enabled")) {
        osrEnabledArg = true;
      } else if(arg.startsWith("--cookie-path=")) {
        cookiePath = arg.substring("--cookie-path=".length());
        File testPath = new File(cookiePath);
        if (!testPath.isDirectory() || !testPath.canWrite()) {
          System.out.println("Can't use " + cookiePath + " as cookie directory. Check if it exists and if it is writable");
          cookiePath = null;
        } else {
          System.out.println("Storing cookies in " + cookiePath);
        }
      }
    }

    System.out.println("Offscreen rendering " + (osrEnabledArg ? "enabled" : "disabled"));
    
    System.out.println("Parent Directory (Main Method) " + parentDirectory);    

    // MainFrame keeps all the knowledge to display the embedded browser
    // frame.
    final MainFrame frame = new MainFrame(osrEnabledArg, cookiePath, args); 
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        CefApp.getInstance().dispose();
        frame.dispose();
      }
    });
    
   // Window.
    
    Image icon = Toolkit.getDefaultToolkit().getImage("ic_launcher.gif");
    frame.setIconImage(icon);
    frame.setSize(800, 600);
    frame.setVisible(true);
  }

  private final CefClient client_;
  private String errorMsg_ = "";
  private final CefBrowser browser_;
  public static ControlPanel control_pane_;
  private StatusPanel status_panel_;
  private final CefCookieManager cookieManager_;
  private ErrorCode ErrCode;
  private String ErrTxt;
  private String FailedURL;
  
  public MainFrame(boolean osrEnabled, String cookiePath, String [] args) {
	  
    // 1) CefApp is the entry point for JCEF. You can pass
    //    application arguments to it, if you want to handle any
    //    chromium or CEF related switches/attributes in
    //    the native world.
    CefSettings settings = new CefSettings();
    settings.windowless_rendering_enabled = osrEnabled;
    // try to load URL "about:blank" to see the background color
    settings.background_color = settings.new ColorType(100, 255, 242, 211);
    CefApp myApp = CefApp.getInstance(args, settings);
    CefVersion version = myApp.getVersion();
    System.out.println("Using:\n" + version);
    	

    //    We're registering our own AppHandler because we want to
    //    add an own schemes (search:// and client://) and its corresponding
    //    protocol handlers. So if you enter "search:something on the web", your
    //    search request "something on the web" is forwarded to www.google.com
    CefApp.addAppHandler(new AppHandler(args));
    
   
    //    By calling the method createClient() the native part
    //    of JCEF/CEF will be initialized and an  instance of
    //    CefClient will be created. You can create one to many
    //    instances of CefClient.
    client_ = myApp.createClient();
    

    // 2) You have the ability to pass different handlers to your
    //    instance of CefClient. Each handler is responsible to
    //    deal with different informations (e.g. keyboard input).
    //
    //    For each handler (with more than one method) adapter
    //    classes exists. So you don't need to override methods
    //    you're not interested in.
    DownloadDialog downloadDialog = new DownloadDialog(this);
    client_.addContextMenuHandler(new ContextMenuHandler(this));
    client_.addDownloadHandler(downloadDialog);
    client_.addDragHandler(new DragHandler());
    client_.addGeolocationHandler(new GeolocationHandler(this));
    client_.addJSDialogHandler(new JSDialogHandler());
    client_.addKeyboardHandler(new KeyboardHandler());
    client_.addRequestHandler(new RequestHandler(this));
    

    //    Beside the normal handler instances, we're registering a MessageRouter
    //    as well. That gives us the opportunity to reply to JavaScript method
    //    calls (JavaScript binding). We're using the default configuration, so
    //    that the JavaScript binding methods "cefQuery" and "cefQueryCancel"
    //    are used.
    CefMessageRouter msgRouter = CefMessageRouter.create();
    msgRouter.addHandler(new MessageRouterHandler(), true);
    msgRouter.addHandler(new MessageRouterHandlerEx(client_), false);
    client_.addMessageRouter(msgRouter);

    // 2.1) We're overriding CefDisplayHandler as nested anonymous class
    //      to update our address-field, the title of the panel as well
    //      as for updating the status-bar on the bottom of the browser
    client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
      @Override
      public void onAddressChange(CefBrowser browser, String url) {
        control_pane_.setAddress(browser, url);
      }
      @Override
      public void onTitleChange(CefBrowser browser, String title) {
        setTitle(title);
      }
      @Override
      public void onStatusMessage(CefBrowser browser, String value) {
        status_panel_.setStatusText(value);
      }
    });
    
    // 2.2) To disable/enable navigation buttons and to display a prgress bar
    //      which indicates the load state of our website, we're overloading
    //      the CefLoadHandler as nested anonymous class. Beside this, the 
    //      load handler is responsible to deal with (load) errors as well.
    //      For example if you navigate to a URL which does not exist, the 
    //      browser will show up an error message.        
    
    
   
    
    client_.addLoadHandler(new CefLoadHandlerAdapter() {
 
    	
	@Override
      public void onLoadingStateChange(CefBrowser browser,
                                       boolean isLoading,
                                       boolean canGoBack,
                                       boolean canGoForward) {
		
        control_pane_.update(browser, isLoading, canGoBack, canGoForward);
        status_panel_.setIsInProgress(isLoading);

        if (!isLoading && !errorMsg_.equals("")) {
         	browser.loadString(errorMsg_, control_pane_.getAddress());
         	errorMsg_ = "";
        }
               
        if (isLoading) {
        	System.out.println("On Loading ....!");
            //Delete All .txt files on user's Desktop
            deleteAllTxtFiles();
		}
      }		  
      
    @Override
    public void onLoadError(CefBrowser browser,
                            int frameIdentifer,
                            ErrorCode errorCode,
                            String errorText,
                            String failedUrl) {
    	
    	 System.out.println("On Load Error Result: "+browser.toString()+", FrameID: "+frameIdentifer
   			  +", ErrorCode: "+errorCode+", FailedURL: "+failedUrl);
    	     	 
		try {
    	   if (checkInternet(browser)) {
    	    	if (errorCode == ErrorCode.ERR_INTERNET_DISCONNECTED) {
    	    		 errorMsg_ = "<html><head>";
    		         errorMsg_ += "<title>Error while loading</title>";
    		         errorMsg_ += "</head><body><center>";
    		         errorMsg_ += "<h1>" + (ErrCode == null ? "" : ErrCode) + "</h1>";
    		         errorMsg_ += "<p><br/><br/><br/></p>";
    		         errorMsg_ += "<h3>Fail to load ... :[ </h3>";
    		         errorMsg_ += "<p>Pls check internet connection.(Connected)</p>";
    		         errorMsg_ += "</center></body></html>";
    		         browser.stopLoad();
    		     }
		}else {
			 errorMsg_ = "<html><head>";
	         errorMsg_ += "<title>Error while loading</title>";
	         errorMsg_ += "</head><body><center>";
	         errorMsg_ += "<h1>" + (ErrCode == null ? "" : ErrCode) + "</h1>";
	         errorMsg_ += "<p><br/><br/><br/></p>";
	         errorMsg_ += "<h3>Fail to load ... :[ </h3>";
	         errorMsg_ += "<p>Pls check internet connection</p>";
	         errorMsg_ += "</center></body></html>";
	         browser.stopLoad();
		}
    	   
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }         
    });   
    
        
    // 3) Before we can display any content, we require an instance of
    //    CefBrowser itself by calling createBrowser() on the CefClient.
    //    You can create one to many browser instances per CefClient.
    //
    //    If the user has specified the application parameter "--cookie-path="
    //    we provide our own cookie manager which persists cookies in a directory.
    CefRequestContext requestContext = null;
    if (cookiePath != null) {
      cookieManager_ = CefCookieManager.createManager(cookiePath, false);
      requestContext = CefRequestContext.createContext(new CefRequestContextHandler() {
        @Override
        public CefCookieManager getCookieManager() {
          return cookieManager_;
        }
      });
    } else {
      cookieManager_ = CefCookieManager.getGlobalManager();
    }
    
    //app.easyticket.com.mm/ezadmin
    //app.easyticket.com.mm
    browser_ = client_.createBrowser("app.easyticket.com.mm/ezadmin",
                                     osrEnabled,
                                     false,
                                     requestContext);       
    
    //Last but not least we're setting up the UI for this example implementation.
    getContentPane().add(createContentPanel(), BorderLayout.CENTER);
    MenuBar menuBar = new MenuBar(this,
                                  browser_,
                                  control_pane_,
                                  downloadDialog,
                                  cookieManager_);

    menuBar.addBookmark("Binding Test", "client://tests/binding_test.html");
    menuBar.addBookmark("Binding Test 2", "client://tests/binding_test2.html");
    menuBar.addBookmark("Download Test", "http://cefbuilds.com");
    menuBar.addBookmark("Geolocation Test","http://slides.html5rocks.com/#geolocation");
    menuBar.addBookmark("Login Test (username:pumpkin, password:pie)","http://www.colostate.edu/~ric/protect/your.html");
    menuBar.addBookmark("Certificate-error Test","https://www.k2go.de");
    menuBar.addBookmark("Resource-Handler Test", "http://www.foo.bar/");
    menuBar.addBookmark("Scheme-Handler Test 1: (scheme \"client\")", "client://tests/handler.html");
    menuBar.addBookmark("Scheme-Handler Test 2: (scheme \"search\")", "search://do a barrel roll/");
    menuBar.addBookmark("Spellcheck test", "client://tests/spellcheck.html");
    menuBar.addBookmark("Test local Storage", "client://tests/localstorage.html");
    menuBar.addBookmarkSeparator();
    menuBar.addBookmark("javachromiumembedded", "https://code.google.com/p/javachromiumembedded/");
    menuBar.addBookmark("chromiumembedded", "https://code.google.com/p/chromiumembedded/");
    setJMenuBar(menuBar);
  }//End Mainframe
  
  /**
   *  Show Error Page
   */
  private void showErrorPage(CefBrowser browser) {
	// TODO Auto-generated method stub
	  System.out.println("Show Error Page!!!!!!");	  
	  
	  errorMsg_ = "<html><head>";
      errorMsg_ += "<title>Error while loading</title>";
      errorMsg_ += "</head><body><center>";
      errorMsg_ += "<h1>" + (ErrCode == null ? "" : ErrCode) + "</h1>";
      errorMsg_ += "<p><br/><br/><br/></p>";
      errorMsg_ += "<h3>Fail to load ... :[ " + FailedURL + "</h3>";
      errorMsg_ += "<p>Pls check internet connection. (or) check website address. </p>";
      errorMsg_ += "</center></body></html>";

}
  
  /**
   *  Delete .txt files
   */
  private void deleteTxtFile() {
	// TODO Auto-generated method stub
	 // Path path = FileSystems.getDefault().getPath(control_pane_.getAddress(), "*.txt");
	  
	Path path = FileSystems.getDefault().getPath("D:/", "*.txt");
	
      //delete if exists
      try {
          boolean success = Files.deleteIfExists(path);
          System.out.println("Delete status: " + success);
      } catch (IOException e) {
          System.err.println("IOException: "+e);
      } catch (SecurityException e) {
    	  System.err.println("SecurityException: "+e);
      }
  }
  
  /**
   *  Delete All .txt files
   */
  public void deleteAllTxtFiles() {
	// TODO Auto-generated method stub
	    //to delete .txt all files
	    FileFilter fileFilter = new FileFilter(deleteExtension);
		File parentDir = new File(parentDirectory);
		
		System.out.println("User Define Dir: "+parentDirectory);
		
		// Put the names of all files ending with .txt in a String array
		String[] listOfTextFiles = parentDir.list(fileFilter);
		
		if (listOfTextFiles != null) {
			if (listOfTextFiles.length == 0) {
				System.out.println("There are no text files in this direcotry!");
				return;
			}
		}else {
			System.out.println("List of txt files are Null!");
			return;
		}

		File fileToDelete;

		if (listOfTextFiles.length != 0) {
			System.out.println("log files count : "+listOfTextFiles.length);
			for (String file : listOfTextFiles) {

				//construct the absolute file paths...
				String absoluteFilePath = new StringBuffer(parentDirectory).append(File.separator).append(file).toString();

				//open the files using the absolute file path, and then delete them...
				fileToDelete = new File(absoluteFilePath);
				boolean isdeleted = fileToDelete.delete();
				System.out.println("File : " + absoluteFilePath + " was deleted : " + isdeleted);
			}
		}
  }
  
  /**
   *  Check Connection Time out 
   * @param browser web link address
   */
  @SuppressWarnings("resource")
private boolean checkTimeOut() {
	// TODO Auto-generated method stub
	    boolean timeOut = false;	   
		
		InetAddress addr = null;
		 
		try {
			addr = InetAddress.getByName("www.google.com");
			System.out.println(addr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			System.out.println("This webpage is not available ==> "+addr);
			e.printStackTrace();
		}
		  int port = 80; 
		  SocketAddress sockaddr = new InetSocketAddress(addr, port);
		  Socket sock = new Socket(); 
		  int timeoutMs = 10; 
		  try {
			sock.connect(sockaddr, timeoutMs);
			System.out.println("No Connection Time Out :)");				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			timeOut = true;
			System.out.println("Connection Time Out! ;(");
			e.printStackTrace();
		}
		
		return timeOut;
}
  
  public boolean checkInternet(CefBrowser browser) throws UnknownHostException, IOException 
	  { 
	  	boolean isConnected = false;
	  	
	  	Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }  
        while (interfaces.hasMoreElements()) {  
            NetworkInterface nic = interfaces.nextElement();  
 
            System.out.print("Interface Name : [" + nic.getDisplayName() + "]");  
            try {
            	isConnected = nic.isUp();
                System.out.println(", Is connected : [" + nic.isUp() + "]");
            } catch (SocketException e) {
                e.printStackTrace();
            }  
        }  
        
	  	try 
	  		{ 
	  		try 
	  			{ 
	  				URL url = new URL(browser.getURL()); 
	  				System.out.println(url.getHost()); 
	  				HttpURLConnection con = (HttpURLConnection)url.openConnection(); 	  				
	  				con.connect(); 
	  				if (con.getResponseCode() == 200)
	  					{ 
	  						System.out.println("Connection established!!");
	  						isConnected = true;
	  					}
	  			}catch (Exception exception){
	  				System.out.println("No Connection");
	  			}
	  		} catch (Exception e){ 
	  			e.printStackTrace(); 
	  		} 
	  	
	  	return isConnected;
	  } 
  
  private JPanel createContentPanel() {
    JPanel contentPanel = new JPanel(new BorderLayout());
    control_pane_ = new ControlPanel(browser_);
    status_panel_ = new StatusPanel();
    contentPanel.add(control_pane_, BorderLayout.NORTH);

    // 4) By calling getUIComponen() on the CefBrowser instance, we receive
    //    an displayable UI component which we can add to our application.
    contentPanel.add(browser_.getUIComponent(), BorderLayout.CENTER);

    contentPanel.add(status_panel_, BorderLayout.SOUTH);
    return contentPanel;
  }
}
