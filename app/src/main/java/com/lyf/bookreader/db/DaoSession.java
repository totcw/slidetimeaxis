package com.lyf.bookreader.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lyf.bookreader.javabean.Book;
import com.lyf.bookreader.javabean.BookCase;

import com.lyf.bookreader.db.BookDao;
import com.lyf.bookreader.db.BookCaseDao;

// THIS CODE IS GENE RATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookDaoConfig;
    private final DaoConfig bookCaseDaoConfig;

    private final BookDao bookDao;
    private final BookCaseDao bookCaseDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookDaoConfig = daoConfigMap.get(BookDao.class).clone();
        bookDaoConfig.initIdentityScope(type);

        bookCaseDaoConfig = daoConfigMap.get(BookCaseDao.class).clone();
        bookCaseDaoConfig.initIdentityScope(type);

        bookDao = new BookDao(bookDaoConfig, this);
        bookCaseDao = new BookCaseDao(bookCaseDaoConfig, this);

        registerDao(Book.class, bookDao);
        registerDao(BookCase.class, bookCaseDao);
    }
    
    public void clear() {
        bookDaoConfig.getIdentityScope().clear();
        bookCaseDaoConfig.getIdentityScope().clear();
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public BookCaseDao getBookCaseDao() {
        return bookCaseDao;
    }

}
