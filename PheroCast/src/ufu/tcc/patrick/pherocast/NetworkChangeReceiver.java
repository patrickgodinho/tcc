package ufu.tcc.patrick.pherocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkChangeReceiver extends BroadcastReceiver
{
  public void enviarParaDocs(Context paramContext)
  {
    NetworkPointDAO localNetworkPointDAO = NetworkPointDAO.getInstance(paramContext);
    ArrayList localArrayList = (ArrayList)localNetworkPointDAO.recuperarTodos();
    HttpRequest localHttpRequest = new HttpRequest();
    Iterator localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      NetworkPoint localNetworkPoint = (NetworkPoint)localIterator.next();
      String str1 = localNetworkPoint.getSsid();
      String str2 = localNetworkPoint.getBssid();
      String str3 = localNetworkPoint.getCapabilities();
      String str4 = String.valueOf(localNetworkPoint.getFrequency());
      String str5 = String.valueOf(localNetworkPoint.getLevel());
      String str6 = localNetworkPoint.getData();
      String str7 = UserEmailFetcher.getEmail(paramContext);
      localHttpRequest.sendPost("https://docs.google.com/forms/d/1G_dkyvwug--i_We7qAaA3QV-Xw_plTBJeKdElW22S4w/formResponse", "entry_2059700=" + URLEncoder.encode(str1) + "&" + "entry_1828317397=" + URLEncoder.encode(str2) + "&" + "entry_2146852893=" + URLEncoder.encode(str3) + "&" + "entry_312023197=" + URLEncoder.encode(str4) + "&" + "entry_644637792=" + URLEncoder.encode(str5) + "&" + "entry_604910793=" + URLEncoder.encode(str6) + "&" + "entry_612999935= " + URLEncoder.encode(str7));
      localNetworkPointDAO.deletar(localNetworkPoint);
    }
  }

  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
    NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
    if ((localNetworkInfo1.isAvailable()) || (localNetworkInfo2.isConnected()));
    try
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          enviarParaDocs(paramContext);
        }
      }).start();
      return;
    }
    catch (Exception localException)
    {
      Log.d("Netowk Available ", localException.getMessage());
    }
  }
}
