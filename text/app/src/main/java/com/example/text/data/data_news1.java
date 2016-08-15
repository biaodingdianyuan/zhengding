package com.example.text.data;

import com.example.text.beans.news_bean;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘海风 on 2016/7/28.
 */

public class data_news1 extends DefaultHandler {
    public List<news_bean> getlist(String xml) throws XmlPullParserException, IOException {
        List<news_bean> persons=null;
        news_bean bean=null;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 获得xml解析类的引用
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(new StringReader(xml));
        // 获得事件的类型
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    persons = new ArrayList<news_bean>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("item".equals(parser.getName())) {
                        bean = new news_bean();

                    } else if ("title".equals(parser.getName())) {
                        String name = parser.nextText();
                        if(bean!=null){
                            bean.setTitle(name);
                        }

                    } else if ("author".equals(parser.getName())) {
                        String author = parser.nextText();
                        if (bean!=null){
                            bean.setAuthor(author);}
                    }else if ("link".equals(parser.getName())){
                        String link=parser.nextText();
                        if (bean!=null){
                            bean.setLink(link);}
                    }else if ("description".equals(parser.getName())){
                        String description =parser.nextText();
                        if (bean!=null){
                            bean.setDescription(description);}
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        persons.add(bean);
                        bean = null;
                    }
                    break;
            }
            eventType = parser.next();
        }





        return persons;
    }

}
