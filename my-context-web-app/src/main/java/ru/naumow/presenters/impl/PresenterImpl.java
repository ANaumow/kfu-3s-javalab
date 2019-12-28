package ru.naumow.presenters.impl;

import freemarker.template.Configuration;
import ru.naumow.presenters.AbstractPresenter;

import java.io.PrintWriter;
import java.io.Writer;

@SuppressWarnings("FieldCanBeLocal")
public class PresenterImpl extends AbstractPresenter<PresenterImpl> {
    public PresenterImpl(Writer out, Configuration configuration, String templatePath) {
        super(out, configuration, templatePath);
    }

    @Override
    public void render(PrintWriter out) {
        throw new IllegalStateException("method is not implemented yet");
    }
    /*private static String STATUS_KEY           = "status";
    private static String CURRENT_ACCOUNTS_KEY = "current_list";
    private static String CREDIT_ACCOUNTS_KEY  = "credit_list";
    private static String DEPOSIT_ACCOUNTS_KEY = "deposit_list";
    private static String ACCOUNTS_KEY         = "account_list";
    private static String BALANCE_KEY          = "balance";
    private static String NAME_KEY             = "name";
    private static String USER_KEY             = "user";
    private static String TO_ACCOUNT_KEY       = "to_account";
    private static String FROM_ACCOUNT_KEY     = "from_account";
    private static String DESCRIPTION_KEY      = "description";
    private static String COUNT_KEY            = "count";
    private static String RECEIVER_KEY         = "receiver";
    private static String TRANSACTION_LIST_KEY = "transaction_list";
    private static String CLOSING_ACCOUNT      = "closing_account";

    public PresenterImpl(Writer out, Configuration configuration, String templatePath) {
        super(out, configuration, templatePath);
    }

    public PresenterImpl custom(String key, String value) {
        return put(key, value);
    }

    public PresenterImpl status(String status) {
        return put(STATUS_KEY, status);
    }

    public PresenterImpl balance(long balance) {
        return put(BALANCE_KEY, balance);
    }

    public PresenterImpl name(String name) {
        return put(NAME_KEY, name);
    }

    public PresenterImpl accounts(List<Account> accounts) {
        return put(ACCOUNTS_KEY, accounts);
    }

    public PresenterImpl currentAccounts(List<CurrentAccount> currentAccounts) {
        return put(CURRENT_ACCOUNTS_KEY, currentAccounts);
    }

    public PresenterImpl creditAccounts(List<CreditAccount> creditAccounts) {
        return put(CREDIT_ACCOUNTS_KEY, creditAccounts);
    }

    public PresenterImpl depositAccounts(List<DepositAccount> depositAccounts) {
        return put(DEPOSIT_ACCOUNTS_KEY, depositAccounts);
    }

    public PresenterImpl user(User user) {
        return put(USER_KEY, user);
    }

    public PresenterImpl receiver(User user) {
        return put(RECEIVER_KEY, user);
    }

    public PresenterImpl accountTo(Account account) {
        return put(TO_ACCOUNT_KEY, account);
    }

    public PresenterImpl accountFrom(Account account) {
        return put(FROM_ACCOUNT_KEY, account);
    }

    public PresenterImpl description(String description) {
        return put(DESCRIPTION_KEY, description);
    }

    public PresenterImpl count(int count) {
        return put(COUNT_KEY, count);
    }

    public PresenterImpl transactions(List<PreparedTransaction> transactions) {
        return put(TRANSACTION_LIST_KEY, transactions);
    }

    public PresenterImpl closingAccount(int accountId) {
        return put(CLOSING_ACCOUNT, accountId);
    }
*/
}
