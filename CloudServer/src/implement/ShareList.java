package implement;

import java.awt.List;
import java.io.File;
import java.io.Serializable;

public class ShareList implements Serializable {
	public String owner,fname;
	public ShareList(String name,String f)
	{
		owner=name;
		fname=f;
	} 
}
