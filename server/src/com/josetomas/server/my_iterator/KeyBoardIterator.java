package com.josetomas.server.my_iterator;

import com.josetomas.server.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class KeyBoardIterator extends MyIterator {

    public KeyBoardIterator(Robot robot) {
        super(robot);
    }

    //converts the char to the keyCode and the robot presses the key
    public void keyPress(char key) throws IllegalArgumentException {
        try {
            int code = (int) key;
            robot.keyPress(code);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid key code");
            robot.keyPress(KeyEvent.VK_SPACE); //Todo: Camabiar por un cartelito o algo mejor.
                                              // Lo tira cuando el keycode de android no corresponde al de la compu
        }
    }

    //Implements distinct methods if the shortCut should or shouldn't be modified.
    public void shortCutPress(String shortCut, boolean custom){
        if(!custom){
            try {
                shortCutPress(shortCut);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                modifyShortCut(shortCut, customPress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //This method searchs in the shortCut's file and ones it founds the shortCut name
    //the next token after the "," will be the shortCut code, then it implements that code.
    private void shortCutPress(String shortCut) throws Exception {
        FileReader reader = new FileReader("server\\src\\com\\josetomas\\server\\my_iterator\\ShortCuts.csv");
        BufferedReader shortCutFile = new BufferedReader(reader);
        String line = shortCutFile.readLine();
        StringTokenizer tokenizer;

        while (line != null) {
            tokenizer = new StringTokenizer(line, ",");

            //search's for the shortcut name in the csv, after the next ',' will be
            //the number of keys to be pressed, after that all the keys separated by ','
            while (tokenizer.hasMoreTokens()) {
                String shortCutLine = tokenizer.nextToken();
                if (shortCutLine.equals(shortCut)) {
                    int keys = Integer.parseInt(tokenizer.nextToken());
                    int[] listKeys = new int[keys];
                    for(int i=0; i<keys; i++){
                        listKeys[i] = Integer.parseInt(tokenizer.nextToken());
                        robot.keyPress(listKeys[i]);
                    }
                    for(int i=keys-1; i>0; i--){
                        robot.keyRelease(listKeys[i]);
                    }
                }
            }

            try {
                line = shortCutFile.readLine();
            } catch (Exception e) {
                //TODO
                line = null;
            }
        }

        reader.close();
    }

    //Uses an auxiliary file to copy all the lines in the shortCut file except for the
    //ShortCut that should be modified
    private void modifyShortCut(String shortCut, int[] keyList) throws IOException {
        File file1 = new File("server\\src\\com\\josetomas\\server\\my_iterator\\ShortCuts.csv");
        FileReader reader = new FileReader(file1);
        BufferedReader shortCutFile = new BufferedReader(reader);
        String line = shortCutFile.readLine();
        StringTokenizer tokenizer;

        File file2 = new File("server\\src\\com\\josetomas\\server\\my_iterator\\ShortCuts2.csv");
        FileWriter writer = new FileWriter(file2);
        BufferedWriter shortCutFile2 = new BufferedWriter(writer);

        while (line!=null) {
            tokenizer = new StringTokenizer(line, ",");

            //search's for the shortcut name in the csv, after the next ',' will be
            //the number of keys to be pressed, after that all the keys separated by ','
            String shortCutLine = tokenizer.nextToken();

            //Copies the line to a new file, if it is the shortCut to be codified
            //add the keyList in that place
            if (!shortCutLine.equals(shortCut)) {
                shortCutFile2.write(line+'\n');
            }
            else{
                String newLine = shortCut+","+keyList.length+",";
                for(int i=0; i<keyList.length; i++){
                    newLine = newLine+keyList[i]+",";
                }
                shortCutFile2.write(newLine+'\n');
            }

            line = shortCutFile.readLine();
        }

        shortCutFile2.flush();

        shortCutFile.close();
        shortCutFile2.close();
        reader.close();
        writer.close();

        //Change info from the auxiliary file shortCuts2 to the shortCuts original file
        changeFiles(file2, file1);
    }

    //change the info from one file to another
    private void changeFiles(File file1, File file2) throws IOException {
        FileReader reader = new FileReader(file1);
        BufferedReader shortCutFile = new BufferedReader(reader);
        String line = shortCutFile.readLine();

        FileWriter writer = new FileWriter(file2);
        BufferedWriter shortCutFile2 = new BufferedWriter(writer);

        while (line!=null) {
            shortCutFile2.write(line+'\n');
            line = shortCutFile.readLine();
        }

        shortCutFile2.flush();

        shortCutFile.close();
        shortCutFile2.close();
        reader.close();
        writer.close();
    }

    //Generates a new list with all the keyCodes that have been add to the jOptionpane
    //TODO improve so it receives more than just keys and also to receive just one key at the time
    private int[] customPress(){
        String keyList = "";
        int[] keys;
        while(true){
            String newEntry = JOptionPane.showInputDialog(Properties.displayValue(Locale.getDefault(), "addKeysID") + keyList);
            if(newEntry!=null){
                keyList = keyList+(newEntry.toUpperCase());
            }
            else{
                keys = new int[keyList.length()];
                for(int i=0; i<keys.length; i++){
                    keys[i] = (int) keyList.charAt(i);
                }
                return keys;

            }
        }
    }

}

