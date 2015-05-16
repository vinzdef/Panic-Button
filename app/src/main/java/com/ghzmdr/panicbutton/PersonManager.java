package com.ghzmdr.panicbutton;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by ghzmdr on 18/01/15.
 */
public class PersonManager {
    private static final String SAVEFILE = "PanicButtonContactsList";

    static void savePerson(Person p, Context c){
        ArrayList<Person> persons = getSavedPersons(c);

        for (Person per : persons){
            if (per.equals(p)){
                return;
            }
        }

        persons.add(p);
        Log.i("PERSON MANAGER", "Added: " + p.getName());
        setSavedPersons(persons, c);
    }

    static void removePerson(Person p, Context c){
        ArrayList<Person> persons = getSavedPersons(c);
        for (int i = 0; i < persons.size(); i++){
            if (persons.get(i).equals(p)){
                persons.remove(i);
                Log.i("PERSON MANAGER", "Removed: " + p.getName());
            }
        }

        setSavedPersons(persons, c);
    }

    private static void setSavedPersons(ArrayList<Person> persons, Context c) {
        try {
            FileOutputStream fos = c.openFileOutput(SAVEFILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(persons);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(c, "UNABLE TO SAVE CONTACTS FILE NOT FOUND", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(c, "UNABLE TO SAVE CONTACTS FILE", Toast.LENGTH_SHORT).show();
        }
    }

    static ArrayList<Person> getSavedPersons(Context c){
        ArrayList<Person> ret = new ArrayList<>();

        try {
            FileInputStream fis = c.openFileInput(SAVEFILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (ArrayList<Person>) ois.readObject();
        } catch (FileNotFoundException e) {
            setSavedPersons(new ArrayList<Person>(), c);
        } catch (IOException e) {
            Toast.makeText(c, "UNABLE TO SAVE CONTACTS FILE", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(c, "UNABLE TO SAVE CONTACTS FILE", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return ret;
    }
}
