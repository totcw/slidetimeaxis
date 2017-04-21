package com.lyf.slidetime;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lyf.slidetime.application.MyApplication;
import com.lyf.slidetime.db.BookDao;
import com.lyf.slidetime.javabean.Book;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lyf.slidetime", appContext.getPackageName());
    }

    @Test
    public void testDb() throws Exception{
        BookDao dao = MyApplication.getInstance().getDaoSession().getBookDao();
        dao.insert(new Book("gl","测试"));
    }
}
