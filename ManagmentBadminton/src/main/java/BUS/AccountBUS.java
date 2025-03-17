package BUS;


import DAO.AccountDAO;
import DTO.AccountDTO;
import java.util.List;

public class AccountBUS {

    public List<AccountDTO> getAllAccounts() {
        return AccountDAO.getAllAccounts();
    }

//    public void updateAccount(AccountDTO account) {
//        AccountDAO dao = new AccountDAO();
//        dao.updateAccount(account);
//    }

}
