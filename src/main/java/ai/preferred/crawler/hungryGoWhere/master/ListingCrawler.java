package ai.preferred.crawler.hungryGoWhere.master;

import ai.preferred.crawler.hungryGoWhere.csv.BusinessStorage;
import ai.preferred.crawler.hungryGoWhere.entity.Business;
import ai.preferred.venom.Crawler;
import ai.preferred.venom.Session;
import ai.preferred.venom.SleepScheduler;
import ai.preferred.venom.fetcher.AsyncFetcher;
import ai.preferred.venom.fetcher.Fetcher;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.validator.EmptyContentValidator;
import ai.preferred.venom.validator.StatusOkValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ListingCrawler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingCrawler.class);

  static final Session.Key<BusinessStorage> STORAGE_KEY = new Session.Key<>();



  public static void main(String[] args) {
    //where they store the information
    final String filename = "data/hungryGoWhere.csv";
    try (final BusinessStorage storage = new BusinessStorage(filename)) {

      storage.append(Business.getHeader());

      final Session session = Session.builder()
          .put(STORAGE_KEY, storage)
          .build();

      try (final Crawler crawler = crawler(fetcher(), session).start()) {
        LOGGER.info("starting crawler...");

        final String startUrl = "https://www.hungrygowhere.com/search-results/?search_location=Singapore&general=1&p=1";
        crawler.getScheduler().add(new VRequest(startUrl), new ListingHandler());
      } catch (Exception e) {
        LOGGER.error("Could not run crawler: ", e);
      }

    } catch (IOException e) {
      LOGGER.error("unable to open file: {}, {}", filename, e);
    }
  }

  private static Fetcher fetcher() {
    return AsyncFetcher.builder()
        .validator(
            EmptyContentValidator.INSTANCE,
            StatusOkValidator.INSTANCE)
        .build();
  }

  private static Crawler crawler(Fetcher fetcher, Session session) {
    return Crawler.builder()
        .fetcher(fetcher)
        .session(session)
        // Just to be polite
        .sleepScheduler(new SleepScheduler(1500, 3000))
        .build();
  }

}
