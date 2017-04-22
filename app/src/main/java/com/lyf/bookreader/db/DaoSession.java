package com.lyf.bookreader.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lyf.bookreader.javabean.BookCase;
import com.lyf.bookreader.javabean.Chapter;

import com.lyf.bookreader.db.BookCaseDao;
import com.lyf.bookreader.db.ChapterDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookCaseDaoConfig;
    private final DaoConfig chapterDaoConfig;

    private final BookCaseDao bookCaseDao;
    private final ChapterDao chapterDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookCaseDaoConfig = daoConfigMap.get(BookCaseDao.class).clone();
        bookCaseDaoConfig.initIdentityScope(type);

        chapterDaoConfig = daoConfigMap.get(ChapterDao.class).clone();
        chapterDaoConfig.initIdentityScope(type);

        bookCaseDao = new BookCaseDao(bookCaseDaoConfig, this);
        chapterDao = new ChapterDao(chapterDaoConfig, this);

        registerDao(BookCase.class, bookCaseDao);
        registerDao(Chapter.class, chapterDao);
    }
    
    public void clear() {
        bookCaseDaoConfig.getIdentityScope().clear();
        chapterDaoConfig.getIdentityScope().clear();
    }

    public BookCaseDao getBookCaseDao() {
        return bookCaseDao;
    }

    public ChapterDao getChapterDao() {
        return chapterDao;
    }

}
