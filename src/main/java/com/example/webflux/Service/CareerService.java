package com.example.webflux.Service;

import com.example.webflux.Domain.Career;
import com.example.webflux.Repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CareerService {
    private Connection connection;
    private Document document;
    private Elements elements;

    private final CareerRepository careerRepository;

    public Flux<Career> getNaverCareers() throws IOException {
        connection = Jsoup.connect("https://recruit.navercorp.com/rcrt/list.do");
        document = connection.get();
        elements = document.select("ul[class=card_list]").select("li.card_item").select("a.card_link");

        ArrayList<Career> careers = new ArrayList<>();

        int idx = 0;
        for(Element e: elements) {
            String link = e.select("a.card_link").attr("onclick").toString(); //.substring(6, -3);
            String title = e.select("h4.card_title").text();
            String company = title.split("]")[0].replace("[", "");
            Iterator<Element> iterator = e.select("dd.info_text").stream().iterator();
            String category = iterator.next().text();
            String team = iterator.next().text();
            String experience = iterator.next().text();
            String condition = iterator.next().text();
            String duration = iterator.next().text();

            careers.add(
                    Career.builder()
                            .link(link)
                            .title(title)
                            .company(company)
                            .category(category)
                            .team(team)
                            .experience(experience)
                            .conditions(condition)
                            .duration(duration)
                            .build()
            );
        }

        return careerRepository.saveAll(
                careers
        );
    }

    public Flux<Career> getNaverCloudCareers() throws IOException {
        connection = Jsoup.connect("https://recruit.navercloudcorp.com/rcrt/list.do");
        document = connection.get();
        elements = document.select("ul[class=card_list]").select("li.card_item").select("a.card_link");

        ArrayList<Career> careers = new ArrayList<>();

        int idx = 0;
        for(Element e: elements) {
            String link = e.select("a.card_link").attr("onclick").toString();//.substring(6, -3);
            String title = e.select("h4.card_title").text();
            String company = title.split("]")[0].replace("[", "");
            Iterator<Element> iterator = e.select("dd.info_text").stream().iterator();
            String category = iterator.next().text();
            String team = iterator.next().text();
            String experience = iterator.next().text();
            String condition = iterator.next().text();
            String duration = iterator.next().text();

            careers.add(
                    Career.builder()
                            .link(link)
                            .title(title)
                            .company(company)
                            .category(category)
                            .team(team)
                            .experience(experience)
                            .conditions(condition)
                            .duration(duration)
                            .build()
            );
        }

        return careerRepository.saveAll(
                careers
        );
    }
}
