package ai.preferred.crawler.hungryGoWhere.master;

import ai.preferred.crawler.hungryGoWhere.csv.BusinessStorage;
import ai.preferred.crawler.hungryGoWhere.entity.Business;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.response.VResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.*;
import org.jsoup.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ListingHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingHandler.class);

  @Override
  public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {
    LOGGER.info("processing: {}", request.getUrl());


    final List<Business> businessList;

    try{
      JSONObject obj = new JSONObject(new String(response.getContent()));
      String htmlString = obj.getString("resultHtml");
      final Document document = Jsoup.parse(htmlString,"https://www.hungrygowhere.com");
      businessList = ListingParser.parseListing(document);
    }catch(JSONException e){
      LOGGER.info("there is no results on this page, stopping: {}", request.getUrl());
      return;
    }

    final BusinessStorage storage = session.get(ListingCrawler.STORAGE_KEY);
    try {
      for (final Business p : businessList) {
        LOGGER.info("storing business: {} [{}]", p.getTitle(), p.getUrl());
        storage.append(p);
      }
    } catch (IOException e) {
      LOGGER.error("could not store business, stopping", e);
      return;
    }

    final String url = request.getUrl();
//    REMEMBER TO PUT ME BACK!
//
    try {
      final URIBuilder builder = new URIBuilder(url);
      int currentPage = 1;
      for (final NameValuePair param : builder.getQueryParams()) {
        if ("p".equals(param.getName())) {
          currentPage = Integer.parseInt(param.getValue());
        }
      }
      builder.setParameter("p", String.valueOf(currentPage + 1));
      final String nextPageUrl = builder.toString();
      scheduler.add(new VRequest(nextPageUrl), this);
    } catch (URISyntaxException | NumberFormatException e) {
      LOGGER.error("unable to parse url: ", e);
    }
  }

}
