package com.zte.smartlink.util;

import com.zte.mos.util.basic.Logger;
import com.zte.ums.umd.api.FileUtil;
import com.zte.ums.umd.api.service.Util;
import com.zte.ums.umd.common.util.OSShell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by lemon on 8/22/17.
 */
public final class MwCpuWorkState {

    private static Logger logger = Logger.logger(MwCpuWorkState.class);

    public static String getProcessCpuRate(String processName) {
        String osName = System.getProperty("os.name");
        if (osName != null) {
            if (osName.toLowerCase().contains("win")) {
                return getWindowsCpuRate(processName);
            }

            return getLinuxCpuRate(processName);
        }
        return "";
    }

    private static String getWindowsCpuRate(String processName) {
        try {
            String e = Util.getUmdSrvPath();
            e = e.substring(0, e.indexOf("ums-server"));
            String homedir = e + "\\ums-server\\procs\\ppus\\mwne.ppu\\mwne-svr.pmu\\smart_link.par\\res";
            homedir = FileUtil.addSeparator(homedir);
            String vbs_cpu = homedir + "win_cpu.vbs";
            File cpu_sc = new File(vbs_cpu);
            if (cpu_sc != null && cpu_sc.exists()) {
                Map retMap = (new OSShell()).execShellCmd(new String[]{"cscript", "//nologo",
                        vbs_cpu,processName}, homedir);
                String ret_cpu = (String) retMap.get("UmdGatherOverResult");
                return ret_cpu.replaceAll("\n", "").replaceAll("\r","");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "0";
    }


    private static String getLinuxCpuRate(String processName) {
        String defaultValue = "0";
        Process pro = null;
        BufferedReader in = null;
        try {
            pro=Runtime.getRuntime().exec(new String[]{"sh", "-c","pidstat -u |grep '"+processName+"' |awk '{print $(NF-2)}'"});
            pro.waitFor();
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = in.readLine();
            if (line == null) {
                return defaultValue;
            }

            return line;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (pro != null) {
                pro.destroy();
            }
        }
        return defaultValue;
    }
}
