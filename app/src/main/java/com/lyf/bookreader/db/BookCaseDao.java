package com.lyf.bookreader.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lyf.bookreader.javabean.BookCase;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK_CASE".
*/
public class BookCaseDao extends AbstractDao<BookCase, Long> {

    public static final String TABLENAME = "BOOK_CASE";

    /**
     * Properties of entity BookCase.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Bookname = new Property(1, String.class, "bookname", false, "BOOKNAME");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Time = new Property(3, String.class, "time", false, "TIME");
        public final static Property Finish = new Property(4, String.class, "finish", false, "FINISH");
        public final static Property Img = new Property(5, String.class, "img", false, "IMG");
        public final static Property CurPage = new Property(6, int.class, "curPage", false, "CUR_PAGE");
        public final static Property Position = new Property(7, int.class, "position", false, "POSITION");
        public final static Property Total = new Property(8, int.class, "total", false, "TOTAL");
        public final static Property Type = new Property(9, String.class, "type", false, "TYPE");
    };


    public BookCaseDao(DaoConfig config) {
        super(config);
    }
    
    public BookCaseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK_CASE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"BOOKNAME\" TEXT," + // 1: bookname
                "\"AUTHOR\" TEXT," + // 2: author
                "\"TIME\" TEXT," + // 3: time
                "\"FINISH\" TEXT," + // 4: finish
                "\"IMG\" TEXT," + // 5: img
                "\"CUR_PAGE\" INTEGER NOT NULL ," + // 6: curPage
                "\"POSITION\" INTEGER NOT NULL ," + // 7: position
                "\"TOTAL\" INTEGER NOT NULL ," + // 8: total
                "\"TYPE\" TEXT);"); // 9: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_CASE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookCase entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String bookname = entity.getBookname();
        if (bookname != null) {
            stmt.bindString(2, bookname);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String finish = entity.getFinish();
        if (finish != null) {
            stmt.bindString(5, finish);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(6, img);
        }
        stmt.bindLong(7, entity.getCurPage());
        stmt.bindLong(8, entity.getPosition());
        stmt.bindLong(9, entity.getTotal());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(10, type);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookCase entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String bookname = entity.getBookname();
        if (bookname != null) {
            stmt.bindString(2, bookname);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String finish = entity.getFinish();
        if (finish != null) {
            stmt.bindString(5, finish);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(6, img);
        }
        stmt.bindLong(7, entity.getCurPage());
        stmt.bindLong(8, entity.getPosition());
        stmt.bindLong(9, entity.getTotal());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(10, type);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public BookCase readEntity(Cursor cursor, int offset) {
        BookCase entity = new BookCase( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bookname
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // finish
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // img
            cursor.getInt(offset + 6), // curPage
            cursor.getInt(offset + 7), // position
            cursor.getInt(offset + 8), // total
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookCase entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setBookname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFinish(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setImg(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCurPage(cursor.getInt(offset + 6));
        entity.setPosition(cursor.getInt(offset + 7));
        entity.setTotal(cursor.getInt(offset + 8));
        entity.setType(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BookCase entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BookCase entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
