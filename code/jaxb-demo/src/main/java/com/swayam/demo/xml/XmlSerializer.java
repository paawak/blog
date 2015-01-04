package com.swayam.demo.xml;

import java.io.OutputStream;

public interface XmlSerializer {

	void serialize(Object object, OutputStream outputStream);

}
