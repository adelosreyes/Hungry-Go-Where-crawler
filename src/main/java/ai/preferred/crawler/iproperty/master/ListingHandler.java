package ai.preferred.crawler.iproperty.master;

import ai.preferred.crawler.iproperty.csv.PropertyStorage;
import ai.preferred.crawler.iproperty.entity.Property;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ListingHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingHandler.class);

  @Override
  public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {
    LOGGER.info("processing: {}", request.getUrl());

    final Document document = response.getJsoup();
    //create Business Class
    final List<Property> propertyList = ListingParser.parseListing(document);

    if (propertyList.isEmpty()) {
      LOGGER.info("there is no results on this page, stopping: {}", request.getUrl());
      return;
    }

    final PropertyStorage storage = session.get(ListingCrawler.STORAGE_KEY);
    try {
      for (final Property p : propertyList) {
        LOGGER.info("storing property: {} [{}]", p.getTitle(), p.getUrl());
        storage.append(p);
      }
    } catch (IOException e) {
      LOGGER.error("could not store property, stopping", e);
      return;
    }

    final String url = request.getUrl();
    try {
      final URIBuilder builder = new URIBuilder(url);
      int currentPage = 1;
      for (final NameValuePair param : builder.getQueryParams()) {
        if ("page".equals(param.getName())) {
          currentPage = Integer.parseInt(param.getValue());
        }
      }
      builder.setParameter("page", String.valueOf(currentPage + 1));
      final String nextPageUrl = builder.toString();
      scheduler.add(new VRequest(nextPageUrl), this);
    } catch (URISyntaxException | NumberFormatException e) {
      LOGGER.error("unable to parse url: ", e);
    }
  }

}
