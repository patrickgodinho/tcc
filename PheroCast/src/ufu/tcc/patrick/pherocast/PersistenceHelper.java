package ufu.tcc.patrick.pherocast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceHelper extends SQLiteOpenHelper
{
  public static final String NOME_BANCO = "wificollector";
  public static final int VERSAO = 1;
  private static PersistenceHelper instance;

  private PersistenceHelper(Context paramContext)
  {
    super(paramContext, "wificollector", null, 1);
  }

  public static PersistenceHelper getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new PersistenceHelper(paramContext);
    return instance;
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE network_point(bssid TEXT, ssid TEXT, capabilities TEXT, frequencia INTEGER, level INTEGER, timestamp LONG, data_adicao STRING )");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS network_point");
    onCreate(paramSQLiteDatabase);
  }
}
