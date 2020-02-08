package com.e.uniqlosalewebscrapper;

import android.app.Activity;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


// JSOUP cannot dynamically scroll through the page so it will only be able to show the first 16 results, even if there are more.
// This would work better if UNIQLO made a separate URL for the next scroll or loaded all of the items at once, but its not.
// Set it to best sellers in mens medium just to make myself feel better that it'll show more important results, still a dud.

public class UniqloHelper {
    public void getWebsiteItems(final MainActivity activity){
        final ArrayList<Item> items = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{ // Scraps UNIQLO Mens Medium Sale Page
                    Document doc = Jsoup.connect("https://www.uniqlo.com/us/en/men/sale/m?srule=best-sellers&ptid=men-sale").get();
                    Elements elements = doc.getElementsByClass("product-tile-info");
                    for (Element element : elements){
                        String productName = element.select(".product-name").text();
                        String productLink = element.select(".thumb-link").first().attr("href");
                        String productImageLink = element.select("img").first().attr("src");
                        String productOrigPrice = element.select(".product-standard-price").text();
                        String productSalePrice = element.select(".product-sales-price").text();
                        items.add(new Item(productName, "Uniqlo", productLink,productImageLink,productOrigPrice,productSalePrice));
                    }
                    Log.d(TAG, "results: " + items.size());
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
