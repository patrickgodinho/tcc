package ufu.tcc.patrick.pherocast;

public class NetworkPoint
{
  private String bssid;
  private String capabilities;
  private String data;
  private int frequency;
  private int level;
  private String ssid;
  private long timestamp;

  public NetworkPoint()
  {
  }

  public NetworkPoint(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    this.bssid = paramString1;
    this.ssid = paramString2;
    this.capabilities = paramString3;
    this.frequency = paramInt1;
    this.level = paramInt2;
    this.data = paramString4;
  }

  public String getBssid()
  {
    return this.bssid;
  }

  public String getCapabilities()
  {
    return this.capabilities;
  }

  public String getData()
  {
    return this.data;
  }

  public int getFrequency()
  {
    return this.frequency;
  }

  public int getLevel()
  {
    return this.level;
  }

  public String getSsid()
  {
    return this.ssid;
  }

  public long getTimestamp()
  {
    return this.timestamp;
  }

  public void setBssid(String paramString)
  {
    this.bssid = paramString;
  }

  public void setCapabilities(String paramString)
  {
    this.capabilities = paramString;
  }

  public void setData(String paramString)
  {
    this.data = paramString;
  }

  public void setFrequency(int paramInt)
  {
    this.frequency = paramInt;
  }

  public void setLevel(int paramInt)
  {
    this.level = paramInt;
  }

  public void setSsid(String paramString)
  {
    this.ssid = paramString;
  }

  public void setTimestamp(long paramLong)
  {
    this.timestamp = paramLong;
  }
}
