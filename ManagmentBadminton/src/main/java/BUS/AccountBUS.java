package BUS;

import DAO.AccountDAO;
import DTO.AccountDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AccountBUS {

    public static ArrayList<AccountDTO> searchAccounts(String keyword) {
        return AccountDAO.searchAccounts(keyword);
    }

    public static AccountDTO getAccountByUsername(String username) {
        return AccountDAO.getAccountByUsername(username);
    }

    public static List<AccountDTO> getAllAccounts() {
        return AccountDAO.getAllAccounts();
    }

    public static String countAccountFromPermission(String ID) {
        return AccountDAO.countAccountFromPermission(ID);
    }

    public static Boolean addAccount(AccountDTO account) {
        if (validationAccount(account)) {
            return AccountDAO.addAccount(account);
        }
        return false;
    }

    public static Boolean updateAccount(AccountDTO account) {
        if (validationAccount(account)) {
            return AccountDAO.updateAccount(account);
        }
        return false;
    }

    public static Boolean deletedAccount(String Username) {
        return AccountDAO.delete_Account(Username);
    }

    public static Boolean validationAccount(AccountDTO acc) {
        // Regex cho username
        String usernamePattern = "^[a-zA-Z0-9._]+$";
        // Regex kiểm tra password có ít nhất 1 chữ cái
        String containsLetter = ".*[a-zA-Z].*";
        // Regex kiểm tra password có ít nhất 1 chữ số
        String containsDigit = ".*[0-9].*";

        // Kiểm tra username
        String username = acc.getUsername();
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên tài khoản không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (username.length() < 5) {
            JOptionPane.showMessageDialog(null, "Tên tài khoản phải có ít nhất 5 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!username.matches(usernamePattern)) {
            JOptionPane.showMessageDialog(null, "Tên tài khoản chỉ được chứa chữ, số, dấu chấm và gạch dưới", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra password
        String password = acc.getPassword();
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 6 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!password.matches(containsLetter) || !password.matches(containsDigit)) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải chứa ít nhất một chữ cái và một số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
//    public void updateAccount(AccountDTO account) {
//        AccountDAO dao = new AccountDAO();
//        dao.updateAccount(account);
//    }

}
