package ufu.tcc.patrick.pherocast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class NetworkPointDAO
{
  public static final String COLUNA_BSSID = "bssid";
  public static final String COLUNA_CAPABILITIES = "capabilities";
  public static final String COLUNA_DATA = "data_adicao";
  public static final String COLUNA_FREQUENCIA = "frequencia";
  public static final String COLUNA_LEVEL = "level";
  public static final String COLUNA_SSID = "ssid";
  public static final String COLUNA_TIMESTAMP = "timestamp";
  public static final String NOME_TABELA = "network_point";
  public static final String SCRIPT_CRIACAO_TABELA_NETWORK_POINT = "CREATE TABLE network_point(bssid TEXT, ssid TEXT, capabilities TEXT, frequencia INTEGER, level INTEGER, timestamp LONG, data_adicao STRING )";
  public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS network_point";
  private static NetworkPointDAO instance;
  private SQLiteDatabase dataBase = null;

  private NetworkPointDAO(Context paramContext)
  {
    this.dataBase = PersistenceHelper.getInstance(paramContext).getWritableDatabase();
  }

  private List<NetworkPoint> construirNetworkPorCursor(Cursor paramCursor)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramCursor == null)
      return localArrayList;
    try
    {
      if (paramCursor.moveToFirst())
      {
        boolean bool;
        do
        {
          int i = paramCursor.getColumnIndex("ssid");
          int j = paramCursor.getColumnIndex("bssid");
          int k = paramCursor.getColumnIndex("capabilities");
          int m = paramCursor.getColumnIndex("frequencia");
          int n = paramCursor.getColumnIndex("level");
          paramCursor.getColumnIndex("timestamp");
          int i1 = paramCursor.getColumnIndex("data_adicao");
          String str1 = paramCursor.getString(i);
          String str2 = paramCursor.getString(j);
          String str3 = paramCursor.getString(k);
          int i2 = paramCursor.getInt(n);
          localArrayList.add(new NetworkPoint(str2, str1, str3, paramCursor.getInt(m), i2, paramCursor.getString(i1)));
          bool = paramCursor.moveToNext();
        }
        while (bool);
      }
      return localArrayList;
    }
    finally
    {
      paramCursor.close();
    }
    //throw localObject;
  }

  private ContentValues gerarContentValues(NetworkPoint paramNetworkPoint)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("bssid", paramNetworkPoint.getBssid());
    localContentValues.put("ssid", paramNetworkPoint.getSsid());
    localContentValues.put("capabilities", paramNetworkPoint.getCapabilities());
    localContentValues.put("frequencia", Integer.valueOf(paramNetworkPoint.getFrequency()));
    localContentValues.put("level", Integer.valueOf(paramNetworkPoint.getLevel()));
    localContentValues.put("data_adicao", paramNetworkPoint.getData());
    return localContentValues;
  }

  public static NetworkPointDAO getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new NetworkPointDAO(paramContext);
    return instance;
  }

  public void deletar(NetworkPoint paramNetworkPoint)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramNetworkPoint.getBssid());
    this.dataBase.delete("network_point", "bssid =  ?", arrayOfString);
  }

  public void editar(NetworkPoint paramNetworkPoint)
  {
    ContentValues localContentValues = gerarContentValues(paramNetworkPoint);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramNetworkPoint.getBssid());
    this.dataBase.update("network_point", localContentValues, "bssid = ?", arrayOfString);
  }

  public void fecharConexao()
  {
    if ((this.dataBase != null) && (this.dataBase.isOpen()))
      this.dataBase.close();
  }

  public int getQuantidade()
  {
    return this.dataBase.rawQuery("select * from network_point", null).getCount();
  }

  public List<NetworkPoint> recuperarTodos()
  {
    return construirNetworkPorCursor(this.dataBase.rawQuery("SELECT * FROM network_point", null));
  }

  public void salvar(NetworkPoint paramNetworkPoint)
  {
    ContentValues localContentValues = gerarContentValues(paramNetworkPoint);
    this.dataBase.insert("network_point", null, localContentValues);
    System.out.println(paramNetworkPoint.getData() + "AUHUHAHUU");
  }

  public void truncarTabela()
  {
    this.dataBase.execSQL("DELETE FROM network_point;");
  }
}