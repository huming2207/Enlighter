package helpers.interfaces;

import helpers.EnlightPushResult;

import java.util.Map;

public interface EnlightSettingPusher
{
    public abstract void pushSetting(Map<String, String> settingPairs);
    public abstract EnlightPushResult getResult();
}
