package controller;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import model.Model;

/*
 * SLhelper - Save and Load from/to XML functions
 */

public class SLhelper {
	public static void save(Model model, String filename) throws Exception{
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        encoder.writeObject(model);
        encoder.close();
    }

    public static Model load(String filename) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Model o = (Model)decoder.readObject();
        decoder.close();
        return o;
    }
}
