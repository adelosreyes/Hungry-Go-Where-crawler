package ai.preferred.crawler.hungryGoWhereV2.master;

import ai.preferred.crawler.hungryGoWhereV2.csv.BusinessStorage;
import ai.preferred.crawler.hungryGoWhereV2.entity.Business;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.response.VResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.URISyntaxException;
import java.util.List;

public class ListingHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingHandler.class);

  @Override
  public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {
    LOGGER.info("processing: {}", request.getUrl());


    final Document document = response.getJsoup();
    final Business business = ListingParser.parseListing(document, request.getUrl());


//    final List<Business> businessList;
//
//    try{
//      JSONObject obj = new JSONObject(new String(response.getContent()));
//      String htmlString = obj.getString("resultHtml");
//      final Document document = Jsoup.parse(htmlString,"https://www.hungrygowhere.com");
//      businessList = ListingParser.parseListing(document);
//    }catch(JSONException e){
//      LOGGER.info("there is no results on this page, stopping: {}", request.getUrl());
//      return;
//    }

    final BusinessStorage storage = session.get(ListingCrawler.STORAGE_KEY);

    try {
      LOGGER.info("storing business: {} [{}]", business.getTitle(), business.getUrl());
      storage.append(business);
    } catch (IOException e) {
      LOGGER.error("could not store business, stopping", e);
      return;
    }

    final String url = request.getUrl();
//    REMEMBER TO PUT ME BACK!
//
//    try {
//      final URIBuilder builder = new URIBuilder(url);
//      int currentPage = 1;
//      for (final NameValuePair param : builder.getQueryParams()) {
//        if ("p".equals(param.getName())) {
//          currentPage = Integer.parseInt(param.getValue());
//        }
//      }
//      builder.setParameter("p", String.valueOf(currentPage + 1));
//      final String nextPageUrl = builder.toString();
//      scheduler.add(new VRequest(nextPageUrl), this);
//    } catch (URISyntaxException | NumberFormatException e) {
//      LOGGER.error("unable to parse url: ", e);
//    }



    //THIS ONE PUT BACK LATER
//      String csvFile = "data/hungryGoWhere.csv";
//      String line = "";
//      String cvsSplitBy = ",";
//      int counter = 3;
//
//      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//        int otherCounter = 1;
//        while ((line = br.readLine()) != null) {
//        String[] information = line.split(cvsSplitBy);
//
////        if(!request.getUrl().equals(information[0]) && !information[0].equals("url")){
////          final String nextPageUrl = information[0];
////          scheduler.add(new VRequest(nextPageUrl), this);
////          System.out.println(information[0]);
////        }
//        if(counter == otherCounter){
//          final String nextPageUrl = information[0];
//          scheduler.add(new VRequest(nextPageUrl), this);
//          System.out.println(information[0] + " " + counter);
//          counter ++;
//        }
//        otherCounter ++;
//      }
//
//      } catch (IOException e) {
//      e.printStackTrace();
//      }

  }

}
