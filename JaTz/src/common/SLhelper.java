package common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/*
 * SLhelper - Save and Load from/to XML functions
 */

public class SLhelper {
	public static void save(Object model, String filename) throws Exception{
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        encoder.writeObject(model);
        encoder.close();
    }

    public static Object load(String filename) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Object o = decoder.readObject();
        decoder.close();
        return o;
    }
}
