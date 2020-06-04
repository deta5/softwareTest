package app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
import main.Main;
import util.Conf;
import util.GUIutil;
import vo.Customer;
import vo.Messege;
public class LoginFrame extends JFrame implements ActionListener {
    private JLabel lbAccount = new JLabel("请输入账号");
    private JTextField tfAccount = new JTextField(10);
    private JLabel lbPassword = new JLabel("请输入密码");
    private JPasswordField pfPassword = new JPasswordField(10);
    private JButton btLogin = new JButton("登录");
    private JButton btRegister = new JButton("注册");
    private JButton btExit = new JButton("退出");
    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    public LoginFrame() {
        super("登录");
        this.setLayout(new FlowLayout());
        this.add(lbAccount);
        this.add(tfAccount);
        this.add(lbPassword);
        this.add(pfPassword);
        this.add(btLogin);
        this.add(btRegister);
        this.add(btExit);
        this.setSize(240, 180);
        GUIutil.toCenter(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        btLogin.addActionListener(this);
        btRegister.addActionListener(this);
        btExit.addActionListener(this);
    }

    public void login() {
        String account = tfAccount.getText();
        Customer cus = new Customer();
        cus.setAccount(account);
        cus.setPassword(new String(pfPassword.getPassword()));
        Messege msg = new Messege();
        msg.setType(Conf.LOGIN);
        msg.setContent(cus);
        try {
            socket = new Socket(Main.serverIP, Main.port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(msg);
            Messege receiveMsg = (Messege) ois.readObject();
            String type = receiveMsg.getType();
            if (type.equals(Conf.LOGINFALL)) {
                JOptionPane.showMessageDialog(this, "登录失败");
                socket.close();
                return;
            }
            JOptionPane.showMessageDialog(this, "登录成功");
            new ChatFrame(ois, oos, receiveMsg,account);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "网络连接异常");
            System.exit(-1);
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==btLogin){
            this.login();
        }else if(e.getSource()==btRegister){
            this.dispose();
            new RegisterFrame();
        }else{
            JOptionPane.showMessageDialog(this,"谢谢光临");
            System.exit(0);
        }

    }
}
