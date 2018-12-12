package ai.preferred.crawler.hungryGoWhereV2.master;

import ai.preferred.crawler.hungryGoWhereV2.entity.Business;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ListingParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingParser.class);

  public static Business parseListing(Document document, String url) {

    return parseBusiness(document, url);

//    final Elements businesses = document.select("article > div");
//    final ArrayList<Business> result = new ArrayList<>(businesses.size());
//    for (final Element p : businesses) {
//      result.add(parseBusiness(p));
//    }
//    return result;
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

  private static Business parseBusiness(Element e, String url) {
    final Business business = new Business();

    final Element extension = (e.select("div.title-wrap > h2 > a").first());

    business.setUrl(url);
    business.setTitle(textOrNull(e.select("div.left > div.module-ibl-summary > h1").first()));
    business.setPriceLevel(textOrNull(e.select("div.left > div.module-ibl-summary > div:nth-child(3) > span.price-range").first()));
    business.setAddress(textOrNull(e.select("div.left > div.module-ibl-summary > p").first()));
    business.setType(textOrNull(e.selectFirst("div.left > div.module-ibl-summary > div:nth-child(3) > span.category-name")));
    business.setCuisine(textOrNull(e.select("div.left > div.module-ibl-summary > div:nth-child(3) > span.cuisine").first()));
    business.setSummary(textOrNull(e.select("div.left > div.module-ibl-summary > div.ibl-des > div > p").first()));
    business.setReservationNum(textOrNull(e.selectFirst("div.info-detail > dl:nth-child(1) > dd")));
    business.setNearestMrt(textOrNull(e.select("div.info-detail > dl:nth-child(3) > dd").first()));
    business.setOpeningHours(textOrNull(e.select("div.info-detail > dl:nth-child(5) > dd").first()));
    business.setAvgPrice(textOrNull(e.selectFirst("div.info-detail > dl:nth-child(6) > dd")));
    business.setSuitableFor(textOrNull(e.select("div.info-detail > dl:nth-child(7) > dd").first()));
    business.setPhoneNum(textOrNull(e.select("div.info-detail > dl:nth-child(8) > dd").first()));
    business.setWebsite(textOrNull(e.selectFirst("div.info-detail > dl:nth-child(9) > dd")));
    business.setGoodFor(textOrNull(e.select("div.info-detail > dl:nth-child(10) > dd").first()));

    return business;
  }

  private ListingParser() {
    throw new AssertionError();
  }

}
