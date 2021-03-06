package team.iknowthatscripture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector {

	   private static final String DATABASE_NAME = "dbScriptures";
	   private SQLiteDatabase database; 
	   private DatabaseOpenHelper databaseOpenHelper; 	
	
	   public DatabaseConnector(Context context) {
		      databaseOpenHelper = 
		         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
	   }	   
	   
	   public void open() throws SQLException {
		      // create or open a database for reading/writing
		      database = databaseOpenHelper.getWritableDatabase();
	   } 	   
	   
	   public void close() {
		      if (database != null)
		         database.close();
	   } 	   
	   
	   // inserts a new scripture into the database
	   public void insertScripture(String passage, String book, int chapter, 
	      int verse) {
		   
	      ContentValues newScripture = new ContentValues();
	      newScripture.put("passage", passage);
	      newScripture.put("book", book);
	      newScripture.put("chapter", chapter);
	      newScripture.put("verse", verse);

	      open();
	      database.insert("scriptures", null, newScripture);
	      close();
	   }        	   
	   
	   // updates a scripture in the database
	   public void updateScripture(long id, String passage, String book, int chapter, 
	      int verse) {
		   
	      ContentValues editScripture = new ContentValues();
	      editScripture.put("passage", passage);
	      editScripture.put("book", book);
	      editScripture.put("chapter", chapter);
	      editScripture.put("verse", verse);


	      open(); 
	      database.update("scriptures", editScripture, "_id=" + id, null);
	      close(); 
	   } 	   
	   
	   public Cursor getAllScriptures() {
		      return database.query(
		    		  "scriptures", 
		    		  new String[] {"_id", "passage", "book", "chapter", "verse"}, 
		    		  null, null, null, null, 
		    		  "passage");
		      // query(String table, 
		      // String[] columns, String selection, String[] selectionArgs, 
		      // String groupBy, String having, String orderBy)
	   }
	   
	   // get a Cursor containing all information about the movie specified
	   // by the given id
	   public Cursor getOneScripture(long id) {
	      return database.query(
	         "scriptures", null, "_id=" + id, null, null, null, null);
	      
	      // public Cursor query (String table, String[] columns, 
	      // String selection, String[] selectionArgs, String groupBy, 
	      // String having, String orderBy, String limit)
	   }	   
	   
	   // delete the scripture specified by the given id
	   public void deleteScripture(long id) {
	      open(); 
	      database.delete("Scriptures", "_id=" + id, null);
	      close();
	   } 	   
	   
	   private class DatabaseOpenHelper extends SQLiteOpenHelper {

		      public DatabaseOpenHelper(Context context, String name,
		         CursorFactory factory, int version) {
		         super(context, name, factory, version);
		      } 


		      // creates the scriptures table when the database is created
		      @Override   
		      public void onCreate(SQLiteDatabase db) {
		         // query to create a new table named ratings
		         String createQuery = "CREATE TABLE scriptures" +
		            "(_id INTEGER PRIMARY KEY autoincrement, " +
		            "passage TEXT, " +
		            "book TEXT, " +
		            "chapter INTEGER, " +
		            "verse INTEGER);";
		                  
		         db.execSQL(createQuery);
		      }

		      @Override
		      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
		          int newVersion) 
		      { /* nothing to do*/ }
		      
	   } 	// end of 	   private class DatabaseOpenHelper extends SQLiteOpenHelper {   
	   
	   
} // end of public class DatabaseConnector {
