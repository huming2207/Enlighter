package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class SysInfo
{
    private IntegerProperty ledCount;
    private BooleanProperty sysInited;
    private IntegerProperty sysFreeRam;
    private StringProperty sysSdkVer;
    private StringProperty sysId;
    private IntegerProperty  sysChipRev;
    private BooleanProperty sysDualCore;
    private StringProperty sysVer;
    private StringProperty netIp;
    private StringProperty netSsid;
    private IntegerProperty netSig;

    public int getLedCount()
    {
        return ledCount.get();
    }

    public IntegerProperty ledCountProperty()
    {
        return ledCount;
    }

    public void setLedCount(int ledCount)
    {
        this.ledCount.set(ledCount);
    }

    public boolean isSysInited()
    {
        return sysInited.get();
    }

    public BooleanProperty sysInitedProperty()
    {
        return sysInited;
    }

    public void setSysInited(boolean sysInited)
    {
        this.sysInited.set(sysInited);
    }

    public int getSysFreeRam()
    {
        return sysFreeRam.get();
    }

    public IntegerProperty sysFreeRamProperty()
    {
        return sysFreeRam;
    }

    public void setSysFreeRam(int sysFreeRam)
    {
        this.sysFreeRam.set(sysFreeRam);
    }

    public String getSysSdkVer()
    {
        return sysSdkVer.get();
    }

    public StringProperty sysSdkVerProperty()
    {
        return sysSdkVer;
    }

    public void setSysSdkVer(String sysSdkVer)
    {
        this.sysSdkVer.set(sysSdkVer);
    }

    public String getSysId()
    {
        return sysId.get();
    }

    public StringProperty sysIdProperty()
    {
        return sysId;
    }

    public void setSysId(String sysId)
    {
        this.sysId.set(sysId);
    }

    public int getSysChipRev()
    {
        return sysChipRev.get();
    }

    public IntegerProperty sysChipRevProperty()
    {
        return sysChipRev;
    }

    public void setSysChipRev(int sysChipRev)
    {
        this.sysChipRev.set(sysChipRev);
    }

    public boolean isSysDualCore()
    {
        return sysDualCore.get();
    }

    public BooleanProperty sysDualCoreProperty()
    {
        return sysDualCore;
    }

    public void setSysDualCore(boolean sysDualCore)
    {
        this.sysDualCore.set(sysDualCore);
    }

    public String getSysVer()
    {
        return sysVer.get();
    }

    public StringProperty sysVerProperty()
    {
        return sysVer;
    }

    public void setSysVer(String sysVer)
    {
        this.sysVer.set(sysVer);
    }

    public String getNetIp()
    {
        return netIp.get();
    }

    public StringProperty netIpProperty()
    {
        return netIp;
    }

    public void setNetIp(String netIp)
    {
        this.netIp.set(netIp);
    }

    public String getNetSsid()
    {
        return netSsid.get();
    }

    public StringProperty netSsidProperty()
    {
        return netSsid;
    }

    public void setNetSsid(String netSsid)
    {
        this.netSsid.set(netSsid);
    }

    public int getNetSig()
    {
        return netSig.get();
    }

    public IntegerProperty netSigProperty()
    {
        return netSig;
    }

    public void setNetSig(int netSig)
    {
        this.netSig.set(netSig);
    }
}
