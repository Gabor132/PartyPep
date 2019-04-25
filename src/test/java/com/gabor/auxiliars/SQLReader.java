package com.gabor.auxiliars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLReader {

    private final static Logger logger = Logger.getLogger(SQLReader.class.toString());

    public static List<String> GetStatements(String path){
        logger.log(Level.INFO, "Trying to retrieve SQL statements from \"" + path + "\"");
        List<String> statements = new ArrayList<>();
        try(FileReader fr = new FileReader(new File(path))){
            BufferedReader bufferedReader = new BufferedReader(fr);
            String statement = "";
            do {
                statement = bufferedReader.readLine();
                statements.add(statement.trim());
            }while(!statement.isEmpty());
        }catch(IOException exception){
            logger.log(Level.SEVERE, "ERROR WHILE READING SQL FILE: " + path);
        }finally {
            logger.log(Level.INFO, "Retrieved " + statements.size() + " lines");
        }
        return statements;
    }
}
