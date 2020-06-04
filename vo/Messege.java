package vo;

import java.io.Serializable;

public class Messege implements Serializable{
    private String type;//消息的类型
    private Object content;//消息的内容
    private String to;//消息的接收方
    private String from;//消息的发送方
    public void setType(String type)
    {
        this.type=type;
    }
    public void setContent(Object content)
    {
        this.content=content;
    }
    public void setTo(String to)
    {
        this.to=to;
    }
    public void setFrom(String from)
    {
        this.from=from;
    }
    public String getType()
    {
        return (this.type);
    }
    public Object getContent()
    {
        return (this.content);
    }
    public String getTo()
    {
        return (this.to);
    }
    public String getFrom()
    {
        return (this.from);
    }
}
