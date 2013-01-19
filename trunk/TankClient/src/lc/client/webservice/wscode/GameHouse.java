
package lc.client.webservice.wscode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import lc.client.environment.UserInfo;


/**
 * <p>Java class for gameHouse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="gameHouse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creator" type="{http://webservice.service.server.lc/}userInfo" minOccurs="0"/>
 *         &lt;element name="gameType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="houseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playerCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gameHouse", propOrder = {
    "creator",
    "gameType",
    "houseId",
    "name",
    "playerCount"
})
public class GameHouse {

    protected UserInfo creator;
    protected int gameType;
    protected String houseId;
    protected String name;
    protected int playerCount;

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link UserInfo }
     *     
     */
    public UserInfo getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfo }
     *     
     */
    public void setCreator(UserInfo value) {
        this.creator = value;
    }

    /**
     * Gets the value of the gameType property.
     * 
     */
    public int getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     * 
     */
    public void setGameType(int value) {
        this.gameType = value;
    }

    /**
     * Gets the value of the houseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     * Sets the value of the houseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseId(String value) {
        this.houseId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the playerCount property.
     * 
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Sets the value of the playerCount property.
     * 
     */
    public void setPlayerCount(int value) {
        this.playerCount = value;
    }

}
