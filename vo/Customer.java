package vo;
import java.io.Serializable;
public class Customer implements Serializable {
    private String account;
    private String password;
    private String name;
    public String getAccount()
    {
        return account;
    }
    public void setAccount(String account)
    {
        this.account=account;
    }
    public String getPassword()
    {
        return getPassword();
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }

}
