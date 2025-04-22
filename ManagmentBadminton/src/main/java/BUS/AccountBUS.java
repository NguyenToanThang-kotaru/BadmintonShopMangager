package BUS;


import DAO.AccountDAO;
import DTO.AccountDTO;
import java.util.List;

public class AccountBUS {
    public static AccountDTO getAccountByUsername(String username){
        return AccountDAO.getAccountByUsername(username);
    }
    public static List<AccountDTO> getAllAccounts() {
        return AccountDAO.getAllAccounts();
    }
    public static String countAccountFromPermission(String ID) {
        return AccountDAO.countAccountFromPermission(ID);
    }
    
    public static Boolean addAccount(AccountDTO account) {
        return AccountDAO.addAccount(account);
    }
    
    public static Boolean updateAccount(AccountDTO account) {
        return AccountDAO.updateAccount(account);
    }
    
    public static Boolean deletedAccount(String Username){
        return AccountDAO.delete_Account(Username);
    }

//    public void updateAccount(AccountDTO account) {
//        AccountDAO dao = new AccountDAO();
//        dao.updateAccount(account);
//    }

}
