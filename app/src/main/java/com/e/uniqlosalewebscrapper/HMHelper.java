package com.e.uniqlosalewebscrapper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


// HM works well, loads 500 items.

public class HMHelper {
    public void getWebsiteItems(final MainActivity activity){
        final ArrayList<Item> items = new ArrayList<>();
        final int numOfItems = 500; // max number of items to load
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{ // Scraps HM Mens Medium Sale Page
                    Document doc = Jsoup.connect("https://www2.hm.com/en_us/sale/men/view-all.html?sort=stock&sizes=305_m_3_menswear&image-size=small&image=stillLife&offset=0&page-size="+numOfItems).get(); // url changes by size, add this customization later
                    Elements elements = doc.getElementsByClass("product-item");
                    for (Element element : elements){
                        String productName = element.select(".item-heading").select(".link").text(); // working
                        String productLink = element.select(".item-heading").select(".link").attr("href");
                        String productImageLink = element.select(".image-container").select(".item-image").attr("data-src"); // the links returned by this are weird, it doesnt work with glide
                        String productOrigPrice = element.select(".item-details").select(".item-price").select(".price.regular").text();
                        String productSalePrice = element.select(".item-details").select(".item-price").select(".price.sale").text(); // . to replace whitespace in classname
                        //Log.d(TAG, "run: " + productImageLink.substring(2));
                        Log.d(TAG, "run: " + items);
                        items.add(new Item(productName, "H&M", "https://www2.hm.com/"+productLink,"https:"+productImageLink,productOrigPrice,productSalePrice));
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setItemsArr(items);
                    }
                });
            }
        }).start();
    }
}
