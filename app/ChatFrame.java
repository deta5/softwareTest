package app;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.*;

import util.Conf;
import vo.Customer;
import vo.Messege;



public class ChatFrame extends JFrame implements ActionListener,Runnable{
    private Socket socket=null;
    private ObjectInputStream ois=null;
    private ObjectOutputStream oos=null;
    private boolean canRun=true;
    private String account;
    private JLabel lbUser=new JLabel("在线人员名单:");
    private List lstUser=new List();
    private JLabel lbMsg=new JLabel("聊天记录:");
    private JTextArea taMsg=new JTextArea();
    private JScrollPane spMsg=new JScrollPane(taMsg);
    private JTextField tfMsg=new JTextField();
    private JButton btSend=new JButton("发送");
    private JPanel plUser=new JPanel(new BorderLayout());
    private JPanel plMsg=new JPanel(new BorderLayout());
    private JPanel plUser_Msg=new JPanel(new GridLayout(1,2));
    private JPanel plSend=new JPanel(new BorderLayout());
    public ChatFrame(ObjectInputStream ois,ObjectOutputStream oos,Messege receiveMessage,String account){
        this.ois=ois;
        this.oos=oos;
        this.account=account;
        this.initFrame();
        this.initUserList(receiveMessage);
        new Thread(this).start();
    }
    public void initFrame()
    {
        this.setTitle("当前在线:"+account);
        this.setBackground(Color.magenta);
        plUser.add(lbUser,BorderLayout.NORTH);
        plUser.add(lstUser,BorderLayout.CENTER);
        plUser_Msg.add(plUser);
        lstUser.setBackground(Color.pink);

        plMsg.add(lbMsg,BorderLayout.NORTH);
        plMsg.add(spMsg,BorderLayout.CENTER);
        plUser_Msg.add(plUser);
        lstUser.setBackground(Color.pink);

        plSend.add(tfMsg,BorderLayout.CENTER);
        plSend.add(btSend,BorderLayout.EAST);
        tfMsg.setBackground(Color.yellow);
        this.add(plUser_Msg,BorderLayout.CENTER);
        this.add(plSend,BorderLayout.SOUTH);

        btSend.addActionListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
    }

    public void initUserList(Messege messege){
        lstUser.removeAll();
        lstUser.add(Conf.ALL);
        lstUser.select(0);
        Vector<Customer> userListVector=(Vector<Customer>)messege.getContent();
        for(Customer cus:userListVector){
            lstUser.add(cus.getAccount()+","+cus.getName());
        }
    }
    public void run()
    {
        try{
            while (canRun){
                Messege msg=(Messege)ois.readObject();
                if(msg.getType().equals(Conf.MESSAGE)){
                    taMsg.append(msg.getContent()+"\n");
                }
                else if(msg.getType().equals(Conf.LOGOUT)){
                    Customer cus=(Customer)msg.getContent();
                    lstUser.remove(cus.getAccount()+","+cus.getName());
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            canRun=false;
            javax.swing.JOptionPane.showMessageDialog(this,"对不起，你被迫下线");
            System.exit(-1);
        }
    }
    public void actionPerformed(ActionEvent e){
        try{
            Messege msg=new Messege();
            msg.setType(Conf.MESSAGE);
            msg.setContent(account+"说:"+tfMsg.getText());
            msg.setFrom(account);
            String toInfo=lstUser.getSelectedItem();
            msg.setTo(toInfo.split(",")[0]);
            oos.writeObject(msg);
            tfMsg.setText("");
        }catch (Exception ex){
            JOptionPane.showMessageDialog(this,"消息发送异常");
        }
    }

}
