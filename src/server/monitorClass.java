package server;

import java.util.ArrayList;
import java.util.List;

class monitorClass{
    List<Process> processList;

    void Monitor(){
        this.processList=new ArrayList<Process>();
    }

    boolean startService(String serviceName){
        try {
            Process newProcess = Runtime.getRuntime().exec("./" + serviceName);
            this.processList.add(newProcess);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}