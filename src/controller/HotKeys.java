package controller;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import view.TranslucentGUI;
import view.CurrentTime;

public class HotKeys {
	Provider provider = Provider.getCurrentProvider(true);
	boolean vis;
	public HotKeys(final TranslucentGUI o, final CurrentTime ct){
		provider.register(KeyStroke.getKeyStroke("shift TAB"), new HotKeyListener(){

			public void onHotKey(HotKey hk) {
				vis = o.isVisible();
				o.setVisible(!vis);
				ct.setVisible(!vis);
			}
			
		});
	}
	
	public void cleanUp(){
		provider.reset();
		provider.stop();
	}
	
}