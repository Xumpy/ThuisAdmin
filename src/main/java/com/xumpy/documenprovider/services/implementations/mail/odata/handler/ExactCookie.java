package com.xumpy.documenprovider.services.implementations.mail.odata.handler;

import org.springframework.stereotype.Service;

@Service
public class ExactCookie {

    private String cookie;

    public String getCookie(){
        return cookie;
    }

    public void setCookie(String cookie){
        this.cookie = cookie;
    }

    /*
    private String exactState;

    private static final String SUCCESS = "success";
    private static final String INCORRECT_KEY = "incorrect key";

    public String getExactState() {
        return exactState;
    }

    private Map<String, List<String>> getHashMap(String uriAsString)  {
        try {
            return CookieManager.getDefault().get(new URI(uriAsString), new HashMap<>());
        } catch (Exception exception){
            throw new RuntimeException("Error fetching cookie");
        }
    }

    public void fetchCookie(String baseUrl, String userAgent, String username, String password, String key) throws PinNotValidException {
        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        webview = new WebView();
                        WebEngine webEngine = webview.getEngine();
                        webEngine.setUserAgent(userAgent);
                        webEngine.setJavaScriptEnabled(true);

                        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                            if (newState == Worker.State.SUCCEEDED) {
                                if (webEngine.getDocument().getDocumentURI().startsWith(baseUrl + "docs/Login.aspx")){
                                    Object htmlId = null;
                                    try{
                                        htmlId = webEngine.executeScript("$('html').attr('id')");
                                    } catch(Exception exception) {}

                                    if (htmlId != null && htmlId.toString().equals("LoginPage")){
                                        Object loginFormUsername = webEngine.executeScript("$('#LoginForm_UserName').val()");
                                        if (loginFormUsername != null && loginFormUsername.toString().equals(username)){
                                            webEngine.executeScript("$('#LoginForm_Password').val('" + password + "')");
                                            webEngine.executeScript("$('#LoginForm_btnSave')[0].click()");
                                        } else {
                                            webEngine.executeScript("$('#LoginForm_UserName').val('" + username + "')");
                                            webEngine.executeScript("$('#LoginForm_btnSave')[0].click()");
                                        }
                                    }
                                }  else if (webEngine.getDocument().getDocumentURI().startsWith(baseUrl + "docs/TotpLogin.aspx")){
                                    if (webEngine.executeScript("$('#LoginForm_Input_ErrorMessages').text()").toString().isEmpty()){
                                        webEngine.executeScript("$('#LoginForm_Input_Key').val('" + key + "')");
                                        webEngine.executeScript("$('#LoginForm_RememberDevice')[0].click()");
                                        webEngine.executeScript("$('#LoginForm_btnSave')[0].click()");
                                    } else {
                                        exactState = INCORRECT_KEY;
                                        latch.countDown();
                                    }
                                } else if (webEngine.getDocument().getDocumentURI().startsWith(baseUrl + "docs/MyFirmPortal.aspx")){
                                    exactState = SUCCESS;
                                    latch.countDown();
                                }
                            }
                        });

                        webEngine.load(baseUrl);
                    }
                });
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(exactState.equals(SUCCESS)){
            cookie = getHashMap(baseUrl + "docs/MyFirmPortal.aspx?_Division_=").get("Cookie").get(0);
            System.out.println("Cookie found: " + cookie);
        } else if(exactState.equals(INCORRECT_KEY)){
            throw new PinNotValidException("Incorrect key provided from authentication application");
        } else {
            throw new RuntimeException("Unknown Exception occured");
        }
    }
     */
}
