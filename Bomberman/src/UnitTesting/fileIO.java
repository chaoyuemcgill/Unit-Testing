package UnitTesting;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVWriter;
import Database.*;
import gameplayModel.*;
import menuModel.*;

public class fileIO extends Database{
	
	File filecurrent = new File("database.csv");
	File filebak = new File("databasebak.csv");
	
	@Before
	public void setUp() throws Exception {
		filebak.delete();
		filecurrent.renameTo(filebak);
	}

	@After
	public void tearDown() throws Exception {
		filecurrent.delete();
		filebak.renameTo(filecurrent);
	}

	@Test
	public void test() throws Exception {
		System.out.println("test");
		//ArrayList<Player> players;
		//players = new ArrayList<Player>();
		//new Database().generateCSV();
		//generateCSV();
	}
	
}



	
	
