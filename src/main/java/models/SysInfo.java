package models;

import javafx.beans.property.*;

public class SysInfo
{
    private IntegerProperty ledCount = new SimpleIntegerProperty();
    private BooleanProperty sysInited = new SimpleBooleanProperty();
    private IntegerProperty sysFreeRam = new SimpleIntegerProperty();
    private StringProperty sysSdkVer = new SimpleStringProperty();
    private StringProperty sysId = new SimpleStringProperty();
    private IntegerProperty sysChipRev = new SimpleIntegerProperty();
    private BooleanProperty sysDualCore = new SimpleBooleanProperty();
    private StringProperty sysVer = new SimpleStringProperty();
    private StringProperty netIp = new SimpleStringProperty();
    private StringProperty netSsid = new SimpleStringProperty();
    private IntegerProperty netSig = new SimpleIntegerProperty();

    public void clone(SysInfo sysInfo)
    {
        // Use invoke
        this.ledCount.set(sysInfo.ledCount.get());
        this.sysInited.set(sysInfo.sysInited.get());
        this.sysFreeRam.set(sysInfo.sysFreeRam.get());
        this.sysSdkVer.set(sysInfo.sysSdkVer.get());
        this.sysId.set(sysInfo.sysId.get());
        this.sysChipRev.set(sysInfo.sysChipRev.get());
        this.sysDualCore.set(sysInfo.sysDualCore.get());
        this.sysVer.set(sysInfo.sysVer.get());
        this.netIp.set(sysInfo.netIp.get());
        this.netSsid.set(sysInfo.netSsid.get());
        this.netSig.set(sysInfo.netSig.get());
    }

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
