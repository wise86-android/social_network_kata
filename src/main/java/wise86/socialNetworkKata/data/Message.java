package wise86.socialNetworkKata.data;


import java.util.Date;

/**
 * Class containing a message post in the social network
 */
public class Message {

    private final User author;
    private final String content;
    private final Date publicisingTime;

    /**
     * contains the message data
     * @param author how write the message
     * @param content the message content
     * @param now when the message was write
     */
    public Message(User author, String content, Date now) {
        this.author = author;
        this.content = content;
        this.publicisingTime = now;
    }

    public Date getPublicisingTime() {
        return publicisingTime;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }
}
