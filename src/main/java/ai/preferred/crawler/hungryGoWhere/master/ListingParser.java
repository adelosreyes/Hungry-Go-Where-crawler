package ai.preferred.crawler.hungryGoWhere.master;

import ai.preferred.crawler.hungryGoWhere.entity.Business;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ListingParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingParser.class);

  public static List<Business> parseListing(Document document) {




    final Elements businesses = document.select("article > div");
    final ArrayList<Business> result = new ArrayList<>(businesses.size());
    for (final Element p : businesses) {
      result.add(parseBusiness(p));
    }
    return result;
  }

  private static String textOrNull(Element element) {
    return null == element ? null : element.text();
  }

  private static Integer intOrNull(Element element) {
    if (element == null) {
      return null;
    }
    try {
      return Integer.parseInt(element.text());
    } catch (NumberFormatException e) {
      LOGGER.error("could not parse integer", e);
      return null;
    }
  }

  private static Business parseBusiness(Element e) {
    final Business business = new Business();

    final Element extension = (e.select("div.title-wrap > h2 > a").first());

    business.setUrl((extension.attr("abs:href")));
    business.setTitle(extension.text());
    business.setPrice(textOrNull(e.select("div.for-votes > span.price-range").first()));
    business.setAddress(textOrNull(e.select("div > span.location").first()));
    business.setType(textOrNull(e.selectFirst("div.for-votes > span.category-name")));
    business.setCuisine(textOrNull(e.select("div.for-votes > span.cuisine").first()));

    return business;
  }

  private ListingParser() {
    throw new AssertionError();
  }

}
