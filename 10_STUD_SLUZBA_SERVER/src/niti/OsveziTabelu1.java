/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import forme.ServerskaForma;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author racunar
 */
public class OsveziTabelu1 extends Thread {
    
    private ServerskaForma sf;
    private boolean kraj = false;
    
    public OsveziTabelu1(ServerskaForma sf) {
        this.sf = sf;
    }
    
    @Override
    public void run() {
        while (!kraj) {
            try {
                sf.popuniTabeluProf();
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziTabelu1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void zaustavi(){
        kraj = true;
    }
    
}
