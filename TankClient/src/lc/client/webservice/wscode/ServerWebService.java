
package lc.client.webservice.wscode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ServerWebService", targetNamespace = "http://webservice.server.lc/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServerWebService {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns lc.client.webservice.wscode.MsgEntry
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://webservice.server.lc/", className = "lc.client.webservice.wscode.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://webservice.server.lc/", className = "lc.client.webservice.wscode.LoginResponse")
    public MsgEntry login(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns lc.client.webservice.wscode.MsgEntry
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "register", targetNamespace = "http://webservice.server.lc/", className = "lc.client.webservice.wscode.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://webservice.server.lc/", className = "lc.client.webservice.wscode.RegisterResponse")
    public MsgEntry register(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}
