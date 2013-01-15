
package lc.client.webservice.wscode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gameThread" type="{http://webservice.service.server.lc/}gameThread" minOccurs="0"/>
 *         &lt;element name="gameType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="houseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playerCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "gameThread",
    "gameType",
    "houseId",
    "name",
    "playerCount"
})
public class GameHouse {

    protected String creator;
    protected GameThread gameThread;
    protected String gameType;
    protected String houseId;
    protected String name;
    protected String playerCount;

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreator(String value) {
        this.creator = value;
    }

    /**
     * Gets the value of the gameThread property.
     * 
     * @return
     *     possible object is
     *     {@link GameThread }
     *     
     */
    public GameThread getGameThread() {
        return gameThread;
    }

    /**
     * Sets the value of the gameThread property.
     * 
     * @param value
     *     allowed object is
     *     {@link GameThread }
     *     
     */
    public void setGameThread(GameThread value) {
        this.gameThread = value;
    }

    /**
     * Gets the value of the gameType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGameType(String value) {
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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerCount() {
        return playerCount;
    }

    /**
     * Sets the value of the playerCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerCount(String value) {
        this.playerCount = value;
    }

}
