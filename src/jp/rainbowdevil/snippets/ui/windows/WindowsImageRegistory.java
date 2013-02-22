package jp.rainbowdevil.snippets.ui.windows;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class WindowsImageRegistory {
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(WindowsImageRegistory.class);
	
	private static WindowsImageRegistory instance = new WindowsImageRegistory();
	
	private ImageRegistry imageRegistry;
	
	private WindowsImageRegistory(){
		imageRegistry = new ImageRegistry();
		
		imageRegistry.put("hoge", ImageDescriptor.createFromFile(getClass(), "/icon/new_con.gif"));
		log.debug("Image hoge="+imageRegistry.get("hoge"));
		
	}
	
	public static WindowsImageRegistory get(){
		return instance;
	}
	
	public static ImageDescriptor getImageDescriptor(String key){
		return get().imageRegistry.getDescriptor(key);
	}
	
	public static Image getImage(String key){
		return get().imageRegistry.get(key);
	}

}
