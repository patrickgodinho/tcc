package ufu.tcc.patrick.pherocast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
	final Handler handler = new Handler();
	boolean scanInitiated;
	TimerTask scanTask;
	Timer t = new Timer();
	TextView text;
	WifiManager wifi;
	boolean wifiOn;
	WifiScanReceiver wifiReciever;
	String[] wifis;
	List<ScanResult> localList = new ArrayList<ScanResult>();

	private String pegarHoraAtual() {
		return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()); 
	}

	public void getWifiState() {
		System.out.println("passou aqui " + wifi.isWifiEnabled());
		if (!wifi.isWifiEnabled()) {
			wifiOn = false;
			Toast.makeText(getApplicationContext(),
					"Wifi desativado, estamos ativando...", 1).show();
			wifi.setWifiEnabled(true);
			return;
		}
		wifiOn = true;
	}

	public void onBackPressed() {
		moveTaskToBack(true);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903064);
		wifi = ((WifiManager) getSystemService("wifi"));
		wifiReciever = new WifiScanReceiver();
		scanTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						getWifiState();
						registerReceiver(wifiReciever, new IntentFilter(
								"android.net.wifi.SCAN_RESULTS"));
						scanInitiated = true;
						wifi.startScan();
					}
				});
			}
		};
		t.schedule(scanTask, 300L, 60000L);
	}

	class WifiScanReceiver extends BroadcastReceiver {
		WifiScanReceiver() {
		}

		@SuppressLint({ "UseValueOf", "NewApi" })
		public void onReceive(Context paramContext, Intent paramIntent) {

			NetworkPointDAO localNetworkPointDAO = NetworkPointDAO
					.getInstance(getBaseContext());
			if (scanInitiated) {
				localList = wifi.getScanResults();
				wifis = new String[localList.size()];

			}
			for (int i = 0;; i++) {
				if (i >= localList.size()) {
					scanInitiated = false;
					System.out.println(wifiOn);
					if (!wifiOn)
						wifi.setWifiEnabled(false);
					unregisterReceiver(this);
					return;
				}
				wifis[i] = (((ScanResult) localList.get(i)).SSID + ", "
						+ ((ScanResult) localList.get(i)).frequency + ", "
						+ ((ScanResult) localList.get(i)).level + ", " + ((ScanResult) localList
						.get(i)).BSSID);
				localNetworkPointDAO
						.salvar(new NetworkPoint(
								((ScanResult) localList.get(i)).BSSID,
								((ScanResult) localList.get(i)).SSID,
								((ScanResult) localList.get(i)).capabilities,
								((ScanResult) localList.get(i)).frequency,
								((ScanResult) localList.get(i)).level,
								pegarHoraAtual()));
			}
		}
	}
}