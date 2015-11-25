package ufu.tcc.patrick.pherocast;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

public class UserEmailFetcher
{
  private static Account getAccount(AccountManager paramAccountManager)
  {
    Account[] arrayOfAccount = paramAccountManager.getAccountsByType("com.google");
    if (arrayOfAccount.length > 0)
      return arrayOfAccount[0];
    return null;
  }

  static String getEmail(Context paramContext)
  {
    Account localAccount = getAccount(AccountManager.get(paramContext));
    if (localAccount == null)
      return null;
    Log.w("TESTE DE EMAIL", localAccount.name);
    return localAccount.name;
  }
}
