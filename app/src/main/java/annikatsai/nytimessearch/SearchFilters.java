package annikatsai.nytimessearch;

import org.parceler.Parcel;

@Parcel
public class SearchFilters{

    public SearchFilters(){}

    public SearchFilters(String beginDate, String sortFilter, String newsDesk) {
        begin_date = beginDate;
        sort = sortFilter;
        news_desk = newsDesk;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public String getSort() {
        return sort;
    }

    public String getNews_desk() {
        return news_desk;
    }

    String begin_date;
    String sort;
    String news_desk;
}
