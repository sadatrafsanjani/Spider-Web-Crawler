package com.rafsanjani.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BreadthFirstSearch {

    private Queue<String> queue;
    private List<String> crawled;

    public BreadthFirstSearch() {
        this.queue = new LinkedList<>();
        this.crawled = new ArrayList<>();
    }

    public void crawlSite(String root) {

        queue.add(root);
        crawled.add(root);

        while (!queue.isEmpty()) {

            String hyperlink = queue.remove();
            List<String> list = spider(hyperlink);

            for (String s : list) {
                if (!crawled.contains(s)) {
                    crawled.add(s);
                    System.out.println(s);
                    queue.add(s);
                }
            }
        }
    }

    private List<String> spider(String link) {

        List<String> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(link)
                    .userAgent(
                            "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").ignoreContentType(true).timeout(3000).get();

            Elements hrefs = doc.select("a[href]");
            for (Element element : hrefs) {

                String url = element.attr("abs:href");

                if (isValid(url)) {
                    list.add(url);
                }
            }

        } catch (IOException e) {
            System.out.println("---------");
        }

        return list;
    }

    private boolean isValid(String url) {

        Pattern p = Pattern.compile("^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$");
        Matcher m = p.matcher(url);

        return m.matches();
    }
}
