
package lc.client.webservice.wscode;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import lc.client.environment.UserInfo;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lc.client.webservice.wscode package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Register_QNAME = new QName("http://webservice.service.server.lc/", "register");
    private final static QName _GetGameHousesResponse_QNAME = new QName("http://webservice.service.server.lc/", "getGameHousesResponse");
    private final static QName _JoinGameResponse_QNAME = new QName("http://webservice.service.server.lc/", "joinGameResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://webservice.service.server.lc/", "loginResponse");
    private final static QName _CreateGame_QNAME = new QName("http://webservice.service.server.lc/", "createGame");
    private final static QName _CreateGameResponse_QNAME = new QName("http://webservice.service.server.lc/", "createGameResponse");
    private final static QName _GetGameHouses_QNAME = new QName("http://webservice.service.server.lc/", "getGameHouses");
    private final static QName _Login_QNAME = new QName("http://webservice.service.server.lc/", "login");
    private final static QName _RegisterResponse_QNAME = new QName("http://webservice.service.server.lc/", "registerResponse");
    private final static QName _GetPlayer_QNAME = new QName("http://webservice.service.server.lc/", "getPlayer");
    private final static QName _GetPlayerResponse_QNAME = new QName("http://webservice.service.server.lc/", "getPlayerResponse");
    private final static QName _JoinGame_QNAME = new QName("http://webservice.service.server.lc/", "joinGame");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lc.client.webservice.wscode
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JoinGame }
     * 
     */
    public JoinGame createJoinGame() {
        return new JoinGame();
    }

    /**
     * Create an instance of {@link GetPlayerResponse }
     * 
     */
    public GetPlayerResponse createGetPlayerResponse() {
        return new GetPlayerResponse();
    }

    /**
     * Create an instance of {@link UserInfo }
     * 
     */
    public UserInfo createUserInfo() {
        return new UserInfo();
    }

    /**
     * Create an instance of {@link CreateGameResponse }
     * 
     */
    public CreateGameResponse createCreateGameResponse() {
        return new CreateGameResponse();
    }

    /**
     * Create an instance of {@link GetGameHouses }
     * 
     */
    public GetGameHouses createGetGameHouses() {
        return new GetGameHouses();
    }

    /**
     * Create an instance of {@link GameHouse }
     * 
     */
    public GameHouse createGameHouse() {
        return new GameHouse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link JoinGameResponse }
     * 
     */
    public JoinGameResponse createJoinGameResponse() {
        return new JoinGameResponse();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link CreateGame }
     * 
     */
    public CreateGame createCreateGame() {
        return new CreateGame();
    }

    /**
     * Create an instance of {@link GetPlayer }
     * 
     */
    public GetPlayer createGetPlayer() {
        return new GetPlayer();
    }

    /**
     * Create an instance of {@link MsgEntry }
     * 
     */
    public MsgEntry createMsgEntry() {
        return new MsgEntry();
    }

    /**
     * Create an instance of {@link GetGameHousesResponse }
     * 
     */
    public GetGameHousesResponse createGetGameHousesResponse() {
        return new GetGameHousesResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameHousesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "getGameHousesResponse")
    public JAXBElement<GetGameHousesResponse> createGetGameHousesResponse(GetGameHousesResponse value) {
        return new JAXBElement<GetGameHousesResponse>(_GetGameHousesResponse_QNAME, GetGameHousesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JoinGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "joinGameResponse")
    public JAXBElement<JoinGameResponse> createJoinGameResponse(JoinGameResponse value) {
        return new JAXBElement<JoinGameResponse>(_JoinGameResponse_QNAME, JoinGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "createGame")
    public JAXBElement<CreateGame> createCreateGame(CreateGame value) {
        return new JAXBElement<CreateGame>(_CreateGame_QNAME, CreateGame.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "createGameResponse")
    public JAXBElement<CreateGameResponse> createCreateGameResponse(CreateGameResponse value) {
        return new JAXBElement<CreateGameResponse>(_CreateGameResponse_QNAME, CreateGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameHouses }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "getGameHouses")
    public JAXBElement<GetGameHouses> createGetGameHouses(GetGameHouses value) {
        return new JAXBElement<GetGameHouses>(_GetGameHouses_QNAME, GetGameHouses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlayer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "getPlayer")
    public JAXBElement<GetPlayer> createGetPlayer(GetPlayer value) {
        return new JAXBElement<GetPlayer>(_GetPlayer_QNAME, GetPlayer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlayerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "getPlayerResponse")
    public JAXBElement<GetPlayerResponse> createGetPlayerResponse(GetPlayerResponse value) {
        return new JAXBElement<GetPlayerResponse>(_GetPlayerResponse_QNAME, GetPlayerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JoinGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.service.server.lc/", name = "joinGame")
    public JAXBElement<JoinGame> createJoinGame(JoinGame value) {
        return new JAXBElement<JoinGame>(_JoinGame_QNAME, JoinGame.class, null, value);
    }

}
