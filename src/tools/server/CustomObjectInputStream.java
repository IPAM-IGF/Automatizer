package tools.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.net.*;

import main.Client.Worker;



public class CustomObjectInputStream extends ObjectInputStream {
		private ClassLoader classLoader;
 
		public CustomObjectInputStream(InputStream in) throws IOException {
			super(in);
		}
 
		protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
				ClassNotFoundException {			
			return Class.forName(desc.getName(), false, Worker.class.getClassLoader());
		}
}
